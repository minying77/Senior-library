<template>
  <div class="aside">
    <el-menu
      :default-active="$route.path==='/'?$route.path:$route.path.substring(1)"
      :default-openeds="['/']"
      router
      class="el-menu-demo"
    >
      <el-menu-item index="/">
        <i class="el-icon-eleme"></i>
        <span>首页</span>
      </el-menu-item>
      <el-submenu index="/">
        <template slot="title">
          <i class="el-icon-question"></i>
          <span>会员管理</span>
        </template>
        <el-menu-item index="addUser">会员添加</el-menu-item>
        <el-menu-item index="user">会员列表</el-menu-item>
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
