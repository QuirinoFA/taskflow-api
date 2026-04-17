package com.taskflow;

import java.util.logging.Logger;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import com.taskflow.config.ApiResourceConfig;

public class App {
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    public static void main(String[] args) throws Exception {
        ResourceConfig config = new ApiResourceConfig();
        int port = resolvePort();

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(port);
        tomcat.getConnector();

        Context context = tomcat.addContext("", System.getProperty("java.io.tmpdir"));

        ServletContainer jerseyServlet = new ServletContainer(config);
        Tomcat.addServlet(context, "jersey-servlet", jerseyServlet);
        context.addServletMappingDecoded("/api/*", "jersey-servlet");

        tomcat.start();
        LOGGER.info(() -> String.format("Tomcat embutido iniciado em http://localhost:%d/api/user (POST)", port));
        tomcat.getServer().await();
    }

    private static int resolvePort() {
        String portValue = System.getenv("APP_PORT");
        if (portValue == null ) {
            return 8080;
        }

        try {
            return Integer.parseInt(portValue);
        } catch (NumberFormatException ex) {
            LOGGER.warning("APP_PORT invalida. Usando 8080 por padrao.");
            return 8080;
        }
    }
}
