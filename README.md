# protools

[![Build Status](https://travis-ci.org/SeanDragon/protools.svg?branch=master)](https://travis-ci.org/SeanDragon/protools)

历经开发周期两年，并且应用过千万级别项目的工具箱

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