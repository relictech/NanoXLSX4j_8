package cf.huzpsb.bib;

import java.util.concurrent.ConcurrentHashMap;

public class OpenAms implements Runnable {
    public static final String ams = "Write a formula in amsmath(Latex) meeting the following requirements: \"%TITLE%\". Begin your response with \"\\begin{align*}\" and end your response with \"\\end{align*}\".";
    public static final ConcurrentHashMap<String, String> amsMap = new ConcurrentHashMap<>();
    public String amsTitle = null;

    // 获取ams对应的task id。如果任务不存在，那么会创建一个新的任务。
    public static String genAmsId(String title) {
        String ans = OpenAms.hashAms(title);
        if (!amsMap.containsKey(ans)) {
            amsMap.put(ans, "queue");
            // 避免重复创建线程
            OpenAms r = new OpenAms();
            r.amsTitle = title;
            new Thread(r).start();
        }
        return ans;
    }

    public static String getAms(String id) {
        return amsMap.get(id);
    }

    public static String hashAms(String title) {
        int code = title.hashCode();
        String ans = code < 0 ? "n" : "p";
        code = code < 0 ? -code : code;
        code = 1000000 + code % 8999999;
        return ans + code;
    }

    @Override
    public void run() {
        String ans = OpenAI.gpt(ams.replace("%TITLE%", amsTitle));
        amsMap.put(hashAms(amsTitle), ans);
    }
}
