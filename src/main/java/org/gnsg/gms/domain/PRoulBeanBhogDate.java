package org.gnsg.gms.domain;

import com.opencsv.bean.CsvBindByPosition;
import java.io.Serializable;

/**
 * @author harjeet
 *
 */
public class PRoulBeanBhogDate implements Serializable {
    @CsvBindByPosition(position = 0)
    private Integer index;

    @CsvBindByPosition(position = 1)
    private String pathiName;

    @CsvBindByPosition(position = 2)
    private String bhogDate;

    public String getPathiName() {
        return pathiName;
    }

    public void setPathiName(String pathiName) {
        this.pathiName = pathiName;
    }

    public String getBhogDate() {
        return bhogDate;
    }

    public void setBhogDate(String bhogDate) {
        this.bhogDate = bhogDate;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
