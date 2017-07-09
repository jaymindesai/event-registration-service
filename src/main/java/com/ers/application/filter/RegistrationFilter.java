package com.ers.application.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;

@WebFilter("/registrations/registration")
@Slf4j
public class RegistrationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String query = request.getQueryString();
        Arrays.stream(query.split("&"))
                .map(this::decode)
                .forEach(segment -> request.setAttribute(segment.split("=")[0], segment.split("=")[1]));
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    private String decode(String value) {
        return new String(Base64.getDecoder().decode(value));
    }
}
