package com.qingshixun.project.crm.module.problem.dao;

import com.qingshixun.project.crm.core.BaseHibernateDao;
import com.qingshixun.project.crm.core.PageContainer;
import com.qingshixun.project.crm.model.ProblemModel;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class ProblemDao extends BaseHibernateDao<ProblemModel,Long> {

    /**
     * 获取所有常见问题信息
     * @param params
     * @return
     */
    public PageContainer getProblemPage(Map<String ,String > params){
        Criterion criterion=createLikeCriterion("problem","%"+params.get("problem")+"%");
        return getDataPage(params,criterion);
    }

}
