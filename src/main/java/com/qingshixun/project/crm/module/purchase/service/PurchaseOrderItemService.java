package com.qingshixun.project.crm.module.purchase.service;

import com.qingshixun.project.crm.core.BaseService;
import com.qingshixun.project.crm.model.ProductModel;
import com.qingshixun.project.crm.model.PurchaseOrderItemModel;
import com.qingshixun.project.crm.module.product.dao.ProductDao;
import com.qingshixun.project.crm.module.purchase.dao.PurchaseOrderItemDao;
import com.qingshixun.project.crm.web.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseOrderItemService extends BaseService {

    @Autowired
    private PurchaseOrderItemDao purchaseOrderItemDao;
    @Autowired
    private ProductDao productDao;

    /**
     * 根据采购订单id获取采购物品信息
     * @param purchaseOrderId
     * @return
     */
    public List<PurchaseOrderItemModel> getPurchaseOrderItemList(Long purchaseOrderId){
        return purchaseOrderItemDao.getPurchaseOrderItemList(purchaseOrderId);
    }

    /**
     * 删除采购物品信息
     * @param id
     */
    public void deletePurchaseOrderItem( Long id){
        if(id!=0L){
            PurchaseOrderItemModel purchaseOrderItem = purchaseOrderItemDao.get(id);
            ProductModel product = productDao.get(purchaseOrderItem.getProduct().getId());
            // 设置产品库存信息
            product.setInventory(product.getInventory() + purchaseOrderItem.getQuantity());
            productDao.save(product);
            purchaseOrderItemDao.delete(id);
        }
    }

}
