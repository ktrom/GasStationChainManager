-- DELETE --
SET FOREIGN_KEY_CHECKS = 0;
DELETE FROM hsnkwamy_GasStation.GasStation;
DELETE FROM hsnkwamy_GasStation.Employee;
DELETE FROM hsnkwamy_GasStation.Item;
DELETE FROM hsnkwamy_GasStation.Inventory;
DELETE FROM hsnkwamy_GasStation.Schedule;
DELETE FROM hsnkwamy_GasStation.Transaction;
DELETE FROM hsnkwamy_GasStation.Task;
SET FOREIGN_KEY_CHECKS = 1;


-- RESET AUTO_INCREMENT IDs --
ALTER TABLE hsnkwamy_GasStation.GasStation AUTO_INCREMENT = 1;
ALTER TABLE hsnkwamy_GasStation.Employee AUTO_INCREMENT = 1;
ALTER TABLE hsnkwamy_GasStation.Item AUTO_INCREMENT = 1;
ALTER TABLE hsnkwamy_GasStation.Transaction AUTO_INCREMENT = 1;
ALTER TABLE hsnkwamy_GasStation.Task AUTO_INCREMENT = 1;


-- INSERTS --
INSERT INTO hsnkwamy_GasStation.GasStation (Location)
	VALUES
    ("4018 Lincoln Way, Ames IA 50014"),
    ("3406 Lincoln Way, Ames IA 50010");

INSERT INTO hsnkwamy_GasStation.Employee(GasStationID, Name, SSN, Salary, Department, EmployeePosition, StartDate)
	VALUES
	(1, "Eli Musgrove", "166-28-2661", 70.33, "Corporate", "coo", NOW()),
    (1, "Elon Musk", "677-28-9188", 66.34, "Corporate", "cfo", NOW()),
	(1, "Kyle", "291-38-1233", 12.50, "Gas Station", "attendant", NOW() - INTERVAL 10 DAY),
    (1, "Cassie", "402-30-1923", 14.00, "Gas Station", "manager", NOW() - INTERVAL 20 DAY),
    (1, "Jill", "978-19-1098", 15.00, "Gas Station", "hiring_manager", NOW() - INTERVAL 2 DAY),
    (2, "Beth", "873-09-1066", 12.50, "Gas Station", "attendant", NOW() - INTERVAL 5 DAY),
    (2, "Josh", "845-09-5671", 14.50, "Gas Station", "manager", NOW() - INTERVAL 3 DAY),
    (2, "Jim", "295-10-5715", 15.00, "Gas Station", "hiring_manager", NOW() - INTERVAL 6 DAY);

INSERT INTO hsnkwamy_GasStation.Item (Name, Price, SupplierPrice, PhotoURL, Notes)
	VALUES
    ("Gasoline", 2.25, 2.00, "", "Standard fuel."),
    ("Ethanol", 2.00, 1.00, "", "Iowan ethanol fuel."),
    ("Diesel", 3.00, 2.75, "", "Diesel fuel, diesel engines only."),
    ("Pepperoni Pizza", 1.00, 0.25, "", "Prepared daily by chef mic."),
    ("Chips", 1.00, 0.50, "", "Probably stale and opened already but they'll still buy them."),
    ("Red Bull", 6.00, 5.00, "", "Not for consumption by children."),
    ("Donut Holes", 2.00, 1.50, "", "When they can bounce throw them out."),
    ("Powerball Lottery Ticket", 2.00, 1.90, "", "Try your luck!");

#     ("Mega Millions Lottery Ticket", 2.00, 1.90, "", "Who wants to be a millionaire?"),
#     ("Scratch-off Lottery Ticket", 5.00, 4.00, "", "Not for sniffing.");



INSERT INTO hsnkwamy_GasStation.Inventory (GasStationID, ItemID, Quantity)
	VALUES
    (1, 1, 100),
    (1, 2, 100),
    (1, 3, 100),
    (2, 1, 100),
    (2, 2, 100),
    (2, 3, 100),
    (1, 4, 10),
    (2, 4, 30),
    (1, 5, 50),
    (2, 5, 30),
    (2, 6, 25),
    (1, 7, 150),
    (2, 7, 300);

INSERT INTO hsnkwamy_GasStation.Transaction (TransactionID, GasStationID, ItemID, Quantity, DateSold)
	VALUES
    (1, 2, 1, 10, NOW());
