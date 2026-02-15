package com.test.springBoot.assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EmployeeRecord {
    private final String id;
    private final String firstName;
    private final String lastName;
    private final double salary;
    private final String managerId;

    private EmployeeRecord manager;
    private final List<EmployeeRecord> subordinates = new ArrayList<>();

    public EmployeeRecord(String id, String firstName, String lastName, double salary, String managerId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.managerId = managerId == null || managerId.trim().isEmpty() ? null : managerId.trim();
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public double getSalary() {
        return salary;
    }

    public String getManagerId() {
        return managerId;
    }

    public EmployeeRecord getManager() {
        return manager;
    }

    public void setManager(EmployeeRecord manager) {
        this.manager = manager;
    }

    public List<EmployeeRecord> getSubordinates() {
        return subordinates;
    }

    public void addSubordinate(EmployeeRecord e) {
        subordinates.add(e);
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeRecord that = (EmployeeRecord) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "EmployeeRecord{" +
                "id='" + id + '\'' +
                ", name='" + getFullName() + '\'' +
                ", salary=" + salary +
                ", managerId='" + managerId + '\'' +
                '}';
    }
}

