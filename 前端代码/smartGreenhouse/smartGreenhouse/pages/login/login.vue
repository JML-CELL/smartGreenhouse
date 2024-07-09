<template>
	<view class="login">
		
		<!-- <goBackIcon></goBackIcon> -->
		
		<view class="part_one">
			<!-- <image src="../../static/images/SmartGreenhouse.png" mode=""></image> -->
			<text>绿源智控</text>
		</view>
		<view class="part_two">
			<view class="form">
				<view class="item">
					<!-- <text class="label">邮箱</text> -->
					<image src="../../static/icons/Email.png" mode="" class="label"></image>
					<input type="text" v-model="username" placeholder="请输入邮箱">
				</view>
				<view class="item">
					<!-- <text class="label">密码</text> -->
					<image src="../../static/icons/password.png" mode="" class="label"></image>
					<input type="password" v-model="password" placeholder="请输入密码">
				</view>
				<button @click="submit" class="is-hover">登录</button>
			</view>
			<view class="register">无账号? <view style="color: #214720; display: inline; font-weight: bold;" @click="Register">立即注册</view>
			</view>
		</view>

		<!-- <view class="uni-form-item uni-column">
			<view class="uni-input-wrapper">
				<input class="uni-input" placeholder="请输入密码" :password="showPassword" />
				<text class="uni-icon" :class="[!showPassword ? 'uni-eye-active' : '']"
					@click="changePassword">&#xe568;</text>
			</view>
		</view> -->

		
		<!-- <view class="part_three">copy-right@Rmrf/</view> -->
	</view>
</template>

<script>
	// import uForm from '@/uni_modules/uview-ui/components/u-form/u-form.vue';
	// import uFormItem from '@/uni_modules/uview-ui/components/u-form-item/u-form-item.vue';
	// import goBackIcon from '@/components/goBackIcon/goBackIcon.vue'
	
	export default {
		// components:{
		// 	uForm,
		// 	uFormItem
		// },
		data() {
			return {
				username: '',
				password: '',
			};
		},
		methods: {
			
			submit() {
				let that = this
				// console.log(that.username)
				let qq = that.username
				// 调用用户登录接口
				uni.request({
					url: 'http://116.62.199.133:8081/user/login',
					method: 'get',
					data: {
						qqMail: that.username,
						password: that.password
					},
					success(res) {
						console.log(res)
						// 登录失败
						if (res.data==0) {
							// uni.showToast({
							// 	title: '用户名或密码输入错误，登录失败',
							// 	icon: 'error'
							// })
							uni.showModal({
								title: '用户名或密码输入错误，登录失败',
								icon: 'error',
								confirmText: "确定",
								showCancel: false,
							})
						} else if(res.data==1) {
							// 登录成功
							// 存储用户信息： 用户名
							let that = this
							console.log(res.data)
							
							// 展示登录成功信息
							uni.showToast({
								title: '登录成功',
								icon: 'success'
							})
							// 延迟一秒再跳转
							setTimeout(() => {
								uni.switchTab({
									url: "/pages/index/index"
								})
							}, 500)
							
							// uni.setStorageSync("qqMail",qqMail)
							// 存储用户邮箱，用于查询用户信息
							uni.setStorage({
								key: 'qqMail',
								data: qq,
								success: function () {
									console.log('success');
								}
							});

						}
					}
				})
				

			},
			Register() {
				uni.navigateTo({
					url: '/pages/register/register'
				})
			},
			
		},
		// components: {
		// 	goBackIcon
		// }
	};
</script>

<style>
	
	/* page{ */
		/* background-color: #F2F6FF; */
		/* background-image: url('../../static/images/智能温室背景图.png'); */
		/* background-size: cover; */
		/* background-repeat: no-repeat; */
		/* width: 100%;
		height: 100%;
		box-sizing: border-box;
		position: relative; */
	/* } */
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
		padding: 30% 30% 30% 15%;
		width: 70%;
	}

	/* .part_one image {
		height: 300rpx;
		width: 300rpx;
		margin-top: 35%;
		margin-left: auto;
		margin-right: auto;
		margin-bottom: 50rpx;
	} */

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
		backgroundColor: #92C47D;
		borderColor: #92C47D; */
		background-color: #C4DFAA;
		color: #4A6C4C;
		font-weight: bold;
		width: 60%;
		margin-top: 21%;
		box-shadow: rgba(60, 64, 67, 0.1) 0px 1px 2px 0px, rgba(60, 64, 67, 0.15) 0px 1px 3px 1px;
	}
	
	/* .part_two 部分*/
	.part_two{
		background-color: white;
		position: fixed;
		width: 100%;
		bottom: 0;
		border-radius: 50rpx 50rpx 0 0;
		box-shadow: rgba(17, 17, 26, 0.1) 0px 4px 16px, rgba(17, 17, 26, 0.05) 0px 8px 32px;
		
	}

	.part_two .form {
		margin-top: 10%;
		
	}
	
	.part_two .register{
		margin: 5% 5% 10% 34%;
	}

	.item {
		display: flex;
		font-size: 18px;
		margin-bottom: 7%;
		align-items: center;
		background-color: white;
		padding: 3% 10% 3% 10%;
	}

	/* .part_three {
		display: flex;
		justify-content: center;
		align-items: center;
		margin-top: 27%;
	} */

	.label {
		margin-right: 5%;
		width: 60rpx;
		height: 60rpx
	}

	/* .register {
		text-align: center;
		margin-top: 2%
	} */
</style>