# 스프링부트 JPA, MySQL, Security 인스타그램

## 의존성

![image](https://user-images.githubusercontent.com/62128942/90703499-e9149c00-e2c8-11ea-9d54-26b5014264ca.png)
![image](https://user-images.githubusercontent.com/62128942/90703489-e3b75180-e2c8-11ea-95ae-d60e6fd6da9d.png)

# ERD

![ERD](https://user-images.githubusercontent.com/62128942/91000443-2dbf7080-e604-11ea-8742-0dc307066fe1.png)

## MySQL 세팅

1. MySQL 한글 설정 (my.ini)

```ini
[client]
default-character-set=utf8

[mysql]
default-character-set=utf8

[mysqld]
collation-server = utf8_unicode_ci
init-connect='SET NAMES utf8'
init_connect='SET collation_connection = utf8_general_ci'
character-set-server=utf8
```

2. MySQL 데이터 베이스 및 사용자 생성

- create user 'insta'@'%' identified by 'bitc5600';
- GRANT ALL PRIVILEGES ON 별.별 TO 'insta'@'%';
- create database insta;
- use insta;

## application.yml 설정

```yml
server:
  port: 8080
  servlet:
    context-path: /
    encoding:
      charset: UTF-8
      enabled: true
      force: true

spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/insta?serverTimezone=Asia/Seoul&useSSL=false&allowPublicKeyRetrieval=true
    username: insta
    password: bitc5600

  jpa:
    open-in-view: true
    hibernate:
      ddl-auto: create
      naming:
        physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
      use-new-id-generator-mappings: false
    show-sql: true

  servlet:
    multipart:
      enabled: true
      max-file-size: 2MB

  security:
    user:
      name: cos
      password: 1234

cos:
  secret: 겟인데어

file:
  path: C:/src/jwtoauthreact/instagram/src/main/resources/upload/
```

# 쿼리문

```sql

# 맞팔 확인 쿼리
SELECT f1.id, f1.fromUserId, f1.toUserId, f1.createDate,if(f2.fromUserId is null, false, true) "matpal"
FROM follow f1 LEFT OUTER JOIN follow f2
ON f1.fromUserId = f2.toUserId AND f1.toUserId = f2.fromUserId
ORDER BY f1.id;

# 맞팔 확인 쿼리 스칼라 서브 쿼리
SELECT f1.id, f1.fromuserId, f1.toUserId, f1.createDate,
(SELECT 1 from follow f2
 WHERE f1.fromUserId = f2.toUserId
 AND f1.toUserId = f2.fromUserId
 ) "matpal"
 from follow f1;

SELECT i.id, i.caption,(
SELECT count(*) from likes lk where i.id=lk.imageId
) "좋아요"
from image i;

```
