import VueRouter from 'vue-router';
import Home from '@/views/Home';
import About from '@/views/About'

const router=new VueRouter({
    mode:'history',
    routes:[
        {
            name:'Home',
            path:'/',
            component:Home,
            meta:{title:'页面1'}
        },
        {
            name:'about',
            path:'/about',
            component:About,
            meta:{title:'关于'}
        },
    ]
})

export default router