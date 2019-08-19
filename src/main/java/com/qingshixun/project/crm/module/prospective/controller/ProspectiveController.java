package com.qingshixun.project.crm.module.prospective.controller;

import com.qingshixun.project.crm.core.PageContainer;
import com.qingshixun.project.crm.model.ProspectiveModel;
import com.qingshixun.project.crm.module.prospective.service.ProspectiveService;
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
@RequestMapping(value = "/prospective")
public class ProspectiveController extends BaseController {
    @Autowired
    private ProspectiveService prospectiveService;

    /**
     * 将请求转到/prospective/list.jsp
     * @return
     */
    @RequestMapping(value = "/list")
    public  String  toProspectivePage(){
        return "/prospective/list";
    }

    /**
     * 获取潜在客户的分页信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/list/data")
    @ResponseBody
    public PageContainer getProspectiveData(@RequestParam Map<String ,String > params){
    return prospectiveService.getProspectiveData(params);
    }


    /**
     * 将请求转到/prospective/form.jsp
     * @param prospectiveId
     * @param model
     * @return
     */
    @RequestMapping(value = "form/{prospectiveId}")
    public String toProspectiveFormPage(@PathVariable Long prospectiveId, Model model){

        ProspectiveModel prospectiveModel;
        if(prospectiveId==0L) {
            prospectiveModel=new ProspectiveModel();
        }else {
            prospectiveModel=prospectiveService.getProspectiveById(prospectiveId);
        }
        model.addAttribute("prospectiveModel",prospectiveModel);
        return "/prospective/form";
        }

    /**
     * 删除潜在用户
     * @param prospectiveId
     * @return
     */
    @RequestMapping(value = "delete/{prospectiveId}")
    @ResponseBody
    public ResponseData deleteProspective(@PathVariable Long prospectiveId) {
        ResponseData responseData = new ResponseData();
        try {
            prospectiveService.deleteProspective(prospectiveId);
        } catch (ConstraintViolationException e){
            responseData.setStatus("3");
        }
        catch (Exception e) {
            responseData.setError(e.getMessage());
            logger.error(e.getMessage());
        }
        return responseData;
    }

    /**
     * 保存潜在用户
     * @param prospectiveModel
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public ResponseData save(ProspectiveModel prospectiveModel){
        ResponseData responseData = new ResponseData();
        try {
        prospectiveService.save(prospectiveModel);
        }
        catch (Exception e) {
            responseData.setError(e.getMessage());
            logger.error(e.getMessage());
        }
        return responseData;
    }


}



