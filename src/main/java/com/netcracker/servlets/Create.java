package com.netcracker.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet("/create")
public class Create extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login=req.getParameter("login");
        String password=req.getParameter("password");

        try(BufferedWriter file = new BufferedWriter(new FileWriter("C:\\Users\\Alyona\\IdeaProjects\\cookies\\src\\main\\resources\\users.txt", true))) {
                file.newLine();
                file.write(login+" "+password);
                resp.sendRedirect("/login.html");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
