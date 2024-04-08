use prs;

INSERT INTO User (Username, Password, Firstname, Lastname, Phone, Email, Reviewer, Admin) VALUES ('eilluvatar', 'theone', 'Eru', 'Illuvatar', '123-456-7890', 'eilluvatar@ea.me', 1, 1);
INSERT INTO User (Username, Password, Firstname, Lastname, Phone, Email, Reviewer, Admin) VALUES ('manwe', 'mvala123', 'Manwe', 'Vala', '456-123-7890', 'manwe@valar.me', 1, 0);
INSERT INTO User (Username, Password, Firstname, Lastname, Phone, Email, Reviewer, Admin) VALUES ('morgoth', 'melkor123', 'Melkor', 'Morgoth', '987-654-3210', 'melkor@valar.me', 1, 0);
INSERT INTO User (Username, Password, Firstname, Lastname, Phone, Email, Reviewer, Admin) VALUES ('olorin','gandalf123','Olorin','Gandalf','123-132-1234','gandalf@maia.me',0,0);
INSERT INTO User (Username, Password, Firstname, Lastname, Phone, Email, Reviewer, Admin) VALUES ('curumo','saruman123','Curumo','Saruman','321-321-3210','saruman@maia.me',0,0);
INSERT INTO User (Username, Password, Firstname, Lastname, Phone, Email, Reviewer, Admin) VALUES ('ethingol','thingol123','Elu','Thingol','456-456-4569','ethingol@doriath.me',1,0);
INSERT INTO User (Username, Password, Firstname, Lastname, Phone, Email, Reviewer, Admin) VALUES ('berchamion','beren123','Beren','Erchamion','564-564-5647','berchamion@tolgalen.me',0,0);
INSERT INTO User (Username, Password, Firstname, Lastname, Phone, Email, Reviewer, Admin) VALUES ('tturambar','turin123','Beren','Erchamion','564-564-5647','berchamion@tolgalen.me',0,0);
INSERT INTO User (Username, Password, Firstname, Lastname, Phone, Email, Reviewer, Admin) VALUES ('elrondhalf','elrond123','Elrond','Half-Elven','987-654-9870','elrond@rivendell.me',1,0);
INSERT INTO User (Username, Password, Firstname, Lastname, Phone, Email, Reviewer, Admin) VALUES ('atelcontar','aeles123','Aragorn','Telcontar','564-564-5647','aragorn@gondor.me',0,0);
INSERT INTO User (Username, Password, Firstname, Lastname, Phone, Email, Reviewer, Admin) VALUES ('legolas','lego123','Legolas','Greenleaf','456-856-3652','lgreenleaf@mirkwood.me',0,0);
INSERT INTO User (Username, Password, Firstname, Lastname, Phone, Email, Reviewer, Admin) VALUES ('fbaggins','bags123','Frodo','Baggins','859-621-7892','fbaggins@shire.me',0,0);
INSERT INTO User (Username, Password, Firstname, Lastname, Phone, Email, Reviewer, Admin) VALUES ('sgamgee','gamgee123','Samwise','Gamgee','458-237-5961','sgamgee@shire.me',0,0);
INSERT INTO User (Username, Password, Firstname, Lastname, Phone, Email, Reviewer, Admin) VALUES ('mbrandybuck','brandy123','Meriadoc','Brandybuck','569-897-1265','mbrandybuck@shire.me',0,0);
INSERT INTO User (Username, Password, Firstname, Lastname, Phone, Email, Reviewer, Admin) VALUES ('ptook','took123','Peregrin','Took','891-305-6902','ptook@shire.me',0,0);
INSERT INTO User (Username, Password, Firstname, Lastname, Phone, Email, Reviewer, Admin) VALUES ('gimli','gimli123','Gimli','Lockbearer','456-801-4932','gimli@erebor.me',0,0);
INSERT INTO User (Username, Password, Firstname, Lastname, Phone, Email, Reviewer, Admin) VALUES ('boromir','boromir123','Boromir','Son of Denethor','478-693-1235','boromir@gondor.me',0,0);
INSERT INTO User (Username, Password, Firstname, Lastname, Phone, Email, Reviewer, Admin) VALUES ('gothmog','gothmog123','Gothmog','Balrog','999-897-6987','gothmog@angband.me',0,0);
INSERT INTO User (Username, Password, Firstname, Lastname, Phone, Email, Reviewer, Admin) VALUES ('carcharoth','caranf123','Carcharoth','Anfauglir','999-658-6987','carcharoth@angband.me',0,0);
INSERT INTO User (Username, Password, Firstname, Lastname, Phone, Email, Reviewer, Admin) VALUES ('sauron','saur123','Sauron','Mairon','999-659-8569','sauron@mordor.me',0,0);

INSERT INTO Vendor (Code, Name, Address, City, State, Zip, Phone, Email) Values ('FEANOR', 'Feanor', 'Unit 123', 'Halls of Mandos', 'VA', '12345', '365-456-7890', 'feanor@hallsofmandos.me');
INSERT INTO Vendor (Code, Name, Address, City, State, Zip, Phone, Email) Values ('GALDRIEL', 'Galadriel', 'Unit 275', 'Caras Galadhon', 'LL', '12456', '569-712-3025', 'galadriel@lothlorien.me');
INSERT INTO Vendor (Code, Name, Address, City, State, Zip, Phone, Email) Values ('PITANG', 'Pits of Angband', 'Unit 6987', 'Angband', 'IP', '56874', '999-865-7854', 'pits@angband.me');
INSERT INTO Vendor (Code, Name, Address, City, State, Zip, Phone, Email) Values ('GWAIMIR', 'Gwaith-i-Mirdain', 'Unit 987', 'Ost-in-Edhil', 'ER', '69865', '968-569-5632', 'gim@eregion.me');
INSERT INTO Vendor (Code, Name, Address, City, State, Zip, Phone, Email) Values ('TELCHAR', 'Telchar', 'Unit 100', 'Nogrod', 'BM', '98682', '362-458-8525', 'telchar@bluemountains.me');

INSERT INTO Product (VendorID, PartNumber, Name, Price, Unit) Values (1, 'SILMARIL', 'Silmaril', 1000000, 'EA');
INSERT INTO Product (VendorID, PartNumber, Name, Price, Unit) Values (1, 'PALANTIR', 'Palantir', 100000, 'EA');
INSERT INTO Product (VendorID, PartNumber, Name, Price, Unit) Values (1, 'FEANLAMP', 'Feanorian Lamp', 10000, 'EA');
INSERT INTO Product (VendorID, PartNumber, Name, Price, Unit) Values (2, 'PHIAL', 'Phial of Galadriel', 1000, 'EA');
INSERT INTO Product (VendorID, PartNumber, Name, Price, Unit) Values (2, 'SHEATH', 'Sheath', 100, 'EA');
INSERT INTO Product (VendorID, PartNumber, Name, Price, Unit) Values (2, 'GBELT', 'Gold Belt', 75, 'EA');
INSERT INTO Product (VendorID, PartNumber, Name, Price, Unit) Values (2, 'SBELT', 'Silver Belt', 50, 'EA');
INSERT INTO Product (VendorID, PartNumber, Name, Price, Unit) Values (2, 'GBOW', 'Galadhrim Bow', 150, 'EA');
INSERT INTO Product (VendorID, PartNumber, Name, Price, Unit) Values (2, 'LEMBAS', 'Lembas', 350, 'EA');
INSERT INTO Product (VendorID, PartNumber, Name, Price, Unit) Values (2, 'CLOAK', 'Elven Cloaks', 125, 'EA');
INSERT INTO Product (VendorID, PartNumber, Name, Price, Unit) Values (3, 'BLAXE', 'Black Axe', 250, 'EA');
INSERT INTO Product (VendorID, PartNumber, Name, Price, Unit) Values (3, 'FOODELF', 'Live Elf', 50, 'EA');
INSERT INTO Product (VendorID, PartNumber, Name, Price, Unit) Values (3, 'FOODMAN', 'Live MAN', 50, 'EA');
INSERT INTO Product (VendorID, PartNumber, Name, Price, Unit) Values (4, 'LROP', 'Lesser Ring of Power', 7500, 'EA');
INSERT INTO Product (VendorID, PartNumber, Name, Price, Unit) Values (4, 'GROP', 'Greater Ring of Power', 25000, 'EA');
INSERT INTO Product (VendorID, PartNumber, Name, Price, Unit) Values (5, 'ANGRIST', 'Angrist', 750, 'EA');
INSERT INTO Product (VendorID, PartNumber, Name, Price, Unit) Values (5, 'DRAGHELM', 'Dragon Helm of Dorlomin', 1200, 'EA');
INSERT INTO Product (VendorID, PartNumber, Name, Price, Unit) Values (5, 'NARSIL', 'Narsil', 1800, 'EA');

INSERT INTO Request (UserId, Description, Justification, DateNeeded, Total) Values (3, 'Silmarils', 'I want the silmarils', '2024-06-01', 3000000);
INSERT INTO LineItem(RequestId, ProductId, Quantity) VALUES (1, 1, 3);

INSERT INTO Request (UserId, Description, Justification, DateNeeded, Total) Values (12, 'Phial of Galadriel', 'May it be a light to you in dark places, when all other lights go out.', '2024-06-01', 1000);
INSERT INTO LineItem(RequestId, ProductId, Quantity) VALUES (2, 4, 1);

INSERT INTO Request (UserId, Description, Justification, DateNeeded, Total) Values (4, 'Lamps of Feanor', 'I need light to be able to cross the ocean', '2024-06-01', 40000);
INSERT INTO LineItem(RequestId, ProductId, Quantity) VALUES (3, 3, 4);