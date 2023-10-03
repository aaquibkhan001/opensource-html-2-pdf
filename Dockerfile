## Image OpenJDK
FROM openjdk:17

## Add the Jarfile
ADD target/opensource-html2pdf-1.0.0-SNAPSHOT.jar

## Define Expose
EXPOSE 9999

## Define Entrypoint
ENTRYPOINT ["java","-jar","opensource-html2pdf-1.0.0-SNAPSHOT.jar"]