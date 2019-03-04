package myapp.imp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
	
	@Bean
    public BeanFileLogger myBeanFileLogger() {
        return new BeanFileLogger();
    }
}
