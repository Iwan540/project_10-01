import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/css/normal.css';
import request from './utils/request.js'
import VuePageTransition from 'vue-page-transition'
import moment from 'moment/moment'
import locale from 'element-ui/lib/locale/lang/en';

Vue.use(ElementUI, { locale });

Vue.use(ElementUI, {size: "medium"})
Vue.use(VuePageTransition)
Vue.config.productionTip = false
Vue.prototype.axios = request
Vue.filter('moment', function (value, formatString) {
    formatString = formatString || 'YYYY-MM-DD';
	return moment(value).format(formatString);
});

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
