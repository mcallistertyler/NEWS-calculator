package dev.mcallistertyler.news_score_calculator_server.exceptions;

import dev.mcallistertyler.news_score_calculator_server.domain.Measurement;
import java.util.List;
import java.util.stream.Collectors;

public class NewsExceptions {

    public abstract static class NewsException extends RuntimeException {
        public abstract String getErrorCode();

        public NewsException(String message) {
            super(message);
        }

    }

    public static class MissingTypeException extends NewsException {

        @Override
        public String getErrorCode() {
            return "MISSING_TYPE";
        }

        public MissingTypeException(List<Measurement> missingMeasurements) {
            super("The following required measurement types are missing: [" + missingMeasurements
                            .stream()
                            .map(Measurement::getReadableName)
                            .collect(Collectors.joining(", ")) + "]");
        }
    }

    public static class MissingValueException extends NewsException {

        @Override
        public String getErrorCode() {
            return "MISSING_VALUE";
        }

        public MissingValueException(Measurement measurementWithMissingValue) {
            super("No value provided for measurement: " + measurementWithMissingValue.getReadableName());
        }
    }

    public static class MeasurementRangeAboveMaximum extends NewsException {

        @Override
        public String getErrorCode() {
            return "MEASUREMENT_ABOVE_MAX";
        }

        public MeasurementRangeAboveMaximum(Measurement measurement, Integer measurementValue, Integer maximumValue) {
            super("Measurement value: " + measurementValue + " is above maximum value: " + maximumValue + " for measurement type " + measurement.getReadableName());
        }
    }


    public static class MeasurementRangeBelowMinimum extends NewsException {

        @Override
        public String getErrorCode() {
            return "MEASUREMENT_BELOW_MIN";
        }

        public MeasurementRangeBelowMinimum(Measurement measurement, Integer measurementValue, Integer minimumValue) {
            super("Measurement value: " + measurementValue + " is below minimum value: " + minimumValue + " for measurement type " + measurement.getReadableName());
        }
    }

    public static class NewsCalculationException extends NewsException {

        @Override
        public String getErrorCode() {
            return "CALCULATION_ERROR";
        }

        public NewsCalculationException() {
            super("An unexpected error occurred when trying to calculate NEWS score");
        }
    }
}
