package com.example.covid_19statistics;

public class StateListItem
{
    private String stateName;
    private String confirmedCases;
    private String activeCases;
    private String deathCases;

    public StateListItem(String stateName, String confirmedCases, String activeCases, String deathCases) {
        this.stateName = stateName;
        this.confirmedCases = confirmedCases;
        this.activeCases = activeCases;
        this.deathCases = deathCases;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getConfirmedCases() {
        return confirmedCases;
    }

    public void setConfirmedCases(String confirmedCases) {
        this.confirmedCases = confirmedCases;
    }

    public String getActiveCases() {
        return activeCases;
    }

    public void setActiveCases(String activeCases) {
        this.activeCases = activeCases;
    }

    public String getDeathCases() {
        return deathCases;
    }

    public void setDeathCases(String deathCases) {
        this.deathCases = deathCases;
    }
}
