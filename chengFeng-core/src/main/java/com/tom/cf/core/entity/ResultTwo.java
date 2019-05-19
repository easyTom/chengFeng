package com.tom.cf.core.entity;

import lombok.Data;

/**
 * @method:
 */
@Data
public class ResultTwo {
    private String id;
    private Integer count;

    public ResultTwo(String id, Integer count) {
        this.id = id;
        this.count = count;
    }
}
