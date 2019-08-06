package com.qingshixun.project.crm.module.sales.controller;

import com.qingshixun.project.crm.core.PageContainer;
import com.qingshixun.project.crm.model.SalesOrderModel;
import com.qingshixun.project.crm.module.sales.service.SalesOrderService;
import com.qingshixun.project.crm.util.ImageUtils;
import com.qingshixun.project.crm.web.ResponseData;
import com.qingshixun.project.crm.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 访问订单的Controller类
 */
@Controller
@RequestMapping( value = "/salesorder")
public class SalesOrderController extends BaseController {

    @Autowired
    private SalesOrderService service;


    @RequestMapping(value = "list")
    public String toList() {

        return "/salesorder/list";
    }

    /**
     * 查询订单分页信息
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/list/data")
    @ResponseBody
    public PageContainer getData(@RequestParam Map<String, String> params) {
        return service.getSalesOrderPage(params);
    }

    /**
     * 进入销售订单编辑/增加页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/form/{salesOrderId}")
    public String salesOrderForm(Model model, @PathVariable Long salesOrderId) {
        SalesOrderModel salesOrder = null;
        if (0L == salesOrderId) {
            salesOrder = new SalesOrderModel();
        } else {
            salesOrder = service.getSalesOrder(salesOrderId);
        }
        model.addAttribute(salesOrder);
        model.addAttribute("imagePath", ImageUtils.DEFAULT_IMAGE_PATH);
        return "/salesorder/form";
    }

    /**
     * 存储订单信息
     * @param request
     * @param salesOrder
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData saveSalesOrder(HttpServletRequest request, @ModelAttribute("salesOrder") SalesOrderModel salesOrder){
        ResponseData responseData=new ResponseData();
        try {
            service.saveSalesOrder(salesOrder,request);
        }
        catch (Exception e){
            logger.error(e.getMessage(),e);
            responseData.setError(e.getMessage());
        }
        return responseData;
    }

    @RequestMapping(value = "/delete/{salesOrderId}")
    @ResponseBody
    public  ResponseData deleteSalesOrder(@PathVariable Long salesOrderId){
        ResponseData responseData=new ResponseData();
        try {
        service.deleteSalesOrder(salesOrderId);
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
