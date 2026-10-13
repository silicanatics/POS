


##Alter table

select *
from item_purchase_details;

select *
from menu_items;

ALTER TABLE item_purchase_details
  MODIFY items float;


## delete created customers

select *
from customers;

update item_purchases ip
set ip.customer_id = 1
where ip.purchase_type_id = 1;


delete from customers
where id not in (select customer_id from item_purchases)
and id >= 1;


delete from entity_addresses
where entity_id not in (select entity_id from customers )
and id > 2;


delete from entity_contacts
where entity_id not in (select entity_id from customers )
and id > 2;

delete from entities
where id not in (select entity_id from customers )
and id not in (1,2,3,1058,1059);


select *
from entities
select *
from users;



delete menu_items 
from menu_items
where id = 6720;


insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id,item_type_id) values(6720,6721,'SIDE DEFATTED A2 Beef',59.00,15.00,2,'',2,5);

insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id,item_type_id) values(6719,6721,'HINDQUARTER DEFAT A2 BEEF',62.50,15.00,2,'',2,5);

insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id,item_type_id) values(6718,6721,'KNUCKLE CHILLED',74.50,15.00,2,'',2,5);

insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id,item_type_id) values(6717,6721,'CROPS BONELESS CHILLED',67.50,15.00,2,'',2,5);

insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id,item_type_id) values(6716,6721,'LIVERS - APPROX 10KG',33.50,15.00,2,'',2,5);

insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id,item_type_id) values(6715,6721,'CLEAN TRIPE 10KG',77.50,15.00,2,'',2,5);

select *
from quantity_type;

select *
from stock_items;

insert into stock_items(total_quantity,menu_item_id,quantity_type_id)
select 100,id,1
from  menu_items
		where linked_id in (select id
		from  menu_items
		where linked_id in (select id
							from menu_items
							where linked_id in (2211)));



insert into stock_items(total_quantity, menu_item_id,quantity_type_id)
select 200,  id,2
from menu_items
where id between 6715 and 6720;


insert into stock_storage(current, date_captured, end_date, initial_quantity, price, quantity_left, sequence, sold_quantity, start_date, stock_item_id)
select true, '2022-04-30',null,sum(ipd.items),min(mi.price),null,si.id,null,'2022-02-01',si.id
from stock_items si inner join item_purchase_details ipd
on si.menu_item_id = ipd.menu_item_id
inner join menu_items mi
on mi.id = ipd.menu_item_id
group by si.id;


insert into stock_in_use(stock, stock_storage_id, stock_item_id)
select initial_quantity, id, stock_item_id
from stock_storage;



select mi.*
from stock_items si inner join menu_items mi
on mi.id = si.menu_item_id;


select *
from item_purchase_details
where id = 45666;

select *
from stock_items;

select *
from menu_items;

select *
from quantity_type;







insert into quantity_type(id, description) values(1,'Items');
insert into quantity_type(id, description) values(2,'Mass');

SELECT *
from menu_items
where id between 6715 and 6720;

select *
from node_types;

select *
from stock_items;

select *
from purchase_invoices pi
where pi.invoice_date >= '2022-04-24';

select ip.*
from purchase_invoices pi inner join item_purchases ip
on pi.invoice_number = ip.purchase_invoice_id
where pi.invoice_date >= '2022-04-24';

select ipd.*
from purchase_invoices pi inner join item_purchases ip
on pi.invoice_number = ip.purchase_invoice_id
inner join item_purchase_details ipd
on ipd.item_purchase_id = ip.id
where pi.invoice_date >= '2022-04-24';

delete from stock_storage where id >= 0;

select *
from stock_storage;

# id, current, date_captured, end_date, initial_quantity, price, quantity_left, sequence, sold_quantity, start_date, stock_item_id

delete from stock_storage
where id >= 1;




update stock_storage
set quantity_left = 0, sold_quantity = 0, quantity_left = 0
where id >= 1;

select *
from stock_storage;

select *
from stock_items;

select *
from stock_in_use
where stock_item_id in (1);

select *
from item_purchases pi;

select mi.caption, pi.invoice_date, ip.*
from item_purchases ip inner join item_purchase_details ipd
on ip.id = ipd.item_purchase_id
inner join menu_items mi
on ipd.menu_item_id = mi.id
inner join purchase_invoices pi
on pi.invoice_number = ip.purchase_invoice_id
where mi.id = 2358;


select mi.caption, siu.*
from stock_in_use siu inner join stock_items si
on siu.stock_item_id = si.id
inner join menu_items mi
on mi.id = si.menu_item_id
where siu.id = 1;


