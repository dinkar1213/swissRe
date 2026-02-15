package com.test.springBoot.assignment;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Usage: java -jar assignment.jar <employees.csv>");
            System.exit(1);
        }
        Path csv = Paths.get(args[0]);
        EmployeeCsvReader reader = new EmployeeCsvReader();
        Map<String, EmployeeRecord> map = reader.read(csv);
        OrganizationAnalyzer analyzer = new OrganizationAnalyzer();
        List<OrganizationAnalyzer.SalaryIssue> issues = analyzer.analyzeSalaries(map.values());
        System.out.println("\nSalary issues:");
        if (issues.isEmpty()) {
            System.out.println("  No managers with salary outside 20%-50% range compared to direct subordinates average.");
        } else {
            for (OrganizationAnalyzer.SalaryIssue si : issues) {
                System.out.println("  " + si.toString());
            }
        }

        System.out.println("\nEmployees with too long reporting line (>4 managers between them and CEO):");
        List<OrganizationAnalyzer.LongReporting> longLines = analyzer.analyzeReportingLength(map.values(), 4);
        if (longLines.isEmpty()) {
            System.out.println("  None");
        } else {
            for (OrganizationAnalyzer.LongReporting lr : longLines) {
                System.out.println("  " + lr.toString());
            }
        }
    }
}

