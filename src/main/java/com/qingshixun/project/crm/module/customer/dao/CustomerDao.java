package com.qingshixun.project.crm.module.customer.dao;

import com.qingshixun.project.crm.core.BaseHibernateDao;
import com.qingshixun.project.crm.core.PageContainer;
import com.qingshixun.project.crm.model.CustomerModel;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class CustomerDao extends BaseHibernateDao<CustomerModel,Long> {

    /**
     * 查询所有可供选择的分页客户信息
     * @param
     * @return
     */
    public PageContainer getSelectCustomerPage(Map<String ,String> params){
        return getDataPage(params);
    }
}
