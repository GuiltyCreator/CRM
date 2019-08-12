package com.qingshixun.project.crm.module.prospective.service;

import com.qingshixun.project.crm.core.BaseService;
import com.qingshixun.project.crm.core.PageContainer;
import com.qingshixun.project.crm.model.CustomerResource;
import com.qingshixun.project.crm.model.ProspectiveModel;
import com.qingshixun.project.crm.module.prospective.dao.ProspectiveDao;
import com.qingshixun.project.crm.util.DateUtils;
import com.qingshixun.project.crm.util.Poi4Excel;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ProspectiveService extends BaseService {

    @Autowired
    private ProspectiveDao prospectiveDao;

    /**
     * 获取潜在客户的分页信息
     * @param params
     * @return
     */
    public PageContainer getProspectiveData(Map<String ,String > params){
        return prospectiveDao.getDataPage(params);
    }

    /**
     * 根据ID查找潜在客户
     * @param id
     * @return
     */
    public ProspectiveModel getProspectiveById(Long id){
        return prospectiveDao.get(id);
    }

    /**
     * 删除潜在客户
     * @param id
     */
    public void deleteProspective(Long id){
        prospectiveDao.delete(id);
    }

    /**
     * 保存潜在用户
     * @param prospectiveModel
     */
    public void save(ProspectiveModel prospectiveModel){
        if(prospectiveModel.getCode().equals("")){
            prospectiveModel.setCode("PRS"+System.currentTimeMillis());
        }
        prospectiveModel.setUpdateTime(DateUtils.timeToString(new Date()));
        prospectiveDao.save(prospectiveModel);
    }

    /**
     * 获得所有潜在客户
     * @return
     */
    public List<ProspectiveModel> getProspectives(){
        return prospectiveDao.getAll();
    }

    /**
     * 获取潜在客户来源
     * @return
     */
    public List<Object> getProspectiveByResource (){
       /* List<ProspectiveModel> listT=prospectiveDao.getProspectiveByResource("CUSTOMER_Telemarketing");
        List<ProspectiveModel> listEX=prospectiveDao.getProspectiveByResource("CUSTOMER_Existing");
        List<ProspectiveModel> listEm=prospectiveDao.getProspectiveByResource("CUSTOMER_Emaimarketing");
        List<ProspectiveModel> listO=prospectiveDao.getProspectiveByResource("CUSTOMER_Other");*/

        List<ProspectiveModel> listT=prospectiveDao.getProspectiveByResource(CustomerResource.telemarketing.getCode());
        List<ProspectiveModel> listEX=prospectiveDao.getProspectiveByResource(CustomerResource.existing.getCode());
        List<ProspectiveModel> listEm=prospectiveDao.getProspectiveByResource(CustomerResource.emaimarketing.getCode());
        List<ProspectiveModel> listO=prospectiveDao.getProspectiveByResource(CustomerResource.other.getCode());

        List<Object> list=new ArrayList<>();
        list.add(listT.size());
        list.add(listEX.size());
        list.add(listEm.size());
        list.add(listO.size());
        return list;
    }

    /**
     * excel导出潜在客户
     * @return Workbook 工作文档对象
     */
    public Workbook export(String fileName, List<ProspectiveModel> prospectiveList) throws IOException {
        // excel格式是.xlsx
        String fileType = "xlsx";
        // 导出excel需要的数据格式
        ArrayList<ArrayList<String>> list = new ArrayList<>();
        // 导出excel第一行标题数据
        ArrayList<String> listTitle = new ArrayList<>();
        // 存放标题顺序
        listTitle.add("潜在客户编号");
        listTitle.add("姓名");
        listTitle.add("手机");
        listTitle.add("来源");
        listTitle.add("状态");
        // 将标题数据放在excel数据
        list.add(listTitle);
        for (int i = 0; i < prospectiveList.size(); i++) {
            // 存放excel内容数据
            ArrayList<String> listBody = new ArrayList<>();
            // 存放数据顺序和存放标题顺序对应
            listBody.add(prospectiveList.get(i).getCode());
            listBody.add(prospectiveList.get(i).getName());
            listBody.add(prospectiveList.get(i).getMobile());
            listBody.add(prospectiveList.get(i).getResource().getName());
            listBody.add(prospectiveList.get(i).getStatus().getName());
            list.add(listBody);
        }
        // 调用公共类的导出方法
        return Poi4Excel.writer(fileName, fileType, list);
    }


}
