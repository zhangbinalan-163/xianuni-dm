package com.alan.dm.service.impl;

import com.alan.dm.common.exception.DMException;
import com.alan.dm.dao.mapper.PersonInfoMapper;
import com.alan.dm.entity.*;
import com.alan.dm.entity.query.*;
import com.alan.dm.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 党员业务信息
 * Created by zhangbinalan on 15/11/16.
 */
@Service(value = "personService")
public class PersonServiceImpl implements IPersonService{
    private static Logger logger= LoggerFactory.getLogger(PersonServiceImpl.class);

    @Autowired
    private PersonInfoMapper personInfoMapper;

    @Autowired
    private IOrgnizationService orgnizationService;

    @Autowired
    private IApplierService applierService;

    @Autowired
    private IActivitistService activitistService;

    @Autowired
    private IIntentionService intentionService;

    @Autowired
    private IPrepareService prepareService;

    @Autowired
    private INormalService normalService;
    @Override
    public List<Person> getByCondition(PersonCondition condition, Page page, PersonResult result) throws DMException {
        List<Person> personList = personInfoMapper.getByCondition(condition,page);
        if(personList!=null&&result!=null){
            for(Person person:personList){
                if(result.isIncludeOrgnization()){
                    person.setOrgnization(orgnizationService.getOrgById(person.getOrgId()));
                }
                if(result.isIncludeNormalInfo()){
                    NormalInfo normalInfo = normalService.getById(person.getNormalInfoId());
                    if(normalInfo==null){
                        normalInfo=new NormalInfo();
                    }
                    person.setNormalInfo(normalInfo);
                }
                if(result.isIncludeActivitistInfo()){
                    ActivitistInfo activitistInfo=activitistService.getById(person.getActivitistInfoId());
                    if(activitistInfo==null){
                        activitistInfo=new ActivitistInfo();
                    }
                    person.setActivitistInfo(activitistInfo);
                }
                if(result.isIncludeIntentionInfo()){
                    IntentionInfo intentionInfo=intentionService.getById(person.getIntentionInfoId());
                    if(intentionInfo==null){
                        intentionInfo=new IntentionInfo();
                    }
                    person.setIntentionInfo(intentionInfo);
                }
                if(result.isIncludeApplierInfo()){
                    ApplierInfo applierInfo=applierService.getById(person.getApplierInfoId());
                    if(applierInfo==null){
                        applierInfo=new ApplierInfo();
                    }
                    person.setApplierInfo(applierInfo);
                }
                if(result.isIncludePrepareInfo()){
                    PrepareInfo prepareInfo=prepareService.getById(person.getPrepareInfoId());
                    if(prepareInfo==null){
                        prepareInfo=new PrepareInfo();
                    }
                    person.setPrepareInfo(prepareInfo);
                }
            }
        }
        return personList;
    }


    @Override
    public int countByOrgWithStatus(Orgnization orgnization, int status, boolean withAllSub) throws DMException {
        int totalCount=0;
        if(orgnization==null){
            return totalCount;
        }
        //组织直接的数量计算
        PersonCondition personCondition=new PersonCondition();
        personCondition.setOrgList(Arrays.asList(orgnization.getId()));
        personCondition.setStatus(Arrays.asList(status));
        totalCount=personInfoMapper.countByCondition(personCondition);
        //下属组织的计算
        if(withAllSub){
            List<Orgnization> subOrgList=orgnizationService.getOrgByParent(orgnization,true);
            if(subOrgList!=null&&subOrgList.size()>0){
                List<Integer> orgIdList=new ArrayList<>();
                for(Orgnization subOrg:subOrgList){
                    orgIdList.add(subOrg.getId());
                }
                personCondition=new PersonCondition();
                personCondition.setOrgList(orgIdList);
                personCondition.setStatus(Arrays.asList(status));
                totalCount=totalCount+personInfoMapper.countByCondition(personCondition);
            }
        }
        return totalCount;
    }

    @Override
    public void updatePerson(Person person) throws DMException {
        person.setUpdateTime(new Date());
        personInfoMapper.update(person);
    }

    @Override
    public List<Person> getByCondition(PersonCondition condition, Page page)  throws DMException{
        return getByCondition(condition,page,null);
    }

    @Override
    public int countByCondition(PersonCondition condition) throws DMException {
        return personInfoMapper.countByCondition(condition);
    }

    @Override
    public void createPerson(Person person) throws DMException {
        person.setCreateTime(new Date());
        personInfoMapper.insert(person);
    }

    @Override
    public Person getById(int personId) throws DMException {
        return personInfoMapper.getById(personId);
    }

    @Override
    public Person getByNumber(String number) throws DMException {
        return personInfoMapper.getByNumber(number);
    }

    @Override
    public void deletePerson(Person person) throws DMException {
        personInfoMapper.delete(person);
    }
}
