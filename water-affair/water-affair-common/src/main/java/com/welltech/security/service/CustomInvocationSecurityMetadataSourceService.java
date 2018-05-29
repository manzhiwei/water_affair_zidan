/**
 * 
 */
package com.welltech.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 最核心的地方，就是提供某个资源对应的权限定义， 即getAttributes方法返回的结果。 此类在初始化时， 应该取到所有资源及其对应角色的定义。
 * Created by Zhujia at 2017年8月4日 下午5:07:53
 */
//@Service
public class CustomInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {

	// @Autowired
	// private SResourceVODao sResourceVODao;
	//
	// @Autowired
	// private SRoleVODao sRoleVODao;

	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	@PostConstruct // 被@PostConstruct修饰的方法会在服务器加载Servle的时候运行，并且只会被服务器执行一次。PostConstruct在构造函数之后执行,init()方法之前执行。
	private void loadResourceDefine() { // 一定要加上@PostConstruct注解
		// 在Web服务器启动时，提取系统中的所有权限。
//		List<Map<String, Object>> list = sRoleVODao.findAll();
//		List<String> query = new ArrayList<String>();
//		if (list != null && list.size() > 0) {
//			for (Map<String, Object> sr : list) {
//				// String name = sr.get("name")
//				Object value = sr.get("name");
//				String name = String.valueOf(value);
//				query.add(name);
//			}
//		}
		/*
		 * 应当是资源为key， 权限为value。 资源通常为url， 权限就是那些以ROLE_为前缀的角色。 一个资源可以由多个权限来访问。
		 * sparta
		 */
		resourceMap = new HashMap<String, Collection<ConfigAttribute>>();

//		for (String auth : query) {
//			ConfigAttribute ca = new SecurityConfig(auth);
//			// List<Map<String,Object>> query1 =
//			// sResourceVODao.findByRoleName(auth);
//			List<String> query1 = new ArrayList<String>();
//			List<Map<String, Object>> list1 = sResourceVODao.findByRoleName(auth);
//			if (list1 != null && list1.size() > 0) {
//				for (Map<String, Object> map : list1) {
//					Object value = map.get("resource_string");
//					String url = String.valueOf(value);
//					query1.add(url);
//				}
//			}
//			for (String res : query1) {
//				String url = res;
//
//				/*
//				 * 判断资源文件和权限的对应关系，如果已经存在相关的资源url，则要通过该url为key提取出权限集合，
//				 * 将权限增加到权限集合中。 sparta
//				 */
//				if (resourceMap.containsKey(url)) {
//
//					Collection<ConfigAttribute> value = resourceMap.get(url);
//					value.add(ca);
//					resourceMap.put(url, value);
//				} else {
//					Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
//					atts.add(ca);
//					resourceMap.put(url, atts);
//				}
//
//			}
//		}

	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return new ArrayList<ConfigAttribute>();
	}

	// 根据URL，找到相关的权限配置。
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		System.out.println("nwuidhwuiehdfu");
		// object 是一个URL，被用户请求的url。
		FilterInvocation filterInvocation = (FilterInvocation) object;
		if (resourceMap == null) {
			loadResourceDefine();
		}
		Iterator<String> ite = resourceMap.keySet().iterator();
		while (ite.hasNext()) {
			String resURL = ite.next();
			RequestMatcher requestMatcher = new AntPathRequestMatcher(resURL);
			if (requestMatcher.matches(filterInvocation.getHttpRequest())) {
				return resourceMap.get(resURL);
			}
		}

		return null;
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

}
