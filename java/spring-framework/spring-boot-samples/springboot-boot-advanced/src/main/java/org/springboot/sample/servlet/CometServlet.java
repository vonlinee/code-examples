package org.springboot.sample.servlet;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * HTTP长连接实现
 * @author 单红宇(365384722)
 * @myblog http://blog.csdn.net/catoop/
 * @create 2016年3月29日
 */
@WebServlet(urlPatterns = "/xs/cometservlet", asyncSupported = true)
//异步处理的servlet若存在过滤器，则过滤器的注解@WebFilter应设置asyncSupported=true，
//否则会报错A filter or servlet of the current chain does not support asynchronous operations.
public class CometServlet extends HttpServlet {

    private static final long serialVersionUID = -8685285401859800066L;

    private final Queue<AsyncContext> asyncContexts = new LinkedBlockingQueue<>();

    private final Thread generator = new Thread("Async Event generator") {

        @Override
        public void run() {
            while (!generator.isInterrupted()) {// 线程有效
                try {
                    while (!asyncContexts.isEmpty()) {// 不为空
                        TimeUnit.SECONDS.sleep(6);// 秒，模拟耗时操作
                        AsyncContext asyncContext = asyncContexts.poll();
                        HttpServletResponse res = (HttpServletResponse) asyncContext.getResponse();
                        res.getWriter().write("{\"result\":\"OK - " + System.currentTimeMillis() + "\"}");
                        res.setStatus(HttpServletResponse.SC_OK);
                        res.setContentType("application/json");
                        asyncContext.complete();// 完成
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    };

    @Override
    public void init() throws ServletException {
        super.init();
        generator.start();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(">>>>>>>>>>CometServlet Request<");
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AsyncContext asyncContext = req.startAsync();
        asyncContext.setTimeout(20 * 1000L);
        asyncContexts.offer(asyncContext);
    }

    @Override
    public void destroy() {
        super.destroy();
        generator.interrupt();
    }


}
