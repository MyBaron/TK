package com.monitoring.seckill.Controller;


import com.monitoring.seckill.Entity.MonGood;
import com.monitoring.seckill.Entity.Result;
import com.monitoring.seckill.Service.GoodService;
import com.monitoring.seckill.Util.ResultVO;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/good")
public class GoodCtl {

    @Autowired
    private GoodService goodService;

    @RequestMapping("/checkGoodAll.do")
    public Result checkGoodsAll() {
        List<MonGood> monGoods = goodService.checkGoodAll();
        return ResultVO.success(monGoods);
    }

    @RequestMapping("/createGood.do")
    public Result createGood(MonGood monGood) {
        if (checkGood(monGood)) {
            goodService.saveGood(monGood);
            return ResultVO.success();
        }else {
            return ResultVO.error();
        }
    }

    private boolean checkGood(MonGood monGood) {
        if (Strings.isEmpty(monGood.getGoodName())) {
            return false;
        }
        if (0==monGood.getGoodAmount()) {
            return false;
        }
        if (0==monGood.getGoodPrice()) {
            return false;
        }
        return true;
    }
}
