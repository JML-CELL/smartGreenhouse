<template>
	<view class="addTimeStop">
		<view class="timeTask">
			<view class="otherItem">
				<text>开始时间</text>
				<picker mode="time" :value="startTime" :start="startMinTime" :end="startMaxTime"
					@change="pickTwo($event)" class="listPicker">
					<view>{{ startTime }}</view>
				</picker>
				<icon class="iconfont icon-youjiantou" style="position: absolute;right: 6%;font-size: 24px;"></icon>
			</view>
			<view class="otherItem">
				<text>结束时间</text>
				<picker mode="time" :value="endTime" :start="endMinTime" :end="endMaxTime" @change="pickThree($event)"
					class="listPicker">
					<view>{{ endTime }}</view>
				</picker>
				<icon class="iconfont icon-youjiantou" style="position: absolute;right: 6%;font-size: 24px;"></icon>
			</view>
			<view class="otherItem">
				<text>控制周期</text>
				<!-- range中为数组的情况 -->
				<picker @change="pickFour($event)" mode="selector" :value="indexFour" :range="listFour"
					class="listPicker">
					<view>{{ listFour[indexFour] }}</view>
				</picker>
				<icon class="iconfont icon-youjiantou" style="position: absolute;right: 6%;font-size: 24px;"></icon>
			</view>

		</view>
		<!--确定按钮-->
		<view class="confirm">
			<button class="confirmButton" @click="showDefinite">确定</button>
		</view>
	</view>
</template>

<script>
	import fuiUpload from "@/uni_modules/uview-ui/components/u-upload/u-upload.vue"
	export default {
		data() {
			return {
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
			}
		},
		methods: {
			pickTwo(e) {
				this.startTime = e.detail.value;
			},
			pickThree(e) {
				this.endTime = e.detail.value;
			},
			pickFour(e) {
				this.indexFour = e.target.value;
			},
			showDefinite: function() {
				uni.showToast({
					title: "设置成功",
					icon: "success"
				})

				// 延迟1秒再跳转
				setTimeout(() => {
					uni.navigateTo({
						url: '/pages/thirdPages/timeSystem/timeSystem'
					})
				}, 1000)

			}
		},
		components: {
			fuiUpload
		}
	}
</script>

<style>
	.timeTask {
		margin: 3%;
		border-radius: 25rpx;
		background-color: #87B9BA;
	}

	.otherItem {
		display: flex;
		padding: 5%;
		color: white;
	}

	.listPicker {
		margin-left: 50%;
	}

	.confirmButton {
		margin-top: 10%;
		width: 20%;
		background-color: #018577;
		color: white;
		font-size: 16px;
	}
</style>