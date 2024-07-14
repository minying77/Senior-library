const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true
})
// const { defineConfig } = require('@vue/cli-service')
// module.exports = defineConfig({
//   transpileDependencies: true,
//   server:{
//     proxy: {
//       '^/admin': {
//         //要代理到的服务器，可以是域名也可以是ip+端口号;
//         target: 'http://localhost:8085',
//         //虚拟托管网站，如果为true，源服务器获取的req.header的信息就是他自己的header，如果为false 那么获取的header信息就是代理服务器的
//         secure: false,
//         changeOrigin: true,
//       },
//     },
//   }
// })



