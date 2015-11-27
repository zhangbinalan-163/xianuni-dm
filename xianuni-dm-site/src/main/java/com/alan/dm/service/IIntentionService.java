package com.alan.dm.service;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.IntentionInfo;

/**
 *
 * Created by zhangbinalan on 15/11/16.
 */
public interface IIntentionService {
    /**
     *
     * @param intentionInfo
     * @throws DMException
     */
    void createIntention(IntentionInfo intentionInfo) throws DMException;

    /**
     *
     * @param intentionInfo
     * @throws DMException
     */
    void deleteIntention(IntentionInfo intentionInfo) throws DMException;

    /**
     *
     * @param intentionId
     * @return
     * @throws DMException
     */
    IntentionInfo getById(int intentionId) throws DMException;
}
