package ir.rastech.analytic.test.jerseytest;

import com.sun.jersey.test.framework.JerseyTest;

//
//import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
//import com.idehgostar.makhsan.domain.init.MakhsanInitDataFiller;
//import com.sun.jersey.api.client.config.ClientConfig;
//import com.sun.jersey.api.client.config.DefaultClientConfig;
//import com.sun.jersey.api.container.filter.LoggingFilter;
//import com.sun.jersey.api.core.ResourceConfig;
//import com.sun.jersey.api.json.JSONConfiguration;
//import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
//import com.sun.jersey.test.framework.AppDescriptor;
//import com.sun.jersey.test.framework.JerseyTest;
//import com.sun.jersey.test.framework.WebAppDescriptor;
//import com.sun.jersey.test.framework.spi.container.TestContainerException;
//import com.sun.jersey.test.framework.spi.container.TestContainerFactory;
//import dbfill.MakhsanTestDataFiller;
//import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.springframework.web.context.ContextLoaderListener;
//
//import java.io.IOException;
//import java.net.ServerSocket;
//
///**
// * Test class which will wire itelf into your the Spring context which
// * is configured on the WebAppDecriptor built for your tests.
// * Ensure you configure annotation-aware support into your contexts,
// * and annotate any auto-wire properties on your test class
// * @author George McIntosh
// *
// */
//
//
//public abstract class AbstractSpringAwareJerseyTest extends JerseyTest {
//
//	public AbstractSpringAwareJerseyTest() {
//		super();
//	}
//
//	public AbstractSpringAwareJerseyTest(WebAppDescriptor wad) {
//		super(wad);
//	}
//
//	@BeforeClass
//	public static void setUp3() throws Exception {
//
//        MakhsanInitDataFiller.main(new String[]{"true", "drop"});
//        MakhsanTestDataFiller.main(new String[]{"true"});
//
//	}
//
//	protected TestContainerFactory getTestContainerFactory() throws TestContainerException {
//		return new jerseytest.SpringAwareGrizzlyTestContainerFactory(this);
//	}
//
//	@Override
//	protected int getPort(int defaultPort) {
//
//		ServerSocket server = null;
//		int port = -1;
//		try {
//			server = new ServerSocket(defaultPort);
//			port = server.getLocalPort();
//		} catch (IOException e) {
//			// ignore
//		} finally {
//			if (server != null) {
//				try {
//					server.close();
//				} catch (IOException e) {
//					// ignore
//				}
//			}
//		}
//		if ((port != -1) || (defaultPort == 0)) {
//			return port;
//		}
//		return getPort(0);
//	}
//
//	@Override
//	public AppDescriptor configure() {
//
//		ClientConfig cc = new DefaultClientConfig();
//		cc.getClasses().add(JacksonJsonProvider.class);
//		WebAppDescriptor wa = new WebAppDescriptor.Builder()
//				.contextPath("/")
//				.contextParam("contextConfigLocation",
//                        "classpath:applicationContext-analytics-resource-test.xml " +
//                                "classpath:applicationContext-analytics-persistence.xml " +
//								"classpath:applicationContext-analytics-security.xml " +
//								"classpath:applicationContext-analytics-service.xml " +
//                                "classpath:applicationContext-analytics.xml " +
//                                "classpath*:/applicationContext-confix.xml"
//
//				)
//				.contextListenerClass(ContextLoaderListener.class)
//				.servletClass(SpringServlet.class)
//				.initParam(ResourceConfig.FEATURE_TRACE, "true")
//				.initParam(ResourceConfig.PROPERTY_CONTAINER_REQUEST_FILTERS, LoggingFilter.class.getCanonicalName())
//				.initParam(ResourceConfig.PROPERTY_CONTAINER_NOTIFIER, LoggingFilter.class.getCanonicalName())
//				.initParam(ResourceConfig.PROPERTY_CONTAINER_RESPONSE_FILTERS, LoggingFilter.class.getCanonicalName())
//				.initParam(JSONConfiguration.FEATURE_POJO_MAPPING, "true")
//				.addFilter(org.springframework.web.filter.DelegatingFilterProxy.class, "authenticationInfoExtractor")
//				.build();
//		return wa;
//	}
//
//	@Before
//	public void setUp2() throws Exception {
//
//        MakhsanInitDataFiller.main(new String[]{"true", "drop"});
//        MakhsanTestDataFiller.main(new String[]{"true"});
//
//	}
//
//
//}
public abstract class AbstractSpringAwareJerseyTest extends JerseyTest {
}