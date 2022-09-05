package com.atguigu.myzhxy.controller;

import com.atguigu.myzhxy.pojo.Clazz;
import com.atguigu.myzhxy.service.ClazzService;
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
@Api(tags = "班级管理器")
@RestController
@RequestMapping("/sms/clazzController")
public class ClazzController {

    @Autowired
    private ClazzService clazzService;



    @ApiOperation("查询所有班级信息")
    @RequestMapping(value = "/getClazzs",method = RequestMethod.GET)
    public Result getAllClazz(){
        List<Clazz> clazz=clazzService.getAllClazz();
        return Result.ok(clazz);
    }

    @ApiOperation("删除单个和多个班级的方法")
    @RequestMapping(value = "/deleteClazz",method = RequestMethod.DELETE)
    public Result deleteClazz(
           @ApiParam("要删除的多个班级的ID的JSON数组") @RequestBody List<Integer> ids
    ){
        clazzService.removeByIds(ids);
        return Result.ok();
    }


    // /sms/clazzController/saveOrUpdateClazz
    @ApiOperation("增加或者修改班级信息")
    @RequestMapping(value = "/saveOrUpdateClazz",method = RequestMethod.POST)
    public Result saveOrUpdateClazz(
           @ApiParam("JSON格式的班级信息") @RequestBody Clazz clazz
    ){
        clazzService.saveOrUpdate(clazz);
        return Result.ok();
    }




    //  sms/clazzController/getClazzsByOpr/1/3?gradeName=&name=
    @ApiOperation("分页带条件查询班级信息")
    @RequestMapping(value = "/getClazzsByOpr/{pageNo}/{pageSize}",method = RequestMethod.GET)
    public Result getClazzByOpr(
            @ApiParam("分页查询的页码数") @PathVariable("pageNo") Integer pageNo,
            @ApiParam("分页查询页显示条数") @PathVariable("pageSize") Integer pageSize,
            @ApiParam("年级名称") String gradeName,//年级名称
            @ApiParam("班级名称") String name//班级名称
    ){
        //分页带条件查询
        Page<Clazz> page = new Page<>(pageNo,pageSize);
        IPage<Clazz> pageRs = clazzService.getClazzByPage(page, gradeName, name);
        return Result.ok(pageRs);
    }
}
