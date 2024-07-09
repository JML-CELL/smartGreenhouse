<template>
	<view class="addScenes">
		<fui-dialog :show="toastShow" maskClosable @click="addSceneName" title="请输入场景名称">
			<slot>
				<input v-model="changeInfo" />
				<text style="color: red;font-size: 12px;" v-show="judge">请至少输入一个字符!</text>
			</slot>
		</fui-dialog>
		<view class="first-block" @click="showDialog">
			{{inputText}}
		</view>
	</view>
</template>

<script>
	import fuiDialog from "@/uni_modules/firstui-uni/firstui/fui-dialog/fui-dialog.vue"
	export default {
		data() {
			return {
				inputText: "请输入场景名称",
				changeInfo:"",
				toastShow:false,
				judge:false,
			}
		},
		methods: {
			addSceneName(e) {
				if(e.index) {
					//进行数据交互
					if(this.changeInfo.length==0) {
						this.judge = true
						return;
					}
					this.inputText = this.changeInfo;
				}
				
				this.toastShow = false;
				this.judge = false;
				this.changeInfo = ""
			},
			showDialog() {
				this.toastShow = true
			}
		},
		components: {
			fuiDialog
		}
	}
</script>

<style>
	page {
		background-color: #EEEEEE;
	}
	.first-block {
		width: 96vw;
		margin-left: 2vw;
		border-radius: 6px;
		height: 50px;
		background-color: white;
		line-height: 50px;
		text-align: center;
		margin-top: 20px;
	}
</style>