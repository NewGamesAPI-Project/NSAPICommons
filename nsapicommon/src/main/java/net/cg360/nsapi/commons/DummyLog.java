package net.cg360.nsapi.commons;

/** Does nothing **/
public class DummyLog extends CommonLog {

    @Override public void debug(String message) { }
    @Override public void info(String message) { }
    @Override public void warn(String message) { }
    @Override public void error(String message) { }

}
