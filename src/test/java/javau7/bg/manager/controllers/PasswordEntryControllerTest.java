package javau7.bg.manager.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import javau7.bg.manager.models.PasswordEntry;
import javau7.bg.manager.services.PasswordEntryService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PasswordEntryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PasswordEntryService passwordEntryService;

    @Test
    @WithMockUser // This annotation provides a mock user with roles
    public void testGetAllPasswordEntries() throws Exception {
        List<PasswordEntry> passwordEntries = Arrays.asList(new PasswordEntry(), new PasswordEntry());
        Mockito.when(passwordEntryService.getAllPasswordEntries()).thenReturn(passwordEntries);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/password-entries")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(2)));
    }
}
