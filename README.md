# Virtex
It's a Demo Application for a Virtual exchange that can be used for trading of
any virtual instrument by way of bidding.

# Requirements
JDK 1.8  
MySql 5.7  

# Getting Started
Use the following steps to start the application for general environment.
## Set Environment Variables
```
export DB_HOST=localhost  
export DB_PORT=3306
export DB_NAME=virtex
export DB_USERNAME=virtex
export DB_PASSWORD=virtex
```
Note: Change values as required.

## Running the Application

### Using the Maven Plugin  
Run `mvn spring-boot:run` for external maven  
or   `./mvnw spring-boot:run` for maven wrapper

### Running as a Packaged Application  
For packaging:  
`mvn package`  
For running packaged jar:  
`java -Dspring.profiles.active=local $JAVA_OPTS -jar target/virtex-0.0.1-SNAPSHOT.jar`

## Change instruments Configuration
Use environment variable `VIRTEX_INSTRUMENTSCONFIG` to pass path of instruments
configuration file with respect to classpath.  
Example file `instruments-example.conf` present in resources directory is being
used by default.