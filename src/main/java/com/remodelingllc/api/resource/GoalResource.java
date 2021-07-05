package com.remodelingllc.api.resource;

import com.remodelingllc.api.entity.Feature;
import com.remodelingllc.api.entity.Goal;
import com.remodelingllc.api.service.GoalService;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GoalResource {

    private final GoalService goalService;

    public GoalResource(final GoalService goalService) {
        this.goalService = goalService;
    }

    @GetMapping(value = "/goal", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Goal> findAll() {
        return goalService.findAll();
    }

    @GetMapping(value = "/goal", params = {"page", "size"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Goal> findAllPaginated(@RequestParam("page") final int page, @RequestParam("size") final int size) {
        return goalService.findAllPaginated(page, size);
    }

    @GetMapping(value = "/goal/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Goal findById(@PathVariable final int id) {
        return goalService.findById(id);
    }

    @PostMapping(value = "/goal", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Goal save(@Validated @RequestBody final Goal goal) {
        return goalService.save(goal);
    }

    @PutMapping(value = "/goal", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Goal update(@Validated @RequestBody final Goal goal) {
        return goalService.update(goal);
    }

    @DeleteMapping(value = "/goal/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable final int id) {
        goalService.delete(id);
    }
}
