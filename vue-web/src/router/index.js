import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/HomeView.vue'
import Home from '../views/Home.vue'
import store from '../store/index.js'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: Home,
	redirect: '/main',
	children: [
		{
			path: '/dashboard',
			name: 'Dashboard',
			component: ()=>import('../views/Dashboard.vue')
		},
		{
			path: '/cart',
			name: 'Cart',
			component: ()=>import('../views/Cart.vue')
		},
		{
			path: '/application',
			name: 'Application',
			component: ()=>import('../views/Application.vue')
		},
		{
			path: '/notice',
			name: 'Notice',
			component: ()=>import('../views/Notice.vue')
		}
	]
  },
  {
	path: '/login',
	name: 'Login',
	component: ()=>import('../views/LoginView.vue')
  },
  {
	path: '/homeview',
	name: 'HomeView',
	component: ()=>import('../views/HomeView.vue'),
	children: [
		{
			path: 'dashboard',
			name: 'DashboardAdmin',
			component: ()=>import('../views/DashboardAdmin.vue')
		},
		{
			path: 'email',
			name: 'Email',
			component: ()=>import('../views/Email.vue')
		},
		{
			path: 'application',
			name: 'ApplicationAdmin',
			component: ()=>import('../views/ApplicationAdmin.vue')
		},
		{
			path: 'notice',
			name: 'NoticeAdmin',
			component: ()=>import('../views/NoticeAdmin.vue')
		}
	]
  }
  
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
router.beforeEach((to, from, next) => {
	// console.log(to)
	// console.log(from)
	// console.log(next)
	let user = sessionStorage.getItem("user");
	if(user == null && to.fullPath != '/login') {
		next('/login')
	}

	// sessionStorage.setItem("currentPathName", to.name)
	// store.commit("setPath")
	next()
})


export default router
