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
          image: 'https://picsum.photos/800/400?random=1',
          title: '沈复《浮生六记》',
          description: '人生碌碌，竞短论长，却不道枯荣有数，得失难量。'
        },
        {
          image: 'https://picsum.photos/800/400?random=2',
          title: '林舒曼蒂《喜欢》',
          description: '春风迎面吹来的时候，我就站在你身后，就好像，春天正在把你推向我。'
        },
        {
          image: 'https://picsum.photos/800/400?random=3',
          title: '鲁迅《热风》',
          description: '曙光在头上，不抬起头，便永远只能看见物质的闪光。'
        },
        {
          image: 'https://picsum.photos/800/400?random=4',
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
</style>