package cf.huzpsb.bib;

import java.util.concurrent.ConcurrentHashMap;

public class OpenLatex implements Runnable {

    private static final String core = "\"This is a Latex file.\\n\\nmain.tex\\n````TeX\\n\\\\documentclass[UTF8]{ctexart}\\n\\\\pagestyle{plain}\\n\\\\begin{document}\\n    \\\\begin{center}\\n        \\\\textbf{\\\\huge %TITLE%}\\n    \\\\end{center}\\n    \\\\section{\\n    [THIS IS THE BLANK]\\n    \\\\begingroup\\n    \\\\bibliography{main}\\n    \\\\bibliographystyle{plain}\\n    \\\\endgroup\\n\\\\end{document}\\n````\\n\\nNow fill in the blank of the main.tex with ~1000 Chinese characters. \\nGive the complete document, including the provided items. \\nAdd \\\\cite{name0} \\\\cite{name1}, etc. at the end of each sentence.\\nThere are 20 cite entries available. They are named, name0, name1, ..., name19.\\nUse no less than 10 of them, and no more than 20 of them.\\nUse them in the numercial order. Say, only use name3 if you've already used name2.\\nSpread the cites at the end of each sentence. Do not place them all at the end of the paragraph.\\nFor example, \\\"On the one hand, xxxx\\\\cite{name0}, on the other hand, xxxx\\\\cite{name1}. Thus, xxxx\\\\cite{name2}\\\" is good, and \\\"On the one hand, xxxx, on the other hand, xxxx. Thus, xxxx \\\\cite{name0} \\\\cite{name1} \\\\cite{name2}\\\" is bad.\\nUse one cite item no more than once.\\n\\nBegin your answer with \\\"\\\\documentclass[UTF8]{ctexart}\\\" and end it with \\\"\\\\end{document}\\\".\"";
    private static final ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
    public String title = null;

    public static String gen(String title) {
        if (map.containsKey(title)) {
            return map.get(title);
        }
        map.put(title, "QUEUE_WORKING");
        OpenLatex r = new OpenLatex();
        r.title = title;
        new Thread(r).start();
        return "QUEUE_START";
    }

    @Override
    public void run() {
        map.put(title, OpenAI.gpt(core.replace("%TITLE%", title)).replace("bibliographystyle{plain}", "bibliographystyle{unsrt}"));
    }
}
