<template>
	<view class="smartIrrigation">
		<view class="top">
			<view class="image">
				<image src="../../../static/images/waterAndFertilizer.png" mode=""></image>
			</view>
			<view class="card">
				<!--item_one-->
				<view class="cardItem1">
					<view class="left">
						<image src="../../../static/icons/SoilMoisture.png" mode=""></image>
					</view>
					<view class="right">
						<view class="part_one">
							土壤湿度
						</view>
						<view class="part_two">
							{{humidity}}hPa
						</view>
					</view>
				</view>

				<!--item_two-->
				<view class="cardItem2">
					<view class="left">
						<image src="../../../static/icons/DateSelection.png" mode=""></image>
					</view>
					<view class="right">
						<view class="part_one">
							日期
						</view>
						<view class="part_two">
							{{(new Date()).toJSON().slice(0, 10).replace('T', ' ')}}
						</view>
					</view>
				</view>

				<!--item_three-->
				<view class="cardItem3">
					<view class="left">
						<icon class="iconfont icon-zengjiatianjiajiahao"></icon>
						<!-- <image src="../../../static/icons/SoilMoisture.png" mode=""></image> -->
					</view>
					<view class="right">
						<view class="part_one">
							添加任务
						</view>
					</view>
				</view>
			</view>
		</view>
		<view class="bottom">
			<view class="navigator">
				<view class="navigatorItem" v-for="(item,index) in navigators">
					{{item}}
				</view>
			</view>
			<view class="details">
				<view class="detailItem" v-for="(item,index) in Details">
					<view class="task">{{item.task}}</view>
					<view class="water">{{item.water}}L</view>
					<view class="fertil">{{item.fertil}}L</view>
					<view class="startT">{{item.startT}}</view>
					<view class="switch"><fui-switch @change="change(item.state,index)" :scaleRatio="0.8"
							:checked="item.state==true"></fui-switch></view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	// (new Date()).toJSON().slice(0, 19).replace('T', ' ')
	import fuiSwitch from "@/uni_modules/firstui-uni/firstui/fui-switch/fui-switch.vue"
	export default {
		data() {
			return {
				navigators: ["任务", "水量", "肥量", "开始时间", "开关"],
				Details: Array,
				humidity: "",
			}
		},
		methods: {
			change: function(state, index) {
				console.log(state)
				this.Details[index].state = !this.Details[index].state
			}
		},
		onLoad() {
			
			this.humidity = this.$globalData.growEnvList[2].value
			
			uni.showLoading({
				title: "加载中"
			})
			setTimeout(() => {
				uni.hideLoading()
			}, 800)

			setTimeout(() => {
				uni.showModal({
					title: "已根据作物生长情况为你推荐设置灌溉计划",
					confirmText: "确定",
					showCancel: false,
				})
				let Details = [{
					task: "1",
					water: "1",
					fertil: "0.2",
					startT: "13:30",
					state: true
				}, {
					task: "2",
					water: "2",
					fertil: "0.3",
					startT: "20:00",
					state: true
				}]
				this.Details = Details
			},500)
			
			// setTimeout(()=>{
			// 	// 设置3秒后根据灌溉情况改变用量的提示信息
			// 	uni.showModal({
			// 		title: "根据作物灌溉情况为你调整用水量为1.5l",
			// 		confirmText: "确定",
			// 		showCancel: false,
			// 	})
			// 	this.Details[0].water = "0.5"
				
				// 过1秒状态为false;
				// setTimeout(()=>{
				// 	this.Details[0].state = false
				// 	uni.showToast({
				// 		title: "任务完成",
				// 		icon: "success"
				// 	})
				// },5000)
			// },4000)
			
			
			
		},
		components: {
			fuiSwitch
		}
	}
</script>

<style>
	.smartIrrigation {
		position: relative;
	}

	.smartIrrigation .top .image image {
		width: 100%;
	}

	.smartIrrigation .top .card {
		overflow: hidden;
	}

	.smartIrrigation .top .card .cardItem1,
	.smartIrrigation .top .card .cardItem2,
	.smartIrrigation .top .card .cardItem3 {
		position: absolute;
		left: 0;
		bottom: -3%;
		background-color: #C4DFAC;
		z-index: 999 !important;
		display: flex;
		border-radius: 15rpx;
		padding-left: 1%;
		box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px;
	}

	.smartIrrigation .top .card .cardItem1 {
		margin-left: 3%;
		width: 27%;
	}

	.smartIrrigation .top .card .cardItem2 {
		margin-left: 35%;
		width: 35%;
	}

	.smartIrrigation .top .card .cardItem3 {
		margin-left: 75%;
	}

	.card .cardItem1 .left,
	.card .cardItem2 .left,
	.card .cardItem3 .left {
		display: flex;
		align-items: center;
	}

	.smartIrrigation .top .card .cardItem1 image,
	.smartIrrigation .top .card .cardItem2 image,
	.smartIrrigation .top .card .cardItem3 image {
		width: 50rpx;
		height: 50rpx;
	}

	.card .cardItem1 .right,
	.card .cardItem2 .right,
	.card .cardItem3 .right {
		text-align: center;
		background-color: white;
		width: 100%;
		margin-left: 5%;
		border-radius: 0 15rpx 15rpx 0;
	}
	
	.part_one{
		font-weight: bold;
	}

	.smartIrrigation .bottom {
		background-color: white;
		height: 65vh;
		width: 100%;
		position: fixed;
		bottom: 0;
	}

	.smartIrrigation .bottom .navigator {
		display: flex;
		justify-content: space-between;
		padding: 7% 3% 0 3%;
		font-weight: bold;
	}

	.detailItem {
		display: flex;
		justify-content: space-between;
		padding: 3% 3% 3% 7%;
	}
</style>