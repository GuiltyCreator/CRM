package com.qingshixun.project.crm.module.OpportunityController.controller;

import com.qingshixun.project.crm.core.PageContainer;
import com.qingshixun.project.crm.module.OpportunityController.service.OpportunityService;
import com.qingshixun.project.crm.web.ResponseData;
import com.qingshixun.project.crm.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping(value = "/opportunity")
public class OpportunityController extends BaseController {

    @Autowired
    private OpportunityService opportunityService;

    /**
     * 进入销售机会的选择界面
     * @return
     */
    @RequestMapping("/opportunity")
    public String toOpportunitySelectPage(){
        return "/opportunity/opportunity";
    }

    /**
     * 获取可供选择的分页销售机会信息
     * @param params
     * @return
     */
    @RequestMapping("/list/select")
    @ResponseBody
    public PageContainer getSelectedOpportunityPage( @RequestParam Map<String,String>params){
        PageContainer pageContainer=opportunityService.getSelectOpportunityPage(params);
        return  pageContainer;
    }


    /**
     * 返回选择的销售机会信息
     * @param opportunityId
     * @return
     */
    @RequestMapping("/getSelectedOpportunity/{opportunityId}")
    @ResponseBody
    public ResponseData getSelectedOpportunity(@PathVariable Long opportunityId){
            ResponseData data=new ResponseData();
            try{
                data.setData(opportunityService.getOpportunityById(opportunityId));
            }
            catch (Exception e){
                logger.error(e.getMessage(), e);
                data.setError(e.getMessage());
            }
            return data;
    }
}
