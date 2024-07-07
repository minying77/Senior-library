import VueRouter from 'vue-router';
import HomeView from '@/views/HomeView';
import AboutView from '@/views/AboutView'

const router=new VueRouter({
    mode:'history',
    routes:[
        {
            name:'HomeView',
            path:'/',
            component:HomeView,
            meta:{title:'页面1'}
        },
        {
            name:'AboutView',
            path:'/About',
            component:AboutView,
            meta:{title:'关于'}
        },
    ]
})

export default router