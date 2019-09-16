package com.example.demo.helloWorld;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping(method = RequestMethod.GET, path = "/hello-world")
	public String helloWorld() {
		return "Hola Mundo";
	}
	
	@GetMapping(path = "/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hola Mundo");
	}
	
	@GetMapping(path = "/hello-world-bean-variable/{name}")
	public HelloWorldBean helloWorldBean(@PathVariable String name) {
		return new HelloWorldBean(String.format("Hola Mundo, %s", name));
	}
	
	@GetMapping("/hello-world-internacional")
	public String helloWorldInternacionali(@RequestHeader(name = "Accept-Language")  Locale locale) {
		return messageSource.getMessage("hello.world", null, locale);
	}
	
	@GetMapping("/hello-world-internacional2")
	public String helloWorldInternacionali2() {
		return messageSource.getMessage("hello.world", null, LocaleContextHolder.getLocale());
	}
	
}
