package com.xitian.smarthealthhub.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.bean.ResultBean;
import com.xitian.smarthealthhub.bean.StatusCode;
import com.xitian.smarthealthhub.domain.entity.DoctorProfiles;
import com.xitian.smarthealthhub.domain.entity.UserProfiles;
import com.xitian.smarthealthhub.domain.entity.Users;
import com.xitian.smarthealthhub.domain.query.DoctorVerificationQuery;
import com.xitian.smarthealthhub.domain.query.PatientVerificationQuery;
import com.xitian.smarthealthhub.domain.vo.DoctorPageVO;
import com.xitian.smarthealthhub.domain.vo.DoctorVerificationVO;
import com.xitian.smarthealthhub.domain.vo.PatientPageVO;
import com.xitian.smarthealthhub.domain.vo.PatientVerificationVO;
import com.xitian.smarthealthhub.service.DoctorProfilesService;
import com.xitian.smarthealthhub.service.UserProfilesService;
import com.xitian.smarthealthhub.service.UsersService;
import com.xitian.smarthealthhub.converter.DoctorPageVOConverter;
import com.xitian.smarthealthhub.converter.PatientPageVOConverter;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 认证审核控制器
 * 用于处理医生和患者的资质认证审核
 */
@RestController
@RequestMapping("/admin/verification")
public class VerificationController {

    @Resource
    private DoctorProfilesService doctorProfilesService;
    
    @Resource
    private UserProfilesService userProfilesService;
    
    @Resource
    private UsersService usersService;

    /**
     * 分页获取待审核的医生列表
     * @param param 分页参数
     * @return 医生信息分页列表
     */
    @PostMapping("/doctors/pending")
    public ResultBean<PageBean<DoctorVerificationVO>> pagePendingDoctors(@RequestBody PageParam<DoctorVerificationQuery> param) {
        if ((param.getPageSize() > 200)) {
            return ResultBean.fail(StatusCode.VALIDATION_ERROR, "每页条数不能超过 200 条");
        }
        
        return ResultBean.success(doctorProfilesService.pagePendingDoctors(param));
    }
    
    /**
     * 分页获取待审核的患者列表
     * @param param 分页参数
     * @return 患者信息分页列表
     */
    @PostMapping("/patients/pending")
    public ResultBean<PageBean<PatientVerificationVO>> pagePendingPatients(@RequestBody PageParam<PatientVerificationQuery> param) {
        if ((param.getPageSize() > 200)) {
            return ResultBean.fail(StatusCode.VALIDATION_ERROR, "每页条数不能超过 200 条");
        }
        
        return ResultBean.success(userProfilesService.pagePendingPatients(param));
    }
    
    /**
     * 审核医生资质
     * @param doctorId 医生档案ID
     * @param status 审核状态：1 通过，0 拒绝
     * @return 操作结果
     */
    @PutMapping("/doctors/{doctorId}/verify")
    public ResultBean<String> verifyDoctor(@PathVariable Long doctorId, 
                                          @RequestParam Byte status) {
        // 检查状态参数是否合法
        if (status != 0 && status != 1) {
            return ResultBean.fail(StatusCode.VALIDATION_ERROR, "审核状态只能是0（拒绝）或1（通过）");
        }
        
        // 查找医生档案
        DoctorProfiles doctorProfile = doctorProfilesService.getById(doctorId);
        if (doctorProfile == null) {
            return ResultBean.fail(StatusCode.DATA_NOT_FOUND, "医生档案不存在");
        }
        
        // 更新医生资质认证状态
        LambdaUpdateWrapper<DoctorProfiles> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(DoctorProfiles::getId, doctorId)
                    .set(DoctorProfiles::getQualificationVerified, status);
        boolean updated = doctorProfilesService.update(updateWrapper);
        
        if (updated) {
            // 如果审核通过，同时更新用户状态为正常（0）
            if (status == 1) {
                LambdaUpdateWrapper<Users> userUpdateWrapper = new LambdaUpdateWrapper<>();
                userUpdateWrapper.eq(Users::getId, doctorProfile.getUserId())
                                .set(Users::getStatus, (byte) 0); // 设置为正常状态
                usersService.update(userUpdateWrapper);
            }
            
                return ResultBean.success(status == 1 ? "医生资质审核通过" : "医生资质审核拒绝");
        } else {
            return ResultBean.fail(StatusCode.INTERNAL_SERVER_ERROR, "审核操作失败");
        }
    }
    
    /**
     * 审核患者实名认证
     * @param patientId 患者档案ID
     * @param status 审核状态：1 通过，0 拒绝
     * @return 操作结果
     */
    @PutMapping("/patients/{patientId}/verify")
    public ResultBean<String> verifyPatient(@PathVariable Long patientId, 
                                           @RequestParam Byte status) {
        // 检查状态参数是否合法
        if (status != 0 && status != 1) {
            return ResultBean.fail(StatusCode.VALIDATION_ERROR, "审核状态只能是0（拒绝）或1（通过）");
        }
        
        // 查找患者档案
        UserProfiles userProfile = userProfilesService.getById(patientId);
        if (userProfile == null) {
            return ResultBean.fail(StatusCode.DATA_NOT_FOUND, "患者档案不存在");
        }
        
        // 更新患者实名认证状态
        LambdaUpdateWrapper<UserProfiles> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserProfiles::getId, patientId)
                    .set(UserProfiles::getIdCardVerified, status);
        boolean updated = userProfilesService.update(updateWrapper);
        
        if (updated) {
            // 如果审核通过，同时更新用户状态为正常（0）
            if (status == 1) {
                LambdaUpdateWrapper<Users> userUpdateWrapper = new LambdaUpdateWrapper<>();
                userUpdateWrapper.eq(Users::getId, userProfile.getUserId())
                                .set(Users::getStatus, (byte) 0); // 设置为正常状态
                usersService.update(userUpdateWrapper);
            }
            
            return ResultBean.success(status == 1 ? "患者实名认证审核通过" : "患者实名认证审核拒绝");
        } else {
            return ResultBean.fail(StatusCode.INTERNAL_SERVER_ERROR, "审核操作失败");
        }
    }
}