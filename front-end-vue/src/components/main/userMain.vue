<template>
    <Carousel :items="items" :interval="interval" />
</template>

<script>
import Carousel from '@/components/Carousel'
export default {
  name: 'userMain',
  components: {
    Carousel
  },
  data() {
    return {
      items: [
        {
          image: 'https://img.zcool.cn/community/0186c76072a35d11013e87f447ead2.jpg?x-oss-process=image/auto-orient,1/resize,m_lfit,w_1280,limit_1/sharpen,100/quality,q_100',
          title: '沈复《浮生六记》',
          description: '人生碌碌，竞短论长，却不道枯荣有数，得失难量。'
        },
        {
          image: 'https://img0.baidu.com/it/u=1529035120,790775991&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800',
          title: '林舒曼蒂《喜欢》',
          description: '春风迎面吹来的时候，我就站在你身后，就好像，春天正在把你推向我。'
        },
        {
          image: 'https://img2.baidu.com/it/u=1011219486,3484723689&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800',
          title: '鲁迅《热风》',
          description: '曙光在头上，不抬起头，便永远只能看见物质的闪光。'
        },
        {
          image: 'https://pic.rmb.bdstatic.com/bjh/c3029e8122d182a0c1507475b41b921f6634.jpeg',
          title: '木心《文学回忆录》',
          description: '一个爱我的人，如果爱得讲话结结巴巴，语无伦次，我就知道他爱我。'
        }
      ],
      interval: 3000
    }
  }
}
</script>

<style lang="less">
body {
  background-color: #ccc;
}
.carousel-item-image{
  width: 800px;
  height: 400px;
}
</style>