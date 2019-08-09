package com.qingshixun.project.crm.module.problem.service;

import com.qingshixun.project.crm.core.BaseService;
import com.qingshixun.project.crm.core.PageContainer;
import com.qingshixun.project.crm.model.ProblemModel;
import com.qingshixun.project.crm.module.problem.dao.ProblemDao;
import com.qingshixun.project.crm.module.product.dao.ProductDao;
import com.qingshixun.project.crm.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

@Service
@Transactional
public class ProblemService extends BaseService {

    @Autowired
    private ProblemDao problemDao;

    /**
     * 获取常见问题的分页信息
     * @param params
     * @return
     */
    public PageContainer getProblemPage(Map<String ,String > params){
    return problemDao.getProblemPage(params);
    }

    /**
     * 根据常见问题id获取对象
     * @param id
     * @return
     */
    public ProblemModel getProblemById(Long id){
        return problemDao.get(id);
    }

    /**
     * 保存常见问题
     * @param problemModel
     */
    public void saveProblem(ProblemModel problemModel){
        String time= DateUtils.timeToString(new Date());
        if(problemModel.getCode().equals("")){
            problemModel.setCode("PRO"+System.currentTimeMillis());
        }
        problemModel.setUpdateTime(time);
        problemDao.save(problemModel);
    }

    /**
     * 删除常见问题
     * @param id
     */
    public void deleteProblem(Long id){
        problemDao.delete(id);
    }

}
