[![](https://jitpack.io/v/saymagic/Galaxy.svg)](https://jitpack.io/#saymagic/Galaxy)

[![Build Status](https://travis-ci.org/saymagic/Galaxy.svg?branch=master)](https://travis-ci.org/saymagic/Galaxy)

## Galaxy

基于配置文件驱动的日志系统。

## Features

- 基于配置文件的日志打印规则，便于线上动态调整日志输出，也有利于线上问题的查找
- 支持单日志的多TAG 标识，可以更具有针对性的对公共模块进行日志检索
- 自定义多渠道日志输出
- 自定义输出格式
- 自定义过滤规则

## Usage

1. 配置Maven

 ```
maven { url 'https://jitpack.io' }

 ```
2. 引入Galaxy

 ```
 compile 'com.github.saymagic:Galaxy:v0.0.1'
 ```
3. 初始化Galaxy

 ```  
Galaxy.bigBang(Your Factor, new GalaxyBridgeAdapter()));
 ```

​	1. Factor 说明：

​	    Factor中封装了日志的配置信息，你可以通过`Factor.creatFromXml`

 	    或者 `Factor.creatFromJson`方法来获取。或者直接提供`Factor`的子类，复写其抽象方法即可。

4. 打印日志

   1. 获取Star

      ```
      Star mStar = Galaxy.get(Your Main Tag);
      ```

   2. 打印日志：

      ```
      mStar.d("Your Message");
      mStar.i("Yore {}", "Message");
      ...
      ```

##  配置文件参考

```
{
    "maxThreadSize":3,
    "appenders":[
        {
            "type":"console",
            "layout":{
                "type":"simple"
            },
            "filter":[
                {
                    "type":"tag",
                    "parameters":[
                        "Main"
                        "Second"
                    ]
                }
            ]
        },
        {
            "type":"file",
            "parameters":[
                "/data/user/0/com.netease.mail.galaxy.demo/files/galaxy.log"
            ],
            "layout":{
                "type":"simple"
            },
            "filter":[
                {
                    "type":"level",
                    "parameters":[
                        "e",
                    ]
                }
            ]
        }
    ]
}
```

> 上面的JSON文件描述了如下规则：
>
> 1. maxThreadSize属性定义了日志输出所占用的线程最大数量。
> 2. appenders属性定义了console和file两个输出
> 3. console中仅输出包含“Main”或“Second” tag的日志
> 4. file中仅输出级别为e的日志

