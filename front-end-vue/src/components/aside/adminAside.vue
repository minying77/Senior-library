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
    <el-submenu index="category">
      <template slot="title">
        <i class="el-icon-s-operation"></i>
        <span>图书分类管理</span>
      </template>
      <el-menu-item index="/addCategory">图书分类添加</el-menu-item>
      <el-menu-item index="/categoryList">图书分类列表</el-menu-item>
    </el-submenu>
      <el-submenu index="book">
        <template slot="title">
          <i class="el-icon-notebook-1"></i>
          <span>图书管理</span>
        </template>
        <el-menu-item index="/addBook">图书添加</el-menu-item>
        <el-menu-item index="/bookList">图书列表</el-menu-item>
      </el-submenu>
      <el-submenu index="borrow">
        <template slot="title">
          <i class="el-icon-document-copy"></i>
          <span>借书管理</span>
        </template>
        <el-menu-item index="/addBorrow">借书添加</el-menu-item>
        <el-menu-item index="/borrowList">借书列表</el-menu-item>
      </el-submenu>
      <el-submenu index="retur">
        <template slot="title">
          <i class="el-icon-document"></i>
          <span>还书管理</span>
        </template>
        <el-menu-item index="/returList">还书列表</el-menu-item>
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: "adminAside",
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
