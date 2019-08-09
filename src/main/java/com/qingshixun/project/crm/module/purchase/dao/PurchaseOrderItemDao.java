package com.qingshixun.project.crm.module.purchase.dao;

import com.qingshixun.project.crm.core.BaseHibernateDao;
import com.qingshixun.project.crm.model.PurchaseOrderItemModel;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PurchaseOrderItemDao extends BaseHibernateDao<PurchaseOrderItemModel,Long> {


    /**
     * 根据采购订单id获取采购物品信息
     * @param purchaseOrderId
     * @return
     */
    public List<PurchaseOrderItemModel> getPurchaseOrderItemList(Long purchaseOrderId){
        Criterion criterion=createCriterion("purchaseOrder.id",purchaseOrderId);
        return find(criterion);
    }

}
