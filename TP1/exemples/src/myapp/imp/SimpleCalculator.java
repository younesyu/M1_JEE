package myapp.imp;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import myapp.services.ICalculator;
import myapp.services.ILogger;

@Service("calculator")
public class SimpleCalculator implements ICalculator {
    
	private ILogger logger;

	@PostConstruct
    public void start() {
        if (logger == null) {
            throw new IllegalStateException("null logger");
        }
        System.err.println("Start " + this);
    }

    @PreDestroy
    public void stop() {
        System.err.println("Stop " + this);
    }

    public int add(int a, int b) {
        logger.log(String.format("add(%d,%d)", a, b));
        return (a + b);
    }

    public ILogger getLogger() {
        return logger;
    }

    @Autowired
    @Qualifier("test")
    public void setLogger(ILogger logger) {
        this.logger = logger;
    }
}
