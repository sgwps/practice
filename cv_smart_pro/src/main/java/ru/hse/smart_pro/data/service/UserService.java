package ru.hse.smart_pro.data.service;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.hse.smart_pro.configuration.PasswordEncoderConfig;
import ru.hse.smart_pro.data.models.HardSkill;
import ru.hse.smart_pro.data.models.LanguageToStudent;
import ru.hse.smart_pro.data.models.SoftSkill;
import ru.hse.smart_pro.data.models.User;
import ru.hse.smart_pro.data.repositories.UserRepository;
import ru.hse.smart_pro.forms.CVForm;

import javax.validation.ValidationException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    HardSkillService hardSkillService;

    @Autowired
    SoftSkillService softSkillService;

    @Autowired
    LanguageService languageService;

    @Autowired
    LanguageLevelService languageLevelService;

    @Autowired
    LanguageToStudentService languageToStudentService;

    @Autowired
    PasswordEncoderConfig passwordEncoderConfig;


    public User saveNew(User user, boolean encode_password) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new ValidationException("Пользователем с таким логином уже существует.");
        }
        if (encode_password) {
            user.setPassword(passwordEncoderConfig.getPasswordEncoder().encode(user.getPassword()));
        }

        return userRepository.save(user);
    }

    public User saveNew(User user){
        return saveNew(user, true);
    }


    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;

    }


    public void saveCv(CVForm cvForm) {
        User user = loadUserByUsername(cvForm.getUsername());
        user.getLanguageToStudentList().stream().forEach(lst -> languageToStudentService.delete(lst));
        user.setLanguageToStudentList(new ArrayList<>());
        userRepository.save(user);


        Set<HardSkill> hardSkillsNames = new HashSet<>();
        cvForm.getHardSkills().stream().forEach(entry -> hardSkillsNames.add(hardSkillService.findByName(entry)));
        Set<SoftSkill> softSkillList = new HashSet<>();

        cvForm.getSoftSkills().stream().forEach(entry -> softSkillList.add(softSkillService.findByName(entry)));


        List<LanguageToStudent> languageToStudents = new ArrayList<>();

        cvForm.getLanguages().entrySet().stream().forEach(entry ->
                    languageToStudents.add(new LanguageToStudent(null, user, languageService.findByName(entry.getKey()), languageLevelService.findByName(entry.getValue()))));


        user.setHardSkills(hardSkillsNames);
        user.setSoftSkills(softSkillList);
        user.setLanguageToStudentList(languageToStudents);
        languageToStudents.forEach(lst -> languageToStudentService.save(lst));
        userRepository.save(user);
    }

    public CVForm getCV(String username) {
        User user = userRepository.findByUsername(username);
        CVForm cvForm = new CVForm();
        cvForm.setUsername(username);
        cvForm.setHardSkills(user.getHardSkills().stream().map(skill -> skill.name).collect(Collectors.toList()));
        cvForm.setSoftSkills(user.getSoftSkills().stream().map(skill -> skill.name).collect(Collectors.toList()));
        HashMap<String, String> languages = new HashMap<>();
        for (LanguageToStudent lts : user.getLanguageToStudentList()) {
            languages.put(lts.getLanguage().getName(), lts.getLanguageLevel().getName());
        }
        cvForm.setLanguages(languages);
        return cvForm;

    }

    public static final String FONT = "./src/main/resources/font.ttf";


    public ByteArrayOutputStream cvInPdf(String username) throws DocumentException, IOException {
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);

        document.open();
        BaseFont baseFont = BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font boldFont = new Font(baseFont, 12, Font.NORMAL);
        Font normalFont = new Font(baseFont, 10, Font.NORMAL);


        User user = userRepository.findByUsername(username);


        Paragraph name = new Paragraph(user.getFullName(), boldFont);
        document.add(name);

        Paragraph program = new Paragraph(user.getFullProgram(), normalFont);
        document.add(program);

        Paragraph GPA = new Paragraph("GPA: ".concat(String.valueOf(user.getGPA())), normalFont);
        document.add(GPA);

        CVForm cvForm = getCV(username);

        document.add(new Paragraph("Hard Skills:", boldFont));
        document.add(new Paragraph(String.join(", ", cvForm.getHardSkills()), normalFont));



        document.add(new Paragraph("Soft Skills:", boldFont));
        document.add(new Paragraph(String.join(", ", cvForm.getSoftSkills()), normalFont));

        document.add(new Paragraph("Languages:", boldFont));

        List<String> languages = cvForm.getLanguages().entrySet().stream().map(ent -> ent.getKey().concat(" - ").concat(ent.getValue())).collect(Collectors.toList());

        document.add(new Paragraph(String.join(", ", languages), normalFont));

        document.close();
        writer.close();
        return baos;
    }



}
