package dev.mcallistertyler.news_score_calculator_server.domain;


public record NewsErrorResponse(int status, String type, String message, String errorCode) {
    public NewsErrorResponse(int status, String message, String errorCode) {
        this(status, "error", message, errorCode);
    }
}
