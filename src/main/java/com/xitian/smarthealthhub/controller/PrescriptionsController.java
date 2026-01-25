package com.xitian.smarthealthhub.controller;

import com.xitian.smarthealthhub.bean.PageBean;
import com.xitian.smarthealthhub.bean.PageParam;
import com.xitian.smarthealthhub.bean.ResultBean;
import com.xitian.smarthealthhub.bean.StatusCode;
import com.xitian.smarthealthhub.domain.entity.Prescriptions;
import com.xitian.smarthealthhub.service.PrescriptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 处方管理控制器
 */
@RestController
@RequestMapping("/prescriptions")
public class PrescriptionsController {

    @Autowired
    private PrescriptionsService prescriptionsService;

    /**
     * 分页查询处方
     */
    @PostMapping("/page")
    public ResultBean<PageBean<Prescriptions>> page(@RequestBody PageParam<Prescriptions> pageParam) {
        return ResultBean.success(prescriptionsService.pageQuery(pageParam));
    }

    /**
     * 创建处方
     */
    @PostMapping("/create")
    public ResultBean<String> create(@RequestBody Prescriptions prescriptions) {
        // 设置初始状态
        if (prescriptions.getStatus() == null) {
            prescriptions.setStatus((byte) 0); // 默认待支付/待取药
        }
        boolean saved = prescriptionsService.save(prescriptions);
        return saved ? ResultBean.success("处方创建成功") : ResultBean.fail(StatusCode.FAIL, "处方创建失败");
    }

    /**
     * 更新处方
     */
    @PostMapping("/update")
    public ResultBean<String> update(@RequestBody Prescriptions prescriptions) {
        boolean updated = prescriptionsService.updateById(prescriptions);
        return updated ? ResultBean.success("处方更新成功") : ResultBean.fail(StatusCode.FAIL, "处方更新失败");
    }

    /**
     * 获取处方详情
     */
    @GetMapping("/{id}")
    public ResultBean<Prescriptions> getById(@PathVariable Long id) {
        return ResultBean.success(prescriptionsService.getById(id));
    }

    /**
     * 删除处方
     */
    @PostMapping("/delete/{id}")
    public ResultBean<String> delete(@PathVariable Long id) {
        boolean removed = prescriptionsService.removeById(id);
        return removed ? ResultBean.success("处方删除成功") : ResultBean.fail(StatusCode.FAIL, "处方删除失败");
    }
}
