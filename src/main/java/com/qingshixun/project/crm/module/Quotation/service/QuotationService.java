package com.qingshixun.project.crm.module.Quotation.service;

import com.qingshixun.project.crm.core.BaseService;
import com.qingshixun.project.crm.core.PageContainer;
import com.qingshixun.project.crm.model.QuotationModel;
import com.qingshixun.project.crm.module.Quotation.dao.QuotationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;

@Service
@Transactional
public class QuotationService extends BaseService {
    @Autowired
    private QuotationDao quotationDao;


    /**
     * 获取所有可供选择的分页报价单信息
     * @return
     */
    public PageContainer getSelectQuotationPage(Map<String ,String > params){
        return quotationDao.getSelectQuotationPage(params);
    }

    /**
     * 根据id查找报价单
     * @param id
     * @return
     */
    public QuotationModel getQuotationById(Long id){
        return quotationDao.get(id);
    }


}
