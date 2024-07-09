<template>
	<view class="record">
		<!--首先要有基本信息，才能添加农事记录和肥药记录-->

		<!--查询-->
		<view class="search">
			<view class="searchWhite">
				<icon class="iconfont icon-sousuo"></icon>
				<input v-model="searchName" @confirm="changeIsBlankOne" class="searchInput" type="text"
					placeholder="请输入想要查询的农作物名称" confirm-type="search" focus />
			</view>
		</view>
		<!--添加-->
		<view class="blank_one">
			<image src="../../../static/images/cropper.png" mode=""></image>
			<button @click="addCropper">添加农作物</button>
		</view>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				// v-model部分
				searchName: '',
				// // 接收得到的农作物的基本信息
				// cropperMsg: {}
			};
		},
		onBackPress() {
			this.back();
			return true;
		},
		onLoad(option) {

			// 共用
			let that = this

			// // 这里判断是否显示某一种植物的农事记录
			// this.isHusbandry = option.isHusbandry
			// if(!this.isHusbandry){
			// 	uni.request({
			// 		url: 'http://116.62.199.133:8081/record/showAgriculturalRecords',
			// 		method: 'get',
			// 		data: {
			// 			cropper: option.husbandryCropper
			// 		},
			// 		success(res) {
			// 			console.log(res.data)
			// 		}
			// 	})
			// }

			// 查询接口和跳转有问题，直接跳到isblank=true的显示页面了  ，建议还是把空白页和非空白页分两个页面
		},
		methods: {
			back() {
				uni.switchTab({
					url: "/pages/index/index"
				})
			},
			addCropper: function() {
				uni.navigateTo({
					url: '/pages/thirdPages/addCropper/addCropper'
				})
			},
			changeIsBlankOne: function() {
				console.log(this.searchName)
				// 这里应该也要查一遍，查到了才能跳转
				let that = this
				uni.request({
					url: 'http://116.62.199.133:8081/cropper/cropperInfo',
					method: 'get',
					data: {
						name: that.searchName
					},
					success(res) {
						console.log(res.data)
						if (res.data.name == that.searchName) {

							// 保存查找的种植物名在本地
							uni.setStorageSync("searchName", that.searchName)

							// 查找成功，就跳转
							uni.navigateTo({
								url: '/pages/indexPages/recoedNoBlank/recoedNoBlank?searchName=' + that
									.searchName
							})
						} else {
							// 查找失败，提示
							uni.showModal({
								title: '查找失败，没有的' + that.searchName + '农事记录',
								icon: 'error',
								confirmText: "确定",
								showCancel: false,
							})
							
						}
					}
				})

			}
		}
	};
</script>

<style>

	/*  空白部分的渲染 样式*/
	.search {
		padding: 2%;
		
	}

	.searchWhite {
		background-color: white;
		display: flex;
		align-items: center;
		padding: 2%;
		border-radius: 25rpx;
		box-shadow: rgba(0, 0, 0, 0.15) 0px 5px 15px 0px;
	}

	.search .searchInput {
		padding-left: 3%;
		
	}

	.blank_one image {
		width: 100%;
	}

	.blank_one button {
		background-color: #C4DFAA;
		color: #4A6C4C;
		font-weight: bold;
		width: 94%;
		margin-top: 3%;
		box-shadow: rgba(60, 64, 67, 0.1) 0px 1px 2px 0px, rgba(60, 64, 67, 0.15) 0px 1px 3px 1px;
	}

	/* 返回接口 数据渲染  样式*/
</style>