<template>
	<view class="devicesMethod">

		<!-- 温室大棚 图片+名称  加阴影 -->
		<view class="part_one">
			<view class="message">
				<!-- <picker mode="selector" :range="list" @change="pickerOne($event)">
					<view>{{list[selectedIndex]}}</view>
				</picker>
				<icon class="iconfont icon-xialakuangxiala"></icon> -->
				{{currentGreen}}
			</view>

			<view class="images">
				<image :src="housePic" mode=""></image>
			</view>
		</view>

		<!-- 手动控制 or 智能控制 （一键开启） -->
		<view class="controlMethod">
			<u-subsection :list="listtwo" :current="current" :animation="true" active-color="#365C3B" class="selector"
				@change="sectionChange"></u-subsection>
		</view>
		<!--icon -> 加效果-->
		<view class="leftInto" @click="gotoDevices">
			<icon class="iconfont icon-youjiantou1"></icon>
		</view>
	</view>
</template>

<script>
	import fuiDropdownMenu from "@/uni_modules/firstui-uni/firstui/fui-dropdown-menu/fui-dropdown-menu.vue"
	import list from "../../../uni_modules/uview-ui/libs/config/props/list"
	export default {
		data() {
			return {
				selectedIndex: 0,
				selectedName: String,
				// list: ['番茄大棚', '草莓大棚', '玫瑰花大棚'],
				housePic: "",
				listtwo: [{
						name: '手动控制'
					},
					{
						name: '一键控制'
					}
				],
				current: 0,
				currentGreen: "番茄大棚",
				housePic: "/static/images/tomato.png"
			}
		},
		beforeCreate() {

			let that = this

			uni.request({
				url: 'http://116.62.199.133:8081/house/houseInfo',
				method: 'get',
				data: {
					name: that.list[that.selectedIndex]
				},
				success(res) {
					console.log(res.data)
					that.housePic = res.data.housePic
				}
			})
			
			// 不管是 哪个大棚 都显示housePic图片
			
		},
		onLoad() {
			
			this.housePic = this.$globalData.currentPic
			this.currentGreen = this.$globalData.currentGreen
			
		},
		methods: {
			pickerOne: function(e) {
				this.selectedIndex = e.target.value
			},
			sectionChange(index) {
				this.current = index;
			},
			gotoDevices:function(){
				if(this.current == 0){
					uni.navigateTo({
						url: '/pages/indexPages/devices/devices'
					})
				}else{
					uni.navigateTo({
						url: '/pages/indexPages/oneClickControl/oneClickControl'
					})
				}
			}
		},
		components: {
			fuiDropdownMenu
		}
	}
</script>

<style>

	.part_one {
		margin: 27% 10% 10% 10%;
		background-color: white;
		color: #254617;
		padding: 5%;
		border-radius: 15px;
		box-shadow: rgba(255, 255, 255, 0.1) 0px 1px 1px 0px inset, rgba(50, 50, 93, 0.25) 0px 50px 100px -20px, rgba(0, 0, 0, 0.3) 0px 30px 60px -30px;
	}

	.part_one .message {
		display: flex;
		width: 50%;
		font-size: 20px;
		margin-bottom: 5%;
		font-weight: bold;
	}

	.part_one image {
		width: 260px;
		height: 260px;
		border-radius: 15rpx;
	}
	
	.controlMethod .selector{
		width: 96%;
		margin-left: 2%;
		margin-top: 20%;
	}
	
	.leftInto{
		background-color: white;
		text-align: center;
		margin-top: 21%;
		padding: 2%;
		width: 40%;
		margin-left: 30%;
		/* background-color: #92C47D; */
		background-color: #7DA47C;
		border-radius: 50rpx;
		color: white;
	}
	
	.leftInto icon{
		font-size: 36px;
	}
</style>