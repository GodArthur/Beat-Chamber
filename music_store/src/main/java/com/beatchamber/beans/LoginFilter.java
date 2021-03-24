package com.beatchamber.beans;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebFilter;

import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Filter checks if LoginBean has loginIn property set to true. If it is not set
 * then request is being redirected to the login.xhml page.
 *
 * @author itcuties http://www.itcuties.com/j2ee/jsf-2-login-filter-example/
 *
 * Changed to annotation rather than a filter mapping in the web.xml
 *
 */
@WebFilter(filterName = "LoginFilter", urlPatterns = {"/management_pages/*"})
public class LoginFilter implements Filter {

    private final static Logger LOG = LoggerFactory.getLogger(LoginFilter.class);

    @Inject
    private LoginBean loginBean; // As an instance variable

    /**
     * Checks if user is logged in. If not it redirects to the login.xhtml page.
     *
     * @param request
     * @param response
     * @param chain
     * @throws java.io.IOException
     * @throws javax.servlet.ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        // Get the loginBean from session attribute

        LOG.info("In the filter");

        // For the first application request there is no loginBean in the
        // session so user needs to log in
        // For other requests loginBean is present but we need to check if user
        // has logged in successfully
        if (loginBean == null || !loginBean.isLoggedIn()) {
            LOG.info("User not logged in");

            String contextPath = ((HttpServletRequest) request)
                    .getContextPath();
            ((HttpServletResponse) response).sendRedirect(contextPath
                    + "/login.xhtml");
        } else {
            LOG.info("User logged in: "
                    + loginBean.getUsername());
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        // Nothing to do here!
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Nothing to do here but must be overloaded
    }

}
