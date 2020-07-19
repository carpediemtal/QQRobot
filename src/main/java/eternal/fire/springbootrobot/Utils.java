package eternal.fire.springbootrobot;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import eternal.fire.springbootrobot.javabean.CqHttpPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class Utils {
    private static final Logger log = LoggerFactory.getLogger(Utils.class);
    private static final HttpClient httpClient = HttpClient.newBuilder().build();

    public static void sendGroupMessage(String message, String groupId) throws URISyntaxException, IOException, InterruptedException {
        log.info("正准备构造HttpRequest");
        String url = String.format("http://47.98.252.1:5700/send_group_msg?group_id=%s&message=%s", groupId, message.replace(" ", "%20").replace("\n", "%0a"));
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

    public static void setGroupBan(long userId, int duration, String groupId) throws URISyntaxException, IOException, InterruptedException {
        log.info("正在准备构造HttpRequest");
        String url = String.format("http://47.98.252.1:5700/set_group_ban?group_id=%s&user_id=%d&duration=%d", groupId, userId, duration);
        HttpRequest httpRequest = HttpRequest.newBuilder(new URI(url))
                .header("User-Agent", "Java HttpClient")
                .header("Accept", "*/*")
                .timeout(Duration.ofSeconds(5))
                .version(HttpClient.Version.HTTP_2)
                .build();

        log.info("正准备用Http Client向服务器发送get请求");
        httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
    }


    public static CqHttpPost getPost(HttpServletRequest request) throws IOException {
        log.info("正在尝试获取CqHttpPost的内容");
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder responseContent = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            responseContent.append(line);
        }
        log.info("获取完毕，CqHttpPost的内容：\n{}", responseContent.toString());


        log.info("正在尝试将JSON转化为Java Bean");
        String json = responseContent.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.readValue(json, CqHttpPost.class);
    }
}
