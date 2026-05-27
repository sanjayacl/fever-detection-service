package com.sanja.feverdetection.controller;

import com.sanja.feverdetection.dto.FeverResponse;
import com.sanja.feverdetection.service.FeverService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FeverController.class)
class FeverControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeverService feverService;

    @Test
    void shouldReturnFeverTrueForHighTemperature() throws Exception {
        when(feverService.checkFever(39.0)).thenReturn(new FeverResponse(39.0, 102.2, true));

        mockMvc.perform(get("/api/fever/check").param("celsius", "39.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fever").value(true))
                .andExpect(jsonPath("$.celsius").value(39.0))
                .andExpect(jsonPath("$.fahrenheit").value(102.2));
    }

    @Test
    void shouldReturnFeverFalseForNormalTemperature() throws Exception {
        when(feverService.checkFever(36.0)).thenReturn(new FeverResponse(36.0, 96.8, false));

        mockMvc.perform(get("/api/fever/check").param("celsius", "36.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fever").value(false))
                .andExpect(jsonPath("$.celsius").value(36.0))
                .andExpect(jsonPath("$.fahrenheit").value(96.8));
    }

    @Test
    void shouldReturnFeverTrueAtExactThreshold() throws Exception {
        when(feverService.checkFever(38.0)).thenReturn(new FeverResponse(38.0, 100.4, true));

        mockMvc.perform(get("/api/fever/check").param("celsius", "38.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fever").value(true))
                .andExpect(jsonPath("$.fahrenheit").value(100.4));
    }

    @Test
    void shouldReturnBadRequestWhenCelsiusParamIsMissing() throws Exception {
        mockMvc.perform(get("/api/fever/check"))
                .andExpect(status().isBadRequest());
    }
}
