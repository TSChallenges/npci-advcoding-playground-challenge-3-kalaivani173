package com.javatechie;

import java.io.*;
import java.util.*;

public class InventoryManager {
    private static final String FILE_NAME = "inventory.txt";
    private static Map<String, Integer> inventory = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadInventory();
        int choice;

        do {
            System.out.println("\nInventory Management System");
            System.out.println("1. View Inventory");
            System.out.println("2. Add New Item");
            System.out.println("3. Update Existing Item");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            while (!scanner.hasNextInt()) {
                System.out.print("Please enter a valid number: ");
                scanner.next(); // Consume invalid input
            }
            choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    readInventory();
                    break;
                case 2:
                    addItemPrompt();
                    break;
                case 3:
                    updateItemPrompt();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);
    }

    private static void addItemPrompt() {
        System.out.print("Enter item name: ");
        String newItem = scanner.nextLine();
        System.out.print("Enter item count: ");

        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid number for item count: ");
            scanner.next(); // Consume invalid input
        }
        int newCount = scanner.nextInt();
        addItem(newItem, newCount);
    }

    private static void updateItemPrompt() {
        System.out.print("Enter item name: ");
        String existingItem = scanner.nextLine();
        System.out.print("Enter new item count: ");

        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid number for item count: ");
            scanner.next(); // Consume invalid input
        }
        int updatedCount = scanner.nextInt();
        updateItem(existingItem, updatedCount);
    }

    private static void loadInventory() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String itemName = parts[0].trim();
                    int itemCount = Integer.parseInt(parts[1].trim());
                    inventory.put(itemName, itemCount);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Inventory file not found. A new file will be created.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readInventory() {
        System.out.println("Current Inventory:");
        if (inventory.isEmpty()) {
            System.out.println("No items in inventory.");
        } else {
            for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }
    }

    private static void addItem(String itemName, int itemCount) {
        if (inventory.containsKey(itemName)) {
            System.out.println("Item already exists. Use update method to change the count.");
        } else {
            inventory.put(itemName, itemCount);
            saveInventory();
            System.out.println("Item added: " + itemName + " with count " + itemCount);
        }
    }

    private static void updateItem(String itemName, int itemCount) {
        if (inventory.containsKey(itemName)) {
            inventory.put(itemName, itemCount);
            saveInventory();
            System.out.println("Item updated: " + itemName + " to count " + itemCount);
        } else {
            System.out.println("Item does not exist. Use add method to add it.");
        }
    }

    private static void saveInventory() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
                bw.write(entry.getKey() + "," + entry.getValue());
                bw.newLine();
            }
            System.out.println("Inventory saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
