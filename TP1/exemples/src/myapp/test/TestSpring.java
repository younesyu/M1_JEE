package myapp.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import myapp.services.ICalculator;
import myapp.services.ILogger;


public class TestSpring {


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
        String conf = "config-logger.xml";

        // create a context and find beans
        try (AbstractApplicationContext ctx = new ClassPathXmlApplicationContext(conf);) {
            ILogger logger = ctx.getBean("stderrLogger", ILogger.class);
            use(logger);
        }
    }

    @Test
    public void testCalculatorWithLogger() {
        System.err.println("+++ CalculatorWithLogger");
        String conf = "config-logger.xml";

        // create a context and find beans
        try (AbstractApplicationContext ctx = new ClassPathXmlApplicationContext(conf);) {
            ICalculator calc = ctx.getBean("calculator", ICalculator.class);
            use(calc);
        }
    }
    
    @Test
    public void testCalculatorWithFileLogger() {
        System.err.println("+++ CalculatorWithFileLogger");
        String conf = "config-logger.xml";

        // create a context and find beans
        try (AbstractApplicationContext ctx = new ClassPathXmlApplicationContext(conf);) {
            ICalculator calc = ctx.getBean("calculatorFileLogger", ICalculator.class);
            use(calc);
        }
    }

}
