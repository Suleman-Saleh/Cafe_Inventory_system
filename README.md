# 🍔 MyCafe: Fast Food Inventory & Shop Management

A desktop system for running a fast-food shop: menu items and deals, orders and receipts, stock and staff, built with **Java** and **JavaFX**.

## Features

- **Users and roles:** admin and salesman accounts
- **Menu:** items by category and size (small to extra large), and combo deals
- **Orders:** dine-in or take-away, with status pending → confirmed → completed or cancelled
- **Receipts** printed for each order, and a sales record
- **Inventory:** stock tracking
- **Storage:** JSON files (and Java serialization) for the data, with a MySQL schema (`mycafe.sql`)

## Project layout

```
model/        Category, Item, Deal, Order, SalesRecord, User and their enums
controller/   Category, Item, Deal, Order and User handlers; file storage
config/       app settings, data loading, MySQL connection, receipt printer
src/main/     the JavaFX application (Maven)
```

## Run it

Requires JDK 21 (JavaFX 21 is pulled in by Maven).

```bash
./mvnw clean javafx:run          # Windows: mvnw.cmd clean javafx:run
```

For MySQL, import `mycafe.sql` and set the connection in `config/DBConnector.java`.

## Tech

Java · JavaFX · Maven · MySQL · Jackson (JSON)

---

Built by [Muhammad Suleman](https://github.com/Suleman-Saleh)
