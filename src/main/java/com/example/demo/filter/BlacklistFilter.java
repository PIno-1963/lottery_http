package com.example.demo.filter;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class BlacklistFilter implements Filter {
    private List<String> blacklist;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Charger la liste noire depuis un fichier de configuration ou une base de données
        blacklist = Arrays.asList("baduser1", "baduser2", "baduser3");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String userName = httpRequest.getParameter("nom");
        if (blacklist.contains(userName)) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied. Your name is on the blacklist.");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }
}
