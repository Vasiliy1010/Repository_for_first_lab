package ru.labs.hm1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.labs.hm1.model.Mission;
import ru.labs.hm1.service.MissionService;

import java.util.List;

@RestController
@RequestMapping("/api/missions")
@RequiredArgsConstructor
public class MissionController {
    private final MissionService missionService;

    @PostMapping
    public Mission addMission(@RequestBody Mission mission) {
        return missionService.createMission(mission);
    }

    @GetMapping
    public List<Mission> getAll() {
        return missionService.getAllMissions();
    }

    @GetMapping("/{id}")
    public Mission getMission(@PathVariable Integer id) throws Exception {
        return missionService.getMissionById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteMission(@PathVariable Integer id) {
        missionService.deleteMissionById(id);
    }

    @PutMapping("/{id}")
    public Mission updateMission(@PathVariable Integer id, @PathVariable String curseName, @PathVariable String outcome, @PathVariable String sorcerersNames) throws Exception {
        return missionService.updateMission(id, curseName, sorcerersNames, outcome);
    }
}