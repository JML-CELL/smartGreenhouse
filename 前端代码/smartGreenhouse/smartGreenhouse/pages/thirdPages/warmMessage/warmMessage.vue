<template>
	<view class="warmMessage">
		<view class="mesItem">
			<!--头像-->
			<view class="image">
				<image src="/static/logo.png" mode=""></image>
			</view>
			<!--主体-->
			<view class="main" @click="intoDatail">
				<view class="part_one">
					<icon class="iconfont icon-circle" style="font-size: 12rpx; color: #018577;"></icon>
					<text style="padding-left: 2%; display: inline-block;">{{messagelist[0].username}}</text>
				</view>
				<view class="part_two">
					{{messagelist[0].msgType}}
				</view>
				<view class="part_three">
					{{messagelist[0].msgContent}}
				</view>
			</view>
			<!--时间-->
			<view class="time">
				{{messagelist[0].msgTime}}
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				messagelist: [{
					username: '新农民',
					msgType: '番茄叶斑病报警',
					msgContent: 'app提示您发现番茄叶斑病！请前往邮箱查收详细信息',
					msgTime: '2024-02-20'
				}]
			}
		},
		methods: {
			intoDatail:function(){
				uni.navigateTo({
					url: '/pages/thirdPages/detailMessage/detailMessage'
				})
			}
		}
	}
</script>

<style>
.warmMessage{
	padding: 5%;
}
.mesItem{
	display: flex;
}
.mesItem image{
	width: 100rpx;
	height: 100rpx;
	border-radius: 50%;
}
.mesItem .main{
	margin-left: 2%;
}
.main .part_one{

}
.main .part_two{
	font-size: 14px;
	margin-top: 2%;
}
.main .part_three{
	font-size: 14px;
	color: gray;
}
.time{
	font-size: 12rpx;
	width: 30%;
	position: absolute;
	right: 0;
	color: gray;
}
</style>
