package com.qingshixun.project.crm.module.OpportunityController.service;

import com.qingshixun.project.crm.core.BaseService;
import com.qingshixun.project.crm.core.PageContainer;
import com.qingshixun.project.crm.model.OpportunityModel;
import com.qingshixun.project.crm.module.OpportunityController.dao.OpportunityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;

@Service
@Transactional
public class OpportunityService extends BaseService {

    @Autowired
    private OpportunityDao opportunityDao;


    /**
     * 获取所有可供选择的分页销售机会信息
     * @return
     */
    public PageContainer getSelectOpportunityPage(Map<String ,String > params){
        return opportunityDao.getSelectOpportunityPage(params);
    }

    /**
     * 根据id查找销售机会
     * @param id
     * @return
     */
    public OpportunityModel getOpportunityById(Long id){
        return opportunityDao.get(id);
    }


}
