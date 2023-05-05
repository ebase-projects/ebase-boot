package me.dwliu.framework.core.tool.bean;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.io.IOException;

/**
 * https://adongs.com/articles/2020/03/20/1584696128501.html
 *
 * @author liudw
 * @date 2023/5/5 21:55
 **/
@Order(Ordered.HIGHEST_PRECEDENCE)
@WebFilter(filterName = "bodyReader", urlPatterns = "/**")
public class BodyReaderFilter implements Filter {

	private final static Logger logger = LoggerFactory.getLogger(BodyReaderFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("filter init");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
		throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) servletRequest;
		BodyReaderRequestWrapper requestWrapper = new BodyReaderRequestWrapper(req);
		filterChain.doFilter(requestWrapper, servletResponse);
	}

	@Override
	public void destroy() {

	}

}
