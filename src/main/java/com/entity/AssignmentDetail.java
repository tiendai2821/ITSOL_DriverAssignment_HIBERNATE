package com.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class AssignmentDetail{
    private Driver driver;
    private Map<Route,Integer> routeMap;
    private int  turnTotal = 0;
    private float distanceTotal = 0;
    public AssignmentDetail(Driver driver, Map<Route,Integer> routeMap) {
        this.driver = driver;
        this.routeMap = routeMap;
        Set<Route> set = this.routeMap.keySet();
        for(Route key:set){
            this.turnTotal+= this.routeMap.get(key);
            this.distanceTotal += key.getKhoangCach()*this.routeMap.get(key);
        }
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Map<Route, Integer> getRouteMap() {
        return routeMap;
    }

    public void setRouteMap(Map<Route, Integer> routeMap) {
        this.routeMap = routeMap;
    }

    public int getTurnTotal() {
        return turnTotal;
    }

    public void setTurnTotal(int turnTotal) {
        this.turnTotal = turnTotal;
    }

    public float getDistanceTotal() {
        return distanceTotal;
    }

    public void setDistanceTotal(float distanceTotal) {
        this.distanceTotal = distanceTotal;
    }

    @Override
    public String toString() {
        Set<Route> set = this.routeMap.keySet();
        String danhsachtuyen ="";
        for(Route r:set){
            String etuyen ="("+ r.hashCode()+",số lượt: "+ this.routeMap.get(r)+")";
            danhsachtuyen += etuyen;

        }

        return "Assignment{" +
                "driver_id=" + driver.getMaLx() +
                ", tên lái xe="+driver.getName()+
                ", danh sách các tuyến [" + danhsachtuyen+"]"+
                '}';
    }


}

