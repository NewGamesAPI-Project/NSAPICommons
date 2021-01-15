package net.cg360.nsapi.commons.math;

import javafx.geometry.Pos;

/**
 * A simple position + rotation object.
 *
 * @author CG360
 */
public class PosRot {

    protected double x;
    protected double y;
    protected double z;

    protected double pitch;
    protected double yaw;

    protected boolean shouldOffsetCenter;

    public PosRot(double x, double y, double z, double pitch, double yaw, boolean shouldOffsetCenter) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
        this.shouldOffsetCenter = shouldOffsetCenter;
    }

    public PosRot add(int pitch, int yaw) { return add(0, 0, 0, pitch, yaw); }
    public PosRot add(int xyz) { return add(xyz, xyz, xyz, 0, 0); }
    public PosRot add(int x, int y, int z) { return add(x, y, z, 0, 0); }
    public PosRot add(PosRot posRot){ return add(posRot.x, posRot.y, posRot.z, posRot.pitch, posRot.yaw); }
    public PosRot add(double x, double y, double z, double pitch, double yaw){
        return new PosRot(this.x + x, this.y + y, this.z + z, this.pitch + pitch, this.yaw + yaw, this.shouldOffsetCenter);
    }

    public PosRot sub(int pitch, int yaw) { return sub(0, 0, 0, pitch, yaw); }
    public PosRot sub(int xyz) { return sub(xyz, xyz, xyz, 0, 0); }
    public PosRot sub(int x, int y, int z) { return sub(x, y, z, 0, 0); }
    public PosRot sub(PosRot posRot){ return sub(posRot.x, posRot.y, posRot.z, posRot.pitch, posRot.yaw); }
    public PosRot sub(double x, double y, double z, double pitch, double yaw){ return add(-x, -y, -z, -pitch, -yaw); }

    public PosRot mult(int pitch, int yaw) { return mult(1, 1, 1, pitch, yaw); }
    public PosRot mult(int xyz) { return mult(xyz, xyz, xyz, 1, 1); }
    public PosRot mult(int x, int y, int z) { return mult(x, y, z, 1, 1); }
    public PosRot mult(PosRot posRot){ return mult(posRot.x, posRot.y, posRot.z, posRot.pitch, posRot.yaw); }
    public PosRot mult(double x, double y, double z, double pitch, double yaw){
        return new PosRot(this.x * x, this.y * y, this.z * z, this.pitch * pitch, this.yaw * yaw, this.shouldOffsetCenter);
    }

    public PosRot div(int pitch, int yaw) { return div(1, 1, 1, pitch, yaw); }
    public PosRot div(int xyz) { return div(xyz, xyz, xyz, 1, 1); }
    public PosRot div(int x, int y, int z) { return div(x, y, z, 1, 1); }
    public PosRot div(PosRot posRot){ return div(posRot.x, posRot.y, posRot.z, posRot.pitch, posRot.yaw); }
    public PosRot div(double x, double y, double z, double pitch, double yaw){
        return new PosRot(this.x * (1/x), this.y * (1/y), this.z * (1/z), this.pitch * (1/pitch), this.yaw * (1/yaw), this.shouldOffsetCenter);
    }

    /**
     * @return a PosRot where the position is offset by +0.5 in the xyz space.
     */
    public PosRot getWithCenteringOffset(){
        return new PosRot(this.x, this.y, this.z, this.pitch, this.yaw, true);
    }


    public double getX() { return x; }
    public double getY() { return y; }
    public double getZ() { return z; }
    public double getPitch() { return pitch; }
    public double getYaw() { return yaw; }
    public boolean shouldOffsetCenter() { return shouldOffsetCenter; }
}
