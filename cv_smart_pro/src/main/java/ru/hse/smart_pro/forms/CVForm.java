package ru.hse.smart_pro.forms;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class CVForm  {
    List<String> hardSkills;
    List<String> softSkills;
    HashMap<String, String> languages;
    String username;

}
