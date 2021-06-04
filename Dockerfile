FROM maven AS build
WORKDIR /MoscowWeatherArchive
COPY . .
RUN mvn package

FROM tomcat
COPY --from=build /MoscowWeatherArchive/target/spring-mvc-app1.war /usr/local/tomcat/webapps 
