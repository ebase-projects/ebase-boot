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

## 模块

`ebase-boot`的源码构建使用到了多个模块，下面是一个快速概述：

### ebase-boot

`ebase-boot`模块是编译整个项目的根目录，所提供的能力如下所示：

- 提供项目编译时使用的公共插件（`flatten`、`release`...）
- 提供项目编译时使用的`Maven`仓库配置
- 统一项目编译的JDK版本

### ebase-boot-autoconfigure

`ebase-boot-autoconfigure`是最为主要的核心模块，内部提供了**全部组件**的`自动化配置类`，这一点完全是利用`SpringBoot`
所提供的[条件判断注解](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-condition-annotations)，位于`resources/META-INF`目录下还提供了附加的配置参数元数据定义内容文件：`additional-spring-configuration-metadata.json`，项目启用时我们所看到的`banner`输出内容也位于该模块中。

### ebase-boot-dependencies

该模块的功能与`spring-boot-dependencies`一致，都是为了固化项目中所使用的依赖版本号，让我们在构建项目中可以很好地对某一个依赖进行升级，不再担心各个依赖之间版本不兼容的困扰。

### ebase-boot-parent

该模块继承自`ebase-boot-dependencies`，可直接使用固化版本后的依赖，是构建其他模块的统一父依赖。

### ebase-boot-starters

该模块下定义了开发过程中具体使用的`Starter`依赖，`Starter`依赖内不包含任何的框架代码，只有一个`pom.xml`
文件，具体的自动化配置实现以及具体集成第三方的实现分别位于：`ebase-boot-autoconfigure`、`ebase-boot-plugins`模块内。

使用方式与`spring-boot-starter-xxx`一致，比如：在项目中集成限流组件，我们只需要在`pom.xml`中添加`ebase-boot-starter-rate-limiter`
依赖即可，版本也无需添加，因为已经通过`ebase-boot-dependencies`模块进行了固化版本依赖。

### ebase-boot-tools

该模块会定义一些常用到的工具类，比如：`ApplicationContext`、`BeanFactory`等。

该模块同样是由`ebase-boot-autoconfigure`进行自动化配置，将部分工具类自动注册到`IOC`。 添加完组件我们就可以根据官方参考文档找到对应组件的文档进行配置使用了。

## License

采用Apache2开源许可进行编写。

## 开源支持

<a href="https://www.jetbrains.com/?from=ebase-projects"><img src="http://blogimage.dwliu.me/image/20200709103201-2ZxFX7.jpg" width="100" heith="100"/></a>

