# ebase-boot 一系列开箱即用的组件


[![](https://img.shields.io/badge/License-Apache%202.0-green.svg)](https://github.com/weibocom/motan/blob/master/LICENSE)
![](https://img.shields.io/badge/JDK-1.8+-green.svg)

## 什么是 ebase-boot？

`ebase-boot` 是一款基于`SpringBoot` 的服务集成基础框架，内部提供了第三方框架的封装集成，让接口开发者可以选择性完成开箱即用。

对`SpringBoot`简单了解的开发者就可以编写安全稳定的接口服务，可为移动端、网页端等多个端点提供丰富的安全接口。

`ebase-boot` 依赖于SpringBoot，可以使用`ebase-boot` 构建独立的Java应用程序。

## 安装

如果你是使用Maven来构建项目，你需要添加`ebase-boot`的版本依赖到你的pom.xml文件内，如下所示：

```xml
<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>me.dwliu.ebase</groupId>
      <artifactId>ebase-boot-dependencies</artifactId>
      <version>${lastVersion}</version>
      <scope>import</scope>
      <type>pom</type>
    </dependency>
  </dependencies>
</dependencyManagement>
```

> 注意：**lastVersion**需要替换为最新的`ebase-boot`版本

版本依赖添加完成后，我们接下来就可以进行添加项目内所需要的`ebase-boot`组件，下面是使用`Mybatis`组件`ebase-boot-starter-mybatis`示例：

```xml
<dependencies>
  <dependency>
    <groupId>me.dwliu.ebase</groupId>
    <artifactId>ebase-boot-starter-mybatis</artifactId>
  </dependency>
</dependencies>
```

添加完组件我们就可以根据官方参考文档找到对应组件的文档进行配置使用了。

## License

ApiBoot采用Apache2开源许可进行编写。

## 开源支持

<a href="https://www.jetbrains.com/?from=ebase-projects"><img src="http://blogimage.dwliu.me/image/20200709103201-2ZxFX7.jpg" width="100" heith="100"/></a>

