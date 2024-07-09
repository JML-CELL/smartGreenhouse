<template>
	<view class="Humidifier" v-if="scrollIndex==3">
		<DevicesCommon :scrollIndex="scrollIndex"></DevicesCommon>
	</view>
</template>

<script>
	import DevicesCommon from '@/components/devicesCommon/devicesCommon.vue'
	export default {
		name:"Humidifier",
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