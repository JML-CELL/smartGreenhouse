<template>
	<view class="addFertilizer">
		
		<!-- imgs 部分 -->
		<view class="imgs">
			<image src="/static/icons/Fertilizer1.png" mode=""></image>
			<image src="/static/icons/Fertilizer2.png" mode=""></image>
			<image src="/static/icons/Fertilizer3.png" mode=""></image>
		</view>
		
		<!-- 添加部分 -->
		<view class="addFertilizerItem">
			<text>作物名称:</text>
			<input class="right_part" type="text" placeholder="请输入作物名称" v-model="cropper" />
		</view>
		<view class="addFertilizerItem">
			<text>用药行为:</text>
			<input class="right_part" type="text" placeholder="请输入用药行为" v-model="action" />
		</view>
		<view class="addFertilizerItem">
			<text>用药设备:</text>
			<input class="right_part" type="text" placeholder="请输入用药设备" v-model="device" />
		</view>
		<view class="addFertilizerItem">
			<text>使用农药:</text>
			<input class="right_part" type="text" placeholder="请输入使用的农药名称" v-model="fertilizer" />
		</view>
		<view class="addFertilizerItem">
			<text>用药时间:</text>
			<input class="right_part" type="text" placeholder="请输入用药时间" v-model="time" />
		</view>

		<!--按钮-->
		<button class="button" @click="addFertilizer">添加</button>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				action: '',
				cropper: '',
				device: '',
				fertilizer: '',
				time: ''
			}
		},
		methods: {
			addFertilizer: function() {

				let that = this

				// 调用添加肥料记录接口
				uni.request({
					url: 'http://116.62.199.133:8081/record/recordFertilizer',
					method: 'get',
					data: {
						action: that.action,
						cropper: that.cropper,
						device: that.device,
						fertilizer: that.fertilizer,
						time: that.time
					},
					success(res) {
						console.log(res.data)
						if (res.data == 1) {

							// 首先提示添加成功
							uni.showModal({
								title: that.cropper + '肥药添加记录成功',
								icon: 'success',
								confirmText: "确定",
								showCancel: false,
							})
							
							// 其次跳转页面
							setTimeout(() => {
								uni.navigateTo({
									url: '/pages/indexPages/recoedNoBlank/recoedNoBlank'
								})
							}, 1000)
						}
					}
				})
			}
		}
	}
</script>

<style>
	page {
		height: 100vh;
		/* background-image: url("/static/images/CompoundFertilizer.png"); */
		background-image: url('/static/images/startBg.jpg');
		background-repeat: no-repeat;
		background-size: contain;
	}
	.addFertilizer{
		background-color: white;
		padding: 3%;
		position: fixed;
		bottom: 3%;
		left: 5%;
		width: 85%;
		border-radius: 25rpx;
		box-shadow: rgba(60, 64, 67, 0.1) 0px 1px 2px 0px, rgba(60, 64, 67, 0.15) 0px 1px 3px 1px;
	}
	
	.addFertilizer .imgs image{
		width: 40rpx;
		height: 40rpx;
		margin: 0 2% 7% 2%;
		/* box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px; */
	}
	
	.addFertilizer .imgs{
		display: flex;
		justify-content: center;
		
	}
	
	.addFertilizerItem {
		display: flex;
		margin: 7% 5%;
	}

	.right_part {
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