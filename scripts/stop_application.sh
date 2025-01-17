#!/bin/bash

# MyApp 서비스 중지
if systemctl list-units --type=service | grep -q "myapp.service"; then
    if systemctl is-active --quiet myapp; then
        echo "myapp 서비스를 중지합니다..."
        sudo systemctl stop myapp || exit 1
        echo "myapp 서비스가 중지되었습니다."
    else
        echo "myapp 서비스가 실행 중이지 않습니다. 건너뜁니다."
    fi
else
    echo "myapp.service가 없습니다. 중지할 서비스가 없습니다."
fi

# S3에서 ZIP 파일 다운로드
S3_BUCKET="project-pappy-bucket"
S3_KEY="cicdtest/latest.zip"
DEPLOY_DIR="/home/ubuntu/deploy"

echo "S3에서 ZIP 파일을 다운로드합니다..."
if ! aws s3 cp s3://${S3_BUCKET}/${S3_KEY} ${DEPLOY_DIR}/app.zip; then
    echo "S3에서 ZIP 파일 다운로드에 실패했습니다. 작업을 중단합니다."
    exit 1
fi

# ZIP 파일을 배포 디렉터리에 해제
echo "ZIP 파일을 해제합니다..."
if ! unzip -o ${DEPLOY_DIR}/app.zip -d ${DEPLOY_DIR}; then
    echo "ZIP 파일 해제에 실패했습니다. 작업을 중단합니다."
    exit 1
fi

# Docker Compose 실행 중인지 확인
cd ${DEPLOY_DIR} || exit 1

if [ -f "docker-compose.yml" ]; then
    echo "Docker Compose 파일을 확인했습니다."

    # 실행 중인 컨테이너 확인
    if docker-compose ps --services --filter "status=running" | grep -q .; then
        echo "Docker Compose가 실행 중입니다. 작업을 건너뜁니다."
    else
        echo "Docker Compose 컨테이너가 실행 중이 아닙니다. 시작합니다."
        if ! docker-compose up -d; then
            echo "Docker Compose를 시작하는 데 실패했습니다. 작업을 종료합니다."
            exit 1
        fi
    fi
else
    echo "docker-compose.yml 파일이 없습니다. 작업을 종료합니다."
    exit 1
fi
