# 项目结构

```
├─java
│  └─com
│      └─pany
│          │  Work4Application.java
│          │
│          ├─config
│          │      WebConfig.java
│          │
│          ├─controller
│          │      ArticleController.java
│          │      CommentController.java
│          │      UserController.java
│          │
│          ├─exception
│          │      GlobalExceptionHandler.java
│          │
│          ├─interceptors
│          │      LoginInterceptors.java
│          │
│          ├─mapper
│          │      ArticleMapper.java
│          │      CommentMapper.java
│          │      FavoriteMapper.java
│          │      UserMapper.java
│          │
│          ├─pojo
│          │      Article.java
│          │      Comment.java
│          │      Favorite.java
│          │      Result.java
│          │      SubComment.java
│          │      User.java
│          │
│          ├─response
│          │      Base.java
│          │      GetArticleListResponse.java
│          │      GetArticleResponse.java
│          │      LoginResponse.java
│          │
│          ├─service
│          │      ArticleService.java
│          │      CommentService.java
│          │      UserService.java
│          │
│          └─util
│                  AliOssUtil.java
│                  JwtUtils.java
│                  Md5Utils.java
│                  SnowFlakeUtils.java
│                  ThreadLocalUtil.java
│
└─resources
        application.yml
```

# API文档

https://apifox.com/apidoc/shared-81c0d39a-8548-4d14-9488-2cc69633ebd1

# 技术栈

- springboot
- springmvc
- mybatis
- mysql
- redis

# 项目部署

项目部署在阿里云ecs上，文章以及头像通过阿里云oss存储。

在ECS本机部署mysql和redis，通过docker部署java环境运行后端应用，docker部署nginx托管前端代码



## 遇到的问题

- 项目部署方面花费了非常多时间，我自己在linux的使用方面不够熟悉，对docker的使用理解不够(数据库莫名其妙消失..)。项目部署时遇到CORS问题困扰了非常久(一开始通过在配置类中添加addCorsMappings方法试图解决cors问题，简单的请求可以正常返回了，对于携带token的请求还是没有解决。又花了很久时间了解到预检请求，发现是自定义拦截器中对OPTIONS方法的请求一样也检验了是否携带token，特判后终于解决)，导致bonus功能没有足够时间开发。
- 数据库设计方面也不够熟练，感觉一些功能设计的有点冗余。
- 对于某些需要用户服务层和文章服务层共同实现(如获取个人文章列表)不知该归属哪个类，导致项目结构有些混乱。
- 对springboot框架(如自动装配..)，servlet，tomcat原理了解还不够，导致遇到一些问题时需要花费很长时间弄清楚问题关键和解决办法。
- 对于一些响应要求比较复杂的接口不知道该如何返回数据，只好用笨办法将需要的实体类和数据重新封装为respones类返回
- 对java部署不熟悉，每次修改代码都有重新打包、发送、构建容器运行，哪怕只是改动了一点。不知道有没有更好的解决办法。

