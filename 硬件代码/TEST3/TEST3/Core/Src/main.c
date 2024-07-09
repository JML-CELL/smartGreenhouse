/* USER CODE BEGIN Header */
/**
  ******************************************************************************
  * @file           : main.c
  * @brief          : Main program body
  ******************************************************************************
  * @attention
  *
  * Copyright (c) 2024 STMicroelectronics.
  * All rights reserved.
  *
  * This software is licensed under terms that can be found in the LICENSE file
  * in the root directory of this software component.
  * If no LICENSE file comes with this software, it is provided AS-IS.
  *
  ******************************************************************************
  */
/* USER CODE END Header */
/* Includes ------------------------------------------------------------------*/
#include "main.h"
#include "adc.h"
#include "dma.h"
#include "i2c.h"
#include "tim.h"
#include "usart.h"
#include "gpio.h"

/* Private includes ----------------------------------------------------------*/
/* USER CODE BEGIN Includes */
/* USER CODE END Includes */

/* Private typedef -----------------------------------------------------------*/
/* USER CODE BEGIN PTD */

/* USER CODE END PTD */

/* Private define ------------------------------------------------------------*/
/* USER CODE BEGIN PD */
/* USER CODE END PD */

/* Private macro -------------------------------------------------------------*/
/* USER CODE BEGIN PM */

/* USER CODE END PM */

/* Private variables ---------------------------------------------------------*/

/* USER CODE BEGIN PV */

/* USER CODE END PV */

/* Private function prototypes -----------------------------------------------*/
void SystemClock_Config(void);
/* USER CODE BEGIN PFP */

//����
/*
     IN0:PA0 ����ʪ��    IN1:PA1 ��ǿ    IN2:PA2 ˮλ    IN3:PA3 ����  IN4:PA4 PH 
	 GPIO:PG2 ����       PG3 ˮ��        PG4 ũҩ        PG5 ������   
     UART4_TX: PC10      ���ֲ�����      UART4_RX: PC11  ������̼
	 USART1_TX:PA9       USART1_RX:PA10  ESP32
	 GPIO:PG6  ���1A    PG7  ���1B     PG8  ���1C     PC6  ���1D 
	 GPIO:PD10 ���2A    PD11 ���2B     PD12 ���2C     PD13 ���2D
*/

//7E 03 1C 1F EF ����/��ͣ
//7E 03 13 10 EF ��һ��
//7E 03 14 17 EF ��һ��

//����ADC��������
uint16_t ADC_Value[5];     //ADC1����
uint8_t  TX_DAT[9];        //������255
uint8_t  RX_DAT[10];       //������255
uint8_t  RX_DAT4[16];      //������255
uint8_t  TX_DAT4[5];       //������255

//�Զ����Ʋ���
uint8_t  soil_flag;        //�Զ���������ʪ�ȱ�־λ
uint8_t  light_flag;       //�Զ����ƹ���ǿ�ȱ�־λ
uint8_t  carbon_flag;      //�Զ����ƶ�����̼��־λ
uint8_t  smoke_flag;       //������������־λ
uint8_t  curtain_flag;     //������־λ
uint8_t  shuifei_flag;     //ˮ��һ�廯��־λ
uint16_t soil_top;
uint16_t soil_floor;
uint16_t light_top;
uint16_t light_floor;
uint16_t carbon_top;
uint16_t carbon_floor;

//����������
uint8_t smoke;             //�����־λ
int8_t  water_ph;          //ˮ��ph
int32_t water_level;       //ˮλ�߶�
uint8_t soil_humidity;     //����ʪ��
int32_t carbon_dioxide;    //������̼Ũ��
int32_t light_intensity;   //����ǿ��

//IIC����
//uint8_t IIC_OBUF[1]=0x80;
//uint8_t IIC_IBUF[4];
uint16_t IIC_BUF;

#define TSL2561_ADDR 0x39  // TSL2561������ַ 
 
#define TSL2561_CMD 0x80  // �����ֽ�
#define TSL2561_REG_TIMING 0x01  // TIMING�Ĵ�����ַ
#define TSL2561_REG_DATA_0 0x0C  // ���ݼĴ���0��ַ
#define TSL2561_REG_DATA_1 0x0E  // ���ݼĴ���1��ַ
 
#define TSL2561_GAIN_1X 0x00   // ����1x
#define TSL2561_GAIN_16X 0x10  // ����16x
 
#define TSL2561_INTEGRATION_TIME_13MS 0x00  // 13.7ms����ʱ��
#define TSL2561_INTEGRATION_TIME_101MS 0x01  // 101ms����ʱ��
#define TSL2561_INTEGRATION_TIME_402MS 0x02  // 402ms����ʱ��
 

void TSL2561_Init(void)
{
    uint8_t buf[4];
     
	// 
    buf[0] = TSL2561_CMD | 0x00;
    buf[1] = 0x03;
	
    HAL_I2C_Master_Transmit(&hi2c2, TSL2561_ADDR, buf, 2, 1000);
    // ��������Ϊ1x������ʱ��Ϊ13.7ms
    buf[0] = TSL2561_CMD | TSL2561_REG_TIMING;
    buf[1] = TSL2561_GAIN_1X | TSL2561_INTEGRATION_TIME_101MS;
	
    HAL_I2C_Master_Transmit(&hi2c2, TSL2561_ADDR, buf, 2, 1000);
}
 
uint16_t TSL2561_ReadData(void)
{
    uint8_t buf[2];
    uint16_t data;
 
    // ��ȡ���ݼĴ���0�����ݼĴ���1��ֵ
    buf[0] = TSL2561_CMD | TSL2561_REG_DATA_0;
    HAL_I2C_Master_Transmit(&hi2c2, TSL2561_ADDR, buf, 1, 1000);
    HAL_I2C_Master_Receive(&hi2c2, TSL2561_ADDR, buf, 2, 1000);
 
    // ����ȡ��������ת��Ϊ16λ����
    data = buf[1] << 8 | buf[0];
 
    return data;
}

//1�����ź����� A-B-C-D ��ת
void Motor_One(uint16_t speed)
{
	HAL_GPIO_WritePin(GPIOG,GPIO_PIN_6,GPIO_PIN_SET);
	HAL_GPIO_WritePin(GPIOG,GPIO_PIN_7|GPIO_PIN_8,GPIO_PIN_RESET);HAL_GPIO_WritePin(GPIOC,GPIO_PIN_6,GPIO_PIN_RESET);
	HAL_Delay(speed);
	
	HAL_GPIO_WritePin(GPIOG,GPIO_PIN_7,GPIO_PIN_SET);
	HAL_GPIO_WritePin(GPIOG,GPIO_PIN_6|GPIO_PIN_8,GPIO_PIN_RESET);HAL_GPIO_WritePin(GPIOC,GPIO_PIN_6,GPIO_PIN_RESET);
	HAL_Delay(speed);
	
	HAL_GPIO_WritePin(GPIOG,GPIO_PIN_8,GPIO_PIN_SET);
	HAL_GPIO_WritePin(GPIOG,GPIO_PIN_6|GPIO_PIN_7,GPIO_PIN_RESET);HAL_GPIO_WritePin(GPIOC,GPIO_PIN_6,GPIO_PIN_RESET);
	HAL_Delay(speed);
	
	HAL_GPIO_WritePin(GPIOC,GPIO_PIN_6,GPIO_PIN_SET);
	HAL_GPIO_WritePin(GPIOG,GPIO_PIN_7|GPIO_PIN_8|GPIO_PIN_9,GPIO_PIN_RESET);
	HAL_Delay(speed);
}

//1�����ź����� D-C-B-A ��ת
void Motor_uOne(uint16_t speed)
{
	HAL_GPIO_WritePin(GPIOC,GPIO_PIN_6,GPIO_PIN_SET);
	HAL_GPIO_WritePin(GPIOG,GPIO_PIN_7|GPIO_PIN_8|GPIO_PIN_9,GPIO_PIN_RESET);
	HAL_Delay(speed);
	
	HAL_GPIO_WritePin(GPIOG,GPIO_PIN_8,GPIO_PIN_SET);
	HAL_GPIO_WritePin(GPIOG,GPIO_PIN_6|GPIO_PIN_7,GPIO_PIN_RESET);HAL_GPIO_WritePin(GPIOC,GPIO_PIN_6,GPIO_PIN_RESET);
	HAL_Delay(speed);
	
	HAL_GPIO_WritePin(GPIOG,GPIO_PIN_7,GPIO_PIN_SET);
	HAL_GPIO_WritePin(GPIOG,GPIO_PIN_6|GPIO_PIN_8,GPIO_PIN_RESET);HAL_GPIO_WritePin(GPIOC,GPIO_PIN_6,GPIO_PIN_RESET);
	HAL_Delay(speed);

	HAL_GPIO_WritePin(GPIOG,GPIO_PIN_6,GPIO_PIN_SET);
	HAL_GPIO_WritePin(GPIOG,GPIO_PIN_7|GPIO_PIN_8,GPIO_PIN_RESET);HAL_GPIO_WritePin(GPIOC,GPIO_PIN_6,GPIO_PIN_RESET);
	HAL_Delay(speed);

}


//1�����ź���2��A-B-C-D ��ת
void Motor2_One(uint16_t speed)
{
	HAL_GPIO_WritePin(GPIOD,GPIO_PIN_10,GPIO_PIN_SET);
	HAL_GPIO_WritePin(GPIOD,GPIO_PIN_11|GPIO_PIN_12|GPIO_PIN_13,GPIO_PIN_RESET);
	HAL_Delay(speed);
	
	HAL_GPIO_WritePin(GPIOD,GPIO_PIN_11,GPIO_PIN_SET);
	HAL_GPIO_WritePin(GPIOD,GPIO_PIN_10|GPIO_PIN_12|GPIO_PIN_13,GPIO_PIN_RESET);
	HAL_Delay(speed);
	
	HAL_GPIO_WritePin(GPIOD,GPIO_PIN_12,GPIO_PIN_SET);
	HAL_GPIO_WritePin(GPIOD,GPIO_PIN_10|GPIO_PIN_11|GPIO_PIN_13,GPIO_PIN_RESET);
	HAL_Delay(speed);
	
	HAL_GPIO_WritePin(GPIOD,GPIO_PIN_13,GPIO_PIN_SET);
	HAL_GPIO_WritePin(GPIOD,GPIO_PIN_10|GPIO_PIN_11|GPIO_PIN_12,GPIO_PIN_RESET);
	HAL_Delay(speed);
}
//1�����ź���2��D-C-B-A ��ת
void Motor2_uOne(uint16_t speed)
{
	HAL_GPIO_WritePin(GPIOD,GPIO_PIN_13,GPIO_PIN_SET);
	HAL_GPIO_WritePin(GPIOD,GPIO_PIN_10|GPIO_PIN_11|GPIO_PIN_12,GPIO_PIN_RESET);
	HAL_Delay(speed);
	
	HAL_GPIO_WritePin(GPIOD,GPIO_PIN_12,GPIO_PIN_SET);
	HAL_GPIO_WritePin(GPIOD,GPIO_PIN_10|GPIO_PIN_11|GPIO_PIN_13,GPIO_PIN_RESET);
	HAL_Delay(speed);
	
	HAL_GPIO_WritePin(GPIOD,GPIO_PIN_11,GPIO_PIN_SET);
	HAL_GPIO_WritePin(GPIOD,GPIO_PIN_10|GPIO_PIN_12|GPIO_PIN_13,GPIO_PIN_RESET);
	HAL_Delay(speed);

	HAL_GPIO_WritePin(GPIOD,GPIO_PIN_10,GPIO_PIN_SET);
	HAL_GPIO_WritePin(GPIOD,GPIO_PIN_11|GPIO_PIN_12|GPIO_PIN_13,GPIO_PIN_RESET);
	HAL_Delay(speed);
}

//1�����źϣ�A1D2-B1C2-C1B2-D1A2 ��
void Motor_on(uint16_t speed)
{
	HAL_GPIO_WritePin(GPIOG,GPIO_PIN_6,GPIO_PIN_SET);
	HAL_GPIO_WritePin(GPIOG,GPIO_PIN_7|GPIO_PIN_8,GPIO_PIN_RESET);HAL_GPIO_WritePin(GPIOC,GPIO_PIN_6,GPIO_PIN_RESET);
	HAL_GPIO_WritePin(GPIOD,GPIO_PIN_13,GPIO_PIN_SET);
	HAL_GPIO_WritePin(GPIOD,GPIO_PIN_10|GPIO_PIN_11|GPIO_PIN_12,GPIO_PIN_RESET);
	HAL_Delay(speed);
	
	HAL_GPIO_WritePin(GPIOG,GPIO_PIN_7,GPIO_PIN_SET);
	HAL_GPIO_WritePin(GPIOG,GPIO_PIN_6|GPIO_PIN_8,GPIO_PIN_RESET);HAL_GPIO_WritePin(GPIOC,GPIO_PIN_6,GPIO_PIN_RESET);
	HAL_GPIO_WritePin(GPIOD,GPIO_PIN_12,GPIO_PIN_SET);
	HAL_GPIO_WritePin(GPIOD,GPIO_PIN_10|GPIO_PIN_11|GPIO_PIN_13,GPIO_PIN_RESET);
	HAL_Delay(speed);
	
	HAL_GPIO_WritePin(GPIOG,GPIO_PIN_8,GPIO_PIN_SET);
	HAL_GPIO_WritePin(GPIOG,GPIO_PIN_6|GPIO_PIN_7,GPIO_PIN_RESET);HAL_GPIO_WritePin(GPIOC,GPIO_PIN_6,GPIO_PIN_RESET);
    HAL_GPIO_WritePin(GPIOD,GPIO_PIN_11,GPIO_PIN_SET);
	HAL_GPIO_WritePin(GPIOD,GPIO_PIN_10|GPIO_PIN_12|GPIO_PIN_13,GPIO_PIN_RESET);
	HAL_Delay(speed);
	
	HAL_GPIO_WritePin(GPIOC,GPIO_PIN_6,GPIO_PIN_SET);
	HAL_GPIO_WritePin(GPIOG,GPIO_PIN_7|GPIO_PIN_8|GPIO_PIN_9,GPIO_PIN_RESET);
	HAL_GPIO_WritePin(GPIOD,GPIO_PIN_10,GPIO_PIN_SET);
	HAL_GPIO_WritePin(GPIOD,GPIO_PIN_11|GPIO_PIN_12|GPIO_PIN_13,GPIO_PIN_RESET);
	HAL_Delay(speed);
}
//1�����źϣ�D1A2-C1B2-B1C2-A1D2 ��
void Motor_off(uint16_t speed)
{
	
	HAL_GPIO_WritePin(GPIOC,GPIO_PIN_6,GPIO_PIN_SET);
	HAL_GPIO_WritePin(GPIOG,GPIO_PIN_7|GPIO_PIN_8|GPIO_PIN_9,GPIO_PIN_RESET);
		HAL_GPIO_WritePin(GPIOD,GPIO_PIN_10,GPIO_PIN_SET);
	HAL_GPIO_WritePin(GPIOD,GPIO_PIN_11|GPIO_PIN_12|GPIO_PIN_13,GPIO_PIN_RESET);
	HAL_Delay(speed);
	
	HAL_GPIO_WritePin(GPIOG,GPIO_PIN_8,GPIO_PIN_SET);
	HAL_GPIO_WritePin(GPIOG,GPIO_PIN_6|GPIO_PIN_7,GPIO_PIN_RESET);HAL_GPIO_WritePin(GPIOC,GPIO_PIN_6,GPIO_PIN_RESET);
	HAL_GPIO_WritePin(GPIOD,GPIO_PIN_11,GPIO_PIN_SET);
	HAL_GPIO_WritePin(GPIOD,GPIO_PIN_10|GPIO_PIN_12|GPIO_PIN_13,GPIO_PIN_RESET);
	HAL_Delay(speed);
	
	HAL_GPIO_WritePin(GPIOG,GPIO_PIN_7,GPIO_PIN_SET);
	HAL_GPIO_WritePin(GPIOG,GPIO_PIN_6|GPIO_PIN_8,GPIO_PIN_RESET);HAL_GPIO_WritePin(GPIOC,GPIO_PIN_6,GPIO_PIN_RESET);
	HAL_GPIO_WritePin(GPIOD,GPIO_PIN_12,GPIO_PIN_SET);
	HAL_GPIO_WritePin(GPIOD,GPIO_PIN_10|GPIO_PIN_11|GPIO_PIN_13,GPIO_PIN_RESET);
	HAL_Delay(speed);
	
	HAL_GPIO_WritePin(GPIOG,GPIO_PIN_6,GPIO_PIN_SET);
	HAL_GPIO_WritePin(GPIOG,GPIO_PIN_7|GPIO_PIN_8,GPIO_PIN_RESET);HAL_GPIO_WritePin(GPIOC,GPIO_PIN_6,GPIO_PIN_RESET);
	HAL_GPIO_WritePin(GPIOD,GPIO_PIN_13,GPIO_PIN_SET);
	HAL_GPIO_WritePin(GPIOD,GPIO_PIN_10|GPIO_PIN_11|GPIO_PIN_12,GPIO_PIN_RESET);
	HAL_Delay(speed);
}
		

//ADC�����˲����� times��ͨ��mȡƽ��
uint16_t GET_ADC_Value1(uint8_t m,uint16_t times)
{
	uint16_t temp_val=0;
	uint16_t t;
	for(t=0;t<times;t++)
	{
		temp_val+=ADC_Value[m];
	}
	return temp_val/times;
} 	 


//WS2812����
#define ws2812_one 143
#define ws2812_zero 67
#define ws2812_reset 9000
#define ws2812_len 24
#define ws2812_num 10
#define ws2812_data_len (ws2812_num*ws2812_len)

uint16_t RGB_buffer[ws2812_data_len+1] = {0}; 
uint8_t RGB[3];            //������255

//1. �ṹ�����Ͷ���
typedef struct 
{
    float LastP;//�ϴι���Э���� ��ʼ��ֵΪ0.02
    float Now_P;//��ǰ����Э���� ��ʼ��ֵΪ0
    float out;//�������˲������ ��ʼ��ֵΪ0
    float Kg;//���������� ��ʼ��ֵΪ0
    float Q;//��������Э���� ��ʼ��ֵΪ0.001
    float R;//�۲�����Э���� ��ʼ��ֵΪ0.543
}KFP;//Kalman Filter parameter

//2. �Ը߶�Ϊ�� ���忨�����ṹ�岢��ʼ������
KFP KFP_height={0.02,0,0,0,0.001,0.543};

/**
 *�������˲���
 *@param KFP *kfp �������ṹ�����
 *   float input ��Ҫ�˲��Ĳ����Ĳ���ֵ�����������Ĳɼ�ֵ��
 *@return �˲���Ĳ���������ֵ��
 */
 float kalmanFilter(KFP *kfp,float input)
 {
     //Ԥ��Э����̣�kʱ��ϵͳ����Э���� = k-1ʱ�̵�ϵͳЭ���� + ��������Э����
     kfp->Now_P = kfp->LastP + kfp->Q;
     //���������淽�̣����������� = kʱ��ϵͳ����Э���� / ��kʱ��ϵͳ����Э���� + �۲�����Э���
     kfp->Kg = kfp->Now_P / (kfp->Now_P + kfp->R);
     //��������ֵ���̣�kʱ��״̬����������ֵ = ״̬������Ԥ��ֵ + ���������� * ������ֵ - ״̬������Ԥ��ֵ��
     kfp->out = kfp->out + kfp->Kg * (input -kfp->out);//��Ϊ��һ�ε�Ԥ��ֵ������һ�ε����ֵ
     //����Э�����: ���ε�ϵͳЭ����� kfp->LastP ����һ������׼����
     kfp->LastP = (1-kfp->Kg) * kfp->Now_P;
     return kfp->out;
 }





//ws2812���� ����
void my_ws2812(uint8_t R,uint8_t G,uint8_t B)
{
	for(uint16_t j = 0; j < ws2812_num; j++)
	{
		uint8_t zy=0;
	     for (uint16_t i = j * 24; i < (j*24+8); i++)
       {
         RGB_buffer[i]      = (G << zy) & (0x80) ? ws2812_one : ws2812_zero;
         RGB_buffer[i + 8]  = (R << zy) & (0x80) ? ws2812_one : ws2812_zero;
         RGB_buffer[i + 16] = (B << zy) & (0x80) ? ws2812_one : ws2812_zero;
		 zy++;
       }
    } 
	RGB_buffer[ws2812_data_len]=0;
	HAL_TIM_PWM_Start_DMA(&htim8, TIM_CHANNEL_4,(uint32_t*)RGB_buffer,ws2812_data_len+1);
}

//��ʱ���жϷ�����
void HAL_TIM_PeriodElapsedCallback(TIM_HandleTypeDef *htim) 
{
	if(htim->Instance == TIM2)               //��ʱ��2�жϷ��ʹ���1���� Ƶ��1Hz 
	{   
		water_ph = (float)(GET_ADC_Value1(4,3)-2369)/(-167.1)*10;       //��ȡˮ��ph
		//light_intensity = GET_ADC_Value1(1,20);                          //��ȡ����ǿ��
		light_intensity = kalmanFilter(&KFP_height,(float) GET_ADC_Value1(1,3));
		water_level = GET_ADC_Value1(2,3)/10;                           //��ȡˮλ
		soil_humidity = (100 - (GET_ADC_Value1(0,3)*100 / 4095 ));      //��ȡ����ʪ��
		
		TX_DAT[0] = soil_humidity;   
	    TX_DAT[1] = light_intensity / 100;
		TX_DAT[2] = light_intensity % 100;
	    TX_DAT[3] = water_ph;
	    TX_DAT[4] = carbon_dioxide /100;
	    TX_DAT[5] = carbon_dioxide %100;
	    TX_DAT[6] = water_level /100;
	    TX_DAT[7] = water_level %100;
	    TX_DAT[8] = smoke;
		
		HAL_UART_Transmit_IT(&huart1,TX_DAT,9); 
		 
	}
	
		if(htim->Instance == TIM3)               //��ʱ��3�ж� Ƶ��5Hz 
	{   
           if(soil_flag)                         //��������ʪ�ȿ���
        {
	          if(soil_humidity>soil_top-3)	
		    {
			    HAL_GPIO_WritePin(GPIOG,GPIO_PIN_4,GPIO_PIN_RESET);
	        }				  
		      if(soil_humidity<soil_floor)
	        {
			    HAL_GPIO_WritePin(GPIOG,GPIO_PIN_4,GPIO_PIN_SET);
	        }
	    }
		   if(light_flag)                        //������ǿ���� 
        {
			/*  if(light_intensity>4000)            //���ϴ���
			{
				curtain_flag = 2;                           
			}*/
			  if(light_intensity>light_top-20)
    	    {
				if(RGB[0]>0)RGB[0]-=1;
				if(RGB[1]>0)RGB[1]-=1;
				if(RGB[2]>0)RGB[2]-=1;
				my_ws2812(RGB[0],RGB[1],RGB[2]);
		    }
			  if(light_intensity<light_floor+20)
		    {
				if(RGB[0]<255)RGB[0]+=1;
				if(RGB[1]<255)RGB[1]+=1;
				if(RGB[2]<255)RGB[2]+=1;
				my_ws2812(RGB[0],RGB[1],RGB[2]);
		    }
	    }
		   if(carbon_flag)                       //����������̼����
        {
			  if(carbon_dioxide>carbon_top)
		    {
				HAL_GPIO_WritePin(GPIOG,GPIO_PIN_7,GPIO_PIN_RESET);
			}
			  if(carbon_dioxide<carbon_floor)
			{
				HAL_GPIO_WritePin(GPIOG,GPIO_PIN_7,GPIO_PIN_SET);
			}
		}
		   if(smoke_flag)                        //����������
		{
			if(GET_ADC_Value1(3,3)>1200)
			{
				HAL_GPIO_WritePin(GPIOG,GPIO_PIN_5,GPIO_PIN_SET);
				smoke=1;
			}
		}
		else{smoke=0,HAL_GPIO_WritePin(GPIOG,GPIO_PIN_5,GPIO_PIN_RESET);}
	}
}


//���ڽ��ջص�����
void HAL_UART_RxCpltCallback(UART_HandleTypeDef *huart)   
{
	if(huart->Instance==USART1)                   //����1���ղ�ִ������
	{
		  switch(RX_DAT[0]-0x30)
		  {
			  //1-6��
			  case 1:
				     switch(RX_DAT[1]-0x30)
					 {
						 case 1:
							     HAL_GPIO_WritePin(GPIOG,GPIO_PIN_2,GPIO_PIN_SET);      //fan
						 break;
						 
						 case 2:
							     HAL_GPIO_WritePin(GPIOG,GPIO_PIN_3,GPIO_PIN_SET);      //water
						 break;
						 
						 case 3:
							     HAL_GPIO_WritePin(GPIOG,GPIO_PIN_4,GPIO_PIN_SET);      //pesticide
						 break;
						 
						 case 4:
							     //7E 03 1C 1F EF
						         TX_DAT4[0]=0x7E;TX_DAT4[1]=0x03;TX_DAT4[2]=0x1C;TX_DAT4[3]=0x1F;TX_DAT4[4]=0xEF;
							     HAL_UART_Transmit_IT(&huart4,TX_DAT4,5);               //music
						 break;
						 
						 case 5:
							     smoke_flag=1;                                          //smoke
						 break;
						 
						 case 6:
							     curtain_flag=1;                                        //curtain
						 break;
					 }
			  break;
					 
             //1-6��
			 case 2:
				     switch(RX_DAT[1]-0x30)
					 {
						 case 1:
							     HAL_GPIO_WritePin(GPIOG,GPIO_PIN_2,GPIO_PIN_RESET);    //fan
						 break;
						 
						 case 2:
							     HAL_GPIO_WritePin(GPIOG,GPIO_PIN_3,GPIO_PIN_RESET);    //water
						 break;
						 
						 case 3:
							     HAL_GPIO_WritePin(GPIOG,GPIO_PIN_4,GPIO_PIN_RESET);    //pesticide
						 break;
						 
						 case 4:
							     //7E 03 1C 1F EF
						         TX_DAT4[0]=0x7E;TX_DAT4[1]=0x03;TX_DAT4[2]=0x1C;TX_DAT4[3]=0x1F;TX_DAT4[4]=0xEF;
							     HAL_UART_Transmit_IT(&huart4,TX_DAT4,5);               //music
						 break;
						 
						 case 5:
							     smoke_flag=0;                                          //smoke
						 break;
						 
						 case 6:
							     curtain_flag=2;                                        //curtain
						 break;
						 
					 }
			  break;
			  
			  //����RGB��
			  case 3:
				     RGB[0] = (RX_DAT[1] - 0x30) * 100; RGB[0] += (RX_DAT[2] - 0x30) * 10;RGB[0] += RX_DAT[3]-0x30;
			         RGB[1] = (RX_DAT[4] - 0x30) * 100; RGB[1] += (RX_DAT[5] - 0x30) * 10;RGB[1] += RX_DAT[6]-0x30;
			         RGB[2] = (RX_DAT[7] - 0x30) * 100; RGB[2] += (RX_DAT[8] - 0x30) * 10;RGB[2] += RX_DAT[9]-0x30;
			         my_ws2812(RGB[0],RGB[1],RGB[2]);
			  break;
			  
			  //�Զ����ƹ���
			  case 4:
				    switch(RX_DAT[1]-0x30)
					 {
						 case 1:
							     soil_top   = (RX_DAT[2] - 0x30) * 10+(RX_DAT[3]-0x30);
					         	 soil_floor = (RX_DAT[4] - 0x30) * 10+(RX_DAT[5]-0x30);
						         soil_flag  = 1;
						 break;
						 
						 case 2:
							     light_top    = (RX_DAT[2]-0x30)*1000 + (RX_DAT[3]-0x30)*100 + (RX_DAT[4]-0x30)*10 + (RX_DAT[5]-0x30);
					         	 light_floor  = (RX_DAT[6]-0x30)*1000 + (RX_DAT[7]-0x30)*100 + (RX_DAT[8]-0x30)*10 + (RX_DAT[9]-0x30);
						         light_flag   = 1;
						 break;
						 
						 case 3:
							     carbon_top   = (RX_DAT[2]-0x30)*1000 + (RX_DAT[3]-0x30)*100 + (RX_DAT[4]-0x30)*10 + (RX_DAT[5]-0x30);
					         	 carbon_floor = (RX_DAT[6]-0x30)*1000 + (RX_DAT[7]-0x30)*100 + (RX_DAT[8]-0x30)*10 + (RX_DAT[9]-0x30);
						         carbon_flag  = 1; 
						 break;
					 }
			  break;
			  
			  //������һ��
			  case 5:
				    switch(RX_DAT[1]-0x30)
					 {
						 case 1:
                                 //7E 03 13 10 EF
						         TX_DAT4[0]=0x7E;TX_DAT4[1]=0x03;TX_DAT4[2]=0x13;TX_DAT4[3]=0x10;TX_DAT4[4]=0xEF;
							     HAL_UART_Transmit_IT(&huart4,TX_DAT4,5);               //music
						 break;
					 }
			  break;
			  //ˮ��һ�廯
			  case 6:
				    switch(RX_DAT[1]-0x30)
					 {
						 case 6:
                                 
						        shuifei_flag=1;
						 break;
					 }
			  break;
		  }
		  HAL_UART_Receive_IT(&huart1,RX_DAT,10);
	}
	
	if(huart->Instance==UART4)                           //����2���ն�����̼����
	{
		 carbon_dioxide=RX_DAT4[6]*256+RX_DAT4[7];
		
	     HAL_UART_Receive_IT(&huart4,RX_DAT4,16);
	}
}



// PWM DMA ��ɻص�����
void HAL_TIM_PWM_PulseFinishedCallback(TIM_HandleTypeDef *htim)
{
	HAL_TIM_PWM_Stop_DMA(&htim8, TIM_CHANNEL_4);
}


/* USER CODE END PFP */

/* Private user code ---------------------------------------------------------*/
/* USER CODE BEGIN 0 */

/* USER CODE END 0 */

/**
  * @brief  The application entry point.
  * @retval int
  */
int main(void)
{
  /* USER CODE BEGIN 1 */

  /* USER CODE END 1 */

  /* MCU Configuration--------------------------------------------------------*/

  /* Reset of all peripherals, Initializes the Flash interface and the Systick. */
  HAL_Init();

  /* USER CODE BEGIN Init */

  /* USER CODE END Init */

  /* Configure the system clock */
  SystemClock_Config();

  /* USER CODE BEGIN SysInit */

  /* USER CODE END SysInit */

  /* Initialize all configured peripherals */
  
  MX_GPIO_Init();
  MX_DMA_Init();
  MX_ADC1_Init();
  MX_TIM2_Init();
  MX_USART1_UART_Init();
  MX_I2C2_Init();
  MX_TIM8_Init();
  MX_UART4_Init();
  MX_TIM3_Init();
  
  /* USER CODE BEGIN 2 */
  
 // TSL2561_Init();
  HAL_TIM_Base_Start_IT(&htim2);                      //��ʱ��2�ж� 1HZ
  HAL_TIM_Base_Start_IT(&htim3);                      //��ʱ��3�ж� 10HZ
  HAL_UART_Receive_IT(&huart1,RX_DAT,10);             //����1����10���ֽ�����  
  HAL_UART_Receive_IT(&huart4,RX_DAT4,16);            //����4����16���ֽ�����
  HAL_ADC_Start_DMA(&hadc1,(uint32_t*)ADC_Value,5);   //ADC1 DMA 5ͨ�� 
  //HAL_I2C_Mem_Write(&hi2c2,0x39,0x00,8,&IIC_OBUF[1],1,0xffff);
  
  /* USER CODE END 2 */

  /* Infinite loop */
  /* USER CODE BEGIN WHILE */
  while (1)
  {
    /* USER CODE END WHILE */

    /* USER CODE BEGIN 3 */
	 if(curtain_flag==1)        //��
	 {
		 int j=9000;
		 int i;
		 for(i=0;i<j;i++)
		 {
	      	 Motor_on(1);
		 }
		 curtain_flag=0;
	 }
	 if(curtain_flag==2)        //��
	 {
		 int j=9000;
		 int i;
		 for(i=0;i<j;i++)
		 {
			 Motor_off(1);
		 }
		 curtain_flag=0;
	 }
	if(curtain_flag==0)
	{
       HAL_Delay(500);
	}
    if(shuifei_flag==1)
	{
		HAL_GPIO_WritePin(GPIOG,GPIO_PIN_2,GPIO_PIN_SET);   //SHUI
		HAL_GPIO_WritePin(GPIOG,GPIO_PIN_3,GPIO_PIN_SET);   //FEI
		HAL_Delay(2000);
		HAL_GPIO_WritePin(GPIOG,GPIO_PIN_3,GPIO_PIN_RESET); //FEI
		HAL_Delay(2000);HAL_Delay(2000);
		HAL_GPIO_WritePin(GPIOG,GPIO_PIN_2,GPIO_PIN_RESET); //SHUI
		HAL_GPIO_WritePin(GPIOG,GPIO_PIN_4,GPIO_PIN_SET);   //HUN
		HAL_Delay(2000);HAL_Delay(2000);
		HAL_Delay(2000);HAL_Delay(2000);
		HAL_Delay(2000);
		HAL_GPIO_WritePin(GPIOG,GPIO_PIN_4,GPIO_PIN_RESET);   //HUN
		shuifei_flag=0;
	}
   // HAL_I2C_Mem_Read (&hi2c2,0x39,0x0C,8,&IIC_IBUF[0],1,0xffff);
   // HAL_I2C_Mem_Read (&hi2c2,0x39,0x0D,8,&IIC_IBUF[1],1,0xffff);
    //HAL_I2C_Mem_Read (&hi2c2,0x39,0x0E,8,&IIC_IBUF[2],1,0xffff);
   // HAL_I2C_Mem_Read (&hi2c2,0x39,0x0F,8,&IIC_IBUF[3],1,0xffff);
	
	
  }
    
  /* USER CODE END 3 */
}

/**
  * @brief System Clock Configuration
  * @retval None
  */
void SystemClock_Config(void)
{
  RCC_OscInitTypeDef RCC_OscInitStruct = {0};
  RCC_ClkInitTypeDef RCC_ClkInitStruct = {0};

  /** Configure the main internal regulator output voltage
  */
  __HAL_RCC_PWR_CLK_ENABLE();
  __HAL_PWR_VOLTAGESCALING_CONFIG(PWR_REGULATOR_VOLTAGE_SCALE1);
  /** Initializes the RCC Oscillators according to the specified parameters
  * in the RCC_OscInitTypeDef structure.
  */
  RCC_OscInitStruct.OscillatorType = RCC_OSCILLATORTYPE_HSE;
  RCC_OscInitStruct.HSEState = RCC_HSE_ON;
  RCC_OscInitStruct.PLL.PLLState = RCC_PLL_ON;
  RCC_OscInitStruct.PLL.PLLSource = RCC_PLLSOURCE_HSE;
  RCC_OscInitStruct.PLL.PLLM = 4;
  RCC_OscInitStruct.PLL.PLLN = 168;
  RCC_OscInitStruct.PLL.PLLP = RCC_PLLP_DIV2;
  RCC_OscInitStruct.PLL.PLLQ = 4;
  if (HAL_RCC_OscConfig(&RCC_OscInitStruct) != HAL_OK)
  {
    Error_Handler();
  }
  /** Initializes the CPU, AHB and APB buses clocks
  */
  RCC_ClkInitStruct.ClockType = RCC_CLOCKTYPE_HCLK|RCC_CLOCKTYPE_SYSCLK
                              |RCC_CLOCKTYPE_PCLK1|RCC_CLOCKTYPE_PCLK2;
  RCC_ClkInitStruct.SYSCLKSource = RCC_SYSCLKSOURCE_PLLCLK;
  RCC_ClkInitStruct.AHBCLKDivider = RCC_SYSCLK_DIV1;
  RCC_ClkInitStruct.APB1CLKDivider = RCC_HCLK_DIV4;
  RCC_ClkInitStruct.APB2CLKDivider = RCC_HCLK_DIV2;

  if (HAL_RCC_ClockConfig(&RCC_ClkInitStruct, FLASH_LATENCY_5) != HAL_OK)
  {
    Error_Handler();
  }
}

/* USER CODE BEGIN 4 */

/* USER CODE END 4 */

/**
  * @brief  This function is executed in case of error occurrence.
  * @retval None
  */
void Error_Handler(void)
{
  /* USER CODE BEGIN Error_Handler_Debug */
  /* User can add his own implementation to report the HAL error return state */
  __disable_irq();
  while (1)
  {
  }
  /* USER CODE END Error_Handler_Debug */
}

#ifdef  USE_FULL_ASSERT
/**
  * @brief  Reports the name of the source file and the source line number
  *         where the assert_param error has occurred.
  * @param  file: pointer to the source file name
  * @param  line: assert_param error line source number
  * @retval None
  */
void assert_failed(uint8_t *file, uint32_t line)
{
  /* USER CODE BEGIN 6 */
  /* User can add his own implementation to report the file name and line number,
     ex: printf("Wrong parameters value: file %s on line %d\r\n", file, line) */
  /* USER CODE END 6 */
}
#endif /* USE_FULL_ASSERT */

