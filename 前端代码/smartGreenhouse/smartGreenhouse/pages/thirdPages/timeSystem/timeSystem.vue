<template>
	<view class="timeSystem">
		<view class="timeBox">
			<view class="timeItem">
				<view class="part_one">
					<image src="../../../static/images/SmartGreenhouse.png" mode="" style="width: 300rpx;height: 400rpx;"></image>
				</view>
				<view class="grid" style="width: 50%;margin-left: 5%;">
					<u-grid :col="2">
						<u-grid-item>
							<view class="gridItem">
								<view style="padding-bottom:20%;">开始时间</view>
								<view style="font-size: 13px;">22:14</view>
							</view>
							<view class="gridItem">
								<view style="padding-bottom:20%;">结束时间</view>
								<view style="font-size: 13px;">22:15</view>
							</view>
						</u-grid-item>
						<u-grid-item>
							<view class="gridItem">
								<view style="padding-bottom:20%;">运行周期</view>
								<view style="font-size: 13px;">不重复</view>
							</view>
							<view class="gridItem">
								<view style="padding-bottom:20%;">开关</view>
								<switch @change="onChange" style="transform: scale(0.8,0.8);float: left;margin-top: -12%;" />
							</view>
						</u-grid-item>
					</u-grid>
				</view>
			</view>
			
			<!--添加定时任务-->
			<view class="addPlan" @click="addTimeTask">
				<icon class="iconfont icon-zengjiatianjiajiahao" style="font-size: 22px;padding-right: 2%;"></icon>
				<text>添加定时任务</text>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			}
		},
		methods: {
			onChange:function(e){
				console.log(e.detail.value)
			},
			addTimeTask:function(){
				uni.navigateTo({
					url: '/pages/thirdPages/addTimeStop/addTimeStop'
				})
			}
		}
	}
</script>

<style>
.timeItem{
	display: flex;
	align-items: center;
	background-color: azure;
	padding: 3%;
	margin: 2%;
	border-radius: 15rpx;
}
.gridItem{
	width: 80%;
	height: 150rpx;
	margin: 10%;
	padding: 6%;
	display: flex;
	flex-direction: column;
	text-align: center;
	background-color: #018577;
	border-radius: 15rpx;
	color: white;
}
.addPlan{
	display: flex;
	justify-content: center;
	align-items: center;
	font-size: 20px;
	margin-top: 5%;
}
</style>
