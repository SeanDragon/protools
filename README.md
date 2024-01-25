# protools

[![Build Status](https://travis-ci.org/SeanDragon/protools.svg?branch=master)](https://travis-ci.org/SeanDragon/protools)
[![License](http://img.shields.io/:license-apache-blue.svg)](https://github.com/SeanDragon/protools/blob/master/LICENSE)
[![JDK 1.8](https://img.shields.io/badge/JDK-1.8-blue.svg)](#protools)

历经开发周期两年，并且应用过千万级别项目的工具箱

我也吸收了[https://www.v2ex.com/t/423700](https://www.v2ex.com/t/423700)该贴中v友对我的建议和意见，项目和我都会变得更好，谢谢大家

大家如果想使用可以先执行mvn clean install即可在本地的其他maven项目中使用

## [更新日志](https://github.com/SeanDragon/protools/blob/master/changelog.txt)
- 简单过了一遍阿里的规范手册，把一些显而易见的代码优化了。
- 去除私库中的包，替换为官方包。

## 开发计划
- 使用阿里的规范手册整理规范所有代码
- 将当时临时添加或欠缺考虑的命名或方法进行优化
- 将maven私人仓库转移到中心仓库

## 目录

- [protools](#protools)
    - [common](#common)
    - [http](#http)
    - [mail](#mail)
    - [security](#common)
    - [all](#all)

### common
* 数据的处理
* 文件的处理
* script 引擎的封装 
* 系统方面查询的封装
* 日期对象的封装DatePlus
* 数值对象的封装Decimal
### http
* 统一发送对象为HttpSend
* 统一接收对象为HttpReceive
* 三个版本的 http 客户端（Jdk、Netty 和 OkHttp）
### mail
* 封装 JavaMail，并采用了队列等方法提高性能，并简化了发送过程
### security
* 封装了 jdk 和 bouncycastle 中几十种常见加密方式
### all
* 如果需要使用上述多个模块，可以导入all模块以使用所有模块