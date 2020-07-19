package eternal.fire.springbootrobot.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import eternal.fire.springbootrobot.RainbowFart;
import eternal.fire.springbootrobot.Utils;
import eternal.fire.springbootrobot.covid19.CovidData;
import eternal.fire.springbootrobot.javabean.CqHttpPost;
import eternal.fire.springbootrobot.javabean.CqHttpReply;
import eternal.fire.springbootrobot.javabean.Hitokoto;
import eternal.fire.springbootrobot.javabean.PoisonousChickenSoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
@CrossOrigin("*")
public class MainController {
    private static final Logger log = LoggerFactory.getLogger(MainController.class);
    private static final HttpClient httpClient = HttpClient.newBuilder().build();
    private static boolean isRepeat = false;
    private final Map<Long, LocalDateTime> map = new HashMap<>();
    @Value("${info.groupId}")
    private String groupId;
    private int index = 0;

    @PostMapping("/")
    public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException, URISyntaxException, InterruptedException {

        log.info("正在获取侦测到的上报信息");
        CqHttpPost post = Utils.getPost(request);
        if (isRepeat) {
            repeat(post.getMessage());
        } else {
            log.info("进入一般模式");
            if (post.getMessage().equals("zao") || post.getMessage().equals("wan")) {
                log.info("早晚安签到环节");
                goodMorningAndNight(response, post);
            } else if (post.getMessage().startsWith("疫情数据")) {
                log.info("获取疫情数据");
                getCovidData(response, post);
            } else if (post.getMessage().equals("进入复读模式")) {
                log.info("修改isRepeat的值");
                isRepeat = true;
            } else if (post.getMessage().equals("夸我")) {
                log.info("开始彩虹屁");
                replyCqHttp(response, RainbowFart.getRainbowFart());
            } else if (post.getMessage().equals("一言")) {
                log.info("开始一言");
                replyCqHttp(response, Hitokoto.getContent());
            } else if (post.getMessage().endsWith("毒鸡汤")) {
                log.info("开始毒鸡汤");
                replyCqHttp(response, PoisonousChickenSoup.getSoup());
            }
        }
    }

    private void repeat(String message) throws InterruptedException, IOException, URISyntaxException {
        log.info("进入复读模式");
        log.info("开始复读");
        Utils.sendGroupMessage(message, groupId);

        if (message.equals("退出复读模式")) {
            log.info("退出复读模式");
            isRepeat = false;
        }
    }


    public void replyCqHttp(HttpServletResponse response, String message) throws IOException {
        log.info("正在尝试做出回应");
        CqHttpReply reply = new CqHttpReply(message);
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.write(reply.toString());
        printWriter.flush();
        log.info("回应结束，回应的内容是：\n{}", reply.toString());
    }

    // 早晚安签到计时
    public void goodMorningAndNight(HttpServletResponse response, CqHttpPost post) throws IOException {
        if (post.getMessage().equals("zao")) {
            if (map.containsKey(post.getUser_id())) {
                replyCqHttp(response, "Pia!<(=ｏ‵-′)ノ☆  你不是起过床了吗?");
            } else {
                map.put(post.getUser_id(), LocalDateTime.now());
                index++;
                if (index == 1) {
                    replyCqHttp(response, String.format("你是第%d起床的好少年，获得成就：最早起床√", index));
                } else {
                    replyCqHttp(response, String.format("你是第%d起床的好少年", index));
                }
            }
        } else {
            if (map.containsKey(post.getUser_id())) {
                Duration duration = Duration.between(map.get(post.getUser_id()), LocalDateTime.now()).abs();
                String message = "今日共清醒：" + duration.toHoursPart() + "时" + duration.toMinutesPart() + "分钟" + duration.toSecondsPart() + "秒，辛苦了(￣o￣) . z Z";
                map.remove(post.getUser_id());
                replyCqHttp(response, message);
            } else {
                replyCqHttp(response, "Pia!<(=ｏ ‵-′)ノ☆ 不起床就睡，睡死你好了");
            }
        }
        log.info("早晚安签到完毕");
    }

    @Scheduled(fixedRate = 1000)
    public void resetMapAndIndex() {
        LocalDateTime now = LocalDateTime.now();
        if (now.getHour() == 3 && now.getMinute() == 0 && now.getSecond() == 0) {
            log.info("凌晨三点整，正在重置map和index");
            map.clear();
            index = 0;
        }
    }

    public void getCovidData(HttpServletResponse response, CqHttpPost post) throws URISyntaxException, IOException, InterruptedException {
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
            replyCqHttp(response, "unknown area");
        } else {
            String content = covidData.results.toString();
            replyCqHttp(response, content);
        }
    }
}
