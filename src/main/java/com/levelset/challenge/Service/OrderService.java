package com.levelset.challenge.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;

import com.levelset.challenge.Model.Project;
import com.levelset.challenge.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    


    @Autowired
    ProjectRepository projectRepository;

    public void generateOrders(Project project) {
        LocalDate noticeDate = LocalDate.now();

        //notice date if in Texas
        if (project.getState() == "TX"){
          
            //if 15th has passed
            if (project.getCommencementDate().getDayOfMonth() > 15){
                noticeDate = project.getCommencementDate().plusMonths(1).withDayOfMonth(15);
            }
            else{
                noticeDate = project.getCommencementDate().withDayOfMonth(15);
            }
            noticeDate = checkForConflict(noticeDate);
        }
        //notice date not in Texas
        else{
            //60 days after last day of Project Commencement Day Month
            noticeDate = project.getCommencementDate().withDayOfMonth(
                project.getCommencementDate().lengthOfMonth()).plusDays(60);
          
            noticeDate = checkForConflict(noticeDate);
        }

        //90 days after last day Project Start Date's MOnth
        LocalDate lienDate = project.getStartDate().withDayOfMonth(
                project.getStartDate().lengthOfMonth()).plusDays(90);
            
        lienDate = checkForConflict(lienDate);

        //add to project if not in the past
        if (!noticeDate.isBefore(LocalDate.now())){
            project.setNotice(noticeDate);
        }
        if (!lienDate.isBefore(LocalDate.now())){
            project.setLien(lienDate);
        }
    }

    private LocalDate checkForConflict(LocalDate orderDate) {
        Boolean valid = false;

        //run until date isn't weekend or holiday
        while (valid = false){
            //if date is a saturday
            if (orderDate.getDayOfWeek() == DayOfWeek.SATURDAY){
                orderDate = orderDate.minusDays(1);
            }
            //if date is a sunday
            else if(orderDate.getDayOfWeek() == DayOfWeek.SUNDAY){
                orderDate = orderDate.minusDays(2);
            }
            //if date is (one of provided) holidays
            else if(orderDate.isEqual(LocalDate.parse("2020-12-31")) ||
            orderDate.isEqual(LocalDate.parse("2020-06-02"))
            || orderDate.isEqual(LocalDate.parse("2020-11-24"))){
                orderDate = orderDate.minusDays(1);
            }
            else{
                valid = true;
            }
        }
    return orderDate;
    }

    
}
