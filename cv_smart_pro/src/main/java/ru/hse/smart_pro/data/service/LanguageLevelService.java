package ru.hse.smart_pro.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hse.smart_pro.data.models.LanguageLevel;
import ru.hse.smart_pro.data.repositories.LanguageLevelRepository;

import java.util.List;

@Service
public class LanguageLevelService {
    @Autowired
    LanguageLevelRepository languageLevelRepository;

    public LanguageLevel save(LanguageLevel languageLevel) {
        return languageLevelRepository.save(languageLevel);
    }

    public List<LanguageLevel> getAll() {
        return languageLevelRepository.findAll();
    }

    public LanguageLevel findByName(String name) {
        return languageLevelRepository.findByName(name).get(0);
    }

}
