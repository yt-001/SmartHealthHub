package com.xitian.smarthealthhub.converter;

import com.xitian.smarthealthhub.domain.entity.MedicalRecords;
import com.xitian.smarthealthhub.domain.vo.MedicalRecordPageVO;

public class MedicalRecordPageVOConverter {
    /**
     * MedicalRecords Entity -> MedicalRecordPageVO
     */
    public static MedicalRecordPageVO toPageVO(MedicalRecords entity) {
        if (entity == null) {
            return null;
        }

        MedicalRecordPageVO vo = new MedicalRecordPageVO();

        // MedicalRecordPageVO 字段映射
        vo.setId(entity.getId());
        vo.setPatientName(entity.getUserName());
        vo.setDoctorName(entity.getDoctorName());
        vo.setDiagnosis(entity.getDiagnosis());
        vo.setVisitDate(entity.getVisitDate());
        vo.setCompletedDate(entity.getCompletedDate());
        vo.setStatus(entity.getStatus());

        return vo;
    }
}