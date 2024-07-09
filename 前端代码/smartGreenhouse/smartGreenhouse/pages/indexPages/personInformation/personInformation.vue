<template>
	<view class="personInformation">

		<!-- 背景UI-->
		<view class="deractorBg">
			<view class="imgs">
				<image src="/static/icons/PersonalInformation1.png" mode=""></image>
				<image src="/static/icons/PersonalInformation2.png" mode=""></image>
				<image src="/static/icons/PersonalInformation3.png" mode=""></image>
			</view>
		</view>

		<view class="partTwo">
			<!--用户头像-->
			<view class="informationItem" @click="uploadImages">
				<text class="title">用户头像：</text>
				<image :src="image" class="right"></image>
			</view>

			<!--用户名-->
			<view class="informationItem">
				<text class="title">用户名：</text>
				<input type="text" class="text" v-model="username" placeholder="请输入修改的用户名" focus="true"></text>
			</view>

			<!--邮箱-->
			<view class="informationItem">
				<text class="title">邮箱：</text>
				<input type="text" class="text" v-model="qqMailS" placeholder="请输入修改的邮箱" disabled="true"></text>
			</view>

			<!--按钮-->
			<view class="button" @click="editor">
				<icon class="iconfont icon-xiugai"></icon>
				<view class="text">修改</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				id: 0,
				username: '',
				qqMailS: '',
				image: '/static/logo.png',
				// flag: true
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

			// 先获取用户名
			uni.getStorage({
				key: 'userName',
				success: function(res) {
					that.username = res.data.trim('"')

					console.log(that.username)
				}
			});

			// 在获取邮箱
			uni.getStorage({
				key: 'qqMail',
				success: function(res) {
					that.qqMailS = res.data

					console.log(that.qqMailS)
				}
			});


			// 再获取用户头像
			uni.getStorage({
				key: 'userUrl',
				success: function(res) {
					console.log(res.data)
					that.image = res.data
				}
			})


		},
		methods: {
			editor: function() {

				let that = this
				// 这里还需要调用 上传头像接口 -- 并且还要试验这个图像是否能在my页面的头像显示

				uni.request({
					url: 'http://116.62.199.133:8081/user/modifyName',
					method: 'get',
					data: {
						id: that.id,
						name: that.username,
					},
					success(res) {
						console.log(res.data)

						// 更新本地存储
						uni.setStorage({
							key: 'userName',
							data: that.username
						});
					}
				})

				// 修改成功 提示信息
				uni.showToast({
					title: '修改成功',
					icon: 'success'
				})

				setTimeout(() => {
					uni.switchTab({
						url: '/pages/my/my'
					})
				}, 500)
			},
			uploadImages: function() {
				let that = this
				uni.chooseImage({
					count: 1, //默认9
					sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
					sourceType: ['album', 'camera'], //从相册选择
					success: function(res) {
						// 
						console.log(JSON.stringify(res.tempFilePaths[0]));
						// that.housePic = res.tempFilePaths[0],
						// that.isChoose = true
						that.image = res.tempFilePaths[0]
						
						// 该接口的url需要本地地址还是云端地址？
						
						
						uni.uploadFile({
							url: 'http://116.62.199.133:8081/upload/img', //仅为示例，非真实的接口地址
							method: 'post',
							filePath: res.tempFilePaths[0],
							name: 'fileImage',
							formData: {
								'user': 'test'
							},
							success: (uploadFileRes) => {
								console.log(uploadFileRes.data);
								
								// 这里才得到云端的图片地址，然后调用上传用户头像
								uni.request({
									url: 'http://116.62.199.133:8081/user/upPicUrl',
									method: 'get',
									data: {
										id: that.id,
										url: uploadFileRes.data
									},
									success(res) {
										console.log(res.data)
										
										// 提示用户修改成功
										// uni.showToast({
										// 	title: '头像上传成功',
										// 	icon: 'success'
										// })
										
										// 更新本地存储
										// 存储用户头像URL
										uni.setStorage({
											key: 'userUrl',
											data: that.image
										})
									}
								})
								
							}
						});
						
						
					}
				});
			
			},
			
		}
	}
</script>

<style>
	.personInformation {
		/* background-color: #EEEEEE; */
	}

	.deractorBg {
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

	.partTwo {
		background-color: white;
		padding: 11% 0;
		width: 90%;
		margin: -30% 5% 0 5%;
		border-radius: 25rpx;
		box-shadow: rgba(60, 64, 67, 0.3) 0px 1px 2px 0px, rgba(60, 64, 67, 0.15) 0px 1px 3px 1px;
	}

	.informationItem {
		display: flex;
		align-items: center;
		padding: 3% 5% 5% 7%;

	}

	.informationItem image {
		width: 100rpx;
		height: 100rpx;
		border-radius: 50%;
	}

	.informationItem .title {
		font-weight: bold;
		text-align: center;
		width: 30%;
		color: #7E8575;
	}

	.informationItem .text {
		width: 50%;

	}

	.informationItem .text,
	.informationItem .right {
		position: absolute;
		left: 35%;
	}

	.button {
		display: flex;
		align-items: center;
		justify-content: center;
		margin-top: 5%;
		margin-left: 23%;
		background-color: #C4DFAA;
		color: #4A6C4C;
		font-weight: bold;
		padding: 1%;
		width: 55%;
		border-radius: 10rpx;
		box-shadow: rgba(60, 64, 67, 0.3) 0px 1px 2px 0px, rgba(60, 64, 67, 0.15) 0px 1px 3px 1px;
	}

	.button .iconfont {
		font-size: 24px;
		margin-right: 1%;
	}

	.button .text {
		font-size: 19px;
	}
</style>