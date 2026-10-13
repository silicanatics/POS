insert into purchases_discounts (id, discount_percent, threashold_amount) values(1,10,1000);
insert into applications(id, name, menu_tile_cols, menu_tile_row_height) values(3,'tulipgardencentre',4,'11vh');

insert into printer_destinations(id,default_printer,destination) values(1,'XP-80','kitchen');
insert into printer_destinations(id,default_printer,destination) values(2,'XP-80','client');
insert into printer_destinations(id,default_printer,destination) values(3,'XP-80','pizzaOven');

insert into payment_type(id, description) values(1,'cash');
insert into payment_type(id, description) values(2,'card');

insert into quantity_types(id, description) values(1,'Items');
insert into quantity_types(id, description) values(2,'Mass');

insert into item_options(id,description,price) values(1,'Iron',20);
insert into item_options(id,description,price) values(2,'Fold',5);
insert into item_options(id,description,price) values(3,'Stay Soft',2);


insert into machine_printer(id,ip_address,machine_name,printer_name,payment_type_id) values (1,'192.168.8.120','XiCenMthembiN','XP-80C',1);
insert into machine_printer(id,ip_address,machine_name,printer_name,payment_type_id) values (2,'192.168.8.120','XiCenMthembiN','XP-80C',2);



insert into purchase_invoices (invoice_date,invoice_date_time,purchase_number) values (curdate(), SYSDATE(), 0)

insert into entity_types(description) values ('Unknown');
insert into entity_types(description) values ('Person');
insert into entity_types(description) values ('User');
insert into entity_types(description) values ('Customer');
insert into entity_types(description) values ('Company');
insert into entity_types(description) values ('Garden Center');
insert into entity_types(description) values ('School');
insert into entity_types(description) values ('Church');
insert into entity_types(description) values ('WholeSaler');
insert into entity_types(description) values ('Town House');
insert into entity_types(description) values ('House');

insert into purchase_type(id,description) values (1,'Walk In');
insert into purchase_type(id,description) values (2,'Collection');
insert into purchase_type(id,description) values (3,'Delivery');
insert into purchase_state(id,description) values (1,'Incomplete');
insert into purchase_state(id,description) values  (2,'Complete');

insert into contact_types (id, description) values (1,'Cell');
insert into contact_types (id, description) values (2,'E-Mail');
insert into contact_types (id, description) values (3,'Office');

insert into entities(id, name, surname,entity_type_id,initials) values (1,'Till1','Till1',3,'');
insert into entities(id, name, surname,entity_type_id,initials) values (2,'Laundromat','Daniel',3,'s');
insert into entities(id, name, surname,entity_type_id,initials) values (3,'WalkIn ','WalkIn ',4,'');


insert into users(id,user_id, username,password,entity_id,enabled) values(1,'','shahiem','{bcrypt}$2a$12$VXVYKzB2lbBwfmPC8pUh4ewUkFbUJRYGp3G0nyEksqY8BP0vwc/dK',2,true);

insert into roles(id,role) values (1,'ROLE_ADMIN');
insert into roles(id,role) values (2,'ROLE_TILL_OPERATOR');

insert into user_roles(user_id,role_id) values (1,1);
insert into user_roles(user_id,role_id) values (1,2);

insert into customers(customer_id, entity_id) values ( 'WalkIn ',3);



insert into entity_contacts(contact,contact_type_id,entity_id) values ('0876545678',1,1);
insert into entity_contacts(contact,contact_type_id,entity_id) values ('0836558434',1,2);


insert into entity_addresses(address_text, entity_id) values ('Shop 1, Str1 & Str2, Mondeo',1);
insert into entity_addresses(address_text, entity_id) values ('44 protea ave, alan manor',2);

 
update entity_addresses set postal_code = 0 where id > 0
 
 
insert into user_printers(printer_name,printer_dest_id,user_id) values('XP-80',1,1);
insert into user_printers(printer_name,printer_dest_id,user_id) values('XP-80',2,1);

insert into client_printers(ip_address, printer_name, printer_dest_id) values('192.168.8.120','XP-80', 1);
insert into client_printers(ip_address, printer_name, printer_dest_id) values('192.168.8.120','XP-80', 2);