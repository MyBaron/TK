package com.mune.system.entity.specification;

import com.mune.system.utils.DateUtil;
import org.springframework.data.jpa.domain.Specification;

import java.text.ParseException;
import java.util.Date;

public class DateFactory {

    public static Specification  create(int type,String date) throws ParseException {
        // type=0 按天查
        // type=1 按月查
        // type=2 按年查
        if (0 == type) {
            return new FindOrderByDate(date);
        }if (1==type){
            Date d = DateUtil.string2Date(date, "yyyy-MM");
            String start = DateUtil.Date2String(d, "yyyy-MM");
            d = DateUtil.DateAddMonth(d, 1);
            String end = DateUtil.Date2String(d, "yyyy-MM");
            return new FindOrderByBetweenDate(start,end);
        }
        if (2 == type) {
            Date d = DateUtil.string2Date(date, "yyyy");
            String start = DateUtil.Date2String(d, "yyyy");
            d = DateUtil.DateAddYear(d, 1);
            String end = DateUtil.Date2String(d, "yyyy");
            return new FindOrderByBetweenDate(start, end);
        }
        return null;
    }
}
