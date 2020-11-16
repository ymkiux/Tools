## BaseTools

打造通用工具类库

![Release](https://jitpack.io/v/ymkiux/tools.svg) ![Apache Licence](http://img.shields.io/badge/license-Apache2.0-ff6600.svg)

#### 引入

```css
implementation 'com.github.ymkiux:tools:latest.release'
```
##### 已实现功能
<table> <tr><td bgcolor="#E6E6E6"><center>Json</center></td><td bgcolor="#E6E6E6"><center>OkGo</center></td><td bgcolor="#E6E6E6"><center>Tools</center></td>  <td bgcolor="#E6E6E6"><center>SP</center></td><td bgcolor="#E6E6E6"><center>Str</center></td></tr>     
    <tr><td bgcolor="#FFFFFF"><center>Json obejct转化为T，Json array转化为List<T></center></td>  <td bgcolor="#FFFFFF"><center>普通get/post回调，携带参数的post回调</center></td>   <td bgcolor="#FFFFFF"><center>通过图片链接获取bitmap</center></td>  <td bgcolor="#FFFFFF"><center>SP简单封装(增删查)</center></td>  <td bgcolor="#FFFFFF"><center>字符串正则检测</center></td> </tr>     
    <tr><td bgcolor="#FFFFFF"><center>Json对象,数组类型字符串通过key获取value</center></td>  <td bgcolor="#FFFFFF"><center>携带参数以及头部自定义参数</center></td>   <td bgcolor="#FFFFFF"><center>通过文件下载链接获取流并下载至公有目录Download下</center></td>  <td colspan="2" bgcolor="#E6E6E6"><center>DataManager</center></td> </tr>
    <td bgcolor="#FFFFFF"><center>将hashmap转化为Jsonobject，JsonArray</center></td><td bgcolor="#FFFFFF"><center>--------------</center></td>  <td bgcolor="#FFFFFF"><center>--------------</center></td><td bgcolor="#FFFFFF"><center>获取存储的图片位图数组，保存图片位图至内置存储内</center></td><td bgcolor="#FFFFFF"><center>删除数据库，键值对，应用内置cache字眼目录下所有文件</center></td></tr> 
#### 简单使用

##### Json解析转化T

```
OkGo.get().url("https://wanandroid.com/wxarticle/chapters/json").build()!!.enqueue(object :Callback{
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                val string = response.body!!.string()
                val jsonObject = Json.getJsonObject(string, WanAndroidHomeBean::class.java)
                Log.d(TAG, "onResponse: "+jsonObject.data)
            }
        })
```

###### 日志结果

```
2020-11-11 15:32:18.755 9546-9618/com.twst D/测试: onResponse: [com.twst.bean.WanAndroidHomeBean$DataBean@6822129, com.twst.bean.WanAndroidHomeBean$DataBean@a38ccae, com.twst.bean.WanAndroidHomeBean$DataBean@5205a4f, com.twst.bean.WanAndroidHomeBean$DataBean@264b5dc, com.twst.bean.WanAndroidHomeBean$DataBean@872a6e5, com.twst.bean.WanAndroidHomeBean$DataBean@1c905ba, com.twst.bean.WanAndroidHomeBean$DataBean@a324c6b, com.twst.bean.WanAndroidHomeBean$DataBean@7553c8, com.twst.bean.WanAndroidHomeBean$DataBean@62bcc61, com.twst.bean.WanAndroidHomeBean$DataBean@cf1c386, com.twst.bean.WanAndroidHomeBean$DataBean@c274447, com.twst.bean.WanAndroidHomeBean$DataBean@96fe474, com.twst.bean.WanAndroidHomeBean$DataBean@1f7cd9d, com.twst.bean.WanAndroidHomeBean$DataBean@b969212]
```

##### Json解析转化List\<T\>

```
OkGo.get().url("https://wanandroid.com/wxarticle/chapters/json").build()!!
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val string = response.body!!.string()
                    val jsonObjectValue = Json.getJsonObjectValue(string, "data").toString()
                    val jsonArrayList =
                        Json.getJsonArrayList(jsonObjectValue, WanAndroidHomeBean::class.java)!!
                    for (i in 0..jsonArrayList.size-1){
                        Log.d(TAG,  jsonArrayList.get(i).name)
                    }
                }
            })
```

###### 日志结果

```
2020-11-11 15:15:04.047 4720-4821/com.twst D/测试: 鸿洋
2020-11-11 15:15:04.048 4720-4821/com.twst D/测试: 郭霖
2020-11-11 15:15:04.048 4720-4821/com.twst D/测试: 玉刚说
2020-11-11 15:15:04.048 4720-4821/com.twst D/测试: 承香墨影
2020-11-11 15:15:04.048 4720-4821/com.twst D/测试: Android群英传
2020-11-11 15:15:04.048 4720-4821/com.twst D/测试: code小生
2020-11-11 15:15:04.048 4720-4821/com.twst D/测试: 谷歌开发者
2020-11-11 15:15:04.048 4720-4821/com.twst D/测试: 奇卓社
2020-11-11 15:15:04.048 4720-4821/com.twst D/测试: 美团技术团队
2020-11-11 15:15:04.048 4720-4821/com.twst D/测试: GcsSloop
2020-11-11 15:15:04.048 4720-4821/com.twst D/测试: 互联网侦察
2020-11-11 15:15:04.048 4720-4821/com.twst D/测试: susion随心
2020-11-11 15:15:04.048 4720-4821/com.twst D/测试: 程序亦非猿
2020-11-11 15:15:04.048 4720-4821/com.twst D/测试: Gityuan
```

##### Okhttp post请求

```
OkGo.post().url("https://www.wanandroid.com/lg/uncollect_originId/2333/json")
            .addParams("id", "1").build()!!.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                Log.d(TAG, "onResponse: "+response.body!!.string())
            }
        })
```

###### 日志结果

```
2020-11-11 15:27:07.092 7904-7979/com.twst D/测试: onResponse: {"errorCode":-1001,"errorMsg":"请先登录！"}
```

#### 尾言

> 如果觉得还不错,期待你的star！