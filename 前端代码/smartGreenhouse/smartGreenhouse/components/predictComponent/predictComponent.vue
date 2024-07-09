<template>
	<view class="predictComponent">
		<!-- 这里应该需要封装成一个组件 -->
		<view class="predictDetail">
			<view class="images">
				<view class="text_up">
					番茄疾病
					<view class="english">
						tomato sickness
					</view>
				</view>
				<image src="@/static/images/tomato.png" mode=""></image>
			</view>
			<!--advise-->
			<view class="advise">
				<view class="header">
					建议
				</view>
				<view class="content">
					内容内容内容内容内容内容内容内容内容内容内容内容
					内容内容内容内容内容内容内容内容内容内容内容内容
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
				
			}
		},
		methods: {
			
		},
		onLoad() {
			
			// 测试接口
			uni.request({
				url: 'http://172.20.10.2:8081/socket/send',
				method: 'get',
				data:{
					command: 11,
				},
				success(res) {
					console.log(res.data)
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
	font-size: 32px;
	z-index: 999;
	font-family: 'Times New Roman', Times, serif;
}
.images .text_up .english{
	font-family: 'Times New Roman', Times, serif;
	font-size: 16px;
	font-style: italic;
	text-align: center;
}
.advise{
	background-color: #E9E9E9;
	padding: 2% 7%;
}
.advise .header{
	font-weight: bold;
	margin-bottom: 2%;
}
</style>
