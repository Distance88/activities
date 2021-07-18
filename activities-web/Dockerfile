FROM java:8


COPY *.jar /app.jar

CMD ["--server.port=8083"]
EXPOSE 8083

ENTRYPOINT ["java","-jar","/app.jar"]

