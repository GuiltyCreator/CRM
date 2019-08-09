package com.qingshixun.project.crm.module.purchase.controller;

import com.qingshixun.project.crm.module.purchase.service.PurchaseOrderItemService;
import com.qingshixun.project.crm.web.ResponseData;
import com.qingshixun.project.crm.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/purchaseorderitem")
public class PurchaseOrderItemController extends BaseController {

    @Autowired
    private PurchaseOrderItemService purchaseOrderItemService;

    @RequestMapping(value = "/list/data/{purchaseOrderId}")
    @ResponseBody
    public ResponseData getPurchaseOrderItemData (@PathVariable Long purchaseOrderId){
        ResponseData responseData=new ResponseData();
        try {
        responseData.setData(purchaseOrderItemService.getPurchaseOrderItemList(purchaseOrderId));
        }
        catch (Exception e){
            responseData.setError(e.getMessage());
        }
        return responseData;
    }

    @RequestMapping(value = "/delete/{id}")
    @ResponseBody
    public ResponseData deletePurchaseOrderItem(@PathVariable Long id){
        ResponseData responseData=new ResponseData();
        try {
            purchaseOrderItemService.deletePurchaseOrderItem(id);
            responseData.setData("删除成功");
        }
        catch (Exception e){
            responseData.setError(e.getMessage());
        }
        return responseData;
    }

}
