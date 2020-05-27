sudo docker images | grep "registry.gitlab.com" | xargs sudo docker rmi
export MYSQL_CONTAINER_NAME=$1
export IMAGE_REPOSITORY=$2
export IMAGE_TAG=$3
export MYSQL_ROOT_PASSWORD=$4
export MYSQL_USER=$5
export MYSQL_PASS=$6
export MYSQL_PORT=$7
export SPRING_DATASOURCE_URL="jdbc:mysql://${MYSQL_CONTAINER_NAME}:${MYSQL_PORT}/retroboarddb?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&autoReconnect=true"
export SPRING_DATASOURCE_USERNAME=${MYSQL_USER}
export SPRING_DATASOURCE_PASSWORD=${MYSQL_PASS}
export CREDENTIAL_FILE_NAME=$8
export SPRING_ACTIVE_PROFILE=$9
docker-compose config
docker-compose pull
docker-compose up