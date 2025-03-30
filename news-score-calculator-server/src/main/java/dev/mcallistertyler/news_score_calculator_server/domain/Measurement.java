package dev.mcallistertyler.news_score_calculator_server.domain;

public enum Measurement {
    TEMP("Temperature"),
    HR("Heart Rate"),
    RR("Respiratory Rate");

    private final String readableName;

    Measurement(String readableName) {
        this.readableName = readableName;
    }

    public String getReadableName() {
        return this.readableName;
    }
}