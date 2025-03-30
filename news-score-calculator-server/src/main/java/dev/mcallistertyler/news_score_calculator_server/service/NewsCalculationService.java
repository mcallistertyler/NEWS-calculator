package dev.mcallistertyler.news_score_calculator_server.service;

import dev.mcallistertyler.news_score_calculator_server.domain.Measurement;
import dev.mcallistertyler.news_score_calculator_server.domain.NewsRequest;
import dev.mcallistertyler.news_score_calculator_server.exceptions.NewsExceptions;
import dev.mcallistertyler.news_score_calculator_server.service.measurement.MeasurementCalculationFactory;
import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class NewsCalculationService {

    private final Logger log = LogManager.getLogger(NewsCalculationService.class);

    public Integer calculate(NewsRequest newsRequest) {
        validateMeasurementsAndValues(newsRequest);
        try {
            int newsScore;
            newsScore = newsRequest.measurements().stream().map(measurementType -> {
                Measurement measurement = measurementType.type();
                Integer measurementValue = measurementType.value();
                return MeasurementCalculationFactory.getCalculator(measurement).calculateScore(measurementValue);
            }).mapToInt(Integer::intValue).sum();
            return newsScore;
        } catch (NewsExceptions.MeasurementRangeAboveMaximum |
                 NewsExceptions.MeasurementRangeBelowMinimum outOfRangeException) {
            log.error("Measurement given was outside of the valid range", outOfRangeException);
            throw outOfRangeException;
        }
        catch (RuntimeException runtimeException) {
            log.error("Error occurred when trying to calculate news score", runtimeException);
            throw new NewsExceptions.NewsCalculationException();
        }
    }

    private void validateMeasurementsAndValues(NewsRequest newsRequest) {
        hasRequiredMeasurementTypes(newsRequest);
        hasMeasurementValues(newsRequest);
    }

    private void hasRequiredMeasurementTypes(NewsRequest newsRequest) {
        List<Measurement> requiredMeasurements = Arrays.stream(Measurement.values()).toList();
        List<Measurement> requestMeasurements = newsRequest.measurements().stream().map(NewsRequest.MeasurementType::type).toList();
        List<Measurement> missingMeasurements = requiredMeasurements.stream()
                .filter(required -> !requestMeasurements.contains(required))
                .toList();
        if (!missingMeasurements.isEmpty()) {
            log.error("The following measurements are missing from the request: {}", missingMeasurements);
            throw new NewsExceptions.MissingTypeException(missingMeasurements);
        }
    }


    private void hasMeasurementValues(NewsRequest newsRequest) {
        newsRequest.measurements().forEach(measurementType -> {
            if (measurementType.value() == null) {
                throw new NewsExceptions.MissingValueException(measurementType.type());
            }
        });
    }

}
