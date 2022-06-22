package com.entity;

import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Scanner;
@Data
@Entity(name = "Route")
public class Route {

    private static int maTuyenLast;
    @Id
    @Column(name = "id")
    private int maTuyen;
    @Column(name = "Distance")
    private float khoangCach;
    @Column(name = "numberofstops")
    private int soDiemDung;
    @OneToMany(mappedBy = "route")
    List<Assignment> assignments;

    public Route() {
        maTuyenLast ++;
        this.maTuyen = maTuyenLast;
    }

    public static int getMaTuyenLast() {
        return maTuyenLast;
    }

    public static void setMaTuyenLast(int maTuyenLast) {
        Route.maTuyenLast = maTuyenLast;
    }

    public void inputInfo(){

        while (true){
            System.out.println("Nhập khoảng cách cho tuyến: ");
            try {
                this.khoangCach = Float.parseFloat(new Scanner(System.in).next());
                break;
            }catch (NumberFormatException e){
                System.out.println("Khoảng cách cho tuyến phải là 1 số, mời nhập lại: ");
            }
        }
        while (true){
            System.out.println("Nhập số điểm dừng cho tuyến: ");
            try {
                this.soDiemDung = Integer.parseInt(new Scanner(System.in).next());
                break;
            }catch (NumberFormatException e){
                System.out.println("Số điểm dừng cho tuyến phải là 1 số nguyên, mời nhập lại: ");
            }
        }

    }

    @Override
    public String toString() {
        return "Route{ Mã tuyến: " + maTuyen +", Khoảng cách: "+ khoangCach + ", Số điểm dừng: "+soDiemDung +"}";
    }

    @Override
    public int hashCode() {
        return this.getMaTuyen();
    }
}
