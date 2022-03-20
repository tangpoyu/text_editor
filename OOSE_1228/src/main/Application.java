package main;

import Proxy.ProtectionProxy;
import pattern.WinWindowImp;
import pattern.WindowImp;

public class Application
{
    public static final WindowImp impl = new WinWindowImp();
    public static void main(String[] args) {
        ProtectionProxy protectionProxy=new ProtectionProxy(impl);
        protectionProxy.run();
    }
}
