package com.xitian.smarthealthhub.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "创建药品订单DTO")
public class MedicineOrderCreateDTO {
    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "药品ID")
    private Long medicineId;

    @Schema(description = "数量")
    private Integer quantity;

    @Schema(description = "取药地点ID")
    private Long pharmacyLocationId;
}
