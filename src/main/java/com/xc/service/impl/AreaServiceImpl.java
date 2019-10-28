package com.xc.service.impl;

import com.xc.common.BaseMapper;
import com.xc.common.BaseServiceImpl;
import com.xc.config.database.ReadOnlyConection;
import com.xc.entity.Area;
import com.xc.mapper.AreaMapper;
import com.xc.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Administrator
 * @date 2016/9/9
 */
@Service
public class AreaServiceImpl extends BaseServiceImpl<Area> implements AreaService {

    @Autowired
    private AreaMapper mapper;

    @Override
    public BaseMapper<Area> getMapper() {
        return mapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertBack() {
        Area area = new Area();
        area.setDistrictId("1111");
        area.setName("testException");
        area.setParentId(0);
        area.setLevel(1);
        mapper.insert(area);
        throw new RuntimeException();
    }

    @Override
//    @ReadOnlyConection
    public String abcd() {
        Area area = mapper.selectByPrimaryKey(1);
        return area.getName();
    }

}
