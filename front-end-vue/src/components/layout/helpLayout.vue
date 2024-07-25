<template>
  <div>
    <!-- 头部区域 -->
    <userHeader />
    <!-- 侧边栏和主体 -->
    <div class="main">
      <!-- 侧边栏导航 -->
      <helpAside />
      <!-- 主体数据 -->
      <div class="container">
        <router-view />
      </div>
    </div>
  </div>
</template>

<script>
import userHeader from "@/components/header/userHeader";
import helpAside from "@/components/aside/helpAside";
export default {
  name: "helpLayout",
  components:{ userHeader,helpAside },
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