package com.mune.system.controller;


import com.mune.system.entity.Mune;
import com.mune.system.entity.Result;
import com.mune.system.service.MuneService;
import com.mune.system.utils.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mune")
@Slf4j
public class MuneController   {

    @Autowired
    private MuneService muneService;

    @GetMapping("/")
    public Map<Object, Object> getMune() {
        Map<Object, Object> map = new HashMap<>();
        List<Mune> list=muneService.getAllMune();
        map.put("list", list);
        return map;
    }

    @PostMapping({"/", ""})
    public Result save(@RequestBody Mune mune) {
        muneService.save(mune);
        return ResultVO.success();
    }

    @DeleteMapping({"/{id}",""})
    public Result delete(@PathVariable long id) {
        muneService.delete(id);
        return ResultVO.success();
    }
}
