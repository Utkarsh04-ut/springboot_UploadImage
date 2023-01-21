package com.example.Image_Upload.controller;

import com.example.Image_Upload.model.Images;
import com.example.Image_Upload.repository.uploadRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private uploadRepository uploadRepo;
    @GetMapping("/")
    public String index(Model m){
        List<Images> list= uploadRepo.findAll();
        m.addAttribute("list", list);
        return "index";
    }

    @PostMapping("/imageUpload")
    public String imageUpload(@RequestParam MultipartFile img , RedirectAttributes attributes) {
        Images im = new Images();
        im.setImageName(img.getOriginalFilename());

        Images uploadImg = uploadRepo.save(im);


        String fileName = null;
        if (uploadImg != null) {
            try {
                File saveFile = new ClassPathResource("static/img").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + img.getOriginalFilename());
                System.out.println(path);
                Files.copy(img.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                fileName = StringUtils.cleanPath(img.getOriginalFilename());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        attributes.addFlashAttribute("msg", "Image upload successfully" + fileName);
        return "redirect:/";
    }
}
