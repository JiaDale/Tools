package com.jdy.util;

import java.util.Locale;
import java.util.Objects;

public class District {
    private static final String[] NORTHCHINA = {"北京市", "天津市", "河北省", "山西省", "内蒙古自治区"};//华北地区
    private static final String[] NORTHEASTCHINA = {"辽宁省", "吉林省", "黑龙江省"}; //东北地区， 其中 内蒙古部分和河北部分被纳入华北地区
    private static final String[] EASTCHINA = {"上海市", "江苏省", "浙江省", "安徽省", "福建省", "江西省", "山东省"};//华东地区
    private static final String[] CENTRALSOUTHCHINA = {"河南省", "湖北省", "湖南省", "广东省", "广西壮族自治区", "海南省"};//中南地区
    private static final String[] SOUTHWESTCHINA = {"重庆市", "四川省", "贵州省", "云南省", "西藏自治区"};//西南地区
    private static final String[] NORTHWESTCHINA = {"陕西省", "甘肃省", "青海省", "宁夏回族自治区", "新疆维吾尔自治区"};//西北地区
    private static final String[] TAIWAN = {"台湾省"}; //台湾
    private static final String[] HONGKONGANDMACAO = {"香港特别行政区", "澳门特别行政区"}; //香港和澳门

    public static String getCity(String regionCode){
        if (Objects.requireNonNull(regionCode, "参数regionCode为空").length() < 4)
            return getProvince(regionCode);

        int regionIndex = Integer.parseInt(regionCode.substring(2, 4));//区域序号
        System.out.printf(".........**********....." + regionIndex);
        return "";
    }

    public static String getProvince(String regionCode) {
        if (Objects.requireNonNull(regionCode, "参数regionCode为空").length() < 2) {
            return Locale.CHINA.getDisplayCountry();
        }

        int regionArea = Integer.parseInt(regionCode.substring(0, 1)); //区域编码
        int regionIndex = Integer.parseInt(regionCode.substring(1, 2));//区域序号

        switch (regionArea) {
            case 1:
                return getProvince(NORTHCHINA, regionIndex - 1, "华北地区");
            case 2:
                return getProvince(NORTHEASTCHINA, regionIndex - 1, "东北地区");
            case 3:
                return getProvince(EASTCHINA, regionIndex - 1, "华东地区");
            case 4:
                return getProvince(CENTRALSOUTHCHINA, regionIndex - 1, "中南地区");
            case 5:
                return getProvince(SOUTHWESTCHINA, regionIndex - 1, "西南地区");
            case 6:
                return getProvince(NORTHWESTCHINA, regionIndex - 1, "西北地区");
            case 7:
                return getProvince(TAIWAN, regionIndex - 1, "台湾地区");
            case 8:
                return getProvince(HONGKONGANDMACAO, regionIndex - 1, "香港和澳门地区");
            default:
                return Locale.CHINA.getDisplayCountry();
        }
    }

    private static String getProvince(String[] area, int index, String areaName) {
        if (area.length <= index) {
            throw new ArrayIndexOutOfBoundsException(areaName + "没有该省信息");
        }
        return area[index];
    }
}
