package testing.core.interceptor;

import core.interceptor.SimpleLog;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;

/**
 * Created by Cian Bolster on 09/11/2016.
 */
public class SimpleLogTest {
    @Test
    public void checkValidityOfMessageCreation(){
        Exception ex = new FileNotFoundException();
        SimpleLog log = new SimpleLog(1);
        String messageToValidate = "Testing";
        StringBuilder builder = new StringBuilder();
        builder.append(ex).append(":\t").append(messageToValidate);
        String expected = builder.toString();
        
        String result = log.messageCreation(ex, messageToValidate);

        Assert.assertEquals(expected, result);

    }
}