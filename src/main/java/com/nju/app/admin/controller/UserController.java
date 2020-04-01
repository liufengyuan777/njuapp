package com.nju.app.admin.controller;

import com.nju.app.dao.UserDao;
import com.nju.app.entities.Result;
import com.nju.app.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Controller()
public class UserController {

    @Resource
    UserDao userDao;

    @ResponseBody
    @RequestMapping("user/findAllUsers")
    public List<User> findAllUsers() {
        return userDao.findAll();
    }

    @ResponseBody
    @RequestMapping("user/findById")
    public User findById(@RequestParam("id") Integer id) {
        Optional<User> user = userDao.findById(id);

        System.out.print(user.get());

        return user.get();
    }

    @ResponseBody
    @RequestMapping("user/findByUsername")
    public User findByUsername(@RequestParam("username") String username) {

        return userDao.findByUsername(username);
    }

    @ResponseBody
    @RequestMapping("user/update")
    public Result updateUser(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             @RequestParam("type") String type) {

        User user = userDao.findByUsername(username);
        user.setPassword(password);
        user.setType(type);

        try {
            userDao.saveAndFlush(user);
            return new Result(true, "更新数据成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "发生未知错误");
        }
    }

    @ResponseBody
    @RequestMapping("user/create")
    public Result createUser(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             @RequestParam("type") String type){

        User user = userDao.findByUsername(username);

        if (user == null){
            user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setType(type);

            try {
                userDao.saveAndFlush(user);
                return new Result(true, "创建成功");
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(false, "发生未知错误");
            }
        }else{
            return new Result(false, "用户已存在");
        }

    }

//    @ResponseBody
//    @RequestMapping("user/create")
//    public Result createUser(@RequestParam("username") String username,
//                            @RequestParam("password") String password,
//                            @RequestParam("type") String type){
//
//        return userService.create(username,password,type);
//
//    }

    @ResponseBody
    @RequestMapping("user/delete")
    public Result deleteUser(@RequestParam("id") Integer id){

        //User user = userDao.findByUsername(username);
        try {
            userDao.deleteById(id);
            //userDao.delete(user);
            return new Result(true, "更新数据成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "发生未知错误");
        }
    }

    @ResponseBody
    @RequestMapping("user/deleteUsers")
    public Result deleteUsers(@RequestBody Integer ...ids){

        //User user = userDao.findByUsername(username);
        try {

            for (Integer id : ids){
                userDao.deleteById(id);
            }

            return new Result(true, "更新数据成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "发生未知错误");
        }
    }


    public void register(){

    }

    @GetMapping("/user")
    public String user(){
        return "admin/user";
    }

}
