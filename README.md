# Introduction

This serves as a microservice implementation that uses the iText html2pdf library (https://mvnrepository.com/artifact/com.itextpdf/html2pdf
) to receive a rest API call with the HTML payload, and responds with the converted PDF

# Getting Started


### 1. Installation process

 i. Clone this repository
 ii. `mvn clean install`
 iii. Execute the jar generated

### 2. Software dependencies
1. Java 8+
2. Maven

### 3.Build and Test

1. Execute the JAR file on server or localhost
```
java -jar opensource-html2pdf-1.0.0-SNAPSHOT.jar
```
       
2. Run with spring boot using

```
mvn spring-boot:run
```
http://localhost:9999/opensourceConverter/v1/health-check


# API Specs

Endpoint URI: /opensourceConverter/v1/html2pdf

Request:
```json

{
  "pdfFileName":"something", --> optional.
  "htmlContent":"ljljlkj" --> base64 enconded string
}
```
Ex:         
```java
String encodedHtml = Base64.getEncoder().encodeToString(htmlContent.getBytes());
```

Response: 
```json

{
  "pdfContent":"ljljlkj" --> base64 enconded string
}

```

# CONFIGURATIONS

Change the port and contextName as necessary from application.properties

```json
default port: 9999
default contextName: opensourceConverter
```
