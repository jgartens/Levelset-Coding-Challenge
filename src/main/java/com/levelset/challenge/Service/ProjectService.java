package com.levelset.challenge.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.levelset.challenge.Model.Project;
import com.levelset.challenge.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private OrderService orderService;

    public List<Long> parseCSV(MultipartFile file, Model model) {

        // Attempt to parse CSV into a list of Strings
        List<List<String>> projects = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            model.addAttribute("text", file.getContentType());
            String line;
            while ((line = br.readLine()) != null) {
                // Split comma deliniated lines into an array
                String[] values = line.split(",");
                projects.add(new ArrayList<String>(Arrays.asList(values)));
            }
          

        } catch (Exception ex) {
            model.addAttribute("errorMsg", "Error processing csv file. Please try again.");
   
            return null;
        }

        // check to see if required columns are in CSV
        List<String> columns = projects.get(0);
        List<Long> currentProjects = new ArrayList<Long>();

        if (columns.containsAll(Arrays.asList("Customer Name", "Project Name", "Project Address", "Project City",
                "Project State", "Project Zip", "Project Start Date"))) {
            for (int i = 1; i < projects.size(); i++) {
                
                Long project_id = createProjectFromRow(projects.get(i), model);
                if (project_id != null){
                    currentProjects.add(project_id);
                }
                
            }
        } else {
            model.addAttribute("errorMsg", "Missing one or more required columns");
            return null;
        }

        return currentProjects;
    }

    private Long createProjectFromRow(List<String> row, Model model) {
     
        //check if row is valid length and contains all necasary valid information
        if (checkForValid(row) == false){
        
            model.addAttribute("rowError", "One or more rows contains invalid information");
            return null;
        }
        //handle the non-required values: add or change if missing or invalid
        row = handleNonReqValues(row);

        //create a new project from valid row
        Project project = new Project(row.get(0), row.get(1), row.get(2), row.get(3), row.get(4).toUpperCase(),
        Integer.parseInt(row.get(5)), getDate(row.get(6)), Float.parseFloat(row.get(7)), getDate(row.get(8)));

        //set ID based on unique address, city, state, zip
        project.setId(generateId(project));

        
        //generate lien and notice
        orderService.generateOrders(project);

        //save new project if no matching ID, or edit ID for matches
        saveProject(project);

        //return model for potential errors
        return project.getId();
    }
    

    // method to check that all required paramters are present and valid
    private boolean checkForValid(List<String> row) {
        //if entry is invalid length
        if (row.size() < 7 || row.size() > 9){
            return false;
        }
        //if required string entries are null
        for (int i = 0; i < 4; i++){
            if (row.get(i) == null || row.get(i).isEmpty()){
                return false;
            }
        }
        //if state isn't 2 letters
        if (!row.get(4).matches("[a-zA-Z][a-zA-Z]") ){
            return false;
        }
        //if zip isn't 5 numbers
        if (!row.get(5).matches("[0-9]+")  || row.get(5).length() !=5){
            return false;
        }

        //if start date isn't date format
        if (!row.get(6).matches("[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]")
		&& !row.get(6).matches("[0-9][0-9][0-9][0-9]-[0-9]-[0-9][0-9]")
		&& !row.get(6).matches("[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9]")
		&& !row.get(6).matches("[0-9][0-9][0-9][0-9]-[0-9]-[0-9]")){
            return false;
        }
        //project is valid, return true
        return true;
    }

    //method to handle non required values 
    private List<String> handleNonReqValues(List<String> row){
       
        //if no non-required data is included
        if (row.size() == 7){
            row.add("0");
            row.add(LocalDate.now().toString());
        }
        //if some non-required data is included
        else if (row.size() == 8){
            //if valid amount is included
            if (row.get(7).matches("[0-9]+.[0-9]+") || row.get(7).matches("[0-9]+")
             || row.get(7).matches("[0-9]+.") || row.get(7).matches(".[0-9]+")){
                row.add(LocalDate.now().toString());
             }
             //if valid date is included
             else if (row.get(7).matches("[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]")
             || row.get(7).matches("[0-9][0-9][0-9][0-9]-[0-9]-[0-9][0-9]")
             || row.get(7).matches("[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9]")
             || row.get(7).matches("[0-9][0-9][0-9][0-9]-[0-9]-[0-9]")){
                 row.add(7, "0");
             }
             //if entry is not valid 
             else{
                 row.set(7, "0");
                 row.add(LocalDate.now().toString());
             }
        }
        //if required data is included
        else {
            //if amount is invalid
            if (!row.get(7).matches("[0-9]+.[0-9]+") && !row.get(7).matches("[0-9]+")
             && !row.get(7).matches("[0-9]+.") && !row.get(7).matches(".[0-9]+")){
                row.set(7, "0");
             }
             //if date is invalid
             if (!row.get(8).matches("[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]")
             && !row.get(8).matches("[0-9][0-9][0-9][0-9]-[0-9]-[0-9][0-9]")
             && !row.get(8).matches("[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9]")
             && !row.get(8).matches("[0-9][0-9][0-9][0-9]-[0-9]-[0-9]")){
                row.set(8, LocalDate.now().toString());
             } 
            
        }
        //row is now filled with correct data
        return row;
    }
    

    // method to generate IDs based on the ASCII value of the full project address
    private Long generateId(Project project) {
        
        int id = 0;
        String full_address = project.getAddress() + project.getCity() + project.getState();
        full_address = full_address.toLowerCase();
        for (int i = 0; i < full_address.length(); i++){
            id += full_address.charAt(i);
        }

        id += project.getZip();
        return Long.valueOf(id);
    }


    //method to save project to SQL database via crudrepository 
    private void saveProject(Project project){
        //check to see if project with same address already exists
        if (projectRepository.existsById(project.getId())) {
            //if yes, edit old project with new info
            Project old_project = projectRepository.findById(project.getId()).orElse(null);
            old_project.setCustomerName(project.getCustomerName());
            old_project.setName(project.getName());
            old_project.setStartDate(project.getStartDate());
            old_project.setCommencementDate(project.getCommencementDate());
            old_project.setOustandingDebt(project.getOustandingDebt());
            old_project.setNotice(project.getNotice());
            old_project.setLien(project.getLien());
            projectRepository.save(old_project);
        }
        else{
            //otherwise, save the new project
            projectRepository.save(project);
        } 
    }

    //method to generate type LocalDate for a given date in valid string format 
    private LocalDate getDate(String dateString){
  
        //adding leading zeros if necasary. IE: 2020-2-1 --> 2020-02-01
        while (dateString.length() < 10){
            if (dateString.charAt(6) == '-'){
                dateString = dateString.substring(0, 5) + '0' + dateString.substring(5);
            }
            else{
                dateString = dateString.substring(0, 8) + '0' + dateString.substring(8);
            }
        }
        return LocalDate.parse(dateString);
    }
}
