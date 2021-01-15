package net.cg360.nsapi.commons.data;

import net.cg360.nsapi.commons.math.PosRot;

public class PointEntityDataStore extends PosRot {

    protected String identifier;

    protected PointEntityDataStore(double x, double y, double z, double pitch, double yaw, boolean shouldOffsetCenter) {
        super(x, y, z, pitch, yaw, shouldOffsetCenter);
    }

}
