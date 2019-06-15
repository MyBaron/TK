package com.mune.system.entity.specification;

import com.mune.system.entity.MuneOrder;
import com.mune.system.entity.dto.MuneOrderDTO;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.Date;

public class FindOrderByDate implements Specification<MuneOrder> {

    private String date;

    public FindOrderByDate(String date) {
        this.date = date;
    }

    /**
     *
     * @param root 代表查询的实体类
     * @param query 可以从中可到 Root 对象, 即告知 JPA Criteria 查询要查询哪一个实体类. 还可以
     * 来添加查询条件, 还可以结合 EntityManager 对象得到最终查询的 TypedQuery 对象.
     * @param cb CriteriaBuilder 对象. 用于创建 Criteria 相关对象的工厂. 当然可以从中获取到 Predicate 对象
     * @return: *Predicate 类型, 代表一个查询条件.
     *
     */

    @Override
    public Predicate toPredicate(Root<MuneOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Path orderDate = root.get("orderDate");
        if (!StringUtils.isEmpty(date)) {
            Predicate equal = cb.equal(orderDate.as(String.class),date);
            return equal;
        }
        return null;
    }
}
