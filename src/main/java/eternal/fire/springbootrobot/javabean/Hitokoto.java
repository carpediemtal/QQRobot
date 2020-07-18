package eternal.fire.springbootrobot.javabean;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class Hitokoto {
    private static final Logger log = LoggerFactory.getLogger(Hitokoto.class);
    private static final HttpClient httpClient = HttpClient.newBuilder().build();
    private String hitokoto;
    private String from;

    public static String getContent() throws IOException, InterruptedException, URISyntaxException {
        log.info("正在构造HttpRequest");
        String url = "https://v1.hitokoto.cn/";
        HttpRequest httpRequest = HttpRequest.newBuilder(new URI(url))
                .header("User-Agent", "Java HttpClient")
                .header("Accept", "*/*")
                .timeout(Duration.ofSeconds(5))
                .version(HttpClient.Version.HTTP_2)
                .build();

        log.info("正在用http client调用hitokoto的api");
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        log.info("正在将json转为java bean");
        String json = httpResponse.body();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Hitokoto content = mapper.readValue(json, Hitokoto.class);

        return content.toString();
    }

    public String getHitokoto() {
        return hitokoto;
    }

    public void setHitokoto(String hitokoto) {
        this.hitokoto = hitokoto;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public String toString() {
        return String.format("%s ————%s", hitokoto, from);
    }
}
