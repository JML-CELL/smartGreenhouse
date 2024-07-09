#include "cJSON.h"
#include "WiFi.h"
#include "Arduino.h"
#include "PubSubClient.h"
#include "Adafruit_BME280.h"
#include "Adafruit_Sensor.h"
#include "IRremoteESP8266.h"
#include "IRsend.h"
#include "ir_Gree.h"
#include "IRrecv.h"

/*
   引脚应用
   P21,P22 BME280
   TX2,RX2 STM21
   P13     AIR
   P12     HUIMIDIFIER
*/

//控制命令参数
int8_t air_flag;
char   air_mode;
int8_t air_temp;

int8_t humidity_flag;
int8_t humidity_top;
int8_t humidity_floor;
//BME280参数
#define I2C_SDA 21         
#define I2C_SCL 22
#define BME280_ADDRESS 0x76
#define ALTITUDE 216.0

//float temperature_sensor;
//float humidity_sensor;
//float pressure_sensor;

Adafruit_BME280 bme;
TwoWire I2CSersor = TwoWire(0);

//红外遥控参数
const uint16_t infrared =13;
IRGreeAC ac(infrared);

//MQTT上传参数 
float   temperature;            //温度
int32_t air_humidity;           //空气相对湿度 
int32_t soil_humidity;          //土壤相对湿度
int32_t light_intensity;        //光强度
float   atmos;                  //大气压
float   water_ph;               //水体ph
int32_t carbon_dioxide;         //二氧化碳
int32_t water_level;            //水位
int32_t smoke;                  //烟雾浓度标志位（0没有，1有）

float   temperature_hour;       //温度
int32_t air_humidity_hour;      //空气相对湿度 
int32_t soil_humidity_hour;     //土壤相对湿度
int32_t light_intensity_hour;   //光强度
float   atmos_hour;             //大气压
float   water_ph_hour;          //水体ph
int32_t carbon_dioxide_hour;    //二氧化碳
int32_t water_level_hour;       //水位


// WiFi连接信息
const char* WIFI_SSID = "xdedm7";        //wifi名称
const char* WIFI_PASSPAUSE = "a20010722";//密码
//const char* WIFI_SSID = "525-1";        //wifi名称
//const char* WIFI_PASSPAUSE = "dq123456";//密码

// MQTT服务器信息
const char* MQTT_Server = "mqtts.heclouds.com";//mqtt请求网站
const int   MQTT_Port = 1883;                  //端口号
const char* MQTT_ID = "T558h4Pj6O";            //产品id
//const char* MQTT_ID = "1CzOiM8X29";            //产品id
const char* MQTT_PASSWORD = "version=2018-10-31&res=products%2FT558h4Pj6O%2Fdevices%2Fesp32&et=2022033248&method=md5&sign=8XAlnl6TPUq1GlxrFCmgNQ%3D%3D"; //密钥生成的token
//const char* MQTT_PASSWORD = "version=2018-10-31&res=products%2F1CzOiM8X29%2Fdevices%2Fesp3222&et=2025092161&method=md5&sign=ZiYA4k5Rr8R4GRSTn1SHPw%3D%3D"; //密钥生成的token
const char* MQTT_NAME = "esp32";               //设备名称
//const char* MQTT_NAME = "esp3222";               //设备名称

#define ONENET_POST        "$sys/T558h4Pj6O/esp32/thing/property/post"                    //设备属性上报请求
#define ONENET_REPLY       "$sys/{pid}/{device-name}/thing/event/post/reply"              //设备事件上报响应
#define ONENET_INVOKE      "$sys/{pid}/{device-name}/thing/service/{identifier}/invoke"   //设备服务调用请求
#define ONENET_SET         "$sys/T558h4Pj6O/esp32/thing/property/set"                     //设备属性获取请求

//属性上报格式
#define ONENET_FORMAT2       "{\"id\":\"1706446339308\",\"version\":\"1.0\",\"params\":{\"air_humidity\":{\"value\":%d},\"atmos\":{\"value\":%0.2f},\"light_intensity\":{\"value\":%d},\"soil_humidity\":{\"value\":%d}}}"
#define ONENET_FORMAT      "{\"id\":\"1706446339308\",\"version\":\"1.0\",\"params\":{\"temperature\":{\"value\":%0.1f},\"water_ph\":{\"value\":%0.1f},\"carbon_dioxide\":{\"value\":%d},\"water_level\":{\"value\":%d},\"smoke\":{\"value\":%d}}}"
#define ONENET_FORMAT_hour  "{\"id\":\"1706446339308\",\"version\":\"1.0\",\"params\":{\"air_humidity_hour\":{\"value\":%d},\"atmos_hour\":{\"value\":%0.2f},\"light_intensity_hour\":{\"value\":%d},\"soil_humidity_hour\":{\"value\":%d}}}"
#define ONENET_FORMAT_hour2 "{\"id\":\"1706446339308\",\"version\":\"1.0\",\"params\":{\"water_ph_hour\":{\"value\":%0.1f},\"temperature_hour\":{\"value\":%0.1f},\"carbon_dioxide_hour\":{\"value\":%d},\"water_level_hour\":{\"value\":%d}}}"

// 创建WiFi客户端和MQTT客户端实例
WiFiClient wifiClient;
PubSubClient mqttClient(wifiClient);

//发送红外函数
void send_ir(char a,int8_t b)
{
  ac.on();
  ac.setFan(1);
  //kGreeAuto,kGreeDry,kGreeCool,kGreeFan,kGreeHeat
  ac.setMode(a);
  ac.setTemp(b);
  ac.setSwingVertical(true,kGreeSwingAuto);
  ac.setFan(false);
  ac.setLight(false);
  ac.setSleep(false);
  ac.setTurbo(false);
  //Serial.println(ac.toString());//显示发送的空调开机红外编码
  ac.send();//发送红外命令
}

//BME280初始化函数
void init_bme280()
{
  I2CSersor.begin(I2C_SDA,I2C_SCL,100000);
  if (!bme.begin(0x76,&I2CSersor))
  {
     Serial.println("Could not find a valid BME280 sensor, check wiring!");
     while (1);
  }
  else{ Serial.println("BME280 Sensor Found"); }
}

//获取BME280数据函数
void getbme280value()
{
  temperature = bme.readTemperature();
  air_humidity = bme.readHumidity();
  atmos = bme.readPressure()/1000;
  //pressure_sensor = bme.seaLevelForAltitude(ALTITUDE,pressure_sensor);
  //pressure_sensor = pressure_sensor/100.0F;
  //Serial.println(temperature);
  //Serial.println(air_humidity);
  //Serial.println(atmos);
}

// 比较两字母字符串 相等返回1 不相等返回0
int cmp_str(uint8_t str1[],char str2[])
{
    int i=0;
     for(i=0;str1[i]!='\0'&&str2[i]!='\0';i++)
    {
        if(str1[i]!=str2[i])
        {
            return 0;
        }
    }
    return 1;
}


//接收信号参数(手动控制)
#define light_on_r_g_b             "light_on_r_g_b"                //开灯    R (三位数)  G (三位数) B (三位数)        3xxxxxxxxx
#define light_off                  "light_off"                     //关灯                                            3000000000
#define fan_on                     "fan_on"                        //风扇开                                          11xxxxxxxx
#define fan_off                    "fan_off"                       //风扇关                                          21xxxxxxxx
#define water_on                   "water_on"                      //水的水泵开                                      12xxxxxxxx
#define water_off                  "water_off"                     //水的水泵关                                      22xxxxxxxx
#define pesticide_on               "pesticide_on"                  //农药的水泵开                                    13xxxxxxxx
#define pesticide_off              "pesticide_off"                 //农药的水泵关                                    23xxxxxxxx
#define humidifier_on              "humidifier_on"                 //加湿器开                                        
#define humidifier_off             "humidifier_off"                //加湿器关                                       
#define air_off                    "air_off"                       //空调关 
#define air_on_temperature_mood    "air_on_temperature_speed_mood" //空调开  TEMPERATURE(两位数,16-30)  speed(一位数，0-5) MOOD(COOL,HOT)
#define music_on                   "music_on"                      //音乐开                                          14xxxxxxxx
#define music_off                  "music_off"                     //音乐关                                          24xxxxxxxx
#define music_next                 "music_next"                    //音乐下一首                                      51xxxxxxxx
#define smoke_on                   "smoke_on"                      //烟雾报警开                                      15xxxxxxxx
#define smoke_off                  "smoke_off"                     //烟雾报警关                                      25xxxxxxxx
#define curtain_on                 "curtain_on"                    //窗帘开                                          16xxxxxxxx
#define curtain_off                "curtain_off"                   //窗帘关                                          26xxxxxxxx

//接收信号参数（自动控制）
#define auto_temperature_toplimit_floor     "auto_temperature_threshold"           //设置温度         threshold(两位数)                        
#define auto_air_humidity_toplimit_floor    "auto_air_humidity_toplimit_floor"     //设置空气湿度     toplimit (两位数)        floor(两位数)    
#define auto_soil_humidity_toplimit_floor   "auto_soil_humidity_toplimit_floor"    //设置土壤湿度     toplimit (两位数)        floor(两位数)     41xxxx0000
#define auto_light_intensity_toplimit_floor "auto_light_intensity_toplimit_floor"  //设置光强         toplimit (四位数)        floor(四位数)     42xxxxxxxx
#define auto_carbon_dioxide_toplimit_floor  "auto_carbon_dioxide_toplimit_floor"   //设置二氧化碳     toplimit (四位数)        floor(四位数)     43xxxxxxxx

//接收ONENET云服务器数据回调函数
void OneNET_recivecallback(char *topic, byte *payload, unsigned int length)
{
    //Serial.println(topic);
    int i = 11;
    int j = 0;

    //获取commond位置
     for (size_t i = 11; i < length; i++)
    {
        //Serial.print((char)payload[i]);
        if(payload[i]=='d') 
        {
            j=i;
            break;
        }
    }
        for (size_t i = 0; i < length; i++)
    {
        Serial.print((char)payload[i]);

    }
        if(payload[j+4]=='b')        //水肥一体控制
      {
          if(payload[j+5]=='b')
          { Serial2.print(6600000000); }
          
      }
        if(payload[j+4]=='l')        //light
      {
          if(payload[j+11]=='f')
        { Serial2.print(3000000000); }
          else
        { Serial2.print(3);  Serial2.print((char)payload[j+13]); Serial2.print((char)payload[j+14]);Serial2.print((char)payload[j+15]);       Serial2.print((char)payload[j+17]);
          Serial2.print((char)payload[j+18]);Serial2.print((char)payload[j+19]);   Serial2.print((char)payload[j+21]);Serial2.print((char)payload[j+22]);Serial2.print((char)payload[j+23]);}
      }

        if(payload[j+4]=='f')        //fan
      {
          if(payload[j+9]=='f')
        { Serial2.print(2100000000); }
          else
        { Serial2.print(1100000000); }
      }

        if(payload[j+4]=='w')        //water
      {
          if(payload[j+11]=='f')
        { Serial2.print(2200000000); }
          else
        { Serial2.print(1200000000); }
      }

        if(payload[j+4]=='p')        //pesticide
      {
          if(payload[j+15]=='f')
        { Serial2.print(2300000000); }
          else
        { Serial2.print(1300000000); }
      }

        if(payload[j+4]=='h')        //humidifier
      {
          if(payload[j+16]=='f')
        {  digitalWrite(12,LOW);  }
          else
        {  digitalWrite(12,HIGH); }
      }

        if(payload[j+4]=='a')        //air
      {
          if(payload[j+9]=='f')
        {
           ac.off();
           Serial.println(ac.toString());//显示发送的空调开机红外编码
           ac.send();//发送红外命令
           Serial.println("AIR_OFF");
        }
          if(payload[j+9]=='n')
        {
           Serial.println("AIR_ON");
           ac.on();
           if(payload[j+16]=='C')
           {
             air_mode=kGreeCool;
           }
           else
           {
            air_mode=kGreeHeat;
           }
           ac.setMode(air_mode);
           air_temp=((int)(payload[j+11])-48)*10+((int)(payload[j+12])-48);
           ac.setTemp(air_temp);
           ac.setFan(((int)(payload[j+14])-48));
           Serial.println(ac.toString());//显示发送的空调开机红外编码
           ac.send();//发送红外命令
        }
      }

        if(payload[j+4]=='m')        //music
      {
          if(payload[j+11]=='f')
        { Serial2.print(2400000000); }
          else if(payload[j+11]=='n')
        { Serial2.print(1400000000); }
          else
        { Serial2.print(5100000000); }
      }

        if(payload[j+4]=='s')        //smoke
      {
          if(payload[j+11]=='f')
        { Serial2.print(2500000000); }
          else
        { Serial2.print(1500000000); }
      }

        if(payload[j+4]=='c')        //curtain
      {
          if(payload[j+13]=='f')
        { Serial2.print(2600000000); }
          else
        { Serial2.print(1600000000); }
      }

      //自动控制
        if(payload[j+4]=='a'&&payload[j+5]=='u')     //start auto
      {
         if(payload[j+9]=='t')                       //temperature                             
         {    
           air_temp=((int)(payload[j+21])-48)*10+((int)(payload[j+22])-48);
           air_flag=1;
         }
         if(payload[j+9]=='a')                       //air_humidity
         {
           humidity_top  = ((int)(payload[j+22])-48)*10+((int)(payload[j+23])-48);
           humidity_floor= ((int)(payload[j+25])-48)*10+((int)(payload[j+26])-48);
           Serial.print(humidity_top);Serial.print(humidity_floor);
           humidity_flag=1;
         }
         if(payload[j+9]=='s')                       //soid_humidity
         {
           Serial2.print(41);Serial2.printf("%d",(int)(payload[j+23])-48);Serial2.printf("%d",(int)(payload[j+24])-48);
                             Serial2.printf("%d",(int)(payload[j+26])-48);Serial2.printf("%d",(int)(payload[j+27])-48);Serial2.print("0000");
         }
         if(payload[j+9]=='l')                       //light_intensity
         {
           Serial2.print(42);Serial2.printf("%d",(int)(payload[j+25])-48);Serial2.printf("%d",(int)(payload[j+26])-48);
                             Serial2.printf("%d",(int)(payload[j+27])-48);Serial2.printf("%d",(int)(payload[j+28])-48);
                             Serial2.printf("%d",(int)(payload[j+30])-48);Serial2.printf("%d",(int)(payload[j+31])-48);
                             Serial2.printf("%d",(int)(payload[j+32])-48);Serial2.printf("%d",(int)(payload[j+33])-48);
         }
         if(payload[j+9]=='c')                       //carbon_dioxide
         {
           Serial2.print(43);Serial2.printf("%d",(int)(payload[j+24])-48);Serial2.printf("%d",(int)(payload[j+25])-48);
                             Serial2.printf("%d",(int)(payload[j+26])-48);Serial2.printf("%d",(int)(payload[j+27])-48);
                             Serial2.printf("%d",(int)(payload[j+29])-48);Serial2.printf("%d",(int)(payload[j+30])-48);
                             Serial2.printf("%d",(int)(payload[j+31])-48);Serial2.printf("%d",(int)(payload[j+32])-48);
         }
      }
}
 
// 设置MQTT连接函数
void MQTT_Connect(void)
{
    // 连接MQTT服务器
    mqttClient.setServer(MQTT_Server, MQTT_Port);
    mqttClient.setCallback(OneNET_recivecallback);
    
    delay(500);
    while(!mqttClient.connect(MQTT_NAME, MQTT_ID, MQTT_PASSWORD))
    {
        Serial.println("Onenet Server Connect Failed. Client State:");
        Serial.println(mqttClient.state());
        delay(800);
    }
    Serial.println("Onenet Connected.");
    //Serial.println("Server Address: ");
    // Serial.println(mqttServer); 
    // subscribeTopic_test();
    // delay(200);
    //mqttClient.setCallback(OneNET_recivecallback);
}

//设备属性上报函数(秒)
void MQTT_VALUEPOST(void)
{
  //  char param[256];
    char jsonbuf[256];
    char jsonbuf4[256];

    sprintf(jsonbuf,ONENET_FORMAT2,air_humidity,atmos,light_intensity,soil_humidity);
    Serial.println(jsonbuf);
     mqttClient.publish(ONENET_POST,jsonbuf);
    sprintf(jsonbuf4,ONENET_FORMAT,temperature,water_ph,carbon_dioxide,water_level,smoke);
     Serial.println(jsonbuf4);
    mqttClient.publish(ONENET_POST,jsonbuf4);
}

//设备属性上报函数(时)
void MQTT_VALUEPOST_HOUR(void)
{
  //char param[256];
    //char jsonbuf2[256];
    //char jsonbuf3[512];

   // sprintf(jsonbuf2,ONENET_FORMAT_hour,air_humidity_hour,atmos_hour,light_intensity_hour,soil_humidity_hour);
    //Serial.println(jsonbuf2);
    //mqttClient.publish(ONENET_POST,jsonbuf2);
    //sprintf(jsonbuf3,ONENET_FORMAT_hour2,water_ph_hour,temperature_hour,carbon_dioxide_hour,water_level_hour);
    //Serial.println(jsonbuf3);
   // mqttClient.publish(ONENET_POST,jsonbuf3);
}

// 设置WiFi连接函数
void WiFi_Connect(void)
{
    WiFi.mode(WIFI_STA);
    WiFi.setTxPower(WIFI_POWER_5dBm);
    WiFi.begin(WIFI_SSID, WIFI_PASSPAUSE,6);
	  while (WiFi.status() != WL_CONNECTED)
	{ 
    Serial.println("wifi Connected.");
		delay(800);
	}
    delay(800);
}

// 重新连接MQTT服务器函数
void reconnectMQTT() {
  while (!mqttClient.connected()) 
  {
      Serial.println("正在连接到MQTT...");
    if (mqttClient.connect(MQTT_NAME, MQTT_ID, MQTT_PASSWORD)) {
      Serial.println("连接到MQTT");
    } else {
      delay(5000);
    }
  }
}

//线程1 每秒上报一次属性
void xTaskOne(void *xTask1)
{
    delay(2000);
    while (1)
    {
         // getbme280value();
            delay(900);
          MQTT_VALUEPOST();
          delay(100);
    }
}

//线程2 串口接收数据
void xTaskTwo(void *xTask2)
{  
    while (1)
    {       
        //检查串口2数据接受缓冲区
              if(Serial2.available())
            {
                 soil_humidity = Serial2.read();   //土壤湿度
                 if(Serial2.available())
               {
                    light_intensity = 100 * Serial2.read();  //光强
                    if(Serial2.available())
                  {
                      light_intensity += Serial2.read(); 
                      if(Serial2.available())
                    {
                         water_ph = (Serial2.read() / 10.0);    //水质ph    
                         if(Serial2.available())
                       {  
                            carbon_dioxide = (Serial2.read()*100);//二氧化碳浓度
                            if(Serial2.available())
                          {
                               carbon_dioxide += Serial2.read();
                               if(Serial2.available())
                             {  
                                   water_level = (Serial2.read()*100);//水位
                                   if(Serial2.available())
                                {
                                     water_level += Serial2.read();
                                     if(Serial2.available())
                                  {
                                        smoke = Serial2.read();       //烟雾标志位
                                  }
                                }
                             }
                          }
                       }
                    }        
                  }
               }
            }
          
    }
}

//线程3 每小时上报一次属性
void xTaskThree(void *xTask3)
{   
    delay(1300);
   // getbme280value();
    atmos_hour = atmos;             
    water_ph_hour = water_ph;          
    temperature_hour = temperature;       
    water_level_hour = water_level;
    air_humidity_hour = air_humidity;   
    soil_humidity_hour = soil_humidity;     
    carbon_dioxide_hour = carbon_dioxide;
    light_intensity_hour = light_intensity; 

    while (1)
    {  
      //   MQTT_VALUEPOST_HOUR();
         delay(900000);
             delay(900000);
                 delay(900000);
                     delay(900000);
    }
}

//线程4 自动控制
void xTaskFour(void *xTask4)
{   
     while (1)
    {  
       if(air_flag)                  //temp_auto
       {
          //Serial.print(air_temp);
           if(temperature>air_temp)
         {
           send_ir(kGreeCool,air_temp-abs(((int)temperature)-air_temp));
           //Serial.print(air_temp-abs(((int)temperature)-air_temp));
         }
           else 
         {
           send_ir(kGreeHeat,air_temp+abs(((int)temperature)-air_temp));
           //Serial.print(air_temp+abs(((int)temperature)-air_temp));
         }
         delay(20);
       }

        if(humidity_flag)            //humidity_auto
       {
          //Serial.print(air_temp);
           if(air_humidity>humidity_top)
         {
           digitalWrite(12,LOW);
           //Serial.println("HUMIDIFIER_OFF");
         }
           if(air_humidity<humidity_floor)
         {
           digitalWrite(12,HIGH);
           //Serial.println("HUMIDIFIER_ON");
         }
         delay(20);
       }

        if(!(air_flag+humidity_flag)) //auto_off
      {
        delay(100);
        //Serial.println("auto_on");
      }

    }
}


void setup()
{
   Serial.begin(115200);   //配置串口0   RX0 TX0
   Serial2.begin(115200);  //配置串口2  RX2 TX2
   ac.begin();             //配置红外
   WiFi_Connect();         //连接WIFI
   MQTT_Connect();         //连接mqtt
   mqttClient.subscribe(ONENET_SET); 
   pinMode(12,OUTPUT);
  // init_bme280();        //初始化bme280
   
   //创建线程1,2,3,4
    xTaskCreate(
        xTaskOne,  /* Task function. */
        "TaskOne", /* String with name of task. */
        4096,        /* Stack size in bytes. */
        NULL,        /* Parameter passed as input of the task */
        2,           /* Priority of the task.(configMAX_PRIORITIES - 1 being the highest, and 0 being the lowest.) */
        NULL);       /* Task handle. */
    xTaskCreate(
        xTaskTwo,  /* Task function. */
        "TaskTwo", /* String with name of task. */
        4096,      /* Stack size in bytes. */
        NULL,      /* Parameter passed as input of the task */
        1,         /* Priority of the task.(configMAX_PRIORITIES - 1 being the highest, and 0 being the lowest.) */
        NULL);     /* Task handle. */
    xTaskCreate(
        xTaskThree,  /* Task function. */
        "TaskThree", /* String with name of task. */
        4096,      /* Stack size in bytes. */
        NULL,      /* Parameter passed as input of the task */
        3,         /* Priority of the task.(configMAX_PRIORITIES - 1 being the highest, and 0 being the lowest.) */
        NULL);     /* Task handle. */
     xTaskCreate(
        xTaskFour,  /* Task function. */
        "TaskFour", /* String with name of task. */
        4096,      /* Stack size in bytes. */
        NULL,      /* Parameter passed as input of the task */
        4,         /* Priority of the task.(configMAX_PRIORITIES - 1 being the highest, and 0 being the lowest.) */
        NULL);     /* Task handle. */
}

void loop()
{

  // 检查MQTT连接状态，如果未连接则尝试重新连接
 if (!mqttClient.connected()) 
  {
    reconnectMQTT();
  }
             mqttClient.loop();   
}

/*
  //接收函数参
  struct Object {
    char id[10];
    char version[10];
    char command[32];
};
 
    char data[64];
   // 动态分配内存来存储payload
    char *json_str = (char *)malloc(length + 1);
    if (json_str == NULL) {
        printf("Memory allocation failed\n");
        return;
    }
    // 将payload内容复制到json_str中
    memcpy(json_str, payload, length);
    json_str[length] = '\0'; // 添加字符串结束符

    cJSON *root = cJSON_Parse(json_str);

    if (root == NULL) {
        printf("Error parsing JSON\n");
        free(json_str); // 释放动态分配的内存
        return;
    }
    cJSON *params = cJSON_GetObjectItem(root, "params");
    if (params == NULL) {
        printf("Error getting params object\n");
        cJSON_Delete(root);
        return;
    }
    cJSON *command = cJSON_GetObjectItem(params, "command");
    if (command == NULL) {
        printf("Error getting command value\n");
        cJSON_Delete(root);
        return;
    }

    if (command->type == cJSON_String) {
        printf("Test value: %s\n", command->valuestring);
    } else {
        printf("Test value is not a string\n");
    }
*/