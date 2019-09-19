package com.example.demo.filtering;

import java.util.Arrays;
import java.util.List;

import org.apache.tomcat.util.digester.ArrayStack;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController {
	
	@GetMapping("/filtering")
	public SomeBean retrieveSomeBean() {
		return new SomeBean("value1","value2","value3");
	}
	
	@GetMapping("/filtering2")
	public MappingJacksonValue retrieveSomeBean2() {
		SomeBean someBean = new SomeBean("value1","value2","value3");
		
		// Tener en cuenta que dentro del bean tenemos tambien filtro
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
				.filterOutAllExcept("field2");
		FilterProvider filters = new SimpleFilterProvider()
				.addFilter("SomeBeanFilter", filter);
		
		MappingJacksonValue mapping = new MappingJacksonValue(someBean);
		mapping.setFilters(filters);
		
		return mapping;
	}
	
	@GetMapping("/filtering-list")
	public List<SomeBean> retrieveListSomeBean() {
		return Arrays.asList(new SomeBean("value1","value2","value3"),
				new SomeBean("value1","value2","value3"));
	}
	
}
