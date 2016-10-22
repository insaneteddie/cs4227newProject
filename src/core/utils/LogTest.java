
package core.utils;

import org.junit.Before;

import static junit.framework.Assert.*;


/**
 * Created by David on 21/10/2016.
 */

public class LogTest {
    public final Log log = new Log(getClass().getName());
    @org.junit.Test
    public void logException()  {
        log.logException(new Exception("This is a test exception A"));
    }

    @org.junit.Test
    public void logException1()  {
        log.logException("Two string argument test",new Exception("This is a test exception B"));
    }

    @org.junit.Test
    public void logException2()  {
        log.logException("This is a test exception C");
    }

}
