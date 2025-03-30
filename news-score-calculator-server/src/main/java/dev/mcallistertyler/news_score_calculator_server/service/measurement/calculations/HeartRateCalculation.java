package dev.mcallistertyler.news_score_calculator_server.service.measurement.calculations;

import dev.mcallistertyler.news_score_calculator_server.domain.Measurement;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.Range;

public class HeartRateCalculation extends RangeBasedMeasurementCalculation {

    private static final Integer minimumValue = 26;
    private static final Integer maximumValue = 220;

    private static final Map<Range<Integer>, Integer> rangeToScoreMap = new HashMap<>(){{
        put(Range.of(minimumValue, 40), 3);
        put(Range.of(41, 50), 1);
        put(Range.of(51, 90), 0);
        put(Range.of(91, 110), 1);
        put(Range.of(111, 130), 2);
        put(Range.of(131, maximumValue), 3);
    }};

    public HeartRateCalculation() {
        super(Measurement.HR, minimumValue, maximumValue, rangeToScoreMap);
    }

}
