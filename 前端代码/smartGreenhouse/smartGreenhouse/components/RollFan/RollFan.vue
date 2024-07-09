<template>
	<view class="RollFan" v-if="scrollIndex==2">
		<DevicesCommon :scrollIndex="scrollIndex"></DevicesCommon>
	</view>
</template>

<script>
	import DevicesCommon from '@/components/devicesCommon/devicesCommon.vue'
	export default {
		name: "RollFan",
		data() {
			return {

			};
		},
		components: {
			DevicesCommon
		},
		props: {
			scrollIndex: Number
		}
	}
</script>

<style>

</style>