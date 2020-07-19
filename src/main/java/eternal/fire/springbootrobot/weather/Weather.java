package eternal.fire.springbootrobot.weather;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import eternal.fire.springbootrobot.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
    private static final Logger log = LoggerFactory.getLogger(Weather.class);
    private static final HttpClient httpClient = HttpClient.newBuilder().build();
    private String status;
    private String count;
    private String info;
    private String infocode;
    private List<Forecasts> forecasts;
    @Value("${info.groupId}")
    private String groupId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfocode() {
        return infocode;
    }

    public void setInfocode(String infocode) {
        this.infocode = infocode;
    }

    public List<Forecasts> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<Forecasts> forecasts) {
        this.forecasts = forecasts;
    }

    public String generateString(HttpClient httpClient) throws IOException, InterruptedException, URISyntaxException {
        log.info("正在通过高德api获取西峰区天气状况，结果将以字符串形式返回给调用函数");
        log.info("构造HttpRequest");
        String url = "https://restapi.amap.com/v3/weather/weatherInfo?key=b74ab54158c425e0ddbe4db7dce8624b&city=621002&extensions=all";
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
        Forecasts forecasts = weather.forecasts.get(0);
        Casts today = forecasts.getCasts().get(0);
        Casts tomorrow = forecasts.getCasts().get(1);
        String result = String.format("天气预报来啦~\n" +
                        "城市：%s\n" +
                        "播报时间：%s\n" +
                        "今日~\n" +
                        "日期：%s\n" +
                        "白天天气：%s\n" +
                        "夜间天气：%s\n" +
                        "白天温度：%s\n" +
                        "夜间温度：%s\n" +
                        "白天风向：%s\n" +
                        "夜间风向：%s\n" +
                        "风力：%s\n" +
                        "\n" +
                        "明日~\n" +
                        "日期：%s\n" +
                        "白天天气：%s\n" +
                        "夜间天气：%s\n" +
                        "白天温度：%s\n" +
                        "夜间温度：%s\n" +
                        "白天风向：%s\n" +
                        "夜间风向：%s\n" +
                        "风力：%s",
                forecasts.getProvince() + forecasts.getCity(), forecasts.getReporttime().toString(),
                today.getDate(), today.getDayweather(), today.getNightweather(), today.getDaytemp(), today.getNighttemp(), today.getDaywind(), today.getNightwind(), today.getDaypower(),
                tomorrow.getDate(), tomorrow.getDayweather(), tomorrow.getNightweather(), tomorrow.getDaytemp(), tomorrow.getNighttemp(), tomorrow.getDaywind(), tomorrow.getNightwind(), tomorrow.getDaypower());

        log.info("拼接完毕，结果：{}", result);
        return result;
    }

    public void broadcastWeather() throws InterruptedException, IOException, URISyntaxException {
        log.info("正准备向群里播报天气");
        log.info("获得天气状况（字符串）");
        String result = generateString(httpClient);
        Utils.sendGroupMessage(result, groupId);
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
