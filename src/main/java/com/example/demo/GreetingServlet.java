package com.example.demo;

import java.io.*;

import com.example.demo.filter.BlacklistDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import com.example.demo.DAO.LotteryDAO;
import com.example.demo.filter.BlacklistFilter;
import com.example.demo.databaseconnection.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class GreetingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Vérifier si l'utilisateur a le rôle "tomcat"
        if (!request.isUserInRole("tomcat")) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied. You do not have the required role.");
            return;
        }

        // Vérifier si le nom de l'utilisateur est dans la liste noire
       /* if (BlacklistDAO.isUserNameBlacklisted(request.getParameter("nom"))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied. Your name is on the blacklist.");
            return;
        }*/

        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Vérifier si l'utilisateur a le rôle "tomcat"
        if (!request.isUserInRole("tomcat")) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied. You do not have the required role.");
            return;
        }

        // Vérifier si le nom de l'utilisateur est dans la liste noire
        /*if (BlacklistDAO.isUserNameBlacklisted(request.getParameter("nom"))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied. Your name is on the blacklist.");
            return;
        }*/

        processRequest(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Implémenter la logique pour la méthode PUT
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Implémenter la logique pour la méthode DELETE
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String docType = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
                    "Transitional//EN\">\n";
            String title = null;
            String votreNom = request.getParameter("nom");
            String nomPrenom = "Anonymous";
            if (votreNom != null)
                nomPrenom = votreNom.toUpperCase();
            title = "<H1>Greetings " + nomPrenom + "!</H1>\n";
            out.println(docType +
                    "<HTML>\n" +
                    "<HEAD><TITLE>Greetings Servlet</TITLE></HEAD>\n" +
                    "<BODY BGCOLOR=\"#FDF5E6\">\n" +
                    title +
                    "</BODY></HTML>");

            double userWinnings = LotteryDAO.getUserWinnings(votreNom);
            out.println("Vous avez gagné: " + userWinnings + " millions de dollars!");

            LotteryDAO.saveUserWinnings(votreNom, Math.random() * 10);
        }
    }
}