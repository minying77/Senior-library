<template>
  <div class="aside">
    <el-menu
        :default-active="$route.path"
        :default-openeds="['user','admin']"
        router
        class="el-menu-demo"
    >
      <el-button @click="goBack"  type="text" icon="el-icon-arrow-left" style="color: black;margin-top:10px;width:130px;font-size: 15px;align-content: center" >返回首页</el-button>
      <el-menu-item index="/helpLayout/help1">
        <i class="el-icon-timer"></i>
        <span>读者须知</span>
      </el-menu-item>
      <el-menu-item index="/helpLayout/help2">
        <template slot="title">
          <i class="el-icon-finished"></i>
          <span>办证须知</span>
        </template>
      </el-menu-item>
      <el-menu-item index="/helpLayout/help3">
        <template slot="title">
          <i class="el-icon-error"></i>
          <span>遗失损毁</span>
        </template>
      </el-menu-item>
      <el-menu-item index="/helpLayout/help4">
          <i class="el-icon-tickets"></i>
          <span>政策法规</span>
      </el-menu-item>

    </el-menu>
  </div>
</template>

<script>
export default {
  name: "helpAside",
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