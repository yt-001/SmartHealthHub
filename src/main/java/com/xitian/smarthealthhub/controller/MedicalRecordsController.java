package com.xitian.smarthealthhub.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xitian.smarthealthhub.bean.ResultBean;
import com.xitian.smarthealthhub.bean.StatusCode;
import com.xitian.smarthealthhub.converter.MedicalRecordDetailConverter;
import com.xitian.smarthealthhub.converter.MedicalRecordListItemConverter;
import com.xitian.smarthealthhub.domain.entity.MedicalRecords;
import com.xitian.smarthealthhub.domain.vo.MedicalRecordDetailVO;
import com.xitian.smarthealthhub.domain.vo.MedicalRecordListItemVO;
import com.xitian.smarthealthhub.service.MedicalRecordsService;
import com.xitian.smarthealthhub.domain.dto.MedicalRecordSaveDTO;
import org.springframework.beans.BeanUtils;
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
     * 根据ID获取病例历史信息（详情页）
     * @param id 病例历史ID
     * @return 病例历史信息
     */
    @GetMapping("/{id}")
    public ResultBean<MedicalRecordDetailVO> getMedicalRecordById(@PathVariable Long id) {
        MedicalRecords medicalRecords = medicalRecordsService.getById(id);
        if (medicalRecords == null) {
            return ResultBean.fail(StatusCode.DATA_NOT_FOUND);
        }
        MedicalRecordDetailVO vo = MedicalRecordDetailConverter.toVO(medicalRecords);
        return ResultBean.success(vo);
    }

    /**
     * 根据患者ID获取所有病例历史（列表页）
     * @param userId 患者ID
     * @return 病例历史列表
     */
    @GetMapping("/user/{userId}")
    public ResultBean<List<MedicalRecordListItemVO>> getMedicalRecordsByUserId(@PathVariable Long userId) {
        LambdaQueryWrapper<MedicalRecords> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MedicalRecords::getUserId, userId);
        List<MedicalRecords> medicalRecordsList = medicalRecordsService.list(queryWrapper);
        List<MedicalRecordListItemVO> voList = MedicalRecordListItemConverter.toVOList(medicalRecordsList);
        return ResultBean.success(voList);
    }

    /**
     * 根据医生ID获取所有病例历史（列表页）
     * @param doctorId 医生ID
     * @return 病例历史列表
     */
    @GetMapping("/doctor/{doctorId}")
    public ResultBean<List<MedicalRecordListItemVO>> getMedicalRecordsByDoctorId(@PathVariable Long doctorId) {
        LambdaQueryWrapper<MedicalRecords> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MedicalRecords::getDoctorId, doctorId);
        List<MedicalRecords> medicalRecordsList = medicalRecordsService.list(queryWrapper);
        List<MedicalRecordListItemVO> voList = MedicalRecordListItemConverter.toVOList(medicalRecordsList);
        return ResultBean.success(voList);
    }

    /**
     * 保存或更新病例信息（包含处方）
     * @param saveDTO 病例信息
     * @return 操作结果
     */
    @PostMapping("/save")
    public ResultBean<String> saveOrUpdate(@RequestBody MedicalRecordSaveDTO saveDTO) {
        MedicalRecords medicalRecords;
        if (saveDTO.getId() != null) {
            medicalRecords = medicalRecordsService.getById(saveDTO.getId());
            if (medicalRecords == null) {
                return ResultBean.fail(StatusCode.DATA_NOT_FOUND);
            }
        } else {
            medicalRecords = new MedicalRecords();
            medicalRecords.setCreatedAt(java.time.LocalDateTime.now());
        }
        
        BeanUtils.copyProperties(saveDTO, medicalRecords, "id", "createdAt", "updatedAt", "deleted");
        
        // 如果是新增，确保有就诊日期
        if (medicalRecords.getVisitDate() == null) {
            medicalRecords.setVisitDate(java.time.LocalDateTime.now());
        }
        
        // 默认状态为治疗中
        if (medicalRecords.getStatus() == null) {
            medicalRecords.setStatus((byte) 0);
        }
        
        boolean success = medicalRecordsService.saveOrUpdate(medicalRecords);
        if (success) {
            return ResultBean.success("保存成功");
        } else {
            return ResultBean.fail(StatusCode.INTERNAL_SERVER_ERROR, "保存失败");
        }
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