<template>
  <div>
    <!-- 头部区域 -->
    <userHeader />
    <!-- 主体 -->
    <div class="main">
      <div class="container">
        <!-- 轮播-->
        <userMain/>
        <!--主页内容，用路由(index.js)实现-->
        <router-view />
      </div>
    </div>
  </div>

</template>

<script>
import userHeader from "@/components/header/userHeader";
import userMain from "@/components/main/userMain";
export default {
  name: "userLayout",
  components: { userHeader, userMain },
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
