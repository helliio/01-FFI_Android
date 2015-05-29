package edu.ntnu.sair.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Chun on 3/10/15.
 * <br>
 * GZIPFilter: GZIPFilter model is used to check if the request from client is with GZIP format.
 */


public class GZIPFilter implements Filter {

    /**
     * Init the filter
     *
     * @param filterConfig the configuration
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * Execute the filter
     *
     * @param servletRequest  the request from client
     * @param servletResponse the response to client
     * @param filterChain     the filters in a chain
     */
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

    /**
     * Destroy the filter
     */
    @Override
    public void destroy() {

    }
}
