<template>
  <!--搜索和筛选-->
  <div>
  <div>
<!--    Input 为受控组件，它总会显示 Vue 绑定值。-->
<!--    通常情况下，应当处理 input 事件，并更新组件的绑定值（或使用v-model）。否则，输入框内显示的值将不会改变。-->
<!--    不支持 v-model 修饰符。-->
    <div style="margin-left: 370px">
  <el-input v-model="input" placeholder="请输入内容" style="width: 250px"></el-input>　&nbsp;
    <el-autocomplete
          class="inline-input"
          v-model="state1"
          :fetch-suggestions="querySearch"
          placeholder="请输入筛选条件"
          @select="handleSelect"
          style="width: 140px"
      ></el-autocomplete>
    &nbsp;
  <el-button type="primary" icon="el-icon-search">搜索</el-button>
    </div>
    <br/>
    <el-button type="primary" icon="el-icon-refresh-left" style="margin-left: 1035px" round plain>换一批</el-button>
  </div>
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

    <!--按数据库顺序显示图书-->
    <div>
      <van-grid :border="true" :column-num="2" class="grid">
        <van-grid-item v-for="(i,inx) in hotBookList" :key="inx">
          <!--可更换图片路径-->
          <van-image :src="`http://127.0.0.1:8081/api`+ i.cover"/>
          <p style="margin-top: 5px">{{ i.title }}</p>
        </van-grid-item>
        <p style="margin-bottom: 100px;margin-left:40%" @click="getHotPage()">点击加载更多</p>
      </van-grid>
    </div>

    <!--分页-->
    <div class="block" style="margin-left: 350px">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page.sync="currentPage3"
          :page-size="100"
          layout="prev, pager, next, jumper"
          :total="1000">
      </el-pagination>
    </div>
  </div>
  </div>
</template>

<script>
var fSize = 10;
export default {
  name: 'userHome',
  components: {},
  data() {
    return {
      input: '',
      categories: [],
      state1: '',
      state2: '',
      currentPage1: 5,
      currentPage2: 5,
      currentPage3: 5,
      currentPage4: 4,

      active: 0,
      book: [],
      bookList: [],
      hotBook: [],
      hotBookList: [],
      priceBook: [],
      page: 1,
      hotPage: 1,
      activateId: 0,
      //请求体参数
      form: {
        size: fSize,
        current: 1
      }

    };
  },
  mounted() {
    this.categories = this.loadAll();
    this.form.size = fSize
    this.getHotBook(this.form)
  },

  methods: {
    getHotBook(form) {
      // var that = this;
      // this.axios.post('/api/book/getHot', form).then(function (response) {
      //   that.hotBook = response.data
      //   that.hotBookList = that.hotBookList.concat(that.hotBook);
      //   // console.log(response.data)
      // })
    },
    getHotPage() {
      var that = this;
      //得到下一组数据
      let newPage = that.hotPage + 1
      that.form.current = that.form.size * (newPage - 1) + 1
      this.getHotBook(that.form)
      that.hotPage++
      // console.log(this.getHotProduct(that.form));
    },

    querySearch(queryString, cb) {
      var categories = this.categories;
      var results = queryString ? categories.filter(this.createFilter(queryString)) : categories;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    createFilter(queryString) {
      return (category) => {
        return (category.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
      };
    },
    loadAll() {
      return [
        { "value": "文学 · · · · · ·"},
        { "value": "小说"},
        { "value": "外国文学"},
        { "value": "经典"},
        { "value": "中国文学"},
        { "value": "随笔"},
        { "value": "日本文学" },
        { "value": "散文" },
        { "value": "诗歌"},
        { "value": "流行 · · · · · ·"},
        { "value": "漫画"},
        { "value": "推理"},
        { "value": "科幻"},
        { "value": "青春"},
        { "value": "文化 · · · · · ·"},
        { "value": "历史"},
        { "value": "心理学"},
        { "value": "哲学"},
        { "value": "社会学"},
        { "value": "传记"},
        { "value": "艺术"},
        { "value": "政治"},
        { "value": "生活 · · · · · ·"},
        { "value": "成长"},
        { "value": "女性"},
        { "value": "旅行"},
        { "value": "励志"},
        { "value": "摄影"},
        { "value": "职场"},
        { "value": "经管 · · · · · ·"},
        { "value": "管理"},
        { "value": "经济"},
        { "value": "商业"},
        { "value": "投资"},
        { "value": "理财"},
        { "value": "科技 · · · · · ·"},
        { "value": "科普"},
        { "value": "互联网"},
        { "value": "科学"},
        { "value": "编程"},
      ];
    },
    handleSelect(item) {
      console.log(item);
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
    }
  },
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
#navBar {
  margin-top: 10px;
}

#head {
  padding: 10px;
  margin: 10px
}

.grid {
  border: #E5E5E5;
  margin: 5px;
  padding: 3px;
}

</style>