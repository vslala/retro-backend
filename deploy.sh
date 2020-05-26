./gradlew clean build -x test
docker system prune -f
docker images
docker rmi vslala/retro-board-backend
docker build -t vslala/retro-board-backend:latest .
docker-compose up

