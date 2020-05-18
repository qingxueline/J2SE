package com.somnus.poi;

import lombok.Data;

/**
 * People
 *
 * @author lyl
 * @version 2020/3/27 0027 12:28:56
 */
@Data
public class People {
    private String name;
    private int age;
    private String location;

    public People() {
    }

    public People(String name, int age, String location) {
        super();
        this.name = name;
        this.age = age;
        this.location = location;
    }
}
