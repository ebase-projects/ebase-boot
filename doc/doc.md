### 项目打包
```
### 预编译
mvn release:prepare -DautoVersionSubmodules=true -Darguments="-DskipTests" -Pnexus-company

### 回滚
mvn release:rollback

### 正式发布
mvn release:perform -DuseReleaseProfile=false -Pnexus-company
```


### 常见问题
#### JSON Java 8 LocalDateTime format in Spring Boot
https://stackoverflow.com/questions/29956175/json-java-8-localdatetime-format-in-spring-boot

#### 解决Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin:3.7.0:compile
通用办法就是：
https://blog.csdn.net/fanrenxiang/article/details/80864908
由于项目所需jdk版本和你当前使用的jdk版本不一致导致的，因为我项目的pom.xml中定义了java版本为1.8，但是我实际idea中run这个项目却是1.7

也有可能是 idea 中选择的是 openjdk ,命令行选择的是 oraclejdk 导致的。
