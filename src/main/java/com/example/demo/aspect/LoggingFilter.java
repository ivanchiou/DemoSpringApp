package com.example.demo.aspect;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Component
public class LoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("Initializing LoggingFilter...");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest httpRequest) {
            logRequest(httpRequest);
        }
        chain.doFilter(request, response);
    }

    public void logRequest(HttpServletRequest request) {
        logger.info("LoggingFilter: Incoming request [{}] {}", request.getMethod(), request.getRequestURI());
        request.getHeaderNames().asIterator().forEachRemaining(headerName -> 
            logger.info("LoggingFilter: Header: {} = {}", headerName, request.getHeader(headerName))
        );
    }

    @Override
    public void destroy() {
        logger.info("Destroying LoggingFilter...");
    }
}

