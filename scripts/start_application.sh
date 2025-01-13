# 기존 애플리케이션 시작
if ! systemctl is-active --quiet myapp; then
    echo "myapp 서비스를 시작합니다..."
    sudo systemctl start myapp || exit 1
    echo "myapp 서비스가 시작되었습니다."
else
    echo "myapp 서비스가 이미 실행 중입니다. 건너뜁니다."
fi