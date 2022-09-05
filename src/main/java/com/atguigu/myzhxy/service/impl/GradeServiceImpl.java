package com.atguigu.myzhxy.service.impl;

import com.atguigu.myzhxy.mapper.GradeMapper;
import com.atguigu.myzhxy.pojo.Grade;
import com.atguigu.myzhxy.service.GradeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Description:
 *
 * @author pt
 * @date 2022/9/1 23:12
 * @since JDK1.8
 */
@Service
@Transactional
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {
    @Override
    public IPage<Grade> getGradeByPage(Page<Grade> pageParm, String gradeName) {
        QueryWrapper<Grade> queryWrapper=new QueryWrapper();

        if (!StringUtils.isEmpty(gradeName)){
            queryWrapper.like("name",gradeName);
        }

        queryWrapper.orderByDesc("id");
        Page<Grade> page = baseMapper.selectPage(pageParm, queryWrapper);
        return page;
    }

    @Override
    public List<Grade> getAllGrades() {
        List<Grade> grades = baseMapper.selectList(null);
        return grades;
    }
}
