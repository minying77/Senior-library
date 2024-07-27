<template>
  <div>
    <div>
      <el-table :data="tableData" stripe row-key="id" default-expand-all>
        <el-table-column prop="id" label="编号" width="80"></el-table-column>
        <el-table-column
          prop="createTime"
          label="日期"
          width="80"
        ></el-table-column>
        <el-table-column prop="title" label="标题"></el-table-column>
        <el-table-column prop="detail" label="详情查看">
          <template v-slot="scope">
            <el-button @click="showDetail(scope.row)" v-slot="scope">
              <i class="el-icon-edit-outline"></i>详情</el-button
            >
          </template>
        </el-table-column>
        <el-table-column prop="reply" width="200" label="回复查看"
          ><template v-slot="scope">
            <el-button @click="showReply(scope.row)" v-slot="scope">
              <i class="el-icon-edit-outline"></i>详情</el-button
            >
          </template></el-table-column
        >
      </el-table>

      <!-- 分页 -->
      <div style="margin-top: 20px">
        <el-pagination
          background
          :current-page="params.pageNum"
          :page-size="params.pageSize"
          layout="prev,pager,next"
          @current-change="handleCurrentChange"
          :total="total"
        />
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
export default {
  name: "myMessage",
  components: {},
  data() {
    return {
      tableData: [],
      total: 0,
      form: {},
      params: {
        pageNum: 1,
        pageSize: 20,
        username: "",
        phone: "",
        email: "",
      },
    };
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      request.get("/message/list", { params: this.params }).then((res) => {
        console.log("我要鼠了" + res.data);
        if (res.code === "200") {
          // this.tableData = res.data.list;
          // this.total = res.data.total;
          this.tableData = res.data;
          this.total = res.data.length;
        }
      });
    },
    reset() {
      this.params = {
        pageNum: 1,
        pageSize: 20,
        username: "",
        phone: "",
        email: "",
      };
      this.load();
    },
    handleCurrentChange(pageNum) {
      // 点击分页按钮触发分页
      this.params.pageNum = pageNum;
      this.load();
    },
    showDetail(row) {
      // request.put("/user/update", row).then((res) => {
      //   if (res.code === "200") {
      //     this.$notify.success("修改成功");
      //     this.load();
      //   } else {
      //     this.$notify.error(res.msg);
      //   }
      // });
    },
    showReply(row) {
      request.get(`/message/isReply/${row.id}`, row).then((res) => {
        if (res.code === "200") {
          this.$notify.success("获取成功");
          // this.load();
          console.log('showReply',res.data)
        } else {
          this.$notify.error(res.msg);
        }
      });
    },
  },
};
</script>

<style scoped></style>
