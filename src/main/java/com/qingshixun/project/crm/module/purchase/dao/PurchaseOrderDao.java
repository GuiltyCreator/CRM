package com.qingshixun.project.crm.module.purchase.dao;

import com.qingshixun.project.crm.core.BaseHibernateDao;
import com.qingshixun.project.crm.core.PageContainer;
import com.qingshixun.project.crm.model.PurchaseOrderModel;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

import java.util.Map;


@Repository
public class PurchaseOrderDao extends BaseHibernateDao<PurchaseOrderModel, Long> {


    /**
     * 查询采购订单的分页信息
     * @param params
     * @return
     */
    public PageContainer getPurchaseOrderData(Map<String, String> params){
        Criterion criterion=createLikeCriterion("theme","%"+params.get("theme")+"%");
        return getDataPage(params,criterion);
    }
}
