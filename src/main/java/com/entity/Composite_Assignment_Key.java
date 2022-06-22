package com.entity;

import java.io.Serializable;
import java.util.Objects;


public class Composite_Assignment_Key implements Serializable{

    private Driver driver;
    private Route route;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Composite_Assignment_Key)) return false;
        Composite_Assignment_Key compositeAssignmentKey = (Composite_Assignment_Key) o;
        return driver.equals(compositeAssignmentKey.driver) && route.equals(compositeAssignmentKey.route);
    }

    @Override
    public int hashCode() {
        return Objects.hash(driver, route);
    }
    /** getters and setters **/
}
