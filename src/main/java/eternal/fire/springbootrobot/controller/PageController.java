package eternal.fire.springbootrobot.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import eternal.fire.springbootrobot.Utils;
import eternal.fire.springbootrobot.javabean.InfoToBan;
import eternal.fire.springbootrobot.javabean.Message;
import eternal.fire.springbootrobot.javabean.ValidateInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

@Controller
@CrossOrigin("*")
public class PageController {
    private static final Logger log = LoggerFactory.getLogger(PageController.class);


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
        Utils.sendGroupMessage(message.getContent(), message.getGroupId());
        return "sendMessage";
    }

    @GetMapping("/ban")
    public String showBanHtml(Model model) {
        log.info("侦测到对/ban的get请求，我必须做出回应");
        model.addAttribute("information", new InfoToBan());
        return "ban";
    }

    @PostMapping("/ban")
    public String ban(@ModelAttribute("information") InfoToBan information) throws InterruptedException, IOException, URISyntaxException {
        log.info("侦测到ban的请求，我必须做出回应");
        log.info("要ban的人是{}，时长{}", information.getUserId(), information.getDuration());
        Utils.setGroupBan(information.getUserId(), information.getDuration(), Long.toString(information.getGroupId()));
        log.info("ban完毕");
        return "ban";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public void validate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("侦测到login页面的post请求，我必须回应");
        log.info("正在从数据库中获取账号密码");
        String username = "overwatch";
        String password = "tracer";

        log.info("将post内容json转化为java bean");
        ValidateInfo info = getValidateInfo(request);

        log.info("正在初始化回复内容");
        String successfulMessage = "{\"status\": \"ok\"}";
        String failedMessage = "{\"status\": \"no\"}";

        log.info("正在回复");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        if (info.getUsername().equals(username) && info.getPassword().equals(password)) {
            printWriter.write(successfulMessage);
        } else {
            printWriter.write(failedMessage);
        }
        printWriter.flush();
        log.info("回复完毕");
    }

    public ValidateInfo getValidateInfo(HttpServletRequest request) throws IOException {
        log.info("正在尝试获取ValidatePost的内容");
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder responseContent = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            responseContent.append(line);
        }
        log.info("获取完毕，ValidatePost的内容：\n{}", responseContent.toString());


        log.info("正在尝试将JSON转化为Java Bean");
        String json = responseContent.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.readValue(json, ValidateInfo.class);
    }
}
