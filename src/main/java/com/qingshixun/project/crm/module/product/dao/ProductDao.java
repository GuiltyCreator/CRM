package com.qingshixun.project.crm.module.product.dao;


import com.qingshixun.project.crm.core.BaseHibernateDao;
import com.qingshixun.project.crm.core.PageContainer;
import com.qingshixun.project.crm.model.ProductModel;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ProductDao extends BaseHibernateDao<ProductModel, Long> {

    /**
     * 查询所有可供选择的分页产品信息
     * @param
     * @return
     */
    public PageContainer getSelectProductPage(Map<String ,String> params, List<Long> list){
        Criterion productName = createCriterion("status.code", "PROS_Sale");
        Criterion gtValue = createGtCriterion("inventory", 0);
        Criterion notInIds = createNotInCriterion("id", list);
        return getDataPage(params, productName, gtValue, notInIds);
    }

    /**
     * 查询选择的产品信息
     * @param ides
     * @return
     */
    public List<ProductModel> getSelectProductByIds(Long[] ides){

        return find(createInCriterion("id",ides));

    }

    /**
     * 获取可供查看的产品分页信息
     * @param params
     * @return
     */
    public PageContainer getProductList(Map<String,String> params){

        Criterion criterion=createCriterion("status.code", "PROS_Sale");
        return getDataPage(params,criterion);
    }

}