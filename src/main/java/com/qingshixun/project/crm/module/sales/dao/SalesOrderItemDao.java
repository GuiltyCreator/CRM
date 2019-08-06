package com.qingshixun.project.crm.module.sales.dao;

import com.qingshixun.project.crm.core.BaseHibernateDao;
import com.qingshixun.project.crm.model.SalesOrderItemModel;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public class SalesOrderItemDao extends BaseHibernateDao<SalesOrderItemModel, Long> {


    /**
     * 根据销售订单id获取销售订单的条目信息
     * @param value 销售订单id
     * @return
     */
    @Transactional(readOnly = true)
    public List<SalesOrderItemModel> getSalesItemOrderList(Long value){
        Criterion salesOrderId=createCriterion("salesOrder.id",value);
        return find(salesOrderId);
    }



}