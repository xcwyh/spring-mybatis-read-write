package com.xc.controller;

import com.xc.entity.Area;
import com.xc.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("areaController")
public class AreaController {

    @Autowired
    private AreaService areaService;

    @GetMapping("findById")
    public String findById() {
        Area area = areaService.selectByPrimaryKey(1);
        return area.getName();
    }

    @GetMapping("findByAbcd")
    public String findByAbcd() {
        return areaService.abcd();
    }

    @GetMapping("add")
    public String add() {
        Area area = new Area();
        area.setDistrictId("0000");
        area.setName("test");
        area.setParentId(0);
        area.setLevel(1);
        areaService.insert(area);
        return area.getName();
    }


    @GetMapping("addException")
    public String addException() {
        try {
            areaService.insertBack();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return areaService.count(new Area()) + "";
    }

}
