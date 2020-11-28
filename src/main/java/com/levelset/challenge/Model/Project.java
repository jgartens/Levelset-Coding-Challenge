package com.levelset.challenge.Model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "projects")
public class Project {
    
    @Id
    @Column(name="project_id")
    private Long id;
   
    private String customerName;

    private String name;
    
    private String address;
   
    private String city;
 
    @Length(max = 2)
    private String state;

    private int zip;
   
    private LocalDate startDate;

    private float oustandingDebt;

    private LocalDate commencementDate;

    private LocalDate notice;

    private LocalDate lien;
    

    public Project() {
    }

   
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public float getOustandingDebt() {
        return oustandingDebt;
    }

    public void setOustandingDebt(float oustandingDebt) {
        this.oustandingDebt = oustandingDebt;
    }

    public LocalDate getCommencementDate() {
        return commencementDate;
    }

    public void setCommencementDate(LocalDate commencementDate) {
        this.commencementDate = commencementDate;
    }
    

    public LocalDate getNotice() {
        return notice;
    }

    public void setNotice(LocalDate notice) {
        this.notice = notice;
    }

    public LocalDate getLien() {
        return lien;
    }

    public void setLien(LocalDate lien) {
        this.lien = lien;
    }


  
    @Override
    public String toString() {
        return "Project [address=" + address + ", city=" + city + ", commencementDate=" + commencementDate
                + ", customerName=" + customerName + ", id=" + id + ", name=" + name + ", oustandingDebt="
                + oustandingDebt + ", startDate=" + startDate + ", state=" + state + ", zip=" + zip + "]";
    }

    //Constructor for Projects with full info
    public Project(String customerName, String name, String address, String city, @Length(max = 2) String state,
            int zip, LocalDate startDate, float oustandingDebt, LocalDate commencementDate) {
        this.customerName = customerName;
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.startDate = startDate;
        this.oustandingDebt = oustandingDebt;
        this.commencementDate = commencementDate;
     }

  
}
