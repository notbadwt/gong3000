package com.gong3000.recommend.service.util;

public class NatureUtil {

    private static String[] COLOR = {"白:1", "灰:1.5", "银:2", "金:3", "黄:3.5", "橙:4", "红:4.5", "粉:5", "紫:6", "棕:7", "青:8", "绿:8.2", "蓝:8.8", "褐:9.5", "黑:10"};
    private static String[] TYPE = {"标配版:16", "高配版:256", "尊享版:512", "32GB:32", "64G:64", "128G:128"};

    public static String colorFilter(String string) {
        for (String color : COLOR) {
            String temp = color.split(":")[0];
            if (string.contains(temp)) {
                return color;
            }

        }
        return string;
    }

    public static String typeFilter(String string) {
        for (String type : TYPE) {
            String temp = type.split(":")[0];
            if (string.contains(temp)) {
                return type;
            }

        }
        return string;
    }

}
