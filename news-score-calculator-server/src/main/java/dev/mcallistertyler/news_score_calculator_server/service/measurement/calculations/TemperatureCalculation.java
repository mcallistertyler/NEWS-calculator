package dev.mcallistertyler.news_score_calculator_server.service.measurement.calculations;

import dev.mcallistertyler.news_score_calculator_server.domain.Measurement;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.Range;

public class TemperatureCalculation extends RangeBasedMeasurementCalculation {

    private static final Integer minimumValue = 32;
    private static final Integer maximumValue = 42;

    private static final Map<Range<Integer>, Integer> rangeToScoreMap = new HashMap<>(){{
        put(Range.of(minimumValue, 35), 3);
        put(Range.of(36, 36), 1);
        put(Range.of(37, 38), 0);
        put(Range.of(39, 39), 1);
        put(Range.of(40, maximumValue), 2);
    }};

    public TemperatureCalculation() {
        super(Measurement.TEMP, minimumValue, maximumValue, rangeToScoreMap);
    }

}
