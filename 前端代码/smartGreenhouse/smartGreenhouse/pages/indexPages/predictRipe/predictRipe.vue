<template>
	<view class="predictRipe">
		<!-- 这里应该需要封装成一个组件 -->
		<view class="predictDetail">
			<view class="images" @click="previewPic">
				<view class="text_up">
					番茄成熟度
					<view class="english">
						tomato ripen
					</view>
				</view>
				<image :src="img" mode=""></image>
			</view>
			<!--advise-->
			<view class="advise">
				<view class="header">
					<icon class="iconfont icon-tousujianyi"></icon>
					建议
				</view>
				<view class="content">
					{{solution}}
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import fuiCard from "@/uni_modules/firstui-uni/firstui/fui-card/fui-card.vue"
	import fuiTag from "@/uni_modules/firstui-uni/firstui/fui-tag/fui-tag.vue"
	export default {
		data() {
			return {
				img: '',
				solution: ''
			}
		},
		methods: {
			// 预览图片
			previewPic:function(){
				let imgsArray = [];
				imgsArray[0] = this.img
				uni.previewImage({
					urls: imgsArray,
					current: 0
				})
			}
		},
		onLoad() {
			
			let that = this
			// 测试接口
			uni.request({
				url: 'http://192.168.9.164:8081/socket/send',
				method: 'get',
				data:{
					command: 11,
				},
				success(res) {
					console.log(JSON.stringify(res.data))
					that.img = res.data.img
					that.solution = res.data.solution
				}
			})
		},
		components: {
			fuiCard,
			fuiTag
		}
	}
</script>

<style>

.images{
	position: relative;
}
.images image{
	width: 100%;
	/* 解决图片和view元素之间的空隙问题 */
	display: flex;
}
.images .text_up{
	position: absolute;
	bottom: 5%;
	left: 3%;
	color: white;
	font-size: 24px;
	z-index: 999;
	font-family: 'Times New Roman', Times, serif;
}
.images .text_up .english{
	font-family: 'Times New Roman', Times, serif;
	font-size: 14px;
	font-style: italic;
	text-align: center;
}
.advise{
	background-color: white;
	border-radius: 25rpx 25rpx 0 0;
	height: 100vh;
	padding: 2% 3%;
	box-shadow: rgba(14, 30, 37, 0.12) 0px 2px 4px 0px, rgba(14, 30, 37, 0.32) 0px 2px 16px 0px;
	font-size: 20px;
}
.advise .header{
	font-weight: bold;
	margin: 2%;
}

.advise .header .iconfont{
	font-size: 20px;
	margin-right: 2%;
	color: #7DA47C;
}

.advise .content{
	border: 1px dotted #365C38;
	border-radius: 10rpx;
	padding: 2%;
}
</style>
