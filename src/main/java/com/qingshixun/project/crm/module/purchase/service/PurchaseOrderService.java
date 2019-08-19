package com.qingshixun.project.crm.module.purchase.service;

import com.qingshixun.project.crm.core.BaseService;
import com.qingshixun.project.crm.core.PageContainer;
import com.qingshixun.project.crm.model.ProductModel;
import com.qingshixun.project.crm.model.PurchaseOrderItemModel;
import com.qingshixun.project.crm.model.PurchaseOrderModel;
import com.qingshixun.project.crm.module.product.dao.ProductDao;
import com.qingshixun.project.crm.module.purchase.dao.PurchaseOrderDao;
import com.qingshixun.project.crm.module.purchase.dao.PurchaseOrderItemDao;
import com.qingshixun.project.crm.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
public class PurchaseOrderService extends BaseService {

    @Autowired
    private PurchaseOrderDao purchaseOrderDao;
    @Autowired
    private PurchaseOrderItemDao purchaseOrderItemDao;
    @Autowired
    private ProductDao productDao;

    /**
     * 获取采购订单分页信息
     * @param params
     * @return
     */
    public PageContainer getPurchaseOrderData(Map<String, String> params){

        return purchaseOrderDao.getPurchaseOrderData(params);

    }

    /**
     * 根据id查找采购订单
     * @param id
     * @return
     */
    public PurchaseOrderModel getPurchaseOrderById(Long id){

        return purchaseOrderDao.get(id);

    }

    /**
     * 保存采购订单
     * @param purchaseOrderModel
     */
    public void savePurchaseOrder(PurchaseOrderModel purchaseOrderModel, HttpServletRequest request){

        String[] quantities = request.getParameterValues("quantity");
        String[] productIds = request.getParameterValues("productId");
        String[] prices = request.getParameterValues("price");
        String[] purchaseOrderItemIds = request.getParameterValues("itemId");
        String[] oldquantities={"0"};
        if(request.getParameterValues("oldQuantity")!=null){
            oldquantities = request.getParameterValues("oldQuantity");
        }

        boolean flag = false;//判断是否是新增

        //保存采购订单

        logger.debug("保存采购订单");
        int totalquantitiy = 0;
        double totalAmount = 0.0;

        for (int i = 0; i < quantities.length; i++) {
            totalquantitiy += Integer.parseInt(quantities[i]);
            totalAmount += Double.parseDouble(prices[i]) * Integer.parseInt(quantities[i]);
        }

        if(purchaseOrderModel.getCode().equals("")){
            purchaseOrderModel.setCode("PUR"+System.currentTimeMillis());
            flag=true;
        }
        purchaseOrderModel.setTotalQuantity(totalquantitiy);
        purchaseOrderModel.setTotalAmount(totalAmount);
        purchaseOrderModel.setUpdateTime(DateUtils.timeToString(new Date()));
        purchaseOrderDao.save(purchaseOrderModel);



        for(int i=0;i<purchaseOrderItemIds.length;i++){


            PurchaseOrderItemModel purchaseOrderItemModel=new PurchaseOrderItemModel();
            ProductModel product = productDao.get(Long.parseLong(productIds[i]));

            //保存产品
            logger.debug("保存产品");
            int inventory = product.getInventory();
            int oldquantity = 0;
            int newquantity = Integer.parseInt(quantities[i]);

            if(!flag){
                if(i<oldquantities.length){
                    oldquantity = Integer.parseInt(oldquantities[i]);
                }
            }

            product.setInventory(inventory + (newquantity - oldquantity));
            if (product.getInventory() <= 0) {
                product.setInventory(0);
            }
            product.setUpdateTime(DateUtils.timeToString(new Date()));
            productDao.save(product);


            //保存采购订单条目
            logger.debug("保存采购订单条目");
            if(!"null".equals(purchaseOrderItemIds[i])){
                purchaseOrderItemModel.setId(Long.parseLong(purchaseOrderItemIds[i]));
            }
            purchaseOrderItemModel.setProduct(product);
            purchaseOrderItemModel.setPurchaseOrder(purchaseOrderModel);
            purchaseOrderItemModel.setQuantity(Integer.parseInt(quantities[i]));
            purchaseOrderItemModel.setAmount(Double.parseDouble(prices[i]) * Integer.parseInt(quantities[i]));
            purchaseOrderItemModel.setUpdateTime(DateUtils.timeToString(new Date()));

            purchaseOrderItemDao.save(purchaseOrderItemModel);

        }

    }

    /**
     * 删除采购订单
     * @param id
     */
    public void deletePurchaseOrder(Long id){

        purchaseOrderDao.delete(id);
    }
}
