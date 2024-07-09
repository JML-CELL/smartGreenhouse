<template>
	<view class="threeAnalysisdayComponent">
		<view class="charts-box">
			<qiun-data-charts type="mix" :opts="opts" :chartData="chartData" :ontouch="true" @complete="complete"
				@error="error" @getIndex="getIndex" />
		</view>
	</view>
</template>

<script>
	export default {
		name: 'threeAnalysisDayComponent',
		props: {
			series: Array,
		},
		data() {
			return {
				chartData: {},
				//这里的 opts 是图表类型 type="mix" 的全部配置参数，您可以将此配置复制到 config-ucharts.js 文件中下标为 ['mix'] 的节点中来覆盖全局默认参数。实际应用过程中 opts 只需传入与全局默认参数中不一致的【某一个属性】即可实现同类型的图表显示不同的样式，达到页面简洁的需求。
				opts: {
					timing: "easeOut",
					duration: 1000,
					rotate: false,
					rotateLock: false,
					color: ["#A1FB8E", "#91CB74", "#FAC858", "#EE6666", "#73C0DE", "#3CA272", "#FC8452", "#9A60B4",
						"#ea7ccc"
					],
					padding: [15, 15, 0, 15],
					fontSize: 13,
					fontColor: "#666666",
					dataLabel: true,
					dataPointShape: true,
					dataPointShapeType: "solid",
					touchMoveLimit: 60,
					enableScroll: true,
					enableMarkLine: false,
					legend: {
						show: true,
						position: "bottom",
						float: "center",
						padding: 5,
						margin: 5,
						backgroundColor: "rgba(0,0,0,0)",
						borderColor: "rgba(0,0,0,0)",
						borderWidth: 0,
						fontSize: 13,
						fontColor: "#666666",
						lineHeight: 11,
						hiddenColor: "#CECECE",
						itemGap: 10
					},
					xAxis: {
						disableGrid: true,
						disabled: false,
						axisLine: true,
						axisLineColor: "#CCCCCC",
						calibration: false,
						fontColor: "#666666",
						fontSize: 13,
						lineHeight: 20,
						marginTop: 0,
						rotateLabel: false,
						rotateAngle: 45,
						itemCount: 5,
						boundaryGap: "center",
						splitNumber: 7,
						gridColor: "#CCCCCC",
						gridType: "solid",
						dashLength: 4,
						gridEval: 1,
						scrollShow: true,
						scrollAlign: "left",
						scrollColor: "#A6A6A6",
						scrollBackgroundColor: "#EFEBEF",
						title: "",
						titleFontSize: 13,
						titleOffsetY: 0,
						titleOffsetX: 0,
						titleFontColor: "#666666",
						format: ""
					},
					yAxis: {
						disabled: false,
						disableGrid: false,
						splitNumber: 5,
						gridType: "dash",
						dashLength: 4,
						gridColor: "#CCCCCC",
						padding: 10,
						showTitle: true,
						data: [{
								position: "left",
								title: "折线",
								min: 0,
								max: 40
							},
							{
								position: "right",
								min: 0,
								max: 40,
								title: "柱形",
								textAlign: "right",
								type: "value",
								disabled: false,
								axisLine: true,
								axisLineColor: "#CCCCCC",
								calibration: false,
								fontColor: "#666666",
								fontSize: 13,
								titleFontSize: 13,
								titleOffsetY: 0,
								titleOffsetX: 0,
								titleFontColor: "#666666",
								tofix: null,
								unit: "",
								format: ""
							},
							{
								position: "right",
								min: 0,
								max: 40,
								title: "点",
								textAlign: "right",
								type: "value",
								disabled: false,
								axisLine: true,
								axisLineColor: "#CCCCCC",
								calibration: false,
								fontColor: "#666666",
								fontSize: 13,
								titleFontSize: 13,
								titleOffsetY: 0,
								titleOffsetX: 0,
								titleFontColor: "#666666",
								tofix: null,
								unit: "",
								format: ""
							}
						]
					},
					extra: {
						mix: {
							column: {
								width: 20,
								seriesGap: 2,
								barBorderCircle: true,
								linearType: "custom",
								linearOpacity: 1,
								colorStop: 0
							},
							area: {
								opacity: 0.2,
								gradient: false
							},
							line: {
								width: 2
							}
						},
						tooltip: {
							showBox: true,
							showArrow: true,
							showCategory: false,
							borderWidth: 0,
							borderRadius: 0,
							borderColor: "#000000",
							borderOpacity: 0.7,
							bgColor: "#000000",
							bgOpacity: 0.7,
							gridType: "solid",
							dashLength: 4,
							gridColor: "#CCCCCC",
							boxPadding: 3,
							fontSize: 13,
							lineHeight: 20,
							fontColor: "#FFFFFF",
							legendShow: true,
							legendShape: "auto",
							splitLine: true,
							horizentalLine: false,
							xAxisLabel: false,
							yAxisLabel: false,
							labelBgColor: "#FFFFFF",
							labelBgOpacity: 0.7,
							labelFontColor: "#666666"
						},
						markLine: {
							type: "solid",
							dashLength: 4,
							data: []
						}
					}
				}
			};
		},
		mounted() {
			this.getServerData();
		},
		updated() {
			this.getServerData();
		},
		methods: {
			getServerData() {
				//模拟从服务器获取数据时的延时
				setTimeout(() => {
					//模拟服务器返回数据，如果数据格式和标准格式不同，需自行按下面的格式拼接
					let res = {
						categories: ["0", "1", "2", "3", "4", "5",
							"6", "7", "8", "9", "10", "11",
							"12", "13", "14", "15", "16", "17",
							"18", "19", "20", "21", "22", "23"
						],
						// series: [{
						// 		name: "柱状图",
						// 		index: 1,
						// 		type: "column",
						// 		data: [40, 30, 55, 110, 24, 58, 58]
						// 	},
						// 	{
						// 		name: "曲线",
						// 		type: "line",
						// 		style: "curve",
						// 		color: "#1890ff",
						// 		disableLegend: true,
						// 		data: [40, 30, 55, 110, 24, 58, 58]
						// 	},
						// 	{
						// 		name: "折线",
						// 		type: "line",
						// 		color: "#F08784",
						// 		data: [40, 30, 55, 110, 24, 58, 58]
						// 	}
						// ]
						series: []

					};
					res.series = this.series
					this.chartData = JSON.parse(JSON.stringify(res));
				}, 500);
			},
			complete(e) {
				console.log(e)
			},
			error(e) {
				console.log(e)
			},
			getIndex(e) {
				console.log(e)
				
			},
		}
	};
</script>

<style scoped>
	/* 请根据实际需求修改父元素尺寸，组件自动识别宽高 */
	.charts-box {
		width: 100%;
		height: 300px;
	}
</style>