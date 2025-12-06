package com.xitian.smarthealthhub.converter;

import com.xitian.smarthealthhub.domain.entity.MedicalRecords;
import com.xitian.smarthealthhub.domain.vo.MedicalRecordDetailVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 病例详情转换器
 */
public class MedicalRecordDetailConverter {
    
    /**
     * MedicalRecords Entity -> MedicalRecordDetailVO
     * @param entity MedicalRecords实体
     * @return MedicalRecordDetailVO
     */
    public static MedicalRecordDetailVO toVO(MedicalRecords entity) {
        if (entity == null) {
            return null;
        }
        
        MedicalRecordDetailVO vo = new MedicalRecordDetailVO();
        vo.setId(entity.getId());
        vo.setUserName(entity.getUserName());
        vo.setDoctorName(entity.getDoctorName());
        vo.setVisitDate(entity.getVisitDate());
        vo.setSymptoms(entity.getSymptoms());
        vo.setDiagnosis(entity.getDiagnosis());
        vo.setTreatmentPlan(entity.getTreatmentPlan());
        vo.setPrescription(entity.getPrescription());
        vo.setNotes(entity.getNotes());
        // 仅当 status=1 时展示完成时间
        vo.setCompletedDate(entity.getStatus() != null && entity.getStatus() == 1 
                ? entity.getCompletedDate() 
                : null);
        vo.setStatus(entity.getStatus() != null ? entity.getStatus().intValue() : null);
        
        return vo;
    }
    
    /**
     * MedicalRecords Entity列表 -> MedicalRecordDetailVO列表
     * @param entityList MedicalRecords实体列表
     * @return MedicalRecordDetailVO列表
     */
    public static List<MedicalRecordDetailVO> toVOList(List<MedicalRecords> entityList) {
        if (entityList == null) {
            return null;
        }
        
        List<MedicalRecordDetailVO> voList = new ArrayList<>();
        for (MedicalRecords entity : entityList) {
            voList.add(toVO(entity));
        }
        return voList;
    }
}