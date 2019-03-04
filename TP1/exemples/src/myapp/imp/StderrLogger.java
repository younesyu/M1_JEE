package myapp.imp;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import myapp.services.ILogger;

import java.util.Date;

@Service("stderrLogger")
@Qualifier("test")
public class StderrLogger implements ILogger {
	
	// start service
    @PostConstruct
    public void start() {
    	System.err.println("Start " + this);
    }

    // stop service
    @PreDestroy
    public void stop() {
        System.err.println("Stop " + this);
    }
	
    @Override
    public void log(String message) {
        System.err.printf("%tF %1$tR | %s\n", new Date(), message);
    }
    
}
