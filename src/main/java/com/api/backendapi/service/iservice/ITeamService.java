package com.api.backendapi.service.iservice;

import com.api.backendapi.entity.Team;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ITeamService {

    Team getTeamInfoById(Long id);

    Team createNewTeam(String teamname);

    List<Team> getAllTeam();
}
