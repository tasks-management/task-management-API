package com.api.backendapi.controller;

import com.api.backendapi.entity.Team;
import com.api.backendapi.service.iservice.ITeamService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeamController {
    @Autowired
    ITeamService teamService;

    @RequestMapping(value = "/api/v1/team/{id:\\d+}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> getTeamInfoById(@PathVariable("id") Long id) {
        JsonObject jsonObject = new JsonObject();
        if (id == null) {
            jsonObject.addProperty("message", "Cannot find without id");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonObject.toString());
        }
        Team result = teamService.findTeamByID(id);
        if (result.getName() == null) {
            jsonObject.addProperty("message", "Cannot find that team with that id");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonObject.toString());
        }
        jsonObject.addProperty("team_name", result.getName());
        return ResponseEntity.status(HttpStatus.OK).body(jsonObject.toString());
    }

//    @RequestMapping(value = "/api/v1/team/create", method = RequestMethod.POST)
//    @ResponseBody
//    public ResponseEntity<Object> createNewTeam(@RequestBody Team team) {
//        Team result = teamService.createNewTeam(team);
//        JsonObject jsonObject = new JsonObject();
//        if (result.getId() == null) {
//            jsonObject.addProperty("id", new Long(0));
//            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(jsonObject.toString());
//        } else {
//            return new ResponseEntity<>(result, HttpStatus.OK);
//        }
//    }

    @RequestMapping(value = "/api/v1/team/teams", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> listAllTeam() {
        return new ResponseEntity<>(teamService.getAllTeam(), HttpStatus.OK);
    }
}
