package net.cg360.nsapi.commons.io;

public abstract class CommonLog {

    protected static CommonLog inst = null;

    public void debug(String message){ debug("Anonymous", message); }
    public void info(String message){ info("Anonymous", message); }
    public void warn(String message){ warn("Anonymous", message); }
    public void error(String message){ error("Anonymous", message); }

    public abstract void debug(String source, String message);
    public abstract void info(String source, String message);
    public abstract void warn(String source, String message);
    public abstract void error(String source, String message);

    public void setAsMain() {
        inst = this;
    }

    public static CommonLog get() { return inst == null ? new DummyLog() : inst; }
}
