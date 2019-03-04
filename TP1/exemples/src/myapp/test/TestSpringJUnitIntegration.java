package myapp.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import myapp.imp.BeanFileLogger;
import myapp.imp.Config;
import myapp.services.ICalculator;
import myapp.services.ILogger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/config-logger.xml")
@Import(Config.class)
public class TestSpringJUnitIntegration {

    @Autowired
    @Qualifier("test")
    ILogger logger;
    
    @Autowired
    BeanFileLogger bfl;
    
    @Autowired
    ICalculator calc;

    @Before
    public void beforeEachTest() {
        System.err.println("====================");
    }

    void use(ILogger logger) {
        logger.log("Voila le résultat");
    }

    void use(ICalculator calc) {
        calc.add(100, 200);
    }

    @Test
    public void testStderrLogger() {
        System.err.println("+++ StderrLogger");
        use(logger);
    }
    
    @Test
    public void testBeanLogger() {
        System.err.println("+++ BeanLogger");
        use(bfl);
    }

    @Test
    public void testCalculatorWithLogger() {
        use(calc);
    }

}