package net.cg360.nsapi.commons.data;

import net.cg360.nsapi.commons.math.PosRot;

import java.util.Map;

/**
 * Store's the core data of a point entity.
 * @author CG360
 */
public class PointEntityDataStore extends PosRot {

    protected String identifier;
    protected String type;

    protected Map<String, String> strings;
    protected Map<String, Number> numbers;
    protected Map<String, Boolean> switches;

    protected PointEntityDataStore(double x, double y, double z, double pitch, double yaw, boolean shouldOffsetCenter) {
        super(x, y, z, pitch, yaw, shouldOffsetCenter);
    }

}
