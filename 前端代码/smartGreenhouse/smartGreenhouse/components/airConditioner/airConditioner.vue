<template>
	<view class="airconditioner" v-if="scrollIndex==1">
		<!--可以作为组件部分，就是手动控制那里需要一个参数呈现不同界面显示时，子传父数据还没有实验过-->
		<view class="functionstart">
			<text>功能启动</text>
			<text style="position: absolute;right: 10%;"><u-switch v-model="checked"></u-switch></text>
		</view>
		<view class="others">
			<!--设置模式-->
			<!-- <view class="otherItem">
				<text>设置模式</text> -->
				<!-- range中为数组的情况 -->
			<!-- 	<picker @change="pickOne($event)" mode="selector" :value="indexOne" :range="listOne" class="listPicker">
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
			<!-- <view class="otherItem">
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

		<!--给空调设备设置参数-->
		<view class="parameter">
			<!--模式-->
			<view class="parameterItem">
				<text>模式</text>
				<picker mode="selector" :range="modeList" @change="pickOne($event)" style="position: absolute;right: 20%;">
					<view>{{modeList[modeIndex]}}</view>
				</picker>
				<icon class="iconfont icon-youjiantou" style="position: absolute;right: 13%;font-size: 24px;"></icon>
			</view>
			<!--风速-->
			<view class="parameterItem">
				<text style="margin-top: -2%;">风速</text>
				<view class="windSpeed" style="padding-bottom: 2%;">
					<!-- <u-number-box v-model="windSpeed" @change="windSpeedChange" inputWidth="60%"></u-number-box> -->
					<fui-input-number custom backgroundColor="#FFF" :size="30" :width="80" margin="0" v-model="windSpeed">
						<fui-icon name="minussign" :size="48" color="#4A6C4C"></fui-icon>
						<template v-slot:plus>
							<fui-icon name="plussign-fill" :size="48" color="#4A6C4C"></fui-icon>
						</template>
					</fui-input-number>
				</view>
			</view>
			<!--温度-->
			<view class="parameterItem">
				<text style="margin-top: -2%;">温度</text>
				<view class="windSpeed" >
					<!-- <u-number-box v-model="warm" @change="warmChange" inputWidth="60%" min="26"></u-number-box> -->
					<fui-input-number custom backgroundColor="#FFF" :size="30" :width="80" margin="0" v-model="warm">
						<fui-icon name="minussign" :size="48" color="#4A6C4C"></fui-icon>
						<template v-slot:plus>
							<fui-icon name="plussign-fill" :size="48" color="#4A6C4C"></fui-icon>
						</template>
					</fui-input-number>
				</view>
			</view>
		</view>
		
		
		<view class="confirm">
			<button class="confirmButton" @click="showDefinite">确定</button>
		</view>
		
		
	</view>
</template>

<script>
	import fuiInputNumber from "@/uni_modules/firstui-uni/firstui/fui-input-number/fui-input-number.vue"
	import fuiIcon from '@/uni_modules/firstui-uni/firstui/fui-icon/fui-icon.vue'
	export default {
		name: "airConditioner",
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
				// isShow: false,
				windSpeed: 1,
				warm: 1,
				modeList: ["COOL","HOT"],
				modeIndex: 0
			};
		},
		updated() {
			
		},
		methods: {
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
			pickOne(e){
				this.modeIndex = e.target.value;
			},
			showDefinite: function() {
				
				// 同调用接口
				let that = this
				console.log(that.warm)
				// 硬件连上才能测试
				if(that.checked==true){
					uni.request({
						url: 'http://116.62.199.133:8081/air/on',
						method: 'get',
						data: {
							mood: that.modeList[that.modeIndex],
							speed: that.windSpeed,
							temperature: that.warm
						},
						success(res) {
							console.log(res)
						}
					})
				}else if(that.checked==false){
					uni.request({
						url: 'http://116.62.199.133:8081/air/off',
						method: 'get',
						success(res) {
							console.log(res)
						}
					})
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
		components: {
			fuiInputNumber,
			fuiIcon
		}
	}
</script>

<style>
	.airconditioner {
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
	.parameterItem{
		display: flex;
		align-items: center;
		justify-content: space-between;
		padding-right: 7%;
		padding-bottom: 3%;
	}
	.parameterItem image{
		width: 40px;
		height: 40px;
	}
	.parameterItem text{
		margin-right: 5%;
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