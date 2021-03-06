﻿# myblog

## 介绍
该博客系统是我大二上学期期末作业修改而来，目前是 springboot 项目。

托管平台 | 地址
:----:| :----:
GitHub | [https://github.com/zhengjunren/myblog](https://github.com/zhengjunren/myblog)
Gitee | [https://gitee.com/zhengjunren/myblog](https://gitee.com/zhengjunren/myblog)

预览地址：[http://test.zhengjunren.cn](http://test.zhengjunren.cn) 、[http://47.100.229.198](http://47.100.229.198/)

### 后端技术
技术 | 说明 | 地址
:----:|:----:|:----:
hutool | Java工具包类库 | [GitHub](https://github.com/looly/hutool/)
Docker | 容器化引擎 | [官网](https://www.docker.com/)
OKHttp3 | 轻量级网络框架 | [GitHub](https://github.com/square/okhttp)
Swagger | API 文档生成工具 | [GitHub](https://github.com/swagger-api/swagger-ui)
HikariCP | 数据库连接池 | [GitHub](https://github.com/brettwooldridge/HikariCP)
TkMyBatis | 基于 MyBatis 二次开发的轻量级框架，用于简化 MyBatis 操作 | [GitHub](https://github.com/abel533/Mapper)
SpringBoot | 新一代 JavaEE 开发标准 | [GitHub](https://github.com/spring-projects/spring-boot)
PageHelper | MyBatis 分页插件 | [GitHub](https://github.com/pagehelper/Mybatis-PageHelper)
UserAgentUtils | 用户代理检查工具 | [GitHub](https://github.com/HaraldWalker/user-agent-utils)
Docker Compose | 容器编排工具 | [官网](https://docs.docker.com/compose/)
MyBatisGenerator | Maven 插件，用于 MyBatis 相关代码生成 | [官网](http://www.mybatis.org/generator/)
MybatisCodeHelper | Intellij IDEA 插件，用于 MyBatis 相关代码生成 | [官网](https://plugins.jetbrains.com/plugin/9837-mybatiscodehelperpro)
Spring Security oAuth2 | 安全认证和授权框架 | [GitHub](https://github.com/spring-projects/spring-security-oauth)

### 前端技术
技术 | 说明 | 地址
:----:|:----:|:----:
Vue | 前端框架，MVVM 模式的实现者 | [GitHub](https://github.com/vuejs/vue)
Axios | 前端 HTTP 框架 | [GitHub](https://github.com/axios/axios)
Vuex | Vue 全局状态管理框架 | [GitHub](https://github.com/vuejs/vuex)
Vue CLI | Vue 脚手架，基于 NodeJS | [GitHub](https://github.com/vuejs/vue-cli)
Element UI | 饿了么 UI 框架 | [官网](https://element.eleme.cn)
Vue Router | Vue 路由框架 | [GitHub](https://github.com/vuejs/vue-router)
Vue Element Admin | 基于 Element UI 的前端后台解决方案 | [GitHub](https://github.com/PanJiaChen/vue-element-admin)
Vue Image Crop Upload | Vue 图片剪裁上传组件 | [GitHub](https://github.com/dai-siki/vue-image-crop-upload)

### 参考项目

项目 | 地址
:----:| :----:
eladmin | [GitHub](https://github.com/elunez/eladmin)
ForestBlog | [GitHub](https://github.com/saysky/ForestBlog)
MyShopPlus | [GitHub](https://github.com/funtl/MyShopPlus)
eladmin-web | [GitHub](https://github.com/elunez/eladmin-web)
spring-boot-demo | [GitHub](https://github.com/xkcoding/spring-boot-demo)
spring-security-oauth2 | [Gitee](https://gitee.com/qq75547276/spring-security-oauth2)
SpringBoot Demo源码 | [Gitee](https://gitee.com/sw008/springbootdemo_source_code)
## 软件架构

分为`business`、`search`、`security`和`frontend`，其中前端采用的是 [`vue-admin-template`](https://github.com/PanJiaChen/vue-admin-template)模板。
`business`是资源服务器，即业务层；`security`为认证服务器，`search`是全文检索的服务器。


## 安装教程
### 基于 docker 安装 elasticsearch
+ 安装
```yaml
version: '3.1'
services:
  es:
    image: elasticsearch:6.4.3
    container_name: elastisearch
    ports:
      - 9200:9200
      - 9300:9300
    environment:
      - discovery.type=single-node
```
+ 进入容器
```shell script
docker exec -it elastisearch /bin/bash
```

+ 安装分词器
```shell script
./bin/elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v6.4.3/elasticsearch-analysis-ik-6.4.3.zip
```

+ 退出容器并重新启动
```shell script
exit

docker restart elastisearch
```
+ 补充：创建索引

对`http://ip:port/indexName`发送`put`请求，下面是携带的`json`数据
```json
{
  "settings":{
    "number_of_shards": "6",
    "number_of_replicas": "1",
    "analysis":{
      "analyzer":{
        "ik":{
          "tokenizer":"ik_max_word"
        }
      }
    }
  }
}
```

### 基于 docker 安装 redis
```yaml
version: '3.1'
services:
  redis:
    image: redis
    container_name: redis
    ports:
      - 6379:6379
```

### 安装项目

**因后端项目为`maven`项目，必须先安装好`maven`**

**安装nodejs**

下面是测试环境的安装
#### database
在 mysql 中创建名为 myblog 的数据库，字符集为 utf8 

#### security
修改`security`模块下的`application.yml`文件：
+ 修改`spring.redis.host`和`spring.redis.port`为相应`redis`服务器地址和端口
+ 修改`spring.datasource.jdbc-url`为相应数据库服务器地址
+ 修改`spring.datasource.username`和`spring.datasource.password`为相应数据库服务器用户名和密码

#### business
修改`business`模块下的`application.yml`文件：
+ 修改`spring.datasource.url`为相应数据库服务器地址
+ 修改`spring.datasource.username`和`spring.datasource.password`为相应数据库服务器用户名和密码
+ 修改`security.oauth2`中的 认证服务器地址

#### search
修改`search`模块下的`application.yml`文件：
+ 修改`spring.data.elasticsearch.cluster-nodes`为相应`elasticsearch`服务器地址
+ 修改`spring.data.elasticsearch.cluster-name`为相应`elasticsearch`名称
+ 修改`spring.datasource.url`为相应数据库服务器地址
+ 修改`spring.datasource.username`和`spring.datasource.password`为相应数据库服务器用户名和密码
+ 修改`security.oauth2`中的 认证服务器地址

#### system
修改`system`模块下的`application.yml`文件：
+ 修改`spring.datasource.url`为相应数据库服务器地址
+ 修改`spring.datasource.username`和`spring.datasource.password`为相应数据库服务器用户名和密码
+ 修改`security.oauth2`中的 认证服务器地址

#### frontend
修改`frontend`模块下的`.env.development`文件：
+ `VUE_APP_BASE_API`值为`business`服务器的地址
+ `VUE_APP_SECURITY_URL`值为`security`服务器的地址
+ `VUE_APP_SEARCH_URL`值为`search`服务器的地址
+ `VUE_APP_SYSTEM_URL`值为`system`服务器的地址

## 使用说明

### 部署云服务器
+ 创建`/usr/local/docker`目录
+ 执行下面任一条命令
```shell script
git clone https://gitee.com/zhengjunren/myblog.git
# 或者执行
git clone https://github.com/zhengjunren/myblog.git
```
+ 进入`myblog`目录，赋予`myblog.sh`可执行的权限
```shell script
chmod +x ./myblog.sh
```
+ 执行`myblog.sh`。

另外，注意开放端口哦！

### 注意
**如果你不用 `myblog.sh` 脚本部署的话，注意下面！**
+ 部署 frontend 时，更改`.env.production`的 `VUE_APP_BASE_API`、`VUE_APP_SECURITY_URL`、`VUE_APP_SEARCH_URL`、`VUE_APP_SYSTEM_URL`值
+ 同时删除 `src/main.js`中的
```js
import { mockXHR } from '../mock'
if (process.env.NODE_ENV === 'production') {
  mockXHR()
}
```
+ 再执行```npm run build:prod```命令


## 参与贡献

1. Fork 本仓库
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request
