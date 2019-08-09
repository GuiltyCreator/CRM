package com.qingshixun.project.crm.module.issue.service;

import com.qingshixun.project.crm.core.BaseService;
import com.qingshixun.project.crm.core.PageContainer;
import com.qingshixun.project.crm.model.IssueModel;
import com.qingshixun.project.crm.module.issue.dao.IssueDao;
import com.qingshixun.project.crm.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

@Service
@Transactional
public class IssueService extends BaseService {

    @Autowired
    private IssueDao issueDao;

    /**
     * 获取问题单分页信息
     * @param params
     * @return
     */
    public PageContainer getIssueData(Map<String,String> params){
        return issueDao.getIssueData(params);
    }

    /**
     * 根据id查找问题单
     * @param issueId
     * @return
     */
    public IssueModel getIssueById(Long issueId){
        return issueDao.get(issueId);
    }

    /**
     * 保存问题单
     * @param issue
     */
    public void saveIssue(IssueModel issue){
        String time=DateUtils.timeToString(new Date());
        if(issue.getCode().equals("")){
            issue.setCode("ISS"+System.currentTimeMillis());
        }
        issue.setUpdateTime(time);
        issueDao.save(issue);
    }

    /**
     * 删除问题单
     * @param issueId
     */
    public void deleteIssue(Long issueId){
        issueDao.delete(issueId);
    }


}
