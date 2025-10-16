package com.xitian.smarthealthhub.controller;

import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.bean.ResultBean;
import com.xitian.smarthealthhub.bean.StatusCode;
import com.xitian.smarthealthhub.converter.UserConverter;
import com.xitian.smarthealthhub.domain.dto.UserUpdateDTO;
import com.xitian.smarthealthhub.domain.entity.Users;
import com.xitian.smarthealthhub.domain.query.UserQuery;
import com.xitian.smarthealthhub.domain.vo.UserVO;
import com.xitian.smarthealthhub.service.UsersService;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Resource
    private UsersService usersService;

    /**
     * 根据ID获取用户信息
     * @param id 用户ID
     * @return 用户信息
     */
    @GetMapping("/{id}")
    public ResultBean<UserVO> getUserById(@PathVariable Long id) {
        Users user = usersService.getById(id);
        if (user == null) {
            return ResultBean.fail(StatusCode.DATA_NOT_FOUND);
        }
        UserVO vo = UserConverter.toVO(user);
        return ResultBean.success(vo);
    }

    /**
     * 更新用户信息
     * @param userUpdateDTO 用户更新信息
     * @return 更新结果
     */
    @PutMapping
    public ResultBean<String> updateUser(@Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        // 调用服务层更新用户信息
        boolean updated = usersService.updateUserInfo(userUpdateDTO);
        if (updated) {
            return ResultBean.success("用户信息更新成功");
        } else {
            return ResultBean.fail(StatusCode.INTERNAL_SERVER_ERROR, "用户信息更新失败");
        }
    }
}