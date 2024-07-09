<template>
	<view class="threshold">

		<view class="greenhouseItem">

			<!--标题-->
			<view class="title" @click="showDrawer">
				{{currentGreen}}
			</view>

			<uni-drawer ref="showLeft" mode="left" class="drawer">
				<view class="titleHead">
					环境数据
				</view>
				<!--空气温度-->
				<view class="leftItem">
					<view class="parameter">
						空气温度
					</view>
					<view class="highest">
						<text>最高：{{proData[0].historyHigh}} °C</text>
					</view>
					<view class="lowest">
						<text>最低：{{proData[0].historyLow}} °C</text>
					</view>
				</view>
				<!--空气湿度-->
				<view class="leftItem">
					<view class="parameter">
						空气湿度
					</view>
					<view class="highest">
						<text>最高：{{proData[1].historyHigh}} hPa</text>
					</view>
					<view class="lowest">
						<text>最低：{{proData[1].historyLow}} hPa</text>
					</view>
				</view>
				<!--土壤湿度-->
				<view class="leftItem">
					<view class="parameter">
						土壤湿度
					</view>
					<view class="highest">
						<text>最高：{{proData[2].historyHigh}} hPa</text>
					</view>
					<view class="lowest">
						<text>最低：{{proData[2].historyLow}} hPa</text>
					</view>
				</view>
				<!--光照-->
				<view class="leftItem">
					<view class="parameter">
						光照
					</view>
					<view class="highest">
						<text>最高：{{proData[3].historyHigh}} cd</text>
					</view>
					<view class="lowest">
						<text>最低：{{proData[3].historyLow}} cd</text>
					</view>
				</view>
				<!--二氧化碳-->
				<view class="leftItem">
					<view class="parameter">
						二氧化碳
					</view>
					<view class="highest">
						<text>最高：{{proData[4].historyHigh}} pm</text>
					</view>
					<view class="lowest">
						<text>最低：{{proData[4].historyLow}} pm</text>
					</view>
				</view>
				<!-- <button @click="closeDrawer">关闭Drawer</button> -->
			</uni-drawer>

			<!--part_one-->
			<view class="cardShow">
				<view class="Item" v-for="(item,index) in sensors">
					<!-- <icon :class="item.icon" style="font-size: 36px; color: #018577; margin-right: 9%;"></icon> -->
					<image :src="item.icon" mode=""></image>
					<view style="font-size: 18px;width:35%;">{{item.text}}</view>
					<view class="parameterNumber" style="width: 25%;">{{item.parameter}}{{item.unit}}</view>
					<icon class="iconfont icon-xiugai" style="font-size: 28px;color: darkgrey;color: #000; "
						@click="toggle('bottom',index)" v-if="index < 5"></icon>
				</view>

				<!--添加按钮-->
				<view class="addSensor" @click="addSensor">
					<button>添加传感器</button>
				</view>

			</view>

			<!--弹窗实现部分-->
			<uni-popup ref="popup" background-color="#fff" @change="change">
				<view class="context">
					<!--标题-->
					<view class="title">
						{{proData[currentIndex].title}}
						<view class="Greenline"></view>
					</view>
					<!--contextItem-->
					<view class="contextItem">
						<text class="name">最高:</text>
						<text class="value">{{proData[currentIndex].historyHigh}}{{proData[currentIndex].unit}}</text>
						<text class="name">修改为:</text>
						<input class="input" @input="highParameterChange" placeholder="请输入修改值"
							v-model="newData[currentIndex].high" />{{proData[currentIndex].unit}}
					</view>
					<view class="contextItem" v-if="currentIndex!=0">
						<text class="name">最低:</text>
						<text class="value">{{proData[currentIndex].historyLow}}{{proData[currentIndex].unit}}</text>
						<text class="name">修改为:</text>
						<input class="input" @input="lowParameterChange" placeholder="请输入修改值"
							v-model="newData[currentIndex].low" />{{proData[currentIndex].unit}}
					</view>
					<button class="button" @click="closePopup('bottom')">确定</button>
				</view>
			</uni-popup>

		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				timer: null, //定时器
				// 历史数据保存更新显示--每类一种
				proData: [{
					historyHigh: 10,
					historyLow: 0,
					unit: '',
					title: ''
				}, {
					historyHigh: 10,
					historyLow: 0,
					unit: '',
					title: ''
				}, {
					historyHigh: 10,
					historyLow: 0,
					unit: '',
					title: ''
				}, {
					historyHigh: 10,
					historyLow: 0,
					unit: '',
					title: ''
				}, {
					historyHigh: 10,
					historyLow: 0,
					unit: '',
					title: ''
				}],
				// 修改数据
				newData: [{
						high: 0,
						low: 0,
					},
					{
						high: 0,
						low: 0,
					}, {
						high: 0,
						low: 0,
					},
					{
						high: 0,
						low: 0,
					},
					{
						high: 0,
						low: 0,
					}
				],

				currentIndex: 0,
				sensors: [{
						// icon: "iconfont icon-guanyuwomen",
						icon: "/static/icons/AirTemperature.png",
						text: "空气温度",
						parameter: 20,
						unit: '°C'
					},
					{
						icon: "/static/icons/AirHumidity.png",
						text: "空气湿度",
						parameter: 20,
						unit: 'hPa'

					},
					{
						icon: "/static/icons/SoilMoisture.png",
						text: "土壤湿度",
						parameter: 20,
						unit: 'hPa'
					},
					{
						icon: "/static/icons/illumination.png",
						text: "光照",
						parameter: 20,
						unit: 'cd'
					},
					{
						icon: "/static/icons/CarbonDioxideConcentration.png",
						text: "二氧化碳",
						parameter: 20,
						unit: 'pm'
					},
					{
						icon: "/static/icons/WaterQualityPH.png",
						text: "水质PH",
						parameter: 20,
						unit: 'ph'
					},
					{
						icon: "/static/icons/waterLevel.png",
						text: "水位",
						parameter: 20,
						unit: 'mm'
					},
					{
						icon: "/static/icons/atmosphericPressure.png",
						text: "大气压",
						parameter: 20,
						unit: 'kPa'
					}
				],
				currentGreen: "番茄大棚",
			};
		},
		methods: {
			change: function(e) {
				console.log('当前模式：' + e.type + ',状态：' + e.show);
			},
			toggle: function(type, index) {
				this.type = type;
				// open 方法传入参数 等同在 uni-popup 组件上绑定 type属性
				this.$refs.popup.open(type);

				this.currentIndex = index

				if (index == 0) {
					this.proData[this.currentIndex].historyHigh = this.newData[this.currentIndex].high
					this.proData[index].historyLow = this.newData[index].low
					// 

					this.proData[index].unit = '°C'
					this.proData[index].title = '修改空气温度参数值'
					// 在再一次toggle时将新数值还原成0
					this.newData[index].high = 0
					this.newData[index].low = 0
				} else if (index == 1) {
					this.proData[index].historyHigh = this.newData[index].high
					this.proData[index].historyLow = this.newData[index].low
					this.proData[index].unit = 'hPa'
					this.proData[index].title = '修改空气湿度参数值'
					// 在再一次toggle时将新数值还原成0
					this.newData[index].high = 0
					this.newData[index].low = 0
				} else if (index == 2) {
					this.proData[index].historyHigh = this.newData[index].high
					this.proData[index].historyLow = this.newData[index].low
					this.proData[index].unit = 'hPa'
					this.proData[index].title = '修改土壤湿度参数值'
					// 在再一次toggle时将新数值还原成0
					this.newData[index].high = 0
					this.newData[index].low = 0
				} else if (index == 3) {
					this.proData[index].historyHigh = this.newData[index].high
					this.proData[index].historyLow = this.newData[index].low
					this.proData[index].unit = 'cd'
					this.proData[index].title = '修改光敏参数值'
					// 在再一次toggle时将新数值还原成0
					this.newData[index].high = 0
					this.newData[index].low = 0
				} else if (index == 4) {
					this.proData[index].historyHigh = this.newData[index].high
					this.proData[index].historyLow = this.newData[index].low
					this.proData[index].unit = 'pm'
					this.proData[index].title = '修改二氧化碳参数值'
					// 在再一次toggle时将新数值还原成0
					this.newData[index].high = 0
					this.newData[index].low = 0
				} else if (index == 5) {
					this.proData[index].historyHigh = this.newData[index].high
					this.proData[index].historyLow = this.newData[index].low
					this.proData[index].unit = 'kPa'
					this.proData[index].title = '修改大气压参数值'
					// 在再一次toggle时将新数值还原成0
					this.newData[index].high = 0
					this.newData[index].low = 0
				} else if (index == 6) {
					this.proData[index].historyHigh = this.newData[index].high
					this.proData[index].historyLow = this.newData[index].low
					this.proData[index].unit = 'ph'
					this.proData[index].title = '修改水质PH参数值'
					// 在再一次toggle时将新数值还原成0
					this.newData[index].high = 0
					this.newData[index].low = 0
				} else if (index == 7) {
					this.proData[index].historyHigh = this.newData[index].high
					this.proData[index].historyLow = this.newData[index].low
					this.proData[index].unit = 'mm'
					this.proData[index].title = '修改水位参数值'
					// 在再一次toggle时将新数值还原成0
					this.newData[index].high = 0
					this.newData[index].low = 0
				}
			},
			closePopup: function(type) {


				this.type = type
				this.$refs.popup.close(type)

				uni.showToast({
					title: '修改成功',
					icon: 'success'
				})


				let that = this
				// 分支判断是哪个阈值就调用哪个接口
				if (that.currentIndex == 0) {

					// let temperatureS = Math.round((that.newData[this.currentIndex].high + that.newData[this.currentIndex].low) / 2)
					// console.log(temperatureS)

					console.log(that.newData[that.currentIndex].high)

					// uni.setStorage({
					// 	key: ''
					// })

					//空气温度
					uni.request({
						url: 'http://116.62.199.133:8081/auto/temperature',
						method: 'get',
						data: {
							temperature: that.newData[that.currentIndex].high
						},
						success(res) {
							console.log(res.data)
						}
					})
				} else if (that.currentIndex == 1) {
					//空气湿度
					uni.request({
						url: 'http://116.62.199.133:8081/auto/airHumidity',
						method: 'get',
						data: {
							floor: that.newData[that.currentIndex].low,
							top: that.newData[that.currentIndex].high
						},
						success(res) {
							console.log(res.data)
						}
					})
				} else if (that.currentIndex == 2) {
					// 土壤湿度  同空气湿度  /auto/soilhumidity
					uni.request({
						url: 'http://116.62.199.133:8081/auto/soilhumidity',
						method: 'get',
						data: {
							floor: that.newData[that.currentIndex].low,
							top: that.newData[that.currentIndex].high
						},
						success(res) {
							console.log(res.data)
						}
					})

				} else if (that.currentIndex == 3) {
					// 光照  同上  /auto/lightIntensity
					uni.request({
						url: 'http://116.62.199.133:8081/auto/lightIntensity',
						method: 'get',
						data: {
							floor: that.newData[that.currentIndex].low,
							top: that.newData[that.currentIndex].high
						},
						success(res) {
							console.log(res.data)
						}
					})

				} else if (that.currentIndex == 4) {
					// 二氧化碳浓度  同上  /auto/carbonDioxide
					uni.request({
						url: 'http://116.62.199.133:8081/auto/carbonDioxide',
						method: 'get',
						data: {
							floor: that.newData[that.currentIndex].low,
							top: that.newData[that.currentIndex].high
						},
						success(res) {
							console.log(res.data)
						}
					})
				}

			},
			getGlobalData:function(){
				
				this.proData[0].historyHigh = this.$globalData.envParameters[0].high
				this.proData[0].historyLow = this.$globalData.envParameters[0].low
				// console.log(this.proData[0].historyHigh)
				this.proData[1].historyHigh = this.$globalData.envParameters[1].high
				this.proData[1].historyLow = this.$globalData.envParameters[1].low
				//
				this.proData[2].historyHigh = this.$globalData.envParameters[2].high
				this.proData[2].historyLow = this.$globalData.envParameters[2].low
				//
				this.proData[4].historyHigh = this.$globalData.envParameters[3].high
				this.proData[4].historyLow = this.$globalData.envParameters[3].low
				//
				this.proData[3].historyHigh = this.$globalData.envParameters[4].high
				this.proData[3].historyLow = this.$globalData.envParameters[4].low
			},
			highParameterChange: function(event) {
				this.newData[this.currentIndex].high = event.target.value
				console.log(this.newData[this.currentIndex].high)
			},
			lowParameterChange: function(event) {
				this.newData[this.currentIndex].low = event.target.value
				console.log(this.newData[this.currentIndex].low)
			},
			addSensor: function() {
				uni.navigateTo({
					url: "/pages/thirdPages/addDevandSensor/addDevandSensor?name=传感器"
				})
			},
			getOneNet: function() {
				// 页面加载时先获取一次数据
				let that = this
				// 定时调用获取oneNet平台监控到的环境数据
				uni.request({
					url: "http://116.62.199.133:8081/oneNet/getOnenetLatestdata",
					method: 'get',
					success(res) {
						// console.log(JSON.stringify(res.data.data))

						let result = JSON.stringify(res.data.data)

						// 空气湿度
						that.sensors[1].parameter = JSON.stringify(res.data.data[0].value).replace(/"/g, '')
						// 大气压
						that.sensors[7].parameter = JSON.stringify(res.data.data[2].value).replace(/"/g, '')
						// 二氧化碳浓度
						that.sensors[4].parameter = JSON.stringify(res.data.data[4].value).replace(/"/g, '')
						// 光照  7
						that.sensors[3].parameter = JSON.stringify(res.data.data[7].value).replace(/"/g, '')
						// smoke  9

						// 土壤湿度  10
						that.sensors[2].parameter = JSON.stringify(res.data.data[10].value).replace(/"/g, '')
						// 温度  12
						that.sensors[0].parameter = JSON.stringify(res.data.data[12].value).replace(/"/g, '')
						// 水位  14
						that.sensors[6].parameter = JSON.stringify(res.data.data[14].value).replace(/"/g, '')
						// 水质PH 16
						that.sensors[5].parameter = JSON.stringify(res.data.data[16].value).replace(/"/g, '')

					}
				})
			},
			showDrawer() {
				this.$refs.showLeft.open();
			},
			closeDrawer() {
				this.$refs.showLeft.close();
			}

		},
		onPullDownRefresh() {

			this.getOneNet()

			// let that = this
			// // 定时调用获取oneNet平台监控到的环境数据
			// uni.request({
			// 	url: "http://116.62.199.133:8081/oneNet/getOnenetLatestdata",
			// 	method: 'get',
			// 	success(res) {
			// 		// console.log(JSON.stringify(res.data.data))

			// 		let result = JSON.stringify(res.data.data)

			// 		// 空气湿度
			// 		that.sensors[1].parameter = JSON.stringify(res.data.data[0].value).replace(/"/g, '')
			// 		// 大气压
			// 		that.sensors[7].parameter = JSON.stringify(res.data.data[2].value).replace(/"/g, '')
			// 		// 二氧化碳浓度
			// 		that.sensors[4].parameter = JSON.stringify(res.data.data[4].value).replace(/"/g, '')
			// 		// 光照  7
			// 		that.sensors[3].parameter = JSON.stringify(res.data.data[7].value).replace(/"/g, '')
			// 		// smoke  9

			// 		// 土壤湿度  10
			// 		that.sensors[2].parameter = JSON.stringify(res.data.data[10].value).replace(/"/g, '')
			// 		// 温度  12
			// 		that.sensors[0].parameter = JSON.stringify(res.data.data[12].value).replace(/"/g, '')
			// 		// 水位  14
			// 		that.sensors[6].parameter = JSON.stringify(res.data.data[14].value).replace(/"/g, '')
			// 		// 水质PH 16
			// 		that.sensors[5].parameter = JSON.stringify(res.data.data[16].value).replace(/"/g, '')

			// 	}
			// })

			// 实现下拉刷新
			setTimeout(() => {
				uni.stopPullDownRefresh()
			}, 500)

		},
		onLoad() {

			this.currentGreen = this.$globalData.currentGreen
			this.getOneNet()
			this.getGlobalData()
		},
		onShow() {
			let that = this
			that.getOneNet()
			that.timer = setInterval(function() {
				that.getOneNet()
			}, 1000)
		},
		onHide() {
			if (this.timer) {
				clearInterval(this.timer)
				this.timer = null
			}
		},
		onUnload() {
			if (this.timer) {
				clearInterval(this.timer)
				this.timer = null
			}
		}
	};
</script>

<style>
	.title {
		font-size: 36px;
		font-family: '华文行楷';
		margin: 9% 9% 9% 30%;
		color: #2B3E2A;
	}
	
	/* 左侧边栏*/
	.drawer .titleHead{
		margin-top: 15%;
		text-align: center;
		font-weight: bold;
		font-size: 18px;
	}
	
	.drawer .leftItem{
		background-color: #C4DFAC;
		margin: 7% 5%;
		border-radius: 25rpx;
		padding: 3%;
		box-shadow: rgba(149, 157, 165, 0.2) 0px 8px 24px;
	}
	
	.drawer .leftItem .parameter{
		font-weight: bold;
		padding-bottom: 2%;
	}
	
	.cardShow {
		background-color: white;
		width: 90% auto;
		margin: 10% 5% 5% 5%;
		border-radius: 25rpx;
		/* box-shadow: 0 0 5px 1px #999 */
		/* box-shadow: rgba(255, 255, 255, 0.1) 0px 1px 1px 0px inset, rgba(50, 50, 93, 0.25) 0px 50px 100px -20px, rgba(0, 0, 0, 0.3) 0px 30px 60px -30px; */
		box-shadow: rgba(0, 0, 0, 0.15) 0px 5px 15px 0px;
	}

	.Item {
		display: flex;
		align-items: center;
		height: 120rpx;
		padding-left: 3%;
		padding-right: 3%;
	}

	.Item image {
		width: 57rpx;
		height: 57rpx;
		margin-right: 10%;

	}

	.cardShow .addSensor {
		padding: 5%;
	}

	.addSensor button {
		/* background-color: #018577; */
		background-color: #C4DFAC;
		color: #254617;
		font-weight: bold;
	}

	.context {
		margin: 3%;
	}

	.contextItem {
		display: flex;
		margin-top: 6%;
	}

	.context .title {
		font-size: 18px;
		font-family: '黑体';
		font-weight: bold;
		margin-left: 25%;
		color: #28491C;
	}

	.context .name {
		font-weight: bold;
		padding-left: 9%;
	}

	.context .value {
		margin-right: 15%;
		margin-left: 2%;
	}

	.context .button {
		background-color: #C4DFAC;
		color: #254617;
		font-weight: bold;
		margin-top: 20%;
		color: white;
	}

	.context .input {
		width: 10%;
		margin-left: 2%;
	}

	.Greenline {
		border-bottom: 3px solid #28491C;
		border-top: 0px;
		border-left: 0px;
		border-right: 0px;
		width: 73%;
	}
</style>