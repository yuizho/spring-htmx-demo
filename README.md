# spring-html-demo
## launch App by spring-boot-run
```bash
./mvnw clean spring-boot:run -Dspring-boot.run.addResources=true -Dspring-boot.run.profiles=dev
```

And then you can access to `http://localhost:8080/todo` by your browser.

## build & launch App server container 
```bash
./mvnw clean package && docker compose up 
```

And then you can access to `http://localhost/todo` by your browser.
Your request is handled by Nginx and forwarded to the Spring Boot server.