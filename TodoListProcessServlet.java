
package com.iss.servlets;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import javax.enterprise.context.SessionScoped;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Deepak Pakhale
 */
@WebServlet("/todolistprocessservlet")
public class TodoListProcessServlet extends HttpServlet{

    RequestDispatcher rd;
    HttpSession session;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       String listcontent = req.getParameter("listcontent");
       String item = req.getParameter("item");
       if(req.getParameter("add")!=null){
          listcontent = listcontent+item;
          session = req.getSession(true);
          session.setAttribute("listcontent", listcontent);
          rd = req.getRequestDispatcher("/todolistdisplay.jsp");
          rd.forward(req, resp);
       }
       if(req.getParameter("logout")!=null){
           session = req.getSession(true);
           Properties prop = new Properties();
           prop.setProperty("user",(String) session.getAttribute("username"));
           prop.setProperty("tasklist",(String)session.getAttribute("listcontent"));      
           File file = new File("G:/todolist/todolist.properties");
           FileOutputStream fileOut = new FileOutputStream(file);
           prop.store(fileOut, "Task List");
           fileOut.close();
           resp.sendRedirect("http://www.iss.nus.edu.sg/");
           session.invalidate();
           
       }
        if(req.getParameter("clear")!=null){
            listcontent="";
            session = req.getSession(true);
            session.setAttribute("listcontent", listcontent);
            rd = req.getRequestDispatcher("/todolistdisplay.jsp");
            rd.forward(req, resp);
        }
    }
    
    
}
