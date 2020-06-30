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
