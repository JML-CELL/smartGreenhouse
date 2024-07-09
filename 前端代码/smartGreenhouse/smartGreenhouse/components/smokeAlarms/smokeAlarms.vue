<template>
	<view class="smokeAlarms" v-if="scrollIndex==5">
		<DevicesCommon :scrollIndex="scrollIndex"></DevicesCommon>
	</view>
</template>

<script>
	import DevicesCommon from '@/components/devicesCommon/devicesCommon.vue'
	export default {
		name:"smokeAlarms",
		data() {
			return {
				
			};
		},
		components:{
			DevicesCommon
		},
		props:{
			scrollIndex: Number
		}
	}
</script>

<style>

</style>