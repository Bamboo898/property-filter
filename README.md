# Property Filter
通过简单的表达式，从对象中拿到需要转化的属性值并对其进行一系列的转化处理，最终得到你想要的文字或对象。

## 安装
```shell
git clone https://github.com/Bamboo898/property-filter.git
cd property-filter
mvn clean install -Dmaven.test.skip=true
```
执行以上命令后可在项目中加入maven依赖，如下:
```xml
<dependency>
	<groupId>com.gs.utils</groupId> 
	<artifactId>property-filter</artifactId>
	<version>1.2</version>
</dependency>
```

## 用法
```java
PropertyUtils.getProperty(user, "mobileNumber|mask:'3':'4'");
```
或者
```java
PropertyUtils.getProperty(user, "status|dynDesc:'common.user.status'", applicationContext);
```
或者将表达式配置在XML或者properties中动态获取

> *表达式解释:* 表达式以符号`|`分隔。 `|`符号前面表示对象中的字段名, 也就是要处理对象中的哪个属性。`|`后面表示所要使用的过滤器类型, `:`号后面接传给过滤器的参数, 每一个`:`表示一个参数, 每个过滤器可传多个参数。  

> *NOTE:* `dynDesc`过滤器需要传入Spring的ApplicationContext实例

> *Tips:* 可多个过滤器同时使用: `mobileNumber | mask:'3':'4' | defaultVal:'未输入手机号'`

## 内置过滤器
### `dynDesc`
- 作用: 主要用于通过拼接资源key来从国际化资源中拿取值。
- 用法: `orderStatus | dynDesc:'common.orderstatus':'zh_CN'`
- 参数1: 国际化资源的Key，必填
- 参数2: 地区标识，选填，默认为zh_CN

### `dateFormat`
- 作用: 用于将日期对象格式化
- 用法: `birthDay | dateFormat:'yyyy-MM-dd'` 结果: 1991-01-13
- 参数1: 日期的目标格式, 必填
- 参数2: 地区标识, 选填

### `mask`
- 作用: 用于将字符串部分文字转换成 ***
- 用法: `mobileNumber | mask:'3':'4'` 结果: 134***4059
- 参数1: *号前面字符长度, 必填
- 参数2: *号后面字符长度, 必填

### `coll2Str`
- 作用: 用于将一个collection中的对象的某个属性值提取出来，并转换成以 `,` 分隔的字符串
- 用法: `users | coll2Str:'userName'` 结果: 李四, 王五, 赵六
- 参数1: 要提取的属性名, 必填

### `collSort`
- 作用: 对collection进行指定字段的排序, 支持多属性排序。将返回ArrayList。
- 用法: `users | collSort:'userName':'age.desc'`
- 参数1: 要排序的第一个属性(默认使用字典顺序排序)
- 参数2: 要排序的第二个属性名及排序方式

### `defaultVal`
- 作用: 用于在传入值为null时返回一个特定的值
- 用法: `amount | defaultVal:'0.0'` 结果: 0.0
- 参数1: 默认值, 必填

## 自定义过滤器
在自定义过滤器时，需实现`com.gs.utils.propertyfilter.processors.PipeProcessor`接口, 并加入注解`@Processor`。

> *Tips:* 可将自定义的过滤器放入Spring容器，只需加入`@Component`注解即可。
