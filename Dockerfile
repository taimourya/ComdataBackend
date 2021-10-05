FROM maven as comdataApi
COPY . /home/

WORKDIR /home

#RUN mvn clean install spring-boot
RUN mvn package -Dmaven.test.skip

FROM openjdk
COPY --from=comdataApi /home/target/*.jar /home/app.jar
EXPOSE 8080
CMD ["java", "-jar", "/home/app.jar"]