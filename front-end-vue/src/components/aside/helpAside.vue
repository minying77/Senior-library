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
        <span>开放情况</span>
      </el-menu-item>
      <el-submenu index="user">
        <template slot="title">
          <i class="el-icon-question"></i>
          <span>读者须知</span>
        </template>
      </el-submenu>
      <el-submenu index="admin">
        <template slot="title">
          <i class="el-icon-user"></i>
          <span>办证须知</span>
        </template>
      </el-submenu>
      <el-submenu index="category">
        <template slot="title">
          <i class="el-icon-s-operation"></i>
          <span>遗失损毁</span>
        </template>
      </el-submenu>
      <el-submenu index="book">
        <template slot="title">
          <i class="el-icon-notebook-1"></i>
          <span>图书管理</span>
        </template>
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: "helpAside"
}
</script>

<style scoped>

</style>