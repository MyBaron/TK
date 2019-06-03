package com.mune.system.entity.dto;

public class PageDTO {

    private int pageSum;
    private int sum;
    private int page;


    public PageDTO() {
    }

    public PageDTO(int pageSum, int sum, int page) {
        this.pageSum = pageSum;
        this.sum = sum;
        this.page = page;
    }

    public int getPageSum() {
        if (sum==0) {
            this.pageSum=20;
        }
        return pageSum;
    }

    public void setPageSum(int pageSum) {
        this.pageSum = pageSum;
    }

    public int getSum() {

        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getPage() {
        if (page==0) {
//            this.page=1;
        }
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "PageDTO{" +
                "pageSum=" + pageSum +
                ", sum=" + sum +
                ", page=" + page +
                '}';
    }
}
