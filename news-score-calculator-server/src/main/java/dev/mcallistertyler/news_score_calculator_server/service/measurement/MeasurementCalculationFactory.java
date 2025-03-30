package dev.mcallistertyler.news_score_calculator_server.service.measurement;

import dev.mcallistertyler.news_score_calculator_server.domain.Measurement;
import dev.mcallistertyler.news_score_calculator_server.service.measurement.calculations.HeartRateCalculation;
import dev.mcallistertyler.news_score_calculator_server.service.measurement.calculations.MeasurementCalculation;
import dev.mcallistertyler.news_score_calculator_server.service.measurement.calculations.RespiratoryCalculation;
import dev.mcallistertyler.news_score_calculator_server.service.measurement.calculations.TemperatureCalculation;
import org.apache.commons.lang3.NotImplementedException;

public class MeasurementCalculationFactory {

    public static MeasurementCalculation getCalculator(Measurement measurement) {
        switch (measurement) {
            case TEMP -> {
                return new TemperatureCalculation();
            }
            case HR -> {
                return new HeartRateCalculation();
            }
            case RR -> {
                return new RespiratoryCalculation();
            }
            default -> {
                throw new NotImplementedException("Measurement " + measurement.getReadableName() + " has no supported calculation.");
            }
        }
    }
}
