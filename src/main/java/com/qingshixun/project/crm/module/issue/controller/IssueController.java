package com.qingshixun.project.crm.module.issue.controller;

import com.qingshixun.project.crm.core.PageContainer;
import com.qingshixun.project.crm.model.IssueModel;
import com.qingshixun.project.crm.module.issue.service.IssueService;
import com.qingshixun.project.crm.util.ImageUtils;
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
@RequestMapping(value = "/issue")
public class IssueController extends BaseController {

    @Autowired
    private IssueService issueService;

    /**
     * 转到问题单页面
     * @return
     */
    @RequestMapping(value = "/list")
    public String  toIssueList(){
        return "/issue/list";
    }

    /**
     * 获取问题单分页信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/list/data")
    @ResponseBody
    public PageContainer getIssueData(@RequestParam Map<String, String> params) {
        return issueService.getIssueData(params);
    }

    /**
     * 将请求转到/issue/form.jsp
     * @param model
     * @param issueId
     * @return
     */
    @RequestMapping(value = "/form/{issueId}")
    public String toSaveOrUpdate(Model model, @PathVariable Long issueId){
        IssueModel issueModel;
        if(issueId==0L){
            issueModel=new IssueModel();
        }
        else{
        issueModel=issueService.getIssueById(issueId);
        }
        model.addAttribute(issueModel);
        return "/issue/form";
    }

    /**
     * 保存问题单
     * @param issue
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public ResponseData saveIssue(IssueModel issue){
        ResponseData responseData=new ResponseData();
        try {
            issueService.saveIssue(issue);
        }
        catch (Exception e){
            responseData.setError(e.getMessage());
            logger.error(e.getMessage());
        }
        return responseData;
    }

    /**
     * 删除定价单
     * @param issueId
     * @return
     */
    @RequestMapping(value = "/delete/{issueId}")
    @ResponseBody
    public ResponseData deleteIssue(@PathVariable  Long issueId){
    ResponseData responseData=new ResponseData();
    try {
        issueService.deleteIssue(issueId);
    }
    catch (org.hibernate.exception.ConstraintViolationException e){
        responseData.setStatus("-1");
        responseData.setError(e.getMessage());
    }
    catch (Exception e){
        responseData.setError(e.getMessage());
        logger.error(e.getMessage());
    }
    return responseData;
    }

}
