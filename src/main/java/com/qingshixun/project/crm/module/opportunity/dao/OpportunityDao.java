package com.qingshixun.project.crm.module.opportunity.dao;

import com.qingshixun.project.crm.core.BaseHibernateDao;
import com.qingshixun.project.crm.core.PageContainer;
import com.qingshixun.project.crm.model.OpportunityModel;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class OpportunityDao extends BaseHibernateDao<OpportunityModel,Long> {


    /**
     * 查询所有可供选择的分页销售机会信息
     * @param
     * @return
     */
    public PageContainer getSelectOpportunityPage(Map<String ,String>params){
        return getDataPage(params);
    }

}
