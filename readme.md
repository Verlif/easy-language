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

```properties
# resource_addon.properties
wohao=我好
```

这三个文件都放在`resources`目录下，此时我们可以通过以下代码来获取：

```java
// 实例化信息获取器，这里也可以设定默认语言，否则以系统语言为准。
MessageGetter getter = new MessageGetter();
// 添加语言文件，可以同时添加不同文件夹。也可以在添加时直接指定对应语言。
getter.addResource("src\\main\\resources");
// 更改信息获取器设置
getter.getConfig().setResultType(GetterConfig.ResultType.WITH_NULL);
// 使用信息获取器默认的语言获取文本
System.out.println(getter.get("nihao"));
// 指定语言获取文本
System.out.println(getter.get("nihao", Locale.ENGLISH));
// 由于之前的设定，当没有此语言文件时，优先从备用语言资源中获取。
System.out.println(getter.get("nihao", Locale.FRENCH));
// 可以直接指定语言文件后缀
System.out.println(getter.get("nihao", "test"));
// 由于之前的设定，当备用语言资源中也没有此语言代码时，会返回null
System.out.println(getter.get("wohao"));
// 对已有的语言进行信息追加。这里的语言传入null则会追加到备用语言资源中。
getter.addResource("src\\main\\resources", (Locale) null);
// 此时再次获取则会查询到信息
System.out.println(getter.get("wohao"));
```

__注意__：没有后缀的`resource.properties`是作为 __备用语言资源__，也就是最终替补出现的。当没有需要的语言或文本代码时，就会从这个资源中提取文本。

所以最终的输出就是：

```text
你好
hello
你好
nihaotest
null
我好
```

## GetterConfig

| 参数         | 参数类型 | 参数说明                                                                                                                                                                                                                            |
|------------|------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| resultType | 枚举   | 取值模式，包括以下模式：<br/>`Hard` - 严格模式，只在设定的语言资源中查询，不进行备用语言资源查询。设定的语言资源中不存在时，返回 __NULL__。<br/>`EASY`(默认) - 兼容模式，设定的语言资源中不存在时，在默认语言资源中查询。当默认语言资源中也不存在时，返回文本代码。<br/>`WITH_NULL` - 空值模式，设定的语言资源中不存在时，在默认语言资源中查询。当默认语言资源中也不存在时，返回 __NULL__。 |

## 说明

1. 目前不支持对文本变量进行操作，主要考虑是简化组件。需要的话可以使用 [vars-parser](https://github.com/Verlif/vars-parser) ，只需要进行一个简单的继承和封装就可以了。

## 添加依赖

1. 添加Jitpack仓库源

   maven

   ```xml
   <repositories>
      <repository>
          <id>jitpack.io</id>
          <url>https://jitpack.io</url>
      </repository>
   </repositories>
   ```

   Gradle

   ```text
   allprojects {
     repositories {
         maven { url 'https://jitpack.io' }
     }
   }
   ```

2. 添加依赖

   __lastVersion__ [![](https://jitpack.io/v/Verlif/easy-language.svg)](https://jitpack.io/#Verlif/easy-language)

   maven

   ```xml
      <dependencies>
          <dependency>
              <groupId>com.github.Verlif</groupId>
              <artifactId>easy-language</artifactId>
              <version>0.2</version>
          </dependency>
      </dependencies>
   ```

   Gradle

   ```text
   dependencies {
     implementation 'com.github.Verlif:easy-language:0.2'
   }
   ```
