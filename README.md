# protools

[![Build Status](https://travis-ci.org/SeanDragon/protools.svg?branch=master)](https://travis-ci.org/SeanDragon/protools)
[![License](http://img.shields.io/:license-apache-blue.svg)](https://github.com/SeanDragon/protools/blob/master/LICENSE)
[![JDK 1.8](https://img.shields.io/badge/JDK-1.8-blue.svg)]()

历经开发周期两年，并且应用过千万级别项目的工具箱

暂时还未添加到maven中心仓库，这几天部门解散，本人正在找工作，中心仓库的事情后续会跟进

大家如果想使用可以先执行mvn clean install即可在本地的其他maven项目中使用

### 目录

- [protools](#protools)
    - [common](#common)
    - [http](#http)
    - [mail](#mail)
    - [security](#common)
    - [all](#all)

## common
* 数据的处理
* 文件的处理
* script 引擎的封装 
* 系统方面查询的封装
* 日期对象的封装DatePlus
* 数值对象的封装Decimal
## http
* 统一发送对象为HttpSend
* 统一接收对象为HttpReceive
* 三个版本的 http 客户端（Jdk、Netty 和 OkHttp）
## mail
* 封装 JavaMail，并采用了队列等方法提高性能，并简化了发送过程
## security
* 封装了 jdk 和 bouncycastle 中几十种常见加密方式
## all
* 如果需要使用上述多个模块，可以导入all模块以使用所有模块