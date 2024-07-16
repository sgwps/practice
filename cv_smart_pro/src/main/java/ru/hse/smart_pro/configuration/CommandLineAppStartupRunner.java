package ru.hse.smart_pro.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.hse.smart_pro.data.service.*;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
    @Autowired
    UserService userService;

    @Autowired
    HardSkillService hardSkillService;

    @Autowired
    SoftSkillService softSkillService;

    @Autowired
    LanguageService languageService;

    @Autowired
    LanguageLevelService languageLevelService;



    @Override
    public void run(String... args) throws Exception {
        /*User user = null;
        try {
            user = userService.loadUserByUsername("username");
        } catch (UsernameNotFoundException e) {
            user = User.builder().username("username").password("password").build();
            userService.saveNew(user);
        }

        hardSkillService.save(new HardSkill(null, "HardSkill 1", new HashSet<>()));
        hardSkillService.save(new HardSkill(null, "HardSkill 2", new HashSet<>()));
        hardSkillService.save(new HardSkill(null, "HardSkill 3", new HashSet<>()));
        hardSkillService.save(new HardSkill(null, "HardSkill 4", new HashSet<>()));
        hardSkillService.save(new HardSkill(null, "HardSkill 5", new HashSet<>()));
        softSkillService.save(new SoftSkill(null, "SoftSkill 1", new HashSet<>()));
        softSkillService.save(new SoftSkill(null, "SoftSkill 2", new HashSet<>()));
        softSkillService.save(new SoftSkill(null, "SoftSkill 3", new HashSet<>()));
        languageService.save(new Language(null, "Russian"));
        languageService.save(new Language(null, "English"));
        languageService.save(new Language(null, "French"));
        languageLevelService.save(new LanguageLevel(null, "A1"));
        languageLevelService.save(new LanguageLevel(null, "A2"));
        languageLevelService.save(new LanguageLevel(null, "B1"));
        languageLevelService.save(new LanguageLevel(null, "B2"));*/
    }
}