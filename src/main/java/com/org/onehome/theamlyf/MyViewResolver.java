package com.org.onehome.theamlyf;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

@Configuration
@EnableWebMvc
public class MyViewResolver implements WebMvcConfigurer {
	@Autowired
	WebApplicationContext webApplicationContext;

	/*
	 * @Override public void addResourceHandlers(ResourceHandlerRegistry registry) {
	 * System.out.println("---1--");
	 * registry.addResourceHandler("/jsp/**").addResourceLocations("/WEB-INF/jsp");
	 * }
	 */

	@Bean
	public SpringResourceTemplateResolver thymeleafTemplateResolver() {
		System.out.println("---2--");
		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
		templateResolver.setApplicationContext(webApplicationContext);
		templateResolver.setOrder(9);
		templateResolver.setPrefix("/WEB-INF/templates/");
		templateResolver.setSuffix("");
		return templateResolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine() {
		System.out.println("---3--");
		SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
		springTemplateEngine.setTemplateResolver(thymeleafTemplateResolver());
		springTemplateEngine.setEnableSpringELCompiler(true);
		return springTemplateEngine;
	}

	@Bean
	public ThymeleafViewResolver thymeleafViewResolver() {
		System.out.println("---4--");
		final ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setViewNames(new String[] { "*.html" });
		viewResolver.setExcludedViewNames(new String[] { "*.jsp" });
		viewResolver.setTemplateEngine(templateEngine());
		viewResolver.setCharacterEncoding("UTF-8");
		return viewResolver;
	}

	@Bean
	public InternalResourceViewResolver jspViewResolver() {
		System.out.println("---5--");
		final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		// viewResolver.setOrder(10);
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/jsp/");
		viewResolver.setSuffix(".jsp");
		/* viewResolver.setViewNames(""); */
		return viewResolver;
	}
	/*
	 * @Bean public UrlBasedViewResolver viewResolver() { UrlBasedViewResolver
	 * resolver = new UrlBasedViewResolver(); resolver.setPrefix("/WEB-INF/jsp/");
	 * resolver.setSuffix(".jsp"); resolver.setViewClass(JstlView.class); return
	 * resolver; }
	 */
}
