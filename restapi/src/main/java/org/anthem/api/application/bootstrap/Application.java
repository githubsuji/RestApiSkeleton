package org.anthem.api.application.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author SUJIKUMAR
 * 
 */
@SpringBootApplication(scanBasePackages={"org.anthem.api"})

public class Application 
{
    public static void main( String[] args )
    {
    
    	SpringApplication.run(Application.class, args);
    }
    
    
}
