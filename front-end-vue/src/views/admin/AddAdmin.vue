<template>
  <div style="width: 80%">
    <div style="margin-bottom: 30px">新增管理员</div>
    <el-form
      :inline="true"
      :model="form"
      :rules="rules"
      ref="ruleForm"
      label-width="100px"
    >
      <el-form-item label="用户名" prop="name">
        <el-input v-model="form.name" placeholder="请输入用户名"></el-input>
      </el-form-item>
      <el-form-item label="联系方式" prop="phone">
        <el-input v-model="form.phone" placeholder="请输入联系方式"></el-input>
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="form.email" placeholder="请输入邮箱"></el-input>
      </el-form-item>
    </el-form>
    <div style="text-align: center; margin-bottom: 30px">
      <el-button type="primary" @click="save" size="medium">提交</el-button>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
export default {
  name: "AddAdmin",
  data() {
    const checkPhone = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("手机不能为空"));
      }
      setTimeout(() => {
        if (!/^1[0-9]{10}$/.test(value)) {
          callback(new Error("请输入合法的手机号"));
        } else {
          callback();
        }
      }, 1000);
    };
    return {
      form: {},
      rules: {
        name: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          { min: 2, max: 10, message: "长度在3-10个字符", trigger: "blur" },
        ],
        phone: [{ validator: checkPhone, trigger: "blur" }],
      },
    };
  },
  methods: {
    save() {
      this.$refs["ruleForm"].validate((valid) => {
        if (valid) {
          request.post("/admin/add", this.form).then((res) => {
            if (res.code === "200") {
              this.$notify.success("新增成功");
              this.form = {};
            } else {
              this.$notify.error(res.msg);
            }
          });
        }
      });
    },
  },
  components: {},
};
</script>

<style scoped></style>
