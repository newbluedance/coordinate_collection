package com.lcf.coordinate_collection.view.entity;

import java.beans.Transient;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author lichunfeng
 */
@Data
@AllArgsConstructor
public class PointInfo implements Serializable {

    private Integer id;
    private Integer x;
    private Integer y;
    private Integer num;

}
