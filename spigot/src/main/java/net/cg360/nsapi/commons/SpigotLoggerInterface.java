package net.cg360.nsapi.commons;

import net.cg360.nsapi.commons.io.CommonLog;

public class SpigotLoggerInterface extends CommonLog {

    @Override public void debug(String source, String message) { SpigotCommons.get().getLogger().fine(String.format("[%s]: %s", source, message)); }
    @Override public void info(String source, String message) { SpigotCommons.get().getLogger().info(String.format("[%s]: %s", source, message)); }
    @Override public void warn(String source, String message) { SpigotCommons.get().getLogger().warning(String.format("[%s]: %s", source, message)); }
    @Override public void error(String source, String message) { SpigotCommons.get().getLogger().severe(String.format("[%s]: %s", source, message)); }

}
