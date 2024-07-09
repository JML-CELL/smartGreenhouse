<template>
	<view class="analysisThreeDay">
		<!--日期部分-->
		<view class="dateShow">
			<text>日期：</text>
			<text>{{date}}</text>
		</view>

		<!--调用某一天组件，可视化-->
		<view class="canavsItem">
			<view class="title">
				<text>柱形图-折线-曲线混合图</text>
				<icon class="iconfont icon-youbian"></icon>
			</view>
			<!--测试一下三种统计分析图 -->
			<threeAnalysisDayComponent :series="airTemperatureDayThree" class="canavs">
			</threeAnalysisDayComponent>
		</view>
	</view>
</template>

<script>
	import threeAnalysisDayComponent from '../../../components/threeAnalysisDayComponent/threeAnalysisDayComponent.vue'
	export default {
		data() {
			return {
				date: "",
				airTemperatureDayThree: [{
						name: "柱状图",
						index: 1,
						type: "column",
						data: [15, 15, 15, 17, 17, 17,
						15, 15, 15, 16, 16, 16,
						16, 16, 16, 16, 16, 15,
						15, 15, 15, 16, 16, 16
					]
					},
					{
						name: "曲线",
						type: "line",
						style: "curve",
						color: "#1890ff",
						disableLegend: true,
						data: [15, 15, 15, 17, 17, 17,
						15, 15, 15, 16, 16, 16,
						16, 16, 16, 16, 16, 15,
						15, 15, 15, 16, 16, 16
					]
					},
					{
						name: "折线",
						type: "line",
						color: "#F08784",
						data: [15, 15, 15, 17, 17, 17,
						15, 15, 15, 16, 16, 16,
						16, 16, 16, 16, 16, 15,
						15, 15, 15, 16, 16, 16
					]
					}
				],
			}
		},
		onLoad(option) {
			let that = this
			that.date = option.date
		},
		methods: {

		},
		components: {
			threeAnalysisDayComponent
		}
	}
</script>

<style>
	.analysisThreeDay {
		display: flex;
		flex-direction: column;
		align-items: center;
	}

	.dateShow {
		display: flex;
		justify-content: center;
		margin-top: 3%;
		margin-bottom: 3%;
		background-color: #C4DFAA;
		width: 40%;
		padding-top: 2%;
		padding-bottom: 2%;
		color: #4A6C4C;
		font-weight: bold;
		font-size: 18px;
		border-radius: 10rpx;
	}

	.canavsItem {
		background-color: white;
		padding: 3% 0;
		box-shadow: rgba(60, 64, 67, 0.3) 0px 1px 2px 0px, rgba(60, 64, 67, 0.15) 0px 1px 3px 1px;
		border-radius: 50rpx;
		margin-top: 9%;
	}

	.canavsItem .title {

		display: flex;
		justify-content: space-between;
		padding: 2% 5% 5% 5%;
		align-items: center;
		font-weight: bold;
		color: #254617;
	}

	.canavsItem .iconfont {
		color: #7DA47C;
		font-size: 19PX;
	}
</style>