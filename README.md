# EzyCollect - Test

## Stack
- Java SE 21
- Postgres
- SpringBoot
- Amazon SQS
- Flyway
- Lombok
- JPA

## Download and start the project

git clone https://github.com/jhinckel/.git  
cd   
sudo service docker start  
docker compose up app  

## Swagger
http://localhost:8080/ezy-collect/swagger-ui/index.htm

## Postgres
URL: jdbc:postgresql://localhost:5432/ezy  
DATABSE: ezy  
USER: system_user  
PASSWORD: #system@user  

## API Examples
curl --location 'http://localhost:8080/ezy-collect/v1/payments' \
--header 'Content-Type: application/json' \
--data '{
    "firstName": "John",
    "lastName": "Doe",
    "zipCode": "1000200",
    "cardNumber": "1234123412341234"
}'

curl --location 'http://localhost:8080/ezy-collect/v1/webhooks' \
--header 'Content-Type: application/json' \
--data '{
    "url": "https://jeison.requestcatcher.com/test"
}'

### Docker Instalation
sudo apt update  
sudo apt install apt-transport-https ca-certificates curl software-properties-common  

curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg  

echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null  

sudo apt update  
sudo apt install docker-ce  

docker --version

sudo usermod -aG docker ${USER}  
su - ${USER}

mkdir -p ~/.docker/cli-plugins/  

curl -SL https://github.com/docker/compose/releases/download/v2.27.1/docker-compose-linux-x86_64 -o ~/.docker/cli-plugins/docker-compose  
chmod +x ~/.docker/cli-plugins/docker-compose

sudo service docker start
