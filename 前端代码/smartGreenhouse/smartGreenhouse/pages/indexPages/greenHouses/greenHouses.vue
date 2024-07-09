<template>
	<view class="greenHouses">
		<!--查询-->
		<view class="search">
			<view class="searchWhite">
				<icon class="iconfont icon-sousuo" style="color: #4A6C4C;"></icon>
				<input v-model="searchName" @confirm="changeIsBlankOne" class="searchInput" type="text"
					placeholder="请输入想要查询的温室名称" confirm-type="search" focus />
			</view>
			<view class="addGreenHouse" @click="toggle('bottom')">
				<icon class="iconfont icon-zengjiatianjiajiahao" style="color: #4A6C4C;"></icon>
			</view>
		</view>
		<!-- 返回内容 -->
		<view class="greenHouse">
			<!--等新接口好了再改 -->
			<view class="greenHouseItem" v-for="(item, index) in greenHouses" :key="index">
				<view class="part_one" :style="{backgroundImage:`url(${item.housePic})`}">
				</view>
				<view class="part_two">
					<view class="partTwoItem">

						<view class="text">
							<text class="left" style="font-weight: bold;">温室名称:</text>
							<text class="right">{{ item.name }}</text>
						</view>
						<view class="text">
							<text class="left" style="font-weight: bold;">温室尺寸:</text>
							<text class="right">{{ item.length }} X {{ item.wide }} X {{ item.high }} m</text>
						</view>
						<view class="text">
							<text class="left" style="font-weight: bold;">作物名称:</text>
							<text class="right">{{ item.cropper }}</text>
						</view>
					</view>
				</view>

			</view>

			<!-- <view class="greenHouseItem"> -->
			<!-- <view class="part_one" :style="{backgroundImage:`url(${housePic})`}"> -->
			<!-- <image :src="housePic" mode="" style="width: 100%;"></image> -->
			<!-- </view>
				<view class="part_two">
					<view class="partTwoItem">
						
						<view class="text">
							<text class="left" style="font-weight: bold;">温室名称:</text>
							<text class="right">{{ name }}</text>
						</view>
						<view class="text">
							<text class="left" style="font-weight: bold;">温室尺寸:</text>
							<text class="right">{{ length }} X {{ width }} X {{ high }} m</text>
						</view>
						<view class="text">
							<text class="left" style="font-weight: bold;">作物名称:</text>
							<text class="right">{{ cropper }}</text>
						</view> -->
			<!-- <view class="text">
								<text class="left" style="font-weight: bold;">作物品种:</text>
								<text class="right">{{ item.cropVariety }}</text>
							</view> -->
			<!-- </view>
				</view> -->
			<!-- </view>/ -->
		</view>

		<!--添加按钮-->
		<!-- <view class="addHouse" @click="toggle('bottom')">
			<icon class="iconfont icon-zengjiatianjiajiahao"></icon>
			<text style="margin-left: 2%;">添加温室</text>
		</view> -->

		<!-- 普通弹窗 -->
		<uni-popup ref="popup" background-color="#fff" @change="change">
			<view class="context">
				<view class="head">
					添加温室
					<recordNavigator></recordNavigator>
				</view>

				<view class="contextItem">
					<text class="left">温室照片:</text>
					<image :src="tempPic" mode="" v-if="isChoose==true" class="afterUpload"></image>
					<view class="uploadImage" @click="uploadImages" v-else>
						<icon class="iconfont icon-zengjiatianjiajiahao"></icon>
					</view>
				</view>
				<view class="contextItem">
					<text class="left">温室名称:</text>
					<input class="input" @input="nameChange" placeholder="请输入温室名称" v-model="tempName" />
				</view>
				<view class="contextItem">
					<text class="left">温室长度:</text>
					<input class="input" @input="lengthChange" placeholder="请输入温室大棚长度" v-model="tempLength" />
				</view>
				<view class="contextItem">
					<text class="left">温室宽度:</text>
					<input class="input" @input="widthChange" placeholder="请输入温室大棚宽度" v-model="tempWidth" />
				</view>
				<view class="contextItem">
					<text class="left">温室高度:</text>
					<input class="input" @input="highChange" placeholder="请输入温室大棚高度" v-model="tempHigh" />
				</view>
				<view class="contextItem">
					<text class="left">作物名称:</text>
					<input class="input" @input="cropNameChange" placeholder="请输入作物名称" v-model="tempCropper" />
				</view>
				<!-- <view class="contextItem">
					<text>作物品种:</text>
					<input class="input" @input="cropVarietyChange" placeholder="请输入作物品种" />
				</view> -->
				<button class="button" @click="closePopup('bottom')">确定</button>
			</view>
		</uni-popup>
	</view>
</template>

<script>
	import recordNavigator from '@/components/recordNavigator/recordNavigator.vue'
	export default {
		data() {
			return {
				greenHouses: [],
				id: 0,
				housePic: "", // 页面图片显示
				name: "",
				high: Number,
				width: Number,
				cropper: "",
				length: Number,
				// cropVariety: '奶油草莓'
				isChoose: false, // 判断是否点击上传图片的标志位
				// tempGreenHouse: {
				// 	tempPic: String,  // pop弹窗图片显示
				// 	tempName:String,
				// 	tempHigh:Number,
				// 	tempWidth:Number,
				// 	tempCropper:String,
				// 	tempLength:Number
				// },
				tempPic: "", // pop弹窗图片显示
				tempName: "",
				tempHigh: "",
				tempWidth: "",
				tempCropper: "",
				tempLength: "",
				searchName: ""

			};
		},
		components: {
			recordNavigator
		},
		onShow() {
			this.getHouseInfo()
		},
		methods: {
			// 返回温室基本信息的接口
			getHouseInfo: function() {

				let that = this
				// 这里只实现了根据name返回一个温室的功能，这个地方后面改： 改成返回所有温室
				uni.request({
					url: 'http://116.62.199.133:8081/house/allShowHouse',
					method: 'get',
					success(res) {
						// that.id = res.data.id,
						// that.name = res.data.name,
						// that.length = res.data.length,
						// that.width = res.data.wide,
						// that.high = res.data.high,
						// that.cropper = res.data.cropper,
						// that.housePic = res.data.housePic

						const result = res.data

						console.log(Array.isArray(res.data))

						that.greenHouses = result

						console.log(that.greenHouses)

					}
				})
			},
			open: function() {
				// 通过组件定义的ref调用uni-popup方法 ,如果传入参数 ，type 属性将失效 ，仅支持 ['top','left','bottom','right','center']
				this.$refs.popup.open('bottom');
			},
			toggle: function(type) {
				this.type = type;
				// open 方法传入参数 等同在 uni-popup 组件上绑定 type属性
				this.$refs.popup.open(type);
			},
			change: function(e) {
				console.log('当前模式：' + e.type + ',状态：' + e.show);
			},
			nameChange: function(event) {
				this.tempName = event.target.value;
				console.log(this.tempName);
			},
			lengthChange: function(event) {
				this.tempLength = event.target.value;
				console.log(this.tempLength);
			},
			widthChange: function(event) {
				this.tempWidth = event.target.value;
				console.log(this.tempWidth);
			},
			highChange: function(event) {
				this.tempHigh = event.target.value;
				console.log(this.tempHigh);
			},
			cropNameChange: function(event) {
				this.tempCropper = event.target.value;
				console.log(this.tempCropper);
			},
			// cropVarietyChange: function(event) {
			// 	this.cropVariety = event.target.value;
			// 	console.log(this.inputMax);
			// },
			closePopup: function(type) {

				let that = this
				console.log(typeof(Number(that.tempWidth)))
				//调用添加温室接口
				uni.request({
					url: 'http://116.62.199.133:8081/house/addHouse',
					method: 'get',
					data: {
						cropper: that.tempCropper,
						// high: that.tempHigh,
						high: Number(that.tempHigh),
						housePic: that.tempPic,
						// length: that.tempLength,
						length: Number(that.tempLength),
						name: that.tempName,
						// wide: that.tempWidth
						wide: Number(that.tempWidth)
					},
					success(res) {
						console.log(res.data)
						// 提示添加成功
						setTimeout(()=>{
							uni.showToast({
								title: '添加成功',
								icon: 'success'
							})
						},500)
						// 全局数据更新
						that.$globalData.greenHousesNameList.push(that.tempName)
						that.getHouseInfo()
						// 
					}
				})
				
				// uni.showToast({
				// 	title: '温室名称已存在',
				// 	icon: 'error'
				// })
				// setTimeout(()=>{
					
				// },500)
				
				that.isChoose = false
				this.type = type
				this.$refs.popup.close(type)
			},
			uploadImages: function() {
				let that = this
				uni.chooseImage({
					count: 1, //默认9
					sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
					sourceType: ['album', 'camera'], //从相册选择
					success: function(res) {
						// console.log(JSON.stringify(res.tempFilePaths[0]));
						// that.housePic = res.tempFilePaths[0],
						that.isChoose = true
						uni.uploadFile({
							url: 'http://116.62.199.133:8081/upload/img', //仅为示例，非真实的接口地址
							method: 'post',
							filePath: res.tempFilePaths[0],
							name: 'fileImage',
							formData: {
								'user': 'test'
							},
							success: (uploadFileRes) => {
								console.log(uploadFileRes.data);
								that.tempPic = uploadFileRes.data
							}
						});
					}
				});

			},
			changeIsBlankOne: function() {
				console.log(this.searchName)
				// 这里应该也要查一遍，查到了才能跳转
				let that = this
				uni.request({
					url: 'http://116.62.199.133:8081/house/houseInfo',
					method: 'get',
					data: {
						name: that.searchName
					},
					success(res) {

						// that.greenHouses = res.data
						if (res.data.name == that.searchName) {
							that.greenHouses = []
							that.greenHouses.push(res.data)
							
							console.log(res.data)
							console.log(that.greenHouses.length)
						} else {
							that.greenHouses = []
							// 查找失败，提示
							uni.showModal({
								title: '查找失败，没有的' + that.searchName + '温室',
								icon: 'error',
								confirmText: "确定",
								showCancel: false,
							})

						}
					}
				})

			},

		},
	};
</script>

<style scoped>
	/*  空白部分的渲染 样式*/
	.search {
		padding: 2%;
		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.searchWhite {
		background-color: white;
		display: flex;
		align-items: center;
		padding: 2%;
		border-radius: 25rpx;
		width: 81%;
		box-shadow: rgba(0, 0, 0, 0.15) 0px 5px 15px 0px;
	}

	.search .addGreenHouse {
		margin-right: 1%;
		background-color: white;
		padding: 2%;
		border-radius: 25rpx;
		box-shadow: rgba(0, 0, 0, 0.15) 0px 5px 15px 0px;
	}

	.search .addGreenHouse .iconfont {
		font-size: 24px;
	}

	.search .searchInput {
		padding-left: 3%;
	}

	.greenHouseItem {
		background-color: white;
		margin: 2%;
		padding: 3%;
		border-radius: 25rpx;
		/* box-shadow: 0 0 5px 1px #999 */
		box-shadow: rgba(0, 0, 0, 0.15) 0px 5px 15px 0px;
	}

	/* page {
		background-color: #EEEEEE;
	} */
	.greenHouses {
		/* background: linear-gradient(#D8F5D4, #FFFFFF);
		width: 100%;
		min-height: 100vh;
		box-sizing: border-box; */
	}

	.uploadImage {
		background-color: lightgray;
		margin-left: 3%;
		padding: 5%;

	}

	.afterUpload {
		margin: 3%;
		width: 100rpx;
		height: 100rpx;
	}

	.part_one {
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		height: 500rpx;
		background-size: 100% 100%;
		background-repeat: no-repeat;
		border-radius: 25rpx;
	}

	.partTwoItem {
		margin: 2%;
	}

	.partTwoItem .text {
		padding-bottom: 2%;
	}

	.partTwoItem .right {
		margin-left: 2%;
	}

	.addHouse {
		display: flex;
		align-items: center;
		justify-content: center;
		font-size: 18px;
		padding: 5% 0;
	}

	.contextItem {
		display: flex;
		margin: 7% 5%;
		align-items: center;
	}

	.context .head {
		font-weight: bold;
		font-size: 20px;
		margin: 3% 0 9% 0;
		text-align: center;
	}

	.contextItem .left {
		font-weight: bold;
	}

	.button {
		background-color: #C4DFAA;
		margin: 7% 5%;
		color: #4A6C4C;
		font-weight: bold;
	}

	.input {
		margin-left: 3%;
	}
</style>