package com.qingshixun.project.crm.module.contact.controller;

import com.qingshixun.project.crm.core.PageContainer;
import com.qingshixun.project.crm.module.contact.service.ContactService;
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
@RequestMapping(value = "/contact")
public class ContactController extends BaseController {


    @Autowired
    private ContactService contactService;

    /**
     * 进入联系人的选择界面
     * @return
     */
    @RequestMapping("/contact")
    public String toContactPage(){
        return "/contact/contact";
    }

    /**
     * 获取可供选择的分页联系人信息
     * @param params
     * @return
     */
    @RequestMapping("/list/select")
    @ResponseBody
    public PageContainer getSelectedContactPage( @RequestParam Map<String,String> params){
        PageContainer pageContainer=contactService.getSelectContactPage(params);
        return  pageContainer;
    }


    /**
     * 返回选择的联系人信息
     * @param contactId
     * @return
     */
    @RequestMapping("/getSelectedContact/{contactId}")
    @ResponseBody
    public ResponseData getSelectedContact(@PathVariable Long contactId){
        ResponseData data=new ResponseData();
        try{
            data.setData(contactService.getContactById(contactId));
        }
        catch (Exception e){
            logger.error(e.getMessage(), e);
            data.setError(e.getMessage());
        }
        return data;
    }

}
