package ir.rastech.analytic.test.jerseytest;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Created by hossein on 10/16/16.
 */
public class RunServer {

    String defaultsDescriptor = "src/main/webapp/WEB-INF/web.xml";
    Integer portNumber = 8716;
    Server server;
    private boolean testMode = false;

    public static void main(String[] args) throws Exception {
        RunServer mserver = new RunServer();
        mserver.start();
    }

    public void start() throws Exception {
        server = new Server(portNumber);

        // Handler for multiple web apps
        HandlerCollection handlers = new HandlerCollection();

        // Creating the first web application context
        WebAppContext serverContext = new WebAppContext();
        serverContext.setResourceBase("src/main/resources");
        serverContext.setContextPath("/");
        serverContext.setDefaultsDescriptor(defaultsDescriptor);
        server.setHandler(serverContext);

        server.start();


        System.out.println("Started!");
    }

    public String getBasePath() {
        return "http://localhost:" + portNumber + "/";
    }

    public String getMessageBasePath() {
        return "http://localhost:" + 8080 + "/mess/";
//        return "http://localhost:" + 8732 + "/";
    }

    public void stop() throws Exception {
        server.stop();
    }

    public Integer getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(Integer portNumber) {
        this.portNumber = portNumber;
    }

    public String getDefaultsDescriptor() {
        return defaultsDescriptor;
    }

    public void setDefaultsDescriptor(String defaultsDescriptor) {
        this.defaultsDescriptor = defaultsDescriptor;
    }

    public boolean isTestMode() {
        return testMode;
    }

    public void setTestMode(boolean testMode) {
        this.testMode = testMode;
    }
}