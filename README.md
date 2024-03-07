# 项目结构

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

# API文档

https://apifox.com/apidoc/shared-81c0d39a-8548-4d14-9488-2cc69633ebd1

todo