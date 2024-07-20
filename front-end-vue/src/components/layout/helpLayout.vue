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
export default {
  name: "help"
}
</script>

<style scoped>

</style>