package eternal.fire.springbootrobot;

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
