<template>
	<view class="oneClickControl">

		<!--标题-->
		<view class="title">
			{{currentGreen}}
		</view>

		<!-- <view class="top">
			<text>一键控制</text>
			<u-switch v-model="checkedAll"></u-switch>
		</view> -->

		<view class="cardShow">

			<!--part_one-->
			<view class="Item" v-for="(item,index) in devices">
				<!-- <icon :class="item.icon" style="font-size: 36px; color: #018577; margin-right: 9%;"></icon> -->
				<image :src="item.icon" mode=""></image>
				<view style="font-size: 18px;width:50%;">{{item.text}}</view>
				<u-switch v-model="checked"></u-switch>
			</view>
		</view>

		<!--确定-->
		<button class="confirm" @click="confirm">确定</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				devices: [{
						icon: "/static/icons/LEDLight.png",
						text: "LED灯",
					},
					{
						icon: "/static/icons/airConditioning.png",
						text: "空调",
					},
					{
						icon: "/static/icons/RollFan.png",
						text: "卷风机",
					},
					{
						icon: "/static/icons/Humidifier.png",
						text: "加湿器",
					},
					{
						icon: "/static/icons/MusicPlayer.png",
						text: "音乐播放器",
					},
					{
						icon: "/static/icons/SmokeAlarms.png",
						text: "烟雾报警器",
					},
					{
						icon: "/static/icons/PesticideSprayers.png",
						text: "农药喷洒器",
					},
					{
						icon: "/static/icons/DripIrrigationEquipment.png",
						text: "滴灌设备",
					},
					{
						icon: "/static/icons/DripIrrigationEquipment.png",
						text: "电动窗帘",
					}
				],
				checked: false,
				currentGreen: "番茄大棚",
				count: 0
			}
		},
		onLoad() {
			this.currentGreen = this.$globalData.currentGreen
		},
		methods: {
			confirm: function() {
				this.checked = !this.checked

				let xhr = ['pesticide', 'humidifier', 'water', 'music', 'fan']
				let xhrOff = ['light','air','pesticide', 'humidifier', 'water', 'music', 'fan']

				if(this.count%2==0){
					
					xhr.forEach((item) => {
						// 调用所有控制器的开启接口
						uni.request({
							url: 'http://116.62.199.133:8081/' + item + '/on',
							method: 'get',
							success(res) {
								console.log(res.data)
							}
						})
						console.log(item); // 打印数组中的每个元素
					});
					
					// 还要调用灯和空调，  需要给默认参数
					// 调用LED灯
					uni.request({
						url: "http://116.62.199.133:8081/light/on",
						method: 'get',
						data: {
							b: 255,
							g: 255,
							r: 255
						},
						success(res) {
							console.log(res.data)
						}
					})
					
					// 调用空调
					uni.request({
						url: 'http://116.62.199.133:8081/air/on',
						method: 'get',
						data: {
							mood: "COOL",
							speed: 1,
							temperature: 26
						},
						success(res) {
							console.log(res.data)
						}
					})
					
					// 给提示信息
					uni.showToast({
						title: "一键开启成功",
						icon: 'success'
					})
					
				}else{
					
					xhrOff.forEach((item) => {
						// 调用所有控制器的开启接口
						uni.request({
							url: 'http://116.62.199.133:8081/' + item + '/off',
							method: 'get',
							success(res) {
								console.log(res.data)
							}
						})
						console.log(item); // 打印数组中的每个元素
					});
					
					// 给提示信息
					uni.showToast({
						title: "一键关闭成功",
						icon: 'success'
					})
					
				}
				
				this.count = this.count + 1
				
			}
		}
	}
</script>

<style>

	.cardShow {
		background-color: white;
		width: 90% auto;
		border-radius: 25rpx;
		/* box-shadow: 0 0 5px 1px #999 */
		margin: 3% 5% 10% 5%;
		background-color: white;
		padding: 5%;
		border-radius: 15px;
		/* box-shadow: rgba(255, 255, 255, 0.1) 0px 1px 1px 0px inset, rgba(50, 50, 93, 0.25) 0px 50px 100px -20px, rgba(0, 0, 0, 0.3) 0px 30px 60px -30px; */
		box-shadow: rgba(0, 0, 0, 0.15) 0px 5px 15px 0px;
	
	}

	/* .top {
		display: flex;
		justify-content: space-between;
		width: 80%;
		margin-left: 7%;
		background-color: white;
		padding: 3%;
		border-radius: 10rpx;
	}

	.top text {
		font-size: 20px;
	} */

	.title {
		font-size: 36px;
		font-family: '华文行楷';
		margin: 9% 9% 9% 29%;
	}

	.Item {
		display: flex;
		align-items: center;
		height: 120rpx;
		padding-left: 3%;
		padding-right: 3%;
	}

	.Item image {
		width: 57rpx;
		height: 57rpx;
		margin-right: 10%;
	}

	.confirm {
		width: 80%;
		background-color: #C4DFAA;
		color: #4A6C4C;
		font-weight: bold;
	}
</style>