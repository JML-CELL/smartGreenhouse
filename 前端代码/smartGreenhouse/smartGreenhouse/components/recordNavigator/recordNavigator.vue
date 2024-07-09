<template>
	<view class="recordComponent">
		<text style="font-weight: bold;">{{title}}</text>
		<view class="Greenline"></view>
	</view>
</template>

<script>
	export default {
		name: "recordNavigator",
		data() {
			return {

			}
		},
		methods: {

		},
		props: {
			title: String
		}
	}
</script>

<style>
	.recordComponent {
		display: flex;
		justify-content: center;
		align-items: center;
		flex-direction: column;
		margin-top: 1%;
	}

	.Greenline {
		border-bottom: 3px solid #92C47D;
		border-top: 0px;
		border-left: 0px;
		border-right: 0px;
		width: 20%;
	}
</style>