package eternal.fire.springbootrobot;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import eternal.fire.springbootrobot.covid19.CovidData;
import eternal.fire.springbootrobot.javabean.InfoToBan;
import eternal.fire.springbootrobot.javabean.Message;
import eternal.fire.springbootrobot.javabean.Post;
import eternal.fire.springbootrobot.javabean.Reply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MyController {
    private static final Logger log = LoggerFactory.getLogger(MyController.class);
    private static final HttpClient httpClient = HttpClient.newBuilder().build();
    private final Map<Long, LocalDateTime> map = new HashMap<>();
    private int index = 0;
    private static boolean repeat = false;

    @PostMapping("/")
    public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException, URISyntaxException, InterruptedException {
        log.info("正在获取侦测到的上报信息");
        Post post = getPost(request);
        if (repeat) {
            log.info("进入复读模式");
            log.info("开始复读");
            Utils.sendGroupMessage(post.getMessage());

            if (post.getMessage().equals("退出复读模式")) {
                log.info("退出复读模式");
                repeat = false;
            }
        } else {
            log.info("进入一般模式");
            if (post.getMessage().equals("zao") || post.getMessage().equals("wan")) {
                log.info("早晚安签到环节");
                goodMorningAndNight(response, post);
            } else if (post.getMessage().startsWith("疫情数据")) {
                log.info("获取疫情数据");
                getCovidData(response, post);
            } else if (post.getMessage().equals("进入复读模式")) {
                repeat = true;
            }
        }
    }

    @GetMapping("/arina")
    public String index() {
        return "arina";
    }

    @GetMapping("/message")
    public String showMessageHtml(Model model) {
        log.info("侦测到对/message的get请求，即将处理此请求");
        model.addAttribute("message", new Message());
        return "sendMessage";
    }

    @PostMapping("/message")
    public String sendMessage(@ModelAttribute Message message) throws InterruptedException, IOException, URISyntaxException {
        log.info("成功获取到了要发送的message，即将发送");
        Utils.sendGroupMessage(message.getContent());
        return "sendMessage";
    }

    @GetMapping("/ban")
    public String showBanHtml(Model model) {
        log.info("侦测到对/ban的get请求，我必须做出回应");
        model.addAttribute("information", new InfoToBan(502795405));
        return "ban";
    }

    @PostMapping("/ban")
    public String ban(@ModelAttribute("information") InfoToBan information) throws InterruptedException, IOException, URISyntaxException {
        log.info("侦测到ban的请求，我必须做出回应");
        log.info("要ban的人是{}，时长{}", information.getUserId(), information.getDuration());
        Utils.setGroupBan(information.getUserId(), information.getDuration());
        log.info("ban完毕");
        return "ban";
    }


    public Post getPost(HttpServletRequest request) throws IOException {
        log.info("正在尝试获取Post的内容");
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder responseContent = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            responseContent.append(line);
        }
        log.info("获取完毕，Post的内容：\n{}", responseContent.toString());


        log.info("正在尝试将JSON转化为Java Bean");
        String json = responseContent.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.readValue(json, Post.class);
    }

    public void reply(HttpServletResponse response, String message) throws IOException {
        log.info("正在尝试做出回应");
        Reply reply = new Reply(message);
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.write(reply.toString());
        printWriter.flush();
        log.info("回应结束，回应的内容是：\n{}", reply.toString());
    }

    // 早晚安签到计时
    public void goodMorningAndNight(HttpServletResponse response, Post post) throws IOException {
        if (post.getMessage().equals("zao")) {
            if (map.containsKey(post.getUser_id())) {
                reply(response, "Pia!<(=ｏ‵-′)ノ☆  你不是起过床了吗?");
            } else {
                map.put(post.getUser_id(), LocalDateTime.now());
                index++;
                if (index == 1) {
                    reply(response, String.format("你是第%d起床的好少年，获得成就：最早起床√", index));
                } else {
                    reply(response, String.format("你是第%d起床的好少年", index));
                }
            }
        } else {
            if (map.containsKey(post.getUser_id())) {
                Duration duration = Duration.between(map.get(post.getUser_id()), LocalDateTime.now()).abs();
                String message = "今日共清醒：" + duration.toHoursPart() + "时" + duration.toMinutesPart() + "分钟" + duration.toSecondsPart() + "秒，辛苦了(￣o￣) . z Z";
                map.remove(post.getUser_id());
                reply(response, message);
            } else {
                reply(response, "Pia!<(=ｏ ‵-′)ノ☆ 不起床就睡，睡死你好了");
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
}
