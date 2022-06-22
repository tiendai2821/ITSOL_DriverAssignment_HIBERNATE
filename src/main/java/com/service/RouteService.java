package com.service;


import com.app.MainRun;
import com.dao.RouteDAO;
import com.entity.Route;

import java.util.Scanner;

public class RouteService {
    public void addRoute(){
        if(MainRun.routeList.isEmpty()){
            Route.setMaTuyenLast(99);
        }else {
            Route.setMaTuyenLast(MainRun.routeList.get(MainRun.routeList.size()-1).getMaTuyen());
        }
        int numRoute;
        System.out.println("Nhập số lượng tuyến muốn thêm: ");
        while(true){
            try{
                numRoute = Integer.parseInt(new Scanner(System.in).next());
                break;
            }catch(NumberFormatException e){
                System.out.println("Chỉ nhập số, mời nhập lại số lượng tuyến: ");
            }
        }
        for(int i = 0 ; i<numRoute; i++){
            Route route = new Route();
            route.inputInfo();
            MainRun.routeList.add(route);
            RouteDAO routeDAO = new RouteDAO();
            routeDAO.saveRoute(route);
        }
//        FileUtil fileUtil = new FileUtil();
//        fileUtil.writeDataToFile(MainRun.routeList,MainRun.routeFile);
    }
    public void showRouteTable(){
        for(Route r:MainRun.routeList){
            System.out.println(r);
        }
    }
}
