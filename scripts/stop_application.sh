#!/bin/bash
# 현재 실행 중인 애플리케이션 중지
sudo systemctl stop myapp
# 의존성 설치
sudo apt-get update
sudo apt-get install -y dependency-package