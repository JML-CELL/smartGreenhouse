<template>
	<view class="analysis">
		<view class="navigator">
			<view class="navItem" @click="gotoday">
				<picker mode="date" :value="date" :start="startDate" :end="endDate" @change="bindDateChange">
					<view class="date">日期 {{ date }}</view>
				</picker>
			</view>
			<view class="navItem" @click="toRecently">近七天</view>
		</view>
		<!--某一天数据可视化-->
		<view class="ucharts" v-if="isRecently==false">
			<!--空气温度-->
			<view class="charts-box"><qiun-data-charts type="line" :opts="opts" :chartData="chartData1" :onzoom="true" /></view>
			<!--空气湿度-->
			<view class="charts-box"><qiun-data-charts type="line" :opts="opts" :chartData="chartData2" :onzoom="true" /></view>
			<!--大气压-->
			<view class="charts-box"><qiun-data-charts type="line" :opts="opts" :chartData="chartData3" :onzoom="true" /></view>
			<!--土壤湿度-->
			<view class="charts-box"><qiun-data-charts type="line" :opts="opts" :chartData="chartData4" :onzoom="true" /></view>
			<!--水质PH-->
			<view class="charts-box"><qiun-data-charts type="line" :opts="opts" :chartData="chartData5" :onzoom="true" /></view>
			<!--水位-->
			<view class="charts-box"><qiun-data-charts type="line" :opts="opts" :chartData="chartData11" :onzoom="true" /></view>
			<!--光照-->
			<view class="charts-box"><qiun-data-charts type="line" :opts="opts" :chartData="chartData12" :onzoom="true" /></view>
			<!--二氧化碳-->
			<view class="charts-box"><qiun-data-charts type="line" :opts="opts" :chartData="chartData13" :onzoom="true" /></view>
		</view>
		<!--近7天数据可视化-->
		<view class="ucharts" v-if="isRecently==true">
			<!--空气温度-->
			<view class="charts-box"><qiun-data-charts type="line" :opts="opts" :chartData="chartData6" :onzoom="true" /></view>
			<!--空气湿度-->
			<view class="charts-box"><qiun-data-charts type="line" :opts="opts" :chartData="chartData7" :onzoom="true" /></view>
			<!--大气压-->
			<view class="charts-box"><qiun-data-charts type="line" :opts="opts" :chartData="chartData8" :onzoom="true" /></view>
			<!--土壤湿度-->
			<view class="charts-box"><qiun-data-charts type="line" :opts="opts" :chartData="chartData9" :onzoom="true" /></view>
			<!--水质PH-->
			<view class="charts-box"><qiun-data-charts type="line" :opts="opts" :chartData="chartData10" :onzoom="true" /></view>
			<!--水位-->
			<view class="charts-box"><qiun-data-charts type="line" :opts="opts" :chartData="chartData14" :onzoom="true" /></view>
			<!--光照-->
			<view class="charts-box"><qiun-data-charts type="line" :opts="opts" :chartData="chartData15" :onzoom="true" /></view>
			<!--二氧化碳-->
			<view class="charts-box"><qiun-data-charts type="line" :opts="opts" :chartData="chartData16" :onzoom="true" /></view>
		</view>
	</view>
</template>

<script>
function getDate(type) {
	const date = new Date();

	let year = date.getFullYear();
	let month = date.getMonth() + 1;
	let day = date.getDate();

	if (type === 'start') {
		year = year - 10;
	} else if (type === 'end') {
		year = year + 10;
	}
	month = month > 9 ? month : '0' + month;
	day = day > 9 ? day : '0' + day;

	return `${year}-${month}-${day}`;
}
export default {
	data() {
		return {
			date: getDate({
				format: true
			}),
			startDate: getDate('start'),
			endDate: getDate('end'),
			isRecently: false,
			// 空气温度
			chartData1: {},
			// 空气湿度
			chartData2: {},
			// 大气压
			chartData3: {},
			// 土壤湿度
			chartData4: {},
			// 水质PH
			chartData5: {},
			// 空气温度
			chartData6: {},
			// 空气湿度
			chartData7: {},
			// 大气压
			chartData8: {},
			// 土壤湿度
			chartData9: {},
			// 水质PH
			chartData10: {},
			//这里的 opts 是图表类型 type="line" 的全部配置参数，您可以将此配置复制到 config-ucharts.js 文件中下标为 ['line'] 的节点中来覆盖全局默认参数。实际应用过程中 opts 只需传入与全局默认参数中不一致的【某一个属性】即可实现同类型的图表显示不同的样式，达到页面简洁的需求。
			opts: {
				timing: 'easeOut',
				duration: 1000,
				rotate: false,
				rotateLock: false,
				color: ['#1890FF', '#91CB74', '#FAC858', '#EE6666', '#73C0DE', '#3CA272', '#FC8452', '#9A60B4', '#ea7ccc'],
				padding: [15, 10, 0, 15],
				fontSize: 13,
				fontColor: '#666666',
				dataLabel: true,
				dataPointShape: true,
				dataPointShapeType: 'solid',
				touchMoveLimit: 60,
				enableScroll: false,
				enableMarkLine: false,
				legend: {
					show: true,
					position: 'bottom',
					float: 'center',
					padding: 5,
					margin: 5,
					backgroundColor: 'rgba(0,0,0,0)',
					borderColor: 'rgba(0,0,0,0)',
					borderWidth: 0,
					fontSize: 13,
					fontColor: '#666666',
					lineHeight: 11,
					hiddenColor: '#CECECE',
					itemGap: 10
				},
				xAxis: {
					disableGrid: true,
					disabled: false,
					axisLine: true,
					axisLineColor: '#CCCCCC',
					calibration: true,
					fontColor: '#666666',
					fontSize: 13,
					lineHeight: 20,
					marginTop: 0,
					rotateLabel: false,
					rotateAngle: 45,
					itemCount: 5,
					boundaryGap: 'center',
					splitNumber: 5,
					gridColor: '#CCCCCC',
					gridType: 'solid',
					dashLength: 4,
					gridEval: 1,
					scrollShow: false,
					scrollAlign: 'left',
					scrollColor: '#A6A6A6',
					scrollBackgroundColor: '#EFEBEF',
					title: '',
					titleFontSize: 13,
					titleOffsetY: 0,
					titleOffsetX: 0,
					titleFontColor: '#666666',
					format: ''
				},
				yAxis: {
					gridType: 'dash',
					dashLength: 2,
					disabled: false,
					disableGrid: false,
					splitNumber: 5,
					gridColor: '#CCCCCC',
					padding: 10,
					showTitle: false,
					data: [
						{
							type: 'value',
							position: 'left',
							disabled: false,
							axisLine: true,
							axisLineColor: '#CCCCCC',
							calibration: false,
							fontColor: '#666666',
							fontSize: 13,
							textAlign: 'right',
							title: '',
							titleFontSize: 13,
							titleOffsetY: 0,
							titleOffsetX: 0,
							titleFontColor: '#666666',
							min: null,
							max: null,
							tofix: null,
							unit: '',
							format: ''
						}
					]
				},
				extra: {
					line: {
						type: 'straight',
						width: 2,
						activeType: 'hollow',
						linearType: 'none',
						onShadow: false,
						animation: 'vertical'
					},
					tooltip: {
						showBox: true,
						showArrow: true,
						showCategory: false,
						borderWidth: 0,
						borderRadius: 0,
						borderColor: '#000000',
						borderOpacity: 0.7,
						bgColor: '#000000',
						bgOpacity: 0.7,
						gridType: 'solid',
						dashLength: 4,
						gridColor: '#CCCCCC',
						boxPadding: 3,
						fontSize: 13,
						lineHeight: 20,
						fontColor: '#FFFFFF',
						legendShow: true,
						legendShape: 'auto',
						splitLine: true,
						horizentalLine: false,
						xAxisLabel: false,
						yAxisLabel: false,
						labelBgColor: '#FFFFFF',
						labelBgOpacity: 0.7,
						labelFontColor: '#666666'
					},
					markLine: {
						type: 'solid',
						dashLength: 4,
						data: []
					}
				}
			}
		};
	},
	onReady() {
		this.getServerData1();
		this.getServerData2();
	},
	methods: {
		bindDateChange: function(e) {
			this.date = e.detail.value;
		},
		toRecently: function(e){
			this.isRecently = true;
			console.log(this.isRecently)
		},
		gotoday:function(e){
			this.isRecently = false
			console.log(this.isRecently)
		},
		getServerData1() {
			if(this.isRecently==false){
				// 空气温度
				setTimeout(() => {
					//模拟服务器返回数据，如果数据格式和标准格式不同，需自行按下面的格式拼接
					let res = {
						categories: ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23'],
						series: [
							{
								name: '空气温度',
								data: [35, 8, 25, 37, 4, 20, 35, 8, 25, 37, 4, 20, 35, 8, 25, 37, 4, 20, 10, 15, 16, 3, 5, 8]
							}
						]
					};
					this.chartData1 = JSON.parse(JSON.stringify(res));
				}, 500);
				
				// 空气湿度
				setTimeout(() => {
					//模拟服务器返回数据，如果数据格式和标准格式不同，需自行按下面的格式拼接
					let res = {
						categories: ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23'],
						series: [
							{
								name: '空气湿度',
								data: [20, 8, 25, 37, 4, 20, 35, 8, 25, 37, 4, 20, 35, 8, 25, 37, 4, 20, 10, 15, 16, 3, 5, 8]
							}
						]
					};
					this.chartData2 = JSON.parse(JSON.stringify(res));
				}, 500);
				
				// 大气压
				setTimeout(() => {
					//模拟服务器返回数据，如果数据格式和标准格式不同，需自行按下面的格式拼接
					let res = {
						categories: ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23'],
						series: [
							{
								name: '大气压',
								data: [12, 8, 25, 37, 4, 20, 35, 8, 25, 37, 4, 20, 35, 8, 25, 37, 4, 20, 10, 15, 16, 3, 5, 8]
							}
						]
					};
					this.chartData3 = JSON.parse(JSON.stringify(res));
				}, 500);
				
				// 土壤湿度
				setTimeout(() => {
					//模拟服务器返回数据，如果数据格式和标准格式不同，需自行按下面的格式拼接
					let res = {
						categories: ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23'],
						series: [
							{
								name: '土壤湿度',
								data: [16, 8, 25, 37, 4, 20, 35, 8, 25, 37, 4, 20, 35, 8, 25, 37, 4, 20, 10, 15, 16, 3, 5, 8]
							}
						]
					};
					this.chartData4 = JSON.parse(JSON.stringify(res));
				}, 500);
				
				// 水质PH
				setTimeout(() => {
					//模拟服务器返回数据，如果数据格式和标准格式不同，需自行按下面的格式拼接
					let res = {
						categories: ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23'],
						series: [
							{
								name: '水质PH',
								data: [10, 8, 25, 37, 4, 20, 35, 8, 25, 37, 4, 20, 35, 8, 25, 37, 4, 20, 10, 15, 16, 3, 5, 8]
							}
						]
					};
					this.chartData5 = JSON.parse(JSON.stringify(res));
				}, 500);
				
				// 水位
				setTimeout(() => {
					//模拟服务器返回数据，如果数据格式和标准格式不同，需自行按下面的格式拼接
					let res = {
						categories: ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23'],
						series: [
							{
								name: '水位',
								data: [10, 8, 25, 37, 4, 20, 35, 8, 25, 37, 4, 20, 35, 8, 25, 37, 4, 20, 10, 15, 16, 3, 5, 8]
							}
						]
					};
					this.chartData11 = JSON.parse(JSON.stringify(res));
				}, 500);
				
				// 光照
				setTimeout(() => {
					//模拟服务器返回数据，如果数据格式和标准格式不同，需自行按下面的格式拼接
					let res = {
						categories: ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23'],
						series: [
							{
								name: '光照',
								data: [10, 8, 25, 37, 4, 20, 35, 8, 25, 37, 4, 20, 35, 8, 25, 37, 4, 20, 10, 15, 16, 3, 5, 8]
							}
						]
					};
					this.chartData12 = JSON.parse(JSON.stringify(res));
				}, 500);
				
				// 二氧化碳
				setTimeout(() => {
					//模拟服务器返回数据，如果数据格式和标准格式不同，需自行按下面的格式拼接
					let res = {
						categories: ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23'],
						series: [
							{
								name: '二氧化碳',
								data: [10, 8, 25, 37, 4, 20, 35, 8, 25, 37, 4, 20, 35, 8, 25, 37, 4, 20, 10, 15, 16, 3, 5, 8]
							}
						]
					};
					this.chartData13 = JSON.parse(JSON.stringify(res));
				}, 500);
			}
		},
		getServerData2(){
			{
				// 空气温度
				setTimeout(() => {
					//模拟服务器返回数据，如果数据格式和标准格式不同，需自行按下面的格式拼接
					let res = {
						categories: ['1', '2', '3', '4', '5', '6', '7'],
						series: [
							{
								name: '空气温度',
								data: [35, 8, 25, 37, 4, 20, 35]
							}
						]
					};
					this.chartData6 = JSON.parse(JSON.stringify(res));
				}, 500);
				
				// 空气湿度
				setTimeout(() => {
					//模拟服务器返回数据，如果数据格式和标准格式不同，需自行按下面的格式拼接
					let res = {
						categories: ['1', '2', '3', '4', '5', '6', '7'],
						series: [
							{
								name: '空气湿度',
								data: [20, 8, 25, 37, 4, 20, 35]
							}
						]
					};
					this.chartData7 = JSON.parse(JSON.stringify(res));
				}, 500);
				
				// 大气压
				setTimeout(() => {
					//模拟服务器返回数据，如果数据格式和标准格式不同，需自行按下面的格式拼接
					let res = {
						categories: ['1', '2', '3', '4', '5', '6', '7'],
						series: [
							{
								name: '大气压',
								data: [12, 8, 25, 37, 4, 20, 35]
							}
						]
					};
					this.chartData8 = JSON.parse(JSON.stringify(res));
				}, 500);
				
				// 土壤湿度
				setTimeout(() => {
					//模拟服务器返回数据，如果数据格式和标准格式不同，需自行按下面的格式拼接
					let res = {
						categories: ['1', '2', '3', '4', '5', '6', '7'],
						series: [
							{
								name: '土壤湿度',
								data: [16, 8, 25, 37, 4, 20, 35]
							}
						]
					};
					this.chartData9 = JSON.parse(JSON.stringify(res));
				}, 500);
				
				// 水质PH
				setTimeout(() => {
					//模拟服务器返回数据，如果数据格式和标准格式不同，需自行按下面的格式拼接
					let res = {
						categories: ['1', '2', '3', '4', '5', '6', '7'],
						series: [
							{
								name: '水质PH',
								data: [10, 8, 25, 37, 4, 20, 35]
							}
						]
					};
					this.chartData10 = JSON.parse(JSON.stringify(res));
				}, 500);
				
				// 水位
				setTimeout(() => {
					//模拟服务器返回数据，如果数据格式和标准格式不同，需自行按下面的格式拼接
					let res = {
						categories: ['1', '2', '3', '4', '5', '6', '7'],
						series: [
							{
								name: '水位',
								data: [10, 8, 25, 37, 4, 20, 35]
							}
						]
					};
					this.chartData14 = JSON.parse(JSON.stringify(res));
				}, 500);
				
				
				// 光照
				setTimeout(() => {
					//模拟服务器返回数据，如果数据格式和标准格式不同，需自行按下面的格式拼接
					let res = {
						categories: ['1', '2', '3', '4', '5', '6', '7'],
						series: [
							{
								name: '光照',
								data: [10, 8, 25, 37, 4, 20, 35]
							}
						]
					};
					this.chartData15 = JSON.parse(JSON.stringify(res));
				}, 500);
				
				
				// 二氧化碳
				setTimeout(() => {
					//模拟服务器返回数据，如果数据格式和标准格式不同，需自行按下面的格式拼接
					let res = {
						categories: ['1', '2', '3', '4', '5', '6', '7'],
						series: [
							{
								name: '二氧化碳',
								data: [10, 8, 25, 37, 4, 20, 35]
							}
						]
					};
					this.chartData16 = JSON.parse(JSON.stringify(res));
				}, 500);
				
				
			}
		}
	}
};
</script>

<style>
.navigator{
	display: flex;
}
.navItem{
	background-color: #018577;
	width: 33%;
	margin: 5%;
	text-align: center;
	color: white;
	padding: 1%;
	border-radius: 100rpx;
}
</style>
