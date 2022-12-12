CREATE TABLE USERS (
Username VARCHAR (20) UNIQUE NOT NULL,
CreditNum VARCHAR (12),
Address VARCHAR (30),
Admi CHAR (1),
Fname VARCHAR (15) NOT NULL,
Lname VARCHAR (15) NOT NULL,
primary key (Username)
);


CREATE TABLE ORDERS (
OrderNum CHAR (9) UNIQUE NOT NULL,
Costs numeric (10,2) NOT NULL,
TrackingNum CHAR (9) UNIQUE NOT NULL,
CreditNum VARCHAR (12) NOT NULL,
Address VARCHAR (30) NOT NULL,
Username VARCHAR (20) NOT NULL,
primary key (OrderNum),
foreign key (Username) references USERS(Username)
);

CREATE TABLE PUBLISHER (
Email VARCHAR (30) UNIQUE NOT NULL,
Pname VARCHAR (50) NOT NULL,
BankNum VARCHAR (12) UNIQUE NOT NULL,
Address VARCHAR (30),
primary key (Email)
);

CREATE TABLE PUBLISHERPHONE (
Email VARCHAR (30) NOT NULL,
Pphone VARCHAR (12) UNIQUE NOT NULL,
primary key (Email, Pphone),
foreign key (Email) references PUBLISHER(Email)
);

CREATE TABLE BOOK (
ISBN CHAR (9) UNIQUE NOT NULL,
Price numeric (10,2) NOT NULL,
SupplierPrice numeric (10,2) NOT NULL,
BName VARCHAR (30) NOT NULL,
PageNum numeric (4) NOT NULL,
Percentage numeric (2,2),
Restock numeric (2),
Qty numeric (4),
Expenditure numeric (10,2),
PubEmail VARCHAR (30) NOT NULL,
TotalSold  numeric (10) NOT NULL,
primary key (ISBN),
foreign key (PubEmail) references PUBLISHER(Email)
);

CREATE TABLE ORDERED (
ISBN CHAR (9) NOT NULL,
OrderNum CHAR (9) NOT NULL,
Qty numeric (4),
primary key (ISBN, OrderNum),
foreign key (ISBN) references BOOK(ISBN),
foreign key (OrderNum) references ORDERS(OrderNum)
);

CREATE TABLE AUTHOR (
Fname VARCHAR (15) NOT NULL,
Sales numeric (4) NOT NULL,
Lname VARCHAR (15) NOT NULL,
AID CHAR (9) UNIQUE NOT NULL,
primary key (AID)
);

CREATE TABLE GENRE (
Genre VARCHAR (15) UNIQUE NOT NULL,
Sales numeric (4) NOT NULL,
primary key (Genre)
);
	
CREATE TABLE WRITES (
ISBN CHAR (9) NOT NULL,
AID CHAR (9)  NOT NULL,
primary key (ISBN, AID),
foreign key (ISBN) references BOOK(ISBN),
foreign key (AID) references AUTHOR(AID)
);

CREATE TABLE ISGENRE (
ISBN CHAR (9) NOT NULL,
Genre VARCHAR (15) NOT NULL,
primary key (ISBN, Genre),
foreign key (ISBN) references BOOK(ISBN),
foreign key (Genre) references GENRE(Genre)
);