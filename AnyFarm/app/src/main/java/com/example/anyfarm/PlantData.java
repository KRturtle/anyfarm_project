package com.example.anyfarm;


public class PlantData
{
    //화분 데이터를 저장하기 위한 객체
    private String userID;
    private String modelNumber;
    private String plantKind;
    private String tvalue;
    private String avalue;
    private String svalue;
    private String lux_value;

    //getter, setter
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getPlantKind() {
        return plantKind;
    }

    public void setPlantKind(String plantKind) {
        this.plantKind = plantKind;
    }

    public String getTvalue() {
        return tvalue;
    }

    public void setTvalue(String tvalue) {
        this.tvalue = tvalue;
    }

    public String getAvalue() {
        return avalue;
    }

    public void setAvalue(String avalue) {
        this.avalue = avalue;
    }

    public String getSvalue() {
        return svalue;
    }

    public void setSvalue(String svalue) {
        this.svalue = svalue;
    }

    public String getLux_value() {
        return lux_value;
    }

    public void setLux_value(String lux_value) {
        this.lux_value = lux_value;
    }
}