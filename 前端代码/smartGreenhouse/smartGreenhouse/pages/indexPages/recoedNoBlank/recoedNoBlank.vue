<template>
	<view class="recoedNoBlank">
		<!-- 标题显示 -->
		<view class="title_part">
			<icon class="iconfont icon-zuojiantou" @click="goBack" style="font-size: 20px;margin-left: 3%;"></icon>
			<view class="head">
				{{searchName}}
			</view>
			<!-- <view class="print">
				<icon class="iconfont icon-tianchongxing-" style="color: #92C47D;"></icon>
				<view>打印农事记录</view>
			</view> -->
		</view>

		<view class="print" @click="printRecord">
			<icon class="iconfont icon-tianchongxing-" style="color: #92C47D;"></icon>
			<view>打印农事记录</view>
		</view>

		<!--农作物基本信息渲染处-->
		<view class="part_one">
			<recordNavigator :title="msgOne"></recordNavigator>
			<view class="msgBox">
				<view class="msgBoxItem">
					<image :src='cropperImg' mode="" style="width: 100%;"></image>
				</view>
				<view class="msgBoxItem" v-for="(item, index) in partOne">
					<icon :class="item.icon" style="color: #92C47D;!important"></icon>
					<text decode>{{ item.title }}</text>
					<text>{{ item.context }}</text>
				</view>
			</view>
		</view>

		<!--农作物生长环境渲染处，这里默认生长环境都是一样的-->
		<view class="part_two">
			<recordNavigator :title="msgTwo"></recordNavigator>
			<view class="wrap">
				<uni-row v-for="(row, rowIndex) in partTwo" :key="rowIndex" class="row">
					<uni-col v-for="(column, columnIndex) in row" :key="columnIndex" class="column">
						<view decode class="textItem">
							<text class="left">{{ column.name }}</text>
							{{ column.number }}{{column.unit}}
							</view>
					</uni-col>
				</uni-row>
			</view>
		</view>


		<!--农作物农事记录渲染处-->
		<view class="part_three">

			<view class="top">
				<recordNavigator :title="msgThree"></recordNavigator>

				<!--来个添加icon，用来添加农事记录 (绝对定位)-->
				<view class="addHusbandry" @click="addHusbandry">
					<icon class="iconfont icon-zengjiatianjiajiahao" style="font-size: 20px;"></icon>
					<text>添加</text>
				</view>
			</view>

			<view class="recordItem" v-for="(item,index) in partThree" :key="index">
				<icon class="iconfont icon-circle" style="font-size: 12rpx; color: #92C47D;" />
				<view class="right_part">
					<view class="title">{{item.action}}</view>
					<view class="time">{{item.time}}</view>
				</view>
			</view>
		</view>

		<!--农作物肥药记录渲染处-->
		<view class="part_four">

			<view class="top">
				<recordNavigator :title="msgFour"></recordNavigator>

				<!--来个添加icon，用来添加农事记录 (绝对定位)-->
				<view class="addFertilizer" @click="addFertilizer">
					<icon class="iconfont icon-zengjiatianjiajiahao" style="font-size: 20px;"></icon>
					<text>添加</text>
				</view>
			</view>

			<view class="recordItem" v-for="(item,index) in partFour" :key="index">
				<icon class="iconfont icon-circle" style="font-size: 12rpx; color: #92C47D;" />
				<view class="right_part">
					<view class="title">{{item.action}}</view>
					<view class="time">{{item.time}}</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import recordNavigator from '../../../components/recordNavigator/recordNavigator.vue';
	export default {
		data() {
			return {
				// 是否显示的判断
				isBlankOne: Boolean,
				// record页面传来的参数
				searchName: String,
				// 农作物的图片
				cropperImg: String,
				msgOne: '基本信息',
				partOne: [{
						icon: 'iconfont icon-zhongzi',
						title: '&ensp;农作物名称&ensp;:&ensp; ',
						context: '草莓'
					},
					{
						icon: 'iconfont icon-tongxunlu01-F',
						title: '&ensp;种植基地&ensp;:&ensp; ',
						context: '湖南省衡阳市蒸湘区雨母山镇南华大学'
					},
					{
						icon: 'iconfont icon-shijian',
						title: '&ensp;种植时间&ensp;:&ensp; ',
						context: '2023-01-26 08:30'
					}
				],

				msgTwo: '生长环境',
				partTwo: [
					[{
						name: '空气温度:',
						number: '20',
						unit: '°C'
					}, {
						name: '空气湿度:',
						number: '20',
						unit: 'hPa'
					}],
					[{
						name: '土壤湿度:',
						number: '20',
						unit: 'hPa'
					}, {
						name: '光照:',
						number: '20',
						unit: 'cd'
					}],
					[{
						name: 'CO₂浓度:',
						number: '20',
						unit: 'pm'
					}, {
						name: '水质PH:',
						number: '10',
						unit: 'ph'
					}],
					[{
						name: '大气压:',
						number: '10',
						unit: 'kPa'
					}]
				],
				msgThree: '农事记录',
				// partThree: [{
				// 		title: "采收",
				// 		time: "2024-02-07 12:30"
				// 	},
				// 	{
				// 		title: "施肥浇水",
				// 		time: "2024-02-07 10:45"
				// 	},
				// 	{
				// 		title: "育苗",
				// 		time: "2024-02-06 08:00"
				// 	}
				// ],
				partThree: [],
				msgFour: '肥药记录',
				partFour: []
			}
		},
		onLoad(option) {
			let that = this

			// that.searchName = option.searchName

			that.searchName = uni.getStorageSync("searchName")

			// console.log('recordNoBlank'+this.searchName)

			// 在这里查询农作物基本信息和农事记录，肥药记录

			// 1.首先是农作物基本信息

			uni.request({
				url: 'http://116.62.199.133:8081/cropper/cropperInfo',
				method: 'get',
				data: {
					name: that.searchName
				},
				success(res) {
					console.log(res.data)
					that.cropperImg = res.data.picUrl
					that.partOne[0].context = res.data.name
					that.partOne[1].context = res.data.house
					that.partOne[2].context = res.data.plantTime
				}
			})

			// 2.其次是农事记录
			uni.request({
				url: 'http://116.62.199.133:8081/record/showAgriculturalRecords',
				method: 'get',
				data: {
					cropper: that.searchName
				},
				success(res) {
					console.log(res.data)
					that.partThree = res.data
					// console.log(that.partThree)
				}
			})

			// 3.最后是肥药记录
			uni.request({
				url: 'http://116.62.199.133:8081/record/showFertilizerRecords',
				method: 'get',
				data: {
					cropper: that.searchName
				},
				success(res) {
					console.log(res.data)
					that.partFour = res.data
				}
			})

			// 页面加载时先获取一次数据
			this.getDatas()

		},
		onPullDownRefresh() {

			this.getDatas()

			// 实现下拉刷新
			setTimeout(() => {
				uni.stopPullDownRefresh()
			}, 500)
		},
		methods: {
			addHusbandry: function() {
				uni.navigateTo({
					url: '/pages/thirdPages/addHusbandry/addHusbandry'
				})
			},
			addFertilizer: function() {
				uni.navigateTo({
					url: '/pages/thirdPages/addFertilizer/addFertilizer'
				})
			},
			goBack: function() {
				// uni.navigateBack()
				uni.navigateTo({
					url: '/pages/indexPages/record/record'
				})
			},

			printRecord: function() {

				let that = this
				uni.request({
					url: 'http://116.62.199.133:8081/word/getword',
					method: 'get',
					success(res) {
						console.log("word: "+res.data)
					}
				})
				
				
				// uni.request({
				// 	url: 'http://116.62.199.133:8081/word/output',
				// 	method: 'get',
				// 	data: {
				// 		cropper:'番茄'
				// 	},
				// 	success(res) {
				// 		console.log("123"+res.data)
				// 	},
				// 	fail(err) {
				// 		console.log(err)
				// 	}
					
				// })


				uni.downloadFile({
					url: 'https://smart-green-home.oss-cn-hangzhou.aliyuncs.com/outmodel/279b3c51-f4db-4ac6-ad36-58a7c197caa1.docx', //下载地址接口返回
					success: (data) => {
						if (data.statusCode === 200) {
							//文件保存到本地
							uni.saveFile({
								tempFilePath: data.tempFilePath, //临时路径
								success: function(res) {
									uni.showToast({
										icon: 'none',
										mask: true,
										title: '文件已保存：' + res.savedFilePath, //保存路径
										duration: 3000,
									});
									setTimeout(() => {
										//打开文档查看
										uni.openDocument({
											filePath: res.savedFilePath,
											success: function(res) {
												// console.log('打开文档成功');
											}
										});
									}, 3000)
								}
							});
						}
					},
					fail: (err) => {
						console.log(err);
						uni.showToast({
							icon: 'none',
							mask: true,
							title: '失败请重新下载',
						});
					},
				});
			},
			getDatas: function() {
				
				let that = this
				// 定时调用获取oneNet平台监控到的环境数据
				uni.request({
					url: "http://116.62.199.133:8081/oneNet/getOnenetLatestdata",
					method: 'get',
					success(res) {
						console.log(JSON.stringify(res.data.data))

						let result = JSON.stringify(res.data.data)

						// 然后更新环境参数
						// that.partTwo[0][0].number = JSON.stringify(res.data.data[12].value).replace(
						// 	/"/g, '')
						// that.partTwo[0][1].number = JSON.stringify(res.data.data[0].value).replace(
						// 	/"/g, '')
						// that.partTwo[1][0].number = JSON.stringify(res.data.data[10].value).replace(
						// 	/"/g, '')
						// that.partTwo[1][1].number = JSON.stringify(res.data.data[7].value).replace(
						// 	/"/g, '')
						// that.partTwo[2][0].number = JSON.stringify(res.data.data[4].value).replace(
						// 	/"/g, '')
						// that.partTwo[2][1].number = JSON.stringify(res.data.data[16].value).replace(
						// 	/"/g, '')
						// that.partTwo[3][0].number = JSON.stringify(res.data.data[2].value).replace(
						// 	/"/g, '')
						
						that.partTwo[0][0].number = 24.2
						that.partTwo[0][1].number = 56
						that.partTwo[1][0].number = 34
						that.partTwo[1][1].number = 621
						that.partTwo[2][0].number = 643
						that.partTwo[2][1].number = 5.9
						that.partTwo[3][0].number = 100.72

					}
				})
			}
		},
		components: {
			recordNavigator
		}
	}
</script>

<style>
	/* page {
		background-color: #EEEEEE;
	} */

	page {
		/* background: linear-gradient(#D8F5D4, #FFFFFF); */
		background-image: url("/static/images/AgriculturalRecordBgImage.png");
		/* background-size: cover;
		background-repeat: no-repeat; */
		width: 100%;
		height: 100vh;
		box-sizing: border-box;
		position: relative;
	}


	.title_part {
		background-color: #F8F8F8;
		/* margin: 0 0 3% 0; */
		/* border-radius: 0 0 15rpx 15rpx; */
		/* box-shadow: 0 0 5px 1px #999; */
		display: flex;
		padding: 2%;
		align-items: center;
		padding-top: 10%;
	}

	.title_part .head {
		font-size: 18px;
		width: 100%;
		text-align: center;
		font-weight: bold;
	}

	/* 	.title_part .print{
		font-size: 14px;
		margin-left: 7%;
		display: flex;
		justify-content: space-between;
		align-items: center;
		width: 30%;
	} */


	.recoedNoBlank .print {
		position: absolute;
		right: 2%;
		top: 41%;
		background-color: #FEE8C9;
		font-weight: bold;
		padding: 2%;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		border-radius: 10px;
	}

	.print icon {
		font-size: 24px;
	}

	.print view {
		margin-top: 7%;
		font-size: 12px;
	}


	/* part_one部分 样式*/
	.part_one {
		/* border-radius: 0 0 25rpx 25rpx; */
		border-radius: 10rpx;
		/* box-shadow: 0 0 5px 1px #999; */
		margin: 90% 2% 2% 2%;
		box-shadow: rgba(0, 0, 0, 0.07) 0px 1px 2px, rgba(0, 0, 0, 0.07) 0px 2px 4px, rgba(0, 0, 0, 0.07) 0px 4px 8px, rgba(0, 0, 0, 0.07) 0px 8px 16px, rgba(0, 0, 0, 0.07) 0px 16px 32px, rgba(0, 0, 0, 0.07) 0px 32px 64px;
	}

	.part_two {
		margin: 2%;
		border-radius: 10rpx;
		background-color: white;
		padding-top: 3%;
		padding-bottom: 3%;
		/* box-shadow: 0 0 5px 1px #999; */
	}
	
	.part_two .wrap .row{
		display: flex;
		
	}
	
	.part_two .wrap .row .column{
		margin: 3% 3% 5% 9%;
		/* background-color: #FEE8C9; */
	}
	
	.part_two .wrap .left{
		font-weight: bold;
	}

	.top {
		position: relative;
	}

	.part_three .addHusbandry,
	.part_four .addFertilizer {
		position: absolute;
		right: 3%;
		top: 10%;
		display: flex;
		width: 20%;
	}

	.addHusbandry text,
	.addFertilizer text {
		margin-left: 5%;
	}

	.part_one,
	.part_three,
	.part_four {
		background-color: white;
		padding: 3%;
	}

	.msgBox,
	.wrap {
		margin-top: 2%;
	}

	.part_three,
	.part_four {
		margin: 2%;
		border-radius: 10rpx;
	}

	.msgBoxItem {
		padding: 2%;
	}

	.part_three .recordItem,
	.part_four .recordItem {
		display: flex;
		align-items: flex-start;
		margin-top: 7%;
		padding-left: 5%;
	}

	.recordItem .right_part {
		margin-left: 5%;
		margin-top: -1%;
	}

	.right_part .title {
		font-size: 18px;
		font-weight: bold;
	}

	.right_part .time {
		font-size: 14px;
		margin-top: 7%;
		color: gray;
	}

	/* 空白部分样式  */
</style>