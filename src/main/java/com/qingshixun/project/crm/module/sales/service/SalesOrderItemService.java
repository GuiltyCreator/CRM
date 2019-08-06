package com.qingshixun.project.crm.module.sales.service;


import com.qingshixun.project.crm.model.SalesOrderItemModel;
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

    /**
     * 删除选中订单产品
     * @param id 订单id
     */
    public void SalesOrderItemDelete(Long id){

        if(id!=0L){
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
