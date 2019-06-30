package ir.rastech.analytic.test.jerseytest;

/**
 * Created by hassan on 05/06/2016.
 */


import com.google.gson.Gson;
import com.idehgostar.makhsan.core.response.SimpleResponse;
import ir.rastech.analytics.AnalyticApi;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import retrofit2.Response;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.io.IOException;


public class WithJettyTest {
    public static AnalyticApi api;
    static protected String basePath;
    static Gson gson = new Gson();
    static Client client = ClientBuilder.newClient();
    static RunServer server = null;
    private String accessToken = "01Yr8KETH1AxF31nPflRB4HQhOxPC2AoyY5Csjzt/uFmZNKLybPq3kLrkiLGR3Al5vo9dl83m1q514aBcpC4xV+AqFZgCKeEEpk44vAqIsdHm9+jqpc024QQ8Xa0s3ggNOxtl0Tec83fe/xn0II5kpaPnCiIZ+T7h+l81O6pZ5o=";

    @BeforeClass
    public static void runServer() throws Exception {
        if (server == null) {
            String messageBasePath;
            if (true) {
                server = new RunServer();
                server.setPortNumber(5718);
                server.setDefaultsDescriptor("src/test/webapp/WEB-INF/web.xml");
                server.setTestMode(true);
                server.start();
                basePath = server.getBasePath();
                messageBasePath = server.getMessageBasePath();
            } else {
                messageBasePath = "http://localhost:8732/mess/";
                basePath = "http://localhost:8080/man/";
            }
            RetrofitFactory retrofitFactory = new RetrofitFactory();
//
            api = retrofitFactory.getRetApi(AnalyticApi.class, basePath + "rest/");

        }
    }

    @AfterClass
    public static void stopServer() throws Exception {
//        server.stop();
//        server = null;
    }

    public static <T> T getObject(String json, Class<T> c) {
        return gson.fromJson(json, c);
    }

    public static SimpleResponse getErrorBody(Response response) throws IOException {
        String string = response.errorBody().string();
        try {
            return getObject(string, SimpleResponse.class);
        } catch (com.google.gson.JsonSyntaxException ex) {
            System.err.println("can not convert response object to SimpleResponseWithObject. object:");
            System.err.println(">>>> " + string);
            ex.printStackTrace();
            assert false;
        }
        return null;
    }

    public static <T> T assertSuccess(Response<T> response) throws IOException {
        if (response.isSuccessful()) {
            return response.body();
        }
        SimpleResponse errorBody = getErrorBody(response);
        throw new AssertionError(response.code() + " :\n" + errorBody.getMessage(), null);
    }

    public static SimpleResponse assert4xx(Response<?> response) {
        return assert4xx(response, null);
    }

    public static SimpleResponse assert4xx(Response<?> response, String statusMessage) {
        return assert4xx("", response, statusMessage);
    }

    public static SimpleResponse assert4xx(String failMessage, Response<?> response, String statusMessage) {
        Assert.assertTrue(failMessage + "\ncode must be 4xx but it is " + response.code(), response.code() >= 400 && response.code() < 500);
        try {
            SimpleResponse errorBody = getErrorBody(response);
            if (statusMessage != null) {
                Assert.assertEquals(errorBody.getMessage(), statusMessage);
            }
            return errorBody;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Before
    public void refresh() throws Exception {

    }

    protected WebTarget target(String path) {
        return client.target(basePath + path);
    }

    protected WebTarget target() {
        return client.target(basePath);
    }

}
