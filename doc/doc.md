### 项目打包
```
mvn release:prepare -DautoVersionSubmodules=true -Darguments="-DskipTests" -Pnexus-company

mvn release:rollback

mvn release:perform -DuseReleaseProfile=false -Pnexus-company
```


### 常见问题
#### JSON Java 8 LocalDateTime format in Spring Boot
https://stackoverflow.com/questions/29956175/json-java-8-localdatetime-format-in-spring-boot

