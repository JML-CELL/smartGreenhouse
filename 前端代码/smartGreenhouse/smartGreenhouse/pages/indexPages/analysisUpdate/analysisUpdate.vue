<template>
	<view class="analysisUpdate">

		<!--标题区：统计分析 -->
		<!-- <view class="head">
			<icon class="iconfont icon-zuojiantou" @click="goBack" style="font-size: 20px;margin-left: 3%;"></icon>
			<view class="context">
				统计分析
			</view>
			<view class="switchpart">
				<picker mode="selector" :range="greenHouseList" @change="pickGreen($event)" :value="indexGreen">
					<icon class="iconfont icon-xialakuangxiala" style="color: #018577;"></icon>
					<text>{{greenHouseList[indexGreen]}}</text>
				</picker>
			</view>
		</view> -->

		<!-- 图片+大棚名称 -->
		<view class="head">
			<image :src="housePic" mode=""></image>
			<view class="name">
				{{houseName}}
			</view>
		</view>

		<!--顶部scrollview-->
		<view class="scroll">
			<scroll-view scroll-x class="scroll-h">
				<block v-for="(tab, index) in tabBars" :key="tab.id">
					<view class="uni-tab-item" :class="{ 'uni-tab-item-title-active': scrollIndex == index }"
						@tap="tabtap(index)">
						<text class="uni-tab-item-title"
							:class="scrollIndex == index ? 'uni-tab-item-title-active' : ''">{{ tab.name }}</text>
					</view>
				</block>
			</scroll-view>
		</view>


		<!--按index显示不同组件-->
		<view class="canavsItem">
			<view class="title">
				<text>折线图</text>
				<icon class="iconfont icon-youbian"></icon>
			</view>
			<analysisComponent :series="airTemperature" v-if="scrollIndex==0" class="canavs"></analysisComponent>
			<analysisComponent :series="airHumidity" v-if="scrollIndex==1" class="canavs"></analysisComponent>
			<analysisComponent :series="illumination" v-if="scrollIndex==2" class="canavs"></analysisComponent>
			<analysisComponent :series="carbonDioxide" v-if="scrollIndex==3" class="canavs"></analysisComponent>
			<analysisComponent :series="soilMoisture" v-if="scrollIndex==4" class="canavs"></analysisComponent>
			<analysisComponent :series="waterQualityPH" v-if="scrollIndex==5" class="canavs"></analysisComponent>
			<analysisComponent :series="atmosphericPressure" v-if="scrollIndex==6" class="canavs"></analysisComponent>
			<analysisComponent :series="waterLevel" v-if="scrollIndex==7" class="canavs"></analysisComponent>
		</view>

		<view class="canavsItem">
			<view class="title">
				<text>柱形图-折线-曲线混合图</text>
				<icon class="iconfont icon-youbian"></icon>
			</view>
			<!--测试一下三种统计分析图 -->
			<threeAnalysisComponent :series="airTemperatureThree" v-if="scrollIndex==0" class="canavs">
			</threeAnalysisComponent>
		</view>
	</view>
</template>

<script>
	import analysisComponent from '../../../components/analysisComponent/analysisComponent.vue'
	import threeAnalysisComponent from '../../../components/threeAnalysisComponent/threeAnalysisComponent.vue'
	import fuiCard from "@/uni_modules/firstui-uni/firstui/fui-card/fui-card.vue"
	export default {
		data() {
			return {
				// 数据可视化所需的数据和判断位
				airTemperature: [{
					name: "最高空气温度",
					data: [15, 15, 16, 16, 21, 22, 21]
				},{
					name: "最低空气温度",
					data: [13, 14, 12, 13, 14, 14, 16]
				}],
				airHumidity: [{
					name: "空气湿度",
					data: [25, 8, 25, 37, 4, 20, 30]
				}],
				illumination: [{
					name: "光照",
					data: [35, 8, 25, 37, 4, 20, 30]
				}],
				carbonDioxide: [{
					name: "二氧化碳",
					data: [35, 8, 25, 37, 4, 20, 30]
				}],
				soilMoisture: [{
					name: "土壤湿度",
					data: [35, 8, 25, 37, 4, 20, 30]
				}],
				waterQualityPH: [{
					name: "水质PH",
					data: [35, 8, 25, 37, 4, 20, 30]
				}],
				atmosphericPressure: [{
					name: "大气压",
					data: [35, 8, 25, 37, 4, 20, 30]
				}],
				waterLevel: [{
					name: "水位",
					data: [35, 8, 25, 37, 4, 20, 30]
				}],

				// 3个统计分析图的数据prop准备
				airTemperatureThree: [{
						name: "柱状图",
						index: 1,
						type: "column",
						data: [15, 15, 16, 16, 21, 22, 21]
					},
					{
						name: "曲线",
						type: "line",
						style: "curve",
						color: "#1890ff",
						disableLegend: true,
						data: [15, 15, 16, 16, 21, 22, 21]
					},
					{
						name: "折线",
						type: "line",
						color: "#F08784",
						data: [15, 15, 16, 16, 21, 22, 21]
					}
				],

				// tabBars页面
				scrollIndex: 0,
				tabBars: [{
						name: '空气温度',
						id: 'airTemperature'
					},
					{
						name: '空气湿度',
						id: 'airHumidity'
					},
					{
						name: '光照',
						id: 'illumination'
					},
					{
						name: '二氧化碳',
						id: 'carbonDioxide'
					},
					{
						name: '土壤湿度',
						id: 'soilMoisture'
					},
					{
						name: '水质PH',
						id: 'waterQualityPH'
					},
					{
						name: '大气压',
						id: 'atmosphericPressure'
					},
					{
						name: '水位',
						id: 'waterLevel'
					}
				],

				// 标题处的picker数据
				greenHouseList: ['番茄大棚', '草莓大棚', '玫瑰花大棚'],
				indexGreen: 0,

				content: '',
				housePic: "/static/images/tomoto.png",
				houseName: "番茄大棚",
			}
		},
		methods: {
			tabtap(index) {
				this.scrollIndex = index;
				console.log(this.scrollIndex)

				// 根据index的判断传入不同的数据到组件analysisComponent中
				if (index == 0) {
					// this.series = [{
					// 	name: "空气温度",
					// 	data: [35, 8, 25, 37, 4, 20, 20]
					// }]
				}
			},
			pickGreen(e) {
				this.indexGreen = e.target.value
			},
			goBack: function() {
				uni.navigateBack()
			},

		},
		onLoad() {

			// 获取全局温室名称
			this.housePic = this.$globalData.currentPic
			this.houseName = this.$globalData.currentGreen

			let that = this

			uni.request({
				url: 'http://116.62.199.133:8081/gpt/predict',
				method: 'get',
				data: {
					cropper: '番茄'
				},
				success(res) {
					console.log(res.data)
					// let temperatureA = res.data.temperature.split("-")
					// // console.log(temperatureA)
					// let soil_moistureA = res.data.soil_moisture.split("-")
					// let carbonA = res.data.carbon.split("-")
					// let illuminationA = res.data.illumination.split("-")
					// let phA = res.data.ph.split("-")

					let content = ["建议" + "番茄" + "作物适宜生长环境为: ",
						"1、温度适宜范围为: " + res.data.temperature + "°C",
						"2、土壤湿度适宜范围为: " + res.data.soil_moisture + "hPa",
						"3、二氧化碳浓度适宜范围为: " + res.data.carbon + "pm",
						"4、光照适宜范围为: " + res.data.illumination + "cd",
						"5、水质PH适宜范围为: " + res.data.ph + ""
					]
					that.content = content
				}
			})

			// onenet

		},
		components: {
			analysisComponent,
			threeAnalysisComponent,
			fuiCard
		}
	}
</script>

<style>
	.head {
		position: relative;
	}

	.head image {
		width: 100%;
		border-radius: 0 0 50rpx 50rpx;
		box-shadow: rgba(60, 64, 67, 0.3) 0px 1px 2px 0px, rgba(60, 64, 67, 0.15) 0px 1px 3px 1px;
	}

	/* .head:after {
	    position: absolute;
	    content: '';
	    width: 100%;
	    height: 100%;
	    top: 0;
	    left: 0;
	    box-shadow:0 0 50px 0 #ffffff inset;
	} */

	.head .name {

		position: absolute;
		bottom: 7%;
		right: 3%;
		color: white;
		font-weight: bold;
		font-size: 24px;
		box-shadow: rgba(60, 64, 67, 0.3) 0px 1px 2px 0px, rgba(60, 64, 67, 0.15) 0px 1px 3px 1px;

	}


	.canavsItem {
		background-color: white;
		padding: 3% 0;
		box-shadow: rgba(60, 64, 67, 0.3) 0px 1px 2px 0px, rgba(60, 64, 67, 0.15) 0px 1px 3px 1px;
		border-radius: 25rpx;
		margin-bottom: 9%;
	}
	
	.canavsItem .title{
		
		display: flex;
		justify-content: space-between;
		padding: 2% 5% 5% 5%;
		align-items: center;
		font-weight: bold;
		color: #254617;
	}
	
	.canavsItem .iconfont{
		color: #7DA47C;
		font-size: 19PX;
	}

	.tabs {
		flex: 1;
		flex-direction: column;
		overflow: hidden;
		/* background-color: #ffffff; */
		height: 100vh;
	}

	.scroll-h {
		width: 750upx;
		height: 100upx;
		flex-direction: row;

		white-space: nowrap;
		margin-bottom: 3%;
	}

	.line-h {
		height: 1upx;
		background-color: #cccccc;
	}

	.uni-tab-item {

		display: inline-block;

		flex-wrap: nowrap;
		padding-left: 34upx;
		padding-right: 34upx;
	}

	.uni-tab-item-title {
		color: #555;
		font-size: 30upx;
		height: 80upx;
		line-height: 80upx;
		flex-wrap: nowrap;

		white-space: nowrap;

	}

	.uni-tab-item-title-active {
		color: #4A6C4C;
		font-weight: bold;
		/* color: #254617; */
	}

	.advices {
		box-shadow: rgba(17, 17, 26, 0.1) 0px 0px 16px;
	}

	.advices .fui-card__content {
		padding: 3% 3% 10% 0;
	}

	.contentItem {
		padding-left: 7%;
	}
</style>