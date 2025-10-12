package com.xitian.smarthealthhub.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.bean.ResultBean;
import com.xitian.smarthealthhub.bean.StatusCode;
import com.xitian.smarthealthhub.converter.MedicalRecordsConverter;
import com.xitian.smarthealthhub.domain.entity.MedicalRecords;
import com.xitian.smarthealthhub.domain.vo.MedicalRecordsVO;
import com.xitian.smarthealthhub.domain.query.MedicalRecordQuery;
import com.xitian.smarthealthhub.domain.vo.MedicalRecordPageVO;
import com.xitian.smarthealthhub.service.MedicalRecordsService;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/medicalRecords")
public class MedicalRecordsController {

    @Resource
    private MedicalRecordsService medicalRecordsService;

    /**
     * 分页获取病例历史归档信息
     * @param param 分页参数
     * @return 病例历史归档信息分页列表
     */
    @PostMapping("/page")
    public ResultBean<PageBean<MedicalRecordPageVO>> page(@RequestBody PageParam<MedicalRecordQuery> param) {
        if ((param.getPageSize() > 200)) {
            return ResultBean.fail(StatusCode.VALIDATION_ERROR, "每页条数不能超过 200 条");
        }
        return ResultBean.success(medicalRecordsService.page(param));
    }

    /**
     * 根据ID获取病例历史信息
     * @param id 病例历史ID
     * @return 病例历史信息
     */
    @GetMapping("/{id}")
    public ResultBean<MedicalRecordsVO> getMedicalRecordById(@PathVariable Long id) {
        MedicalRecords medicalRecords = medicalRecordsService.getById(id);
        if (medicalRecords == null) {
            return ResultBean.fail(StatusCode.DATA_NOT_FOUND);
        }
        MedicalRecordsVO vo = MedicalRecordsConverter.toVO(medicalRecords);
        return ResultBean.success(vo);
    }

    /**
     * 根据患者ID获取所有病例历史
     * @param userId 患者ID
     * @return 病例历史列表
     */
    @GetMapping("/user/{userId}")
    public ResultBean<List<MedicalRecordsVO>> getMedicalRecordsByUserId(@PathVariable Long userId) {
        LambdaQueryWrapper<MedicalRecords> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MedicalRecords::getUserId, userId);
        List<MedicalRecords> medicalRecordsList = medicalRecordsService.list(queryWrapper);
        List<MedicalRecordsVO> voList = medicalRecordsList.stream()
                .map(MedicalRecordsConverter::toVO)
                .collect(Collectors.toList());
        return ResultBean.success(voList);
    }

    /**
     * 根据医生ID获取所有病例历史
     * @param doctorId 医生ID
     * @return 病例历史列表
     */
    @GetMapping("/doctor/{doctorId}")
    public ResultBean<List<MedicalRecordsVO>> getMedicalRecordsByDoctorId(@PathVariable Long doctorId) {
        LambdaQueryWrapper<MedicalRecords> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MedicalRecords::getDoctorId, doctorId);
        List<MedicalRecords> medicalRecordsList = medicalRecordsService.list(queryWrapper);
        List<MedicalRecordsVO> voList = medicalRecordsList.stream()
                .map(MedicalRecordsConverter::toVO)
                .collect(Collectors.toList());
        return ResultBean.success(voList);
    }

    /**
     * 完成治疗并记录到历史档案
     * @param id 病例ID
     * @return 操作结果
     */
    @PutMapping("/{id}/complete")
    public ResultBean<String> completeTreatment(@PathVariable Long id) {
        MedicalRecords medicalRecords = medicalRecordsService.getById(id);
        if (medicalRecords == null) {
            return ResultBean.fail(StatusCode.DATA_NOT_FOUND);
        }
        
        // 更新状态为已完成，并设置完成时间
        medicalRecords.setStatus((byte) 1); // 1 表示已完成
        medicalRecords.setCompletedDate(java.time.LocalDateTime.now());
        medicalRecordsService.updateById(medicalRecords);
        
        return ResultBean.success("治疗已完成，记录已存入历史档案");
    }
}