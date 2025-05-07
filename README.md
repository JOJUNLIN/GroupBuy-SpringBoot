# 组团团购系统——后端

## 项目简介

这是团购毕设的后端SpringBoot，实现uniapp开发的小程序和vue3开发的web端接口api

### 技术栈

- 后端框架：[SpringBoot](https://springdoc.cn/spring-boot/)
- 持久层框架：[MyBatis](https://mybatis.net.cn/)
- 数据库：[MySQL](https://www.mysql.com/)

### 开发工具

- 集成开发环境：IntelliJ IDEA 2024.1.1 专业版
- 接口调试：Postman
- 数据库工具：Navicat Premium 16

## 资料说明

### 📀 参考学习视频

[https://www.bilibili.com/video/BV14z4y1N7pg?vd_source=e4d78e412d6bcec239867be6e62854fe](https://www.bilibili.com/video/BV1Bp4y1379L/?share_source=copy_web&vd_source=2ac50d29193927b3c8597537dc4bc81d)

### 📦 后端SpringBoot项目源码

[https://github.com/JOJUNLIN/GroupBuy-SpringBoot.git](https://github.com/JOJUNLIN/GroupBuy-SpringBoot.git)

### 📦 小程序项目源码

[https://github.com/JOJUNLIN/GroupBuy-UniApp.git](https://github.com/JOJUNLIN/GroupBuy-UniApp.git)

### 📦 web端Vue项目源码

[https://github.com/JOJUNLIN/GroupBuy-Vue.git](https://github.com/JOJUNLIN/GroupBuy-Vue.git)

## 工程结构解析

```
├─.idea                             -- IntelliJ IDEA 项目配置文件目录 (或其他IDE的项目配置)
├─.mvn                              -- Maven Wrapper 配置目录，用于确保项目使用统一的Maven版本
│  └─wrapper
│      └─ maven-wrapper.jar         -- Maven Wrapper 的 JAR 文件
│      └─ maven-wrapper.properties  -- Maven Wrapper 的属性配置文件
├─src                               -- 项目源代码和资源目录
│  ├─main                           -- 主要的应用代码和资源
│  │  ├─java                        -- Java 源代码根目录
│  │  │  └─com
│  │  │      └─jojun
│  │  │          └─groupbuy         -- 项目的基础包名 (base package)
│  │  │              ├─config       -- 配置类目录
│  │  │              ├─controller   -- 控制器 (Controller) 目录
│  │  │              ├─dto          -- 数据传输对象 (Data Transfer Object) 目录 (用于在不同层之间传递数据，尤其是在Controller和Service之间，或用于API的请求/响应体)
│  │  │              ├─interceptors -- 拦截器 (Interceptor) 目录
│  │  │              ├─mapper       -- MyBatis Mapper 接口目录 (或称DAO层，定义与数据库交互的接口)
│  │  │              ├─pojo         -- 普通 Java 对象 (Plain Old Java Object) 目录，通常指实体类 (Entity)，对应数据库表结构
│  │  │              ├─service      -- 服务 (Service) 接口目录 (定义业务逻辑接口)
│  │  │              │  └─impl      -- 服务接口的实现类目录
│  │  │              └─utils        -- 工具类 (Utility) 目录 
│  │  └─resources                   -- 资源文件目录
│  │      ├─static                  -- 静态资源目录 
│  │      ├─application.yml         -- Spring Boot 的主配置文件 
│  │      └─templates               -- 模板引擎的模板文件目录 
│  └─test                           -- 测试代码目录
│     └─java                        -- Java 测试源代码根目录
│         └─com
│             └─jojun
│                 └─groupbuy        -- 测试代码的基础包名，通常与main中的包结构对应
│                     └─ GroupbuyApplicationTests.java -- Spring Boot 自动生成的测试启动类
└─target                            -- Maven 构建输出目录 (编译后的类文件、打包后的JAR/WAR文件等)
   ├─classes                        -- 编译后的 Java 类文件 (.class) 和 main/resources 下的资源文件
   │  └─com
   │      └─jojun
   │          └─groupbuy            -- 对应 main/java 下的包结构
   │              ├─config
   │              ├─controller
   │              ├─dto
   │              ├─interceptors
   │              ├─mapper
   │              ├─pojo
   │              ├─service
   │              │  └─impl
   │              └─utils
   └─generated-sources              -- 由构建过程（如注解处理器）生成的源代码目录
      └─annotations                 -- (通常) 由注解处理器生成的代码
```