package com.qingshixun.project.crm.module.problem.controller;

import com.qingshixun.project.crm.core.PageContainer;
import com.qingshixun.project.crm.model.ProblemModel;
import com.qingshixun.project.crm.module.problem.service.ProblemService;
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
@RequestMapping(value = "/problem")
public class ProblemController extends BaseController {

    @Autowired
    private ProblemService problemService;

    /**
     * 到常见问题界面
     * @return
     */
    @RequestMapping(value = "/list")
    public String toProblemList(){
        return "/problem/list";
    }

    /**
     * 获取常见问题分页信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/list/data")
    @ResponseBody
    public PageContainer getProblemPage(@RequestParam Map<String ,String > params){
        return problemService.getProblemPage(params);
    }

    /**
     * 转到编辑常见问题的界面
     * @return
     */
    @RequestMapping(value = "/form/{problemId}")
    public String toAddOrUpdateProblem(@PathVariable Long problemId, Model model){
        ProblemModel problemModel;
        if(problemId==0L){
            problemModel=new ProblemModel();
        }
        else {
            problemModel=problemService.getProblemById(problemId);
        }
        model.addAttribute(problemModel);
        return "/problem/form";
    }

    /**
     * 保存常见问题信息
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public ResponseData saveProblem(ProblemModel problemModel){
    ResponseData responseData=new ResponseData();
    try{
        problemService.saveProblem(problemModel);
    }
    catch (Exception e){
        responseData.setError(e.getMessage());
        logger.error(e.getMessage());
    }
    return responseData;
    }

    @RequestMapping(value = "delete/{problemId}")
    @ResponseBody
    public ResponseData deleteProblem(@PathVariable Long problemId){
        ResponseData responseData=new ResponseData();
        try{
        problemService.deleteProblem(problemId);
        }
        catch (ConstraintViolationException e){
            responseData.setStatus("-1");
        }
        catch (Exception e){
            responseData.setError(e.getMessage());
            logger.error(e.getMessage());
        }
        return responseData;
    }

}
