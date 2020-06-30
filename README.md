# Robot
基于酷Q和CQhttp插件用Spring Boot编写的qqRobot
http://47.98.252.1:7000/arina

以下内容摘自我的博客


---
title: QQ聊天机器人
date: 2020-05-30 09:39:14
tags:

---

# QQ聊天机器人-教程向

这篇文章将引导大家如何去写一个QQ聊天机器人。在此之前，先给大伙儿展示一下本fw的Robot的一些特点和功能：

<img src="http://ww1.sinaimg.cn/large/005VT09Qly1gfa8i163zbj30aa08wglu.jpg"/>

Name: Arina

Attribute: 萌妹子

官网：http://47.98.252.1:7000/arina

## 功能：

#### 发送以#为前缀的消息可以触发图灵机器人的功能，这些功能包含但不限于：

- 天气查询<img src="http://ww1.sinaimg.cn/large/005VT09Qly1gga0qsbw9ij30hr0crq3c.jpg"/>
- 数学运算<img src="http://ww1.sinaimg.cn/large/005VT09Qly1gfa8lxa5h7j30mf03z3ye.jpg"/>
- 聊天对话<img src="http://ww1.sinaimg.cn/large/005VT09Qly1gfa8mred9wj30mz056mx3.jpg"/>
- 单词翻译<img src="http://ww1.sinaimg.cn/large/005VT09Qly1gfa8nrobwvj30m904p3yf.jpg"/>

#### 不需要#前缀能对一些特定的语句做出反应

<img src="http://ww1.sinaimg.cn/large/005VT09Qly1gfa8pfypwbj30mn04wdfp.jpg"/>



#### 可以在群里@其他人或者禁言（需要管理员权限）

#### <img src="http://ww1.sinaimg.cn/large/005VT09Qly1gga0s0pwzcj31hc091js7.jpg"/>

#### 可以通过接口向指定的群手动发送消息

<img src="http://ww1.sinaimg.cn/large/005VT09Qly1gga0uf9r82j31hc087jrw.jpg"/>

#### 定时天气播报

每天早上8点准时向qq群播送今日天气状况，误差小于2秒。

<img src="http://ww1.sinaimg.cn/large/005VT09Qly1gfa8s8hx0qj30lp040t8t.jpg"/>

#### 定时晚安

每晚12点，可以从指定的文件里随机抽取一行向qq群发送晚安。

<img src="http://ww1.sinaimg.cn/large/005VT09Qly1gfa8t1l9j5j30k20powg8.jpg"/>

<img src="http://ww1.sinaimg.cn/large/005VT09Qly1gfa8vix9lxj30e402f0sm.jpg"/>



#### 记录清醒时长

回复“zao”或“wan”可以触发功能。

<img src="http://ww1.sinaimg.cn/large/005VT09Qly1gga0x84659j30js04raa1.jpg"/>

<img src="http://ww1.sinaimg.cn/large/005VT09Qly1gga0wtd3u1j30li04zglm.jpg"/>

#### 疫情数据查询

回复关键字 “疫情数据 area”可以触发功能，栗子：<img src="http://ww1.sinaimg.cn/large/005VT09Qly1gga11ttyf0j30ut04baab.jpg"/>

#### 复读机

回复关键字“进入复读模式”、“关闭复读模式”可以触发功能。

Arina会重复你说的话。

#### Arina的个人页面

<img src="http://ww1.sinaimg.cn/large/005VT09Qly1gga146vip3j31hb0g23z2.jpg"/>

## 需要的技能或工具：

- 服务器一个（如果你想让robot 24小时在线）
- QQ小号一个
- 精通java（的拼写）
- 对Http有所了解
- 会使用java向服务器指定的端口发送GET请求
- 会使用java对文件进行读取操作
- ~~会使用java创建线程（定时推送时所需）~~
- 掌握linux常用命令若干（可以随时百度）
- 会使用maven进行依赖管理和打包部署
- 会JSON和JavaBean的相互转换（调天气查询api以及疫情数据api时所需）
- 会使用Spring Boot编写web服务器（接受cqhttp插件的post请求以及对robot页面的get请求）
- 会使用html+css+JavaScript+jquery+bootstrap（写页面时所需）
- 会使用一种模板引擎（比如ThymeLeaf，前后端交互所需）
- 图灵机器人（可以去官网免费领取）
- <u>不抛弃、不放弃（坑实在太多了）</u>

## 本项目基于：

- 酷Q
- 酷Q插件：CQHTTP（酷Q本身使用易语言编写的，这就对不熟悉易语言的人造成很大障碍，这个插件通过 HTTP 对 酷Q 的事件进行上报以及接收 HTTP 请求来调用 酷Q 的 DLL 接口，从而可以使用其它语言编写 酷Q 插件。现已支持 WebSocket。不得不说，这是个绝妙的注意。
- java
- Spring Boot

## 本fw使用的开发平台：

全宇宙第一的Intellij IDEA 2020

~~还有好多作业没做，等我做完在更（逃~~

## 酷q和cqhttp介绍

### 酷q

酷q是类似qq客户端的一个软件，提供了许多基本的功能（发送接收消息，群管理等）。酷q本身提供了一些功能（自动回复，图灵机器人插件），但这些功能仍然比较匮乏，想要增加新的功能，就需要编写酷q插件。但是编写酷q插件需要用到易语言，因此像我这种不会易语言的就变得束手无策。

### cqhttp

cqhttp是酷q的一个插件，这个插件通过 HTTP 对 酷Q 的事件进行上报以及接收 HTTP 请求来调用 酷Q 的 DLL 接口，从而可以使用其它语言编写 酷Q 插件。于是，我就可以用java来实现我想要的功能了。

## 购买服务器

Arina需要在一个24小时不关机的电脑上运行，如果愿意自己的电脑永远不关机，也可以不需要服务器。

服务器可以上阿里云买最便宜的，能用就行。

可以不用域名（域名备案很麻烦），直接通过ip地址访问服务器。

## 服务器开放端口

比如我的NoVNC运行在服务器的9000端口下，我需要修改安全组策略，对9000端口“放行”。<img src="http://ww1.sinaimg.cn/large/005VT09Qly1gga1tchswdj31hc0ssmxz.jpg"/>

如何修改安全组策略取决于你的服务器供应商，请自行百度。

## 酷q在linux环境下的运行

酷q在windows下可以简单的安装运行，但在linux下需要通过wine借助docker来运行。请参考https://github.com/CoolQ/docker-wine-coolq。

## 获取并配置图灵机器人api key

上官网http://www.turingapi.com/注册领取。

获取到之后手动添加到酷q的图灵机器人插件里：

<img src="http://ww1.sinaimg.cn/large/005VT09Qly1gga1xl84ytj31hc0swmza.jpg"/>

图灵机器人本身有丰富的功能，免费版的api调用次数有限制。到此为止，robot就像模像样了。根据我的体验，这个图灵机器人并不是很智能，有时候像个智障，远远不及小爱同学或者cortana，但毕竟是免费的。

## 核心：cqhttp插件的使用（java）

### 安装

使用cqhttp，可以手动给酷q安装插件，也可以用作者预先配置好的镜像直接运行docker。这部分内容请参考cqhttp的官方文档：https://cqhttp.cc/docs/4.15/#/。

### 发送qq消息

cqhttp插件可以监听指定端口的事件，我们需要根据想要发送的内容拼接url，向服务器的指定端口发送get请求。

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class Utils {
    private static final Logger log = LoggerFactory.getLogger(Utils.class);
    private static final HttpClient httpClient = HttpClient.newBuilder().build();

    public static void sendGroupMessage(String message) throws URISyntaxException, IOException, InterruptedException {
        log.info("正准备构造HttpRequest");
        String url = String.format("http://47.98.252.1:5700/send_group_msg?group_id=549594617&message=%s", message.replace(" ", "%20"));// main:549594617 测试群：851736129
        log.info("构造好的url：{}", url);
        HttpRequest httpRequest = HttpRequest.newBuilder(new URI(url))
                .header("User-Agent", "Java HttpClient")
                .header("Accept", "*/*")
                .timeout(Duration.ofSeconds(5))
                .version(HttpClient.Version.HTTP_2)
                .build();


        log.info("正准备用Http Client向服务器发送get请求");
        httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
    }
}
```

url中，group_id参数是群号或者qq号，message参数是想要发送的内容。上面是用java编写的构造url并利用httpClient向服务器的5700端口发送get请求的代码。

注意服务器的安全组策略，需要开放指定的端口才行（我这儿是5700）。

### 禁言群用户

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class Utils {
    private static final Logger log = LoggerFactory.getLogger(Utils.class);
    private static final HttpClient httpClient = HttpClient.newBuilder().build();
    
    public static void setGroupBan(long userId, int duration) throws URISyntaxException, IOException, InterruptedException {
        log.info("正在准备构造HttpRequest");
        String url = String.format("http://47.98.252.1:5700/set_group_ban?group_id=549594617&user_id=%d&duration=%d", userId, duration);
        HttpRequest httpRequest = HttpRequest.newBuilder(new URI(url))
                .header("User-Agent", "Java HttpClient")
                .header("Accept", "*/*")
                .timeout(Duration.ofSeconds(5))
                .version(HttpClient.Version.HTTP_2)
                .build();

        log.info("正准备用Http Client向服务器发送get请求");
        httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
    }
}
```

这其实和发送qq消息的过程一样，只是url的参数有所不同。需要设置群号，qq号和禁言的时间。

### 定时发送消息

起初我的实现思路是新创建一个线程，不停地判断当前的时间，等到指定的时间再执行相关的代码。

后来我遇到一些我无法解决问题，把整个项目用spring重新组织了一遍。

用了spring之后，事情就变得简单起来了，定时发送消息可以通过spring的注解`@Scheduled(fixedRate = 1000)`实现。

栗子：

```java
@Scheduled(fixedRate = 1000)
public void resetMapAndIndex() {
    LocalDateTime now = LocalDateTime.now();
    if (now.getHour() == 3 && now.getMinute() == 0 && now.getSecond() == 0) {
        log.info("凌晨三点整，正在重置map和index");
        map.clear();
        index = 0;
    }
}
```

这个方法每隔一秒执行一次，判断时间再执行相应的操作就可以达成目的。

### 定时晚安功能

如果是每天都不变的一句话，未免过于单调了。可以从以下文本中随机抽取一行作为今晚要发送的消息。

```txt
要想身体好，睡觉不蒙脑。
早睡早起，没病惹你。
早睡早起，清爽欢喜;迟睡迟起，强拉眼皮。
早睡早起，赛过人参补身体。
早睡早起拥有健康生活。
睡眠好身体才好。
睡前洗脚，胜吃补药。
晚餐少喝水，睡前不饮茶。
晚上开窗，一夜都香。
开启心灵之窗，共同关注睡眠。
科学管理睡眠。
瞌睡没根，越睡越深。
良好睡眠、健康人生。
培养良好睡眠习惯，保持健康生活方式。
食不多言，寝不多语。
适当吃睡，无病增岁。
科学享睡，健康无病。
不觅仙方觅睡方。
吃罢中饭睡一觉，健健康康活到老。
吃洋参，不如睡五更。
坐有坐相，睡有睡相，睡觉要像弯月亮。
坐有坐相，睡有睡相，睡觉要像弯月亮。
科学享睡，健康无病。
不觅仙方觅睡方。
吃罢中饭睡一觉，健健康康活到老。
吃洋参，不如睡五更。
吃药十付，不如独宿一夜。
充足睡眠，身心健康。
关注睡眠障碍，提倡科学睡眠。
合理安排生活，拥有健康睡眠。
健康生活，良好睡眠。
今天的沉睡成就明天的活力。
开启心灵之窗，共同关注睡眠。
科学管理睡眠。
瞌睡没根，越睡越深。
良好睡眠、健康人生。
培养良好睡眠习惯，保持健康生活方式。
食不多言，寝不多语。
适当吃睡，无病增岁。
适度少睡眠，可以保长寿。
睡出健康来。
睡觉不蒙头，活到九十九。
睡眠，健康的选择。
```

思路是生成一个指定范围内的随机数作为行数，读取指定行的内容并定时发送。

```java
package eternal.fire.springbootrobot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Component
public class GoodNight {
    private static final Logger log = LoggerFactory.getLogger(GoodNight.class);
    private static final HttpClient httpClient = HttpClient.newBuilder().build();

    public String getWords() throws IOException {
        log.info("正在尝试从文本文件里随机抽取一行作为晚安语录");
        InputStream inputStream = getClass().getResourceAsStream("/goodNight.txt");
        Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(reader);
        //文件有43行
        int line = ((int) (Math.random() * 100)) % 43 + 1;
        String ans = "";
        for (int i = 0; i < line; i++) {
            ans = bufferedReader.readLine();
        }
        return ans + "大家晚安＜(▰˘◡˘▰)";
    }

    public void broadcastGoodNight() throws IOException, InterruptedException, URISyntaxException {
        String words = getWords();
        Utils.sendGroupMessage(words);
    }

    @Scheduled(fixedRate = 1000)
    public void broadcast() throws IOException, InterruptedException, URISyntaxException {
        LocalDateTime now = LocalDateTime.now();
        if (now.getHour() == 0 && now.getMinute() == 0 && now.getSecond() == 0) {
            log.info("晚上24点整，正准备播报晚安");
            broadcastGoodNight();
        }
    }
}
```

### 定时天气播报

首先需要一个高德地图查询天气的api，可以去官网免费申请。向这个api发送get请求可以得到想要的天气数据。

返回的天气数据是json模样的字符串，我们需要把字符串转为java bean，这样操作起来更方便，因此需要准备一个接受数据的java bean：Weather。我这里java bean和定时发送的代码写在了一起。

得到java bean之后，就可以定时发送天气情况了。

```java
package eternal.fire.springbootrobot.weather;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import eternal.fire.springbootrobot.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class Weather {
    public String status;
    public String count;
    public String info;
    public String infocode;
    public List<Live> lives;
    private static final Logger log = LoggerFactory.getLogger(Weather.class);
    private static final HttpClient httpClient = HttpClient.newBuilder().build();

    public String generateString(HttpClient httpClient) throws IOException, InterruptedException, URISyntaxException {
        log.info("正在通过高德api获取西峰区天气状况，结果将以字符串形式返回给调用函数");
        log.info("构造HttpRequest");
        String url = "https://restapi.amap.com/v3/weather/weatherInfo?key=b74ab54158c425e0ddbe4db7dce8624b&city=621002";
        HttpRequest httpRequest = HttpRequest.newBuilder(new URI(url))
                .header("User-Agent", "Java HttpClient")
                .header("Accept", "*/*")
                .timeout(Duration.ofSeconds(5))
                .version(HttpClient.Version.HTTP_2)
                .build();


        log.info("正在通过http client发送构造好的HttpRequest，并获得天气查询结果");
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());


        log.info("正在将查询到的天气json转化为Java Bean");
        String json = httpResponse.body();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Weather weather = mapper.readValue(json, Weather.class);


        log.info("正在准备拼接字符串");
        Live live = weather.lives.get(0);
        String result = "今日天气预报来啦~++++播报时间:" + live.reporttime + "++++省份：" + live.province + "++++城市：" + live.city + "++++天气：" + live.weather
                + "++++温度：" + live.temperature
                + "++++风向：" + live.winddirection + "++++风力：" + live.windpower + "++++湿度：" + live.humidity;
        result = result.replaceAll(" ", "+");
        log.info("拼接完毕，结果：{}", result);
        return result;
    }

    public void broadcastWeather() throws InterruptedException, IOException, URISyntaxException {
        log.info("正准备向群里播报天气");
        log.info("获得天气状况（字符串）");
        String result = generateString(httpClient);
        Utils.sendGroupMessage(result);
        log.info("播报完毕");
    }

    @Scheduled(fixedRate = 1000)
    public void broadcast() throws InterruptedException, IOException, URISyntaxException {
        LocalDateTime now = LocalDateTime.now();
        if (now.getHour() == 8 && now.getMinute() == 0 && now.getSecond() == 0) {
            log.info("早上八点整，正准备播报天气");
            broadcastWeather();
        }
    }
}
```

### 疫情数据查询

疫情数据查询时由用户主动发起的事件，所以我们需要知道用户发送了什么。cqhttp插件会将所有的事件上报到指定的端口，我们可以通过监听端口来获得事件的内容。

之前我用socket监听cqhttp的事件，但是bug很多，有一些是我百思不得其解的，后来觉得用spring 是个好主意，用spring之后就没什么问题了。

具体做法是编写一个Controller，监听根路径的所有post事件（`@PostMapping("/")`），并作出回应。

首先我们需要从用户发送的消息中判断所要查询的地区。

然后同样我们也需要一个查询疫情数据的api（我这儿使用的是https://lab.isaaclin.cn/nCoV/zh）。

准备一个java bean用来接受存储数据。

```java
package eternal.fire.springbootrobot.covid19;

import java.util.List;

public class CovidData {
    public List<Result> results;
}
```

```java
package eternal.fire.springbootrobot.covid19;

public class Result {
    public String countryName;
    public String countryEnglishName;
    public int currentConfirmedCount;
    public int confirmedCount;
    public int curedCount;
    public int deadCount;

    @Override
    public String toString() {
        return String.format("国家名称：%s，国家名称（英文）：%s，现存确诊人数：%d，累计确诊人数：%d，治愈人数：%d，死亡人数：%d",
                countryName, countryEnglishName, currentConfirmedCount, confirmedCount, curedCount, deadCount);
    }
}
```

最后将json转为java bean并回复消息（这里利用了cqhttp的快速回复功能，跟之前的发送方式有所不同）。

```java
public void getCovidData(HttpServletResponse response, Post post) throws URISyntaxException, IOException, InterruptedException {
    log.info("正在尝试获取地区名称");
    String province = post.getMessage().substring(4).strip();


    log.info("正在构造HttpRequest");
    String url = String.format("https://lab.isaaclin.cn/nCoV/api/area?latest=1&province=%s", province);
    HttpRequest httpRequest = HttpRequest.newBuilder(new URI(url))
            .header("User-Agent", "Java HttpClient")
            .header("Accept", "*/*")
            .timeout(Duration.ofSeconds(5))
            .version(HttpClient.Version.HTTP_2)
            .build();

    log.info("正在用http client调用疫情数据查询api");
    HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());


    log.info("正在将json转为Java Bean");
    String json = httpResponse.body();
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    CovidData covidData = objectMapper.readValue(json, CovidData.class);


    log.info("正在发送消息");
    if (covidData.results.size() == 0) {
        reply(response, "unknown area");
    } else {
        String content = covidData.results.toString();
        reply(response, content);
    }
}
```

## 参考资料

Arina：http://47.98.252.1:7000/arina

Arina的github（可以看我的源代码）：https://github.com/carpediemtal/Robot

cqhttp官方文档：https://cqhttp.cc/docs/4.15/#/

通过wine在docker中运行酷q：https://github.com/CoolQ/docker-wine-coolq

图灵机器人：http://www.turingapi.com/

