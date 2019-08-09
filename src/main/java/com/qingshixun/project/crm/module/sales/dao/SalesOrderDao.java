package com.qingshixun.project.crm.module.sales.dao;

import com.qingshixun.project.crm.core.BaseHibernateDao;
import com.qingshixun.project.crm.core.PageContainer;
import com.qingshixun.project.crm.model.SalesOrderModel;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Map;


@Repository
public class SalesOrderDao extends BaseHibernateDao<SalesOrderModel,Long> {

    /**
     * 根据销售订单的主题来搜索订单
     * @param value 主题
     * @return 销售订单对象
     */
    @Transactional(readOnly = true)
    public List<SalesOrderModel> getSalesOrderList(String value){
        Criterion salesorderName=createLikeCriterion("theme","%"+value+"%");
        return find(salesorderName);
    }

    /**
     * 获取销售订单分页信息
     * @param params
     * @return
     */
    public PageContainer getSalesOrderPage(Map<String ,String > params){
        Criterion salesOrderName = createLikeCriterion("theme","%"+params.get("theme")+"%");
        return getDataPage(params,salesOrderName);
    }



}
