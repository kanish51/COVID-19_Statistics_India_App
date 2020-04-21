package com.example.covid_19statistics;

public class DistrictListItem
{
    private String districtName;
    private String noOfCases;

    public DistrictListItem(String districtName, String noOfCases) {
        this.districtName = districtName;
        this.noOfCases = noOfCases;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getNoOfCases() {
        return noOfCases;
    }

    public void setNoOfCases(String noOfCases) {
        this.noOfCases = noOfCases;
    }
}
