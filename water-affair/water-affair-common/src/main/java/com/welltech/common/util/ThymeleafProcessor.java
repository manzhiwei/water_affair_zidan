package com.welltech.common.util;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

@Service
public class ThymeleafProcessor {

//	public String process(String ftl, Map<String, Object> map,
//			BaseAction baseAction) throws IOException, TemplateException {
//		ApplicationContext context = WebApplicationContextUtils
//				.getWebApplicationContext(ServletActionContext
//						.getServletContext());
//		FreeMarkerConfigurer configurer = (FreeMarkerConfigurer) context
//				.getBean("freemarkerConfigurer");
//		Configuration freemarkerCfg = configurer.getConfiguration();
//		freemarkerCfg.setEncoding(Locale.getDefault(), "UTF-8");
//		String commonResultPath = baseAction.getCommonResultPath();
//
//		String ftlPath = "/" + commonResultPath;
//		if (ftl.startsWith("/")) {
//			ftlPath = ftlPath + ftl;
//		} else {
//			ftlPath = ftlPath + "/" + ftl;
//		}
//		Template template = freemarkerCfg.getTemplate(ftlPath);
//		template.setEncoding("UTF-8");
//
//		StringWriter result = new StringWriter();
//		map.put("commonResultPath", commonResultPath);
//
//		TemplateModel model = createModel(baseAction,
//				freemarkerCfg.getObjectWrapper(), map);
//
//		((ScopesHashModel) model).putAll(map);
//
//		template.process(model, result);
//		return result.toString();
//	}
//
//	protected TemplateModel createModel(Object action, ObjectWrapper wrapper,
//			Map<String, Object> map) throws TemplateModelException {
//		ServletContext servletContext = ServletActionContext
//				.getServletContext();
//		HttpServletRequest request = ServletActionContext.getRequest();
//		HttpServletResponse response = ServletActionContext.getResponse();
//		ValueStack stack = ServletActionContext.getContext().getValueStack();
//
//		for (String key : map.keySet()) {
//			stack.set(key, map.get(key));
//		}
//
//		return freemarkerManager.buildTemplateModel(stack, action,
//				servletContext, request, response, wrapper);
//	}
	
	
	public String process(HttpServletRequest request, HttpServletResponse response,
			TemplateEngine templateEngine,Map<String, Object> map,String html) throws Exception {
		
//		String commonResultPath = baseAction.getCommonResultPath();
//		String htmlPath = "/" + commonResultPath;
//		if (html.startsWith("/")) {
//			htmlPath = htmlPath + html;
//		} else {
//			htmlPath = htmlPath + "/" + html;
//		}
		WebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale());
		ctx.setVariables(map);
		PrintWriter writer = response.getWriter();
		templateEngine.process(html, ctx, writer);
		return writer.toString();
	}
}