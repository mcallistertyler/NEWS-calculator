package dev.mcallistertyler.news_score_calculator_server.service.measurement.calculations;

import dev.mcallistertyler.news_score_calculator_server.domain.Measurement;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.Range;

public class RespiratoryCalculation extends RangeBasedMeasurementCalculation {

    private static final Integer minimumValue = 4;
    private static final Integer maximumValue = 60;

    private static final Map<Range<Integer>, Integer> rangeScoreToMap = new HashMap<>(){{
        put(Range.of(minimumValue, 8), 3);
        put(Range.of(9, 11), 1);
        put(Range.of(12, 20), 0);
        put(Range.of(21, 24), 2);
        put(Range.of(25, maximumValue), 3);
    }};

    public RespiratoryCalculation() {
        super(Measurement.RR, minimumValue, maximumValue, rangeScoreToMap);
    }

}
