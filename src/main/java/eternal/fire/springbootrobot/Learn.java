package eternal.fire.springbootrobot;

import eternal.fire.springbootrobot.controller.MainController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Learn {
    public static final Map<String, String> map = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(Learn.class);

    public static int learn(String message) {
        String[] args = message.split(" ");
        log.info("获取到的内容是{}", Arrays.toString(args));
        if (args.length < 3) {
            return -1;
        }
        map.put(args[1], args[2]);
        return 0;
    }
}
