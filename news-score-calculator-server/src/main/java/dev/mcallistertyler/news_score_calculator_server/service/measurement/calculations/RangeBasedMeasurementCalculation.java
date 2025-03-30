package dev.mcallistertyler.news_score_calculator_server.service.measurement.calculations;

import dev.mcallistertyler.news_score_calculator_server.domain.Measurement;
import dev.mcallistertyler.news_score_calculator_server.exceptions.NewsExceptions;
import java.util.Map;
import org.apache.commons.lang3.Range;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class RangeBasedMeasurementCalculation implements MeasurementCalculation {

    protected final Measurement measurement;
    protected final Integer minimumValue;
    protected final Integer maximumValue;
    protected final Map<Range<Integer>, Integer> rangeToScoreMap;

    public RangeBasedMeasurementCalculation(Measurement measurement,
                                            Integer minimumValue,
                                            Integer maximumValue,
                                            Map<Range<Integer>, Integer> rangeToScoreMap) {
        this.measurement = measurement;
        this.minimumValue = minimumValue;
        this.maximumValue = maximumValue;
        this.rangeToScoreMap = rangeToScoreMap;
    }


    private final Logger log = LogManager.getLogger(RangeBasedMeasurementCalculation.class);

    public void valueWithinRange(Integer measurementValue) {
        if (measurementValue < minimumValue) {
            throw new NewsExceptions.MeasurementRangeBelowMinimum(measurement, measurementValue, minimumValue);
        }
        if (measurementValue > maximumValue) {
            throw new NewsExceptions.MeasurementRangeAboveMaximum(measurement, measurementValue, maximumValue);
        }
    }

    @Override
    public Integer calculateScore(Integer measurementValue) throws RuntimeException {
        valueWithinRange(measurementValue);
        try {
            return rangeToScoreMap.entrySet().stream().filter(rangeIntegerEntry -> rangeIntegerEntry.getKey().contains(measurementValue))
                    .map(Map.Entry::getValue)
                    .toList()
                    .getFirst();
        } catch (Exception e) {
            log.error("Caught a generic exception when performing score lookup for measurement: {}", measurement, e);
            throw new NewsExceptions.NewsCalculationException();
        }
    }

    @Override
    public Measurement getMeasurementType() {
        return this.measurement;
    }

}
