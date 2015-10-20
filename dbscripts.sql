--Catalog Table
create table catalog (
	CATALOG_ID int(11) unsigned zerofill not null auto_increment primary key,
    ITEM_UPC varchar(255),
    MERCHANT_ID int(11),
    PRICE float(9,2),
    QUANTITY int(4),
    AVAILABILITY boolean
 );

-- Item Table
 create table item (
  ITEM_ID int(11) unsigned zerofill not null auto_increment primary key,
  ITEM_UPC varchar(255),
  ITEM_NAME varchar(255),
  ITEM_DESC varchar(255)
 );

-- Merchant Table
 create table merchant (
  MERCHANT_ID int(11) unsigned zerofill not null auto_increment primary key,
  BUSINESS_NAME varchar(255),
  MERCH_ACCT_ID varchar(255)
 );

-- Transaction Table
 create table transaction (
 	TRANSACTION_ID int(11) unsigned zerofill not null auto_increment primary key,
     TRANSACTION_TYPE varchar(50),
     MERCHANT_ID int(11),
     CUSTOMER_ID int(11),
     ITEM_UPC varchar(255),
     AMOUNT float(9,2),
     STATUS varchar(50),
     SESSION_ID varchar(50)
  );


