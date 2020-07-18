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

public class RainbowFart {
    private static final Logger log = LoggerFactory.getLogger(RainbowFart.class);
    private static final HttpClient httpClient = HttpClient.newBuilder().build();

    public static String getRainbowFart() throws URISyntaxException, IOException, InterruptedException {
        log.info("正在构造HttpRequest");
        String url = "https://chp.shadiao.app/api.php";
        HttpRequest httpRequest = HttpRequest.newBuilder(new URI(url))
                .header("User-Agent", "Java HttpClient")
                .header("Accept", "*/*")
                .timeout(Duration.ofSeconds(5))
                .version(HttpClient.Version.HTTP_2)
                .build();

        log.info("正在用http client调用彩虹屁api");
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        return httpResponse.body();
    }
}
