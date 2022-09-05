package com.atguigu.myzhxy.controller;

import com.atguigu.myzhxy.pojo.Student;
import com.atguigu.myzhxy.service.StudentService;
import com.atguigu.myzhxy.util.MD5;
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
@Api(tags = "学生管理器")
@RestController
@RequestMapping("/sms/studentController")
public class StudentController {

    @Autowired
    private StudentService studentService;


    // DELETE
    //	http://localhost:9001/sms/studentController/delStudentById
    @ApiOperation("删除单个或者多个学生信息")
    @RequestMapping(value = "/delStudentById",method = RequestMethod.DELETE)
    public Result delStudentById(
            @ApiParam("要删除的学生编号的JSON数组") @RequestBody List<Integer> ids
    ){
        studentService.removeByIds(ids);
        return Result.ok();
    }


    @ApiOperation("保存或者修改学生信息")
    @RequestMapping(value = "/addOrUpdateStudent",method = RequestMethod.POST)
    public Result addOrUpdateStudent(@ApiParam("要保存或修改的学生JSON") @RequestBody Student student){

        Integer id = student.getId();
        //判断id是不是为空，如果为空则为添加操作，需要把密码转换成md5格式
        if (null==id || 0==id){
            student.setPassword(MD5.encrypt(student.getPassword()));
        }
        studentService.saveOrUpdate(student);
        return Result.ok();
    }



    @ApiOperation("分页带条件查询学生信息")
    @RequestMapping(value = "/getStudentByOpr/{pageNo}/{pageSize}",method = RequestMethod.GET)
    public Result getStudentByPage(
            @ApiParam("分页查询的页码数") @PathVariable("pageNo") Integer pageNo,
            @ApiParam("分页查询页显示条数") @PathVariable("pageSize") Integer pageSize,
            @ApiParam("班级名称") String clazzName,
            @ApiParam("学生名称") String name
    ){
        //分页信息封装Page对象
        Page<Student> page=new Page<>(pageNo,pageSize);
        IPage<Student> iPage =studentService.getStudentByPage(page,clazzName,name);


        return Result.ok(iPage);
    }
}
