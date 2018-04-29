package com.robintegg.store.rest;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Scope("cucumber-glue")
@Component
public class RestClient implements InitializingBean {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	private ResultActions resultActions;

	@Override
	public void afterPropertiesSet() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	public ResultActions getResultActions() {
		return resultActions;
	}

	public void perform(MockHttpServletRequestBuilder requestBuilder) throws Exception {
		this.resultActions = this.mockMvc.perform(requestBuilder);
	}

}
