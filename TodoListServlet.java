
package com.iss.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.jms.Session;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.omg.PortableServer.REQUEST_PROCESSING_POLICY_ID;

/**
 *
 * @author Deepak Pakhale
 */
@WebServlet("/todolistservlet")
public class TodoListServlet extends HttpServlet{

    RequestDispatcher rd;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        String username=(String) req.getParameter("username");
        String password=(String) req.getParameter("password");
        /*User is allowed to login only when username and password is equal.
         */
        if(username.equals(password)){
            HttpSession session = req.getSession(true);
            session.setAttribute("username", username);
            session.setAttribute("password", password);
            Properties prop = new Properties();
            File file = new File("G:/todolist/todolist.properties");
            FileInputStream fileInput = new FileInputStream(file);
            prop.load(fileInput);
            fileInput.close();
            if(prop.getProperty("user").equals(username))
              session.setAttribute("listcontent",(String)prop.getProperty("tasklist"));
            rd = req.getRequestDispatcher("/todolistdisplay.jsp");
            rd.forward(req, resp);
        }
        else{
            rd = req.getRequestDispatcher("/error.html");
            rd.forward(req, resp);
        }
        
    }
    
    
    
}
