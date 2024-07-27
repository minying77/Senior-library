<template>
    <div>
      <!-- 头部区域 -->
      <userHeader />
      <!-- 侧边栏和主体 -->
      <div class="main">
        <!-- 侧边栏导航 -->
        <centerAside />
        <!-- 主体数据 -->
        <div class="container">
          <router-view />
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import userHeader from "@/components/header/userHeader";
  import centerAside from "../aside/centerAside.vue";
  export default {
    name: "centerLayout",
    components:{ userHeader,centerAside },
  };
  </script>
  
  <style scoped>
  .main {
    display: flex;
  .container {
    flex: 1;
    width: 0;
    background-color: white;
    padding: 10px;
  }
  }
  </style>