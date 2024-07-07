<template>
  <div class="header">
    <div class="left">
      <img class="logo" src="@/assets/logo.png" alt="" />
      <span class="title">图书管理系统</span>
    </div>
    <div class="right">
      <el-dropdown size="medium">
        <span class="el-dropdown-link" style="cursor: pointer">
          {{ admin.username }}<i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <el-dropdown-menu slot="dropdown" style="margin-top: -5px">
          <el-dropdown-item>
            <div style="width: 50px; text-align: center" @click="logout">
              退出
            </div>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import Cookies from "js-cookie";
export default {
  name: "Header",
  data() {
    return {
      admin: Cookies.get("admin") ? JSON.parse(Cookies.get("admin")) : {},
    };
  },
  methods: {
    logout() {
      // 清除浏览器用户数据
      Cookies.remove("admin");
      this.$router.push("/login");
    },
  },
};
</script>

<style lang="less" scoped>
.header {
  height: 60px;
  line-height: 60px;
  background-color: #fff;
  margin-bottom: 2px;
  display: flex;
  .left {
    width: 300px;
  }
  .right {
    flex: 1;
    text-align: right;
    padding-right: 20px;
  }
}
.logo {
  width: 40px;
  position: relative;
  top: 10px;
  left: 20px;
}
.title {
  margin-left: 25px;
  font-size: 24px;
}
</style>
