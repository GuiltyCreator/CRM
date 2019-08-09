package com.qingshixun.project.crm.module.issue.dao;

import com.qingshixun.project.crm.core.BaseHibernateDao;
import com.qingshixun.project.crm.core.PageContainer;
import com.qingshixun.project.crm.model.IssueModel;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Repository
public class IssueDao extends BaseHibernateDao<IssueModel,Long> {

    /**
     * 获取问题单分页信息。
     * @param params
     * @return
     */
    public PageContainer getIssueData(Map<String ,String> params){
        Criterion criterion=createLikeCriterion("title","%"+params.get("title")+"%");
        return getDataPage(params,criterion);
    }


}
