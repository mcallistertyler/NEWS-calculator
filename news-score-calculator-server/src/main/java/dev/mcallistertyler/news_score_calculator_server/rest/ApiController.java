package dev.mcallistertyler.news_score_calculator_server.rest;

import dev.mcallistertyler.news_score_calculator_server.domain.NewsRequest;
import dev.mcallistertyler.news_score_calculator_server.domain.NewsSuccessResponse;
import dev.mcallistertyler.news_score_calculator_server.exceptions.NewsExceptions;
import dev.mcallistertyler.news_score_calculator_server.service.NewsCalculationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/news")
public class ApiController {

    private final Logger log = LogManager.getLogger(ApiController.class);

    private final NewsCalculationService newsCalculationService;

    public ApiController(NewsCalculationService newsCalculationService) {
        this.newsCalculationService = newsCalculationService;
    }

    @Operation(summary = "Calculate news score")
    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<NewsSuccessResponse> calculateNewsValue(@Valid @RequestBody NewsRequest newsRequest) {
        Integer newsScore = newsCalculationService.calculate(newsRequest);
        if (newsScore == null) {
            log.error("Calculated score was null in news score calculation");
            throw new NewsExceptions.NewsCalculationException();
        }
        NewsSuccessResponse newsSuccessResponse = new NewsSuccessResponse(newsScore);
        return ResponseEntity.ok(newsSuccessResponse);
    }

}
