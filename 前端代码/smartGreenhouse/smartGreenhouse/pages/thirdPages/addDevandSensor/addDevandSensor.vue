<template>
	<view class="addDevandSensor">
		
		<!-- imgs 部分 -->
		<view class="imgs">
			<image src="/static/icons/GrowFillLights.png" mode=""></image>
			<image src="/static/icons/MusicPlayer_add.png" mode=""></image>
			<image src="/static/icons/RollFan_add.png" mode=""></image>
		</view>
		
		<!-- 添加部分 -->
		<view class="addDevandSensorItem">
			<text>{{name}}名称:</text>
			<input class="right_part" type="text" placeholder="请输入名称" v-model="Devname" />
		</view>
		<view class="addDevandSensorItem">
			<text>温室名称:</text>
			<input class="right_part" type="text" placeholder="请输入设备所在种植温室的名称" v-model="houseName" />
		</view>
		<view class="addDevandSensorItem">
			<text>CONTENT:</text>
			<input class="right_part" type="text" placeholder="请输入content" v-model="content"
				style="width: 70%;" />
		</view>

		<!--按钮-->
		<button class="button" @click="addDevandSensor">添加</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				name: '设备',
				Devname: '',
				houseName: '',
				content: '',
			}
		},
		onLoad(option) {
			this.name = option.name
		},
		methods: {
			addDevandSensor:function(){
				
				let that = this
				
				// 调用添加设备接口
				uni.request({
					url: 'http://116.62.199.133:8081/device/add',
					method: 'get',
					data: {
						content: that.Devname,
						house: that.houseName,
						name: that.content
					},
					success(res) {
						console.log(JSON.stringify(res.data))
						if(res.data==1){
							
							// 提示添加成功
							uni.showModal({
								title: that.name+'添加成功',
								icon: 'success',
								confirmText: "确定",
								showCancel: false,
							})
							// uni.showToast({
							// 	title: that.cropperName+'的农事记录添加成功',
							// 	icon: 'success'
							// })
							setTimeout(()=>{
								// 跳转到对应界面
								if(that.name=="设备"){
									uni.navigateTo({
										url: '/pages/indexPages/recoedNoBlank/recoedNoBlank'
									})
								}else if(that.name=="传感器"){
									uni.navigateTo({
										url: '/pages/indexPages/recoedNoBlank/recoedNoBlank'
									})
								}
								
							},1000)
						}else{
							// 提示添加失败
							uni.showModal({
								title: that.name+'添加失败',
								icon: 'success',
								confirmText: "确定",
								showCancel: false,
							})
							
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
		/* background-image: url("/static/images/GrowFillLights.png"); */
		background-image: url('/static/images/startBg.jpg');
		background-repeat: no-repeat;
		background-size: cover;
	}
	
	.addDevandSensor{
		background-color: white;
		padding: 5%;
		position: fixed;
		bottom: 3%;
		left: 5%;
		width: 80%;
		border-radius: 25rpx;
		box-shadow: rgba(60, 64, 67, 0.1) 0px 1px 2px 0px, rgba(60, 64, 67, 0.15) 0px 1px 3px 1px;
	}
	
	.addDevandSensor .imgs image{
		width: 40rpx;
		height: 40rpx;
		margin: 0 2% 7% 2%;
		/* box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px; */
	}
	
	.addDevandSensor .imgs{
		display: flex;
		justify-content: center;
		
	}
	
	
	.addDevandSensorItem {
		display: flex;
		align-items: center;
		margin: 7% 3%;
	}

	.addDevandSensorItem .right_part {
		/* position: absolute;
	left: 32%; */
		margin-left: 3%;
	}

	.button {
		background-color: #C4DFAA;
		color: #4A6C4C;
		font-weight: bold;
		margin-top: 15%;
		box-shadow: rgba(60, 64, 67, 0.1) 0px 1px 2px 0px, rgba(60, 64, 67, 0.15) 0px 1px 3px 1px;
	}
</style>