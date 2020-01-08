#!/bin/bash
echo "----------------------------拉取代码----------------------------"

git pull

echo "--------------------------maven清理打包-------------------------"

mvn clean package

echo "---------------------------复制jar包----------------------------"

cp ./admin/target/admin-0.0.1-SNAPSHOT.jar ./docker/admin

cd ./docker/admin

echo "----------------------停止admin容器----------------------------"

docker-compose down

echo "---------------------------删除镜像-----------------------------"

docker rmi admin_myblog-admin

echo "---------------------构建镜像并启动容器-------------------------"

docker-compose up -d