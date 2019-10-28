package com.xc.service;


import com.xc.common.BaseService;
import com.xc.entity.Area;

/**
 * @author gengqiang
 */
public interface AreaService extends BaseService<Area> {

    /**
     * 插入回滚
     */
    void insertBack();

    String abcd();
}
