package com.saksonik.headmanager;

import com.saksonik.headmanager.configuration.SecurityConfig;
import com.saksonik.headmanager.controller.CallScheduleController;
import com.saksonik.headmanager.service.ScheduledCallService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CallScheduleController.class)
@Import(SecurityConfig.class)
public class WebTest {
    @Autowired
    private ScheduledCallService scheduledCallService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithAnonymousUser
    public void withoutAuthentication() throws Exception {
        mockMvc.perform(get("/call-schedule"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", roles = {"STUDENT"})
    public void withAuthentication() throws Exception {
        mockMvc.perform(get("/call-schedule"))
                .andExpect(status().isOk());
    }
//
//    @Test
//    @WithMockUser(username = "user", roles = {"USER"})
//    public void testSecuredEndpointWithUserRole() throws Exception {
//        mockMvc.perform(get("/api/secured"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @WithMockUser(username = "user", roles = {"USER"})
//    public void testAdminEndpointWithUserRole() throws Exception {
//        mockMvc.perform(get("/api/admin"))
//                .andExpect(status().isForbidden());
//    }
//
//    @Test
//    @WithMockUser(username = "admin", roles = {"ADMIN"})
//    public void testAdminEndpointWithAdminRole() throws Exception {
//        mockMvc.perform(get("/api/admin"))
//                .andExpect(status().isOk());
//    }
}
