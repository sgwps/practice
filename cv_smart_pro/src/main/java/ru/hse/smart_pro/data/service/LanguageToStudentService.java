package ru.hse.smart_pro.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hse.smart_pro.data.models.LanguageToStudent;
import ru.hse.smart_pro.data.models.User;
import ru.hse.smart_pro.data.repositories.LanguageToStudentRepository;

import java.util.List;

@Service
public class LanguageToStudentService {
    @Autowired
    LanguageToStudentRepository languageToStudentRepository;

    public LanguageToStudent save(LanguageToStudent languageToStudent) {
        return languageToStudentRepository.save(languageToStudent);
    }

    public List<LanguageToStudent> findByUser(User user) {
        return languageToStudentRepository.findByUser(user);
    }

    public void delete(LanguageToStudent languageToStudent) {
        languageToStudentRepository.deleteById(languageToStudent.getId());
    }


}
