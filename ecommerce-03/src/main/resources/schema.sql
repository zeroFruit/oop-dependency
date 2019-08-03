DROP TABLE IF EXISTS STORES;
CREATE TABLE STORES(
    STORE_ID INT PRIMARY KEY AUTO_INCREMENT,
    NAME VARCHAR(150) NOT NULL              COMMENT 'store name',
    OPEN BIT NOT NULL                       COMMENT 'status whether store is open or not',
    MIN_ORDER_AMOUNT INT NOT NULL           COMMENT 'minimum delivery amount',
    COMMISSION_RATE FLOAT NOT NULL          COMMENT 'commission rate',
    COMMISSION INT NOT NULL DEFAULT 0       COMMENT 'commission'
);


DROP TABLE IF EXISTS PRODUCTS;
CREATE TABLE PRODUCTS(
    PRODUCT_ID INT PRIMARY KEY AUTO_INCREMENT,
    STORE_ID INT NULL                        COMMENT 'store id',
    PRODUCT_NAME VARCHAR(200) NOT NULL         COMMENT 'product name',
    PRODUCT_DESCRIPTION VARCHAR(500) NOT NULL  COMMENT 'product description',
    PRODUCT_PRICE INT NOT NULL                 COMMENT 'product base price'
);

DROP TABLE IF EXISTS OPTION_GROUP_SPECS;
CREATE TABLE OPTION_GROUP_SPECS(
    OPTION_GROUP_SPEC_ID INT PRIMARY KEY AUTO_INCREMENT,
    NAME VARCHAR(100) NOT NULL              COMMENT 'option spec name',
    PRODUCT_ID INT NULL                        COMMENT 'product id',
    EXCLUSIVE BIT NOT NULL                  COMMENT 'possibility whether option can be choosed exclusively',
    BASIC BIT NOT NULL                      COMMENT 'status whether option is basic group'
);


DROP TABLE IF EXISTS OPTION_SPECS;
CREATE TABLE OPTION_SPECS(
    OPTION_SPEC_ID INT PRIMARY KEY AUTO_INCREMENT,
    OPTION_GROUP_SPEC_ID INT NULL           COMMENT 'option group spec id',
    NAME VARCHAR(100) NOT NULL              COMMENT 'option spec name',
    PRICE INT NOT NULL                      COMMENT 'price'
);


DROP TABLE IF EXISTS ORDERS;
CREATE TABLE ORDERS(
    ORDER_ID INT PRIMARY KEY AUTO_INCREMENT,
    USER_ID INT NOT NULL                    COMMENT 'user id',
    STORE_ID INT NOT NULL                    COMMENT 'store id',
    ORDERED_TIME TIMESTAMP NOT NULL         COMMENT 'order timestamp',
    STATUS VARCHAR(30) NOT NULL             COMMENT 'shipping status'
);

DROP TABLE IF EXISTS ORDER_ITEMS;
CREATE TABLE ORDER_ITEMS(
    ORDER_ITEM_ID INT PRIMARY KEY AUTO_INCREMENT,
    ORDER_ID INT NULL                       COMMENT 'order id',
    PRODUCT_ID INT NOT NULL                    COMMENT 'product id',
    PRODUCT_NAME VARCHAR(200) NOT NULL         COMMENT 'product name',
    PRODUCT_COUNT INT NOT NULL                 COMMENT 'product count'
);


DROP TABLE IF EXISTS ORDER_OPTION_GROUPS;
CREATE TABLE ORDER_OPTION_GROUPS(
    ORDER_OPTION_GROUP_ID INT PRIMARY KEY AUTO_INCREMENT,
    ORDER_ITEM_ID INT NULL             COMMENT 'cart id',
    NAME  VARCHAR(100) NOT NULL             COMMENT 'name'
);


DROP TABLE IF EXISTS ORDER_OPTIONS;
CREATE TABLE ORDER_OPTIONS(
    ORDER_OPTION_GROUP_ID INT NOT NULL      COMMENT 'order option group id',
    NAME VARCHAR(100) NOT NULL              COMMENT 'order spec name',
    PRICE INT NOT NULL                      COMMENT 'price',
    CONSTRAINT PK_ORDER_OPTIONS PRIMARY KEY (ORDER_OPTION_GROUP_ID, NAME, PRICE)
);


DROP TABLE IF EXISTS SHIPPINGS;
CREATE TABLE SHIPPINGS(
    SHIPPING_ID INT PRIMARY KEY AUTO_INCREMENT,
    ORDER_ID INT NOT NULL                   COMMENT 'order id',
    STATUS VARCHAR(30) NOT NULL             COMMENT 'shipping status'
);

DROP TABLE IF EXISTS BILLINGS;
CREATE TABLE BILLINGS(
    BILLING_ID INT PRIMARY KEY AUTO_INCREMENT,
    STORE_ID VARCHAR(150) NOT NULL          COMMENT 'store id',
    COMMISSION INT NOT NULL                 COMMENT 'commission fee'
);