package net.cg360.nsapi.commons;

import net.cg360.nsapi.commons.CommonLog;

public class NukkitLoggerInterface extends CommonLog {

    @Override public void debug(String source, String message) { NukkitCommons.get().getLogger().debug(String.format("[%s]: %s", source, message)); }
    @Override public void info(String source, String message) { NukkitCommons.get().getLogger().info(String.format("[%s]: %s", source, message)); }
    @Override public void warn(String source, String message) { NukkitCommons.get().getLogger().warning(String.format("[%s]: %s", source, message)); }
    @Override public void error(String source, String message) { NukkitCommons.get().getLogger().error(String.format("[%s]: %s", source, message)); }

}
