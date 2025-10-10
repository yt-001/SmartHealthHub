package com.xitian.smarthealthhub.converter;

import com.xitian.smarthealthhub.domain.entity.MedicalRecords;
import com.xitian.smarthealthhub.domain.vo.MedicalRecordsVO;

public class MedicalRecordsConverter {
    /**
     * MedicalRecords Entity -> MedicalRecordsVO
     */
    public static MedicalRecordsVO toVO(MedicalRecords entity) {
        if (entity == null) {
            return null;
        }

        MedicalRecordsVO vo = new MedicalRecordsVO();

        // BaseVO 字段映射
        vo.setId(entity.getId());
        vo.setCreateTime(entity.getCreatedAt());
        vo.setUpdateTime(entity.getUpdatedAt());

        // MedicalRecordsVO 字段映射
        vo.setUserId(entity.getUserId());
        vo.setDoctorId(entity.getDoctorId());
        vo.setUserName(entity.getUserName());
        vo.setDoctorName(entity.getDoctorName());
        vo.setSymptoms(entity.getSymptoms());
        vo.setDiagnosis(entity.getDiagnosis());
        vo.setTreatmentPlan(entity.getTreatmentPlan());
        vo.setPrescription(entity.getPrescription());
        vo.setNotes(entity.getNotes());
        vo.setVisitDate(entity.getVisitDate());
        vo.setCompletedDate(entity.getCompletedDate());
        vo.setStatus(entity.getStatus());

        return vo;
    }
}