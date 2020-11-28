package com.levelset.challenge.Controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.levelset.challenge.Model.Customer;
import com.levelset.challenge.Model.Project;
import com.levelset.challenge.Repository.ProjectRepository;
import com.levelset.challenge.Service.ProjectService;

import org.hibernate.boot.model.source.internal.hbm.CommaSeparatedStringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MainController {
    
    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectRepository projectRepository;


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
        List<Long> currentProject_ids = projectService.parseCSV(file, model);

        //if there's an error, return home for reprompt CSV upload
        if (model.containsAttribute("errorMsg")){
            return "home.html";
        }
        List<Project> currentProjects = projectRepository.findByIdIn(currentProject_ids);

        model.addAttribute("projects", currentProjects);
        
        return "result.html";
    }

    @GetMapping("/report/saved")
    public String getSavedReports(Model model){
        List<Project> allProjects = projectRepository.findAll();

        model.addAttribute("projects", allProjects);
        return "projects.html";
    }

    @GetMapping("/customer/{customer_id}")
    public String getCustomerReport(@PathVariable(value = "customer_id") String customer_name, Model model){
        List<Project> customer_projects = projectRepository.findByCustomerName(customer_name);
        System.out.println(customer_projects);
        System.out.println(customer_name);
        if (!customer_projects.isEmpty()){
            double totalDebt = 0;
            int totalOrders = 0;
            int totalProjects = 0;
    
            for (Project project : customer_projects){
                totalDebt += project.getOustandingDebt();
                totalProjects += 1;
                if (project.getNotice() != null){
                    totalOrders+=1;
                }
                if (project.getLien() != null){
                    totalOrders+=1;
                }
            }

            Customer customer = new Customer(customer_name, totalDebt, totalOrders, totalProjects);
            model.addAttribute("customer", customer);
            return "customer.html";
        }

        else{

            model.addAttribute("errorMsg", "Customer was not found, please try again");
            return "projects.html";
        }  
    }


    @PostMapping("/customer/{customer_id}")
    public String getCustomerReportFromName(@RequestParam("customer") String customer_name, Model model){
        List<Project> customer_projects = projectRepository.findByCustomerName(customer_name);
        System.out.println(customer_projects);
        System.out.println("HERE THO");
        if (!customer_projects.isEmpty()){
            double totalDebt = 0;
            int totalOrders = 0;
            int totalProjects = 0;
    
            for (Project project : customer_projects){
                totalDebt += project.getOustandingDebt();
                totalProjects += 1;
                if (project.getNotice() != null){
                    totalOrders+=1;
                }
                if (project.getLien() != null){
                    totalOrders+=1;
                }
            }

            Customer customer = new Customer(customer_name, totalDebt, totalOrders, totalProjects);
            model.addAttribute("customer", customer);
            return "customer.html";
        }

        else{

            model.addAttribute("errorMsg", "Customer was not found, please try again");
            return "projects.html";
        }  
    }
}
