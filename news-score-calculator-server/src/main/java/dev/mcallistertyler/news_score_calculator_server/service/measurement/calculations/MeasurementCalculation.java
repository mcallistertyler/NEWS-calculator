package dev.mcallistertyler.news_score_calculator_server.service.measurement.calculations;

import dev.mcallistertyler.news_score_calculator_server.domain.Measurement;

public interface MeasurementCalculation {
    Measurement getMeasurementType();
    Integer calculateScore(Integer measurementValue);
}
