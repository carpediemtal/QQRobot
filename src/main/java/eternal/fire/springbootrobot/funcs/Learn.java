package eternal.fire.springbootrobot.funcs;

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

    public static int forget(String key) {
        String[] args= key.split(" ");
        log.info("要忘记的内容是{}",args[1]);
        if (args.length < 2) {
            log.info("遗忘异常");
            return -1;
        }
        map.remove(args[1]);
        return 0;
    }
}
