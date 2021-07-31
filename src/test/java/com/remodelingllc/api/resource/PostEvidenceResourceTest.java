package com.remodelingllc.api.resource;

import com.remodelingllc.api.dto.PostEvidenceModelDTO;
import com.remodelingllc.api.exception.BadRequestException;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Objects;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class PostEvidenceResourceTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private HttpHeaders globalHeaders;

    private PostEvidenceModelDTO model;
    private MockMultipartFile file;

    @BeforeEach
    void setup() {
        model = new PostEvidenceModelDTO();
        model.setPostId(1);
    }

    @Test
    @DisplayName("Get Evidences By Post ID - Success ")
    public void getEvidencesByPostId_Success() throws Exception {
        int postId = 1;
        mockMvc.perform(MockMvcRequestBuilders.get("/postEvidence?postId=" + postId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()) )
                .andExpect(jsonPath("$.content.*", hasSize(2)) );
    }

    @Test
    @DisplayName("Get Evidence By ID - Success ")
    public void getEvidenceById_Success() throws Exception {
        int id = 1;
        mockMvc.perform(MockMvcRequestBuilders.get("/postEvidence/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()) )
                .andExpect(jsonPath("$.id", is(id)) );
    }

    @Test
    @DisplayName("Get Evidence By ID - Not Found ")
    public void getEvidenceById_notFound() throws Exception {
        int id = 99;
        mockMvc.perform(MockMvcRequestBuilders.get("/postEvidence/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadRequestException) )
                .andExpect(result -> assertEquals(Objects.requireNonNull(result.getResolvedException()).getMessage(),
                        "Evidence Not Found"));
    }

    @Test
    @DisplayName("Create Evidence Picture - Success")
    public void createEvidence_uploadPicture_success() throws Exception {
        file = new MockMultipartFile("picture", "picture.png",
                MediaType.IMAGE_PNG_VALUE, new byte[10]);
        model.setType(com.remodelingllc.api.entity.enums.MediaType.PICTURE);
        model.setPicture(file);
        mockMvc.perform(post("/postEvidence")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .headers(globalHeaders)
                .flashAttr("postEvidenceModelDTO", model))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()) )
                .andExpect(jsonPath("$.id", greaterThan(0) ));
    }

    @Test
    @DisplayName("Create Evidence Picture - Invalid format")
    public void createEvidence_uploadPicture_invalidFormat() throws Exception {
        file = new MockMultipartFile("picture", "picture.pdf",
                MediaType.APPLICATION_PDF_VALUE, new byte[10]);
        model.setType(com.remodelingllc.api.entity.enums.MediaType.PICTURE);
        model.setPicture(file);
        mockMvc.perform(post("/postEvidence")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .headers(globalHeaders)
                .flashAttr("postEvidenceModelDTO", model))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadRequestException) )
                .andExpect(result -> assertEquals(Objects.requireNonNull(result.getResolvedException()).getMessage(),
                        "Invalid Content Type"));
    }

    @Test
    @DisplayName("Create Evidence Video - Success")
    public void createEvidence_uploadVideo_success() throws Exception {
        model.setVideoUrl("https://www.youtube.com/watch?v=pNHm9LW4uE8");
        model.setType(com.remodelingllc.api.entity.enums.MediaType.VIDEO);
        mockMvc.perform(post("/postEvidence")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .headers(globalHeaders)
                .flashAttr("postEvidenceModelDTO", model))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()) )
                .andExpect(jsonPath("$.id", greaterThan(0) ));
    }

    @Test
    @DisplayName("Create Evidence Video - Null Value")
    public void createEvidence_uploadVideo_nullValue() throws Exception {
        model.setVideoUrl(null);
        model.setType(com.remodelingllc.api.entity.enums.MediaType.VIDEO);
        mockMvc.perform(post("/postEvidence")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .headers(globalHeaders)
                .flashAttr("postEvidenceModelDTO", model))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadRequestException) )
                .andExpect(result -> assertEquals(Objects.requireNonNull(result.getResolvedException()).getMessage(),
                        "Video URL cant be null"));
    }

    @Test
    @DisplayName("Delete Evidence - Success ")
    public void deleteEvidence_Success() throws Exception {
        int id = 2;
        mockMvc.perform(MockMvcRequestBuilders.delete("/postEvidence/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Delete Evidence - Not Found ")
    public void deleteEvidence_notFound() throws Exception {
        int id = 99;
        mockMvc.perform(MockMvcRequestBuilders.delete("/postEvidence/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadRequestException) )
                .andExpect(result -> assertEquals(Objects.requireNonNull(result.getResolvedException()).getMessage(),
                        "Evidence Not Found"));
    }

}
