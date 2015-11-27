package com.alan.dm.service;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.ActivitistInfo;
/**
 * 党员积极分子
 * Created by zhangbinalan on 15/11/16.
 */
public interface IActivitistService {

    /**
     *
     * @param activitistInfo
     * @throws DMException
     */
    void createActivitist(ActivitistInfo activitistInfo) throws DMException;

    /**
     *
     * @param activitistInfo
     * @throws DMException
     */
    void deleteActivitist(ActivitistInfo activitistInfo) throws DMException;

    /**
     *
     * @param activitistId
     * @return
     * @throws DMException
     */
    ActivitistInfo getById(int activitistId) throws DMException;

}
