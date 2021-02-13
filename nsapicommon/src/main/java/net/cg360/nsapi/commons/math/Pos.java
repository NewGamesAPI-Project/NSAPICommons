package net.cg360.nsapi.commons.math;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 * A simple position + rotation object.
 * @author CG360
 */
public class Pos {

    protected double x;
    protected double y;
    protected double z;

    protected boolean shouldOffsetCenter;

    public Pos(double x, double y, double z, boolean shouldOffsetCenter) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.shouldOffsetCenter = shouldOffsetCenter;
    }

    public static Pos parseFromJson(JsonObject root) {
        double oX = 0, oY = 0, oZ = 0;
        boolean oOffsetCenter = true;

        JsonElement eX = root.get("x");
        JsonElement eY = root.get("y");
        JsonElement eZ = root.get("z");
        JsonElement eOffsetCenter = root.get("offset_center");

        if(eX instanceof JsonPrimitive){
            JsonPrimitive p = (JsonPrimitive) eX;
            if(p.isNumber()) oX = p.getAsNumber().doubleValue();
        }

        if(eY instanceof JsonPrimitive){
            JsonPrimitive p = (JsonPrimitive) eY;
            if(p.isNumber()) oY = p.getAsNumber().doubleValue();
        }

        if(eZ instanceof JsonPrimitive){
            JsonPrimitive p = (JsonPrimitive) eZ;
            if(p.isNumber()) oZ = p.getAsNumber().doubleValue();
        }

        if(eOffsetCenter instanceof JsonPrimitive){
            JsonPrimitive p = (JsonPrimitive) eOffsetCenter;
            if(p.isBoolean()) oOffsetCenter = p.getAsBoolean();
        }

        return new Pos(oX, oY, oZ, oOffsetCenter);
    }

    public Pos add(int xyz) { return add(xyz, xyz, xyz); }
    public Pos add(Pos pos){ return add(pos.x, pos.y, pos.z); }
    public Pos add(double x, double y, double z){
        return new Pos(this.x + x, this.y + y, this.z + z, this.shouldOffsetCenter);
    }

    public Pos sub(int xyz) { return sub(xyz, xyz, xyz); }
    public Pos sub(Pos pos){ return sub(pos.x, pos.y, pos.z); }
    public Pos sub(double x, double y, double z){ return add(-x, -y, -z); }

    public Pos mult(int xyz) { return mult(xyz, xyz, xyz); }
    public Pos mult(Pos pos){ return mult(pos.x, pos.y, pos.z); }
    public Pos mult(double x, double y, double z){
        return new Pos(this.x * x, this.y * y, this.z * z, this.shouldOffsetCenter);
    }

    public Pos div(int xyz) { return div(xyz, xyz, xyz); }
    public Pos div(Pos pos){ return div(pos.x, pos.y, pos.z); }
    public Pos div(double x, double y, double z){
        return new Pos(this.x * (1/x), this.y * (1/y), this.z * (1/z), this.shouldOffsetCenter);
    }

    /**
     * @return a PosRot where the position is offset by +0.5 in the xyz space.
     */
    public Pos getWithCenteringOffset(){
        return new Pos(this.x, this.y, this.z, true);
    }


    public double getX() { return shouldOffsetCenter ? x + 0.5 : x; }
    public double getY() { return shouldOffsetCenter ? y + 0.5 : y; }
    public double getZ() { return shouldOffsetCenter ? z + 0.5 : z; }
    public double getActualX() { return x; }
    public double getActualY() { return y; }
    public double getActualZ() { return z; }
    public boolean shouldOffsetCenter() { return shouldOffsetCenter; }
}
