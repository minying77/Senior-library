<template>
    <div class="aside">
      <el-menu
          :default-active="$route.path"
          :default-openeds="['user','admin']"
          router
          class="el-menu-demo"
      >
        <el-button @click="goBack"  type="text" icon="el-icon-arrow-left" style="color: black;margin-top:10px;width:130px;font-size: 15px;align-content: center" >返回首页</el-button>
        <el-menu-item index="/center/userInfo">
          <i class="el-icon-info"></i>
          <span>个人信息</span>
        </el-menu-item>
        <el-menu-item index="/center/myBorrow">
          <template slot="title">
            <i class="el-icon-notebook-2"></i>
            <span>我的借阅</span>
          </template>
        </el-menu-item>
        <el-menu-item index="/center/myMessage">
          <template slot="title">
            <i class="el-icon-message"></i>
            <span>我的留言</span>
          </template>
        </el-menu-item>
  
      </el-menu>
    </div>
  </template>
  
  <script>
  export default {
    name: "centerAside",
    components: {},
    methods: {
      goBack() {
        this.$router.push('/home');
        console.log('go back');
      }
    }
  };
  </script>
  
  <style lang="less" scoped>
  .aside {
    width: 160px;
    overflow: hidden;
    margin-right: 2px;
    background-color: #fff;
    min-height: calc(100vh - 62px);
  }
  </style>