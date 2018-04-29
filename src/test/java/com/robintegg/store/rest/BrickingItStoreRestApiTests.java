package com.robintegg.store.rest;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:com/robintegg/store/rest/", strict = true, glue = "com.robintegg.store.rest")
public class BrickingItStoreRestApiTests {

}
