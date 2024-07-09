<template>
	<view class="addHusbandry">
		
		<!-- imgs 部分 -->
		<view class="imgs">
			<image src="/static/icons/AgriculturalRecords1.png" mode=""></image>
			<image src="/static/icons/AgriculturalRecords2.png" mode=""></image>
			<image src="/static/icons/AgriculturalRecords3.png" mode=""></image>
		</view>
		
		<!-- 添加部分 -->
		<view class="addHusbandryItem">
			<text>种植物名称:</text>
			<input class="right_part" type="text" placeholder="请输入种植物名称" v-model="cropperName" />
		</view>
		<view class="addHusbandryItem">
			<text>农事行为:</text>
			<input class="right_part" type="text" placeholder="请输入行为(采收,育苗)等" v-model="action" />
		</view>
		<view class="addHusbandryItem">
			<text>记录时间:</text>
			<input class="right_part" type="text" placeholder="格式为 yyyy-mm-dd hh:mm:ss" v-model="recordTime"
				style="width: 70%;" />
		</view>

		<!--按钮-->
		<button class="button" @click="addHusbandry">添加</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				action: '',
				cropperName: '',
				recordTime: '',
				isHusbandry: Boolean,
			}
		},
		onLoad(option) {
			this.isHusbandry = option.isHusbandry
		},
		methods: {
			addHusbandry:function(){
				
				let that = this
				
				// 调用添加农事记录接口
				uni.request({
					url: 'http://116.62.199.133:8081/record/recordAgricultural',
					method: 'get',
					data: {
						action: that.action,
						cropper: that.cropperName,
						time: that.recordTime
					},
					success(res) {
						console.log(res.data)
						if(res.data==1){
							
							// 改变标志位
							that.isHusbandry = false
							
							// 提示添加成功
							uni.showModal({
								title: that.cropperName+'的农事记录添加成功',
								icon: 'success',
								confirmText: "确定",
								showCancel: false,
							})
							// uni.showToast({
							// 	title: that.cropperName+'的农事记录添加成功',
							// 	icon: 'success'
							// })
							setTimeout(()=>{
								// 跳转到农事记录页面
								uni.navigateTo({
									url: '/pages/indexPages/recoedNoBlank/recoedNoBlank'
								})
							},1000)
						}else{
							// 提示添加失败
							uni.showModal({
								title: that.cropperName+'的农事记录添加失败',
								icon: 'success',
								confirmText: "确定",
								showCancel: false,
							})
							// uni.showToast({
							// 	title: that.cropperName+'的农事记录添加失败',
							// 	icon: 'error'
							// })
							
						}
					}
				})
			}
		}
	}
</script>

<style>
	/* page {
		position: relative;
	} */
	
	page {
		height: 100vh;
		/* background-image: url("/static/images/AgriculturalRecordsBg.png"); */
		background-image: url('/static/images/startBg.jpg');
		background-repeat: no-repeat;
		background-size: cover;
	}
	
	.addHusbandry{
		background-color: white;
		padding: 3%;
		position: fixed;
		bottom: 3%;
		left: 5%;
		width: 84%;
		border-radius: 25rpx;
		box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px;
		
	}
	
	.addHusbandry .imgs image{
		width: 40rpx;
		height: 40rpx;
		margin: 0 2% 7% 2%;
		
	}
	
	.addHusbandry .imgs{
		display: flex;
		justify-content: center;
		
	}
	
	
	.addHusbandryItem {
		display: flex;
		align-items: center;
		margin: 7% 5%;
	}

	.addHusbandryItem .right_part {
		/* position: absolute;
	left: 32%; */
		margin-left: 3%;
	}

	.button {
		background-color: #C4DFAA;
		color: #4A6C4C;
		margin-top: 15%;
		font-weight: bold;
		box-shadow: rgba(60, 64, 67, 0.1) 0px 1px 2px 0px, rgba(60, 64, 67, 0.15) 0px 1px 3px 1px;
	}
</style>