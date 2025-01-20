package com.example.demo.config;

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
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest httpRequest) {
            // 記錄請求的基本資訊
            logger.info("Incoming request: [{}] {}", httpRequest.getMethod(), httpRequest.getRequestURI());

            // 記錄請求標頭
            httpRequest.getHeaderNames().asIterator().forEachRemaining(headerName -> 
                logger.info("Header: {} = {}", headerName, httpRequest.getHeader(headerName))
            );

            // 如果需要，可以讀取更多請求資訊（例如參數或內容）
        }

        // 繼續執行後續的過濾器或請求處理
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        logger.info("Destroying LoggingFilter...");
    }
}

