<template>
  <div>
    <!-- 头部区域 -->
    <adminHeader />
    <!-- 侧边栏和主体 -->
    <div class="main">
      <!-- 侧边栏导航 -->
      <adminAside />
      <!-- 主体数据 -->
      <div class="container">
        <router-view />
      </div>
    </div>
  </div>
</template>

<script>
import adminHeader from "@/components/header/adminHeader";
import adminAside from "@/components/aside/adminAside";
export default {
  name: "adminLayout",
  components: { adminHeader, adminAside },
};
</script>

<style lang="less" scoped>
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
