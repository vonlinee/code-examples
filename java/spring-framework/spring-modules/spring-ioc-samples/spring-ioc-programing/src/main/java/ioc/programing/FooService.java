package ioc.programing;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import ioc.programing.bean.Model;

@Service
public class FooService {

	private final FooRepository repository;

    @Autowired
    public FooService(FooRepository repository) {
        this.repository = repository;
        System.out.println(this.repository);
    }
    
    public FooService(ObjectProvider<FooRepository> repositoryProvider) {
    	System.out.println("=========================");
        this.repository = repositoryProvider.getIfUnique();
        int i = 1 / 0;
    }
    
    @Cacheable(cacheNames = "foos", sync = true)
    public Model getModel(String id) {
    	return new Model();
    }
}