package com.remodelingllc.api.resource;

import com.remodelingllc.api.dto.FeatureModelDTO;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class FeatureResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HttpHeaders globalHeaders;

    private FeatureModelDTO feature;
    private MockMultipartFile file;

    @BeforeEach
    void setup() {
        feature = new FeatureModelDTO();
        feature.setTitle("New Feature");
        feature.setDescription("This is our new feature.");
        file = new MockMultipartFile("image", "image.png",
                MediaType.IMAGE_PNG_VALUE, new byte[10]);
        feature.setImage(file);
    }

    @Test
    @DisplayName("Find feature by ID - Success")
    public void findFeatureById_success() throws Exception {
        feature.setId(1);
        mockMvc.perform(get("/feature/" + feature.getId())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()) )
                .andExpect(jsonPath("$.id", is(feature.getId())) );
    }

    @Test
    @DisplayName("Find feature by ID - Not found")
    public void findFeatureById_notFound() throws Exception {
        mockMvc.perform(get("/feature/" + 100)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException) )
                .andExpect(result -> assertEquals(Objects.requireNonNull(result.getResolvedException()).getMessage(),
                        "Feature Not Found") );
    }

    @Test
    @DisplayName("Create feature - Success")
    public void createFeature_success() throws Exception {
        mockMvc.perform(post("/feature")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders)
                .flashAttr("featureModelDTO", feature))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", greaterThan(0)));
    }

    @Test
    @DisplayName("Create feature - Invalid file")
    public void createFeature_invalidFile() throws Exception {
        file = new MockMultipartFile("image", "image.pdf",
                MediaType.APPLICATION_PDF_VALUE, new byte[10]);
        feature.setImage(file);
        mockMvc.perform(post("/feature")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders)
                .flashAttr("featureModelDTO", feature))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadRequestException))
                .andExpect(result -> assertEquals(Objects.requireNonNull(result.getResolvedException()).getMessage(),
                        "Invalid Content Type") );
    }

    @Test
    @DisplayName("Update feature - Success")
    public void updateFeature_success() throws Exception {
        feature.setId(2);
        feature.setTitle("Feature Updated!");
        mockMvc.perform(put("/feature")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders)
                .flashAttr("featureModelDTO", feature))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.title", is(feature.getTitle())) );
    }

    @Test
    @DisplayName("Update feature - Not Found")
    public void updateFeature_notFound() throws Exception {
        feature.setId(100);
        mockMvc.perform(put("/feature")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders)
                .flashAttr("featureModelDTO", feature))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException) )
                .andExpect(result -> assertEquals(Objects.requireNonNull(result.getResolvedException()).getMessage(),
                        "Feature Not Found") );
    }

    @Test
    @DisplayName("Update feature - Invalid file")
    public void updateFeature_invalidFile() throws Exception {
        file = new MockMultipartFile("image", "image.pdf",
                MediaType.APPLICATION_PDF_VALUE, new byte[10]);
        feature.setId(1);
        feature.setImage(file);
        mockMvc.perform(put("/feature")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders)
                .flashAttr("featureModelDTO", feature))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadRequestException))
                .andExpect(result -> assertEquals(Objects.requireNonNull(result.getResolvedException()).getMessage(),
                        "Invalid Content Type") );
    }

    @Test
    @DisplayName("Delete feature - Success")
    public void deleteFeature_success() throws Exception {
        feature.setId(1);
        mockMvc.perform(delete("/feature/" + feature.getId())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Delete feature - Not found")
    public void deleteFeature_notFound() throws Exception {
        mockMvc.perform(delete("/feature/" + 100)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException) )
                .andExpect(result -> assertEquals(Objects.requireNonNull(result.getResolvedException()).getMessage(),
                        "Feature Not Found") );
    }

}
