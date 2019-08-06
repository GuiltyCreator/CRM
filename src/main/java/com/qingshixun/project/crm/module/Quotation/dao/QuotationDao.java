package com.qingshixun.project.crm.module.Quotation.dao;

import com.qingshixun.project.crm.core.BaseHibernateDao;
import com.qingshixun.project.crm.core.PageContainer;
import com.qingshixun.project.crm.model.QuotationModel;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class QuotationDao extends BaseHibernateDao<QuotationModel,Long> {
    /**
     * 查询所有可供选择的分页报价单信息
     * @param
     * @return
     */
    public PageContainer getSelectQuotationPage(Map<String ,String> params){
        return getDataPage(params);
    }
}
