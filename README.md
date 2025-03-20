# Example project built with Spring Boot + vips-ffm (libvips)

### Running the app

From the project's root directory run the following command:

```
./mvnw -X spring-boot:run
```

### Testing the app

After the app is up and running use curl to download the resized image:

```
curl --output output.png localhost:8080
```
