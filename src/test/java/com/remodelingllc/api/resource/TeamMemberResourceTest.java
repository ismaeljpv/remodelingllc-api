package com.remodelingllc.api.resource;

import com.remodelingllc.api.dto.TeamMemberModelDTO;
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
public class TeamMemberResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HttpHeaders globalHeaders;

    private TeamMemberModelDTO member;
    private MockMultipartFile file;

    @BeforeEach
    void setup() {
        member = new TeamMemberModelDTO();
        member.setName("New Member");
        member.setPosition("CEO");
        file = new MockMultipartFile("photo", "image.png",
                MediaType.IMAGE_PNG_VALUE, new byte[10]);
        member.setPhoto(file);
    }

    @Test
    @DisplayName("Find team member by ID - Success")
    public void findTeamMemberById_success() throws Exception {
        member.setId(1);
        mockMvc.perform(get("/team/" + member.getId())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()) )
                .andExpect(jsonPath("$.id", is(member.getId())) );
    }

    @Test
    @DisplayName("Find team member by ID - Not found")
    public void findTeamMemberById_notFound() throws Exception {
        mockMvc.perform(get("/team/" + 100)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException) )
                .andExpect(result -> assertEquals(Objects.requireNonNull(result.getResolvedException()).getMessage(),
                        "Team Member Not Found") );
    }

    @Test
    @DisplayName("Create team member - Success")
    public void createTeamMember_success() throws Exception {
        mockMvc.perform(post("/team")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders)
                .flashAttr("teamMemberModelDTO", member))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", greaterThan(0)));
    }

    @Test
    @DisplayName("Create team member - Invalid file")
    public void createTeamMember_invalidFile() throws Exception {
        file = new MockMultipartFile("image", "image.pdf",
                MediaType.APPLICATION_PDF_VALUE, new byte[10]);
        member.setPhoto(file);
        mockMvc.perform(post("/team")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders)
                .flashAttr("teamMemberModelDTO", member))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadRequestException))
                .andExpect(result -> assertEquals(Objects.requireNonNull(result.getResolvedException()).getMessage(),
                        "Invalid Content Type") );
    }

    @Test
    @DisplayName("Delete team member - Success")
    public void deleteTeamMember_success() throws Exception {
        member.setId(2);
        mockMvc.perform(delete("/team/" + member.getId())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Delete team member - Not found")
    public void deleteTeamMember_notFound() throws Exception {
        mockMvc.perform(delete("/team/" + 100)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException) )
                .andExpect(result -> assertEquals(Objects.requireNonNull(result.getResolvedException()).getMessage(),
                        "Team Member Not Found") );
    }

}
