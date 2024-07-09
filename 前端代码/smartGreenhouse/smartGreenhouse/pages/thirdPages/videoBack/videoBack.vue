<template>
	<view class="videoback">
		<view class="timePart">
			<!-- <view class="left">
				日期
				<picker mode="date" :value="date" :start="startDate" :end="endDate" @change="bindDateChange">
					<view class="date">{{ date }}</view>
				</picker>
				时间
				<picker mode="time" :value="startTime" :start="startMinTime" :end="startMaxTime"
					@change="pickTwo($event)" class="listPicker">
					<view>{{ startTime }}</view>
				</picker>
			</view> -->
			<view class="left">
				{{listStart}}
			</view>
			--
			<view class="right">
				{{listEnd}}
			</view>
			<!-- <view class="right">
				日期
				<picker mode="date" :value="dateR" :start="startDateR" :end="endDateR" @change="bindDateChangeR">
					<view class="date">{{ dateR }}</view>
				</picker>
				时间
				<picker mode="time" :value="startTimeR" :start="startMinTimeR" :end="startMaxTimeR"
					@change="pickTwoR($event)" class="listPicker">
					<view>{{ startTimeR }}</view>
				</picker>
			</view> -->
		</view>

		<view class="videos">
			<scroll-view scroll-y class="scroll-h">
				<block v-for="(item, index) in list" class="videoItems">
					<view class="time">开始于：{{JSON.stringify(item.time).replace(":00","").replace(/"/g, '')}}</view>
					<video :id="''+item.id" :src="item.url" class="videoItem" @play="saveplay(item.id)"></video>
					
				</block>
				<!-- <video id="index" src="https://smart-green-home.oss-cn-hangzhou.aliyuncs.com/video/20240409_174349.mp4" class="videoItem"></video> -->
			</scroll-view>
		</view>


	</view>
</template>

<script>
	function getDate(type) {
		const date = new Date();

		let year = date.getFullYear();
		let month = date.getMonth() + 1;
		let day = date.getDate();

		if (type === 'start') {
			year = year - 10;
		} else if (type === 'end') {
			year = year + 10;
		}
		month = month > 9 ? month : '0' + month;
		day = day > 9 ? day : '0' + day;

		return `${year}-${month}-${day}`;
	}
	export default {
		data() {
			return {

				date: getDate({
					format: true
				}),
				startDate: getDate('start'),
				endDate: getDate('end'),
				dateR: getDate({
					format: true
				}),
				startDateR: getDate('start'),
				endDateR: getDate('end'),
				// part_two
				startTime: '12:00',
				startMinTime: '00:00',
				startMaxTime: '23:59',
				// part_two
				startTimeR: '12:00',
				startMinTimeR: '00:00',
				startMaxTimeR: '23:59',

				list: [],
				listStart: String,
				listEnd: String
				// currentId: 0,
				// scrollH: 0,
				// scrollTop: 0,

			}
		},
		onLoad() {

			// 获取视频list
			let that = this
			uni.request({
				url: 'http://172.16.56.148:8081/video/top10',
				method: 'GET',
				success(res) {
					// console.log( JSON.stringify(res.data))
					// for (let i=0;i<res.data.length;i++) {
					// 	that.list.push( JSON.stringify(res.data))
					// }

					let data = res.data

					that.list = data

					// console.log(JSON.stringify(res.data[0].url))

					for (let i = 0; i < that.list.length; i++) {
						console.log(that.list[i])
					}
					console.log(typeof(that.list[0].time))

					that.listStart = that.list[0].time.replace(":00","").replace(/"/g, '')
					that.listEnd = that.list[that.list.length - 1].time.replace(":00","").replace(/"/g, '')
				},
				fail(err) {
					console.log(err)
				}
			})


		},
		methods: {
			bindDateChange: function(e) {
				this.date = e.detail.value;
			},
			pickTwo(e) {
				this.startTime = e.detail.value;
			},
			bindDateChangeR: function(e) {
				this.dateR = e.detail.value;
			},
			pickTwoR(e) {
				this.startTimeR = e.detail.value;
			},
			saveplay(index) {
				
				// 实现不了
				// 获取当前视频id
				let currentId = index;
				// 获取json对象并遍历, 停止非当前视频
				let trailer = this.list;
				const videoContent = uni.createVideoContext(''+currentId);
				
				for (let i = 0; i < trailer.length; i++) {
					let temp = trailer[i].id
					if (temp != currentId) {
						uni.createVideoContext(''+temp).pause();
						console.log(uni.createVideoContext(''+temp))
					}
				}
			},

		}
	}
</script>

<style>
	.timePart {
		display: flex;
		background-color: #C4DFAA;
		color: #4A6C4C;
		border-radius: 25rpx;
		justify-content: space-around;
		padding: 2%;
		margin: 2%;
	}

	/* 	.left,
	.right {
		display: flex;
		font-weight: bold;
	}

	.listPicker {
		margin-left: 5px;
	} */


	.videos {
		padding-bottom: 5%;
	}

	.scroll-h {
		flex-direction: column;
		
	}


	.videoItem {
		width: 94%;
		margin: 3%;
	}

	.time {
		margin-left: 3%;
		padding-top: 3%;
	}
</style>