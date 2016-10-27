package core.utils;
/**
 * Created by David on 21/10/2016.
 */

public class LogTest {
    /** Log object used in each junit test*/
    public final Log log = new Log(getClass().getName());
    /** test Log.java, logWarning(Exception e)*/
    @org.junit.Test
    public void logException()  {
        log.logWarning(new Exception("This is a test exception A"));
    }

    /** test Log.java, logWarning(Exception e, String desc)*/
    @org.junit.Test
    public void logException1()  {
        log.logWarning(new Exception("This is a test exception B"),"Two string argument test");
    }

    /** test Log.java, logInfo(String desc)*/
    @org.junit.Test
    public void logException2()  {
        log.logInfo("This is a test exception C");
    }

}
