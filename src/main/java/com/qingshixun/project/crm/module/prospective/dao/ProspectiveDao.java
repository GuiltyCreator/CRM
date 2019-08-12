package com.qingshixun.project.crm.module.prospective.dao;

import com.qingshixun.project.crm.core.BaseHibernateDao;
import com.qingshixun.project.crm.core.PageContainer;
import com.qingshixun.project.crm.model.ProspectiveModel;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ProspectiveDao extends BaseHibernateDao<ProspectiveModel,Long> {

    /**
     * 获取潜在客户的分页信息
     * @param params
     * @return
     */
    public PageContainer getProspectiveData( Map<String ,String > params){
        Criterion criterion=createLikeCriterion("name","%"+params.get("name")+"%");
        return getDataPage(params,criterion);
    }

    /**
     * 通过来源寻找潜在客户
     * @param resource
     * @return
     */
    public List<ProspectiveModel> getProspectiveByResource(String resource){
        Criterion criterion=createLikeCriterion("resource.code","%"+resource+"%");
        return find(criterion);
    }

}
