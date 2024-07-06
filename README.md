### 【0706】系统原型设计草稿
https://pixso.cn/app/product/oFC_VLyDR_My1l8QPA1eGQ?showQuickFrame=true&page-id=2%3A1126 邀请您加入 Pixso 原型文件「原型文件」

### 【0706】系统功能模块：
```
- 分用户类型登录：/login
  - user读者进入前台
  - admin管理者进入后台
  
- 前台：
  - 前台首页：/front
  - 个人中心模块：/center
  - 好书推荐模块：/recommend
  - 分类资源模块：/classified
  - 图书借阅模块：/userborrow
  - 座位预约模块：/seat
  - 读者留言模块：/usermessage
  
- 后台：
  - 读者管理模块：/user
  - 管理者管理模块：/admin
  - 借阅管理：/adminborrow
  - 留言管理：/adminmessage
```
### Project setup
```
npm install
```

### Compiles and hot-reloads for development
```
npm run serve
```

### Compiles and minifies for production
```
npm run build
```

### Lints and fixes files
```
npm run lint
```

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).
