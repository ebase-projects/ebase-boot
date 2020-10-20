### 项目打包
```
mvn release:prepare -DautoVersionSubmodules=true -Darguments="-DskipTests" -Pnexus-linewell-sd

mvn release:perform -DuseReleaseProfile=false -Pnexus-linewell-sd
```


### 常见问题
#### JSON Java 8 LocalDateTime format in Spring Boot
https://stackoverflow.com/questions/29956175/json-java-8-localdatetime-format-in-spring-boot

