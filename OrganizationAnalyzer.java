package com.test.springBoot.assignment;

import java.util.*;
import java.util.stream.Collectors;

public class OrganizationAnalyzer {

    public static class SalaryIssue {
        public final EmployeeRecord manager;
        public final double expectedMin;
        public final double expectedMax;
        public final double actual;
        public final double diff;
        public final boolean tooLow;

        public SalaryIssue(EmployeeRecord manager, double expectedMin, double expectedMax, double actual, double diff, boolean tooLow) {
            this.manager = manager;
            this.expectedMin = expectedMin;
            this.expectedMax = expectedMax;
            this.actual = actual;
            this.diff = diff;
            this.tooLow = tooLow;
        }

        @Override
        public String toString() {
            String status = tooLow ? "UNDERPAID" : "OVERPAID";
            return String.format("%s (%s) %s: actual=%.2f expectedRange=[%.2f,%.2f] diff=%.2f",
                    manager.getFullName(), manager.getId(), status, actual, expectedMin, expectedMax, diff);
        }
    }

    public static class LongReporting {
        public final EmployeeRecord employee;
        public final int managersBetween;

        public LongReporting(EmployeeRecord employee, int managersBetween) {
            this.employee = employee;
            this.managersBetween = managersBetween;
        }

        @Override
        public String toString() {
            return String.format("%s (%s) managersBetween=%d", employee.getFullName(), employee.getId(), managersBetween);
        }
    }

    // compute salary issues for managers who have at least one direct subordinate
    public List<SalaryIssue> analyzeSalaries(Collection<EmployeeRecord> employees) {
        List<SalaryIssue> res = new ArrayList<>();
        for (EmployeeRecord m : employees) {
            List<EmployeeRecord> subs = m.getSubordinates();
            if (subs.isEmpty()) continue;
            double avg = subs.stream().mapToDouble(EmployeeRecord::getSalary).average().orElse(0);
            double expectedMin = avg * 1.20; // at least 20% more
            double expectedMax = avg * 1.50; // at most 50% more
            double actual = m.getSalary();
            if (actual < expectedMin) {
                res.add(new SalaryIssue(m, expectedMin, expectedMax, actual, expectedMin - actual, true));
            } else if (actual > expectedMax) {
                res.add(new SalaryIssue(m, expectedMin, expectedMax, actual, actual - expectedMax, false));
            }
        }
        // sort by magnitude of diff desc
        return res.stream().sorted(Comparator.comparingDouble((SalaryIssue s) -> Math.abs(s.diff)).reversed()).collect(Collectors.toList());
    }

    // compute employees with more than limit managers between them and CEO.
    public List<LongReporting> analyzeReportingLength(Collection<EmployeeRecord> employees, int limit) {
        List<LongReporting> res = new ArrayList<>();
        // find CEO(s): those without manager
        List<EmployeeRecord> ceos = employees.stream().filter(e -> e.getManager() == null).collect(Collectors.toList());
        for (EmployeeRecord e : employees) {
            int count = countManagersToCeo(e);
            if (count > limit) {
                res.add(new LongReporting(e, count));
            }
        }
        // sort by managersBetween desc
        res.sort(Comparator.comparingInt((LongReporting lr) -> lr.managersBetween).reversed());
        return res;
    }

    private int countManagersToCeo(EmployeeRecord e) {
        int count = 0;
        EmployeeRecord cur = e.getManager();
        while (cur != null) {
            count++;
            cur = cur.getManager();
        }
        return count;
    }
}

