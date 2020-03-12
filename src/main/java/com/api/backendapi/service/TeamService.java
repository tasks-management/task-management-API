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
    public Team findTeamByID(Long id) {
        return teamRepository.findById(id).orElse(null);
    }

    @Override
    public Team createNewTeam(String teamname) {
        Team team = new Team();
        team.setName(teamname);
        return teamRepository.save(team);
    }

    @Override
    public List<Team> getAllTeam() {
        return teamRepository.findAll();
    }
}
