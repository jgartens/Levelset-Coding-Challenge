package com.levelset.challenge.Controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.levelset.challenge.Service.ProjectService;

import org.hibernate.boot.model.source.internal.hbm.CommaSeparatedStringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MainController {
    
    @Autowired
    private ProjectService projectService;

    @GetMapping("/")
    public String home(){

  
        return "home.html";
    }

    @PostMapping("/")
    public String submitCSV(@RequestParam("file") MultipartFile file, Model model){
       //Check if CSV is empty
        if (file.isEmpty()){
            model.addAttribute("errorMsg", "File is empty. Please upload a valid csv file");
            return "home.html";
        }

        //Attempt to parse file, using model for error handling
        model = projectService.parseCSV(file, model);

        //if there's an error, return home for reprompt CSV upload
        if (model.containsAttribute("errorMsg")){
            return "home.html";
        }
        //else, give the results page 
        return "result.html";
    }
}
