package net.cg360.nsapi.commons;

public abstract class CommonLog {

    protected static CommonLog inst = null;

    public abstract void debug(String message);
    public abstract void info(String message);
    public abstract void warn(String message);
    public abstract void error(String message);

    public void setAsMain() {
        inst = this;
    }

    public static CommonLog get() { return inst == null ? new DummyLog() : inst; }
}
