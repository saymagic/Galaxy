[![](https://jitpack.io/v/saymagic/Galaxy.svg)](https://jitpack.io/#saymagic/Galaxy)

[![Build Status](https://travis-ci.org/saymagic/Galaxy.svg?branch=master)](https://travis-ci.org/saymagic/Galaxy)

## Galaxy

基于配置文件驱动的日志系统。

## Features

- 基于配置文件驱动的日志打印框架，便于动态调整线上日志输出，也有利于线上问题的查找
- 支持单日志的多TAG 标识，可以更具有针对性的对公共模块进行日志检索
- 自定义多渠道日志输出
- 自定义输出格式
- 自定义过滤规则

## Usage

- 配置Maven

 ```
maven { url 'https://jitpack.io' }

 ```
- 引入Galaxy

 ```
 compile 'com.github.saymagic:Galaxy:v1.0.1'
 ```
- 初始化Galaxy

 ```  
Galaxy.bigBang(Your Factor, new GalaxyBridgeAdapter()));
 ```

- 打印日志

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
- 说明
	- Factor
		
	  Factor中封装了日志的配置信息，你可以通过`Factor.creatFromJson`方法来获取。或者直接提供`Factor`的子类，复写其抽象方法即可。
   - Star
   
   	  Galaxy对外的主要接口，提供打印相关的函数
   	  
	- Appender、Layout、filter
	
		三者都包含type与parameters属性，type用于表明类型（字符串），parameters用于描述该类型的参数（数组）
		
		Appender为日志的输出方式的抽象，用于指定日志的输出位置，type原生支持console（控制台）、file（文件）、android（android log）三种方式；
		
		Layout与Filter都是Appender的内部参数，用于更近一步的描述Appender:
		* layout为日志的输出样式，原生提供simple模式；
		* filter为过滤器，单个Appender支持多个filter，，原生type支持tag与level两种，tag为按照标签进行过滤，level为按照级别进行过滤。
		
		Appender、 Layout、Filter都只是一种抽象，本库中也仅提供了基本的实现，具体的方式可按照自己的业务需求进行重写。

		
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

