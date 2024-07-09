<template>
	<view class="videoControl">
		<view class="videos">
			<view class="videoItem">
				<!-- <video src="" controls style="width: 100%;"></video> -->
				<view class="gradient-border" id="box">
					<view class="head">{{currentGreen}}</view>
				</view>
				<image src="http://172.16.56.211/mjpeg/1" mode=""></image>
				<!-- <image src="http://172.16.56.211/mjpeg/1" mode=""></image> -->
				
			</view>
		</view>
		
		<view class="videoBack" @click="videoBack">
			<icon class="iconfont icon-dianbohuifang"></icon>
			<text>录像回放</text>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				currentGreen: "番茄大棚",
			}
		},
		onLoad() {
			this.currentGreen = this.$globalData.currentGreen
		},
		methods: {
			videoBack:function(){
				uni.navigateTo({
					url: "/pages/thirdPages/videoBack/videoBack"
				})
			}
		}
	}
</script>

<style>
	page{
		/* background-image: url("/static/images/videoBg.png"); */
		width: 100%;
		height: 100%;
		box-sizing: border-box;
		background-repeat: no-repeat;
		background-size: cover;
	}
	.videoItem {
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		
	}
	.videoItem image{
		width: 100%;
		margin-top: 25%;
		
	}
	
	/* 录像回放 */
	.videoBack{
		background-color: #C4DFAA;
		color: #4A6C4C;
		position: fixed;
		bottom: 15%;
		left: 20%;
		padding: 3%;
		font-size: 18px;
		width: 60%;
		text-align: center;
		border-radius: 50rpx;
		font-weight: bold;
		box-shadow: rgba(60, 64, 67, 0.3) 0px 1px 2px 0px, rgba(60, 64, 67, 0.15) 0px 1px 3px 1px;
	}
	.videoBack .iconfont{
		margin-right: 3%;
	}
	
	/* 边框样式 */
	@import url('https://fonts.googleapis.com/css?family=Raleway:200');
	
	#box {
	  display: flex;
	  align-items: center;
	  justify-content: center;
	  margin-top: 5%;
	  color: white;
	  font-size: 22px;
	  padding: 3%;
	  z-index: 2;
	}
	
	.head{
		background-color: #C4DFAA;
		color: #4A6C4C;
		padding: 3%;
		border-radius: 50rpx;
		box-shadow: rgba(60, 64, 67, 0.3) 0px 1px 2px 0px, rgba(60, 64, 67, 0.15) 0px 1px 3px 1px;
	}
	
	.gradient-border{
		width: 100%;
	}
	
	/* .gradient-border {
	
	  --borderWidth: 3px;
	  background: #1D1F20;
	  position: relative;
	  border-radius: var(--borderWidth);
	}
	.gradient-border:after {
	  content: '';
	  position: absolute;
	  top: calc(-1 * var(--borderWidth));
	  left: calc(-1 * var(--borderWidth));
	  height: calc(100% + var(--borderWidth) * 2);
	  width: calc(100% + var(--borderWidth) * 2);
	  background: linear-gradient(60deg, #f79533, #f37055, #ef4e7b, #a166ab, #5073b8, #1098ad, #07b39b, #6fba82);
	  border-radius: calc(2 * var(--borderWidth));
	  z-index: -1;
	  animation: animatedgradient 3s ease alternate infinite;
	  background-size: 300% 300%;
	}
	 */
	
	@keyframes animatedgradient {
		0% {
			background-position: 0% 50%;
		}
		50% {
			background-position: 100% 50%;
		}
		100% {
			background-position: 0% 50%;
		}
	}
</style>