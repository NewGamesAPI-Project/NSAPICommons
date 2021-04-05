package net.cg360.nsapi.commons.io;

/** Does nothing **/
public class DummyLog extends CommonLog {


    @Override public void debug(String source, String message) { }
    @Override public void info(String source, String message) { }
    @Override public void warn(String source, String message) { }
    @Override public void error(String source, String message) { }

}
