package com.qingshixun.project.crm.module.statistics;

import com.qingshixun.project.crm.model.ProspectiveModel;
import com.qingshixun.project.crm.module.prospective.service.ProspectiveService;
import com.qingshixun.project.crm.util.GarbledUtil;
import com.qingshixun.project.crm.web.controller.BaseController;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.util.List;

@Controller
@RequestMapping(value = "/statistics")
public class StatisticsController extends BaseController {

    @Autowired
    private ProspectiveService prospectiveService;

    /**
     * 将请求转到/statistics/prospective.jsp
     * @param model
     * @return
     */
    @RequestMapping(value = "/prospective")
    public String toProspectivePage(Model model){

        List<ProspectiveModel> list=prospectiveService.getProspectives();
        List<Object> listResource=prospectiveService.getProspectiveByResource();

        model.addAttribute("prospectiveList",list);
        model.addAttribute("lineValues",listResource);
        model.addAttribute("barValues",listResource);


        return "/statistics/prospective";
    }


    /**
     * 导出潜在客户信息
     * @param fileName
     * @param response
     */
    @RequestMapping(value = "/doExport/{fileName}")
    @ResponseBody
    public void doExportProspective(@PathVariable String fileName, HttpServletResponse response) {
        try {
            String value = "";
            // 如果乱码
            if (GarbledUtil.isMessyCode(fileName)) {
                value = fileName;
            } else {
                value = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
            }
            // 获取所有需要导出的客户信息
            List<ProspectiveModel> prospectiveList = prospectiveService.getProspectives();
            // 获取工作文档对象
            Workbook wb = prospectiveService.export(fileName, prospectiveList);
            // 设置发送到客户端的响应的内容类型
            response.setContentType("application/vnd.ms-excel");
            // 设置下载文件名称
            response.setHeader("Content-disposition", "attachment;filename=" + value + ".xlsx");
            // 获取输出流
            OutputStream ouputStream = new BufferedOutputStream(response.getOutputStream());
            // 下载文件(写输出流)
            wb.write(ouputStream);
            // 刷新流
            ouputStream.flush();
            // 关闭流
            ouputStream.close();



        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
