package dev.mcallistertyler.news_score_calculator_server.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "NewsSuccessResponse",
        example = """
            {
                "type": "success",
                "score": 3
            }
        """)
public record NewsSuccessResponse(String type, Integer score) {
    public NewsSuccessResponse(Integer score) {
        this("success", score);
    }

}
