package cf.huzpsb.bib;

import nano.http.bukkit.api.BukkitServerProvider;
import nano.http.d2.console.Logger;
import nano.http.d2.consts.Mime;
import nano.http.d2.consts.Status;
import nano.http.d2.core.Response;
import nano.http.d2.session.Captcha;
import nano.http.d2.session.Session;
import nano.http.d2.session.SessionManager;
import nano.http.d2.utils.Misc;

import java.io.File;
import java.util.Properties;

public class Main extends BukkitServerProvider {
    public static final String key = "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";

    @Override
    public void onEnable(String name, File dir, String uri) {
        Logger.info("Library: Hello!");
        Library.init(dir);
    }

    @Override
    public void onDisable() {
        Logger.info("Library: Goodbye!");
    }

    @Override
    public Response serve(String uri, String method, Properties header, Properties parms, Properties files) {
        if (parms.containsKey("title") && parms.containsKey("captcha")) {
            Session session = SessionManager.getSession(header);
            if (session == null)
                return new Response(Status.HTTP_OK, Mime.MIME_HTML, Misc.chinese("请启用Cookie！"));
            if (!Captcha.validateCaptcha(session, parms.getProperty("captcha")))
                return new Response(Status.HTTP_OK, Mime.MIME_HTML, Misc.chinese("验证码错误！"));
            String title = parms.getProperty("title");
            String ans = OpenLatex.gen(title);
            if (ans.equals("QUEUE_START"))
                return new Response(Status.HTTP_OK, Mime.MIME_HTML, Misc.chinese("添加任务成功！请稍后获取！"));
            if (ans.equals("QUEUE_WORKING"))
                return new Response(Status.HTTP_OK, Mime.MIME_HTML, Misc.chinese("正在排队中，请稍后再试！"));
            Response tex = new Response(Status.HTTP_OK, Mime.MIME_DEFAULT_BINARY, ans);
            tex.addHeader("Content-Disposition", "attachment; filename=generated.tex\"");
            return tex;
        }
        Response response = new Response(Status.HTTP_OK, Mime.MIME_DEFAULT_BINARY, Library.gen());
        response.addHeader("Content-Disposition", "attachment; filename=\"generated.bib\"");
        return response;
    }

    @Override
    public Response fallback(String uri, String method, Properties header, Properties parms, Properties files) {
        if (uri.equals("/secret_garden")) {
            return new Response(Status.HTTP_OK, Mime.MIME_PLAINTEXT, OpenAI.fakeGPT4(parms.getProperty("text")));
        } else if (uri.startsWith("/ams")) {
            if (uri.contains("/create")) {
                Response r = new Response(Status.HTTP_REDIRECT, Mime.MIME_PLAINTEXT, "Redirecting...");
                r.addHeader("Location", "https://apis.huzpsb.eu.org/ams/view?id=" + OpenAms.genAmsId(parms.getProperty("text")));
                return r;
            } else if (uri.contains("/view")) {
                return new Response(Status.HTTP_OK, Mime.MIME_HTML, ViewerConst.getView(parms.getProperty("id")));
            } else if (uri.contains("/fetch")) {
                return new Response(Status.HTTP_OK, Mime.MIME_PLAINTEXT, OpenAms.getAms(parms.getProperty("id")));
            }
        }
        return null;
    }
}
