<template>
	<view class="start">
		<view class="part_one">
			<image src="/static/logo.png" mode=""></image>
			<text>绿源智控</text>
		</view>
		
		<view class="select">
			<button type="default" class="login" @click="login">登录</button>
			<button type="default" class="register" @click="register">注册</button>
		</view>
		
		<view class="part_three">copy-right@Rmrf/</view>
		
		
	</view>

</template>

<script>
	export default {
		data() {
			return {

			}
		},
		onLoad() {
			this.getDatas()
		},
		methods: {
			login:function(){
				uni.navigateTo({
					url: '/pages/login/login'
				})
			},
			register:function(){
				uni.navigateTo({
					url: '/pages/register/register'
				})
			},
			
			getDatas:function(){
				let that = this
				// 定时调用获取oneNet平台监控到的环境数据
				uni.request({
					url: "http://116.62.199.133:8081/oneNet/getOnenetLatestdata",
					method: 'get',
					success(res) {
						console.log(JSON.stringify(res.data.data))
						
						let result = JSON.stringify(res.data.data)
						
						// 空气湿度
						that.$globalData.growEnvList[1].value = JSON.stringify(res.data.data[0].value).replace(/"/g, '')
						// 大气压
						that.$globalData.growEnvList[7].value = JSON.stringify(res.data.data[2].value).replace(/"/g, '')
						// 二氧化碳浓度
						that.$globalData.growEnvList[4].value = JSON.stringify(res.data.data[4].value).replace(/"/g, '')
						// 光照  7
						that.$globalData.growEnvList[3].value = JSON.stringify(res.data.data[7].value).replace(/"/g, '')
						// smoke  9
						
						// 土壤湿度  10
						that.$globalData.growEnvList[2].value = JSON.stringify(res.data.data[10].value).replace(/"/g, '')
						// 温度  12
						that.$globalData.growEnvList[0].value = JSON.stringify(res.data.data[12].value).replace(/"/g, '')
						// 水位  14
						that.$globalData.growEnvList[6].value = JSON.stringify(res.data.data[14].value).replace(/"/g, '')
						// 水质PH 16
						that.$globalData.growEnvList[5].value = JSON.stringify(res.data.data[16].value).replace(/"/g, '')
						
					}
				})
			},
			
		}
	}
</script>

<style>
	/* page{
		background-image: url("../../static/images/SmartGreenhouseBg.png");
		background-size: cover;
		background-repeat: no-repeat;
		width: 100%;
		height: 100%;
		box-sizing: border-box;
		position: relative;
	} */
	page{
		background-image: url("/static/images/startBg.jpg");
		background-repeat: no-repeat;
		background-size: cover;
		background-color: rgba(0, 0, 0, 0.1); /* 设置背景图透明度为50% */
	}

	.part_one {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		
	}

	.part_one image {
		height: 300rpx;
		width: 300rpx;
		margin-top: 20%;
		margin-left: auto;
		margin-right: auto;
		margin-bottom: 50rpx;
	}

	.part_one text {
		font-size: 64rpx;
		font-weight: bold;
		font-family: '楷体';
		color: white;
	}
	
	.select{
		position: absolute;
		bottom: 17%;
		left: 20%;
		width: 60%;
	}
	
	.select .login{
		background-color: #C4DFAA;
		color: #4A6C4C;
		font-weight: bold;
		box-shadow: rgba(60, 64, 67, 0.1) 0px 1px 2px 0px, rgba(60, 64, 67, 0.15) 0px 1px 3px 1px;
	}
	
	.select .register{
		margin-top: 13%;
		background-color: #4A6C4C;
		color: #C4DFAA;
		font-weight: bold;
	}
	
	.part_three {
		position: absolute;
		bottom: 3%;
		left: 31%;
		color: #214720;
	}
</style>