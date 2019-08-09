package com.qingshixun.project.crm.module.supplier.controller;

import com.qingshixun.project.crm.core.PageContainer;
import com.qingshixun.project.crm.model.SupplierModel;
import com.qingshixun.project.crm.module.supplier.service.SupplierService;
import com.qingshixun.project.crm.web.ResponseData;
import com.qingshixun.project.crm.web.controller.BaseController;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping(value = "/supplier")
public class SupplierController extends BaseController {

    @Autowired
    private SupplierService supplierService;

    /**
     * 将请求转发到/supplier/list.jsp中
     * @return
     */
    @RequestMapping(value = "/list")
    public String toSupplierPage(){
        return "/supplier/list";
    }

    /**
     * 将请求转发到/supplier/supplier.jsp中
     * @return
     */
    @RequestMapping(value = "/supplier")
    public String toSelectSupplierPage(){
        return "/supplier/supplier";
    }

    /**
     * 获取可供选择的供应商分页信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/list/select")
    @ResponseBody
    public PageContainer getSelectSupplierData(@RequestParam Map<String ,String > params){
        return supplierService.getSelectSupplierData(params);
    }

    /**
     * 获取供应商分页信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/list/data")
    @ResponseBody
    public PageContainer getSupplierData(@RequestParam Map<String ,String> params){

        return supplierService.getSupplierData(params);
    }

    @RequestMapping(value = "/delete/{supplierId}")
    @ResponseBody
    public ResponseData deleteSupplier(@PathVariable Long supplierId) {
        ResponseData responseData = new ResponseData();
        try{
            supplierService.deleteSupplier(supplierId);
        }
        catch (ConstraintViolationException e){
            responseData.setStatus("3");
        }
        catch (Exception e){
            responseData.setError(e.getMessage());
            logger.error(e.getMessage());
        }
        return responseData;
    }

    /**
     * 将请求转发到/supplier/form.jsp中
     * @param supplierId
     * @param model
     * @return
     */
    @RequestMapping(value = "/form/{supplierId}")
    public String toSupplierForm(@PathVariable Long supplierId, Model model){
        SupplierModel supplier;
        if(supplierId==0L){
            supplier=new SupplierModel();
        }
        else {
            supplier=supplierService.getSupplierById(supplierId);
        }
        model.addAttribute("supplierModel",supplier);
        return "/supplier/form";
    }

    /**
     * 保存供应商
     * @param supplierModel
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public ResponseData saveSupplier(SupplierModel supplierModel){
        ResponseData responseData = new ResponseData();
        try{
            supplierService.saveSupplier(supplierModel);
        }
        catch (Exception e){
            responseData.setError(e.getMessage());
            logger.error(e.getMessage());
        }
        return responseData;
    }

    /**
     * 获取选择的供应商
     * @param supplierId
     * @return
     */
    @RequestMapping(value = "/getSelectedSupplier/{supplierId}")
    @ResponseBody
    public ResponseData getSelectedSupplier(@PathVariable Long supplierId){
        ResponseData responseData = new ResponseData();
        try{
            responseData.setData(supplierService.getSupplierById(supplierId));
        }
        catch (Exception e){
            responseData.setError(e.getMessage());
            logger.error(e.getMessage());
        }
        return responseData;
    }

}
