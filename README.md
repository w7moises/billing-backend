# Spring Boot 3 

Project contains an spring boot project example.

[![MIT licensed][shield-mit]](LICENSE)
[![Java v17][shield-java]](https://openjdk.java.net/projects/jdk/17/)
[![Spring Framework v6][shield-spring]](https://jakarta.ee/specifications/platform/10/)
[![Spring Boot v3][shield-spring-boot]](https://jakarta.ee/specifications/platform/10/)

## Dependencies


| Dependency                     | description                                                                 | 
|--------------------------------|-----------------------------------------------------------------------------|
| spring-boot-starter-data-jpa   | Provides support for the JPA persistence layer                              |      
| spring-boot-starter-web        | Offers a set of dependencies for building web applications using Spring MVC |  
| mysql-connector-j              | JDBC connector for MySQL                                                    |  
| modelmapper                    | Facilitates object conversion between different models                      |             
| spring-boot-starter-validation | Provides support for data validation                                        |                 
| spring-boot-starter-security   | Provides support for implementing security                                  |                  
| hibernate-envers               | Hibernate extension providing support for entity auditing                   |                
| jasperreports                  | Library for generating reports and documents                                |        

## Requirements

The list of tools required to build and run the project:

* Open JDK 17
* Apache Maven 3.8

## Building

In order to build project use:

```bash
mvn clean package
```

## Configuration

Configuration can be updated in `application.properties` or using environment variables.

## Running

In order to run using embedded Apache Tomcat server use:

```bash
java -jar target/simple-rpg-1.0.0-SNAPSHOT.jar
```

## License

Project is licensed under the [MIT](LICENSE) license.


## Author

Copyright &copy; 2020 - 2023, Walter (Hipe) Molina

![alt-text](https://static.ankama.com/dofus/ng/modules/mmorpg/community/directories/characters/ornaments/ornament_82.png)

[shield-mit]: https://img.shields.io/badge/license-MIT-blue.svg
[shield-java]: https://img.shields.io/badge/Java-17-blue.svg
[shield-spring]: https://img.shields.io/badge/Spring%20Framework-6-blue.svg
[shield-spring-boot]: https://img.shields.io/badge/Spring%20Boot-3-blue.svg
