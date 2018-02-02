## 模块简介
+ sso-auth-*：权限服务模块
+ sso-eureka-server：Netflix Eureka Server 服务中心
+ sso-eureka-client：每个第三方WebApp或者非WebApp的依赖模块
+ sso-app1：第三方演示应用（注册到eureka服务中心，并调用权限服务。只需开发本模块的内容或者集成现有的插件）
    + 注：插件只[template](https://github.com/miyakowork/template-boot-modules)模块中的，目前还在完善
---
## 技术品种\(≧▽≦)/
+ 包括但不仅限于以下所列举的，想到再加上 （滑稽）

|   名称        | 版本 |
|:-----------------------------------:|:-------------:|
|           Java                            |      1.8    |
|           SpringBoot                |      1.5.9.RELEASE    |
|           SpringCloud              |      Edgware.RELEASE    |
|           Apache Shiro            |      1.4.0    |
|           TemplateModules    |      1.10.18-SNAPSHOT    |
