package com.entity;

import lombok.Data;
import javax.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;
@Data

@Entity(name = "Driver")
public class Driver {
    private static int maLxLast;
    @Id
    @Column(name = "id")

    private int maLx;
    @Column(name = "fullname")
    private String name;
    @Column(name = "address")
    private String address;
    @Column(name = "phone")
    private long phoneNum;
    @Column(name = "levele")
    private String trinhDo;

    public Driver() {
        this.maLxLast ++;
        this.maLx = maLxLast;
    }
    public static int getMaLxLast() {
        return maLxLast;
    }
    public static void setMaLxLast(int maLxLast) {
        Driver.maLxLast = maLxLast;
    }
    @OneToMany(mappedBy = "driver")
    List<Assignment> assignments;


    public void inputInfo(){
        System.out.println("Nhập tên cho lái xe: ");
        this.name = new Scanner(System.in).nextLine();
        System.out.println("Nhập số địa chỉ cho lái xe: ");
        this.address = new Scanner(System.in).nextLine();

        while (true){
            System.out.println("Nhập số điện thoại cho lái xe: ");
            try {
                this.phoneNum = Long.parseLong(new Scanner(System.in).next());
                break;
            }catch (NumberFormatException e){
                System.out.println("Số điện thoại là chuỗi số có 10 chữ số, mời nhập lại: ");
            }
        }
        while (true){
            System.out.println("Nhập trình độ lái xe: ");
            System.out.println("1. Trình độ A");
            System.out.println("2. Trình độ B");
            System.out.println("3. Trình độ C");
            System.out.println("4. Trình độ D");
            System.out.println("5. Trình độ E");
            System.out.println("6. Trình độ F");

            int level;
            try {
                level = Integer.parseInt(new Scanner(System.in).next());
                switch (level){
                    case 1:
                        this.trinhDo = "A";
                        break;
                    case 2:
                        this.trinhDo = "B";
                        break;
                    case 3:
                        this.trinhDo = "C";
                        break;
                    case 4:
                        this.trinhDo = "D";
                        break;
                    case 5:
                        this.trinhDo = "E";
                        break;
                    case 6:
                        this.trinhDo = "F";
                        break;

                }
                if(level <=6 && level >=1){
                    break;
                }
                System.out.println("Chỉ nhập từ 1 đến 6, mới nhập lại: ");
            } catch (NumberFormatException e){
                System.out.println("Chỉ nhập số, mời nhập lại");
            }


        }

    }
    @Override
    public String toString() {
        return "Driver{ MaLx: "+maLx+", Name: "+ name +", Address: "+ address + ", Phonenum: "+phoneNum+" }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Driver)) return false;
        Driver driver = (Driver) o;
        return getMaLx() == driver.getMaLx();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMaLx());
    }
}

