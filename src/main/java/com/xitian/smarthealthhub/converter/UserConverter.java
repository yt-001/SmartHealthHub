package com.xitian.smarthealthhub.converter;

import com.xitian.smarthealthhub.domain.entity.Users;
import com.xitian.smarthealthhub.domain.vo.UserVO;
import com.xitian.smarthealthhub.utils.DesensitizeUtil;

public class UserConverter {
    /**
     * Entity -> VO  手动 setter，零反射
     */
    public static UserVO toVO(Users entity) {
        if (entity == null) {
            return null;
        }

        UserVO vo = new UserVO();

        // BaseVO 字段映射
        vo.setId(entity.getId());
        vo.setCreateTime(entity.getCreatedAt());
        vo.setUpdateTime(entity.getUpdatedAt());

        // UserVO 字段映射
        vo.setUsername(entity.getUsername());
        vo.setAvatarUrl(entity.getAvatarUrl());
        vo.setRealName(entity.getRealName());
        vo.setRole(entity.getRole());
        vo.setPhone(DesensitizeUtil.phone(entity.getPhone()));
        vo.setEmail(DesensitizeUtil.email(entity.getEmail()));
        vo.setBirthDate(entity.getBirthDate());
        vo.setGender(entity.getGender());
        vo.setStatus(entity.getStatus());

        return vo;
    }
}
