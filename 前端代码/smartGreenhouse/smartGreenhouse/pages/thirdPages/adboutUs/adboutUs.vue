<template>
	<view class="aboutUs">
		<!--名称区域-->
		<view id="qrCodeView">
			<image src="/static/logo.png"></image>
			<text class="kshd">绿源智控</text>
			<view class="dqbb">当前版本2024.2.20</view>
		</view>

		<!--开发者信息区域-->
		<view id="developerView">
			<view class="developerItemView">
				<view>开发团队: 国家富强</view>
			</view>
			<view class="developerItemView">
				<view>邮箱: 2030175625@qq.com</view>
			</view>
			<view class="developerItemView">
				<view>QQ: 2030175625</view>
			</view>
			<view class="developerItemView">
				<view>微信: 2030175625</view>
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

		}
	}
</script>

<style>
	/* pages/aboutus/aboutus.wxss */
	.aboutUs{
		color: #234721;
	}
	
	.kshd {
		font-size: 50rpx;
		font-weight: bold;
	}
	
	.dqbb{
		margin-top: 1%;
	}

	#qrCodeView {
		width: 300rpx;
		margin: 30rpx auto 60rpx;
		text-align: center;
		font-size: 26rpx;

	}

	#qrCodeView>image {
		width: 300rpx;
		height: 300rpx;
	}

	/*开发者信息区域*/
	#developerView {
		position: absolute;
		width: 100vw;
		/* padding:210rpx 40rpx; */
		bottom: 50px
	}

	.developerItemView {
		font-size: 26rpx;
		padding-bottom: 10rpx;
		text-align: center;
	}


</style>