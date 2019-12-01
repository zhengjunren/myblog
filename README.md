# myblog

## 介绍
该博客系统是我大二上学期期末作业修改而来，目前是 springboot 项目。

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

### 安装项目
**因后端项目为`maven`项目，必须先安装好`maven`**

**安装nodejs**

下面是测试环境的安装
#### security
修改`security`模块下的`application.yml`文件：
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

## 使用说明

### 部署生产环境
+ 部署 frontend 时，更改`.env.production`的 `VUE_APP_BASE_API`、`VUE_APP_SECURITY_URL`、`VUE_APP_SEARCH_URL`值
+ 同时删除 `src/main.js`中的
```js
import { mockXHR } from '../mock'
if (process.env.NODE_ENV === 'production') {
  mockXHR()
}
```
+ 再执行```npm run build:prod```命令


#### 参与贡献

1. Fork 本仓库
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request
