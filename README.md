# Property Filter
此项目属于Property的工具类。主要作用是通过反射拿到对象中的值并对值进行转化处理，最终得到一个字符串。

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
	<version>1.0</version>
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
> *NOTE:* dynDesc过滤器需要传入Spring的ApplicationContext实例

> *Tips:* 可多个过滤器同时使用: mobileNumber | defaultVal:'该未输入手机号' | mask:'3':'4'

## 内置过滤器
### dynDesc
- 作用: 主要用于通过拼接资源key来从国际化资源中拿取值。
- 用法: orderStatus | dynDesc:'common.orderstatus':'zh_CN'
- 参数1: 国际化资源的Key，必填
- 参数2: 地区标识，选填，默认为zh_CN

### dateFormat
- 作用: 用于将日期对象格式化
- 用法: birthDay | dateFormat:'yyyy-MM-dd' 结果: 1991-01-13
- 参数1: 日期的目标格式, 必填
- 参数2: 地区标识, 选填

### mask
- 作用: 用于将字符串部分文字转换成 ***
- 用法: mobileNumber | mask:'3':'4' 结果: 134***4059
- 参数1: *号前面字符长度, 必填
- 参数2: *号后面字符长度, 必填

### coll2Str
- 作用: 用于将一个collection中的对象的某个属性值提取出来，并转换成以 "," 分隔的字符串
- 用法: users | coll2Str:'userName' 结果: 李四, 王五, 赵六
- 参数1: 要提取的属性名, 必填

### defaultVal
- 作用: 用于在传入值为null时返回一个特定的值
- 用法: amount | defaultVal:'0.0' 结果: 0.0
- 参数1: 默认值, 必填
