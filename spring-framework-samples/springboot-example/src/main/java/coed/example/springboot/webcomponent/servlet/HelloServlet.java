package coed.example.springboot.webcomponent.servlet;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletRegistration;

public class HelloServlet implements ServletRegistration{

	@Override
	public String getName() {
		return null;
	}

	@Override
	public String getClassName() {
		return null;
	}

	@Override
	public boolean setInitParameter(String name, String value) {
		return false;
	}

	@Override
	public String getInitParameter(String name) {
		return null;
	}

	@Override
	public Set<String> setInitParameters(Map<String, String> initParameters) {
		return null;
	}

	@Override
	public Map<String, String> getInitParameters() {
		return null;
	}

	@Override
	public Set<String> addMapping(String... urlPatterns) {
		return null;
	}

	@Override
	public Collection<String> getMappings() {
		return null;
	}

	@Override
	public String getRunAsRole() {
		return null;
	}

}
