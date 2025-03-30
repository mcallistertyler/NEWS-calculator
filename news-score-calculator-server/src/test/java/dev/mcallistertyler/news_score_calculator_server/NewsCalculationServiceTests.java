package dev.mcallistertyler.news_score_calculator_server;

import dev.mcallistertyler.news_score_calculator_server.domain.Measurement;
import dev.mcallistertyler.news_score_calculator_server.domain.NewsRequest;
import dev.mcallistertyler.news_score_calculator_server.exceptions.NewsExceptions;
import dev.mcallistertyler.news_score_calculator_server.service.NewsCalculationService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class NewsCalculationServiceTests {

    private final NewsCalculationService newsCalculationService = new NewsCalculationService();

    @Test
    public void validNewsCalculation() {
        Integer newsScore = newsCalculationService.calculate(validMeasurements());
        assertEquals(3, newsScore);
    }

    @Test
    public void missingMeasurementTypes() {
        assertThrows(NewsExceptions.MissingTypeException.class,
                () -> newsCalculationService.calculate(missingMeasurementType()));
    }


    @Test
    public void missingMeasurementValues() {
        assertThrows(NewsExceptions.MissingValueException.class,
                () -> newsCalculationService.calculate(missingMeasurementValue()));
    }

    public NewsRequest missingMeasurementValue() {
        NewsRequest.MeasurementType temperatureMeasurement = new NewsRequest.MeasurementType(Measurement.TEMP, 40);
        NewsRequest.MeasurementType heartRateMeasurement = new NewsRequest.MeasurementType(Measurement.HR, null);
        NewsRequest.MeasurementType respiratoryMeasurement = new NewsRequest.MeasurementType(Measurement.RR, 19);
        return new NewsRequest(List.of(temperatureMeasurement, heartRateMeasurement, respiratoryMeasurement));
    }

    public NewsRequest missingMeasurementType() {
        NewsRequest.MeasurementType temperatureMeasurement = new NewsRequest.MeasurementType(Measurement.TEMP, 40);
        NewsRequest.MeasurementType heartRateMeasurement = new NewsRequest.MeasurementType(Measurement.HR, 50);
        return new NewsRequest(List.of(temperatureMeasurement, heartRateMeasurement));
    }

    public NewsRequest validMeasurements() {
        NewsRequest.MeasurementType temperatureMeasurement = new NewsRequest.MeasurementType(Measurement.TEMP, 40);
        NewsRequest.MeasurementType heartRateMeasurement = new NewsRequest.MeasurementType(Measurement.HR, 50);
        NewsRequest.MeasurementType respiratoryMeasurement = new NewsRequest.MeasurementType(Measurement.RR, 19);
        return new NewsRequest(List.of(temperatureMeasurement, heartRateMeasurement, respiratoryMeasurement));
    }

}
