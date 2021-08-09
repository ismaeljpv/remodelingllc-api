package com.remodelingllc.api.resource;

import com.remodelingllc.api.dto.PostModelDTO;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class PostResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HttpHeaders globalHeaders;

    private PostModelDTO post;
    private MockMultipartFile file;

    @BeforeEach
    void setup () {
        post = new PostModelDTO();
        post.setClient("Donald Trump");
        post.setTitle("New House Project");
        post.setDescription("This is our brand new project for former president Donald Trump");
        post.setTags(new ArrayList<>(Arrays.asList("House", "Construction")));
        post.setProjectDate("2021-05-30");
        post.setStatus(Status.ACTIVE);
        post.setSubcontract(false);
        post.setUserId(1);
        file = new MockMultipartFile("thumbnail", "image.png",
                MediaType.IMAGE_PNG_VALUE, new byte[10]);
        post.setThumbnail(file);
    }

    @Test
    @DisplayName("Get Post By ID - Success")
    public void getPostById_success() throws Exception {
        int postId = 1;
        mockMvc.perform(MockMvcRequestBuilders.get("/post/" + postId)
                .contentType(MediaType.APPLICATION_JSON) )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()) )
                .andExpect(jsonPath("$.id", is(postId)) );
    }

    @Test
    @DisplayName("Get Post By ID - Post Not Found")
    public void getPostById_postNotFound() throws Exception {
        int postId = 9999;
        mockMvc.perform(MockMvcRequestBuilders.get("/post/" + postId)
                .contentType(MediaType.APPLICATION_JSON) )
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException))
                .andExpect(result -> assertEquals("Post Not Found",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()) );
    }

    @Test
    @DisplayName("Create Post - Success")
    public void createPost_success() throws Exception {
        mockMvc.perform(post("/post")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .headers(globalHeaders)
                .flashAttr("postModelDTO", post))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", greaterThan(0)) );
    }

    @Test
    @DisplayName("Create Post - User Null")
    public void createPost_userNull() throws Exception {
        post.setUserId(0);
        mockMvc.perform(post("/post")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders)
                .flashAttr("postModelDTO", post))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Create Post - User Not Found")
    public void createPost_userNotFound() throws Exception {
        post.setUserId(99);
        mockMvc.perform(post("/post")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders)
                .flashAttr("postModelDTO", post))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException))
                .andExpect(result -> assertEquals("User Not Found",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()) );
    }

    @Test
    @DisplayName("Create Post - Invalid Media Type")
    public void createPost_invalidMediaType() throws Exception {
        file = new MockMultipartFile("thumbnail", "file.pdf",
                MediaType.APPLICATION_PDF_VALUE, new byte[10]);
        post.setThumbnail(file);
        mockMvc.perform(post("/post")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders)
                .flashAttr("postModelDTO", post))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadRequestException))
                .andExpect(result -> assertEquals("Invalid Content Type",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()) );
    }

    @Test
    @DisplayName("Update Post - Success")
    public void updatePost_success() throws Exception {
        post.setId(1);
        mockMvc.perform(MockMvcRequestBuilders.put("/post")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .headers(globalHeaders)
                .flashAttr("postModelDTO", post))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()) )
                .andExpect(jsonPath("$.id", is(post.getId())) );
    }

    @Test
    @DisplayName("Update Post - User Null")
    public void updatePost_userNull() throws Exception {
        post.setUserId(0);
        mockMvc.perform(MockMvcRequestBuilders.put("/post")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders)
                .flashAttr("postModelDTO", post))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Update Post - Post Not Found")
    public void updatePost_postNotFound() throws Exception {
        post.setId(99);
        mockMvc.perform(MockMvcRequestBuilders.put("/post")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders)
                .flashAttr("postModelDTO", post))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException))
                .andExpect(result -> assertEquals("Post Not Found",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()) );
    }

    @Test
    @DisplayName("Update Post - User Not Found")
    public void updatePost_userNotFound() throws Exception {
        post.setId(1);
        post.setUserId(99);
        mockMvc.perform(MockMvcRequestBuilders.put("/post")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders)
                .flashAttr("postModelDTO", post))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException))
                .andExpect(result -> assertEquals("User Not Found",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()) );
    }

    @Test
    @DisplayName("Update Post - Invalid Media Type")
    public void updatePost_invalidMediaType() throws Exception {
        file = new MockMultipartFile("thumbnail", "file.pdf",
                MediaType.APPLICATION_PDF_VALUE, new byte[10]);
        post.setThumbnail(file);
        post.setId(1);
        mockMvc.perform(MockMvcRequestBuilders.put("/post")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders)
                .flashAttr("postModelDTO", post))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadRequestException))
                .andExpect(result -> assertEquals("Invalid Content Type",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()) );
    }

    @Test
    @DisplayName("Delete Post - Success")
    public void deletePost_success() throws Exception {
        post.setId(1);
        mockMvc.perform(MockMvcRequestBuilders.delete("/post/" + post.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .headers(globalHeaders))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Delete Post - Post Not Found")
    public void deletePost_PostNotFound() throws Exception {
        post.setId(99);
        mockMvc.perform(MockMvcRequestBuilders.delete("/post/" + post.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .headers(globalHeaders))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException))
                .andExpect(result -> assertEquals("Post Not Found",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()) );
    }

}
