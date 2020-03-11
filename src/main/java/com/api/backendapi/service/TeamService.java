package com.api.backendapi.service;

import com.api.backendapi.entity.Team;
import com.api.backendapi.repository.TeamRepository;
import com.api.backendapi.service.iservice.ITeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService implements ITeamService {

    @Autowired
    TeamRepository teamRepository;

    @Override
    public Team getTeamInfoById(Long id) {
        return teamRepository.findTeamInfoById(id);
    }

    @Override
    public Team createNewTeam(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public List<Team> getAllTeam() {
        return teamRepository.findAll();
    }
}
