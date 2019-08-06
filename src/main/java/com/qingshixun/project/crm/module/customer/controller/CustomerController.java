package com.qingshixun.project.crm.module.customer.controller;

import com.qingshixun.project.crm.core.PageContainer;
import com.qingshixun.project.crm.module.customer.service.CustomerService;
import com.qingshixun.project.crm.web.ResponseData;
import com.qingshixun.project.crm.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping(value = "customer")
public class CustomerController  extends BaseController {

    @Autowired
    private CustomerService customerService;

    /**
     * 进入客户的选择界面
     * @return
     */
    @RequestMapping("/customer")
    public String toCustomerSelectPage(){
        return "/customer/customer";
    }
    /**
     * 获取可供选择的分页客户信息
     * @param params
     * @return
     */
    @RequestMapping("/list/select")
    @ResponseBody
    public PageContainer getSelectedCustomerPage(@RequestParam Map<String,String> params){
        PageContainer pageContainer=customerService.getSelectCustomerPage(params);
        return  pageContainer;
    }

    /**
     * 返回选择的客户信息
     * @param customerId
     * @return
     */
    @RequestMapping("/getSelectedCustomer/{customerId}")
    @ResponseBody
    public ResponseData getSelectedCustomer(@PathVariable Long customerId){
        ResponseData data=new ResponseData();
        try{
            data.setData(customerService.getCustomerById(customerId));
        }
        catch (Exception e){
            logger.error(e.getMessage(), e);
            data.setError(e.getMessage());
        }
        return data;
    }
}
