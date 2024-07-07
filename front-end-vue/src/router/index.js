import Vue from 'vue';
import VueRouter from 'vue-router';
import Home from '@/views/home/Home.vue';
import Layout from '@/views/Layout.vue';
import Cookies from 'js-cookie';

Vue.use(VueRouter)

const routes = [
  // === 登录 ===
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login/Login.vue'),
  },
  // === 主页 ===
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect: '/home',
    // 子路由
    children: [
      {
        path: 'home',
        name: 'Home',
        component: () => import('@/views/home/Home.vue')
      },
      // === user ===
      {
        path: 'userList',
        name: 'UserList',
        component: () => import('@/views/user/User.vue'),
      },
      {
        path: 'addUser',
        name: 'AddUser',
        component: () => import('@/views/user/AddUser.vue'),
      },
      {
        path: 'editUser',
        name: 'EditUser',
        component: () => import('@/views/user/EditUser.vue'),
      },
      // === admin ===
      {
        path: 'adminList',
        name: 'AdminList',
        component: () => import('@/views/admin/List.vue'),
      },
      {
        path: 'addAdmin',
        name: 'AddAdmin',
        component: () => import('@/views/admin/AddAdmin.vue'),
      },
      {
        path: 'editAdmin',
        name: 'EditAdmin',
        component: () => import('@/views/admin/EditAdmin.vue'),
      },
        // === Category ===
      { path: 'categoryList', name: 'CategoryList', component: () => import('@/views/category/List.vue') },
      { path: 'addCategory', name: 'AddCategory', component: () => import('@/views/category/Add.vue') },
      { path: 'editCategory', name: 'EditCategory', component: () => import('@/views/category/Edit.vue') }
    ]
  },
  {
    path: '*',
    component: () => import('@/views/404.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  if (to.path === '/login') return next()
  const admin = Cookies.get('admin')
  if (!admin && to.path !== '/login') return next('/login')
  next()
})

export default router
