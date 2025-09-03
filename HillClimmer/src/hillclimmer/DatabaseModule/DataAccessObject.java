/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hillclimmer.DatabaseModule;

import java.io.*;
import java.util.*;

/**
 * Base DataAccessObject class for handling data storage using CSV files.
 * Follows DAO principles for CRUD operations.
 * Subclasses should implement specific object handling.
 *
 * @author las
 */
public abstract class DataAccessObject<T> {
    protected String filePath;

    public DataAccessObject(String filePath) {
        this.filePath = filePath;
        // Ensure the file exists
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Error creating file: " + e.getMessage());
        }
    }

    // Abstract methods to be implemented by subclasses
    protected abstract String objectToCSV(T object);
    protected abstract T csvToObject(String csvLine);
    protected abstract String getId(T object);

    // Save object to file
    public void save(T object) {
        List<T> allObjects = loadAll();
        boolean exists = false;
        for (int i = 0; i < allObjects.size(); i++) {
            if (getId(allObjects.get(i)).equals(getId(object))) {
                allObjects.set(i, object);
                exists = true;
                break;
            }
        }
        if (!exists) {
            allObjects.add(object);
        }
        writeAllToFile(allObjects);
    }

    // Load object by ID
    public T load(String id) {
        List<T> allObjects = loadAll();
        for (T obj : allObjects) {
            if (getId(obj).equals(id)) {
                return obj;
            }
        }
        return null;
    }

    // Update object (same as save)
    public void update(T object) {
        save(object);
    }

    // Delete object by ID
    public void delete(String id) {
        List<T> allObjects = loadAll();
        allObjects.removeIf(obj -> getId(obj).equals(id));
        writeAllToFile(allObjects);
    }

    // Load all objects
    public List<T> loadAll() {
        List<T> objects = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    T obj = csvToObject(line);
                    if (obj != null) {
                        objects.add(obj);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return objects;
    }

    // Helper method to write all objects to file
    private void writeAllToFile(List<T> objects) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (T obj : objects) {
                bw.write(objectToCSV(obj));
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
