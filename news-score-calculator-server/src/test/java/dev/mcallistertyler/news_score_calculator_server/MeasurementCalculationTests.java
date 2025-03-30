package dev.mcallistertyler.news_score_calculator_server;

import dev.mcallistertyler.news_score_calculator_server.exceptions.NewsExceptions;
import dev.mcallistertyler.news_score_calculator_server.service.measurement.calculations.HeartRateCalculation;
import dev.mcallistertyler.news_score_calculator_server.service.measurement.calculations.RangeBasedMeasurementCalculation;
import dev.mcallistertyler.news_score_calculator_server.service.measurement.calculations.RespiratoryCalculation;
import dev.mcallistertyler.news_score_calculator_server.service.measurement.calculations.TemperatureCalculation;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class MeasurementCalculationTests {

    public void calculationRunner(RangeBasedMeasurementCalculation calculator, List<Integer> possibleValues, Integer expectedScore) {
        possibleValues.forEach(possibleValue -> {
            assertEquals(expectedScore, calculator.calculateScore(possibleValue));
        });
    }

    public List<Integer> createRangeList(Integer startInclusive, Integer endInclusive) {
        return IntStream.range(startInclusive, endInclusive).boxed().toList();
    }

    @Test
    public void temeperateCalculationOutOfRange() {
        TemperatureCalculation temperatureCalculation = new TemperatureCalculation();
        assertThrows(
                NewsExceptions.MeasurementRangeBelowMinimum.class,
                () -> temperatureCalculation.calculateScore(31)
        );

        assertThrows(
                NewsExceptions.MeasurementRangeAboveMaximum.class,
                () -> temperatureCalculation.calculateScore(43)
        );
    }

    @Test
    public void temperatureCalculations() {
        TemperatureCalculation temperatureCalculation = new TemperatureCalculation();

        List<Integer> score_3_tempRanges = createRangeList(32, 35);
        calculationRunner(temperatureCalculation, score_3_tempRanges, 3);

        List<Integer> score_1_tempRange_1 = List.of(36);
        calculationRunner(temperatureCalculation, score_1_tempRange_1, 1);

        List<Integer> score_1_tempRange_2 = List.of(39);
        calculationRunner(temperatureCalculation, score_1_tempRange_2, 1);

        List<Integer> score_2_tempRanges = createRangeList(40, 42);
        calculationRunner(temperatureCalculation, score_2_tempRanges, 2);

        List<Integer> score_0_tempRanges = createRangeList(37, 38);
        calculationRunner(temperatureCalculation, score_0_tempRanges, 0);
    }

    @Test
    public void heartRateCalculationsOutOfRange() {
        HeartRateCalculation heartRateCalculation = new HeartRateCalculation();
        assertThrows(
                NewsExceptions.MeasurementRangeBelowMinimum.class,
                () -> heartRateCalculation.calculateScore(25)
        );

        assertThrows(
                NewsExceptions.MeasurementRangeAboveMaximum.class,
                () -> heartRateCalculation.calculateScore(221)
        );
    }

    @Test
    public void heartRateCalculations() {
        HeartRateCalculation heartRateCalculation = new HeartRateCalculation();

        List<Integer> score_3_lower_ranges = createRangeList(26, 40);
        calculationRunner(heartRateCalculation, score_3_lower_ranges, 3);

        List<Integer> score_1_lower_ranges = createRangeList(41, 50);
        calculationRunner(heartRateCalculation, score_1_lower_ranges, 1);

        List<Integer> score_0_ranges = createRangeList(51, 90);
        calculationRunner(heartRateCalculation, score_0_ranges, 0);

        List<Integer> score_1_upper_ranges = createRangeList(91, 110);
        calculationRunner(heartRateCalculation, score_1_upper_ranges, 1);

        List<Integer> score_2_ranges = createRangeList(111, 130);
        calculationRunner(heartRateCalculation, score_2_ranges, 2);

        List<Integer> score_3_ranges = createRangeList(131, 220);
        calculationRunner(heartRateCalculation, score_3_ranges, 3);

    }

    @Test
    public void respiratoryCalculationsOutOfRange() {
        RespiratoryCalculation respiratoryCalculation = new RespiratoryCalculation();
        assertThrows(
                NewsExceptions.MeasurementRangeBelowMinimum.class,
                () -> respiratoryCalculation.calculateScore(3)
        );

        assertThrows(
                NewsExceptions.MeasurementRangeAboveMaximum.class,
                () -> respiratoryCalculation.calculateScore(61)
        );
    }

    @Test
    public void respiratoryCalculations() {
        RespiratoryCalculation respiratoryCalculation = new RespiratoryCalculation();

        List<Integer> score_3_lower_ranges = createRangeList(4, 8);
        calculationRunner(respiratoryCalculation, score_3_lower_ranges, 3);

        List<Integer> score_1_ranges = createRangeList(9, 11);
        calculationRunner(respiratoryCalculation, score_1_ranges, 1);

        List<Integer> score_0_ranges = createRangeList(12, 20);
        calculationRunner(respiratoryCalculation, score_0_ranges, 0);

        List<Integer> score_2_ranges = createRangeList(21, 24);
        calculationRunner(respiratoryCalculation, score_2_ranges, 2);

        List<Integer> score_3_ranges = createRangeList(25, 60);
        calculationRunner(respiratoryCalculation, score_3_ranges, 3);

    }
}
