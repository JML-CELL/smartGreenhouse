<template>
	<view class="register">
		<view class="part_one">
			<!-- <image src="../../static/images/智能温室.png" mode=""></image> -->
			<text>绿源智控</text>
		</view>
		<view class="part_two">
			<view class="form">
				<view class="item">
					<!-- <text class="label">用户名</text> -->
					<image src="../../static/icons/username.png" mode="" class="label"></image>
					<input type="text" v-model="username" placeholder="请输入用户名">
				</view>
				<view class="item">
					<!-- <text class="label">密码</text> -->
					<image src="../../static/icons/password.png" mode="" class="label"></image>
					<input type="password" v-model="password" placeholder="请输入密码">
				</view>
				<view class="item">
					<!-- <text class="label">邮箱</text> -->
					<image src="../../static/icons/Email.png" mode="" class="label"></image>
					<input type="text" v-model="qqMail" placeholder="请输入邮箱">
				</view>

				<button @click="register" class="is-hover">注册</button>
			</view>
		</view>

		<!-- <view class="part_three" style="color: darkgray; margin-left: 5%;" >
			返回登录
		</view> -->
	</view>
</template>

<script>
	export default {
		data() {
			return {
				username: '',
				password: '',
				qqMail: '',
			}
		},
		methods: {
			register: function() {

				let that = this

				uni.request({
					url: 'http://116.62.199.133:8081/user/enRoll',
					method: 'get',
					data: {
						password: that.password,
						qqMail: that.qqMail,
						username: that.username
					},
					success(res) {
						console.log(res)
						if (res.data == 1) {

							// 将用户名，qq邮箱存储在本地
							uni.setStorageSync("username", that.username)

							uni.showToast({
								title: '注册成功',
								icon: 'success'
							})
							setTimeout(() => {
								uni.navigateTo({
									url: '/pages/login/login'
								})
							}, 1000)
						} else {
							uni.showToast({
								title: '注册失败',
								icon: 'error'
							})
						}
					}
				})
			}
		}
	}
</script>

<style>
	/* page { */
		/* background-color: #F2F6FF; */
		/* background-image: url('../../static/images/智能温室背景图.png'); */
		/* background-size: cover; */
		/* background-repeat: no-repeat;
		width: 100%;
		height: 100%;
		box-sizing: border-box;
		position: relative; */
	/* } */

	.part_one {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		padding: 30% 30% 30% 15%;
		width: 70%;
		
	}

	/* .part_one image {
		height: 300rpx;
		width: 300rpx;
		margin-top: 200rpx;
		margin-left: auto;
		margin-right: auto;
		margin-bottom: 50rpx;
	} */
	page{
		background-image: url("/static/images/startBg.jpg");
		background-repeat: no-repeat;
		background-size: cover;
		background-color: rgba(0, 0, 0, 0.1); /* 设置背景图透明度为50% */
	}
	
	.part_one text {
		font-size: 64rpx;
		font-weight: bold;
		font-family: '楷体';
		color: white;
		/* color: white; */
	}

	.is-hover {
		/* color: rgba(255, 255, 255, 0.6);
		background-color: #92C47D;
		border-color: #92C47D;

		color: #ffffff;
		borderColor: #92C47D; */
		background-color: #C4DFAA;
		color: #4A6C4C;
		font-weight: bold;
		width: 60%;
		margin-top: 17%;
		margin-bottom: 10%;
		box-shadow: rgba(60, 64, 67, 0.1) 0px 1px 2px 0px, rgba(60, 64, 67, 0.15) 0px 1px 3px 1px;
	}

	.item {
		display: flex;
		padding: 7%;
		align-items: center;
	}

	.label {
		margin-right: 5%;
		width: 60rpx;
		height: 60rpx
	}
	/* part_two 部分样式 */
	.part_two .form {
		position: fixed;
		background-color: white;
		width: 100%;
		bottom: 0;
		border-radius: 50rpx 50rpx 0 0;
		box-shadow: rgba(17, 17, 26, 0.1) 0px 4px 16px, rgba(17, 17, 26, 0.05) 0px 8px 32px;
	}
</style>