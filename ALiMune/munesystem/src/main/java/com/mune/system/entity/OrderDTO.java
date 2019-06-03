package com.mune.system.entity;

public class OrderDTO {

    private Long muneId;
    private Long sum;

    public Long getMuneId() {
        return muneId;
    }

    public void setMuneId(Long muneId) {
        this.muneId = muneId;
    }

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "muneId=" + muneId +
                ", sum=" + sum +
                '}';
    }
}
