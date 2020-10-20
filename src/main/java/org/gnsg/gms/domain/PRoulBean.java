package org.gnsg.gms.domain;

import com.opencsv.bean.CsvBindByPosition;
import java.io.Serializable;

public class PRoulBean implements Serializable {
    @CsvBindByPosition(position = 0)
    private Integer index;

    @CsvBindByPosition(position = 1)
    private String pathiName;

    @CsvBindByPosition(position = 2)
    private Double totalRoul;

    @CsvBindByPosition(position = 3)
    private Double totalAmt;

    @CsvBindByPosition(position = 4)
    private String sign;

    //@CsvBindByPosition(position = 3)
    private String bhogDate;

    public String getPathiName() {
        return pathiName;
    }

    public void setPathiName(String pathiName) {
        this.pathiName = pathiName;
    }

    public Double getTotalRoul() {
        return totalRoul;
    }

    public void setTotalRoul(Double totalRoul) {
        this.totalRoul = totalRoul;
    }

    public Double getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(Double totalAmt) {
        this.totalAmt = totalAmt;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getBhogDate() {
        return bhogDate;
    }

    public void setBhogDate(String bhogDate) {
        this.bhogDate = bhogDate;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return (
            "PRoulBean [pathiName=" +
            pathiName +
            ", totalRoul=" +
            totalRoul +
            ", totalAmt=" +
            totalAmt +
            ", sign=" +
            sign +
            ", bhogDate=" +
            bhogDate +
            "]"
        );
    }
}
