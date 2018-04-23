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

## 内置过滤器
### dynDesc
- 作用: 国际化处理类。主要用于通过拼接资源key来从国际化资源中拿取值。
- 用法: orderStatus | dynDesc:'common.orderstatus':'zh_CN'
- 参数1: 国际化资源的Key，必填
- 参数2: 地区标识，选填，默认为zh_CN

### 
