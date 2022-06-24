### SET UP

1. add the `application.properties` file to the path -> `backend/todo/src/main/resources/`
with the following content:

```
server.port=8080

spring.datasource.url=jdbc:mysql://localhost/todo
spring.datasource.username=[YOUR USERNAME]
spring.datasource.password=[YOUR PASSWORD]

spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.properties.hibernate.format_sql=true
```

2. create a database called `todo` in MySQL