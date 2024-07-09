<template>
	<view class="addCropper">
		<!-- imgs 部分 -->
		<view class="imgs">
			<image src="/static/icons/redTomato.png" mode=""></image>
			<image src="/static/icons/Strawberry.png" mode=""></image>
			<image src="/static/icons/rose.png" mode=""></image>
		</view>
		<!--添加农作物-->
		<view class="addCropperItem">
			<text>农作物图片:</text>
			<image :src="CropperPic" mode="" v-if="isChoose==true" class="afterUpload"></image>
			<view class="uploadImage" @click="uploadImages" v-else>
				<icon class="iconfont icon-zengjiatianjiajiahao"></icon>
			</view>
		</view>
		<view class="addCropperItem">
			<text>农作物名称:</text>
			<input class="right_part" type="text" placeholder="请输入农作物名称" v-model="cropperName" />
		</view>
		<view class="addCropperItem">
			<text>种植地址:</text>
			<input class="right_part" type="text" placeholder="请输入农作物种植地址" v-model="cropperAddress" />
		</view>
		<view class="addCropperItem">
			<text>种植时间:</text>
			<input class="right_part" type="text" placeholder="格式为 yyyy-mm-dd hh:mm:ss" v-model="plantTime"
				style="width: 70%;" />
		</view>

		<!--按钮-->
		<button class="button" @click="addCropper">添加</button>
	</view>
</template>

<script>
import parse from '../../../uni_modules/uview-ui/libs/config/props/parse'

	export default {
		data() {
			return {
				isChoose: false,
				// part_two
				plantTime: '', // 农作物种植时间 -- String
				plantTimeObj: Date,  // 农作物种植时间  -- Date
				CropperPic: '', // 农作物图片
				cropperName: '', //农作物名称
				cropperAddress: '' ,// 农作物种植地址
			}
		},
		methods: {
			uploadImages: function() {
				let that = this
				uni.chooseImage({
					count: 1, //默认9
					sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
					sourceType: ['album', 'camera'], //从相册选择
					success: function(res) {
						console.log(JSON.stringify(res.tempFilePaths[0]));
						that.CropperPic = res.tempFilePaths[0], // 需要修改成农作物  这个可以在upLoadFile返回结果得到后赋值
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
								that.CropperPic = uploadFileRes.data
							}
						});
					}
				});

			},
			pickTwo(e) {
				this.plantTime = e.detail.value;
			},
			addCropper: function() {

				let that = this
 
				// 我真服了，原来datetime也可以用标准日期时间格式的字符串来替代

				
				// if(this.plantTime){
					
				// 	// 首先把字符串类型转换成datetime日期类型
				// 	// 尝试将字符串转换为日期时间对象
				// 	const dateTimeParts = this.plantTime.split(' ');
				// 	const dateParts = dateTimeParts[0].split('-');
				// 	const timeParts = dateTimeParts[1].split(':');
					
				// 	// // 检查日期和时间部分是否有效
				// 	if (dateParts.length === 3 && timeParts.length === 3) {
				// 		const year = parseInt(dateParts[0]);
				// 		const month = parseInt(dateParts[1]) - 1; // 月份从0开始
				// 		const day = parseInt(dateParts[2]);
				// 		const hour = parseInt(timeParts[0]);
				// 		const minute = parseInt(timeParts[1]);
				// 		const second = parseInt(timeParts[2]);
					
				// 		// 构建日期时间对象
				// 		var dateTime = new Date(year, month, day, hour, minute, second);
						
				// 		// 2024-02-01 20:20:30
				// 		// console.log(year,month,day,hour,minute,second)
				// 		console.log(dateTime)
						
				// 		// 检查转换后的日期时间是否有效
				// 		if (!isNaN(dateTime.getTime())) {
				// 			// 日期时间有效，更新dateTimeObject
				// 			this.plantTimeObj = dateTime.toLocaleString(); // 可根据需要调整日期时间的显示格式
				// 		} else {
				// 			// 日期时间无效，重置dateTimeObject
				// 			this.plantTimeObj = null;
				// 		}
				// 	} else {
				// 		// 日期时间格式无效，重置dateTimeObject
				// 		this.plantTimeObj = null;
				// 	}
				// }

				// 调用添加农作物
				uni.request({
					url: 'http://116.62.199.133:8081/cropper/addCropper',
					method: 'get',
					data: {
						house: this.cropperAddress,
						name: this.cropperName,
						picUrl: this.CropperPic,
						plantTime: this.plantTime
					},
					success(res) {
						console.log(res.data)
						if(res.data==1){
							
							uni.showModal({
								title: that.cropperName+'农作物添加成功',
								icon: 'success',
								confirmText: "确定",
								showCancel: false,
							})
							// 这里跳转页面到？
							setTimeout(()=>{
								uni.navigateTo({
									url: '/pages/indexPages/record/record'
								})
							},1000)
						}else{
							uni.showToast({
								title: '农作物'+that.cropperName+'添加失败'
							})
						}
					}
				})
			}
		}
	}
</script>

<style>
	
	page {
		height: 100vh;
		/* background-image: url("/static/images/addCropperBg.png"); */
		background-image: url('/static/images/startBg.jpg');
		background-repeat: no-repeat;
		background-size: cover;
	}
	
	.uploadImage {
		background-color: lightgray;
		margin-left: 3%;
		padding: 5%;
		width: 5%;
	}

	.afterUpload {
		margin: 3%;
		width: 100rpx;
		height: 100rpx;
	}
	
	.addCropper .imgs image{
		width: 40rpx;
		height: 40rpx;
		margin: 0 2% 10% 2%;
		/* box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px; */
	}
	
	.addCropper .imgs{
		display: flex;
		justify-content: center;
		
	}

	.addCropper {
		padding: 5%;
		width: 80%;
		background-color: white;
		position: fixed;
		bottom: 3%;
		left: 5%;
		border-radius: 25rpx;
		box-shadow: rgba(60, 64, 67, 0.1) 0px 1px 2px 0px, rgba(60, 64, 67, 0.15) 0px 1px 3px 1px;
	}
	
	.addCropperItem {
		display: flex;
		align-items: center;
		margin-bottom: 7%;
	}
	
	
	.addCropperItem .DT{
		position: absolute;
		left: 29%;
	}
	
	.right_part {
		margin-left: 3%;
	}

	.button {
		/* background-color: #018577; */
		background-color: #C4DFAA;
		color: #4A6C4C;
		font-weight: bold;
		margin-top: 15%;
		box-shadow: rgba(60, 64, 67, 0.1) 0px 1px 2px 0px, rgba(60, 64, 67, 0.15) 0px 1px 3px 1px;
	}
</style>