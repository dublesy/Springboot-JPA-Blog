package com.cos.blog.model;


import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class Dish {
    private final String name;
    private final boolean vegetarian;
    private final int calories;
    private final Type type;

    @Override
    public String toString() {
        return name;
    }

    public enum Type {MEAT, FISH, OTHER};
}