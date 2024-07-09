<template>
  <div>
    <!-- 头部区域 -->
    <Header />
    <!-- 侧边栏和主体 -->
    <div class="main">
      <!-- 侧边栏导航 -->
      <Aside />
      <!-- 主体数据 -->
      <div class="container">
        <router-view />
      </div>
    </div>
  </div>
</template>

<script>
import Header from "@/components/Header";
import Aside from "@/components/Aside";
export default {
  name: "Layout",
  components: { Header, Aside },
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
