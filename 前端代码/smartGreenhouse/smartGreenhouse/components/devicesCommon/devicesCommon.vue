<template>
	<view class="devicesCommon">
		<view class="functionstart">
			<text>功能启动</text>
			<text style="position: absolute;right: 10%;"><u-switch v-model="checked" @change="changeV"></u-switch></text>
		</view>
		<view class="others">
			<!--设置模式-->
<!-- 			<view class="otherItem">
				<text>设置模式</text> -->
				<!-- range中为数组的情况 -->
<!-- 				<picker @change="pickOne($event)" mode="selector" :value="indexOne" :range="listOne" class="listPicker">
					<view>{{ listOne[indexOne] }}</view>
				</picker>
				<icon class="iconfont icon-youjiantou" style="position: absolute;right: 6%;font-size: 24px;"></icon>
			</view> -->
			<!-- <view class="otherItem">
				<text>开始时间</text>
				<picker mode="time" :value="startTime" :start="startMinTime" :end="startMaxTime"
					@change="pickTwo($event)" class="listPicker">
					<view>{{ startTime }}</view>
				</picker>
				<icon class="iconfont icon-youjiantou" style="position: absolute;right: 6%;font-size: 24px;"></icon>
			</view> -->
			<!-- <view class="otherItem">
				<text>结束时间</text>
				<picker mode="time" :value="endTime" :start="endMinTime" :end="endMaxTime" @change="pickThree($event)"
					class="listPicker">
					<view>{{ endTime }}</view>
				</picker>
				<icon class="iconfont icon-youjiantou" style="position: absolute;right: 6%;font-size: 24px;"></icon>
			</view> -->
		<!-- 	<view class="otherItem">
				<text>控制周期</text> -->
				<!-- range中为数组的情况 -->
				<!-- <picker @change="pickFour($event)" mode="selector" :value="indexFour" :range="listFour"
					class="listPicker">
					<view>{{ listFour[indexFour] }}</view>
				</picker>
				<icon class="iconfont icon-youjiantou" style="position: absolute;right: 6%;font-size: 24px;"></icon>
			</view> -->
		</view>
		<!-- <view class="openCondition">
			<text>打开条件</text>
			<view class="part_bottom">
				<input class="input_min" @input="inputMinChange" placeholder="最小值"
					placeholder-style="text-align:center;" />
				<icon class="iconfont icon-hengxian" style="font-size: 24px;"></icon>
				<input class="input_max" @input="inputMaxChange" placeholder="最大值"
					placeholder-style="text-align:center;" />
			</view>
		</view> -->
		
		<view class="confirm">
			<button class="confirmButton" @click="showDefinite">确定</button>
		</view>
		
		
	</view>
</template>

<script>
import checkbox from '../../uni_modules/uview-ui/libs/config/props/checkbox';
	export default {
		name: "devicesCommon",
		data() {
			return {
				checked: false,
				inputMin: '',
				inputMax: '',
				// part_one
				// listOne: ['智能控制', '手动控制'],
				// indexOne: 0,
				// part_two
				startTime: '12:00',
				startMinTime: '00:00',
				startMaxTime: '23:59',
				//part_three
				endTime: '12:00',
				endMinTime: '00:00',
				endMaxTime: '23:59',
				//part_four
				listFour: ['不重复', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六', '星期日'],
				indexFour: 0,
				isShow: false
			};
		},
		methods: {
			// switch打开或者关闭时触发，值为true或者false
			// 即使不监听此事件，this.checked此时也会相应的变成true或者false
			change(status) {
				console.log(status);
			},
			// inputMinChange: function(event) {
			// 	this.inputMin = event.target.value;
			// 	console.log(this.inputMin);
			// },
			// inputMaxChange: function(event) {
			// 	this.inputMax = event.target.value;
			// 	console.log(this.inputMax);
			// },
			// pickOne(e) {
			// 	this.indexOne = e.target.value;
			// 	console.log(this.indexOne)
			// 	if (this.indexOne == 0) this.isShow = false;
			// 	if (this.indexOne == 1) this.isShow = true;
			// 	this.$emit("send", isShow)
			// },
			pickTwo(e) {
				this.startTime = e.detail.value;
			},
			pickThree(e) {
				this.endTime = e.detail.value;
			},
			pickFour(e) {
				this.indexFour = e.target.value;
			},
			changev:function(){
					let that = this
					console.log(this.checked)
			},
			showDefinite: function() {
					
					let that = this
					
					//硬件那边打开才能有响应
					// 卷风机  2  /fan/on
					if(that.scrollIndex==2){
						if(that.checked==true){
							uni.request({
								url: 'http://116.62.199.133:8081/fan/on',
								method: 'get',
								success(res) {
									console.log(res.data)
								}
							})
						}else if(that.checked==false){
							uni.request({
								url: 'http://116.62.199.133:8081/fan/off',
								method: 'get',
								success(res) {
									console.log(res.data)
								}
							})
						}
					}
					// 加湿器  3  /hummidifier/on
					if(that.scrollIndex==3){
						if(that.checked==true){
							uni.request({
								url: 'http://116.62.199.133:8081/humidifier/on',
								method: 'get',
								success(res) {
									console.log(res.data)
								}
							})
						}else if(that.checked==false){
							uni.request({
								url: 'http://116.62.199.133:8081/humidifier/off',
								method: 'get',
								success(res) {
									console.log(res.data)
								}
							})
						}
					}
					// 音乐播放器  4
					
					
					// 烟雾报警器  5
					if(that.scrollIndex==5){
						if(that.checked==true){
							uni.request({
								url: 'http://116.62.199.133:8081/smoke/on',
								method: 'get',
								success(res) {
									console.log(res.data)
								}
							})
						}else if(that.checked==false){
							uni.request({
								url: 'http://116.62.199.133:8081/smoke/off',
								method: 'get',
								success(res) {
									console.log(res.data)
								}
							})
						}
					}
					
					// 农药喷洒器  6  /pesticide/on
					if(that.scrollIndex==6){
						if(that.checked==true){
							uni.request({
								url: 'http://116.62.199.133:8081/pesticide/on',
								method: 'get',
								success(res) {
									console.log(res.data)
								}
							})
						}else if(that.checked==false){
							uni.request({
								url: 'http://116.62.199.133:8081/pesticide/off',
								method: 'get',
								success(res) {
									console.log(res.data)
								}
							})
						}
					}
					// 滴灌设备  7  /water/on
					if(that.scrollIndex==7){
						if(that.checked==true){
							uni.request({
								url: 'http://116.62.199.133:8081/water/on',
								method: 'get',
								success(res) {
									console.log(res.data)
								}
							})
						}else if(that.checked==false){
							uni.request({
								url: 'http://116.62.199.133:8081/water/off',
								method: 'get',
								success(res) {
									console.log(res.data)
								}
							})
						}
					}
					
					// 8
					if(that.scrollIndex==8){
						if(that.checked==true){
							uni.request({
								url: 'http://116.62.199.133:8081/curtain/on',
								method: 'get',
								success(res) {
									console.log(res.data)
								}
							})
						}else if(that.checked==false){
							uni.request({
								url: 'http://116.62.199.133:8081/curtain/off',
								method: 'get',
								success(res) {
									console.log(res.data)
								}
							})
						}
					}
				
				// 提示信息
				uni.showToast({
					title: "设置成功",
					icon: "success"
				})
				
			}
		},
		props: {
			scrollIndex: Number
		},
		updated() {}
	}
</script>

<style>
	.devicesCommon {
		margin: 3%;
	}

	.functionstart {
		display: flex;
		align-items: center;
	}

	.functionstart,
	.otherItem,
	.openCondition,
	.parameter {
		padding: 3%;
		margin: 1%;
		background-color: #ABD0A7;
		color: #365C3B;
		font-weight: bold;
	}

	.part_bottom {
		display: flex;
		margin-top: 5%;
	}

	.input_min,
	.input_max {
		border: 2px solid white;
		width: 40%;
		border-radius: 25rpx;
	}

	.otherItem {
		display: flex;
	}

	.listPicker {
		margin-left: 50%;
	}
	
	.confirmButton {
		position: fixed;
		bottom: 15%;
		left: 20%;
		width: 60%;
		background-color: #ABD0A7;
		color: #365C3B;
		font-weight: bold;
		font-size: 16px;
	}

</style>