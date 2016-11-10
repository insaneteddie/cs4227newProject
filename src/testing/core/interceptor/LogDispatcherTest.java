package testing.core.interceptor;

import core.interceptor.AbstractLoggingRequest;
import core.interceptor.ComplexLog;
import core.interceptor.LogDispatcher;
import core.interceptor.SimpleLog;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Christian on 09/11/2016.
 */
public class LogDispatcherTest {
    @Test
    public void CheckingChainValidity()
    {
        AbstractLoggingRequest logSimple = new SimpleLog(AbstractLoggingRequest.SIMPLELOG);
        AbstractLoggingRequest logComplex = new ComplexLog(AbstractLoggingRequest.COMPLEXLOG);
        logSimple.setNextInChain(logComplex);

        AbstractLoggingRequest testChain = LogDispatcher.getChain();

        Assert.assertEquals(logSimple.getNextInChain().getClass().toString(), testChain.getNextInChain().getClass().toString());

    }
}
