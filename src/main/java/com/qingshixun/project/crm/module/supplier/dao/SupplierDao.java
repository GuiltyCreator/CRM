package com.qingshixun.project.crm.module.supplier.dao;

import com.qingshixun.project.crm.core.BaseHibernateDao;
import com.qingshixun.project.crm.core.PageContainer;
import com.qingshixun.project.crm.model.SupplierModel;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class SupplierDao extends BaseHibernateDao<SupplierModel,Long> {

    /**
     * 获取供应商的分页信息
     * @param params
     * @return
     */
    public PageContainer getSupplierData(Map<String ,String> params){
        Criterion criterion=createLikeCriterion("name","%"+params.get("name")+"%");
        return getDataPage(params,criterion);
    }

    /**
     * 获取可供选择的供应商分页信息
     * @param params
     * @return
     */
    public PageContainer getSelectSupplierData(Map<String,String> params){
        return getDataPage(params);
    }

}
