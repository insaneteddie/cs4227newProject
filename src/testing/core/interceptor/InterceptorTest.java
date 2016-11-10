package testing.core.interceptor;

import core.interceptor.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;

/**
 * Created by Christian on 09/11/2016.
 */
public class InterceptorTest {
    @Test
    public void TestInterceptorIsCreatedCorrectly()
    {
        Exception ex = new FileNotFoundException();
        String messageToValidate = "Testing";
        StringBuilder builder = new StringBuilder();
        builder.append(ex).append(":\t").append(messageToValidate);
        String expected = builder.toString();

        LogInterceptor interceptor = new LogInterceptor() {
            @Override
            public void onLogRequestReceived(LoggingRequest context) {
                Assert.assertEquals(expected, context.getFinalMessage());
            }
        };

        LogDispatcher.getInstance().registerLogRequestInterceptor(interceptor);
        LogDispatcher.getInstance().onLogRequestReceived(new ConcreteSimpleLoggingRequest(LoggingRequest.Severity.INFO, ex, messageToValidate));
    }
}
