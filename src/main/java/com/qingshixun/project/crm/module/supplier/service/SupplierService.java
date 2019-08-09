package com.qingshixun.project.crm.module.supplier.service;

import com.qingshixun.project.crm.core.BaseService;
import com.qingshixun.project.crm.core.PageContainer;
import com.qingshixun.project.crm.model.SupplierModel;
import com.qingshixun.project.crm.module.supplier.dao.SupplierDao;
import com.qingshixun.project.crm.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

@Service
@Transactional
public class SupplierService extends BaseService {
    @Autowired
    private SupplierDao supplierDao;

    /**
     * 获取供应商的分页信息
     * @param params
     * @return
     */
    public PageContainer getSupplierData(Map<String ,String> params){
        return supplierDao.getDataPage(params);
    }

    /**
     * 获取可供选择的供应商分页信息
     * @param params
     * @return
     */
    public PageContainer getSelectSupplierData(Map<String ,String> params){
        return supplierDao.getDataPage(params);
    }

    /**
     * 根据id查找供应商
     * @param id
     * @return
     */
    public SupplierModel getSupplierById(Long id){
        return supplierDao.get(id);
    }

    /**
     * 删除供应商
     * @param id
     */
    public void deleteSupplier(Long id){
        supplierDao.delete(id);
    }

    /**
     * 保存供应商
     * @param supplierModel
     */
    public void saveSupplier(SupplierModel supplierModel){
        String time= DateUtils.timeToString(new Date());
        if("".equals(supplierModel.getCode())){
            supplierModel.setCode("SUP"+System.currentTimeMillis());
        }
        supplierModel.setUpdateTime(time);
        supplierDao.save(supplierModel);
    }

}
