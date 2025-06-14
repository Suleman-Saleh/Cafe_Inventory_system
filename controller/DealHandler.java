package com.progmeleon.mycafe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.progmeleon.mycafe.config.ConfigureExistingData;
import com.progmeleon.mycafe.model.Deal;
import com.progmeleon.mycafe.model.Item;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static com.progmeleon.mycafe.config.ConfigureApp.DEALS_FILE_PATH;
import static com.progmeleon.mycafe.controller.FileHandler.saveDataToFile;

public class DealHandler {


    private static List<Deal> deals = new ArrayList<>();


    static {
        deals = ConfigureExistingData.deals;
    }

    public static void saveDealsToFile() {
        saveDataToFile(deals, DEALS_FILE_PATH);
    }

    public static void manageDeals() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n====== Deal Management ======");
            System.out.println("1. Add Deal");
            System.out.println("2. Update Deal");
            System.out.println("3. Delete Deal");
            System.out.println("4. Display Deals");
            System.out.println("5. Back to Admin Menu");
            System.out.print("Enter your choice: ");
            try {
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        makeDeal();
                        break;
                    case 2:
                        updateDeal();
                        break;
                    case 3:
                        deleteDeal();
                        break;
                    case 4:
                        displayDeals();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume the newline character
            }

        } while (true);
    }

    public static void makeDeal() {
        Scanner scanner = new Scanner(System.in);

        // Get deal details from the user
        System.out.print("Enter deal name: ");
        String dealName = scanner.nextLine();
        System.out.print("Enter deal price: ");
        double dealPrice = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character
        System.out.print("Enter deal description: ");
        String dealDescription = scanner.nextLine();

        // Display existing items for user selection
//        System.out.println("Existing items:");
//        displayItems(ConfigureExistingData.items);

        // Get items for the deal
        ArrayList<Item> dealItems = new ArrayList<>();
        System.out.print("Enter the number of items in the deal: ");
        int itemCount = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        for (int i = 0; i < itemCount; i++) {
            System.out.print("Enter item name for deal: ");
            String itemName = scanner.nextLine();

            // Find the existing item by name
            Item existingItem = findItemByName(itemName, ConfigureExistingData.items);

            if (existingItem != null) {
                dealItems.add(existingItem);
            } else {
                System.out.println("Item not found. Please enter a valid item name.");
                i--; // Decrement i to re-enter the item name
            }
        }

        // Create a new deal and add it to the mutable copy of the deals list
        Deal newDeal = new Deal(dealName, dealItems, dealPrice, dealDescription);
        List<Deal> mutableDeals = new ArrayList<>(deals);
        mutableDeals.add(newDeal);

        // Update the original deals list with the new mutable list
        deals = List.copyOf(mutableDeals);

        saveDealsToFile();
        System.out.println("Deal added successfully.");
    }


    private static Item findItemByName(String itemName, List<Item> itemList) {
        for (Item item : itemList) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    private static void displayItems(List<Item> itemList) {
        for (Item item : itemList) {
            System.out.println(item.getName());
        }
    }



    public static void updateDeal() {
        Scanner scanner = new Scanner(System.in);

        // Get the deal ID to update
        System.out.print("Enter the ID of the deal to update: ");
        int dealId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        // Find the deal by ID
        int index = findDealIndexById(dealId);
        if (index != -1) {
            // Get updated details from the user
            System.out.print("Enter updated deal name: ");
            String updatedDealName = scanner.nextLine();
            System.out.print("Enter updated deal price: ");
            double updatedDealPrice = scanner.nextDouble();
            scanner.nextLine(); // Consume the newline character
            System.out.print("Enter updated deal description: ");
            String updatedDealDescription = scanner.nextLine();

            // Display existing items for user selection
            System.out.println("Existing items:");
            displayItems(ConfigureExistingData.items);

            // Get the current items in the deal
            List<Item> currentDealItems = deals.get(index).getDealItems();

            // Display current items in the deal
            System.out.println("Current items in the deal:");
            displayItems(currentDealItems);

            // Modify items in the deal
            System.out.println("1. Add item to the deal");
            System.out.println("2. Remove item from the deal");
            System.out.println("3. Continue without modifying items");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    // Add new items to the deal
                    System.out.print("Enter the number of items to add to the deal: ");
                    int addItemCount = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character

                    for (int i = 0; i < addItemCount; i++) {
                        System.out.print("Enter item name to add to the deal: ");
                        String itemName = scanner.nextLine();

                        // Find the existing item by name
                        Item existingItem = findItemByName(itemName, ConfigureExistingData.items);

                        if (existingItem != null) {
                            currentDealItems.add(existingItem);
                        } else {
                            System.out.println("Item not found. Please enter a valid item name.");
                            i--; // Decrement i to re-enter the item name
                        }
                    }
                    break;

                case 2:
                    // Remove items from the deal
                    System.out.print("Enter the number of items to remove from the deal: ");
                    int removeItemCount = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character

                    for (int i = 0; i < removeItemCount; i++) {
                        System.out.print("Enter item name to remove from the deal: ");
                        String itemName = scanner.nextLine();

                        // Find the item by name in the current items of the deal
                        Item itemToRemove = findItemByName(itemName, currentDealItems);

                        if (itemToRemove != null) {
                            currentDealItems.remove(itemToRemove);
                        } else {
                            System.out.println("Item not found in the deal. Please enter a valid item name.");
                            i--; // Decrement i to re-enter the item name
                        }
                    }
                    break;

                case 3:
                    // Continue without modifying items
                    break;

                default:
                    System.out.println("Invalid choice. Continuing without modifying items.");
            }

            // Update the deal with the modified items
            Deal updatedDeal = new Deal(updatedDealName, currentDealItems, updatedDealPrice, updatedDealDescription);
            deals.set(index, updatedDeal);
            saveDealsToFile();
            System.out.println("Deal updated successfully.");
        } else {
            System.out.println("Deal not found.");
        }
    }


    public static void deleteDeal() {
        Scanner scanner = new Scanner(System.in);

        // Get the deal ID to delete
        System.out.print("Enter the ID of the deal to delete: ");
        int dealId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        // Find the deal by ID
        int index = findDealIndexById(dealId);
        if (index != -1) {
            // Delete the deal
            deals.remove(index);
            saveDealsToFile();
            System.out.println("Deal deleted successfully.");
        } else {
            System.out.println("Deal not found.");
        }
    }

    public static void displayDeals() {
        if (deals.isEmpty()) {
            System.out.println("No deals available.");
        } else {
            System.out.println("\n====== Deals ======");
            for (Deal deal : deals) {
                System.out.println("Deal ID: " + deal.getId());
                System.out.println("Deal Name: " + deal.getDealName());
                System.out.println("Deal Price: " + deal.getDealPrice());
                System.out.println("Deal Description: " + deal.getDealDescription());
                System.out.println("Deal Items:");

                List<Item> dealItems = deal.getDealItems();
                for (Item item : dealItems) {
                    System.out.println("- Item Name: " + item.getName());
                    // Add other item details as needed
                }

                System.out.println("==========");
            }
        }
    }



    private static int findDealIndexById(int dealId) {
        for (int i = 0; i < deals.size(); i++) {
            if (deals.get(i).getId() == dealId) {
                return i;
            }
        }
        return -1;
    }
}