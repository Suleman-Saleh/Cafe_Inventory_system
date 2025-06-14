package InterFace;
import com.progmeleon.mycafe.controller.FileHandler;
import com.progmeleon.mycafe.controller.InventorySystem;
import com.progmeleon.mycafe.model.Category;
import com.progmeleon.mycafe.model.Item;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.progmeleon.mycafe.model.User;
import com.progmeleon.mycafe.model.UserRole;
import com.progmeleon.mycafe.controller.CategoryController;
import com.progmeleon.mycafe.controller.ItemController;

import static com.progmeleon.mycafe.controller.ItemController.*;

public class HelloApplication extends Application {
    File data = new File("FileData.txt");
    private List<User> users = new ArrayList<>();
    private User currentUser;
    private String currentUserRole;
    private InventorySystem inventorySystem;
    private Stage primaryStage;
    private List<Category> categories = new ArrayList<>();

    private List<Item> items;

    private void loadCategories() throws IOException, ClassNotFoundException {
        try (ObjectInputStream categoryInputStream = new ObjectInputStream(new FileInputStream("categories.ser"))) {
            categories = (List<Category>) categoryInputStream.readObject();
            System.out.println("Categories loaded successfully.");
        } catch (EOFException | FileNotFoundException e) {
            System.out.println("No existing category data found.");

        }
    }

    private void loadItems() throws IOException, ClassNotFoundException {
        try (ObjectInputStream itemInputStream = new ObjectInputStream(new FileInputStream("items.ser"))) {
            items = (List<Item>) itemInputStream.readObject();
            System.out.println("Items loaded successfully.");
        } catch (EOFException | FileNotFoundException e) {
            System.out.println("No existing item data found.");
        }
    }

    private void loadUsers() throws IOException, ClassNotFoundException {
        try (ObjectInputStream userInputStream = new ObjectInputStream(new FileInputStream("users.ser"))) {
            users = (List<User>) userInputStream.readObject();
            System.out.println("User data loaded successfully.");
        } catch (EOFException | FileNotFoundException e) {
            System.out.println("No existing user data found.");
        }
    }
    private void createDefaultAdminUser() {
        User adminUser = new User("admin", "admin");
        adminUser.setRole(UserRole.ADMIN);
        users = new ArrayList<>();  // Initialize the users list
        users.add(adminUser);
        saveUserData();
    }
    private void saveUserData() {
        try (ObjectOutputStream userOutputStream = new ObjectOutputStream(new FileOutputStream("users.ser"))) {
            userOutputStream.writeObject(users);
            System.out.println("User data saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    Font.loadFont()

    public void start(Stage primaryStage) throws IOException {
        try {
            loadCategories();
            loadItems();
            loadUsers();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        this.primaryStage = primaryStage;
        inventorySystem = new InventorySystem();
        BorderPane borderPane = new BorderPane();

        Label anchorpaneLabel = new Label("My Cafe");
        anchorpaneLabel.setAlignment(Pos.CENTER);
        anchorpaneLabel.setStyle("-fx-text-fill: grey; -fx-font-weight: Bold;");
        anchorpaneLabel.setFont(Font.font("Ariel",16));

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.setHgap(10);
        layout.setVgap(10);
        TextField username = new TextField("username");
        username.setPrefWidth(200);
        username.setPrefHeight(30);
        PasswordField password = new PasswordField();
        password.setPrefWidth(200);
        password.setPrefHeight(30);

        ImageView UserImage = new ImageView(new Image("file:E:\\2nd Semester\\Java opp\\MyCafe\\src\\img_2.png"));
        UserImage.setFitHeight(20);
        UserImage.setFitWidth(20);
        Label label = new Label("",UserImage);
//        label.setFont(Font.font("Times New Roman", 16));
//        label.setStyle("-fx-font-weight: Bold");

        ImageView keyIcon = new ImageView(new Image("file:E:\\2nd Semester\\Java opp\\MyCafe\\src\\img_1.png"));
        keyIcon.setFitHeight(20);
        keyIcon.setFitWidth(20);
        Label passwordlabel = new Label("",keyIcon);
//        passwordlabel.setFont(Font.font("Times New Roman", 16));
//        passwordlabel.setStyle("-fx-font-weight: Bold");

        layout.add(label, 0, 0);
        layout.add(passwordlabel, 0, 1);
        layout.add(username, 2, 0);
//        HBox passwordBox = new HBox(5);
//        passwordBox.getChildren().addAll(passwordlabel, password);
//        layout.add(passwordBox, 2, 1);
        layout.add(password, 2, 1);
        layout.setAlignment(Pos.CENTER);

        Button register = new Button("Forgot Password");
        register.setFont(new Font("Arial", 15));
        register.setStyle("-fx-text-fill: black; -fx-border-color: transparent; -fx-cursor: hand; -fx-background-color: transparent");
        register.setUnderline(true);

        Button loginbutton = new Button("Login");
        loginbutton.setStyle("-fx-cursor: hand;");
        loginbutton.setOnAction(e -> authenticateUser(username.getText(), password.getText()));

        layout.add(register, 2, 2);
        layout.add(loginbutton, 2, 3);

        borderPane.setCenter(layout);

        AnchorPane anchorPane = new AnchorPane();
        AnchorPane.setTopAnchor(anchorpaneLabel, 0.0);
        AnchorPane.setRightAnchor(anchorpaneLabel, 0.0);
        AnchorPane.setBottomAnchor(anchorpaneLabel, 0.0);
        AnchorPane.setLeftAnchor(anchorpaneLabel, 0.0);
        anchorPane.getChildren().add(anchorpaneLabel);
        anchorPane.setStyle("-fx-background-color: black;");
        anchorPane.setPrefWidth(200);
        borderPane.setLeft(anchorPane);


//        Image icon = new Image("E:\\2nd Semester\\Java opp\\Jfx\\src\\img.png");
//        primaryStage.getIcons().add(icon);
        Scene scene = new Scene(borderPane, 900, 600, Color.WHITE);

        primaryStage.setTitle("Login Credentials");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void authenticateUser(String enteredUsername, String enteredPassword) {
        boolean isAuthenticated = false;

        users.add(new User("suleman","password123",UserRole.ADMIN));
        users.add(new User("ali","1122",UserRole.userRole));
        users.add(new User("Ahmad", "ghijkl@mn", UserRole.SALESMAN));
        createDefaultAdminUser();

        for (User user : users) {
            if (user.getUsername().equals(enteredUsername) && user.getPassword().equals(enteredPassword)) {
                currentUser = user;
                currentUserRole = user.getRole().toString();
                showAlert("Login successful. Welcome, " + currentUser.getUsername() + "!", Alert.AlertType.INFORMATION);
                showNextScene();
                isAuthenticated = true;
                break;
            }
        }

        if (!isAuthenticated) {
            showAlert("Invalid username or password",Alert.AlertType.ERROR);
            System.out.println("Invalid credentials. Please try again.");
        }
    }
    private void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Inventory System");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void showNextScene() {

        try {
            loadCategories();
            loadItems();
            loadUsers();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        BorderPane bpane = new BorderPane();
        bpane.setStyle("-fx-background-color: grey;");

//        AnchorPane anchorPane = new AnchorPane();
//        anchorPane.setStyle("-fx-background-color: black;");

        Label label = new Label("Welcome to the main page");
        label.setTextFill(Color.WHITE);
        label.setAlignment(Pos.CENTER);
        label.setFont(Font.font("Times New Roman", 15));


        VBox adminMenu = new VBox(10);
        adminMenu.setAlignment(Pos.CENTER);
        adminMenu.setPadding(new Insets(20));
        adminMenu.setMinWidth(200);
        adminMenu.setStyle("-fx-background-color: black;");

        double buttonWidth = 180;
        double buttonHeight = 30;

        Button manageCategoriesButton = new Button("1. Manage Categories");
        manageCategoriesButton.setMinSize(buttonWidth,buttonHeight);
        manageCategoriesButton.setStyle("-fx-cursor: hand;");

        Button manageItemsButton = new Button("2. Manage Items");
        manageItemsButton.setMinSize(buttonWidth,buttonHeight);
        manageItemsButton.setStyle("-fx-cursor: hand;");

        Button manageUsersButton = new Button("3. Manage Users");
        manageUsersButton.setMinSize(buttonWidth,buttonHeight);
        manageUsersButton.setStyle("-fx-cursor: hand;");

        Button changeUsernameButton = new Button("4. Change Username");
        changeUsernameButton.setMinSize(buttonWidth,buttonHeight);
        changeUsernameButton.setStyle("-fx-cursor: hand;");

        Button changePasswordButton = new Button("5. Change Password");
        changePasswordButton.setMinSize(buttonWidth,buttonHeight);
        changePasswordButton.setStyle("-fx-cursor: hand;");

        Button logoutButton = new Button("6. Logout");
        logoutButton.setMinSize(buttonWidth,buttonHeight);
        logoutButton.setStyle("-fx-cursor: hand;");

        adminMenu.getChildren().addAll(
                manageCategoriesButton,
                manageItemsButton,
                manageUsersButton,
                changeUsernameButton,
                changePasswordButton,
                logoutButton
        );

        manageCategoriesButton.setOnAction(e -> showManageCategories());
        manageItemsButton.setOnAction(e -> showManageItems());


        bpane.setLeft(adminMenu);

        Scene nextScene = new Scene(bpane, 900, 600);
        primaryStage.setScene(nextScene);
    }
    private void showManageCategories() {
        // Create VBox for "Manage Categories" buttons
        VBox manageCategoriesMenu = new VBox(10);
        manageCategoriesMenu.setAlignment(Pos.CENTER);

        Button addCategoryButton = new Button("Add Category");
        Button deleteCategoryButton = new Button("Delete Category");
        Button updateCategoryButton = new Button(" Update Category");
        Button displayCategoriesButton = new Button("Display Categories");
        Button backButton = new Button("Back to Admin Menu");

        // Set a fixed size for each button
        double buttonWidth = 180;
        double buttonHeight = 30;

        addCategoryButton.setMinSize(buttonWidth, buttonHeight);
        deleteCategoryButton.setMinSize(buttonWidth, buttonHeight);
        updateCategoryButton.setMinSize(buttonWidth, buttonHeight);
        displayCategoriesButton.setMinSize(buttonWidth, buttonHeight);
        backButton.setMinSize(buttonWidth, buttonHeight);


        addCategoryButton.setOnAction(e -> addCategory());
        deleteCategoryButton.setOnAction(e -> deleteCategory());
        updateCategoryButton.setOnAction(e -> updateCategory());
        displayCategoriesButton.setOnAction(e -> displayCategories());
        backButton.setOnAction(e -> showNextScene()); // Go back to the main Admin Menu

        manageCategoriesMenu.getChildren().addAll(
                addCategoryButton,
                deleteCategoryButton,
                updateCategoryButton,
                displayCategoriesButton,
                backButton
        );

        ((BorderPane) primaryStage.getScene().getRoot()).setCenter(manageCategoriesMenu);
    }
    private void addCategory() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Category");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter category name:");

        // Traditional way to get the response value.
        String categoryName = dialog.showAndWait().orElse(null);

        if (categoryName != null && !categoryName.isEmpty()) {
            Category newCategory = new Category(categoryName);
            categories.add(newCategory);
            // Save categories to file or perform other necessary actions
            System.out.println("Category added successfully: " + categoryName);
        } else {
            System.out.println("Invalid category name.");
        }
    }

    private void deleteCategory() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Delete Category");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter category name to delete:");

        String categoryName = dialog.showAndWait().orElse(null);

        if (categoryName != null && !categoryName.isEmpty()) {
            categories.removeIf(category -> category.getCategoryName().equalsIgnoreCase(categoryName));

            // Save categories to file or perform other necessary actions
            System.out.println("Category deleted successfully: " + categoryName);
        } else {
            System.out.println("Invalid category name.");
        }
    }

    // Update category
    private void updateCategory() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Update Category");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter category name to update:");

        String oldCategoryName = dialog.showAndWait().orElse(null);

        if (oldCategoryName != null && !oldCategoryName.isEmpty()) {
            // Find the category to update
            for (Category category : categories) {
                if (category.getCategoryName().equalsIgnoreCase(oldCategoryName)) {
                    // Show another dialog to get the new category name
                    TextInputDialog updateDialog = new TextInputDialog();
                    updateDialog.setTitle("Update Category");
                    updateDialog.setHeaderText(null);
                    updateDialog.setContentText("Enter new category name:");

                    String newCategoryName = updateDialog.showAndWait().orElse(null);

                    if (newCategoryName != null && !newCategoryName.isEmpty()) {
                        // Update the category
                        category.setCategoryName(newCategoryName);
                        // Save categories to file or perform other necessary actions
                        System.out.println("Category updated successfully: " + newCategoryName);
                        return;
                    } else {
                        System.out.println("Invalid new category name.");
                        return;
                    }
                }
            }

            System.out.println("Category not found.");
        } else {
            System.out.println("Invalid category name.");
        }
    }

    // Display categories
    private void displayCategories() {
        // Create a TextArea to display categories
        TextArea categoriesTextArea = new TextArea();
        categoriesTextArea.setEditable(false);
        categoriesTextArea.setFont(Font.font("Arial", 14));

        System.out.println("\n====== Categories ======");
        for (Category category : categories) {
            categoriesTextArea.appendText(category.getCategoryName() + "\n");
        }

        // Create a new Stage to display the TextArea
        Stage categoriesStage = new Stage();
        categoriesStage.setTitle("Categories");
        Scene categoriesScene = new Scene(new BorderPane(categoriesTextArea), 300, 400);
        categoriesStage.setScene(categoriesScene);
        categoriesStage.show();
    }

    public void showManageItems() {
        VBox manageItemsMenu = new VBox(10);
        manageItemsMenu.setAlignment(Pos.CENTER);

        Button addItemButton = new Button("Add Item");
        Button deleteItemButton = new Button("Delete Item");
        Button updateItemButton = new Button(" Update Item");
        Button displayItemButton = new Button("Display Item");
        Button backButton = new Button("Back to Admin Menu");

        // Set a fixed size for each button
        double buttonWidth = 180;
        double buttonHeight = 30;

        addItemButton.setOnAction(e -> addItem());
        deleteItemButton.setOnAction(e -> deleteItem());
        updateItemButton.setOnAction(e -> updateItem());
        displayItemButton.setOnAction(e -> displayItems());
        backButton.setOnAction(e -> showNextScene());

        addItemButton.setMinSize(buttonWidth, buttonHeight);
        deleteItemButton.setMinSize(buttonWidth, buttonHeight);
        updateItemButton.setMinSize(buttonWidth, buttonHeight);
        displayItemButton.setMinSize(buttonWidth, buttonHeight);
        backButton.setMinSize(buttonWidth, buttonHeight);

        manageItemsMenu.getChildren().addAll(
                addItemButton,
                deleteItemButton,
                updateItemButton,
                displayItemButton,
                backButton
        );
        ((BorderPane) primaryStage.getScene().getRoot()).setCenter(manageItemsMenu);

    }
    private void addItem() {
        TextInputDialog itemNameDialog = new TextInputDialog();
        itemNameDialog.setTitle("Add Item");
        itemNameDialog.setHeaderText(null);
        itemNameDialog.setContentText("Enter item name:");

        itemNameDialog.showAndWait().ifPresent(itemName -> {
            TextInputDialog itemPriceDialog = new TextInputDialog();
            itemPriceDialog.setTitle("Add Item");
            itemPriceDialog.setHeaderText(null);
            itemPriceDialog.setContentText("Enter item price:");

            double itemPrice = Double.parseDouble(itemPriceDialog.showAndWait().orElse("0.0"));

            TextInputDialog itemCategoryDialog = new TextInputDialog();
            itemCategoryDialog.setTitle("Add Item");
            itemCategoryDialog.setHeaderText(null);
            itemCategoryDialog.setContentText("Enter item category:");

            String itemCategoryName = itemCategoryDialog.showAndWait().orElse("");

            int categoryId = getCategoryIDByName(itemCategoryName);

            if (categoryId != -1) {
                Item newItem = new Item(itemName, itemPrice, categoryId);
                items.add(newItem);
                FileHandler.saveDataToFile(items, "items.ser");
                showAlert("Item added successfully.", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Category not found. Item not added.", Alert.AlertType.ERROR);
            }
        });
    }

    private void deleteItem() {
        TextInputDialog itemNameDialog = new TextInputDialog();
        itemNameDialog.setTitle("Delete Item");
        itemNameDialog.setHeaderText(null);
        itemNameDialog.setContentText("Enter item name to delete:");

        itemNameDialog.showAndWait().ifPresent(itemName -> {
            items.removeIf(item -> item.getItemName().equalsIgnoreCase(itemName));
            FileHandler.saveDataToFile(items, "items.ser");
            showAlert("Item deleted successfully.", Alert.AlertType.INFORMATION);
        });
    }

    // Update item
    private void updateItem() {
        TextInputDialog oldItemNameDialog = new TextInputDialog();
        oldItemNameDialog.setTitle("Update Item");
        oldItemNameDialog.setHeaderText(null);
        oldItemNameDialog.setContentText("Enter item name to update:");

        oldItemNameDialog.showAndWait().ifPresent(oldItemName -> {
            TextInputDialog newItemNameDialog = new TextInputDialog();
            newItemNameDialog.setTitle("Update Item");
            newItemNameDialog.setHeaderText(null);
            newItemNameDialog.setContentText("Enter new item name:");

            String newItemName = newItemNameDialog.showAndWait().orElse("");

            TextInputDialog newItemPriceDialog = new TextInputDialog();
            newItemPriceDialog.setTitle("Update Item");
            newItemPriceDialog.setHeaderText(null);
            newItemPriceDialog.setContentText("Enter new item price:");

            double newItemPrice = Double.parseDouble(newItemPriceDialog.showAndWait().orElse("0.0"));

            TextInputDialog newItemCategoryDialog = new TextInputDialog();
            newItemCategoryDialog.setTitle("Update Item");
            newItemCategoryDialog.setHeaderText(null);
            newItemCategoryDialog.setContentText("Enter new item category:");

            String newItemCategoryName = newItemCategoryDialog.showAndWait().orElse("");

            int newCategoryId = getCategoryIDByName(newItemCategoryName);

            for (Item item : items) {
                if (item.getItemName().equalsIgnoreCase(oldItemName)) {
                    item.setItemName(newItemName);
                    item.setItemPrice(newItemPrice);
                    item.setCategoryId(newCategoryId);
                    FileHandler.saveDataToFile(items, "items.ser");
                    showAlert("Item updated successfully.", Alert.AlertType.INFORMATION);
                    return;
                }
            }

            showAlert("Item not found.", Alert.AlertType.ERROR);
        });
    }
    // Display items
    private void displayItems() {

        TextArea itemsTextArea = new TextArea();
        itemsTextArea.setEditable(false);
        itemsTextArea.setFont(Font.font("Arial", 14));
        System.out.println("\n====== Categories ======");
        for (Category category : categories) {
            itemsTextArea.appendText(category.getCategoryName() + "\n");
        }

        Stage itemsStage = new Stage();
        itemsStage.setTitle("Categories");
        Scene categoriesScene = new Scene(new BorderPane(itemsTextArea), 300, 400);
        itemsStage.setScene(categoriesScene);
        itemsStage.show();
    }



    public static void main(String[] args) {
        launch();
    }
}
