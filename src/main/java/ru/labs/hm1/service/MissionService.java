package ru.labs.hm1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.labs.hm1.model.Mission;
import ru.labs.hm1.model.MissionFactory;
import ru.labs.hm1.parsing.MissionParser;
import ru.labs.hm1.parsing.ParserFactory;
import ru.labs.hm1.repository.MissionRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MissionService {
    private final MissionRepository missionRepository;
    private final MissionFactory missionFactory;

    @Transactional
    public void loadMissionFromFile(String filePath) throws Exception {
        MissionParser parser = ParserFactory.getParser(filePath);
        Mission mission = parser.loadMission(filePath);
        missionRepository.save(mission);
    }

    @Transactional
    public Mission createMission(Mission mission) {
        return missionRepository.save(mission);
    }

    @Transactional
    public List<Mission> getAllMissions() {
        return missionRepository.findAll();
    }

    @Transactional
    public Mission getMissionById(Integer id) throws Exception {
        Optional<Mission> optionalMission = missionRepository.findById(id);
        if (optionalMission.isEmpty()) {
            throw new Exception("Миссия не найдена: " + id);
        }
        return optionalMission.get();
    }

    @Transactional
    public void deleteMissionById(Integer id) {
        if (missionRepository.existsById(id)) {
            missionRepository.deleteById(id);
        }
    }

    @Transactional
    public Mission updateMission(Integer id, String curseName, String sorcerersNames, String outcome) throws Exception {
        Mission mission = getMissionById(id);
        missionFactory.updateMission(mission, curseName, sorcerersNames, outcome);
        return missionRepository.save(mission);
    }

    @Transactional(readOnly = true)
    public String getReport(Integer id, String type) {
        Optional<Mission> optionalMission = missionRepository.findById(id);
        if (optionalMission.isEmpty()) {
            throw new RuntimeException("Миссия с id " + id + " не найдена");
        }

        Mission mission = optionalMission.get();

        return missionFactory.getReport(mission, type);
    }
}