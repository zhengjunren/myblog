#!/bin/bash
echo "----------------------------拉取代码----------------------------"

git pull

echo "--------------------------maven清理打包-------------------------"

mvn clean package

echo "---------------------------复制jar包----------------------------"

cp ./security/target/security-0.0.1-SNAPSHOT.jar ./docker/security

cp ./search/target/search-0.0.1-SNAPSHOT.jar ./docker/search

cp ./business/target/business-0.0.1-SNAPSHOT.jar ./docker/business

cp ./system/target/system-0.0.1-SNAPSHOT.jar ./docker/system

cd ./docker/security

echo "----------------------停止security容器--------------------------"

docker-compose down

echo "---------------------------删除镜像-----------------------------"

docker rmi security_myblog-security

echo "---------------------构建镜像并启动容器-------------------------"

docker-compose up -d

cd ../search

echo "----------------------停止search容器-----------------------------"

docker-compose down

echo "---------------------------删除镜像-----------------------------"

docker rmi search_myblog-search

echo "---------------------构建镜像并启动容器-------------------------"

docker-compose up -d

cd ../business

echo "---------------------停止business容器-----------------------------"

docker-compose down

echo "---------------------------删除镜像-----------------------------"

docker rmi business_myblog-business

echo "---------------------构建镜像并启动容器-------------------------"

docker-compose up -d

cd ../system

echo "-----------------------停止system容器-----------------------------"

docker-compose down

echo "---------------------------删除镜像-----------------------------"

docker rmi system_myblog-system

echo "---------------------构建镜像并启动容器-------------------------"
docker-compose up -d

echo "---------------------------部署前端-----------------------------"

cd ../frontend

docker-compose down

cp main.js ../../frontend/src

cd ../../frontend

npm run build:prod

cp -fr dist/ ../docker/frontend/wwwroot

cd ../docker/frontend

docker-compose up -d
