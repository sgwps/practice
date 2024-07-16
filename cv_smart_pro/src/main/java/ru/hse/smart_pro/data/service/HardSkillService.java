package ru.hse.smart_pro.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hse.smart_pro.data.models.HardSkill;
import ru.hse.smart_pro.data.repositories.HardSkillRepository;

import java.util.List;

@Service
public class HardSkillService {

    @Autowired
    HardSkillRepository hardSkillRepository;

    public HardSkill save(HardSkill hardSkill) {
        return hardSkillRepository.save(hardSkill);
    }


    public List<HardSkill> getAll() {
        return hardSkillRepository.findAll();
    }

    public HardSkill findByName(String name) {
        return hardSkillRepository.findByName(name).get(0);
    }
}
