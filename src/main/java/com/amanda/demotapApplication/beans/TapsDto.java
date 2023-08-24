package com.amanda.demotapApplication.beans;

import com.opencsv.bean.CsvBindByName;

public class TapsDto {

@CsvBindByName(column = "ID",required = true)
private Integer id;
@CsvBindByName(column = "DateTimeUTC")
private String dateTime;
@CsvBindByName(column = "TapType")
private String tapType;
@CsvBindByName(column = "StopId")
private String stopId;
@CsvBindByName(column = "CompanyId")
private String companyId;
@CsvBindByName(column = "BusID")
private String busId;
@CsvBindByName(column = "PAN")
private String pan;

    public TapsDto() {
    }

    public TapsDto(int id, String dateTime, String tapType, String stopId, String companyId, String busId, String pan) {
        this.id = id;
        this.dateTime = dateTime;
        this.tapType = tapType;
        this.stopId = stopId;
        this.companyId = companyId;
        this.busId = busId;
        this.pan = pan;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getTapType() {
        return tapType;
    }

    public void setTapType(String tapType) {
        this.tapType = tapType;
    }

    public String getStopId() {
        return stopId;
    }

    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }


    @Override
    public String toString() {
        return "TapsDto{" +
                "id=" + id +
                ", dateTime='" + dateTime + '\'' +
                ", tapType='" + tapType + '\'' +
                ", stopId='" + stopId + '\'' +
                ", companyId='" + companyId + '\'' +
                ", busId='" + busId + '\'' +
                ", pan='" + pan + '\'' +
                '}';
    }
}
