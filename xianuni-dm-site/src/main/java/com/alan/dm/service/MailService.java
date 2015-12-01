package com.alan.dm.service;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.entity.MailInfo;
import com.alan.dm.entity.Page;
import com.alan.dm.entity.Person;
import com.alan.dm.entity.query.MailCondition;

import java.util.List;

/**
 * 站内信业务接口
 * Created by zhangbinalan on 15/11/29.
 */
public interface MailService {
    /**
     *
     * @param condition
     * @return
     * @throws DMException
     */
    int countByCondition(MailCondition condition) throws DMException;
    /**
     *
     * @param condition
     * @param page
     * @return
     * @throws DMException
     */
    List<MailInfo> getByCondition(MailCondition condition,Page page) throws DMException;
    /**
     * 获取指定人员的站内信
     * @param person
     * @param page
     * @return
     */
    List<MailInfo> getByPerson(Person person,Page page) throws DMException;

    /**
     *
     * @param id
     * @return
     * @throws DMException
     */
    MailInfo getById(Integer id) throws DMException;

    /**
     *
     * @param mailInfo
     * @throws DMException
     */
    void sendMail(MailInfo mailInfo) throws DMException;

    /**
     *
     * @param mailInfo
     * @throws DMException
     */
    void deleteMail(MailInfo mailInfo) throws DMException;
}
