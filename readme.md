# EASY LANGUAGE

__简单语言包__

这仅仅是一个简单的语言文件切换工具，与Java内置的`ResourceBundle`类似，都是通过`properties`文件来加载不同的语言文件的。  
唯一的区别就是更自由一些。

## 使用

例如此时我们有如下三个语言文件：

```properties
# resource.properties
nihao=你好
```

```properties
# resource_en.properties
nihao=hello
```

```properties
# resource_test.properties
nihao=nihaotest
```

这三个文件都放在`resources`目录下，此时我们可以通过以下代码来获取：

```java
// 实例化信息获取器，这里也可以设定默认语言，否则以系统语言为准。
MessageGetter getter = new MessageGetter();
// 添加语言文件，可以同时添加不同文件夹。也可以在添加时直接指定对应语言。
getter.addResource("src\\main\\resources");
// 使用信息获取器默认的语言获取文本
System.out.println(getter.get("nihao"));
// 指定语言获取文本
System.out.println(getter.get("nihao", Locale.ENGLISH));
// 当没有此语言文件时，优先从默认语言资源中获取
System.out.println(getter.get("nihao", Locale.FRENCH));
// 可以直接指定语言文件后缀
System.out.println(getter.get("nihao", "test"));
// 当默认语言资源中没有此语言代码时，会直接返回语言代码
System.out.println(getter.get("wohao"));
```

__注意__：没有后缀的`resource.properties`是作为 __默认语言资源__，也就是最终替补出现的。当没有需要的语言或文本代码时，就会从这个资源中提取文本。

所以最终的输出就是：

```text
你好
hello
你好
nihaotest
wohao
```

## 说明

1. 目前不支持对文本变量进行操作，主要考虑是简化组件。需要的话可以使用 [vars-parser](https://github.com/Verlif/vars-parser) ，只需要进行一个简单的继承和封装就可以了。

## 添加依赖

1. 添加Jitpack仓库源

> maven
> ```xml
> <repositories>
>    <repository>
>        <id>jitpack.io</id>
>        <url>https://jitpack.io</url>
>    </repository>
> </repositories>
> ```

> Gradle
> ```text
> allprojects {
>   repositories {
>       maven { url 'https://jitpack.io' }
>   }
> }
> ```

2. 添加依赖

> maven
> ```xml
>    <dependencies>
>        <dependency>
>            <groupId>com.github.Verlif</groupId>
>            <artifactId>easy-language</artifactId>
>            <version>0.1</version>
>        </dependency>
>    </dependencies>
> ```

> Gradle
> ```text
> dependencies {
>   implementation 'com.github.Verlif:easy-language:0.1'
> }
> ```
