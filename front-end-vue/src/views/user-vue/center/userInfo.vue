<template>
  <div class="userInfo">
    <div class="info">
      <div style="margin-bottom: 20px">
        <h2>用户姓名</h2>
      </div>
      <div class="infoDetail">
        <div class="block">
          <span class="title">读者号</span> <span>{{ user.id }}</span>
        </div>
        <div class="block">
          <span class="title">手机</span> <span>+86 {{ phoneHide(user.phone) }}</span>
        </div>
        <div class="block">
          <span class="title">邮箱</span> <span>{{ user.email }}</span>
        </div>
      </div>
    </div>
    <el-form ref="form" :model="user" label-width="80px">
      <el-form-item label="年龄">
        <el-input v-model="user.age"  style="width: 200px;"></el-input>
      </el-form-item>
      <el-form-item label="性别">
        <el-select v-model="user.sex" placeholder="请选择性别" :width="300">
          <el-option label="男" value="0"></el-option>
          <el-option label="女" value="1"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="积分">
        <el-input v-model="user.score" placeholder="请输入内容" style="width: 300px;"></el-input>
      </el-form-item>
      <el-form-item label="地址">
        <el-input v-model="user.address" style="width: 300px;"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">确认修改</el-button>
        <el-button>取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "userInfo",
  components: {},
  data() {
    return {
    //   user: Cookies.get("user") ? JSON.parse(Cookies.get("user")) : {},
    user:{
        id:'111',
        phone:'13212345678',
        email:'account',
        age:'11',
        sex:'0',
        score:'100',
        address:'abcdefg'
    }
    };
  },
  methods: {
    // 手机号做脱敏处理
    phoneHide(phone) {
      let reg = /^(1[3-9][0-9])\d{4}(\d{4}$)/; // 定义手机号正则表达式
      phone = phone.replace(reg, "$1****$2");
      return phone; // 185****6696
    },
    onSubmit(){
        console.log('修改个人信息')
    },
  },
};
</script>

<style scoped lang="less">
.userInfo {
  padding: 30px 80px;
  .info {
    margin-left: 40px;
    margin-bottom: 20px;
    .infoDetail {
      width: 300px;
      display: flex;
      flex-direction: row;
      justify-content: space-between;
      margin-bottom: 10px;
      .block {
        display: flex;
        flex-direction: column;
        .title {
          margin-bottom: 10px;
          text-align: center;
        }
      }
    }
  }
}
</style>
