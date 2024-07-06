<template>
  <div class="aside">
    <el-menu :default-active="$route.path" :default-openeds="['/']" router class="el-menu-demo">
      <el-menu-item index="/">
        <i class="el-icon-eleme"></i>
        <span>首页</span>
      </el-menu-item>
      <el-submenu index="/">
        <template slot="title">
          <i class="el-icon-question"></i>
          <span>关于页面</span>
        </template>
        <el-menu-item index="/about">关于详情</el-menu-item>
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: "Aside",
  components: {},
};
</script>

<style lang="less" scoped>
.aside {
  width: 200px;
  overflow: hidden;
  margin-right: 2px;
  background-color: #fff;
  min-height: calc(100vh - 62px);
}
</style>
