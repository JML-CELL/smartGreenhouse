<template>
	<view class="forecastWizard">
		<view class="prompt">
			<view class="analysis">检</view>
			<view class="analysis">测</view>
			<view class="analysis">分</view>
			<view class="analysis">析</view>
		</view>

		<view class="predictIndex">

			<!--growingEnvironment-->
			<view class="predictItem" @click="goOne">
				<view class="circle">
					<image src="/static/icons/sicknessPet.png" mode=""></image>
				</view>
				<view class="centre">
					病虫害检测
				</view>
				<icon class="iconfont icon-youbian"></icon>
			</view>
			<!--identifyResults-->
			<view class="predictItem" @click="goTwo">
				<view class="circle">
					<image src="/static/icons/Maturity.png" mode=""></image>
				</view>
				<view class="centre">
					生长阶段检测
				</view>
				<icon class="iconfont icon-youbian"></icon>
			</view>
			<!--thresholdRecommendation-->
			<view class="predictItem" @click="goThree">
				<view class="circle">
					<image src="/static/icons/weed.png" mode=""></image>
				</view>
				<view class="centre">
					杂草检测
				</view>
				<icon class="iconfont icon-youbian"></icon>
			</view>

		</view>
	</view>
</template>

<script>
	// import fuiTag from "@/uni_modules/firstui-uni/firstui/fui-tag/fui-tag.vue"
	export default {
		data() {
			return {

			};
		},
		methods: {
			goOne: function() {
				uni.navigateTo({
					url: '/pages/indexPages/predict/predict'
				})
			},
			goTwo: function() {
				uni.navigateTo({
					url: '/pages/indexPages/predictRipe/predictRipe'
				})
			},
			goThree: function() {
				uni.navigateTo({
					url: '/pages/indexPages/pridictWeed/pridictWeed'
				})
			}
		},
		onLoad() {

		},
	};
</script>

<style>
	page {
		background-image: url("/static/images/predictIndex.png");
		background-color: rgba(0, 0, 0, 0.1); /* 设置背景图透明度为50% */
		width: 100%;
		height: 100vh;
		background-repeat: no-repeat;
		background-size: cover;
	}

	.prompt {
		display: flex;
		flex-direction: column;
		font-weight: bold;
		font-size: 36px;
		color: white;
		
		position: absolute;
		top: 7%;
		left: 45%;
	}
	
	.analysis{
		margin-top: 80%;
	}

	.predictIndex {
		position: fixed;
		bottom: 0;
		width: 90%;
		margin-left: 5%;
	}

	.predictItem {
		/* background-color: white; */
		background-color: #ABD0A7;
		padding: 5%;
		text-align: center;
		font-size: 20px;
		border-radius: 15rpx;
		font-weight: bold;
		margin: 5% 0;

		display: flex;
		align-items: center;
		justify-content: space-between;
		color: #214720;

	}

	.predictItem .circle {
		background-color: white;
		padding: 3%;
		border-radius: 50%;
		box-shadow: rgba(60, 64, 67, 0.1) 0px 1px 2px 0px, rgba(60, 64, 67, 0.15) 0px 1px 3px 1px;
	}

	.circle image {
		width: 60rpx;
		height: 60rpx;
	}

	.predictItem .iconfont {
		font-size: 28px;
	}
</style>