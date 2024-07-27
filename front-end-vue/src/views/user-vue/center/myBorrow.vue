<template>
  <div>
    <el-table :data="tableData" stripe row-key="id" default-expand-all>
      <el-table-column prop="id" label="编号" width="80"></el-table-column>
      <el-table-column prop="title" label="书名" width="80"></el-table-column>
      <el-table-column prop="publishDate" label="日期"></el-table-column>
      <el-table-column prop="createtime" label="借阅起始日期"></el-table-column>
      <el-table-column
        prop="times"
        width="200"
        label="借阅天数"
      ></el-table-column>
      <el-table-column prop="price" label="押金"></el-table-column>
      <el-table-column prop="author" label="归还日期"></el-table-column>
      <el-table-column prop="status" label="状态">
        <template v-slot="scope">
        <el-button @click="updateBookStatus(scope.row)" v-slot="scope">
          <i class="el-icon-edit-outline"></i>{{ status }}</el-button
        >
        </template>
      </el-table-column>
      <!-- <el-table-column prop="status" label="状态" width="230">
        <template v-slot="scope">
          <el-switch
            v-model="scope.row.status"
            @change="changeStatus(scope.row)"
            active-color="#13ce66"
            inactive-color="#ff4949"
          />
        </template>
      </el-table-column> -->
      <el-table-column label="操作" width="140">
        <template v-slot="scope">
          <!-- scope.row 就是当前行数据 -->
          <el-button @click="returnBook(scope.row)"><i class="el-icon-edit-outline"></i>归还</el-button>
        </template>
      </el-table-column>
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
</template>

<script>
import request from "@/utils/request";
export default {
  name: "myBorrow",
  components: {},
  data() {
    return {
      tableData: [],
      total: 0,
      status: '',
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
      request.get("/borrow/list").then((res) => {
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
    changeStatus(row) {
      if (this.admin.id === row.id && !row.status) {
        row.status = true;
        this.$notify.error("您的操作不合法");
        return;
      }
      request.put("/user/update", row).then((res) => {
        if (res.code === "200") {
          this.$notify.success("修改成功");
          this.load();
        } else {
          this.$notify.error(res.msg);
        }
      });
    },
    updateBookStatus(row) {
      // /api/book/update
      console.log("updateBookStatus");
      request.put(`/borrow/changeStatus${this.status==='借阅中'?'Yes':'No'}/${row.id}`, row).then((res) => {
        if (res.code === "200") {
          this.$notify.success("修改状态成功");
          this.load();
        } else {
          this.$notify.error(res.msg);
        }
      });
    },
    returnBook(row) {
      console.log("returnBook");
      request.post("/borrow/saveReturn", row).then((res) => {
        if (res.code === "200") {
          this.$notify.success("归还成功");
          this.load();
        } else {
          this.$notify.error(res.msg);
        }
      });
    },
  },
};
</script>

<style scoped></style>
