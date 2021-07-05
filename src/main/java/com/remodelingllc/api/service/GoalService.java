package com.remodelingllc.api.service;

import com.remodelingllc.api.entity.Goal;
import com.remodelingllc.api.exception.BadRequestException;
import com.remodelingllc.api.repository.GoalRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalService {

    private GoalRepository goalRepository;

    public GoalService(final GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    public List<Goal> findAll(){
        return (List<Goal>) goalRepository.findAll();
    }

    public Page<Goal> findAllPaginated(final int page, final int size) {
        return goalRepository.findAll(PageRequest.of(page, size));
    }

    public Goal findById(final int id) {
        var goal = goalRepository.findById(id);
        if (goal.isEmpty()) {
            throw new BadRequestException("Goal Not Found");
        }
        return goal.get();
    }

    public Goal save(final Goal goal){
        return goalRepository.save(goal);
    }

    public Goal update(final Goal goal) {
        var oldGoal = goalRepository.findById(goal.getId());
        if (oldGoal.isEmpty()) {
            throw new BadRequestException("Goal Not Found");
        }
        return goalRepository.save(goal);
    }

    public void delete(final int id) {
        var oldGoal = goalRepository.findById(id);
        if (oldGoal.isEmpty()) {
            throw new BadRequestException("Goal Not Found");
        }
        goalRepository.delete(oldGoal.get());
    }
}
