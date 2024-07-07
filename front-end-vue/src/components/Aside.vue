<template>
  <div class="aside">
    <el-menu
      :default-active="$route.path"
      :default-openeds="['user','admin']"
      router
      class="el-menu-demo"
    >
      <el-menu-item index="/">
        <i class="el-icon-eleme"></i>
        <span>首页</span>
      </el-menu-item>
      <el-submenu index="user">
        <template slot="title">
          <i class="el-icon-question"></i>
          <span>会员管理</span>
        </template>
        <el-menu-item index="/addUser">会员添加</el-menu-item>
        <el-menu-item index="/userList">会员列表</el-menu-item>
      </el-submenu>
      <el-submenu index="admin">
        <template slot="title">
          <i class="el-icon-user"></i>
          <span>管理员管理</span>
        </template>
        <el-menu-item index="/addAdmin">管理员添加</el-menu-item>
        <el-menu-item index="/adminList">管理员列表</el-menu-item>
      </el-submenu>
    <el-submenu index="admin">
      <template slot="title">
        <i class="el-icon-s-operation"></i>
        <span>图书分类管理</span>
      </template>
      <el-menu-item index="/addACategory">图书分类添加</el-menu-item>
      <el-menu-item index="/categoryList">图书分类列表</el-menu-item>
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
