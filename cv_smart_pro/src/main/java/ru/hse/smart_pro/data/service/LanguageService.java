package ru.hse.smart_pro.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hse.smart_pro.data.models.Language;
import ru.hse.smart_pro.data.repositories.LanguageRepository;

import java.util.List;

@Service
public class LanguageService {
    @Autowired
    LanguageRepository languageRepository;

    public Language save(Language language) {
        return languageRepository.save(language);
    }

    public List<Language> getAll() {
        return languageRepository.findAll();
    }

    public Language findByName(String name) {
        return languageRepository.findByName(name).get(0);
    }
}
