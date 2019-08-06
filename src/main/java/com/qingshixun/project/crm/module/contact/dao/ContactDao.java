package com.qingshixun.project.crm.module.contact.dao;

import com.qingshixun.project.crm.core.BaseHibernateDao;
import com.qingshixun.project.crm.core.PageContainer;
import com.qingshixun.project.crm.model.ContactModel;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class ContactDao extends BaseHibernateDao<ContactModel,Long> {

    /**
     * 查询所有可供选择的分页联系人信息
     * @param
     * @return
     */
    public PageContainer getSelectContactPage(Map<String ,String> params){
        return getDataPage(params);
    }

}
