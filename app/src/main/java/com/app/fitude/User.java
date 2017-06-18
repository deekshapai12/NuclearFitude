package com.app.fitude;

/**
 * Created by Deeksha on 18-Jun-17.
 */

public class User {
    private String userId;
    private String userName;
    private String name;
    private int age;
    private String gender;
    private String bloodGroup;
    private Double height;
    private Double weight;
    private String isDiabetic;
    private int fitnessScore;
    private Double totalCaloriesBurnt;

    User(String userId,String userName,String name,int age,String gender,String bloodGroup,double height,double weight,String isDiabetic,int fitnessScore,double totalCaloriesBurnt){
        this.userId=userId;
        this.userName=userName;
        this.name=name;
        this.age=age;
        this.gender=gender;
        this.bloodGroup=bloodGroup;
        this.height=height;
        this.weight=weight;
        this.isDiabetic=isDiabetic;
        this.fitnessScore=fitnessScore;
        this.totalCaloriesBurnt=totalCaloriesBurnt;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public Double getHeight() {
        return height;
    }

    public Double getWeight() {
        return weight;
    }

    public String getIsDiabetic() {
        return isDiabetic;
    }

    public int getFitnessScore() {
        return fitnessScore;
    }

    public Double getTotalCaloriesBurnt() {
        return totalCaloriesBurnt;
    }

    @Override
    public String toString() {
        return String.format(userId+":"+name);
    }
}
