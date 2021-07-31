package com.remodelingllc.api.resource;

import com.remodelingllc.api.dto.ServiceModelDTO;
import com.remodelingllc.api.entity.enums.Status;
import com.remodelingllc.api.exception.BadRequestException;
import com.remodelingllc.api.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class ServicesResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HttpHeaders globalHeaders;

    private ServiceModelDTO service;
    private MockMultipartFile file;

    @BeforeEach
    void setup() {
        service = new ServiceModelDTO();
        service.setService("New Service");
        service.setDescription("This is my new brand service");
        service.setStatus(Status.ACTIVE);
        file = new MockMultipartFile("thumbnail", "image.png",
                MediaType.IMAGE_PNG_VALUE, new byte[10]);
        service.setThumbnail(file);
    }

    @Test
    @DisplayName("Find service by ID - Success")
    public void findServiceById_success() throws Exception {
        service.setId(1);
        mockMvc.perform(get("/services/" + service.getId())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()) )
                .andExpect(jsonPath("$.id", is(service.getId())));
    }

    @Test
    @DisplayName("Find service by ID - Not found")
    public void findServiceById_notFound() throws Exception {
        mockMvc.perform(get("/services/" + 100)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException) )
                .andExpect(result -> assertEquals(Objects.requireNonNull(result.getResolvedException()).getMessage(),
                        "Service Not Found") );
    }

    @Test
    @DisplayName("Create service - Success")
    public void createService_success() throws Exception {
        mockMvc.perform(post("/services")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders)
                .flashAttr("serviceModelDTO", service))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", greaterThan(0)));
    }

    @Test
    @DisplayName("Create service - Invalid file")
    public void createService_invalidFile() throws Exception {
        file = new MockMultipartFile("thumbnail", "image.pdf",
                MediaType.APPLICATION_PDF_VALUE, new byte[10]);
        service.setThumbnail(file);
        mockMvc.perform(post("/services")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders)
                .flashAttr("serviceModelDTO", service))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadRequestException))
                .andExpect(result -> assertEquals(Objects.requireNonNull(result.getResolvedException()).getMessage(),
                        "Invalid Content Type") );
    }

    @Test
    @DisplayName("Update service - Success")
    public void updateService_success() throws Exception {
        service.setId(1);
        service.setService("Service Updated");
        mockMvc.perform(put("/services")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders)
                .flashAttr("serviceModelDTO", service))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.service", is(service.getService())));
    }

    @Test
    @DisplayName("Update service - Not Found")
    public void updateService_notFound() throws Exception {
        service.setId(100);
        mockMvc.perform(put("/services")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders)
                .flashAttr("serviceModelDTO", service))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException) )
                .andExpect(result -> assertEquals(Objects.requireNonNull(result.getResolvedException()).getMessage(),
                        "Service Not Found") );
    }

    @Test
    @DisplayName("Update service - Invalid file")
    public void updateService_invalidFile() throws Exception {
        file = new MockMultipartFile("thumbnail", "image.pdf",
                MediaType.APPLICATION_PDF_VALUE, new byte[10]);
        service.setId(1);
        service.setThumbnail(file);
        mockMvc.perform(put("/services")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders)
                .flashAttr("serviceModelDTO", service))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadRequestException))
                .andExpect(result -> assertEquals(Objects.requireNonNull(result.getResolvedException()).getMessage(),
                        "Invalid Content Type") );
    }

    @Test
    @DisplayName("Delete service - Success")
    public void deleteService_success() throws Exception {
        service.setId(1);
        mockMvc.perform(delete("/services/" + service.getId())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Delete service - Not found")
    public void deleteService_notFound() throws Exception {
        mockMvc.perform(delete("/services/" + 100)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException) )
                .andExpect(result -> assertEquals(Objects.requireNonNull(result.getResolvedException()).getMessage(),
                        "Service Not Found") );
    }

}

