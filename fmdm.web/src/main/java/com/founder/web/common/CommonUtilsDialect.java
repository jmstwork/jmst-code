package com.founder.web.common;



import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.context.IContext;
import org.thymeleaf.context.IProcessingContext;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.dialect.IExpressionEnhancingDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.spring3.dialect.SpringStandardDialect;

/**
 * 
 * @author wp
 *
 */
public class CommonUtilsDialect extends SpringStandardDialect implements
		IExpressionEnhancingDialect {

	public static final String DEFAULT_PREFIX = "wp";

	private static final String PROPERTY_EXPRESSION_OBJECT_NAME = "wpload";

	public CommonUtilsDialect() {
		super();
	}

	public String getPrefix() {
		return DEFAULT_PREFIX;
	}

	public boolean isLenient() {
		return false;
	}

	@Override
	public Set<IProcessor> getProcessors() {
		final Set<IProcessor> processors = new LinkedHashSet<IProcessor>();
		return processors;
	}

	public Map<String, Object> getAdditionalExpressionObjects(
			IProcessingContext processingContext) {

		final IContext context = processingContext.getContext();
		final IWebContext webContext = (context instanceof IWebContext ? (IWebContext) context
				: null);

		final Map<String, Object> objects = new HashMap<String, Object>(3, 1.0f);

		/*
		 * Create the #authentication and #authorization expression objects
		 */
		if (webContext != null) {

			final HttpServletRequest request = webContext
					.getHttpServletRequest();
			final HttpServletResponse response = webContext
					.getHttpServletResponse();
			final ServletContext servletContext = webContext
					.getServletContext();

			if (request != null && response != null && servletContext != null) {
				objects.put(PROPERTY_EXPRESSION_OBJECT_NAME,
						new CommonUtils());
			}

		}

		return objects;
	}
}