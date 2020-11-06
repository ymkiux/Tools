## BaseTools

打造通用工具类库

![Release](https://jitpack.io/v/ymkiux/tools.svg) ![Apache Licence](http://img.shields.io/badge/license-Apache2.0-ff6600.svg)

#### 引入

```css
implementation 'com.github.ymkiux:tools:0.0.12'
```
##### 已实现功能

| Json| OkGo |
| :--------------: | :--------------: |
| Json obejct转化为T，Json array转化为List<T> |普通get/post回调，携带参数的post回调|
| Json对象类型字符串通过key获取value |携带参数以及头部自定义参数|
| Json数组类型字符串通过key获取value |通过图片链接获取bitmap|
| 将hashmap转化为Jsonobject，JsonArray |通过文件下载链接获取流并下载至公有目录Download下|

#### 简单使用

##### Json解析转化T

```
OkGo.getUrl("https://wanandroid.com/wxarticle/chapters/json", object : Callback {
    override fun success(responseBody: ResponseBody?) {
        val string = responseBody!!.string()
        val jsonArrays:TestM = Json.getJsonObject(
            string,
            TestM::class.java
        )
    }
    override fun error(e: IOException) {

    }
})
```

##### Json解析转化List\<T\>

```
OkGo.getUrl("https://wanandroid.com/wxarticle/chapters/json", object : Callback {
    override fun success(responseBody: ResponseBody?) {
        val string = responseBody!!.string()
        val jsonArray = Json.getJsonObject(string,"data")
        val jsonArrays:List<TestM> = Json.getJsonArrayList(
            jsonArray.toString(),
            TestM::class.java
        )
    }
    override fun error(e: IOException) {

    }
})
```

##### Okhttp post请求

```
val hashMap = java.util.HashMap<String, String>()
hashMap.put("local_version","1.090103")
OkGo.postUrl("http://192.168.0.104:8080/api/update",hashMap,object :Callback{
    /**
     * Called when the HTTP response was successfully returned by the remote server
     */
    override fun success(responseBody: ResponseBody?) {
        Log.d("测试", "success: " + responseBody!!.string())
    }

    /**
     * Called when the request could not be executed due to cancellation
     */
    override fun error(e: IOException) {
        Log.d("测试", "error: " )
    }

})
```

> 由于空闲时间编写 如有不足还请指正