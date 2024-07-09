<template>
	<view class="chat">
		<scroll-view :style="{height: `${windowHeight-inputHeight}rpx`}" id="scrollview" scroll-y="true"
			:scroll-top="scrollTop" class="scroll-view">
			<!-- 聊天主体 -->
			<view id="msglistview" class="chat-body">
				<!-- 聊天记录 -->
				<view v-for="(item,index) in msgList" :key="index">
					<!-- 自己发的消息 -->
					<view class="item self" v-if="item.userContent != ''">
						<!-- 文字内容 -->
						<view class="content right">
							{{item.userContent}}
						</view>
						<!-- 头像 -->
						<view class="avatar">
							<image :src="image" mode=""></image>
						</view>
					</view>
					<!-- 机器人发的消息 -->
					<view class="item Ai" v-if="item.botContent != ''">
						<!-- 头像 -->
						<view class="avatar">
							<image src="../../static/icons/SmartAssistants.jpg" mode=""></image>
						</view>
						<!-- 文字内容 -->
						<view class="content left" v-if="item.botContent != ''">
							{{item.botContent}}
						</view>
					</view>
				</view>
				<Loading v-if="isShow==true"></Loading>
			</view>
		</scroll-view>
		<!-- 底部消息发送栏 -->
		<!-- 用来占位，防止聊天消息被发送框遮挡 -->
		<view class="chat-bottom" :style="{height: `${inputHeight}rpx`}">
			<view class="send-msg" :style="{bottom:`${keyboardHeight}rpx`}">
				<view class="uni-textarea">
					<textarea v-model="chatMsg" maxlength="300" confirm-type="send" @confirm="handleSend"
						:show-confirm-bar="false" :adjust-position="false" @linechange="sendHeight" @focus="focus"
						@blur="blur" auto-height></textarea>
				</view>
				<button @click="handleSend" class="send-btn">发送</button>
			</view>
		</view>
	</view>
</template>
<script>
	import Loading from '@/components/Loading/Loading.vue'
	export default {
		data() {
			return {
				image: '/staic/title.png',
				isShow: false,
				//键盘高度
				keyboardHeight: 0,
				//底部消息发送高度
				bottomHeight: 0,
				//滚动距离
				scrollTop: 0,
				userId: '',
				//发送的消息
				chatMsg: "",
				msgList: [{
						botContent: "你好，请问有什么可以帮助你的吗？",
						recordId: 0,
						titleId: 0,
						userContent: "",
						userId: 0
					}
				]
			}
		},
		updated() {
			//页面更新时调用聊天消息定位到最底部
			this.scrollToBottom();
		},
		computed: {
			windowHeight() {
				return this.rpxTopx(uni.getSystemInfoSync().windowHeight)
			},
			// 键盘弹起来的高度+发送框高度
			inputHeight() {
				return this.bottomHeight + this.keyboardHeight
			}
		},
		onLoad() {
			uni.onKeyboardHeightChange(res => {
				//这里正常来讲代码直接写
				//this.keyboardHeight=this.rpxTopx(res.height)就行了
				//但是之前界面ui设计聊天框的高度有点高,为了不让键盘和聊天输入框之间距离差太大所以我改动了一下。
				this.keyboardHeight = this.rpxTopx(res.height - 50)
				if (this.keyboardHeight < 0) this.keyboardHeight = 0;
			})
		},
		onShow() {
			let that = this
			// 再获取用户头像
			uni.getStorage({
				key: 'userUrl',
				success: function(res) {
					console.log(res.data)
					that.image = res.data
				}
			})
		},
		onUnload() {
			uni.offKeyboardHeightChange()
		},
		methods: {
			focus() {
				this.scrollToBottom()
			},
			blur() {
				this.scrollToBottom()
			},
			// px转换成rpx
			rpxTopx(px) {
				let deviceWidth = wx.getSystemInfoSync().windowWidth
				let rpx = (750 / deviceWidth) * Number(px)
				return Math.floor(rpx)
			},
			// 监视聊天发送栏高度
			sendHeight() {
				setTimeout(() => {
					let query = uni.createSelectorQuery();
					query.select('.send-msg').boundingClientRect()
					query.exec(res => {
						this.bottomHeight = this.rpxTopx(res[0].height)
					})
				}, 10)
			},
			// 滚动至聊天底部
			scrollToBottom(e) {
				setTimeout(() => {
					let query = uni.createSelectorQuery().in(this);
					query.select('#scrollview').boundingClientRect();
					query.select('#msglistview').boundingClientRect();
					query.exec((res) => {
						if (res[1].height > res[0].height) {
							this.scrollTop = this.rpxTopx(res[1].height - res[0].height)
						}
					})
				}, 15)
			},
			// 发送消息
			handleSend() {
				let that = this
				
				//如果消息不为空
				if (!this.chatMsg || !/^\s+$/.test(this.chatMsg)) {
					// 如果用户消息不为空就再创建一条用户信息
					let obj = {
						botContent: "",
						recordId: 0,
						titleId: 0,
						userContent: this.chatMsg,
						userId: 0
					}
					this.msgList.push(obj);
					// 然后再调用  gpt  回答用户 并将其push进msgChat
					// 调用接口
					let tempAnswer = ""
					uni.request({
						url: 'http://116.62.199.133:8081/gpt/robot',
						method: 'get',
						data: {
							content: that.chatMsg
						},
						success(res) {
							// console.log(res.data)
							tempAnswer = res.data
							console.log(tempAnswer)
						}
					})
					that.isShow = true
					setTimeout(()=>{
						// 创建机器人对话并添加到消息列表
						let objTwo = {
							botContent: tempAnswer,
							recordId: 0,
							titleId: 0,
							userContent: "",
							userId: 0
						}
						that.msgList.push(objTwo);
						
						console.log(that.msgList.length)
						
						that.isShow=false
					},10000)
					
					this.chatMsg = "";
					this.scrollToBottom()
				} else {
					uni.showToast({
						title: '不能发送空白消息',
						icon: 'error'
					})
				}
			},
		},
		components: {
			Loading
		}
	}
</script>
<style lang="scss" scoped>
	$chatContentbgc: #D8F5D4;
	$sendBtnbgc: #C4DFAA;
	
	view,
	button,
	text,
	input,
	textarea {
		margin: 0;
		padding: 0;
		box-sizing: border-box;
	}

	/* 聊天消息 */
	.chat {
		.scroll-view {
			::-webkit-scrollbar {
				display: none;
				width: 0 !important;
				height: 0 !important;
				-webkit-appearance: none;
				background: transparent;
				color: transparent;
			}

			

			.chat-body {
				display: flex;
				flex-direction: column;
				padding-top: 23rpx;
				// background-color:skyblue;

				.self {
					justify-content: flex-end;
				}

				.item {
					display: flex;
					padding: 23rpx 30rpx;
					// background-color: greenyellow;

					.right {
						background-color: $sendBtnbgc;
					}

					.left {
						background-color: #FFFFFF;
					}

					// 聊天消息的三角形
					.right::after {
						position: absolute;
						display: inline-block;
						content: '';
						width: 0;
						height: 0;
						left: 100%;
						top: 10px;
						border: 12rpx solid transparent;
						border-left: 12rpx solid $chatContentbgc;
					}

					.left::after {
						position: absolute;
						display: inline-block;
						content: '';
						width: 0;
						height: 0;
						top: 10px;
						right: 100%;
						border: 12rpx solid transparent;
						border-right: 12rpx solid #FFFFFF;
					}

					.content {
						position: relative;
						max-width: 486rpx;
						border-radius: 8rpx;
						word-wrap: break-word;
						padding: 24rpx 24rpx;
						margin: 0 24rpx;
						border-radius: 5px;
						font-size: 32rpx;
						font-family: PingFang SC;
						font-weight: 500;
						color: #333333;
						// line-height: 42rpx;
					}

					.avatar {
						display: flex; 
						justify-content: center;
						
						// background: $sendBtnbgc;
						// border-radius: 8rpx;
						overflow: hidden;
						// border-radius: 50%;

						image {
							width: 120rpx;
							height: 120rpx;
							align-self: flex-start;
						}

					}
				}
			}
		}

		/* 底部聊天发送栏 */
		.chat-bottom {
			width: 100%;
			height: 100rpx;
			background: #F4F5F7;
			transition: all 0.1s ease;


			.send-msg {
				display: flex;
				align-items: center;
				width: 100%;
				position: fixed;
				bottom: 0;
				background: #EDEDED;
				transition: all 0.1s ease;
				// margin-bottom: 3%;
				padding: 3% 5%;
			}

			.uni-textarea {
				// padding-bottom: 70rpx;

				textarea {
					width: 537rpx;
					min-height: 75rpx;
					max-height: 500rpx;
					background: #FFFFFF;
					border-radius: 8rpx;
					font-size: 32rpx;
					font-family: PingFang SC;
					color: #333333;
					line-height: 75rpx;
					padding: 5rpx 8rpx;
				}
			}

			.send-btn {
				display: flex;
				align-items: center;
				justify-content: center;
				margin-left: 25rpx;
				width: 128rpx;
				height: 75rpx;
				background: $sendBtnbgc;
				border-radius: 8rpx;
				font-size: 28rpx;
				font-family: PingFang SC;
				font-weight: 500;
				color: #4A6C4C;
				line-height: 75rpx;
			}
		}
	}
</style>