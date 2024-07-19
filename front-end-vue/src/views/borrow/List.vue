<template>
  <div>
    <!-- 搜索表单 -->
    <div style="margin-bottom: 20px">
      <el-input
          style="width: 240px"
          placeholder="请输入图书名称"
          v-model="params.title"
      ></el-input>
      <el-input
          style="width: 240px; margin-left: 5px"
          placeholder="请输入用户名称"
          v-model="params.name"
      ></el-input>
      <el-button style="margin-left: 5px" type="primary" @click="load">
        <i class="el-icon-search"></i>
        搜索
      </el-button>
      <el-button style="margin-left: 5px" type="warning" @click="reset">
        <i class="el-icon-refresh"></i>
        重置
      </el-button>
    </div>
    <el-table :data="tableData" stripe row-key="id" default-expand-all>
      <el-table-column prop="id" label="编号" width="80"></el-table-column>
      <el-table-column prop="userNo" label="会员码"></el-table-column>
      <el-table-column prop="name" label="用户名称"></el-table-column>
      <el-table-column prop="phone" label="用户联系方式" width="100"></el-table-column>
      <el-table-column prop="title" label="图书名称"  width="100"></el-table-column>
      <el-table-column prop="isbn" label="图书标准码"></el-table-column>
      <el-table-column prop="score" label="图书积分"></el-table-column>
      <el-table-column prop="price" label="押金"></el-table-column>
      <el-table-column prop="createtime" label="借出日期" width="110"></el-table-column>
      <el-table-column prop="status" label="借出状态">
        <template v-slot="scope">

          <el-tag type="success" v-if="scope.row.status === '1'">待审核</el-tag>
          <el-tag type="primary" v-if="scope.row.status === '2'">借阅中</el-tag>
          <el-tag type="danger" v-if="scope.row.status === '3'">审核不通过</el-tag>
          <el-tag type="warning" v-if="scope.row.status=== '4'">已归还</el-tag>

        </template>
      </el-table-column>
      <el-table-column prop="times" label="借出天数"></el-table-column>
      <el-table-column prop="returnDate" label="归还日期" width="100"></el-table-column>
      <el-table-column prop="note" label="过期提醒">
        <template v-slot="scope">
          <el-tag type="primary" v-if="scope.row.note === '未到期'">{{scope.row.note}}</el-tag>
          <el-tag type="warning" v-if="scope.row.note === '已到期'">{{scope.row.note}}</el-tag>
          <el-tag type="danger" v-if="scope.row.note === '已过期'">{{scope.row.note}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="140">
        <template v-slot="scope">
          <!-- scope.row 就是当前行数据 -->
          <el-popconfirm
              style="margin-left: 5px"
              title="请审核该借阅申请。   "
              confirm-button-text="通过"
              cancel-button-text="不通过"
              @confirm="changeStatusYes(scope.row.id)"
              @cancel="changeStatusNo(scope.row.id)"
          >
            <el-button type="text"   slot="reference" :disabled="scope.row.status != '1'" style="margin-right: 8px;">审核</el-button>
          </el-popconfirm>
          <el-button type="text" @click="returnBooks(scope.row)" :disabled="scope.row.status != '2'">提醒还书</el-button>
        </template>
      </el-table-column>

      <el-table-column label="管理">
        <template v-slot="scope">
          <el-popconfirm
              style="margin-left: 5px"
              title="您确定删除这行数据吗？"
              @confirm="del(scope.row.id)"
          >
            <el-button type="danger" slot="reference" >删除</el-button>
          </el-popconfirm>
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
import Cookies from "js-cookie";
export default {
  name: "BorrowList",
  data() {
    return {
      tableData: [],
      total: 0,
      params: {
        pageNum: 1,
        pageSize: 10,
        title: "",
        name: ""
      },
      admin: Cookies.get("admin") ? JSON.parse(Cookies.get("admin")) : {},
    };
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      // fetch('http://localhost:9090/Borrow/list').then(res=>res.json()).then(res=>{
      //   console.log(res)
      //   this.tableData=res
      // })
      request.post("/borrow/page", this.params ).then((res) => {
        if (res.code === "200") {
          this.tableData = res.data.records;
          this.total = res.data.total;
        }
      });
    },
    reset() {
      this.params = {
        pageNum: 1,
        pageSize: 10,
        title: "",
        name: ""
      };
      this.load();
    },
    handleCurrentChange(pageNum) {
      // 点击分页按钮触发分页
      this.params.pageNum = pageNum;
      this.load();
    },
    del(id) {
      request.delete("/borrow/delete/" + id).then(res => {
        if (res.code === "200") {
          this.$notify.success("删除成功");
          this.load();
        } else {
          this.$notify.error(res.msg);
        }
      });
    },
    returnBooks(row){
      request.post("/borrow/saveReturn" ,row).then(res => {
        if (res.code === "200") {
          this.$notify.success("提醒成功");
          this.load();
        } else {
          this.$notify.error(res.msg);
        }
      });
    },
    changeStatusYes(id){
      request.put("/borrow/changeStatusYes/" + id).then(res => {
        if (res.code === "200") {
          this.$notify.success("审核通过");
          this.load();
        } else {
          this.$notify.error(res.msg);
        }
      });
    },
    changeStatusNo(id){
      request.put("/borrow/changeStatusNo/" + id).then(res => {
        if (res.code === "200") {
          this.$notify.success("审核不通过");
          this.load();
        } else {
          this.$notify.error(res.msg);
        }
      });
    }
  },
};
</script>

<style scoped></style>