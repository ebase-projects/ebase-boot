### 项目打包
```
mvn release:prepare -DautoVersionSubmodules=true -Darguments="-DskipTests" -Pnexus-linewell-sd

mvn release:perform -DuseReleaseProfile=false -Pnexus-linewell-sd
```
