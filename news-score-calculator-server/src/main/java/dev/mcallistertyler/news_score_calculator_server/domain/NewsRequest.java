package dev.mcallistertyler.news_score_calculator_server.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "NewsRequest",
        example = """
            {
                "measurements": [
                    {
                        "type": "TEMP",
                        "value": 40
                    },
                    {
                        "type": "HR",
                        "value": 50
                    },
                    {
                        "type": "RR",
                        "value": 19
                    }
                ]
            }
""")
public record NewsRequest(
        List<MeasurementType> measurements
) {

    public record MeasurementType(
            Measurement type,
            Integer value
    ){};
}

