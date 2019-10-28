package com.xc;

import com.xc.entity.Area;
import com.xc.service.AreaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApplication {

    @Autowired
    private AreaService areaService;


    //    @Test
    public void contextLoads() throws Exception {
        try {
            areaService.insertBack();

        } catch (Exception e) {
//            e.printStackTrace();
        }
        System.out.println(areaService.count(new Area()));

    }

    //    @Test
    public void test() {
        Area area = new Area();
        area.setDistrictId("0000");
        area.setName("test");
        area.setParentId(0);
        area.setLevel(1);
        areaService.insert(area);
    }

    @Test
    public void test2() {
        Area area = areaService.selectByPrimaryKey(1);
        System.out.println(area.getName());
    }

}
