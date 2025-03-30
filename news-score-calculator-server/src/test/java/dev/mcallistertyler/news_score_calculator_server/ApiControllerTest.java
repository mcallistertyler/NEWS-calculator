package dev.mcallistertyler.news_score_calculator_server;

import dev.mcallistertyler.news_score_calculator_server.domain.Measurement;
import dev.mcallistertyler.news_score_calculator_server.exceptions.NewsExceptions;
import dev.mcallistertyler.news_score_calculator_server.rest.ApiController;
import dev.mcallistertyler.news_score_calculator_server.service.NewsCalculationService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApiController.class)
public class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    NewsCalculationService newsCalculationService;

    @Test
    public void testValidRequest() throws Exception {
        String validRequestBody = """
                {
                    "measurements": [
                        {
                            "type": "TEMP",
                            "value": 39
                        },
                        {
                            "type": "HR",
                            "value": 43
                        },
                        {
                            "type": "RR",
                            "value": 19
                        }
                    ]
                }""";

        String validResponse = """
                {"type": "success","score":2}
                """;
        when(newsCalculationService.calculate(any())).thenReturn((2));
        mockMvc.perform(
                post("/news").contentType(MediaType.APPLICATION_JSON).content(validRequestBody)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(validResponse));}

    @Test
    public void testOutsideOfRangeRequest() throws Exception {
        String invalidJsonRequestBody = """
                {
                    "measurements": [
                        {
                            "type": "TEMP",
                            "value": 100
                        },
                        {
                            "type": "HR",
                            "value": 43
                        },
                        {
                            "type": "RR",
                            "value": 19
                        }
                    ]
                }
                
                """;
        NewsExceptions.MeasurementRangeAboveMaximum measurementRangeAboveMaximum = new NewsExceptions.MeasurementRangeAboveMaximum(Measurement.TEMP, 100, 32);

        when(newsCalculationService.calculate(any())).thenThrow(measurementRangeAboveMaximum);
        mockMvc.perform(
                        post("/news").contentType(MediaType.APPLICATION_JSON).content(invalidJsonRequestBody)
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                        {
                            "status": 400,
                            "message": "Measurement value: 100 is above maximum value: 32 for measurement type Temperature",
                            "errorCode": "MEASUREMENT_ABOVE_MAX"
                        }"""));
    }

    @Test
    public void testMissingType() throws Exception {
        String invalidJsonRequestBody = """
                {
                    "measurements": [
                        {
                            "value": 39
                        },
                        {
                            "value": 50
                        },
                        {
                            "type": "RR",
                            "value": 19
                        }
                    ]
                }
                """;
        NewsExceptions.MissingTypeException missingTypeException = new NewsExceptions.MissingTypeException(List.of(Measurement.TEMP, Measurement.HR));
        when(newsCalculationService.calculate(any())).thenThrow(missingTypeException);

        mockMvc.perform(
                        post("/news").contentType(MediaType.APPLICATION_JSON).content(invalidJsonRequestBody)
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                        {
                            "status":400,
                            "message":"The following required measurement types are missing: [Temperature, Heart Rate]",
                            "errorCode":"MISSING_TYPE"
                        }
                        """));
    }

    @Test
    public void testMissingValue() throws Exception {
        String invalidJsonRequestBody = """
                {
                    "measurements": [
                        {
                            "type": "TEMP"
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
                """;
        NewsExceptions.MissingValueException misingValueException = new NewsExceptions.MissingValueException(Measurement.TEMP);
        when(newsCalculationService.calculate(any())).thenThrow(misingValueException);

        mockMvc.perform(
                        post("/news").contentType(MediaType.APPLICATION_JSON).content(invalidJsonRequestBody)
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                            {
                                "status": 400,
                                "message": "No value provided for measurement: Temperature",
                                "errorCode": "MISSING_VALUE"
                            }
                        """));
    }

    @Test
    public void testCalculationError() throws Exception {
        String jsonRequestBody = """
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
                """;

        NewsExceptions.NewsCalculationException newsCalculationException = new NewsExceptions.NewsCalculationException();
        when(newsCalculationService.calculate(any())).thenThrow(newsCalculationException);

        mockMvc.perform(
                        post("/news").contentType(MediaType.APPLICATION_JSON).content(jsonRequestBody)
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                        {
                            "status":400,
                            "message":"An unexpected error occurred when trying to calculate NEWS score",
                            "errorCode":"CALCULATION_ERROR"
                        }
                        """));
    }

}
