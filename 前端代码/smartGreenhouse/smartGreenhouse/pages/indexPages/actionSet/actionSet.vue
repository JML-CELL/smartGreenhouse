<template>
	<view class="actionSet">
		<!-- <view class="part_one">
			<image src="/static/logo.png" mode="" style="width: 100%;"></image>
		</view> -->

		<view class="uni-margin-wrap">
			<swiper class="swiper" circular :indicator-dots="indicatorDots" :autoplay="autoplay" :interval="interval"
				:duration="duration">
				<swiper-item>
					<view class="swiper-item"><image src="/static/images/actionSet1.png"></image></view>
				</swiper-item>
				<swiper-item>
					<view class="swiper-item"><image src="/static/images/actionSet2.png"></image></view>
				</swiper-item>
				<swiper-item>
					<view class="swiper-item"><image src="/static/images/actionSet3.png"></image></view>
				</swiper-item>
			</swiper>
		</view>

		<view class="part_two">
			<text class="title">专属场景</text>
			<scroll-view scroll-x class="scroll_x">
				<view class="action">
					<view class="actionItem">
						<view class="item_one">
							<view class="titleText">番茄开花阶段</view>
							<switch @change="switchChange" color="#4A6C4C" />
						</view>
						<!-- <view class="plan" @click="gotoTimeStop">
							<icon class="iconfont icon-naozhong" style="font-size: 20px;margin-right: 5%;"></icon>
							<text style="font-size: 17px;" @click="gotoTimeSystem">定时计划</text>
						</view> -->
					</view>
					<view class="addItem" style="font-size: 20px;width: 30%;" @click="addScenes">
						<icon class="iconfont icon-zengjiatianjiajiahao" style="font-size: 22px; margin-right: 3%;">
						</icon>
						<text>添加场景</text>
					</view>
				</view>
			</scroll-view>
		</view>

		<view class="part_three">
			<text class="title">推荐生长环境参数</text>
			<view class="addItem" style="font-size: 20px;width: 30%;" @click="addScenes">
			</view>
		</view>

		<!-- 在这里接收一下建议的传感器参数 -->
		<view class="advices">
			<fui-card src="/static/icons/username.png" title="植物生长环境阈值参数建议" tag="详情" :padding="['20rpx','32rpx']" full>
				<view class="fui-card__content">
					<view class="contentItem" v-for="(item,index) in content">
						{{item}}
					</view>
				</view>
			</fui-card>
		</view>
	</view>
</template>

<script>
	import fuiCard from "@/uni_modules/firstui-uni/firstui/fui-card/fui-card.vue"
	export default {
		data() {
			return {
				content: ['请选择特定场景，我们会根据你选择的场景为你推荐植物生成环境参数并为你自动设置'],
				indicatorDots: true,
				autoplay: true,
				interval: 2000,
				duration: 500
			}
		},
		components: {
			fuiCard
		},
		methods: {
			switchChange: function(e) {
				console.log(e.detail.value)
				// 如果开启了就进行如下操作 -- 请求gpt查询适宜参数   切割数字设置阈值并给予用户提示

				// 调用推荐传感器参数的接口
				let that = this
				if (e.detail.value == true) {
					uni.request({
						url: 'http://116.62.199.133:8081/gpt/predict',
						method: 'get',
						data: {
							cropper: '番茄',
							stage: '开花'
						},
						success(res) {
							console.log(res)
							let temperatureA = res.data.temperature.split("-")
							// console.log(temperatureA[0].replace(/"/g, ''))
							let soil_moistureA = res.data.soil_moisture.split("-")
							let carbonA = res.data.carbon.split("-")
							let illuminationA = res.data.illumination.split("-")
							let phA = res.data.ph.split("-")
							let airHumidity = res.data.air_humidity.split("-")

							let content = ["建议" + "番茄" + "作物" + "开花" + "阶段的适宜生长环境为: ",
								"1、温度适宜范围为: " + res.data.temperature + "°C",
								"2、空气湿度适宜范围为: " + res.data.air_humidity + "hPa",
								"3、土壤湿度适宜范围为: " + res.data.soil_moisture + "hPa",
								"4、二氧化碳浓度适宜范围为: " + res.data.carbon + "pm",
								"5、光照适宜范围为: " + res.data.illumination + "cd",
								"6、水质PH适宜范围为: " + res.data.ph + ""
							]
							that.content = content

							// 得到参数再调用阈值设置接口
							// console.log(Math.round(55/2))
							let temperatureAV = Math.round((temperatureA[0].replace(/"/g, '') + temperatureA[1]
								.replace(/"/g, '')) / 2)
							//空气温度
							// 先保存在全局
							that.$globalData.envParameters[0].high = temperatureA[0].replace(/"/g, '')
							that.$globalData.envParameters[0].low = temperatureA[1].replace(/"/g, '')
							
							uni.request({
								url: 'http://116.62.199.133:8081/auto/temperature',
								method: 'get',
								data: {
									temperature: temperatureAV
								},
								success(res) {
									console.log(res)
								}
							})

							//空气湿度
							// 先保存在全局
							that.$globalData.envParameters[1].high = airHumidity[0].replace(/"/g, '')
							that.$globalData.envParameters[1].low = airHumidity[1].replace(/"/g, '')
							uni.request({
								url: 'http://116.62.199.133:8081/auto/airHumidity',
								method: 'get',
								data: {
									floor: airHumidity[0].replace(/"/g, ''),
									top: airHumidity[1].replace(/"/g, '')
								},
								success(res) {
									console.log(res)
								}
							})

							// 土壤湿度  同空气湿度  /auto/soilhumidity
							// 先保存在全局
							that.$globalData.envParameters[2].high = soil_moistureA[0].replace(/"/g, '')
							that.$globalData.envParameters[2].low = soil_moistureA[1].replace(/"/g, '')
							uni.request({
								url: 'http://116.62.199.133:8081/auto/soilhumidity',
								method: 'get',
								data: {
									floor: soil_moistureA[0].replace(/"/g, ''),
									top: soil_moistureA[1].replace(/"/g, '')
								},
								success(res) {
									console.log(res)
								}
							})

							// 二氧化碳浓度  同上  /auto/carbonDioxide
							// 先保存在全局
							that.$globalData.envParameters[3].high = carbonA[0].replace(/"/g, '')
							that.$globalData.envParameters[3].low = carbonA[1].replace(/"/g, '')
							uni.request({
								url: 'http://116.62.199.133:8081/auto/carbonDioxide',
								method: 'get',
								data: {
									floor: carbonA[0].replace(/"/g, ''),
									top: carbonA[1].replace(/"/g, '')
								},
								success(res) {
									console.log(res)
								}
							})

							// 光照  同上  /auto/lightIntensity
							// 先保存在全局
							that.$globalData.envParameters[4].high = illuminationA[0].replace(/"/g, '')
							that.$globalData.envParameters[4].low = illuminationA[1].replace(/"/g, '')
							uni.request({
								url: 'http://116.62.199.133:8081/auto/lightIntensity',
								method: 'get',
								data: {
									floor: illuminationA[0].replace(/"/g, ''),
									top: illuminationA[1].replace(/"/g, '')
								},
								success(res) {
									console.log(res)
								}
							})
							
							// 提示导入成功
							setTimeout(()=>{
								// uni.showToast({
								// 	title: '推荐阈值范围导入成功',
								// 	icon: 'success'
								// })
								uni.showModal({
									title: '推荐阈值范围导入成功',
									icon: 'error',
									confirmText: "确定",
									showCancel: false,
								})
							},500)
							
							
							// 跳转
							uni.navigateTo({
								url: '/pages/indexPages/ThresholdSet/ThresholdSet'
							})
							
						}
					})
				}
			},
			gotoTimeStop: function() {
				uni.navigateTo({
					url: "/pages/thirdPages/timeSystem/timeSystem"
				})
			},
			addScenes: function() {
				uni.navigateTo({
					url: '/pages/thirdPages/addScenes/addScenes'
				})
			},
			gotoTimeSystem: function() {
				// 这里在跳转页面的时候还要传参，把场景的id传过去,这里后面再补
				uni.navigateTo({
					url: '/pages/thirdPages/timeSystem/timeSystem'
				})
			}
		}
	}
</script>

<style>
	.uni-margin-wrap {
		width: 690rpx;
		width: 100%;
	}

	.swiper {
		height: 300rpx;
	}

	.swiper-item {
		display: block;
		height: 300rpx;
		line-height: 300rpx;
		text-align: center;
	}
	
	.swiper-item image{
		width: 100%;
	}
	
	.part_two{
		margin-top: 3%;
	}

	.action {
		display: flex;
		flex-direction: row;
		align-items: center;
	}

	.actionItem {
		margin: 5% 3%;
		background-color: #C4DFAA;
		text-align: center;
		color: #4A6C4C;
		border-radius: 10rpx;
		padding: 2% 3%;
		/* box-shadow: 0 0 5px 1px #999; */
		box-shadow: rgba(17, 17, 26, 0.1) 0px 0px 16px;
		width: 37%;
	}

	.actionItem .item_one {
		display: flex;
		align-items: center;
		justify-content: center;
		flex-direction: column;
	}

	.item_one .titleText {
		font-size: 20px;
		font-weight: bold;
		margin-bottom: 5%;
	}

	/* .plan {
		margin: 7%;
		background-color: #87B9BA;
		padding: 2%;
		border-radius: 10rpx;
	} */

	.part_two .title,
	.part_three .title {
		font-size: 20px;
		font-weight: bold;
		margin: 5%;
	}

	.advices {
		box-shadow: rgba(17, 17, 26, 0.1) 0px 0px 16px;
		margin: 5% 5% 5% 3%;
	}

	.advices .fui-card__content {
		padding: 3% 3% 10% 0;
	}

	.contentItem {
		padding-left: 7%;
	}
</style>