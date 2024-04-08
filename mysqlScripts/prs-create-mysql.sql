DROP DATABASE IF EXISTS prs;

CREATE DATABASE prs;

USE prs;

-- main differences from sql server:
-- no GO
-- no USE master
-- AUTO_INCREMENT vs IDENTITY(1,1)
-- No need for square braces
-- Need semicolons

CREATE TABLE User (
	Id int PRIMARY KEY AUTO_INCREMENT,
	Username varchar(20) NOT NULL UNIQUE,
	Password varchar(10) NOT NULL,
	Firstname varchar(20) NOT NULL,
	Lastname varchar(20) NOT NULL,
	Phone varchar(12),
	Email varchar(75),
	Reviewer bit,
	Admin bit 
);

CREATE TABLE Vendor (
	Id int PRIMARY KEY AUTO_INCREMENT,
	Code varchar(10) NOT NULL UNIQUE,
	Name varchar(255) NOT NULL,
	Address varchar(255) NOT NULL,
	City varchar(255) NOT NULL,
	State varchar(2) NOT NULL,
	Zip varchar(5) NOT NULL,
	Phone varchar(12),
	Email varchar(100)
);

CREATE TABLE Product (
	Id int PRIMARY KEY AUTO_INCREMENT,
	VendorId int NOT NULL,
	PartNumber varchar(50) NOT NULL,
	Name varchar(150) NOT NULL,
	Price decimal(10,2) NOT NULL,
	Unit varchar(255) NOT NULL,
	PhotoPath varchar(255),
	CONSTRAINT UQ_Vendor_PartNum UNIQUE (VendorId, PartNumber),
	FOREIGN KEY (VendorId) REFERENCES Vendor(Id)
);

CREATE Table Request (
	Id int PRIMARY KEY AUTO_INCREMENT,
	UserId int NOT NULL,
	Description varchar(100) NOT NULL,
	Justification varchar(255) NOT NULL,
	DateNeeded date NOT NULL,
	DeliveryMode varchar(25) NOT NULL DEFAULT 'Pickup',
	Status varchar(20) NOT NULL DEFAULT 'New',
	Total decimal(10,2) NOT NULL DEFAULT 0.0,
	SubmittedDate date NOT NULL default (CURRENT_DATE),
	ReasonForRejection varchar(100),
	FOREIGN KEY (UserId) REFERENCES User(Id)
);

CREATE Table LineItem (
	Id int PRIMARY KEY AUTO_INCREMENT,
	RequestId int,
	ProductId int,
	Quantity int DEFAULT 1,
	FOREIGN KEY (RequestId) REFERENCES Request(Id),
	FOREIGN KEY (ProductId) REFERENCES Product(Id),
	CONSTRAINT UQ_Req_Pdt UNIQUE (RequestId, ProductId)
);