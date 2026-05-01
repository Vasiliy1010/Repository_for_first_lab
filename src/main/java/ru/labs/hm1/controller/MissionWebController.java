package ru.labs.hm1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.labs.hm1.model.Mission;
import ru.labs.hm1.service.MissionService;

import java.nio.file.Files;
import java.nio.file.Path;

@Controller
@RequestMapping("/missions")
@RequiredArgsConstructor
public class MissionWebController {
    private final MissionService missionService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("missions", missionService.getAllMissions());
        return "index";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            Path tempFile = Files.createTempFile("upload_", file.getOriginalFilename());
            file.transferTo(tempFile);
            missionService.loadMissionFromFile(tempFile.toString());
            Files.delete(tempFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/missions";
    }

    @GetMapping("/report/{id}")
    public String showReport(@PathVariable Integer id, @RequestParam(defaultValue = "small") String type, Model model) {
        String report = missionService.getReport(id, type);
        model.addAttribute("reportText", report);
        model.addAttribute("missionId", id);
        model.addAttribute("currentType", type);
        return "report";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) throws Exception {
        Mission mission = missionService.getMissionById(id);
        model.addAttribute("mission", mission);
        return "edit";
    }

    @PostMapping("/update/{id}")
    public String updateMissionWeb(@PathVariable Integer id, @RequestParam String curseName, @RequestParam String outcome, @RequestParam String sorcerersNames) throws Exception {
        missionService.updateMission(id, curseName, sorcerersNames, outcome);
        return "redirect:/missions";
    }
}
