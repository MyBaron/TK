package com.mune.system.entity.dto;

import lombok.Data;

@Data
public class PageDTO {

    private int pageSum;
    private long sum;
    private int page;
    private boolean hasPage;


    public PageDTO() {
    }

    public PageDTO(int pageSum, long sum, int page) {
        this.pageSum = pageSum;
        this.sum = sum;
        this.page = page;
    }

}
