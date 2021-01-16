package net.cg360.nsapi.commons.math;

import net.cg360.nsapi.commons.math.PosRot;

/**
 * Stores the data that a functional MapRegion implementation may use.
 * Examples of implementation can be seen in NGAPI 2 (And NGAPI 1 with an older
 * format)
 *
 * @author CG360
 */
public class Region {

    protected PosRot pointMin;
    protected PosRot pointMax;

    public Region(PosRot one, PosRot two) {
        if(one == null) throw new IllegalArgumentException("First corner of MapRegion must not be null");
        if(two == null) throw new IllegalArgumentException("Second corner of MapRegion must not be null");

        this.pointMin = one;
        this.pointMax = two;
        this.orderCorners();
    }


    protected void orderCorners() {
        double x1, y1, z1, x2, y2, z2;
        boolean center = pointMin.shouldOffsetCenter() || pointMax.shouldOffsetCenter();

        if(pointMin.getX() < pointMax.getX()){
            x1 = pointMin.getX();
            x2 = pointMax.getX();
        } else {
            x1 = pointMax.getX();
            x2 = pointMax.getX();
        }

        if(pointMin.getY() < pointMax.getY()){
            y1 = pointMin.getY();
            y2 = pointMax.getY();
        } else {
            y1 = pointMax.getY();
            y2 = pointMax.getY();
        }

        if(pointMin.getZ() < pointMax.getZ()){
            z1 = pointMin.getZ();
            z2 = pointMax.getZ();
        } else {
            z1 = pointMax.getZ();
            z2 = pointMax.getZ();
        }

        this.pointMin = new PosRot(x1, y1, z1, 0, 0, center);
        this.pointMax = new PosRot(x2, y2, z2, 0, 0, center);
    }

    public boolean isPositionWithinRegion(int x, int y, int z){ return isPositionWithinRegion(new PosRot(x, y, z, 0, 0, false)); }
    public boolean isPositionWithinRegion(PosRot posRot) {
        double x = posRot.getX(), y = posRot.getY(), z = posRot.getZ();

        if( (x >= pointMin.getX()) && (x <= pointMax.getX()) ) return true;
        if( (y >= pointMin.getY()) && (y <= pointMax.getY()) ) return true;
        if( (z >= pointMin.getZ()) && (z <= pointMax.getZ()) ) return true;

        return false;
    }

    public PosRot getPointMin() { return pointMin; }
    public PosRot getPointMax() { return pointMax; }
}
