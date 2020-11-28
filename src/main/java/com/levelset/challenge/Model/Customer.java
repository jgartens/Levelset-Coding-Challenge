package com.levelset.challenge.Model;

public class Customer {

    private String name;

    private double totalDebt;

    private int totalOrders;

    private int totalProjects;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTotalDebt() {
        return totalDebt;
    }

    public void setTotalDebt(double totalDebt) {
        this.totalDebt = totalDebt;
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }

    public int getTotalProjects() {
        return totalProjects;
    }

    public void setTotalProjects(int totalProjects) {
        this.totalProjects = totalProjects;
    }

    public Customer() {
    }

    public Customer(String name, double totalDebt, int totalOrders, int totalProjects) {
        this.name = name;
        this.totalDebt = totalDebt;
        this.totalOrders = totalOrders;
        this.totalProjects = totalProjects;
    }

    @Override
    public String toString() {
        return "Customer [name=" + name + ", totalDebt=" + totalDebt + ", totalOrders=" + totalOrders
                + ", totalProjects=" + totalProjects + "]";
    }

    
    
}
