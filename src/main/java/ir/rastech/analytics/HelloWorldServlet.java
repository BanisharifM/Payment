package ir.rastech.analytics;


import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HelloWorldServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.getOutputStream().write("Hello ...".getBytes());
        EntityManagerFactory emf = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext()).getBean("entityManagerFactory", EntityManagerFactory.class);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

       /* Translation rt = new Translation();
        em.persist(rt);*/

        em.getTransaction().commit();
        em.close();
    }
}
