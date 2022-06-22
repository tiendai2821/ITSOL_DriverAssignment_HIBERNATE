package com.service;


import com.app.MainRun;
import com.dao.AssignmentDAO;
import com.entity.Assignment;
import com.entity.AssignmentDetail;
import com.entity.Driver;
import com.entity.Route;

import java.util.*;

public class AssignmentDetailService implements Sort,Statistic{



    // tìm kiếm assignment i theo id driver
    // for(j in i.service
    public void  addAssignment(){
        // check thông tin 2 bảng thông tin
        if(checkData() == false){
            System.out.println("Chưa có thông tin bảng lái xe hoặc bảng tuyến xe, vui lòng kiểm tra lại");
            return;
        }
        int driverNum = -1;
        while (true){
            System.out.println("Mời nhập số lượng lái xe muốn thêm vào danh sách quản lý: ");
            try {
                driverNum = Integer.parseInt(new Scanner(System.in).next());
                if(driverNum > 0){
                    break;
                }else {
                    System.out.println("Số lượng lái xe phải là số dương, mời nhập lại");
                }
            }catch (NumberFormatException e){
                System.out.println("Số lượng lái xe phải là 1 số, mời nhập lại: ");
            }
        }
        for(int i = 0; i<driverNum; i++){
            int driverId;
            int routeNum;
            Driver driver;
            System.out.println("Nhập thông tin cho lái xe thứ "+ (i+1) +" :" );
            while(true){
                try {
                    System.out.println("Nhập id cho lái xe thứ "+ (i+1)+" :");
                    driverId = Integer.parseInt(new Scanner(System.in).next());
                    driver  = findDriverById(driverId);
                    if (driver== null){
                        System.out.println("Không tìm thấy id lái xe, mời nhập lại");
                    }
                    else break;

                }catch (NumberFormatException e){
                    System.out.println("Id lái xe là 1 số nguyên có 5 chữ số, mời nhập lại");
                }
            }
            while(true){
                try {
                    System.out.println("Nhập số tuyến cho lái xe thứ "+ (i+1) +" :" );
                    routeNum= Integer.parseInt(new Scanner(System.in).next());
                    if(routeNum >0 ) break;
                    System.out.println("Số tuyến phải lớn hơn 0, mời nhập lại: ");

                }catch (NumberFormatException e){
                    System.out.println("Số tuyến phải là 1 số nguyên >0, mời nhập lại:");
                }
            }
            for(int j = 0; j<routeNum; j++){
                int routeId;
                Route route;
                while(true){
                    try {
                        System.out.println("Nhập id cho tuyến thứ "+ (j+1) +" :" );
                        routeId= Integer.parseInt(new Scanner(System.in).next());
                        route  = findRouteById(routeId);
                        if (route== null) {
                            System.out.println("Không tìm thấy mã tuyến");
                        }else if(checkSameData(driverId,routeId)){
                            System.out.println("Mã tuyến bị trùng, mời nhập lại mã tuyến khác.");
                        } else break;
                    }catch (NumberFormatException e){
                        System.out.println("Mã tuyến là 1 số nguyên có 3 chữ số, mời nhập lại");
                    }
                }
                if(getIdexAssignmentFromArr(driverId) !=-1){
// danh sach la toan bo list bao gom tong so luot
                    // có giá trị assignment, bổ sung
                    int index = getIdexAssignmentFromArr(driverId);
                    int turnTotal = MainRun.assignmentDetailList.get(index).getTurnTotal();
                    int turnNum = 0;
                    while(true){
                        try {
                            System.out.println("Mời nhập số lượt cho tuyến thứ "+ (j+1)+" :");
                            turnNum = Integer.parseInt(new Scanner(System.in).next());
                            if(turnNum+turnTotal >15){
                                System.out.println("Số lượt trong ngày cho lái xe không được vượt quá 15, mời nhập lại: ");
                            } else if (turnNum == 0) {
                                System.out.println("Số lượt cho mỗi tuyến không được phép <=0, mời nhập lại: ");
                            } else break;
                        }catch (NumberFormatException e){
                            System.out.println("Số lượt phải là 1 số, mời nhập lại:");
                        }
                    }
                    updateAssignment(driver,route,turnNum);
                }else {
                    // chưa có giá trị assignment, thêm mới
                    int turnNumber;
                    Map<Route,Integer> routeMap;
                    while (true){
                        System.out.println("Nhập số lượng lượt cho tuyến thứ "+(j+1)+" :");
                        try{
                            turnNumber = Integer.parseInt(new Scanner(System.in).next());
                            if(turnNumber<=15){
                                routeMap = new HashMap<>();
                                routeMap.put(route,turnNumber);
                                break;
                            }
                            System.out.println("Tổng số lượt cho mỗi tuyến không được vượt quá 15/ngày, mời nhập lại:");
                        }catch (NumberFormatException e){
                            System.out.println("Số lượng lượt phải là 1 số, mời nhập lại: ");
                        }
                    }
                    // 2 việc phải làm
//                    + them moi cho db
                    // them moi cho danh sach cua luong hien tai
                    AssignmentDetail assignmentDetail = new AssignmentDetail(driver,routeMap);
                    MainRun.assignmentDetailList.add(assignmentDetail);
                    AssignmentDAO assignmentDAO = new AssignmentDAO();
                    Assignment assignment = new Assignment(driver,route,turnNumber);
                    assignmentDAO.addNew(assignment);
                }

            }
        }

    }
    private boolean checkData(){
        if (MainRun.driverList.isEmpty() || MainRun.routeList.isEmpty())
            return false;
        return true;
    }
    public static Driver findDriverById(int DriverId){
        for(Driver driver:MainRun.driverList){
            if(driver.getMaLx() == DriverId){
                return driver;
            }
        }
        return null;
    }
    public static Route findRouteById(int routeId){
        for(Route route:MainRun.routeList){
            if(route.getMaTuyen() == routeId){
                return route;
            }
        }
        return null;
    }
    private boolean checkSameData(int driverId, int routeId){
        // neu ton tai id, chua ton tai tuyen => add tuyen
        //  neu ton tai id, da ton tai tuyen => da ton tai du dieu tuong tu
        for(AssignmentDetail a:MainRun.assignmentDetailList){
            if(a.getDriver().getMaLx() == driverId){
                Set<Route> set = a.getRouteMap().keySet();
                for(Route r:set){
                    if (r.getMaTuyen()== routeId){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private int getIdexAssignmentFromArr(int driverId){
        for(AssignmentDetail a:MainRun.assignmentDetailList){
            if(a.getDriver().getMaLx() == driverId){
                return MainRun.assignmentDetailList.indexOf(a);
            }
        }
        return -1;
    }

    private void updateAssignment(Driver driver , Route route, int turnNumber){
        for(AssignmentDetail a:MainRun.assignmentDetailList){
            if(a.getDriver().getMaLx() == driver.getMaLx()){
                a.getRouteMap().put(route,turnNumber);
            }
        }
        Assignment assignment = new Assignment(driver, route, turnNumber);
        AssignmentDAO assignmentDAO = new AssignmentDAO();
        assignmentDAO.addNew(assignment);
    }
    public void showAssignment(){
        for(AssignmentDetail a:MainRun.assignmentDetailList){
            System.out.println(a);
        }
    }

/// sort in conllection

    @Override
    public void sortByName() {
        Collections.sort(MainRun.assignmentDetailList,(AssignmentDetail a1, AssignmentDetail a2)-> a1.getDriver().getName().compareTo(a2.getDriver().getName()));
    }

    @Override
    public void sortByQuantity() {
        Collections.sort(MainRun.assignmentDetailList,(AssignmentDetail a1, AssignmentDetail a2)-> a2.getRouteMap().size()-a1.getRouteMap().size());
    }

    @Override
    public void statisticByDistance() {
        for(AssignmentDetail a:MainRun.assignmentDetailList){
            System.out.println("Tài xế:"+ a.getDriver().getMaLx()+ "--Tổng khoảng cách chay trong ngày="+a.getDistanceTotal());
        }
    }

}

