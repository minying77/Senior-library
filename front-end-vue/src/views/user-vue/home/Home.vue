<template>
  <div>
  <div>
<!--    Input 为受控组件，它总会显示 Vue 绑定值。-->
<!--    通常情况下，应当处理 input 事件，并更新组件的绑定值（或使用v-model）。否则，输入框内显示的值将不会改变。-->
<!--    不支持 v-model 修饰符。-->
  <el-input v-model="input" placeholder="请输入内容" style="width: 300px;margin-left: 420px"></el-input>
  <el-button type="primary" icon="el-icon-search">搜索</el-button>
    <br/>
    <el-button type="primary" icon="el-icon-refresh-left" style="margin-left: 1035px" round plain>换一批</el-button></div>
    <br/>
  <div>
    <el-row :gutter="20" style="margin-left: 30px">
      <el-col :span="6"><div class="grid-content bg-purple"><img src="@/assets/0.jpg" width="200px" height="260px"></div></el-col>
      <el-col :span="6"><div class="grid-content bg-purple"><img src="@/assets/0.jpg" width="200px" height="260px"></div></el-col>
      <el-col :span="6"><div class="grid-content bg-purple"><img src="@/assets/0.jpg" width="200px" height="260px"></div></el-col>
      <el-col :span="6"><div class="grid-content bg-purple"><img src="@/assets/0.jpg" width="200px" height="260px"></div></el-col>
    </el-row>
    <br/>
    <el-row :gutter="20" style="margin-left: 30px">
      <el-col :span="6"><div class="grid-content bg-purple"><img src="@/assets/1.jpg" width="200px" height="260px"></div></el-col>
      <el-col :span="6"><div class="grid-content bg-purple"><img src="@/assets/1.jpg" width="200px" height="260px"></div></el-col>
      <el-col :span="6"><div class="grid-content bg-purple"><img src="@/assets/1.jpg" width="200px" height="260px"></div></el-col>
      <el-col :span="6"><div class="grid-content bg-purple"><img src="@/assets/1.jpg" width="200px" height="260px"></div></el-col>
    </el-row>
  </div>
  </div>
</template>

<script>
export default {
  name: 'userHome',
  components: {},
  data() {
    return {
      input: ''
    }
  }
}
</script>

<style scoped>
.el-row {
  margin-bottom: 20px;
}
.el-col {
  border-radius: 4px;
}
.bg-purple-dark {
  background: #99a9bf;
}
.bg-purple {
  background: #d3dce6;
}
.bg-purple-light {
  background: #e5e9f2;
}
.grid-content {
  border-radius: 4px;
  width: 200px;
  min-height: 260px;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}
</style>