package com.example.test3pizza;



public class pizza {
    private int id;
    private String customerName;
    private String phoneNum;
    private String pizzaSize;
    private int numberOfToppings;
    private double totalBill;

    public pizza(String customerName, String phoneNum, String pizzaSize, int numberOfToppings, double totalBill) {
        this.customerName = customerName;
        this.phoneNum = phoneNum;
        this.pizzaSize = pizzaSize;
        this.numberOfToppings = numberOfToppings;
        this.totalBill = totalBill;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPizzaSize() {
        return pizzaSize;
    }

    public void setPizzaSize(String pizzaSize) {
        this.pizzaSize = pizzaSize;
    }

    public int getNumberOfToppings() {
        return numberOfToppings;
    }

    public void setNumberOfToppings(int numberOfToppings) {
        this.numberOfToppings = numberOfToppings;
    }

    public double getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(double totalBill) {
        this.totalBill = totalBill;
    }
}
