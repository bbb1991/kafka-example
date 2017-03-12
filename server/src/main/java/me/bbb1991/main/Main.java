package me.bbb1991.main;

import me.bbb1991.config.PropertyReader;
import me.bbb1991.config.PropertyReaderImpl;
import me.bbb1991.servlet.Resources;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jboss.resteasy.plugins.server.servlet.HttpServlet30Dispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Главный класс, который запускает сервер
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public class Main {

    /**
     * Логгер класса
     */
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        logger.info("Starting server...");
        PropertyReader reader = new PropertyReaderImpl("application.properties");

        int port = Integer.parseInt(reader.getPropsAsProperties().getProperty("jetty.port"));

        Server server = new Server(port);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        ServletHolder h = new ServletHolder(new HttpServlet30Dispatcher());
        h.setInitParameter("javax.ws.rs.Application", Resources.class.getCanonicalName());
        context.addServlet(h, "/*");
        server.setHandler(context);
        server.start();

        logger.info("Server started!");

        server.join();
    }
}
