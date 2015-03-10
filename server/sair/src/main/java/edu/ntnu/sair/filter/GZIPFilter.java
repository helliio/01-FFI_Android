package edu.ntnu.sair.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by chun on 3/10/15.
 */

public class GZIPFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String acceptEncoding = request.getHeader("Accept-Encoding");
        if (acceptEncoding != null || acceptEncoding.toLowerCase().equals("gzip")) {
            GZIPResponseWrapper wrappedResponse = new GZIPResponseWrapper(response);
            filterChain.doFilter(servletRequest, wrappedResponse);
            wrappedResponse.finishResponse();
        } else
            filterChain.doFilter(servletRequest, response);
        return;

    }

    @Override
    public void destroy() {

    }
}
