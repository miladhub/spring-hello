package org.sample.spring.hello;

/**
 * Greeting service which prints its greeting onto <code>System.out</code>
 * 
 * @author milad
 */
public class GreetingServiceImpl implements GreetingService {
    
    private String greeting;
    
    private EnvService env;
    
    public GreetingServiceImpl() {}
    
    public GreetingServiceImpl(String greeting) {
        this.greeting = greeting;
    }
    
    public void sayGreeting() {
        env.println(greeting);
    }
    
    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }   
    
    public void setEnv(EnvService env) {
		this.env = env;
	}

	public String getName() {
        return "greeting impl";
    }
    
}