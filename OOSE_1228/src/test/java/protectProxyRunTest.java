package test.java;

import Proxy.ProtectionProxy;
import org.junit.Test;
import pattern.WinWindowImp;
import pattern.WindowImp;
public class protectProxyRunTest {
    boolean expected;
    ProtectionProxy protectionProxy;
    WindowImp impl;

    public void setUp(){
        impl = new WinWindowImp();
        protectionProxy = new ProtectionProxy(impl);
    }

    @Test
    public void allRight(){
        setUp();
        expected = true;
        boolean result = protectionProxy.run();
        assert( expected == result );
    }

    @Test
    public void accountIsWrong(){
        setUp();
        expected = false;
        boolean result = protectionProxy.run();
        assert( expected == result );
    }

    @Test
    public void passwordIsWrong(){
        setUp();
        expected = false;
        boolean result = protectionProxy.run();
        assert( expected == result );
    }

    @Test
    public void allWrong(){
        setUp();
        expected = false;
        boolean result = protectionProxy.run();
        assert( expected == result );
    }
}
