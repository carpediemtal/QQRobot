package eternal.fire.springbootrobot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Component
@PropertySource("groupInfo.properties")
public class GoodNight {
    private static final Logger log = LoggerFactory.getLogger(GoodNight.class);
    private static final HttpClient httpClient = HttpClient.newBuilder().build();
    @Value("${info.groupId}")
    private String groupId;

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
        Utils.sendGroupMessage(words, groupId);
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

