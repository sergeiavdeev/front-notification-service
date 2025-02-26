
mvn clean package -Dmaven.test.skip=true
docker ps -aq --filter "name=front-notification-service" | xargs -r docker rm -f
docker system prune -a -f
cp /opt/crt/www_ttc-tops_ru.crt www_ttc-tops_ru.crt
cp /opt/crt/14776967.key 14776967.key
docker build -t sergeiavdeev/front-notification-service:1-dev .
PROFILE=dev TAG=1-dev docker compose up -d
docker system prune -a -f