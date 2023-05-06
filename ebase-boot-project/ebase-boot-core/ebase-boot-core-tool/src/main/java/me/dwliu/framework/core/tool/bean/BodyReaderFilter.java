package me.dwliu.framework.core.tool.bean;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.io.IOException;

/**
 * https://adongs.com/articles/2020/03/20/1584696128501.html
 *
 * @author liudw
 * @date 2023/5/5 21:55
 **/
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@WebFilter(filterName = "bodyReader", urlPatterns = "/**")
public class BodyReaderFilter implements Filter {


	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.debug("===开启BodyReaderFilter ，阻止getInputStream 只能读取一次的问题===");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
		throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) servletRequest;
		BodyReaderRequestWrapper requestWrapper = new BodyReaderRequestWrapper(req);
		filterChain.doFilter(requestWrapper, servletResponse);
	}

//	@Override
//	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
//		throws IOException, ServletException {
//		HttpServletRequest req = (HttpServletRequest) servletRequest;
//		ContentCachingRequestWrapper contentCachingRequestWrapper = new ContentCachingRequestWrapper(req);
//		filterChain.doFilter(contentCachingRequestWrapper, servletResponse);
//	}

	@Override
	public void destroy() {

	}

}
