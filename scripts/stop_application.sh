#!/bin/bash
if systemctl is-active --quiet myapp; then
    echo "myapp 서비스를 중지합니다..."
    sudo systemctl stop myapp || exit 1
    echo "myapp 서비스가 중지되었습니다."
else
    echo "myapp 서비스가 실행 중이지 않습니다. 건너뜁니다."
fi

# S3에서 ZIP 파일 다운로드
S3_BUCKET="project-pappy-bucket"
S3_KEY="cicdtest/latest.zip"
DEPLOY_DIR="/home/ubuntu/deploy"

echo "S3에서 ZIP 파일을 다운로드합니다..."
aws s3 cp s3://${S3_BUCKET}/${S3_KEY} ${DEPLOY_DIR}/app.zip || exit 1

# ZIP 파일을 배포 디렉터리에 해제
echo "ZIP 파일을 해제합니다..."
unzip -o ${DEPLOY_DIR}/app.zip -d ${DEPLOY_DIR} || exit 1

# Docker Compose 실행 중인지 확인
cd ${DEPLOY_DIR} || exit 1

if [ -f "docker-compose.yml" ]; then
    echo "Docker Compose 파일을 확인했습니다."

    # 실행 중인 컨테이너 확인
    if docker-compose ps | grep -q 'Up'; then
        echo "Docker Compose가 실행 중입니다. 작업을 건너뜁니다."
    else
        echo "Docker Compose 컨테이너가 실행 중이 아닙니다. 시작합니다."
        docker-compose up -d
    fi
else
    echo "docker-compose.yml 파일이 없습니다. 작업을 종료합니다."
    exit 1
fi
