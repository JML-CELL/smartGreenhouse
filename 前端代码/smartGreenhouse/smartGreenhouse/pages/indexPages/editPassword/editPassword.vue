<template>
	<view class="editPassword">
		
		<!-- deractorBg-->
		<view class="deractorBg">
			<view class="imgs">
				<image src="/static/icons/password1.png" mode=""></image>
				<image src="/static/icons/password2.png" mode=""></image>
				<image src="/static/icons/password3.png" mode=""></image>
			</view>
		</view>
		
		<!-- 修改部分 -->
		<view class="editMain">
			<view class="editItem">
				<text>原密码：</text>
				<input type="text" disabled="true" v-model="oldPassword" />
			</view>
			
			<view class="editItem">
				<text>新密码：</text>
				<input type="text" v-model="newPassword" placeholder="请输入新密码" />
			</view>
			
			<view class="editItem">
				<text>密码确认：</text>
				<input type="text" v-model="newAgainPassword" placeholder="请再次输入新密码" />
			</view>
			
			<!--按钮-->
			<view class="button" @click="confirm">
				<view class="text">确认</view>
			</view>
		</view>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				id: 0,
				oldPassword: '',
				newPassword: '',
				newAgainPassword: ''
			}
		},
		onLoad() {
			// 获取用户ID
			let that = this

			// 获取返回用户信息
			uni.getStorage({
				key: 'userID',
				success: function(res) {
					that.id = res.data

					console.log(that.id)

				}
			});

			// 先获取用户密码
			uni.getStorage({
				key: 'oldPassword',
				success: function(res) {
					that.oldPassword = res.data.trim('"')

					console.log(that.oldPassword)
				}
			});

		},
		methods: {
			confirm: function() {
				let that = this
				uni.request({
					url: 'http://116.62.199.133:8081/user/modify',
					method: 'get',
					data: {
						id: that.id,
						password: that.newPassword
					},
					success(res) {
						console.log(res.data)
						// 修改成功 更新原密码
						that.oldPassword = that.newPassword
						// 并更新本地存储中用户密码信息、
						uni.setStorage({
							key: 'oldPassword',
							data: that.oldPassword
						});

						// 两次密码输入正确并且服务器请求成功 提示成功
						if (that.newPassword == that.newAgainPassword) {
							uni.showToast({
								title: '密码修改成功',
								icon: 'success'
							})

							setTimeout(() => {
								uni.switchTab({
									url: '/pages/my/my'
								})
							}, 500)
						}


					}
				})


			}
		}
	}
</script>

<style>
	.editPassword {
		/* background-color: #EEEEEE; */
	}
	
	.deractorBg{
		background-color: #C4DFAA;
		height: 30vh;
		width: 100%;
		border-radius: 0 0 20% 20%;
	}
	
	.deractorBg .imgs image {
		width: 50rpx;
		height: 50rpx;
		margin: 0 2% 10% 2%;
		/* box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px; */
	}
	
	.deractorBg .imgs {
		display: flex;
		justify-content: center;
		position: relative;
		top: 25%;
	}
	
	.editMain{
		background-color: white;
		padding: 7% 0;
		width: 90%;
		margin: -30% 5% 0 5%;
		border-radius: 25rpx;
		box-shadow: rgba(60, 64, 67, 0.3) 0px 1px 2px 0px, rgba(60, 64, 67, 0.15) 0px 1px 3px 1px;
	}
	
	.editItem {
		display: flex;
		align-items: center;
		padding: 3% 5% 5% 7%;
		
	}

	.editItem text {
		font-weight: bold;
		color: #7E8575;
	}

	.button {
		display: flex;
		align-items: center;
		justify-content: center;
		margin-top: 7%;
		margin-left: 21%;
		background-color: #C4DFAA;
		color: #4A6C4C;
		font-weight: bold;
		padding: 2%;
		width: 55%;
		border-radius: 10rpx;
		font-size: 18px;
		box-shadow: rgba(60, 64, 67, 0.3) 0px 1px 2px 0px, rgba(60, 64, 67, 0.15) 0px 1px 3px 1px;
	}
</style>