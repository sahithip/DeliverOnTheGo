package com.finalproject.deliveronthego;

/**
 * Created by Anita on 3/14/15.
 */
public class DeliveryDetails {
    private String pickUpLocation;
    private String dropOffLocation;
    private float length;
    private float width;
    private float breadth;

    public String getPickUpLocation() {
        return pickUpLocation;
    }

    public String getDropOffLocation() {
        return dropOffLocation;
    }

    public float getLength() {
        return length;
    }

    public float getWidth() {
        return width;
    }

    public void setPickUpLocation(String pickUpLocation) {
        this.pickUpLocation = pickUpLocation;
    }

    public void setDropOffLocation(String dropOffLocation) {
        this.dropOffLocation = dropOffLocation;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setBreadth(float breadth) {
        this.breadth = breadth;
    }

    public float getBreadth() {
        return breadth;
    }
}
