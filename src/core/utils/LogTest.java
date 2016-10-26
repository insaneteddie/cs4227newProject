package core.utils;
/**
 * Created by David on 21/10/2016.
 */

public class LogTest {
    public final Log log = new Log(getClass().getName());
    @org.junit.Test
    public void logException()  {
        log.logWarning(new Exception("This is a test exception A"));
    }

    @org.junit.Test
    public void logException1()  {
        log.logWarning(new Exception("This is a test exception B"),"Two string argument test");
    }

    @org.junit.Test
    public void logException2()  {
        log.logInfo("This is a test exception C");
    }

}
