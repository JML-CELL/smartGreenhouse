<template>
	<view class="dripIrrigation"  v-if="scrollIndex==7">
		<DevicesCommon :scrollIndex="scrollIndex"></DevicesCommon>
	</view>
</template>

<script>
	import DevicesCommon from '@/components/devicesCommon/devicesCommon.vue'
	export default {
		name:"dripIrrigation",
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