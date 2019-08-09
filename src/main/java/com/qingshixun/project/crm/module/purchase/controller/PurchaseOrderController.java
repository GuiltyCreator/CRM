package com.qingshixun.project.crm.module.purchase.controller;

import com.qingshixun.project.crm.core.PageContainer;
import com.qingshixun.project.crm.model.PurchaseOrderModel;
import com.qingshixun.project.crm.module.purchase.service.PurchaseOrderService;
import com.qingshixun.project.crm.util.ImageUtils;
import com.qingshixun.project.crm.web.ResponseData;
import com.qingshixun.project.crm.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping(value = "/purchaseorder")
public class PurchaseOrderController extends BaseController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    /**
     * 将请求转发到/purchaseorder/list.jsp
     * @return
     */
    @RequestMapping(value = "/list")
    public String toPurchaseOrderPage(){

        return "/purchaseorder/list";
    }

    /**
     * 获取采购订单分页信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/list/data")
    @ResponseBody
    public PageContainer getPurchaseOrderData(@RequestParam Map<String, String> params) {

        return purchaseOrderService.getPurchaseOrderData(params);

    }

    /**
     * 将请求转到/purchaseorder/form。jsp
     * @param purchaseOrderId
     * @param model
     * @return
     */
    @RequestMapping(value = "/form/{purchaseOrderId}")
    public String toPurchaseOrderForm(@PathVariable Long purchaseOrderId, Model model){

        PurchaseOrderModel purchaseOrderModel;
        if(purchaseOrderId==0L){
            purchaseOrderModel=new PurchaseOrderModel();
        }else {
            purchaseOrderModel=purchaseOrderService.getPurchaseOrderById(purchaseOrderId);
        }
        model.addAttribute("purchaseOrderModel",purchaseOrderModel);
        model.addAttribute("imagePath", ImageUtils.DEFAULT_IMAGE_PATH);


        return "/purchaseorder/form";
    }

    /**
     * 保存采购订单
     * @param purchaseOrderModel
     * @param request
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public ResponseData savePurchaseOrder(PurchaseOrderModel purchaseOrderModel, HttpServletRequest request){
        ResponseData responseData=new ResponseData();
        try {
            purchaseOrderService.savePurchaseOrder(purchaseOrderModel,request);
        }
        catch (Exception e){
            responseData.setError(e.getMessage());
        }
        return responseData;
    }

    @RequestMapping(value = "/delete/{purchaseOrderId}")
    @ResponseBody
    public ResponseData deletePurchaseOrder(@PathVariable Long purchaseOrderId){
        ResponseData responseData=new ResponseData();
        try {
        purchaseOrderService.deletePurchaseOrder(purchaseOrderId);
        }
        catch (org.hibernate.exception.ConstraintViolationException e){
            responseData.setStatus("3");
            responseData.setError(e.getMessage());
        }
        catch (Exception e){
            responseData.setError(e.getMessage());
        }
        return responseData;
    }


}
