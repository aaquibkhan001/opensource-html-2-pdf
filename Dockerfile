## Image OpenJDK
FROM openjdk:17

ARG JAR_FILE

COPY ${JAR_FILE} opensource-html2pdf.jar

ENTRYPOINT ["java","-jar","opensource-html2pdf.jar"]