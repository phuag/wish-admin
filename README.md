WISH-ADMIN
==========================

前后端框架学习项目

后端仅提供REST数据,前端单页面应用组织方式,页面子功能菜单通过前端路由实现

##Contribution

_I appreciate any contribution for this project, including suggestions, documentation improvements, reporting issues, forks and bugfixs,  etc. I have found there are some unrelated issues added, before you file an issue, please **READ THE STEPS IN THIS README.md**  carefully_.

**在你提交 ISSUE 前，请务必确认已经严格完成了本文中描述的操作步骤**。

Thanks.

##后端

  * 技术选择
  
    spring-boot

    mybatis + pagehelper + druid

    shiro

    ehcache
    
  * 运行及开发环境
  
    JDK 8 +  Apache Maven


##前端（二选一,推荐vue）

 * angularjs 1.x + angular-ui-router + angular-bootstrap + adminLTE
    
    nodejs + bower
    
    js:ECMAScript5.1
    
    依赖管理工具: bower
 
 * vue 2 + vue-router + vuex + element-ui
 
    nodejs + npm + webpack + vue-cli
    
    js: ECMAScript6, babel转化为es5.1
    
    webpack: js css image 打包
    
    vue-cli: vue脚手架,搭建目录结构
    
##前后端结合

    由于spring-boot默认将resource下static目录下的文件当成资源文件，
    所以：
    1 angularjs技术路线下，直接将前端代码放在resource/static下
    2 vue 2最终的前端代码需要编译打包，仅将前端build后输出文件放到resource/static下
      源码放在frontend中，输出路径配置在frontend/config/index.js中，编译打包命令：
      在frontend目录中运行 npm run build 
    
    
##运行

    
    选择vue 2 技术，需要先编译打包前端代码，编译完成后在使用spring-boot方式启动
    
    方式1: 在开发环境中运行Application类
    方式2：通过spring-boot的maven插件将整个工程打成一个可运行jar包,java -jar命令运行jar
    方式3: 通过spring-boot的maven插件将整个工程打成一个war包,发布到tomcat等容器中运行