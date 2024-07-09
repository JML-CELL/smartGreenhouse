<template>
	<view class="control">
		<!-- 图片展示 -->
		<view class="images">
			<image src="/static/images/controller.png" mode=""></image>
		</view>
		<!-- whether -->
		<view class="whether">
			<view class="dateTime">
				{{dateFormat(date)}}
			</view>
			<view class="Greenline"></view>
			<view class="whetherItem">
				<view class="item">
					<icon class="iconfont icon-dingxiang"></icon>
					{{city}}
				</view>
				<view class="item">
					<image :src="whetherPic" mode=""></image>
					{{weather}}
				</view>
				<view class="item">{{temperature}}°c</view>
			</view>
		</view>
		
		<!-- 切换 组件-->
		<view class="switch">
			<view class="left">
				<picker mode="selector" :range="switchList" @change="pickOne($event)">
					<view>{{switchList[switchIndex]}}</view>
				</picker>
				<icon class="iconfont icon-open"></icon>
			</view>
			<view class="add" @click="gotoAdd">
				<icon class="iconfont icon-zengjiatianjiajiahao"></icon>
			</view>
		</view>
		
		<!-- 数据渲染区(写两个，用v-if判定只显示一个)  要根据SwitchIndex渲染-->
		<!-- 植物生长环境 -->
		<view class="growEnv">
			<view class="growEnvItem" v-for="(item,index) in growEnvList" :key="switchIndex" v-if="switchIndex==0" @click="gotoGrowEnv">
				<view class="circle">
					<image :src="item.img" mode=""></image>
				</view>
				<view class="right_bottom">
					<view class="value">
						{{item.value}}{{item.unit}}
					</view>
					<view class="en">
						{{item.en}}
					</view>
				</view>
			</view>
		</view>
		
		<!-- 自动控制子系统 -->
		<view class="growEnv">
			<view class="growEnvItem" v-for="(item,index) in controlList" :key="switchIndex" v-if="switchIndex==1" @click="gotoControl">
				<view class="circle">
					<image :src="item.img" mode=""></image>
				</view>
				<view class="right_bottom_contorll">
					<view class="value">
						{{item.en}}
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	
	export default {
		data() {
			return {
				// 实时天气API调用参数
				key: 'b7632b180079b92eca9159ac73a8efa1',
				acode: '430408',
				// 显示在页面上的天气参数
				province: '',
				city: '',
				weather: '',
				humidity: '',
				temperature: '',
				winddirection: '',
				date: new Date().toISOString(), //现在时间
				whetherPic: "/static/icons/clear.png",
				switchList: ["植物生长环境","自动控制子系统"],
				switchIndex: 0,
				// 准备数据 -- 植物生长环境
				growEnvList: [{
					img: "/static/icons/AirTemperature.png",
					en: "Air temperature",
					value: "",
					unit: '°C'
				},{
					img: "/static/icons/AirHumidity.png",
					en: "Air humidity",
					value: "",
					unit: 'hPa'
				},{
					img: "/static/icons/SoilMoisture.png",
					en: "Soil moisture",
					value: "",
					unit: 'hPa'
				},{
					img: "/static/icons/illumination.png",
					en: "Illumination",
					value: "",
					unit: 'cd'
				},{
					img: "/static/icons/CarbonDioxideConcentration.png",
					en: "Carbon dioxide concentration",
					value: "",
					unit: 'pm'
				},{
					img: "/static/icons/WaterQualityPH.png",
					en: "Water pH",
					value: "",
					unit: 'ph'
				},{
					img: "/static/icons/waterLevel.png",
					en: "Water Level",
					value: "",
					unit: 'm'
				},{
					img: "/static/icons/atmosphericPressure.png",
					en: "Atmospheric Pressure",
					value: "",
					unit: 'kPa'
				}],
				// 准备数据 -- 自动控制子系统
				controlList: [{
					img: "/static/icons/LEDLight.png",
					en: "Grow fill lights"
				},{
					img: "/static/icons/airConditioning.png",
					en: "Air conditioning"
				},{
					img: "/static/icons/RollFan.png",
					en: "Roll fan"
				},{
					img: "/static/icons/Humidifier.png",
					en: "Humidifier"
				},{
					img: "/static/icons/MusicPlayer.png",
					en: "Music player"
				},{
					img: "/static/icons/SmokeAlarms.png",
					en: "Smoke alarms"
				},{
					img: "/static/icons/PesticideSprayers.png",
					en: "Pesticide sprayers"
				},{
					img: "/static/icons/DripIrrigationEquipment.png",
					en: "Drip irrigation equipment"
				},{
					img: "/static/icons/DripIrrigationEquipment.png",
					en: "Motorized curtains"
				}]
			}
		},
		methods: {
			// 时间格式转换
			dateFormat(time) {
				let date = new Date(time);
				let year = date.getFullYear();
				// 在日期格式中，月份是从0开始的，因此要加0，使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
				let month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
				let day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
				let hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
				let minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
				let seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
				// 拼接
				return year + "年" + month + "月" + day + "日 " + hours + ":" + minutes + ":" + seconds;
				// return year + "-" + month + "-" + day;
			},
			pickOne(e) {
				this.switchIndex = e.detail.value;
			},
			getDatas:function(){
				let that = this
				// 定时调用获取oneNet平台监控到的环境数据
				uni.request({
					url: "http://116.62.199.133:8081/oneNet/getOnenetLatestdata",
					method: 'get',
					success(res) {
						console.log(JSON.stringify(res.data.data))
						
						let result = JSON.stringify(res.data.data)
						
						// 空气湿度
						that.growEnvList[1].value = JSON.stringify(res.data.data[0].value).replace(/"/g, '')
						// 大气压
						that.growEnvList[7].value = JSON.stringify(res.data.data[2].value).replace(/"/g, '')
						// 二氧化碳浓度
						that.growEnvList[4].value = JSON.stringify(res.data.data[4].value).replace(/"/g, '')
						// 光照  7
						that.growEnvList[3].value = JSON.stringify(res.data.data[7].value).replace(/"/g, '')
						// smoke  9
						
						// 土壤湿度  10
						that.growEnvList[2].value = JSON.stringify(res.data.data[10].value).replace(/"/g, '')
						// 温度  12
						that.growEnvList[0].value = JSON.stringify(res.data.data[12].value).replace(/"/g, '')
						// 水位  14
						that.growEnvList[6].value = JSON.stringify(res.data.data[14].value).replace(/"/g, '')
						// 水质PH 16
						that.growEnvList[5].value = JSON.stringify(res.data.data[16].value).replace(/"/g, '')
						
					}
				})
			},
			gotoAdd:function(){
				if(this.switchIndex==0){
					uni.navigateTo({
						url: '/pages/thirdPages/addDevandSensor/addDevandSensor?name=传感器'
					})
				}else if(this.switchIndex==1){
					uni.navigateTo({
						url: '/pages/thirdPages/addDevandSensor/addDevandSensor?name=设备'
					})
				}
			},
			gotoGrowEnv:function(){
				uni.navigateTo({
					url: '/pages/indexPages/ThresholdSet/ThresholdSet'
				})
			},
			gotoControl:function(){
				uni.navigateTo({
					url: '/pages/indexPages/devices/devices'
				})
			}
		},
		onShow() {

			let that = this
			uni.request({
				url: 'https://restapi.amap.com/v3/weather/weatherInfo?key=' + this.key + '&city=' + this.acode +
					'',
				method: 'get',
				success(res) {
					console.log(res.data)
					that.province = res.data.lives[0].province
					that.city = res.data.lives[0].city
					that.weather = res.data.lives[0].weather
					that.humidity = res.data.lives[0].humidity
					that.temperature = res.data.lives[0].temperature
					that.winddirection = res.data.lives[0].winddirection
					
					// 文字和图片的切换
					if(that.weather.includes("晴")){
						that.whetherPic = "/static/icons/clear.png"
					}else if(that.weather.includes("多云")){
						that.whetherPic = "/static/icons/cloudy.png"
					}else if(that.weather.includes("阴")){
						that.whetherPic = "/static/icons/overcast.png"
					}else if(that.weather.includes("雨")){
						that.whetherPic = "/static/icons/lightRain.png"
					}
				}
			})

		},
		onLoad() {
			this.getDatas()
		},
		onPullDownRefresh(){
			
			this.getDatas()
			
			// 实现下拉刷新
			setTimeout(()=>{
				uni.stopPullDownRefresh()
			},500)
			
		},
	}
</script>

<style>
	
	
	page{
		width: 100%;
		height: 100vh;
		position: relative;
		
	}
	.control{
		
		padding-bottom: calc(var(--window-bottom) + 20px);
	}
	
	.images image {
		width: 100%;
	}

	.whether {
		font-weight: bold;
		background-color: white;
		padding: 3%;
		text-align: center;
		width: 60%;
		border-radius: 25rpx;
		position: absolute;
		top: 30%;
		left: 17%;
		box-shadow: rgba(17, 17, 26, 0.1) 0px 0px 16px;
	}
	.whether .dateTime{
		margin-bottom: 1%;
		color: #28491C;
		font-weight: bold;
	}
	.whether .whetherItem{
		display: flex;
		justify-content: space-between;
		align-items: center;
		margin-top: 7%;
	}
	.whetherItem .iconfont{
		color: #C3DEA9;
		font-weight: bold;
		margin-right: 5%;
	}
	.whetherItem .item{
		width: 35%;
		display: flex;
		align-items: center;
		justify-content: center;
	}
	.item image{
		width: 40rpx;
		height: 40rpx;
		margin-right: 5%;
	}
	.Greenline {
		border-bottom: 3px solid #28491C;
		border-top: 0px;
		border-left: 0px;
		border-right: 0px;
	}
	
	.switch{
		
		
	}
	.switch .left{
		background-color: white;
		position: absolute;
		top: 45%;
		left: 7%;
		width: 60%;
		display: flex;
		justify-content: space-between;
		align-items: center;
		padding: 3%;
		font-weight: bold;
		border-radius: 25rpx;
		box-shadow: rgba(17, 17, 26, 0.1) 0px 0px 16px;
	}
	
	.switch .add{
		position: absolute;
		top: 45%;
		right: 7%;
		background-color: white;
		padding: 3%;
		border-radius: 25rpx;
		box-shadow: rgba(17, 17, 26, 0.1) 0px 0px 16px;
	}
	.switch .add .iconfont{
		font-size: 24px;
	}
	
	.growEnv{
		position: absolute;
		top: 55%;
		left: 7%;
		display: flex;
		flex-wrap: wrap;
		justify-content: space-between;
		width: 86%;
	}
	
	.growEnvItem{
		width: 39%;
		height: 120px;
		background-color: white;
		margin-bottom: 5%;
		padding: 3%;
		border-radius: 25rpx;
		box-shadow: rgba(17, 17, 26, 0.1) 0px 0px 16px;
	}
	.growEnvItem image{
		width: 50rpx;
		height: 50rpx;
	}
	
	.growEnvItem .circle{
		background-color: white;
		width: 15%;
		text-align: center;
		padding: 2%;
		border-radius: 50%;
	}
	
	.growEnvItem .right_bottom{
		float: right;
		margin-top: 30%;
		text-align: right;
	}
	.right_bottom .en{
		font-size: 12px;
		font-style: italic;
	}
	.right_bottom .value{
		font-weight: bold;
	}
	
	.growEnvItem .right_bottom_contorll{
		float: right;
		margin-top: 40%;
		text-align: right;
		font-size: 14px;
		font-weight: bold;
		font-style: italic;
	}
</style>