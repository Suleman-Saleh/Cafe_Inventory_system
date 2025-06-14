package com.progmeleon.mycafe.model;

import com.progmeleon.mycafe.config.ConfigureExistingData;
import com.progmeleon.mycafe.config.DBConnector;
import com.progmeleon.mycafe.config.ReceiptPrinter;
import com.progmeleon.mycafe.controller .InventorySystem;

import java.io.IOException;
import java.sql.SQLException;

import static com.progmeleon.mycafe.controller.OrderHandler.placeOrder;

public class  Main {
    // section main
    public static void main(String[] args) throws IOException {
        ConfigureExistingData.loadExistingData();
        new InventorySystem().start();
//        placeOrder();

//        String receiptContent = "Item 1: $10.00\nItem 2: $20.00\nTotal: $30.00";
//
//        ReceiptPrinter receiptPrinter = new ReceiptPrinter(receiptContent);
//        receiptPrinter.printReceipt();

    }


}
