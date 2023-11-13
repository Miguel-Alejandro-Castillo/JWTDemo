# JWTDemo
Implementacion de Login con JWT
## JWTDemo es una api rest que sirve para la creación y consulta de usuarios

### Documentacion servicios REST de la aplicacion
POST /users/login HTTP 
Servicio que sirve para auntenticar un usuario mediante email y password 
#### Request:
```json
{
    "email": "miguel@gmail.com",
    "password": "1234"
}
```
#### Response:
```json
{
	"id": 1,
	"name": "India",
	"email": "miguel@gmail.com",
	"password": "$2a$10$RuDRVNqvRE2UNrs9DKVa0ODD3NhDsoq2DSn0EtcovkWRv7tTf6oXu",
	"created": "2023-11-13 11:59:32",
	"lastLogin": "2023-11-13 12:16:21",
	"isActive": true,
	"token": "Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJEZW1vSldUIiwic3ViIjoibWlndWVsQGdtYWlsLmNvbSIsImF1dGhvcml0aWVzIjpbXSwiaWF0IjoxNjk5ODg4NTgwLCJleHAiOjE2OTk4ODk0ODB9.rNyijA3ZLNMbR6qGDhxHZCqJBj98I0nXWyppWDwKaLp_fSdtml9eo_ItYOYKbJ9-A-yOCzxEc0elO5rze0wsYg",
	"phones": []
}
```

POST /users/sign-up HTTP 
Servicio que sirve para registrar un usuario
#### Request:
```json
{
	"name": "usuario1",
	"email": "miguel@gmail.com.ar",
	"password": "12345",
	"phones": [
		{
			"number": 5374117,
			"cityCode": 221,
			"countryCode": "549"
		}
	]
}
```
#### Response:
```json
{
	"id": 2,
	"name": "usuario1",
	"email": "miguel@gmail.com.ar",
	"password": "$2a$10$mfh.vYDmUUMyl2VLnm2WTOXE73XviwCJFt7QSlR.gP84dV0Q4vVqS",
	"created": "2023-11-13 12:20:18",
	"lastLogin": null,
	"isActive": true,
	"token": null,
	"phones": [
		{
			"id": 1,
			"number": 5374117,
			"cityCode": 221,
			"countryCode": "549"
		}
	]
}
```



### Requisitos para la instalacion de la aplicacion backend
* Java 1.8
* Spring Boot 2.7.17

### Comandos para construir y levantar en local

* mvn clean install -DskipTests
* mvn spring-boot:run


