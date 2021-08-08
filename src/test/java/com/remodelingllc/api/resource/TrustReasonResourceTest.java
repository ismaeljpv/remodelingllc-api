package com.remodelingllc.api.resource;

import com.remodelingllc.api.dto.TrustReasonModelDTO;
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
public class TrustReasonResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HttpHeaders globalHeaders;

    private TrustReasonModelDTO reason;
    private MockMultipartFile file;

    @BeforeEach
    void setup() {
        reason = new TrustReasonModelDTO();
        reason.setTitle("New Trust Reason");
        reason.setDescription("New Trust Reason Description");
        file = new MockMultipartFile("image", "image.png",
                MediaType.IMAGE_PNG_VALUE, new byte[10]);
        reason.setImage(file);
    }

    @Test
    @DisplayName("Find trust reason by ID - Success")
    public void findTrustReasonById_success() throws Exception {
        reason.setId(1);
        mockMvc.perform(get("/trustReason/" + reason.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(reason.getId())) );
    }

    @Test
    @DisplayName("Find trust reason by ID - Not found")
    public void findTrustReasonById_notFound() throws Exception {
        reason.setId(100);
        mockMvc.perform(get("/trustReason/" + reason.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException) )
                .andExpect(result -> assertEquals(Objects.requireNonNull(result.getResolvedException()).getMessage(),
                        "Trust Reason Not Found") );
    }

    @Test
    @DisplayName("Create trust reason - Success")
    public void createTrustReason_success() throws Exception {
        mockMvc.perform(post("/trustReason")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders)
                .flashAttr("trustReasonModelDTO", reason))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", greaterThan(0)));
    }

    @Test
    @DisplayName("Create trust reason - Invalid file")
    public void createTrustReason_invalidFile() throws Exception {
        file = new MockMultipartFile("image", "image.pdf",
                MediaType.APPLICATION_PDF_VALUE, new byte[10]);
        reason.setImage(file);
        mockMvc.perform(post("/trustReason")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders)
                .flashAttr("trustReasonModelDTO", reason))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadRequestException))
                .andExpect(result -> assertEquals(Objects.requireNonNull(result.getResolvedException()).getMessage(),
                        "Invalid Content Type"));
    }

    @Test
    @DisplayName("Create trust reason - Success")
    public void updateTrustReason_success() throws Exception {
        reason.setId(1);
        reason.setTitle("Updated Trust Reason");
        mockMvc.perform(put("/trustReason")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders)
                .flashAttr("trustReasonModelDTO", reason))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(reason.getId())) )
                .andExpect(jsonPath("$.title", is(reason.getTitle())) );
    }

    @Test
    @DisplayName("Create trust reason - Invalid file")
    public void updateTrustReason_invalidFile() throws Exception {
        file = new MockMultipartFile("image", "image.pdf",
                MediaType.APPLICATION_PDF_VALUE, new byte[10]);
        reason.setImage(file);
        mockMvc.perform(put("/trustReason")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders)
                .flashAttr("trustReasonModelDTO", reason))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadRequestException))
                .andExpect(result -> assertEquals(Objects.requireNonNull(result.getResolvedException()).getMessage(),
                        "Invalid Content Type"));
    }

    @Test
    @DisplayName("Delete trust reason - Success")
    public void deleteTrustReason_success() throws Exception {
        reason.setId(2);
        mockMvc.perform(delete("/trustReason/" + reason.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Delete trust reason - Not found")
    public void deleteTrustReason_notFound() throws Exception {
        reason.setId(100);
        mockMvc.perform(delete("/trustReason/" + reason.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException) )
                .andExpect(result -> assertEquals(Objects.requireNonNull(result.getResolvedException()).getMessage(),
                        "Trust Reason Not Found") );
    }

}
