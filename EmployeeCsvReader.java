package com.test.springBoot.assignment;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class EmployeeCsvReader {

    public Map<String, EmployeeRecord> read(Path csvFile) throws IOException {
        Map<String, EmployeeRecord> map = new HashMap<>();
        try (BufferedReader br = Files.newBufferedReader(csvFile)) {
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) { // skip header if present
                    first = false;
                    if (line.trim().startsWith("Id") || line.toLowerCase().contains("firstname")) {
                        continue;
                    }
                }
                String[] parts = splitCsv(line);
                if (parts.length < 5) continue; // skip invalid
                String id = parts[0].trim();
                String firstName = parts[1].trim();
                String lastName = parts[2].trim();
                double salary = 0;
                try {
                    salary = Double.parseDouble(parts[3].trim());
                } catch (NumberFormatException e) {
                    continue; // skip invalid salary
                }
                String managerId = parts[4].trim();
                EmployeeRecord er = new EmployeeRecord(id, firstName, lastName, salary, managerId);
                map.put(id, er);
            }
        }
        // link managers
        for (EmployeeRecord e : map.values()) {
            String mId = e.getManagerId();
            if (mId != null && map.containsKey(mId)) {
                EmployeeRecord m = map.get(mId);
                e.setManager(m);
                m.addSubordinate(e);
            }
        }
        return map;
    }

    // very simple CSV splitter (no quoted fields support) - acceptable per assignment simplicity
    private String[] splitCsv(String line) {
        return line.split(",");
    }
}

