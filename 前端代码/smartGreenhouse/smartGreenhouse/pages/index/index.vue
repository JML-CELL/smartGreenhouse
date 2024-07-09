<template>
	<view class="index">
		
		<!--实时天气-->
		<view class="weather">
			<view class="list" @click="toggle('left')">
				<icon class="iconfont icon-liebiao"></icon>
			</view>
			<text>{{city}}</text>
			<text style="margin-left: 2%;">{{weather}}</text>
			<text style="margin-left: 4%;">{{temperature}}°c</text>
		</view>
		<!--地图-->
		<view class="map">
			<scroll-view scroll-y="true">
				<!-- <view><map :latitude="latitude" :longitude="longitude" :scale="1" :markers="markers"></map></view> -->
				<view class="chinaMap">
					<!-- <image src="../../static/images/chinaMap.png" mode=""></image> -->
					<!--ucharts可视化中国地图-->
					<!-- <chinaMap></chinaMap> -->
					<image src="../../static/images/freecompress-ChinaMap.png" mode=""></image>
				</view>
			</scroll-view>
		</view>
 
		<view class="bottom">
			<view class="grid">
				<u-grid :col="3">
					<u-grid-item v-for="(item, index) in gridItem" :key="index" @click="gotoNextPage(index)">
						<icon :class="item.icon" :size="46" style="color: #224620;!important"></icon>
						<view class="grid-text">{{ item.text }}</view>
					</u-grid-item>
				</u-grid>
			</view>

			<!--统计-->
			<view class="total">
				<view class="totalItem" v-for="(item,index) in counts" @click="gotoPage(index)">
					<icon class="iconfont icon-shoucang" style="padding-left: 3%;"></icon>
					<text>{{item.name}}</text>
					<text>( {{item.count}} )</text>
					<icon class="iconfont icon-youjiantou" style="position: absolute;right: 5%;"></icon>
				</view>
			</view>
		</view>


		<!-- 左侧边栏 -->
		<!-- <view class="sidebar-container">
			
			<transition name="slide-fade">
				<view class="sidebar" v-if="leftShow">

					<view class="head">
						智农
					</view>

					<view class="sidebarItem">
						<view class="sidebar-header">
							我的应用便捷操作
						</view>

						<fui-loading type="col" :isFixed="true" text="录音中" v-if="judge"></fui-loading>

						<view class="sidebarItemOne">
							<view class="top_item" v-for="(item,index) in leftList" @click="gotoPage(index)"
								@longpress="startRecord(index)" @touchend="stopRecord(index)">
								<icon :class="item.icon"></icon>
								<view class="texttext">
									{{item.text}}
								</view>
							</view>
						</view>
					</view>

					<view class="sidebarItem">
						<view class="sidebar-header">
							请选择需要管理的温室大棚
						</view>
						<view class="sidebar-menu">
							<view class="sidebar-item" v-for="(item, index) in sidebarItems" :key="index"
								:class="{ 'uni-tab-item-title-active':  switchIndex == index }"
								@click="handleSidebarItemClick(item,index)">
								{{ item }}
							</view>
						</view>
					</view>
				</view>
			</transition>
			
			
		</view> -->
		
		<uni-popup ref="popup" @change="change" background-color="#C3DEA9">
			<view class="sidebar">
				<view class="head">
					智农
				</view>
		
				<view class="sidebarItem">
					<view class="sidebar-header">
						我的应用便捷操作
					</view>
		
					<fui-loading type="col" :isFixed="true" text="录音中" v-if="judge"></fui-loading>
		
					<view class="sidebarItemOne">
						<view class="top_item" v-for="(item,index) in leftList" @click="gotoPage(index)"
							@longpress="startRecord(index)" @touchend="stopRecord(index)">
							<icon :class="item.icon"></icon>
							<view class="texttext">
								{{item.text}}
							</view>
						</view>
					</view>
				</view>
		
				<view class="sidebarItem">
					<view class="sidebar-header">
						请选择需要管理的温室大棚
					</view>
					<view>
						<view class="sidebar-item" v-for="(item, index) in sidebarItems" :key="index"
							:class="{ 'uni-tab-item-title-active':  switchIndex == index }"
							@click="handleSidebarItemClick(item,index,'left')">
							{{ item }}
						</view>
					</view>
				</view>
			</view>
		</uni-popup> 
		

		<!-- 智能助手 -->
		<view class="smartHelp" @click="gotoGpt" v-if="isHidden==false">
			<image src="../../static/images/SmartAssistants.png" mode=""></image>
		</view>

		<!-- 当智能助手收起来的 显示-->
		<view class="smartHelpHidden" @click="isChange" v-if="isHidden==true">
			<image src="../../static/images/leftArrow.png" mode=""></image>
		</view>
	</view>
</template>

<script>
	import chinaMap from '../../components/chinaMap/chinaMap.vue'
	import fuiLoading from "@/uni_modules/firstui-uni/firstui/fui-loading/fui-loading.vue"

	const recorderManager = uni.getRecorderManager();
	const innerAudioContext = uni.createInnerAudioContext();
	innerAudioContext.autoplay = true;


	export default {
		data() {
			return {
				judge: false,
				isHidden: true,
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
				// 八宫格示意图
				gridItem: [{
						icon: 'iconfont icon-jiankong',
						text: '监控'
					},
					{
						icon: 'iconfont icon-fenxi',
						text: '统计分析'
					},
					{
						icon: 'iconfont icon-shebeizongkong',
						text: '设备总控'
					},
					{
						icon: 'iconfont icon-jilu',
						text: '农事记录'
					},
					{
						icon: 'iconfont icon-xiaoshouyuce',
						text: '作物检测'
					},
					{
						icon: 'iconfont icon-changjingshezhi',
						text: '场景定制'
					},
					{
						icon: 'iconfont icon-guanli',
						text: '温室管理'
					},
					{
						icon: 'iconfont icon-yuzhishezhi-copy',
						text: '阈值设置'
					},
					{
						icon: 'iconfont icon-guangai1',
						text: '智能灌溉'
					}
				],
				counts: [{
						name: "大棚总数",
						count: 0
					},
					{
						name: "设备总数",
						count: 9
					},
					{
						name: "传感器总数",
						count: 8
					}
				],
				// 左侧边栏数据列表和bool判定值
				// sidebarItems: [],
				leftList: [{
					icon: 'iconfont icon-zhinengzhushou_zhinengzhushou',
					text: '农智助手'
				}, {
					icon: 'iconfont icon-voice',
					text: '语音助手'
				}],
				switchIndex: 0,
				houseName: '番茄大棚'
			};
		},
		onLoad() {
			// this.sidebarItems = this.$globalData.greenHousesNameList
			this.houseInfo()
			recorderManager.onStop(function(res) {
				
				console.log('recorder stop' + JSON.stringify(res));
				// var p = plus.io.convertLocalFileSystemURL(res.tempFilePath);
				// console.log('file:/' + p)
				
				// 录音文件上传
				uni.uploadFile({
					url: 'http://192.168.9.164:8081/voice/voice', //仅为示例，非真实的接口地址
					// method: 'post',
					filePath: res.tempFilePath,
					name: 'fileImage',
					// formData: {
					// 	'user': 'test'
					// },
					success: (res) => {
						console.log("123" + res.data);
						// that.tempPic = uploadFileRes.data
					},
					fail(err) {
						console.log(err)
					}
				});
			
				console.log("Res.data")
			
			});
		},
		computed: {
			sidebarItems() {
				return this.$globalData.greenHousesNameList
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
				}
			})

			this.totalHouses()

		},
		methods: {
			open: function() {
				// 通过组件定义的ref调用uni-popup方法 ,如果传入参数 ，type 属性将失效 ，仅支持 ['top','left','bottom','right','center']
				this.$refs.popup.open('left');
			},
			toggle: function(type) {
				this.type = type;
				// open 方法传入参数 等同在 uni-popup 组件上绑定 type属性
				this.$refs.popup.open(type);
			},
			change: function(e) {
				console.log('当前模式：' + e.type + ',状态：' + e.show);
			},
			houseInfo: function() {
				let that = this
				that.houseName = this.$globalData.currentGreen
				console.log(that.houseName)
				// 返回item对应的温室大棚图片
				uni.request({
					url: 'http://116.62.199.133:8081/house/houseInfo',
					method: 'get',
					data: {
						name: that.houseName
					},
					success(res) {
						console.log(res.data)
						that.$globalData.currentPic = res.data.housePic
					}
				})
			},
			totalHouses: function() {
				let that = this
				uni.request({
					url: 'http://116.62.199.133:8081/house/allShowHouse',
					method: 'get',
					success(res) {
						console.log(res.data)
						that.$globalData.totalHouse = res.data.length
						that.counts[0].count = res.data.length
					}
				})
			},
			totalDevices: function() {

			},
			totalSensors: function() {

			},
			// 左侧边栏的点击事件
			handleSidebarItemClick(item, index,type) {
				// 处理侧边栏菜单项点击事件
				this.$globalData.currentGreen = item
				this.switchIndex = index
				console.log('点击了菜单项:', this.$globalData.currentGreen);

				this.houseInfo()

				setTimeout(() => {
					this.type = type
					this.$refs.popup.close(type)
				}, 500)
				// console.log('点击了菜单项:', item);
				
			},
			gotoNextPage: function(index) {
				console.log(index)
				if (index == 0) {
					uni.navigateTo({
						url: "/pages/indexPages/videoControl/videoControl"
					})
				} else if (index == 1) {
					uni.navigateTo({
						url: "/pages/indexPages/analysisUpdate/analysisUpdate"
					})
				} else if (index == 2) {
					uni.navigateTo({
						url: "/pages/indexPages/deivecesMethod/deivecesMethod"
					})
				} else if (index == 3) {
					uni.navigateTo({
						url: "/pages/indexPages/record/record"
					})
				} else if (index == 4) {
					uni.navigateTo({
						url: "/pages/indexPages/predictIndex/predictIndex"
					})
				} else if (index == 5) {
					uni.navigateTo({
						url: "/pages/indexPages/actionSet/actionSet"
					})
				} else if (index == 6) {
					uni.navigateTo({
						url: "/pages/indexPages/greenHouses/greenHouses"
					})
				} else if (index == 7){
					uni.navigateTo({
						url: "../indexPages/ThresholdSet/ThresholdSet"
					})
				} else if (index == 8) {
					uni.navigateTo({
						url: "../indexPages/smartIrrigation/smartIrrigation"
					})
				}
			},
			gotoPage: function(index) {
				if (index == 0) {
					uni.navigateTo({
						url: '/pages/indexPages/greenHouses/greenHouses'
					})
				} else if (index == 1) {
					uni.navigateTo({
						url: '/pages/indexPages/deivecesMethod/deivecesMethod'
					})
				} else if (index == 2) {
					uni.navigateTo({
						url: '/pages/indexPages/ThresholdSet/ThresholdSet'
					})
				}
			},
			gotoGpt: function() {
				uni.switchTab({
					url: '/pages/gptChat/gptChat'
				})
			},
			isChange: function() {
				this.isHidden = false
				setTimeout(() => {
					this.isHidden = true
				}, 1000)
			},
			// 以下是左侧边栏，特别是录音管理器的操作
			gotoPage: function(index) {
				if (index == 0) {
					this.leftShow = !this.leftShow
					uni.switchTab({
						url: '/pages/gptChat/gptChat'
					})
				}
			},
			startRecord: function(index) {
				if (index == 1) {
					// 还有一个判断  圈圈 出不出来
					this.judge = true
					recorderManager.start({
						format: "mp3",
						sampleRate: 8000
					});
				}
			},
			stopRecord: function(index) {
				if (index == 1) {
						this.judge = false
						recorderManager.stop();
						
						

				}
			}
		},
		components: {
			chinaMap,
			fuiLoading
		}
	};
</script>

<style>
	page {
		position: relative;
	}
	
	.chinaMap image {
		width: 100%;
	}

	.weather {
		/* position: absolute;
		top: 2%;
		left: 5%;
		font-family: '华文行楷';
		
		width: 100%; */
		margin: 11% 3%;
		font-size: 20px;
		font-weight: bold;
		display: flex;
		align-items: center;
		color: #24491D;

	}

	.list {
		margin-right: 5%;
		background-color: #BCD1A8;
		padding: 1% 2%;
		display: flex;
		opacity: 0.8;
		border-radius: 5rpx;
	}

	.iconfont {
		color: #24491D;
		font-size: 48rpx;
		/* margin-right: 5%; */
	}

	.bottom {
		background-color: white;
		padding: 5% 0;
		border-radius: 50rpx 50rpx 0 0;
		/* box-shadow: rgba(14, 30, 37, 0.12) 0px 2px 4px 0px, rgba(14, 30, 37, 0.32) 0px 2px 16px 0px; */
		box-shadow: rgba(0, 0, 0, 0.15) 0px 5px 15px 0px;

	}

	.grid-text {
		font-size: 32rpx;
		margin-top: 4rpx;
		color: $u-type-info;
	}

	.totalItem {
		background-color: #7DA47C;
		color: #34482F;
		font-weight: bold;
		border-radius: 50rpx;
		margin: 5%;
		height: 100rpx;
		line-height: 100rpx;
		font-size: 18px;
		/* font-family: '华文行楷'; */
		position: relative;
		box-shadow: rgba(60, 64, 67, 0.3) 0px 1px 2px 0px, rgba(60, 64, 67, 0.15) 0px 1px 3px 1px;
	}

	.totalItem icon {
		font-size: 42rpx;
		margin: 0 2%;

	}

	.smartHelp image {

		width: 240rpx;
		height: 240rpx;

	}

	.smartHelp {
		position: fixed;
		top: 37%;
		right: 0;


		/*   一点动画效果  * /  
		transition: all 0.3s ease;
		transform: translateX(0%) !important;

		/* z-index: 9999 !important; */
	}

	.smartHelpHidden {
		position: fixed;
		top: 43%;
		right: -4%;
		z-index: 999;

		transition: all 0.3s ease;
		transform: translateX(0%);
	}

	.smartHelpHidden image {
		width: 100rpx;
		height: 100rpx;
	}

	/* 左侧边栏的样式 */
	.sidebar-container {
		display: flex;
		position: fixed;
		top: 0;
		transition: all 0.5s ease;
		box-shadow: rgba(0, 0, 0, 0.15) 1.95px 1.95px 2.6px;
	}

	.sidebarItem {
		background-color: white;
		margin: 7%;
		border-radius: 25rpx;
		box-shadow: rgba(0, 0, 0, 0.15) 1.95px 1.95px 2.6px;
	}

	.head {
		text-align: center;
		padding: 7% 0;
		font-weight: bold;
		padding-top: 21%;
	}

	.sidebarItem .sidebarItemOne {
		display: flex;
		justify-content: space-between;
		margin: 3% 13%;
	}

	.sidebarItemOne .top_item {
		display: flex;
		flex-direction: column;
		align-items: center;
		padding: 7% 0;
	}

	.sidebar {
		background-color: #C3DEA9;
		/* width: 200px; */
		height: 100vh;
		/* background-color: white; */
	}

	.sidebar-header {
		padding: 20px;
		font-weight: bold;
		border-bottom: 1px solid #224821;
		color: #224821;
	}

	.sidebar-menu {
		margin-top: 20px;
	}

	.sidebar-item {
		padding: 10px;
		cursor: pointer;
		transition: background-color 0.3s;
	}

	.sidebar-item:hover {
		background-color: #8AC272;
	}

	/* 进入和离开动画 */
	.slide-fade-enter-active {
		transition: all 0.3s ease;
	}

	.slide-fade-leave-active {
		transition: all 0.3s cubic-bezier(1, 0.5, 0.8, 1);
	}

	.slide-fade-enter,
	.slide-fade-leave-to {
		transform: translateX(-100%);
		opacity: 0;
	}

	.uni-tab-item-title-active {
		color: #D6EECE;
		font-weight: bold;
	}
</style>