package com.qingshixun.project.crm.module.sales.service;

import com.qingshixun.project.crm.core.BaseService;
import com.qingshixun.project.crm.core.PageContainer;
import com.qingshixun.project.crm.model.ProductModel;
import com.qingshixun.project.crm.model.ProductStatus;
import com.qingshixun.project.crm.model.SalesOrderItemModel;
import com.qingshixun.project.crm.model.SalesOrderModel;
import com.qingshixun.project.crm.module.product.dao.ProductDao;
import com.qingshixun.project.crm.module.sales.dao.SalesOrderDao;
import com.qingshixun.project.crm.module.sales.dao.SalesOrderItemDao;
import com.qingshixun.project.crm.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SalesOrderService extends BaseService {

    @Autowired
    private SalesOrderDao salesOrderDao;

    @Autowired
    private SalesOrderItemDao salesOrderItemDao;

    @Autowired
    private ProductDao productDao;


    /**
     * 获取所有销售订单信息
     *
     * @return
     */
    public PageContainer getSalesOrderPage(Map<String, String> params) {
        return salesOrderDao.getSalesOrderPage(params);
    }

    /**
     * 根据销售订单ID，获取销售订单信息
     *
     * @param salesOrderId
     * @return
     */
    public SalesOrderModel getSalesOrder(Long salesOrderId) {
        return salesOrderDao.get(salesOrderId);
    }

    /**
     * 删除销售订单
     *
     * @param salesOrderId
     */
    public void deleteSalesOrder(Long salesOrderId) {
        salesOrderDao.delete(salesOrderId);
    }


    /**
     * 根据主题搜索销售订单
     *
     * @param
     * @return
     */
    public List<SalesOrderModel> getSalesOrderList(String value) {
        return salesOrderDao.getSalesOrderList(value);
    }

    /**
     * 保存销售订单
     *
     * @param salesOrder
     * @param request
     */
    public void saveSalesOrder(SalesOrderModel salesOrder, HttpServletRequest request) {
        String[] quantities = request.getParameterValues("quantity");
        String[] productIds = request.getParameterValues("productId");
        String[] prices = request.getParameterValues("price");
        String[] salesOrderItemIds = request.getParameterValues("itemId");
        //编辑之前的需求数量。
        String[] oldquantities=request.getParameterValues("oldQuantity");

        List<SalesOrderItemModel> itemModels=null;
        boolean flag = false;//判断是否是新增的标志
        //订单总数量
        int totalquantitiy = 0;
        //订单的money
        double totalAmount = 0.0;


        /*注意这里为什么要这样做？
        因为SalesOrderItemModel.class中设置了CascadeType.PERSIST的联级操作：
        应该让SalesOrderModel和ProductModel先保存，再保存SalesOrderItemModel。*/

        //保存订单
        for (int i = 0; i < quantities.length; i++) {
            totalquantitiy += Integer.parseInt(quantities[i]);
            totalAmount += Double.parseDouble(prices[i]) * Integer.parseInt(quantities[i]);
        }

        if ("".equals(salesOrder.getCode())) {
            logger.debug("新增");
            flag = true;//没有code则表示是新增
            salesOrder.setCode("SAO" + System.currentTimeMillis());
        }

        salesOrder.setTotalQuantity(totalquantitiy);
        salesOrder.setTotalAmount(totalAmount);
        salesOrder.setUpdateTime(DateUtils.timeToString(new Date()));
        salesOrderDao.save(salesOrder);


/*
            报错原因：org.hibernate.NonUniqueObjectException:
            a different object with the same identifier value was already associated with the session
            存在多个session对象
            所以采用前端hide以前的需求数量，存到request里，后台提取。

            if(!flag){
            itemModels=new SalesOrderItemDao().getSalesItemOrderList(salesOrder.getId());
            System.out.println("itemModels大小为："+itemModels.size());
        }*/


        for (int i = 0; i < salesOrderItemIds.length; i++) {
            SalesOrderItemModel salesOrderItem = new SalesOrderItemModel();
            ProductModel product = productDao.get(Long.parseLong(productIds[i]));

            //更新产品信息
            int inventory = product.getInventory();
            int oldquantity = 0;
            int newquantity = Integer.parseInt(quantities[i]);

            //如果是编辑，则要查出之前订单的需要量然后再进行更新，防止2次减少产品库存
            if (!flag) {
               /*
                    如上报错原因。
                    for (SalesOrderItemModel item : itemModels) {
                    System.out.println("集合id:"+item.getProduct().getId());
                    System.out.println("产品id:"+product.getId());
                    if (item.getProduct().getId().longValue()==product.getId().longValue()){
                        logger.debug("旧需求为:"+item.getQuantity());
                        oldquantities = item.getQuantity();
                    }
                }*/

               oldquantity=Integer.parseInt(oldquantities[i]);
                logger.debug("寻找以前的需求量"+oldquantity);
            }

                product.setInventory(inventory - (newquantity - oldquantity));
                if (product.getInventory() <= 0) {
                    product.setInventory(0);
                    product.setStatus(ProductStatus.stopStatus);
                }
                product.setUpdateTime(DateUtils.timeToString(new Date()));
                productDao.save(product);


                //保存订单条目内容
                salesOrderItem.setProduct(product);
                salesOrderItem.setSalesOrder(salesOrder);
                salesOrderItem.setQuantity(Integer.parseInt(quantities[i]));
                salesOrderItem.setAmount(Double.parseDouble(prices[i]) * Integer.parseInt(quantities[i]));
                salesOrderItem.setUpdateTime(DateUtils.timeToString(new Date()));
                if (!"null".equals(salesOrderItemIds[i])) {
                    salesOrderItem.setId(Long.parseLong(salesOrderItemIds[i]));
                }
                salesOrderItemDao.save(salesOrderItem);
            }
        }
    }
