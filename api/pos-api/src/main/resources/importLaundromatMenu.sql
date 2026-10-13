
insert into node_types(id,description) values(1,'root');
insert into node_types(id,description) values(2,'branch');
insert into node_types(id,description) values(3,'sterm');
insert into node_types(id,description) values(4,'leaf');

insert into menu_item_type(id,description) values(1,'Clothing Item');
insert into menu_item_type(id,description) values(2,'Laundry');
insert into menu_item_type(id,description) values(3,'Sneeker');
insert into menu_item_type(id,description) values(4,'Bakery Item');
insert into menu_item_type(id,description) values(5,'Other Item');
insert into menu_item_type(id,description) values(6,'Tailoring Services');

insert into menu_item_categories(id,description) values(1,'Kitchen');
insert into menu_item_categories(id,description) values(2,'Groceries');
insert into menu_item_categories(id,description) values(3,'Drinks');
insert into menu_item_categories(id,description) values(4,'Butchery');
insert into menu_item_categories(id,description) values(5,'Backery');
insert into menu_item_categories(id,description) values(6,'Bus Tickets');
insert into menu_item_categories(id,description) values(7,'Airtime');
insert into menu_item_categories(id,description) values(8,'General');


insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id,item_type_id) values(1,0,'Edible Items',0.00,0.00,0,'',1,3);
insert into menu_items(id,linked_id,caption,price,vat,menu_level, icon,node_type_id,item_type_id) values(2,0,'Laundry',0.00,0.00,0,'laundry.png',1,1);


insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id,icon,item_type_id) values(3,2,'Wash Dry Fold',38.00,0.15,3,'',4,'laundry.png',2);
insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id,icon,item_type_id) values(4,2,'Wash Dry Iron',45.00,0.15,3,'',4,'laundry.png',2);
insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id,icon,item_type_id) values(5,2,'Iron Only',40.00,0.15,3,'',4,'laundry.png',2);
insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id,icon,item_type_id) values(6,2,'Dry Only',35.00,0.15,3,'',4,'laundry.png',2);

insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id,item_type_id) values(7,0,'Blankets',0.00,0.00,0,'',1,3);


insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id,item_type_id) values(9,1,'Nuts',133.00,0.15,3,'',4,3);


insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id,item_type_id) values(30,0,'Additional Services',0.00,0.00,0,'',1,1);
insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id,icon,item_type_id) values(31,30,'Sneeker Wash',50.00,0.15,3,'',4,'sneaker.png',3);
insert into menu_items(id,linked_id,caption,price,vat,menu_level, icon,node_type_id,item_type_id) values(32,30,'Dry Cleaning',0.00,0.00,1,'dryCleaning.png',2,1);
insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id,icon,item_type_id) values(33,32,'Trousers',85.00,0.15,3,'',4,'SleevelessShirt.png',3);

insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id,icon,item_type_id) values(34,32,'Jackets',90.00,0.15,3,'',4,'SleevelessShirt.png',3);
insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id,icon,item_type_id) values(35,32,'1x2 Suit',170.00,0.15,3,'',4,'SleevelessShirt.png',3);
insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id,icon,item_type_id) values(36,32,'1x3 Suit',250.00,0.15,3,'',4,'SleevelessShirt.png',3);
insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id,icon,item_type_id) values(37,32,'Waistcoat',80.00,0.15,3,'',4,'SleevelessShirt.png',3);
insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id,icon,item_type_id) values(38,32,'Jersey / Cardigan',110.00,0.15,3,'',4,'SleevelessShirt.png',3);
insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id,icon,item_type_id) values(39,32,'Lumber Jacket',120.00,0.15,3,'',4,'SleevelessShirt.png',3);
insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id,icon,item_type_id) values(40,32,'Bomba Jacket',160.00,0.15,3,'',4,'SleevelessShirt.png',3);
insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id,icon,item_type_id) values(41,32,'Ties',70.00,0.15,3,'',4,'SleevelessShirt.png',3);
insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id,icon,item_type_id) values(42,32,'Caps',70.00,0.15,3,'',4,'SleevelessShirt.png',3);
insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id,icon,item_type_id) values(43,32,'T-Shirts / Shirts',70.00,0.15,3,'',4,'SleevelessShirt.png',3);
insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id,icon,item_type_id) values(44,32,'Skirt',110.00,0.15,3,'',4,'SleevelessShirt.png',3);
insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id,icon,item_type_id) values(45,32,'Skirt Pleated',165.00,0.15,3,'',4,'SleevelessShirt.png',3);
insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id,icon,item_type_id) values(46,32,'Dress Short',140.00,0.15,3,'',4,'SleevelessShirt.png',3);
insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id,icon,item_type_id) values(47,32,'Dress Long',210.00,0.15,3,'',4,'SleevelessShirt.png',3);
insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id,icon,item_type_id) values(48,32,'Beret',70.00,0.15,3,'',4,'SleevelessShirt.png',3);

insert into menu_items(id,linked_id,caption,price,vat,menu_level, icon,node_type_id,item_type_id) values(50,30,'Tailoring',0.00,0.00,1,'tailoring.jpg',2,6);
insert into menu_items(id,linked_id,caption,price,vat,menu_level, icon,node_type_id,item_type_id) values(51,30,'Shoe Repair',0.00,0.00,1,'sneaker.png',2,6);
insert into menu_items(id,linked_id,caption,price,vat,menu_level, icon,node_type_id,item_type_id) values(52,30,'Upholstery & Carpet Cleaning',0.00,0.00,1,'apholstery.jpg',2,6);
insert into menu_items(id,linked_id,caption,price,vat,menu_level, icon,node_type_id,item_type_id) values(53,30,'House Call Domesting Cleaning',0.00,0.00,1,'domestic.png',2,6);


insert into menu_items(id,linked_id,caption,price,vat,menu_level, icon,node_type_id,item_type_id) values(54,7,'Blanket Wash (Single Ply)',0.00,0.00,1,'laundry.png',2,1);
insert into menu_items(id,linked_id,caption,price,vat,menu_level, icon,node_type_id,item_type_id) values(55,7,'Blanket Wash (Double Ply)',0.00,0.00,1,'laundry.png',2,1);
insert into menu_items(id,linked_id,caption,price,vat,menu_level, icon,node_type_id,item_type_id) values(56,7,'Blanket Wash (Tripple Ply)',0.00,0.00,1,'laundry.png',2,1);

insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id,icon,item_type_id) values(57,54,'Blanket Wash (Single bed)',120.00,0.15,3,'',4,'laundry.png',2);
insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id,icon,item_type_id) values(58,54,'Blanket Wash (Double bed)',130.00,0.15,3,'',4,'laundry.png',2);
insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id,icon,item_type_id) values(59,54,'Blanket Wash (Queen bed)',140.00,0.15,3,'',4,'laundry.png',2);
insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id,icon,item_type_id) values(60,54,'Blanket Wash (Queen bed)',150.00,0.15,3,'',4,'laundry.png',2);

insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id,icon,item_type_id) values(61,55,'Blanket Wash (Single bed)',160.00,0.15,3,'',4,'laundry.png',2);
insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id,icon,item_type_id) values(62,55,'Blanket Wash (Double bed)',170.00,0.15,3,'',4,'laundry.png',2);
insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id,icon,item_type_id) values(63,55,'Blanket Wash (Queen bed)',180.00,0.15,3,'',4,'laundry.png',2);
insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id,icon,item_type_id) values(64,55,'Blanket Wash (Queen bed)',190.00,0.15,3,'',4,'laundry.png',2);

insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id,icon,item_type_id) values(65,56,'Blanket Wash (Single bed)',200.00,0.15,3,'',4,'laundry.png',2);
insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id,icon,item_type_id) values(66,56,'Blanket Wash (Double bed)',210.00,0.15,3,'',4,'laundry.png',2);
insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id,icon,item_type_id) values(67,56,'Blanket Wash (Queen bed)',220.00,0.15,3,'',4,'laundry.png',2);
insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id,icon,item_type_id) values(68,56,'Blanket Wash (Queen bed)',230.00,0.15,3,'',4,'laundry.png',2);