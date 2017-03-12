package me.bbb1991.main;

import me.bbb1991.config.PropertyReader;
import me.bbb1991.config.PropertyReaderImpl;
import me.bbb1991.servlet.EntryPoint;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by bbb1991 on 3/12/17.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        logger.info("Starting server...");
        PropertyReader reader = new PropertyReaderImpl("application.properties");

        int port = Integer.parseInt(reader.getPropsAsProperties().getProperty("jetty.port"));

        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);

        contextHandler.setContextPath("/");

        Server server = new Server(port);
        server.setHandler(contextHandler);

        ServletHolder jerseyServlet = contextHandler.addServlet(ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        jerseyServlet.setInitParameter("jersey.config.server.provider.classnames", EntryPoint.class.getCanonicalName());

        server.start();

        logger.info("Server started!");

        server.join();

    }
}
