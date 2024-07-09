<script>
	import {
		mapMutations
	} from 'vuex'
	export default {
		onLaunch: function() {
			console.log('App Launch')
			let userInfo = uni.getStorageInfoSync('userInfo') || ''
				if (userInfo.id) {
					uni.getStorage({
						key: 'userInfo',
						success: res => {
							this.login(res.data)
						}
					})
				}
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		},
		methods: {
			...mapMutations(['login'])
		}
		// globalData:{

		// }
	}
</script>

<style>
	/*每个页面公共css */

	/* 引入阿里巴巴图标库 */
	@import url('static/iconfont.css');
	/* page{
		background: linear-gradient(#D8F5D4, #FFFFFF);
	} */

	page {
		/* background: linear-gradient(#D8F5D4, #FFFFFF); */
		background-color: #E6EFDE;
		width: 100%;
		min-height: 100vh;
		box-sizing: border-box;
	}
</style>