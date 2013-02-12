package org.sample.spring.hello;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

/**
 * Main class.
 * 
 * @author milad
 */
public class HelloApp {
    
	/**
	 * Logger.
	 */
    protected static Logger logger = Logger.getLogger(HelloApp.class);
    
    /**
	 * Command-line syntax.
	 */
	private static final Object SYNTAX = new StringBuffer(200).append("Syntax:\n")
					.append("\tgreet\n")
					.append("\tlist [<city>]\n")
					.append("\tshow <name> <surname>\n")
					.append("\tadd <name> <surname> <city> <age>\n")
					.append("\thelp\n")
					.append("\tquit").toString();
	
    /**
     * Spring context.
     */
    protected GenericApplicationContext ctx;
    
    /**
     * Facility to read xml configuration and inject it into the context.
     */
    protected XmlBeanDefinitionReader xmlReader;
    
    /**
     * No-arg constructor. Calls the {@link #init()} template method.
     */
    public HelloApp() {
    	init();
    }
    
    /**
     * Template methods. Calls these methods in this order:
     * <ol>
     * <li>{@link #initContext(String)} with <code>"hello.xml"</code> as argument</li>
     * <li>{@link #readDatasource(String)} with <code>"ds.xml"</code> as argument</li>
     * </ol>
     * and finally refreshes the context.
     */
    protected void init() {
    	initContext("hello.xml");
    	readDatasource("ds.xml");
    	ctx.refresh();
    }

    /**
     * Initializes the context from XML file <code>ctxFile</code>.
     * 
     * @param ctxName
     */
    protected void initContext(String ctxName) {
    	ctx = new GenericApplicationContext();
        xmlReader = new XmlBeanDefinitionReader(ctx);
        xmlReader.loadBeanDefinitions(new ClassPathResource(ctxName));
    }
    
    /**
     * Reads bean <code>"dataSource"</code> from the specified external file.
     * 
     * @param dsFile relative path of the external file
     */
    protected void readDatasource(String dsFile) {
    	FileSystemResource deployFileDir = new FileSystemResource(".");
        logger.debug("deploying dataSource from path: " + deployFileDir.getFile().getAbsolutePath());
        xmlReader.loadBeanDefinitions(deployFileDir.createRelative(dsFile));
    }
    
    public void greet() {
    	GreetingService greetingService =
            (GreetingService) ctx.getBean("greeting");            
        greetingService.sayGreeting();
    }
    
    public void listPepleInCity(String city) {		
		PersonDao nameDao = (PersonDao) ctx.getBean("personDao");
		List<Person> matches = nameDao.findByCity(city);
		if (matches.size() == 0)
			logger.info("no people found in " + city);
		else
			for (Person p : matches) {
				logger.info("found person: " + p);
			}		
    }
    
    public void listPeple() {		
		PersonDao nameDao = (PersonDao) ctx.getBean("personDao");
		List<Person> matches = nameDao.findAll();
		if (matches.size() == 0)
			logger.info("no people found");
		else
			for (Person p : matches) {
				logger.info("found person: " + p);
			}		
    }
    
    public void addPerson(Person p) {
    	PersonDao nameDao = (PersonDao) ctx.getBean("personDao");
    	nameDao.insert(p);
    	logger.info("person added: " + p);
    }
    
    public void showPersonByNameAndSurname(String name, String surname) {
    	PersonDao nameDao = (PersonDao) ctx.getBean("personDao");
    	logger.info("found person: " + nameDao.findByNameAndSurname(name, surname));			
    }
    
    public static void main(String[] args) throws Exception {
        HelloApp app = new HelloApp();
        
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        app.greet();
        while (true) {
        	System.out.print("> ");
        	exec(in.readLine().split("\\s+"), app);
        }
    }

	private static void exec(String[] args, HelloApp app) {
		try {
			if ("greet".equals(args[0]))
				app.greet();
			else if ("list".equals(args[0])) {
				if (args.length > 1)
					app.listPepleInCity(args[1]);
				else
					app.listPeple();
			}
			else if ("show".equals(args[0]))
				app.showPersonByNameAndSurname(args[1], args[2]);
			else if ("add".equals(args[0]))
				app.addPerson(new Person(args[1], args[2], args[3], Integer.parseInt(args[4])));
			else if ("quit".equals(args[0])) {
				logger.info("bye.");
				System.exit(0);
			}
			else if ("help".equals(args[0])) {
				logger.info(SYNTAX);
			}
			else {
				logger.error("Invalid command: '" + dumpCommands(args) + '\'');
				logger.info(SYNTAX);
			}
		}
        catch (ArrayIndexOutOfBoundsException e) {
			logger.error("Bad syntax.\n" + SYNTAX);
		}
        catch (NumberFormatException e) {
        	logger.error("Invalid integer", e);
        }
	}

	private static String dumpCommands(String[] args) {
		StringBuilder sb = new StringBuilder(100);
		boolean first = true;
		for (String str : args) {
			if (!first ) sb.append(' ');
			else first = false;
			sb.append(str);
		}
		return sb.toString();
	}
    
}