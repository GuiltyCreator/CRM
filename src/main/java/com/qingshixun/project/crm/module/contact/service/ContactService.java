package com.qingshixun.project.crm.module.contact.service;

import com.qingshixun.project.crm.core.BaseService;
import com.qingshixun.project.crm.core.PageContainer;
import com.qingshixun.project.crm.model.ContactModel;
import com.qingshixun.project.crm.module.contact.dao.ContactDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ContactService extends BaseService {

    @Autowired
    private ContactDao contactDao;
    /**
     * 获取所有可供选择的分页联系人信息
     * @return
     */
    public PageContainer getSelectContactPage(Map<String ,String > params){
        return contactDao.getSelectContactPage(params);
    }

    /**
     * 根据id查找联系人
     * @param id
     * @return
     */
    public ContactModel getContactById(Long id){
        return contactDao.get(id);
    }

}
