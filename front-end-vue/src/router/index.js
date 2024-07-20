import Vue from 'vue';
import VueRouter from 'vue-router';
import Home from '@/views/admin-vue/home/Home.vue';
import Layout from '@/components/layout/adminLayout.vue';
import Cookies from 'js-cookie';
import adminLayout from "@/components/layout/adminLayout";
import userLayout from "@/components/layout/userLayout";

Vue.use(VueRouter)

const routes = [
  //=== 登录 ===
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/admin-vue/Login/Login.vue'),
  },
  // === 主页 ===
  {
    path: '/',
    name: 'userLayout',
    component: userLayout,
    redirect: '/home',
    // 子路由
    children: [
      {
        path: 'home',
        name: 'Home',
        component: () => import('@/views/user-vue/home/Home.vue')
      },
      // === user ===
      {
        path: 'userList',
        name: 'UserList',
        component: () => import('@/views/admin-vue/user/User.vue'),
      },
      {
        path: 'addUser',
        name: 'AddUser',
        component: () => import('@/views/admin-vue/user/AddUser.vue'),
      },
      {
        path: 'editUser',
        name: 'EditUser',
        component: () => import('@/views/admin-vue/user/EditUser.vue'),
      },
      // === admin ===
      {
        path: 'adminList',
        name: 'AdminList',
        component: () => import('@/views/admin-vue/admin/List.vue'),
      },
      {
        path: 'addAdmin',
        name: 'AddAdmin',
        component: () => import('@/views/admin-vue/admin/AddAdmin.vue'),
      },
      {
        path: 'editAdmin',
        name: 'EditAdmin',
        component: () => import('@/views/admin-vue/admin/EditAdmin.vue'),
      },
        // === Category ===
      { path: 'categoryList', name: 'CategoryList', component: () => import('@/views/admin-vue/category/List.vue') },
      { path: 'addCategory', name: 'AddCategory', component: () => import('@/views/admin-vue/category/Add.vue') },
      { path: 'editCategory', name: 'EditCategory', component: () => import('@/views/admin-vue/category/Edit.vue') },
      // === Book ===
      { path: 'bookList', name: 'BookList', component: () => import('@/views/admin-vue/book/List.vue') },
      { path: 'addBook', name: 'AddBook', component: () => import('@/views/admin-vue/book/Add.vue') },
      { path: 'editBook', name: 'EditBook', component: () => import('@/views/admin-vue/book/Edit.vue') },
      // === Borrow ===
      { path: 'borrowList', name: 'BorrowList', component: () => import('@/views/admin-vue/borrow/List.vue') },
      { path: 'addBorrow', name: 'AddBorrow', component: () => import('@/views/admin-vue/borrow/Add.vue') },
      { path: 'editBorrow', name: 'EditBorrow', component: () => import('@/views/admin-vue/borrow/Edit.vue') },
      // === Return ===
      { path: 'returList', name: 'returList', component: () => import('@/views/admin-vue/retur/List.vue') }
    ]
  },
  {
    path: '*',
    component: () => import('@/components/404.vue')
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
