package com.netcracker.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@WebServlet("/login")
public class Login extends HttpServlet {

    String login;
    String password;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getParameter("login") == "" || req.getParameter("password") == "") {
            if (CheckCookies(req, resp) /*CheckSession(req, resp)*/) resp.getWriter().write("Hello, " + login);
            else resp.getWriter().write("Wrong login or password");
            return;
        }

        login=req.getParameter("login");
        password=req.getParameter("password");

        if (CheckLoginAndPassword(resp)) {
            //SaveSession(req, resp);
            SaveCookies(req, resp);
        }

    }

    boolean CheckLoginAndPassword(HttpServletResponse resp){
        try (BufferedReader file = new BufferedReader
                (new FileReader("C:\\Users\\Alyona\\IdeaProjects\\cookies\\src\\main\\resources\\users.txt"))) {
            String str;
            while ((str = file.readLine()) != null) {
                String loginStr = str.substring(0, str.indexOf(" "));

                if (loginStr.equals(login)) {
                    String passwordStr = str.substring(str.indexOf(" ") + 1, str.length());
                    if (passwordStr.equals(password)) {
                        resp.getWriter().write("Hello, " + login);
                        return true;
                    } else resp.getWriter().write("Wrong password");
                    return false;
                }
            }

            resp.sendRedirect("/create.html");
            return false;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    void SaveSession(HttpServletRequest req, HttpServletResponse resp){

        HttpSession session = req.getSession();
        session.setAttribute("login", login);
        session.setAttribute("password", password);
    }

    boolean CheckSession(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        if (req.getSession().getAttribute("login").equals(login) &&
                req.getSession().getAttribute("password").equals(password)) return true;
         return false;

    }


    void SaveCookies(HttpServletRequest req, HttpServletResponse resp){

        Cookie cook1=new Cookie("login", login);
        Cookie cook2=new Cookie("password", password);

        resp.addCookie(cook1);
        resp.addCookie(cook2);
    }

    boolean CheckCookies(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        boolean rightLogin=false;
        boolean rightPassword=false;

        Cookie[] cookies = req.getCookies();
        for (Cookie cook: cookies){
            if (cook.getName().equals("login")&&cook.getValue().equals(login)) rightLogin=true;
            if (cook.getName().equals("password")&&cook.getValue().equals(password)) rightPassword=true;
        }

        return rightLogin&&rightPassword;
    }
}
