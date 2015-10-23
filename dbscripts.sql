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
  SUB_MERCHANT_ID varchar(255)
 );

-- Transaction Table
create table transaction (
	SERIAL_ID int(11) unsigned zerofill not null auto_increment primary key,
	TRANSACTION_ID varchar(255),
    TRANSACTION_TYPE varchar(50),
    MERCHANT_ID int(11),
    CUSTOMER_ID int(11),
    ITEM_UPC varchar(255),
    AMOUNT float(9,2),
    STATUS varchar(50),
    SESSION_ID varchar(50)
 );

 -- Customer Table

 create table CUSTOMER (
  CUSTOMER_ID int(11) unsigned zerofill not null auto_increment primary key,
  FIRST_NAME varchar(255),
  LAST_NAME varchar(255),
  EMAIL varchar(255),
  PHONE_NUMBER varchar(15),
  BT_CUSTOMER_ID varchar(255),
  PAYMENT_METHOD_TOKEN varchar(255),
  ADDRESS_LINE1 varchar(255),
  LOCALITY varchar(255),
  REGION varchar(255),
  POSTAL_CODE varchar(255)
 );

 -- Device Table

 create table DEVICE (
  DEVICE_ID int(11) unsigned zerofill not null auto_increment primary key,
  MAC_ID varchar(100) unique,
  BT_CUSTOMER_ID varchar(255),
  ITEM_ID int(11),
  QUANTITY int(4)
 );


