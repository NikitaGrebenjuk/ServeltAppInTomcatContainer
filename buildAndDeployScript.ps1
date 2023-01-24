#create War-File using maven package
mvn package

# stopp the docker container if it exists
docker kill tc1

# delete docker contaienr if it exists
docker rm tc1

#build a docker container using the docker file in the same directory
docker build -t tc .

docker start mySQL
docker network connect mynet mySQL

#Start tomcat docker container on the defined port
docker run --network mynet --name tc1 -p 8888:8080 -d tc sh -c "catalina.sh run"