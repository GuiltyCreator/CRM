package com.qingshixun.project.crm.module.product.controller;


import com.qingshixun.project.crm.core.PageContainer;
import com.qingshixun.project.crm.module.product.service.ProductService;
import com.qingshixun.project.crm.util.ImageUtils;
import com.qingshixun.project.crm.web.ResponseData;
import com.qingshixun.project.crm.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping(value = "/product")
public class ProductController extends BaseController {

    @Autowired
    private ProductService productService;

    /**
     * 进入产品的选择界面
     * @return
     */
    @RequestMapping("/products")
    public String toProductsPage(Model model){
        model.addAttribute("imagePath", ImageUtils.DEFAULT_IMAGE_PATH);
        return "/product/products";
    }

    /**
     * 进入产品的选择界面
     * @return
     */
    @RequestMapping("/product")
    public String toProductPage(Model model){
        model.addAttribute("imagePath", ImageUtils.DEFAULT_IMAGE_PATH);
        return "/product/product";
    }

    /**
     * 获取可供选择的分页产品信息
     * @param params
     * @return
     */
    @RequestMapping("/list/select")
    @ResponseBody
    public PageContainer getSelectedProductPage(@RequestParam Map<String,String> params){
        PageContainer pageContainer=productService.getSelectProductPage(params);
        return  pageContainer;
    }


    /**
     * 返回选择的产品信息
     * @param ids
     * @return
     */
    @RequestMapping("/getSelectedProducts/{ids}")
    @ResponseBody
    public ResponseData getSelectedProducts(@PathVariable Long[] ids){
        ResponseData data=new ResponseData();
        try{
            data.setData(productService.getProductByIds(ids));
        }
        catch (Exception e){
            logger.error(e.getMessage(), e);
            data.setError(e.getMessage());
        }
        return data;
    }

    /**
     * 返回选择的产品信息
     * @param productId
     * @return
     */
    @RequestMapping("/getSelectedProduct/{productId}")
    @ResponseBody
    public ResponseData getSelectedProduct(@PathVariable Long productId){
        ResponseData data=new ResponseData();
        try{
            data.setData(productService.getProductById(productId));
        }
        catch (Exception e){
            logger.error(e.getMessage(), e);
            data.setError(e.getMessage());
        }
        return data;
    }



    /**
     * 获取可供查看的产品分页信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/list/problem")
    @ResponseBody
    public PageContainer getProductList(@RequestParam Map<String ,String> params){
    return productService.getProductList(params);
    }

}

