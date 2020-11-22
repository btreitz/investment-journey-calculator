package com.btreitz.investmentcalc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {CalculationService.class, CalculationController.class})
class CalculationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void index() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().is(200));
    }

    @Test
    void calculation_mapping() throws Exception {
        this.mockMvc.perform(get("/calculate?initialInvestment=0.0&periodicContribution=0&contributionFrequency=1&annualGrowth=0&duration=0"))
                .andExpect(status().is(200));
    }

    @Test
    void input_validation() throws Exception {
        // Initial Investment negative
        this.mockMvc.perform(get("/calculate?initialInvestment=-1000.0&periodicContribution=200&contributionFrequency=1&annualGrowth=8&duration=15"))
                .andExpect(status().isBadRequest());

        // Periodic Contribution negative
        this.mockMvc.perform(get("/calculate?initialInvestment=1000.0&periodicContribution=-200&contributionFrequency=1&annualGrowth=8&duration=15"))
                .andExpect(status().isBadRequest());

        // Contribution Frequency above 12
        this.mockMvc.perform(get("/calculate?initialInvestment=1000.0&periodicContribution=200&contributionFrequency=13&annualGrowth=8&duration=15"))
                .andExpect(status().isBadRequest());

        // Annual Growth above 100 %
        this.mockMvc.perform(get("/calculate?initialInvestment=1000.0&periodicContribution=200&contributionFrequency=1&annualGrowth=110&duration=15"))
                .andExpect(status().isBadRequest());

        // Duration is zero
        this.mockMvc.perform(get("/calculate?initialInvestment=1000.0&periodicContribution=200&contributionFrequency=1&annualGrowth=8&duration=0"))
                .andExpect(status().isBadRequest());
    }
}