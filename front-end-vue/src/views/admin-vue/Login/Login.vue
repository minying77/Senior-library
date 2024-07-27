<template>
  <div style="height: 100vh; overflow: hidden; position: relative">
    <el-card class="cover" v-if="loginObj.id">
      <slide-verify
        :l="42"
        :r="10"
        :w="310"
        :h="155"
        :accuracy="5"
        :imgs="puzzleImgList"
        slider-text="向右滑动"
        @success="onSuccess"
        @fail="onFail"
        @refresh="onRefresh"
      ></slide-verify>
    </el-card>
    <div class="container">
      <el-radio-group class="text" v-model="loginRule">
        <el-radio :label="0">读者</el-radio>
        <el-radio :label="1">管理者</el-radio>
      </el-radio-group>
      <el-form :model="obj" ref="loginForm" :rules="rules">
        <el-form-item prop="username">
          <el-input
            v-model="obj.username"
            placeholder="请输入您的账号"
            prefix-icon="el-icon-user"
            size="medium"
          ></el-input>
        </el-form-item>
        <el-form-item style="margin-bottom: 60px" prop="password">
          <el-input
            v-model="obj.password"
            show-password
            placeholder="请输入您的密码"
            prefix-icon="el-icon-lock"
            size="medium"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-radio v-model="agree"
            >我同意「{{
              loginRule === 0 ? "读者" : "管理者"
            }}用户使用协议」</el-radio
          >
        </el-form-item>
        <el-form-item prop="username">
          <el-button
            @click="login"
            style="width: 100%"
            size="medium"
            type="primary"
            >登录</el-button
          >
        </el-form-item>
      </el-form>
      <div>
        还没有账号？<a href="/register" style="text-decoration: none"
          >免费注册</a
        >
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import Cookies from "js-cookie";
import img0 from "@/assets/0.jpg";
import img1 from "@/assets/1.jpg";
import img2 from "@/assets/2.jpg";
export default {
  name: "Login",
  data() {
    return {
      agree: "",
      loginRule: 0,
      puzzleImgList: [img0, img1, img2],
      obj: {},
      loginObj: {},
      rules: {
        // username: [
        //   { required: true, message: "请输入用户名", trigger: "blur" },
        //   { min: 3, max: 10, message: "长度在3-10个字符", trigger: "blur" },
        // ],
        // password: [
        //   { required: true, message: "请输入密码", trigger: "blur" },
        //   { min: 3, max: 10, message: "长度在3-10个字符", trigger: "blur" },
        // ],
      },
    };
  },
  methods: {
    login() {
      this.$refs["loginForm"].validate((valid) => {
        if (valid) {
          // this.loginObj = { ...this.obj, id: "1" };
          if (this.loginRule === 1) {
            request.post("admin/login", this.obj).then((res) => {
              if (res.code === "200") {
                this.loginObj = res.data; // 滑块出现
              } else {
                this.$notify.error(res.msg);
              }
            });
          } else {
            request.post("user/login", this.obj).then((res) => {
              if (res.code === "200") {
                this.loginObj = res.data; // 滑块出现
              } else {
                this.$notify.error(res.msg);
              }
            });
          }
        }
      });
    },
    onSuccess() {
      if (this.loginRule === 1) {
        Cookies.set("admin", JSON.stringify(this.loginObj));
      } else {
        Cookies.set("user", JSON.stringify(this.loginObj));
      }
      this.$notify.success("登录成功");
      this.$router.push("/");
    },
    onFail() {},
    onRefresh() {
      console.log("refresh");
    },
  },
};
</script>

<style lang="less" scoped>
.container {
  width: 500px;
  height: 420px;
  background-color: #fff;
  border-radius: 10px;
  margin: 150px auto;
  padding: 50px;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  .text {
    margin: 30px;
    padding: 0 30px;
    display: flex;
    justify-content: space-between;
    font-size: 30px;
    font-weight: bold;
    color: dodgerblue;
  }
}
.cover {
  width: fit-content;
  background-color: white;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 1000;
}
</style>
