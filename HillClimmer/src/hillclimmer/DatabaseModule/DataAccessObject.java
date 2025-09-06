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
    protected final Object fileLock = new Object(); // Synchronization lock for file operations

    public DataAccessObject(String filePath) {
        this.filePath = filePath;
        // Ensure the file exists
        synchronized (fileLock) {
            try {
                File file = new File(filePath);
                if (!file.exists()) {
                    file.createNewFile();
                }
            } catch (IOException e) {
                System.err.println("Error creating file: " + e.getMessage());
            }
        }
    }

    // Abstract methods to be implemented by subclasses
    protected abstract String objectToCSV(T object);
    protected abstract T csvToObject(String csvLine);
    protected abstract String getId(T object);

    // Save object to file
    public void save(T object) {
        synchronized (fileLock) {
            List<T> allObjects = loadAll();
            boolean exists = false;
            
            // Check if object already exists
            for (int i = 0; i < allObjects.size(); i++) {
                if (getId(allObjects.get(i)).equals(getId(object))) {
                    allObjects.set(i, object);
                    exists = true;
                    break;
                }
            }
            
            if (!exists) {
                // Generate new ID if the object doesn't exist and ID is default (0 or empty)
                String currentId = getId(object);
                if (currentId.equals("0") || currentId.isEmpty()) {
                    object = generateNewId(object, allObjects);
                }
                allObjects.add(object);
            }
            
            // Always rewrite the entire file to ensure data integrity
            writeAllToFile(allObjects);
        }
    }

    // Generate new unique ID for the object
    protected T generateNewId(T object, List<T> existingObjects) {
        // This method should be overridden by subclasses that need ID generation
        // Default implementation returns the object as-is
        return object;
    }

    // Load object by ID
    public T load(String id) {
        synchronized (fileLock) {
            List<T> allObjects = loadAll();
            for (T obj : allObjects) {
                if (getId(obj).equals(id)) {
                    return obj;
                }
            }
            return null;
        }
    }

    // Update object (same as save)
    public void update(T object) {
        synchronized (fileLock) {
            save(object);
        }
    }

    // Delete object by ID
    public void delete(String id) {
        synchronized (fileLock) {
            List<T> allObjects = loadAll();
            allObjects.removeIf(obj -> getId(obj).equals(id));
            writeAllToFile(allObjects);
        }
    }

    // Load all objects
    public List<T> loadAll() {
        synchronized (fileLock) {
            List<T> objects = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        try {
                            T obj = csvToObject(line);
                            if (obj != null) {
                                objects.add(obj);
                            }
                        } catch (Exception e) {
                            // Skip corrupted lines silently
                            System.err.println("Warning: Skipping corrupted line in " + filePath);
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
            }
            return objects;
        }
    }

    // Helper method to write all objects to file
    private void writeAllToFile(List<T> objects) {
        synchronized (fileLock) {
            try {
                // Sort objects by ID before writing
                objects.sort((a, b) -> getId(a).compareTo(getId(b)));
                
                java.io.FileWriter fw = new java.io.FileWriter(filePath, false);
                try (java.io.BufferedWriter bw = new java.io.BufferedWriter(fw)) {
                    for (T obj : objects) {
                        String csvLine = objectToCSV(obj);
                        bw.write(csvLine);
                        bw.newLine();
                    }
                    bw.flush();
                }
            } catch (IOException e) {
                System.err.println("Error writing to file: " + e.getMessage());
            }
        }
    }
}
