package net.cg360.nsapi.commons;

import net.cg360.nsapi.commons.event.NSEventManager;
import net.cg360.nsapi.commons.io.CommonLog;
import net.cg360.nsapi.commons.scheduler.NSSyncScheduler;
import net.cg360.nsapi.commons.scheduler.ServerSchedulerBridge;
import net.cg360.nsapi.commons.util.Check;

public final class CommonsAPI {

    protected static CommonsAPI apiInstance;

    protected CommonLog logger;
    protected ServerSchedulerBridge schedulerBridge;

    protected NSSyncScheduler mainScheduler;
    protected NSEventManager mainEventManager;


    public CommonsAPI(CommonLog logger, ServerSchedulerBridge schedulerBridge, NSSyncScheduler scheduler, NSEventManager eventManager) {
        Check.nullParam(logger, "logger");
        Check.nullParam(schedulerBridge, "schedulerBridge");
        Check.nullParam(scheduler, "scheduler");
        Check.nullParam(eventManager, "eventManager");

        this.logger = logger;
        this.schedulerBridge = schedulerBridge;
        this.mainScheduler = scheduler;
        this.mainEventManager = eventManager;
    }

    /** Sets the primary instance of the api. */
    public void setAsPrimaryAPI() {

        if(apiInstance == null) {
            apiInstance = this;

            this.logger.setAsMain();
            this.mainScheduler.setAsPrimaryInstance();
            this.mainEventManager.setAsPrimaryManager();
        }
    }



    public CommonLog getLogger() { return logger; }
    public ServerSchedulerBridge getSchedulerBridge() { return schedulerBridge; }

    public NSEventManager getMainEventManager() { return mainEventManager; }
    public NSSyncScheduler getMainScheduler() { return mainScheduler; }

    /** Get the primary instance of the api. */
    public static CommonsAPI get() { return apiInstance; }
}
