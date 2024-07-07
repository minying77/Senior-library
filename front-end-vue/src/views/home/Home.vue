<template>
  <div>
    <div class="item">用户名：{{ admin.adminname }}</div>
    <div>手机号：{{ admin.phone }}</div>
    <div>邮箱：{{ admin.email }}</div>
  </div>
</template>

<script>
import Cookies from "js-cookie";
import request from "@/utils/request";
export default {
  name: "Home",
  data() {
    return {
      admin: Cookies.get("admin") ? JSON.parse(Cookies.get("admin")) : {},
    };
  },
  created() {
    // request.get("/admin/"+this.admin.id, this.form).then((res) => {
    //   if (res.code === "200") {
    //     this.admin=res.data
    //   } else {
    //     this.$notify.error(res.msg);
    //   }
    // });
  },
};
</script>

<style scoped>
.item {
  margin: 10px 0;
}
</style>
