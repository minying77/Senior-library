<template>
  <div id="app">
    <!-- 头部区域 -->
    <Header/>
    <!-- 侧边栏和主体 -->
    <div class="main">
      <!-- 侧边栏导航 -->
      <Aside/>
      <!-- 主体数据 -->
      <div class="container">
          <router-view/>
      </div>
    </div>
  </div>
</template>

<script>
import Header from "@/components/Header";
import Aside from '@/components/Aside'
export default {
  name: "App",
  components: { Header,Aside },
};
</script>

<style lang="less">
body{
  background-color: #ccc;
}
.main{
  display: flex;
  .container{
    flex: 1;
    background-color: #fff;
    padding: 10px;
  }
}
</style>
