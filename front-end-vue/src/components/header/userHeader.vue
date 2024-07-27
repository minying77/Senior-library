<template>
  <div class="header">
    <div class="left">
      <img class="logo" src="@/assets/logo.png" alt="" />
      <span class="title">　图书管理系统</span>
    </div>
    <div class="right">
      <el-button
        @click="centerPage"
        type="text"
        style="color: black; font-size: 14px"
        >个人中心　　</el-button
      >
      <el-button
        @click="helpPage"
        type="text"
        style="color: black; font-size: 14px"
        >帮助中心　　</el-button
      >
      <el-dropdown size="medium">
        <span class="el-dropdown-link" style="cursor: pointer">
          　　{{ user.username
          }}<i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <el-dropdown-menu slot="dropdown" style="margin-top: -5px">
          <el-dropdown-item>
            <div style="width: 60px; text-align: center" @click="editInfo">
              编辑信息
            </div>
          </el-dropdown-item>

          <el-dropdown-item>
            <div
              style="width: 60px; text-align: center"
              @click="updatePassword"
            >
              修改密码
            </div>
          </el-dropdown-item>

          <el-dropdown-item>
            <div style="width: 60px; text-align: center" @click="logout">
              退出登录
            </div>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <el-dialog
      title="修改密码"
      :visible.sync="UpdatedialogFormVisible"
      width="30%"
    >
      <el-form :model="form" label-width="100px" ref="updateFormRef">
        <el-form-item label="新密码">
          <el-input placeholder="请输入原密码"> </el-input>
        </el-form-item>

        <el-form-item label="新密码">
          <el-input placeholder="请输入新密码"> </el-input>
        </el-form-item>

        <el-form-item label="新密码">
          <el-input placeholder="请确认密码"> </el-input>
        </el-form-item>
      </el-form>
      <div style="margin-left: 30px;">
        <a href="#" @click="forgetPass">忘记密码？</a>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="UpdatedialogFormVisible = false">取消</el-button
        ><el-button type="primary" @click="changePass">确认</el-button>
      </div>
    </el-dialog>
    <el-dialog
      title="忘记密码"
      :visible.sync="ForgetdialogFormVisible"
      width="30%"
    >
      <el-form :model="form" label-width="100px" ref="forgetFormRef">
        <el-form-item label="邮箱">
          <el-input placeholder="请输入邮箱"> </el-input>
        </el-form-item>

        <el-form-item label="验证码">
          <el-input
            placeholder="请输入验证码"
            style="width: 200px; margin-right: 10px"
          >
          </el-input>
          <el-button type="primary" @click="getCode">获取验证码</el-button>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="ForgetdialogFormVisible = false">取消</el-button
        ><el-button type="primary" @click="ForgetdialogFormVisible = false"
          >确认</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>

<script>
import Cookies from "js-cookie";
export default {
  name: "userHeader",
  data() {
    return {
      form: {},
      user: Cookies.get("user") ? JSON.parse(Cookies.get("user")) : {},
      UpdatedialogFormVisible: false,
      ForgetdialogFormVisible: false,
    };
  },
  methods: {
    // 忘记密码
    forgetPass() {
      this.form = {};
      this.ForgetdialogFormVisible = true;
    },
    // 修改密码
    updatePassword() {
      this.form = Cookies.get("user") ? JSON.parse(Cookies.get("user")) : {};
      // this.form = {};
      this.UpdatedialogFormVisible = true;
    },
    changePass() {
      this.$refs["updateFormRef"].validate((valid) => {
        if (valid) {
          request.put("/user/password", this.form).then((res) => {
            if (res.code === "200") {
              this.$notify.success("修改成功");
              if (this.form.id === this.user.id) {
                Cookies.remove("user");
                this.$router.push("/login");
              } else {
                this.UpdatedialogFormVisible = false;
              }
            } else {
              this.$notify.error("修改失败");
            }
          });
        }
      });
    },
    // 编辑信息
    editInfo() {
      this.$router.push("/center/userInfo");
    },
    // 退出登录
    logout() {
      // 清除浏览器用户数据
      Cookies.remove("user");
      this.$router.push("/login");
    },
    helpPage() {
      // 在index.js中写一个path为'/helpLayout'的路由请求
      this.$router.push("/helpLayout");
    },
    centerPage() {
      // 在index.js中写一个path为'/center'的路由请求
      this.$router.push("/center");
    },
    // 获取验证码
    getCode() {
      this.$message.success("验证码已发送，请注意查收！");
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
