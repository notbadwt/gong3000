package com.gong3000.data.mining.model;

import java.util.HashMap;

public class ClassType {

    private static HashMap<String, ClassType> classTypeHashMap = new HashMap<>();

    private String name;

    public static ClassType getClassType(String name) {
        if (!classTypeHashMap.containsKey(name)) {
            classTypeHashMap.put(name, new ClassType(name));
        }
        return classTypeHashMap.get(name);
    }

    private ClassType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
