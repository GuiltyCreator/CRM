package com.qingshixun.project.crm.module.product.service;

import com.qingshixun.project.crm.core.BaseService;
import com.qingshixun.project.crm.core.PageContainer;
import com.qingshixun.project.crm.model.ProductModel;
import com.qingshixun.project.crm.module.product.dao.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class ProductService extends BaseService {
    @Autowired
    private ProductDao productDao;

    /**
     * 获取所有可供选择的产品信息
     * @return
     */
    public PageContainer getSelectProductPage(Map<String ,String > params){
        String[] productIds = params.get("productIds").toString().split(",");
        List<Long> list = new ArrayList<>();
        for (int i = 0; i < productIds.length; i++) {
            if ("".equals(productIds[i])) {
                list.add(0L);
            } else {
                list.add(Long.parseLong(productIds[i]));
            }
        }
        return productDao.getSelectProductPage(params, list);
    }

    /**
     * 根据id数组查找产品
     * @param ids
     * @return
     */
    public List<ProductModel> getProductByIds(Long[] ids){
        return productDao.getSelectProductByIds(ids);
    }

}