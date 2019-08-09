package com.qingshixun.project.crm.module.sales.service;


import com.qingshixun.project.crm.model.ProductModel;
import com.qingshixun.project.crm.model.SalesOrderItemModel;
import com.qingshixun.project.crm.module.product.dao.ProductDao;
import com.qingshixun.project.crm.module.sales.dao.SalesOrderItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qingshixun.project.crm.core.BaseService;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class SalesOrderItemService extends BaseService {

    @Autowired
    private SalesOrderItemDao salesOrderItemDao;
    @Autowired
    private ProductDao productDao;

    /**
     * 删除选中订单产品
     * @param id 订单id
     */
    public void SalesOrderItemDelete(Long id){

        if(id!=0L){
            SalesOrderItemModel salesOrderItemModel = salesOrderItemDao.get(id);
            ProductModel product = productDao.get(salesOrderItemModel.getProduct().getId());
            // 设置产品库存信息
            product.setInventory(product.getInventory() + salesOrderItemModel.getQuantity());
            productDao.save(product);
            salesOrderItemDao.delete(id);
        }
    }

    /**
     * 获取销售订单条目信息
     * @param salesOrderId 销售订单id
     * @return
     */
    public List<SalesOrderItemModel> getSalesItemOrderList(Long salesOrderId){
        return salesOrderItemDao.getSalesItemOrderList(salesOrderId);
    }

}
