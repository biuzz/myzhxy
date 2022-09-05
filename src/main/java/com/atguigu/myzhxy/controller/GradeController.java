package com.atguigu.myzhxy.controller;

import com.atguigu.myzhxy.pojo.Grade;
import com.atguigu.myzhxy.service.GradeService;
import com.atguigu.myzhxy.util.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description:
 *
 * @author pt
 * @date 2022/9/1 23:23
 * @since JDK1.8
 */

@Api(tags = "年级控制器")
@RestController
@RequestMapping("/sms/gradeController")
public class GradeController {
    @Autowired
    private GradeService gradeService;


    // /sms/gradeController/getGrades
    @ApiOperation("获取全部年级")
    @RequestMapping(value = "/getGrades",method = RequestMethod.GET)
    public Result getAllGrades(){
        List<Grade> grades=gradeService.getAllGrades();

        return Result.ok(grades);
    }


    //sms/gradeController/deleteGrade
    @ApiOperation("删除Grade信息")
    @RequestMapping(value = "/deleteGrade",method = RequestMethod.DELETE)
    public Result deleteGrade(
           @ApiParam("要删除的所有grade的id的json集合") @RequestBody List<Integer> ids
    ){

        gradeService.removeByIds(ids);
        return Result.ok();

    }


    //修改和添加
    @RequestMapping(value = "/saveOrUpdateGrade",method = RequestMethod.POST)
    public Result saveOrUpdateGrade(@RequestBody Grade grade){
        //接受参数
        //调用服务层完成增加或修改
        gradeService.saveOrUpdate(grade);
        return Result.ok();
    }

    //sms/gradeController/getGrades/1/3?gradeName=
    //分页查询所有年级信息
    @RequestMapping(value = "/getGrades/{pageNo}/{pageSize}",method = RequestMethod.GET)
    public Result getGrades(
            @PathVariable("pageNo") Integer pageNo,
            @PathVariable("pageSize") Integer pageSize,
             String gradeName
    ){
        //分页带条件查询
        Page<Grade> page=new Page<>(pageNo,pageSize);
        //通过服务层查询
        IPage<Grade> pageRs= gradeService.getGradeByPage(page,gradeName);
        //封装Result对象并返回
        return Result.ok(pageRs);
    }

}
