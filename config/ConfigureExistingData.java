package com.progmeleon.mycafe.config;

import com.progmeleon.mycafe.controller.FileHandler;
import com.progmeleon.mycafe.model.Category;
import com.progmeleon.mycafe.model.Deal;
import com.progmeleon.mycafe.model.Item;
import com.progmeleon.mycafe.model.User;

import java.util.List;

public class ConfigureExistingData {
    public static List<Category> categories;
    public static List<Item> items;
    public static List<User> users;
    public static List<Deal> deals;

    public ConfigureExistingData() {
        loadExistingData();
    }

    public static void loadExistingData() {

        categories = FileHandler.loadData(Category.class, ConfigureApp.CATEGORIES_FILE_PATH);
        items = FileHandler.loadData(Item.class, ConfigureApp.ITEMS_FILE_PATH);
        users = FileHandler.loadData(User.class, ConfigureApp.USERS_FILE_PATH);
        deals = FileHandler.loadData(Deal.class, ConfigureApp.DEALS_FILE_PATH);
    }

}
