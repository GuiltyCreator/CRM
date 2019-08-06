package com.qingshixun.project.crm.module.customer.service;

import com.qingshixun.project.crm.core.BaseService;
import com.qingshixun.project.crm.core.PageContainer;
import com.qingshixun.project.crm.model.CustomerModel;
import com.qingshixun.project.crm.module.customer.dao.CustomerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;

@Service
@Transactional
public class CustomerService  extends BaseService {

    @Autowired
    private CustomerDao customerDao;


    /**
     * 获取所有可供选择的分页客户信息
     * @return
     */
    public PageContainer getSelectCustomerPage(Map<String ,String > params){
        return customerDao.getSelectCustomerPage(params);
    }

    /**
     * 根据id查找客户
     * @param id
     * @return
     */
    public CustomerModel getCustomerById(Long id){
        return customerDao.get(id);
    }
}
