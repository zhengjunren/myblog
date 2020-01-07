#!/bin/bash
echo "----------------------------拉取代码----------------------------"

git pull

echo "---------------------------部署前端-----------------------------"

cd docker/frontend

docker-compose down

cp main.js ../../frontend/src

cd ../../frontend

npm run build:prod

cp -fr dist/ ../docker/frontend/wwwroot

cd ../docker/frontend

docker-compose up -d
