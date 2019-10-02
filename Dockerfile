FROM openjdk:8
ADD target/newsletter-app.jar newsletter-app.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "newsletter-app.jar"]