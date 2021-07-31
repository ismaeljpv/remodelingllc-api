package com.remodelingllc.api.resource;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.remodelingllc.api.entity.Role;
import com.remodelingllc.api.entity.User;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;
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
public class UserResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HttpHeaders globalHeaders;

    private User user;
    private ObjectMapper mapper;

    @BeforeEach
    void setup() {
        user = new User();
        user.setUsername("superUser");
        user.setPassword("user12345.");
        user.setFirstname("Super");
        user.setLastname("User");
        user.setEmail("user@gmail.com");
        user.setStatus(Status.ACTIVE);
        var role = new Role();
        role.setId(1);
        role.setRole("ADMIN");
        role.setStatus(Status.ACTIVE);
        user.setRoles(Collections.singletonList(role));

        mapper = new ObjectMapper();
        mapper.disable(MapperFeature.USE_ANNOTATIONS);
    }

    @Test
    @DisplayName("Find user by ID - Success")
    public void findUserById_success() throws Exception {
        user.setId(1);
        mockMvc.perform(get("/user/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()) )
                .andExpect(jsonPath("$.id", is(user.getId())) );
    }

    @Test
    @DisplayName("Find user by ID - Not found")
    public void findFeatureById_notFound() throws Exception {
        mockMvc.perform(get("/user/" + 100)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException) )
                .andExpect(result -> assertEquals(Objects.requireNonNull(result.getResolvedException()).getMessage(),
                        "User Not Found") );
    }

    @Test
    @DisplayName("Create user - Success")
    public void createUser_success() throws Exception {
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders)
                .content(mapper.writeValueAsString(user)) )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", greaterThan(0)));
    }

    @Test
    @DisplayName("Create user - Invalid email")
    public void createUser_invalidEmail() throws Exception {
        user.setEmail("sascc.com");
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders)
                .content(mapper.writeValueAsString(user)) )
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException()
                        instanceof MethodArgumentNotValidException));
    }

    @Test
    @DisplayName("Create user - Repeated username")
    public void createUser_repeatedUsername() throws Exception {
        user.setUsername("admin");
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders)
                .content(mapper.writeValueAsString(user)) )
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadRequestException))
                .andExpect(result -> assertEquals(Objects.requireNonNull(result.getResolvedException()).getMessage() ,
                        "Username Is Already Taken"));
    }

    @Test
    @DisplayName("Update User - Success")
    public void updateUser_success() throws Exception {
        user.setId(2);
        user.setUsername("superAdmin");
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders)
                .content(mapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.username", is(user.getUsername())) );
    }

    @Test
    @DisplayName("Update user - Not Found")
    public void updateUser_notFound() throws Exception {
        user.setId(100);
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders)
                .content(mapper.writeValueAsString(user)) )
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException) )
                .andExpect(result -> assertEquals(Objects.requireNonNull(result.getResolvedException()).getMessage(),
                        "User Not Found") );
    }

    @Test
    @DisplayName("Update user - Invalid email")
    public void updateUser_invalidEmail() throws Exception {
        user.setEmail("sascc.com");
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders)
                .content(mapper.writeValueAsString(user)) )
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException()
                        instanceof MethodArgumentNotValidException));
    }

    @Test
    @DisplayName("Update user - Repeated username")
    public void updateUser_repeatedUsername() throws Exception {
        user.setId(2);
        user.setUsername("admin");
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders)
                .content(mapper.writeValueAsString(user)) )
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadRequestException))
                .andExpect(result -> assertEquals(Objects.requireNonNull(result.getResolvedException()).getMessage() ,
                        "Username Is Already Taken"));
    }

    @Test
    @DisplayName("Delete user - Success")
    public void deleteUser_success() throws Exception {
        user.setId(2);
        mockMvc.perform(delete("/user/" + user.getId())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Delete user - Not found")
    public void deleteUser_notFound() throws Exception {
        mockMvc.perform(delete("/user/" + 100)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .headers(globalHeaders))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException) )
                .andExpect(result -> assertEquals(Objects.requireNonNull(result.getResolvedException()).getMessage(),
                        "User Not Found") );
    }

}
