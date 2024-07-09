<template>
	<view class="warning">
		
		<!--growingEnvironment-->
		<view class="warningItem" style="background-color: #3CAA28;" @click="goOne">
			生长环境
		</view>
		<!--identifyResults-->
		<view class="warningItem" style="background-color: #E83D2E;" @click="goTwo">
			v8识别结果
		</view>
		<!--thresholdRecommendation-->
		<view class="warningItem" style="background-color: #F3C443;" @click="goThree">
			传感器阈值推荐
		</view>
	</view>
</template>

<script>
	// import fuiTag from "@/uni_modules/firstui-uni/firstui/fui-tag/fui-tag.vue"
	export default {
		data() {
			return {

			};
		},
		methods: {
			goOne:function(){
				uni.navigateTo({
					url: '/pages/thirdPages/warmMessage/warmMessage'
				})
			},
			goTwo:function(){
				uni.navigateTo({
					url: '/pages/thirdPages/warmMessage/warmMessage'
				})
			},
			goThree:function(){
				uni.navigateTo({
					url: '/pages/thirdPages/warmMessage/warmMessage'
				})
			}
		},
		onLoad() {
			
		},
		// components: {
		// 	fuiTag
		// }
	};
</script>

<style>
.warning{
	margin-top: 45%;
}
.warningItem{
	background-color: white;
	margin: 25% 5% 5% 5%;
	padding: 5%;
	text-align: center;
	font-size: 18px;
	border-radius: 25rpx;
	color: white;
	font-weight: bold;
}
</style>