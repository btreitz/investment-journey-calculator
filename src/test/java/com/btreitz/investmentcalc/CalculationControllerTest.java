package com.btreitz.investmentcalc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {CalculationService.class, CalculationController.class})
class CalculationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void index() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().is(200));
    }

    @Test
    void calculation_mapping() throws Exception {
        this.mockMvc.perform(get("/calculate?initialInvestment=0.0&periodicContribution=0&contributionFrequency=monthly&annualGrowth=0&duration=0"))
                .andDo(print())
                .andExpect(status().is(200));
    }
}