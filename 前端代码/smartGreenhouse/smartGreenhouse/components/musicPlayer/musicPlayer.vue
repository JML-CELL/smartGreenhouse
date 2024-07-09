<template>
	<view class="MusicPlayer" v-if="scrollIndex==4">
		<view class="functionstart">
			<text>功能启动</text>
			<text style="position: absolute;right: 10%;"><u-switch v-model="checked"
					@change="changeV"></u-switch></text>
		</view>
		<view class="others">
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
			<!-- 	<picker @change="pickFour($event)" mode="selector" :value="indexFour" :range="listFour"
					class="listPicker">
					<view>{{ listFour[indexFour] }}</view>
				</picker>
				<icon class="iconfont icon-youjiantou" style="position: absolute;right: 6%;font-size: 24px;"></icon>
			</view> -->
		</view>


		<view class="parameter">
			<!--风格-->
			<view class="parameterItem">
				<text>风格</text>
				<picker mode="selector" :range="styleList" @change="pickOne($event)" class="stylePicker">
					<view>{{styleList[styleIndex]}}</view>
				</picker>
				<icon class="iconfont icon-youjiantou" style="position: absolute;right: 6%;font-size: 24px;"></icon>
			</view>
		</view>

		<view class="confirm">
			<button class="confirmButton" @click="showDefinite">确定</button>
		</view>
	</view>

</template>

<script>
	import DevicesCommon from '@/components/devicesCommon/devicesCommon.vue'
	export default {
		name: "musicPlayer",
		data() {
			return {
				checked: false,
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


				styleList: ["抒情", "欢快"],
				styleIndex: 0
			};
		},
		components: {
			DevicesCommon
		},
		props: {
			scrollIndex: Number
		},
		methods: {
			change(status) {
				console.log(status);
			},
			changev:function(){
					let that = this
					console.log(this.checked)
			},
			pickTwo(e) {
				this.startTime = e.detail.value;
			},
			pickThree(e) {
				this.endTime = e.detail.value;
			},
			pickFour(e) {
				this.indexFour = e.target.value;
			},
			pickOne: function(e) {
				this.styleIndex = e.target.value;
				uni.request({
					url: 'http://116.62.199.133:8081/music/next',
					method: 'get',
					success(res) {
						console.log(res.data)
					}
				})
				
			},
			showDefinite:function(){
				if(this.checked==true){
					
					uni.request({
						url: 'http://116.62.199.133:8081/music/on',
						method: 'get',
						success(res) {
							console.log(res.data)
							// 在切换的时候只能延迟开启了，看在测试的时候会不会有什么问题
							// if(res.statusCode==200){
							// 	setTimeout(()=>{
									
							// 	},500)
							// }
						}
					})
				}else if(this.checked==false){
					uni.request({
						url: 'http://116.62.199.133:8081/music/off',
						method: 'get',
						success(res) {
							console.log(res.data)
						}
					})
				}
				
				// 提示信息
				uni.showToast({
					title: "设置成功",
					icon: "success"
				})
				
			},
			
			

		}
	}
</script>

<style>
	.MusicPlayer{
		margin-top: 3%;
	}
	
	.parameterItem {
		display: flex;
		align-items: center;
		margin: 1%;
	}

	.parameterItem image {
		width: 40px;
		height: 40px;
	}

	.parameterItem text {
		margin-right: 5%;
		
	}
	.stylePicker{
		margin-left: 60%;
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
		margin: 1% 3%;
		background-color: #ABD0A7;
		color: #365C3B;
		font-weight: bold;
	}
	
	.part_bottom {
		display: flex;
		margin-top: 5%;
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