<template>
	<view class="pesticideSprayers" v-if="scrollIndex==6">
		<DevicesCommon :scrollIndex="scrollIndex"></DevicesCommon>
	</view>
</template>

<script>
	import DevicesCommon from '@/components/devicesCommon/devicesCommon.vue'
	export default {
		name:"pesticideSprayers",
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