<template>
	<view class="devices">
		
		<!-- 下方区域 -->
		<view class="tabs">
		
			<!--左边scroll部分-->
			<!--标题区：设备总控 -->
			<!-- <view class="head">
				<icon class="iconfont icon-zuojiantou" @click="goBack" style="font-size: 20px;margin-left: 3%;"></icon>
				<view class="context">
					设备总控
				</view>
				<view class="switchpart">
					<picker mode="selector" :range="greenHouseList" @change="pickGreen($event)" :value="indexGreen">
						<icon class="iconfont icon-xialakuangxiala" style="color: #018577;"></icon>
						<text>{{greenHouseList[indexGreen]}}</text>
					</picker>
				</view>
			</view> -->
			
			<!--顶部scroll部分-->
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
			
			<!--LED灯-->
			<LEDLight :scrollIndex="scrollIndex" class="centre"></LEDLight>
			<!--空调-->
			<airConditioner :scrollIndex="scrollIndex" class="centre"></airConditioner>
			<!--卷风机-->
			<RollFan :scrollIndex="scrollIndex" class="centre"></RollFan>
			<!--加湿器-->
			<Humidifier :scrollIndex="scrollIndex" class="centre"></Humidifier>
			<!--音乐播放器-->
			<MusicPlayer :scrollIndex="scrollIndex" class="centre"></MusicPlayer>
			<!--烟雾报警器-->
			<smokeAlarms :scrollIndex="scrollIndex" class="centre"></smokeAlarms>
			<!--农药喷洒器-->
			<pesticideSprayers :scrollIndex="scrollIndex" class="centre"></pesticideSprayers>
			<!--滴灌设备-->
			<dripIrrigation :scrollIndex="scrollIndex" class="centre"></dripIrrigation>
			<!--电动窗帘-->
			<motorizedCurtains :scrollIndex="scrollIndex" class="centre"></motorizedCurtains>
			
			<!--button区-->
			<view class="button">
				<view class="add" @click="addDev">
					<icon class="iconfont icon-zengjiatianjiajiahao"></icon>
				</view>
			</view>
			
		</view>
	</view>
</template>

<script>
	import LEDLight from '../../../components/LEDLight/LEDLight.vue'
	import airConditioner from '../../../components/airConditioner/airConditioner.vue'
	import RollFan from '../../../components/RollFan/RollFan.vue'
	import Humidifier from '../../../components/Humidifier/Humidifier.vue'
	import MusicPlayer from '../../../components/musicPlayer/musicPlayer.vue'
	import smokeAlarms from '../../../components/smokeAlarms/smokeAlarms.vue'
	import pesticideSprayers from '../../../components/pesticideSprayers/pesticideSprayers.vue'
	import dripIrrigation from '../../../components/dripIrrigation/dripIrrigation.vue'
	import motorizedCurtains from '../../../components/motorizedCurtains/motorizedCurtains.vue'
	export default {
		data() {
			return {
				scrollIndex: 0,

				tabBars: [{
						name: 'LED灯',
						id: 'LEDLight'
					},
					{
						name: '空调',
						id: 'aircondition'
					},
					{
						name: '卷风机',
						id: 'RollFan'
					},
					{
						name: '加湿器',
						id: 'Humidifier'
					},
					{
						name: '音乐播放器',
						id: 'musicPlayer'
					},
					{
						name: '烟雾报警器',
						id: 'smokeAlarms'
					},
					{
						name: '农药喷洒器',
						id: 'pesticideSprayers'
					},
					{
						name: '滴灌设备',
						id: 'dripIrrigation'
					},
					{
						name: '电动窗帘',
						id: 'motorizedCurtains'
					}
				],

				// greenHouseList: ['1号大棚','2号大棚','3号大棚'],
				// indexGreen: 0,
			};
		},
		components: {
			LEDLight,
			airConditioner,
			RollFan,
			Humidifier,
			MusicPlayer,
			smokeAlarms,
			pesticideSprayers,
			dripIrrigation,
			motorizedCurtains
		},
		onLoad() {
			
		},
		methods: {
			tabtap(index) {
				this.scrollIndex = index;
			},
			/* 左侧导航点击 */
			leftTap(e) {
				let index = e.currentTarget.dataset.index;
				this.isMainScroll = false;
				this.leftIndex = Number(index);
				this.scrollInto = `item-${index}`;
			},
			addDev:function(){
				uni.navigateTo({
					url: "/pages/thirdPages/addDevandSensor/addDevandSensor?name=设备"
				})
			}
			
			
			// pickGreen(e){
			// 	this.indexGreen = e.target.value
			// },
			// goBack:function(){
			// 	uni.navigateBack()
			// }
		}
	};
</script>

<style>
	page{
		background-image: url("/static/images/devicesBg.png");
		width: 100%;
		height: 100vh;
		background-repeat: no-repeat;
		background-size: contain;
	}
	.head{
		display: flex;
		align-items: center;
		padding-top: 10%;
		padding-bottom: 3%;
		font-size: 18px;
		background-color: #F8F8F8;
	}
	
	.head .context{
		margin-left: 28%;
	}
	.head .switchpart{
		margin-left: 15%;
	}
	.tabs {
		flex: 1;
		flex-direction: column;
		overflow: hidden;
		background-color: #ffffff;
		/* opacity: 0.9; */
		/* height: 100vh; */
		position: fixed;
		bottom: 0;
		height: 60vh;
		border-radius: 50rpx 50rpx 0  0 ;
		box-shadow: rgba(0, 0, 0, 0.16) 0px 10px 36px 0px, rgba(0, 0, 0, 0.06) 0px 0px 0px 1px;
		margin-top: 10%;
	}

	.other {
		margin-left: 15%;
		padding-left: 3%;
	}

	.scroll-h {
		width: 750upx;
		height: 80upx;
		flex-direction: row;

		white-space: nowrap;
		padding-top: 3%;
		

		/* flex-wrap: nowrap; */
		/* border-color: #cccccc;
    border-bottom-style: solid;
    border-bottom-width: 1px; */
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
		color: #7DA47C;
		font-weight: bold;
	}

	.scroll_left {
		position: absolute;
		top: 0;
		background-color: #EEEEEE;
		padding: 2%;
	}

	.button {
		position: fixed;
		bottom: 0;
		width: 100%;
		background-color: #E7EEDE;
		box-shadow: rgba(231, 238, 222, 0.5) 0px 1px 2px 0px, rgba(60, 64, 67, 0.15) 0px 1px 3px 1px;
	}

	.button .add {
		/* background-color: #018577; */
		text-align: center;
		padding: 3%;
		color: #234721;
		border-radius: 50rpx;
	}
	
	.button .add .iconfont{
		background-color: #C4DFAC;
		padding: 3%;
		border-radius: 50rpx;
		font-size: 20px;
	}

	/* .confirmButton{
	width: 20%;
	background-color: #018577;
	color: white;
	margin-top: 40%;
	font-size: 16px;
} */

</style>