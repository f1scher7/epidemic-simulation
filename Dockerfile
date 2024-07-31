FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /EpidemicSimulation

COPY pom.xml .
COPY src ./src

RUN mvn clean install

FROM openjdk:17-alpine
WORKDIR /EpidemicSimulation
COPY --from=build /EpidemicSimulation/target/EpidemicSimulation-0.0.1-SNAPSHOT.jar /EpidemicSimulation/epidemicsimulation.jar

EXPOSE 8080
CMD ["java", "-jar", "epidemicsimulation.jar"]