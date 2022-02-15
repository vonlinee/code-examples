package code.pocket.base.utils;

import java.util.List;

public class FilterChain {
	
	private List<Filter> filters;
	
	public void addFilter(Filter filter) {
		filters.add(filter);
	}
	
	public boolean doFilter(Object obj) {
		for(Filter filter : filters) {
			if (!filter.apply(obj)) {
				return false;
			}
		}
		return true;
	}
}
