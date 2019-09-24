package com.jdy.spring.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CrossDomainFilter implements Filter {
	
	
	public CrossDomainFilter() {
		
	}
	

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletResponse res = (HttpServletResponse)response;
		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		res.setHeader("Access-Control-Max-Age", "3600");
		res.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		response.setContentType("text/html");
		chain.doFilter(request, response);
	}
	
	
	public void init(FilterConfig fConfig) throws ServletException {
	
	}


	
	public void destroy() {
		
	}
}
