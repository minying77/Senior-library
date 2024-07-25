user-vue.md  
目的：说明用户登录主页的部分路由修改

1. 将原来的Aside.vue改为adminAside.vue作为管理者登录跳转的页面,将原来的Layout.vue改为adminLayout.vue作为管理者布局页面；
2. 新建userMain.vue和userLayout.vue作为用户登录跳转的页面和布局,新建userHeader.vue区分于adminHeader.vue
3. 在index.js中修改主页路由来实现不同页面跳转（需要后端写接口来调用不同页面）
```index.js
// === 主页 ===
  {
    path: '/',
    name: 'userLayout',
    component: userLayout,
    redirect: '/home',
   }
```
4.添加轮播图组件Carousel.vue('/components/Carousel.vue')
```
<template>
  <el-carousel :interval="interval" arrow="always" indicator-position="outside" @change="handleChange">
    <el-carousel-item v-for="(item, index) in items" :key="index">
      <img :src="item.image" alt="" class="carousel-item-image">
      <div class="carousel-item-text">
        <h3>{{ item.title }}</h3>
        <p>{{ item.description }}</p>
      </div>
    </el-carousel-item>
  </el-carousel>
</template>

<script>
export default {
  name: 'Carousel',
  props: {
    items: {
      type: Array,
      required: true
    },
    interval: {
      type: Number,
      default: 5000
    }
  },
  methods: {
    handleChange(index) {
      console.log(`轮播图切换到第 ${index + 1} 张`)
    }
  }
}
</script>

<style scoped>
.carousel-item-text {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  background-color: rgba(0, 0, 0, 0.5);
  color: #fff;
  padding: 16px;
  box-sizing: border-box;
}

.carousel-item-text h3 {
  margin-top: 0;
  margin-bottom: 8px;
}

.carousel-item-text p {
  margin-top: 0;
  margin-bottom: 0;
}

.carousel-item-enter-active,
.carousel-item-leave-active {
  transition: all 0.5s;
}

.carousel-item-enter,
.carousel-item-leave-to {
  opacity: 0;
}

.carousel-item-image {
  width: 100%;
  height: auto;
  object-fit: cover;
}
</style>
```
在上面的代码中，我们创建了一个名为 Carousel 的组件。该组件有两个属性：items 和 interval。items 属性用于传递轮播图的内容，每个内容包括图片和文字。interval 属性用于指定轮播图的切换时间间隔，默认为 5000 毫秒。

在组件的模板中，我们使用 Element UI 提供的 el-carousel 和 el-carousel-item 组件来展示轮播图。我们使用 v-for 指令遍历 items 数组，并使用 :src 绑定图片的 URL。在 el-carousel-item 组件内部，我们添加了一个 div 元素，用于展示文字内容。
5.在usermain.vue中实现轮播
6.user-vue的Home.vue中的分页默认按照总量为1000，平均每页100个，可通过写方法获取当前图书数量来修改total的值。


