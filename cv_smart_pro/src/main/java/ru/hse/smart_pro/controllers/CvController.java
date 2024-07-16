package ru.hse.smart_pro.controllers;

import com.itextpdf.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ru.hse.smart_pro.cv_storage.StorageFileNotFoundException;
import ru.hse.smart_pro.cv_storage.StorageService;
import ru.hse.smart_pro.data.service.UserService;
import ru.hse.smart_pro.forms.CVForm;

import java.io.*;

@RestController
@RequestMapping("/cv")
@SessionAttributes("cvForm")
public class CvController {

    @Autowired
    StorageService storageService;


    @Autowired
    UserService userService;

    @Value("${upload.path}")
    private String uploadPath;


    @GetMapping("/cv")
    public CVForm newTaskView(@RequestParam String username) {

        return userService.getCV(username);


    }


    @PostMapping("/cv")
    public String newTask(@RequestBody CVForm form)  {
        userService.saveCv(form);
        return "/";
    }

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> generatePDF(@RequestParam String username, HttpServletResponse response) throws DocumentException, IOException {
        try {

            byte[] bytes = storageService.loadAsResource(userService.loadUserByUsername(username).getId().toString() + ".pdf").getContentAsByteArray();
            ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length);
            baos.write(bytes, 0, bytes.length);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "generated-pdf.pdf");
            return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);


        } catch (StorageFileNotFoundException e) {

            try {
                ByteArrayOutputStream baos = userService.cvInPdf(username);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentDispositionFormData("filename", "generated-pdf.pdf");

                return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);
            } catch (DocumentException e1) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            } catch (IOException e1) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }
    }


    @PostMapping("/uploadPdf")
    public HttpStatus uploadPdf(@RequestParam("pdfFile") MultipartFile pdfFile, @RequestParam("username") String username ) {

        storageService.store(pdfFile, userService.loadUserByUsername(username).getId().toString() + ".pdf");
        return HttpStatus.OK;
    }


    @GetMapping("/deletePdf")
    public HttpStatus uploadPdf(@RequestParam("username") String username ) {

        storageService.delete(userService.loadUserByUsername(username).getId().toString() + ".pdf");
        return HttpStatus.OK;
    }
}
