package com.qingshixun.project.crm.module.sales.controller;


import com.qingshixun.project.crm.module.sales.service.SalesOrderItemService;
import com.qingshixun.project.crm.web.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/salesorderitem")
public class SalesOrderItemController {

    @Autowired
    private SalesOrderItemService salesOrderItemService;


    /**
     * 删除选中订单产品
     * @param id 订单id
     * @return
     */
    @RequestMapping(value = "/delete/{id}")
    @ResponseBody
    public ResponseData deleteSalesItemOrder(@PathVariable Long id){
        ResponseData responseData=new ResponseData();
        try {
            salesOrderItemService.SalesOrderItemDelete(id);
        }
        catch (Exception e){
            e.printStackTrace();
            responseData.setError(e);

        }
        return responseData;
    }

    @RequestMapping(value = "/list/data/{salesOrderId}")
    @ResponseBody
    public ResponseData getSalesItemOrder(@PathVariable Long salesOrderId){
        ResponseData responseData=new ResponseData();
        try {
            responseData.setData(salesOrderItemService.getSalesItemOrderList(salesOrderId));
        }
        catch (Exception e){
            responseData.setError(e.getMessage());
        }
        return responseData;
    }
}
