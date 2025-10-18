package com.xitian.smarthealthhub.controller;

import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.bean.ResultBean;
import com.xitian.smarthealthhub.bean.StatusCode;
import com.xitian.smarthealthhub.domain.query.DoctorPageQuery;
import com.xitian.smarthealthhub.domain.query.MedicalRecordQuery;
import com.xitian.smarthealthhub.domain.query.PatientPageQuery;
import com.xitian.smarthealthhub.domain.query.UserQuery;
import com.xitian.smarthealthhub.domain.vo.DoctorPageVO;
import com.xitian.smarthealthhub.domain.vo.MedicalRecordPageVO;
import com.xitian.smarthealthhub.domain.vo.PatientPageVO;
import com.xitian.smarthealthhub.domain.vo.UserVO;
import com.xitian.smarthealthhub.service.DoctorProfilesService;
import com.xitian.smarthealthhub.service.MedicalRecordsService;
import com.xitian.smarthealthhub.service.UserProfilesService;
import com.xitian.smarthealthhub.service.UsersService;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private DoctorProfilesService doctorProfilesService;
    
    @Resource
    private UserProfilesService userProfilesService;
    
    @Resource
    private MedicalRecordsService medicalRecordsService;
    
    @Resource
    private UsersService usersService;

    /**
     * 分页获取医生信息
     * @param param 分页参数
     * @return 医生信息分页列表
     */
    @PostMapping("/doctors/page")
    public ResultBean<PageBean<DoctorPageVO>> pageDoctors(@RequestBody PageParam<DoctorPageQuery> param) {
       if ((param.getPageSize() > 200)) {
            return ResultBean.fail(StatusCode.VALIDATION_ERROR, "每页条数不能超过 200 条");
        }
        return ResultBean.success(doctorProfilesService.page(param));
    }
    
    /**
     * 分页获取患者信息
     * @param param 分页参数
     * @return 患者信息分页列表
     */
    @PostMapping("/patients/page")
    public ResultBean<PageBean<PatientPageVO>> pagePatients(@RequestBody PageParam<PatientPageQuery> param) {
        if ((param.getPageSize() > 200)) {
            return ResultBean.fail(StatusCode.VALIDATION_ERROR, "每页条数不能超过 200 条");
        }
        return ResultBean.success(userProfilesService.page(param));
    }
    
    /**
     * 分页获取病例历史归档信息
     * @param param 分页参数
     * @return 病例历史归档信息分页列表
     */
    @PostMapping("/medicalRecords/page")
    public ResultBean<PageBean<MedicalRecordPageVO>> pageMedicalRecords(@RequestBody PageParam<MedicalRecordQuery> param) {
        if ((param.getPageSize() > 200)) {
            return ResultBean.fail(StatusCode.VALIDATION_ERROR, "每页条数不能超过 200 条");
        }
        return ResultBean.success(medicalRecordsService.page(param));
    }
    
    /**
     * 分页获取用户信息
     * @param param 分页参数
     * @return 用户信息分页列表
     */
    @PostMapping("/users/page")
    public ResultBean<PageBean<UserVO>> pageUsers(@RequestBody PageParam<UserQuery> param) {
        if ((param.getPageSize() > 200)) {
            return ResultBean.fail(StatusCode.VALIDATION_ERROR, "每页条数不能超过 200 条");
        }
        return ResultBean.success(usersService.page(param));
    }
}