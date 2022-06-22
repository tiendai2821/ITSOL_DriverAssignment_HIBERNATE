package com.app;


import com.dao.AssignmentDAO;
import com.dao.DriverDAO;
import com.dao.RouteDAO;
import com.entity.Assignment;
import com.entity.AssignmentDetail;
import com.entity.Driver;
import com.entity.Route;
import com.service.AssignmentDetailService;
import com.service.DriverService;
import com.service.RouteService;

import java.util.*;

public class MainRun {

    public static List<Driver> driverList;
    public static List<Route> routeList;
    public static List<Assignment> assignmentList;
    public static List<AssignmentDetail> assignmentDetailList;
    public static DriverService driverService = new DriverService();
    public static RouteService routeService = new RouteService();
    public static AssignmentDetailService assignmentDetailService = new AssignmentDetailService();

    public static void main(String[] args) {


        initData();
        Menu();
    }

    private static void initData() {
        DriverDAO driverDAO = new DriverDAO();
        driverList = driverDAO.driverGetAll();
        RouteDAO routeDAO = new RouteDAO();
        routeList = routeDAO.getAllRoute();
        AssignmentDAO assignmentDAO = new AssignmentDAO();
        assignmentList = assignmentDAO.getAll();
        assignmentDetailList = new ArrayList<>();
        Set<Assignment> setByDriver  = new HashSet<>(assignmentList);

        // đưa về cấu trúc 1 driver cùng 1 map route
        for (Assignment a:setByDriver){
            Map<Route,Integer> rm = new HashMap<>();
            for(Assignment i: assignmentList){
                if(i.getDriver() == a.getDriver()){

                    rm.put(i.getRoute(),i.getSoLuot());

                }
            }
            AssignmentDetail assignmentDetail = new AssignmentDetail(a.getDriver(),rm);
            assignmentDetailList.add(assignmentDetail);
        }

    }

    private static void Menu() {
        while (true) {
            int function = -1;
            try {

                while (true) {
                    System.out.println("\n\n-------Phần mềm quản lý phân công lái xe-------\n\n");
                    System.out.println("1.Nhập lái xe mới và in ra danh sách lái xe");
                    System.out.println("2.Nhập tuyến mới và in ra danh sách tuyến");
                    System.out.println("3.Nhập và in ra danh sách phân công cho mỗi lái xe");
                    System.out.println("4.Sắp xếp danh sách phân công theo họ tên lái xe");
                    System.out.println("5.Sắp xếp danh sách phân công theo số lượng tuyến");
                    System.out.println("6.Thống kê tổng khoảng cách chạy xe trong ngày của mỗi lái xe");
                    System.out.println("7.Thoát chương trình");
                    function = Integer.parseInt(new Scanner(System.in).next());
                    if (function <= 7 && function >= 1) {
                        break;
                    }
                    System.out.println("Chỉ nhập từ 1 đến 7, mời nhập lại");
                }


            } catch (NumberFormatException e) {
                System.out.println("Đầu vào là 1 số, mời nhập lại: ");
            }
            switch (function){
                case 1:
                    driverService.addDriver();
                    driverService.showDriverTable();
                    break;
                case 2:
                    routeService.addRoute();
                    routeService.showRouteTable();
                    break;
                case 3:
                    assignmentDetailService.addAssignment();
                    assignmentDetailService.showAssignment();
                    break;
                case 4:
                    assignmentDetailService.sortByName();
                    assignmentDetailService.showAssignment();
                    break;
                case 5:
                    assignmentDetailService.sortByQuantity();
                    assignmentDetailService.showAssignment();
                    break;
                case 6:
                    assignmentDetailService.statisticByDistance();
                    break;
                case 7:
                    System.out.println("Tks for use our system!!!");
                    return;
            }
        }
    }

}
