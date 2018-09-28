package api;


import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/message")
public class initialServlet extends HttpServlet {

    @Resource(lookup = "java:comp/DefaultJMSConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "java:jboss/exported/jms/queue/test")
    private Queue queue;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        try(JMSContext jmsContext = connectionFactory.createContext()){
            jmsContext.createProducer().send(queue,jmsContext.createMessage());

        }

        response.setContentType("text/html");
        try(PrintWriter out =response.getWriter()){
            out.print("<html><body><h1>");
            out.print("Sending message.");
            out.print("</h1></body></html>");
        }
    }

}
