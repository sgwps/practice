package ru.hse.smart_pro.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hse.smart_pro.data.models.SoftSkill;
import ru.hse.smart_pro.data.repositories.SoftSkillRepository;

import java.util.List;

@Service
public class SoftSkillService {
    @Autowired
    SoftSkillRepository softSkillRepository;

    public SoftSkill save(SoftSkill softSkill) {
        return softSkillRepository.save(softSkill);
    }

    public List<SoftSkill> getAll() {
        return softSkillRepository.findAll();
    }

    public SoftSkill findByName(String name) {
        return softSkillRepository.findByName(name).get(0);
    }
}
