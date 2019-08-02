INSERT INTO STORES(NAME, OPEN, MIN_ORDER_AMOUNT, COMMISSION_RATE, COMMISSION) VALUES('E-Store', 1, 13000, 0.01, 0);

INSERT INTO PRODUCTS(STORE_ID, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_DESCRIPTION) VALUES(1, 'Fusion5 ProGlide Power razors', 12000, 'Gillette Fusion5 ProGlide Power razors for men feature 5 anti-friction blades.');

INSERT INTO OPTION_GROUP_SPECS(PRODUCT_ID, NAME, EXCLUSIVE, BASIC) VALUES(1, 'Razor blade', 1, 1);
INSERT INTO OPTION_SPECS(OPTION_GROUP_SPEC_ID, NAME, PRICE) VALUES(1, '2 blade', 12000);
INSERT INTO OPTION_SPECS(OPTION_GROUP_SPEC_ID, NAME, PRICE) VALUES(1, '4 blade', 24000);
INSERT INTO OPTION_SPECS(OPTION_GROUP_SPEC_ID, NAME, PRICE) VALUES(1, '8 blade', 36000);

INSERT INTO OPTION_GROUP_SPECS(PRODUCT_ID, NAME, EXCLUSIVE, BASIC) VALUES(1, 'Color', 0, 0);
INSERT INTO OPTION_SPECS(OPTION_GROUP_SPEC_ID, NAME, PRICE) VALUES(2, 'White', 0);
INSERT INTO OPTION_SPECS(OPTION_GROUP_SPEC_ID, NAME, PRICE) VALUES(2, 'Black', 1000);

INSERT INTO OPTION_GROUP_SPECS(PRODUCT_ID, NAME, EXCLUSIVE, BASIC) VALUES(1, 'Accessories', 0, 0);
INSERT INTO OPTION_SPECS(OPTION_GROUP_SPEC_ID, NAME, PRICE) VALUES(3, 'Razor case', 1000);
INSERT INTO OPTION_SPECS(OPTION_GROUP_SPEC_ID, NAME, PRICE) VALUES(3, 'Razor cleaner', 1000);
INSERT INTO OPTION_SPECS(OPTION_GROUP_SPEC_ID, NAME, PRICE) VALUES(3, 'Shaving form', 5000);

