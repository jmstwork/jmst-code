package com.founder.web.security;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

public class SessionTimeOutInterceptor extends LoginUrlAuthenticationEntryPoint {

	private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	public void commence(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {

		String redirectUrl = null;

		if (this.isUseForward()) {
			if (this.isForceHttps() && "http".equals(request.getScheme())) {
				redirectUrl = buildHttpsRedirectUrlForRequest(request);
			}
			if (redirectUrl == null) {
				String loginForm = determineUrlToUseForThisRequest(request,
						response, authException);
				RequestDispatcher dispatcher = request
						.getRequestDispatcher(loginForm);
				dispatcher.forward(request, response);
				return;
			}
		} else {
			if (!this.isAjaxRequest(request))
				redirectUrl = buildRedirectUrlToLoginPage(request, response,
						authException);
		}
//		request.getSession().invalidate();
		if (!this.isAjaxRequest(request)) {
			redirectStrategy.sendRedirect(request, response, redirectUrl);
		} else {
			// 在响应头设置session状态
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
	}

	public static boolean isAjaxRequest(HttpServletRequest request){
		
		String requestType = request.getHeader("X-Requested-With");  
		if(requestType!=null && "XMLHttpRequest".equals(requestType)){
			return true;
		}
		return false;
	}
}