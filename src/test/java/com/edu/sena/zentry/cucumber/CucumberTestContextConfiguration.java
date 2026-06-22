package com.edu.sena.zentry.cucumber;

import com.edu.sena.zentry.IntegrationTest;
import com.edu.sena.zentry.security.AuthoritiesConstants;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;

@CucumberContextConfiguration
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser(authorities = AuthoritiesConstants.ADMIN)
public class CucumberTestContextConfiguration {}
