import App from './App'
// main.js 文件
import uView from "@/uni_modules/uview-ui";
Vue.use(uView);

// #ifndef VUE3
import Vue from 'vue'

import store from './store' //引入vuex

// 挂载vuex
Vue.prototype.$store = store;

Vue.config.productionTip = false

// 创建全局数据对象
// 在[温室管理]界面，添加了新温室，需要在该全局数据上添加新温室的名称
// 其他页面访问全局数据的方式:
//     通过计算属性访问
//       computed: {
//    userInfo() {
//      // 通过计算属性访问全局数据
//      return this.$globalData.userInfo;
//    }
//  },
const globalData = {
	greenHousesNameList: ["番茄大棚", "草莓大棚", "玫瑰花大棚"],
	currentGreen: "番茄大棚",
	currentPic: "/static/images/tomoto.png",
	currentIcon: "",
	totalHouse: 0,
	totalDevice: 9,
	totalSensor: 8,
	// 环境参数
	growEnvList: [{
		img: "/static/icons/AirTemperature.png",
		en: "Air temperature",
		value: "",
		unit: '°C'
	}, {
		img: "/static/icons/AirHumidity.png",
		en: "Air humidity",
		value: "",
		unit: 'hPa'
	}, {
		img: "/static/icons/SoilMoisture.png",
		en: "Soil moisture",
		value: "",
		unit: 'hPa'
	}, {
		img: "/static/icons/illumination.png",
		en: "Illumination",
		value: "",
		unit: 'cd'
	}, {
		img: "/static/icons/CarbonDioxideConcentration.png",
		en: "Carbon dioxide concentration",
		value: "",
		unit: 'pm'
	}, {
		img: "/static/icons/WaterQualityPH.png",
		en: "Water pH",
		value: "",
		unit: 'ph'
	}, {
		img: "/static/icons/waterLevel.png",
		en: "Water Level",
		value: "",
		unit: 'm'
	}, {
		img: "/static/icons/atmosphericPressure.png",
		en: "Atmospheric Pressure",
		value: "",
		unit: 'kPa'
	}],
	// 保存一组阈值
	envParameters: [{
			high: "",
			low: "",
		},
		{
			high: "",
			low: "",
		}, {
			high: "",
			low: "",
		}, {
			high: "",
			low: "",
		}, {
			high: "",
			low: "",
		}
	]
};

// 将全局数据对象挂在到Vue原型上
Vue.prototype.$globalData = globalData;

App.mpType = 'app'
const app = new Vue({
	...App,
	store
})
app.$mount()
// #endif

// #ifdef VUE3
import {
	createSSRApp
} from 'vue'
export function createApp() {
	const app = createSSRApp(App)
	return {
		app
	}
}
// #endif