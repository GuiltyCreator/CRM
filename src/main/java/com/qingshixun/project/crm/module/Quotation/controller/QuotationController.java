package com.qingshixun.project.crm.module.Quotation.controller;

import com.qingshixun.project.crm.core.PageContainer;
import com.qingshixun.project.crm.module.Quotation.service.QuotationService;
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
@RequestMapping(value = "/quotation")
public class QuotationController extends BaseController {

    @Autowired
    private QuotationService quotationService;

    /**
     * 进入报价单的选择界面
     * @return
     */
    @RequestMapping("/quotation")
    public String toQuotationSelectPage(){
        return "/quotation/quotation";
    }

    /**
     * 获取可供选择的分页报价单信息
     * @param params
     * @return
     */
    @RequestMapping("/list/select")
    @ResponseBody
    public PageContainer getSelectedQuotationPage(@RequestParam Map<String,String> params){
        PageContainer pageContainer=quotationService.getSelectQuotationPage(params);
        return  pageContainer;
    }


    /**
     * 返回选择的报价单信息
     * @param quotationId
     * @return
     */
    @RequestMapping("/getSelectedQuotation//{quotationId}")
    @ResponseBody
    public ResponseData getSelectedQuotation(@PathVariable Long quotationId){
        ResponseData data=new ResponseData();
        try{
            data.setData(quotationService.getQuotationById(quotationId));
        }
        catch (Exception e){
            logger.error(e.getMessage(), e);
            data.setError(e.getMessage());
        }
        return data;
    }

}
