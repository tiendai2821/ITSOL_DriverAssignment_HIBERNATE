package com.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity(name = "assignments")
@EqualsAndHashCode
@IdClass(Composite_Assignment_Key.class)
@NoArgsConstructor
public class Assignment{

    @ManyToOne
    @Id
    @JoinColumn(name = "driver_id")
    Driver driver;
    @ManyToOne
    @Id
    @JoinColumn(name = "route_id")
    Route route;
    @Column(name = "soluot")
    private int soLuot;

    public Assignment(Driver driver, Route route, int turnNum) {
        this.route = route;
        this.driver = driver;
        this.soLuot = turnNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Assignment)) return false;
        Assignment that = (Assignment) o;
        return getDriver().equals(that.getDriver());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDriver());
    }
}
