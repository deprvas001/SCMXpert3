package com.example.scmxpert.model.filter;

import java.util.List;

public class FilterGetResponse {
    List<String>  device_Id;

    public List<String> getDevice_Id() {

        return device_Id;
    }

    public void setDevice_Id(List<String> device_Id) {
        this.device_Id = device_Id;
    }

    public List<String> getDepartments() {
        return departments;
    }

    public void setDepartments(List<String> departments) {
        this.departments = departments;
    }

    List<String> departments;


}
