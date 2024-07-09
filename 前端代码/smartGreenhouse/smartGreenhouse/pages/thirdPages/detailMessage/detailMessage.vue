<template>
	<view class="detailMessage">

		<!--导航标题栏-->
		<view class="navigator">
			<icon class="iconfont icon-zuojiantou" @click="goBack"></icon>
			<view class="right">
				<icon class="iconfont icon-caozuo-shangla" style="color: darkgray;"></icon>
				<icon class="iconfont icon-open"></icon>
			</view>
		</view>
		
		<!--内容栏-->
		<!--内容标题-->
		<view class="detailContent">
			<view class="contentHead">
				<view class="part_one">
					番茄成熟度预警
				</view>
				<view class="part_two">
					<text class="username">新农民</text>
					<text class="detail">详情</text>
				</view>
			</view>
			
			<!--主体文字内容-->
			<view class="mainContent">
				发现番茄叶斑病
			</view>
			
			<!--图片渲染-->
			<view class="picture">
				<view class="textHead">
					------当前识别结果如下------
				</view>
				<view class="image">
					<image src="../../../static/images/TomatoRipeness.png"></image>
				</view>
			</view>
		</view>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				qqMailS: ''
			}
		},
		methods: {
			goBack:function(){
				uni.navigateBack()
			}
		},
		onLoad() {
			
			let that = this
			
			// 获取本地缓存： qqMail
			uni.getStorage({
				key: 'qqMail',
				success: function (res) {
					console.log(res.data)
					that.qqMailS = res.data
				}
			});
			console.log(typeof(that.qqMailS))
			
			// 理应先得到返回的用户信息  保存用户名在本地 （这个接口有问题）
			// 所以先用死数据测试一下邮箱接口
			// uni.request({
			// 	url: 'http://116.62.199.133:8081/mail/sendMail',
			// 	method: 'get',
			// 	data: {
			// 		name: '吴坤瑞',
			// 		recipient: that.qqMailS,
			// 		temprature: 20
			// 	},
			// 	success(res) {
			// 		console.log(res.data) 
			// 	}
			// })
		}
	}
</script>

<style>
	.navigator {
		padding-top: 10%;
		margin-left: 3%;
		margin-right: 9%;
		display: flex;
		justify-content: space-between;
	}

	.navigator .iconfont {
		font-size: 24px;
	}
	.right{
		display: flex;
		justify-content: space-between;
		width: 30%;
	}
	
	.detailContent{
		padding: 3%;
		
	}
	.contentHead .part_one{
		font-size: 22px;
		font-weight: bold;
	}
	.contentHead .part_two{
		display: flex;
		justify-content: space-between;
		padding-right: 7%;
		color: cornflowerblue;
		margin-top: 2%;
	}
	
	.mainContent{
		margin-top: 5%;
		color: darkred;
	}
	
	.picture{
		margin-top: 7%;
		text-align: center;
	}
	.picture .image{
		margin-top: 2%;
	}
</style>