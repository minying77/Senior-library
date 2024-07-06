<template>
    <div class="header">
        <img class="logo" src="@/assets/logo.png" alt="">
        <span class="title">图书管理系统</span>
    </div>
</template>

<script>
export default {
    name: 'Header' ,
}
</script>

<style lang="less" scoped>
.header{
    height: 60px;
    line-height: 60px;
    background-color: #fff ;
    margin-bottom: 2px;
}
.logo{
    width: 40px;
    position: relative;
    top: 10px;
    left: 20px;
}
.title{
    margin-left: 25px;
    font-size: 24px;
}
</style>