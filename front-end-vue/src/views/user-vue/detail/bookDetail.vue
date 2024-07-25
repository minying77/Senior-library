<template>
  <div>
    <el-container>
      <el-aside width="25%" id="thumbnail">
        <div>
          <van-grid :border="true" :column-num="1" :gutter="10" style="width: 50%">
            <van-grid-item v-for="(i,inx) in images" :key="inx">
              <!--      得到每个缩略图的id        -->
              <div class="thumbnail" @click="getId(i)">
                <van-image :src="`http://127.0.0.1:8081/api`+ i.url"  :class="currentClass(inx)"/>
              </div>
            </van-grid-item>
          </van-grid>
        </div>
      </el-aside>
      <el-aside width="25%" style="float: right">
        <div id="mes">
          <p>书名：{{ bookDetail.title}}</p>
          <p>作者：{{ bookDetail.author }}</p>
          <p>描述：{{ bookDetail.description }}</p>
          <p>出版日期：{{ bookDetail.publishdate }}</p>
          <p>出版社：{{ bookDetail.publisher}}</p>
          <p>押金：¥{{ bookDetail.price}}</p>
        </div>
      </el-aside>
    </el-container>

  </div>
</template>

<script>
export default {
  name: "bookDetail",
  data() {
    return {
      activeId: 0,
      //商品id
      id: 0,
      bookDetail: [],
      imageList: [],
      images: [],
      num1: 1,
    }
  },
  mounted() {
    this.getBookDetail()
  },
  methods: {
    handleChange(value) {
      console.log(value);
    },
    getBookDetail() {
      var that = this
      //用$route接收传递过来的id值，没有$router没有使用path时，传递的参数会存在 params中
      //在使用path后params会被忽略，这种情况可以使用querry
      that.id = this.$route.params.id
      this.axios.get(`/api/product/${that.id}`).then(function (response) {
        that.bookDetail = response.data
        // console.log(that.productDetail)
        //将所有图片都放在 image 对象中
        that.imageList = response.data.subImages.split(",");
        for (let i = 0; i < that.imageList.length; i++) {
          //每张图片赋值一个id = 索引号
          let obj = {id: i, url: that.imageList[i]}
          that.images.push(obj)
        }
        // console.log(that.images)
      })
    },
    //获取对应缩略图的id值
    getId(i) {
      var that = this
      that.activeId = i.id
    },
    //  改变样式
    currentClass(inx) {
      var that = this
      return [that.activeId == inx ? 'thumbnailActive' : ''];
    }
  }
}
</script>

<style scoped>
#thumbnail {
  display: inline-block;
  margin-left: 200px;
  width: 25%;
  /*margin-top: 20%;*/
}

#slider {
  display: inline-block;
  width: 70%;
  margin-top: 15%;
}

#mes {
  margin-top: 25%;
}

.thumbnail {
  /*border: 2px solid orange;*/
  width: 80%;
}
.thumbnailActive{
  border: 2px solid orange;
}
</style>