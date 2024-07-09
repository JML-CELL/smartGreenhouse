<template>
	<view class="motorizedCurtains" v-if="scrollIndex==8">
		<DevicesCommon :scrollIndex="scrollIndex"></DevicesCommon>
	</view>
</template>

<script>
	import DevicesCommon from '@/components/devicesCommon/devicesCommon.vue'
	export default {
		name:"motorizedCurtains",
		data() {
			return {
				
			};
		},
		props: {
			scrollIndex: Number
		},
		components:{
			DevicesCommon
		},
	}
</script>

<style>

</style>