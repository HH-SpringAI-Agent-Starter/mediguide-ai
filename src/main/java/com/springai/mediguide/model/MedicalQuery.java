package com.springai.mediguide.model;

import java.util.List;

public class MedicalQuery {
    private String symptom;
    private Integer age;
    private String gender;
    private String duration;
    private List<String> preExistingConditions;
    private List<String> currentMedications;
    private String query;
    private String category; // SYMPTOM, MEDICATION, HEALTH_SUMMARY, CHRONIC_CARE

    // Getters and Setters
    public String getSymptom() { return symptom; }
    public void setSymptom(String symptom) { this.symptom = symptom; }
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }
    public List<String> getPreExistingConditions() { return preExistingConditions; }
    public void setPreExistingConditions(List<String> preExistingConditions) { this.preExistingConditions = preExistingConditions; }
    public List<String> getCurrentMedications() { return currentMedications; }
    public void setCurrentMedications(List<String> currentMedications) { this.currentMedications = currentMedications; }
    public String getQuery() { return query; }
    public void setQuery(String query) { this.query = query; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}
