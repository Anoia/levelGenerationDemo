package com.stuckinadrawer;

import java.io.Serializable;

public class Point implements Serializable{
    private int x;
    private int y;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public boolean equals(Object obj){
        if (obj instanceof Point) {
            Point pos = (Point) obj;
            return (this.getX() == pos.getX()) && (this.getY() == pos.getY());
        }
        return super.equals(obj);
    }


    @Override
    public int hashCode() {
        long bits = Double.doubleToLongBits(getX());
        bits ^= Double.doubleToLongBits(getY()) * 31;
        return (((int) bits) ^ ((int) (bits >> 32)));
    }
}
