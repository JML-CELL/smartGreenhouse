<template>
	<view class="my">
		<view class="part_one">
			<image :src="image" mode=""></image>
			<text>{{name}}</text>
		</view>
		<view class="part_two">
			<view class="Item" v-for="(item,index) in my" @click="gotoThird(index)">
				<icon :class="item.icon" style="font-size: 32px; color: #254619; margin-right: 9%;"></icon>
				<view style="font-size: 18px;">{{item.text}}</view>
				<icon class="iconfont icon-youjiantou" style="font-size: 24px; margin-left: 45%;"></icon>
			</view>
		</view>

		<!--测试 能否获取存储在本地数据的按钮 -->
		<!-- <button @click="testM">测试</button> -->
	</view>
</template>

<script>
	import {
		data
	} from '../../uni_modules/uview-ui/libs/mixin/mixin';
	export default {
		data() {
			return {
				image: "/static/title.jpg",
				name: "pry",
				id: 0,
				qqMailS: '',
				my: [{
						icon: "iconfont icon-youjian",
						text: "预警消息"
					},
					{
						icon: "iconfont icon-gerenxinxi",
						text: "个人信息"
					},
					{
						icon: "iconfont icon-xiugaimima",
						text: "修改密码"
					},
					{
						icon: "iconfont icon-guanyuwomen",
						text: "关于我们"
					}
				]
			}
		},
		mounted() {


		},
		onShow() {

			let that = this

			// 获取返回用户信息
			uni.getStorage({
				key: 'qqMail',
				success: function(res) {
					that.qqMailS = res.data
					// var str = "   123"
					// console.log(str.replace(/\s*/g,""))

					console.log(that.qqMailS)

					that.userInfo()
				}
			});

			// 如果用户头像url为空，则默认为头像
		},
		methods: {

			userInfo: function() {
				
				let that = this

				// 调用返回用户信息接口
				uni.request({
					url: 'http://116.62.199.133:8081/user/userInfo',
					method: 'get',
					data: {
						qqMail: that.qqMailS
					},
					success(res) {
						console.log(JSON.stringify(res.data))
						const dataID = JSON.stringify(res.data.id).replace(/"/g, '')
						const dataUsername = JSON.stringify(res.data.username).replace(/"/g, '')
						const password = JSON.stringify(res.data.password).replace(/"/g, '')
						const dataUrl = JSON.stringify(res.data.url).replace(/"/g, '')

						console.log(dataID)
						console.log(dataUsername)
						// 存储用户ID， 用户个人信息修改那里
						uni.setStorage({
							key: 'userID',
							data: dataID
						})

						// 存储用户名
						uni.setStorage({
							key: 'userName',
							data: dataUsername
						})

						// 存储旧密码（用户密码）
						uni.setStorage({
							key: 'oldPassword',
							data: password
						})


						// 存储用户头像URL
						uni.setStorage({
							key: 'userUrl',
							data: dataUrl
						})


						that.name = dataUsername
						that.id = dataID

						// 新用户头像为默认头像
						if (dataUrl == null) {
							that.image = "/static/title.png"
						} else {
							that.image = dataUrl
						}
					},
					fail(err) {
						console.log(err)
					}
				})
			},
			gotoThird: function(index) {
				if (index == 0) {
					uni.navigateTo({
						url: '/pages/thirdPages/warmMessage/warmMessage'
					})
				} else if (index == 1) {
					uni.navigateTo({
						url: '/pages/indexPages/personInformation/personInformation'
					})
				} else if (index == 2) {
					uni.navigateTo({
						url: '/pages/indexPages/editPassword/editPassword'
					})
				} else if (index == 3) {
					uni.navigateTo({
						url: '/pages/thirdPages/adboutUs/adboutUs'
					})
				}
			}
		}
	}
</script>

<style>
	/* .part_one{
	display: flex;
	align-items: center;
	background-color: #018577;
	height: 400rpx;
	border-radius: 0 0 10% 10%;
	margin-top: -5%;
} */

	page {
		background-image: url("/static/images/startBg.jpg");
		background-repeat: no-repeat;
		background-size: cover;
		background-color: rgba(0, 0, 0, 0.1);
		/* 设置背景图透明度为50% */
	}

	.part_one {
		display: flex;
		align-items: center;
		flex-direction: column;
		margin-top: 13%;
	}

	.part_one image {
		width: 200rpx;
		height: 200rpx;
		border-radius: 50%;

	}

	.part_one text {
		font-size: 18px;
		font-weight: bold;
		margin-top: 3%;
		color: #808777;
	}

	.part_two {
		/* background-color: white; */
		/* background-color: #C6E1AC; */
		background-color: #7DA47C;
		border-radius: 50rpx 50rpx 0 0;
		position: fixed;
		width: 97%;
		bottom: 0;
		padding: 5% 0 40% 3%;
		/* box-shadow: 0 0 5px 1px #C6E1AC; */
		/* box-shadow: rgba(60, 64, 67, 0.3) 0px 1px 2px 0px, rgba(60, 64, 67, 0.15) 0px 2px 6px 2px; */
		box-shadow: rgba(60, 64, 67, 0.3) 0px 1px 2px 0px, rgba(60, 64, 67, 0.15) 0px 1px 3px 1px;
	}

	.Item {
		display: flex;
		align-items: center;
		height: 120rpx;
		padding: 2% 7%;
		color: #254619;
		font-weight: bold;

	}
</style>