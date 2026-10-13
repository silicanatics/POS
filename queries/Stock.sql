


select ip.amount_tendered - ip.amount - ip.change_ as test,
pi.invoice_date,
		 pt.description,
        mi.caption,
        mi.price,
        pi.purchase_number,
        pi.invoice_number
		
from item_purchases ip inner join item_purchase_details ipd on ip.id = ipd.item_purchase_id
     inner join menu_items mi on mi.id = ipd.menu_item_id
     inner join payment_type pt on pt.id = ip.payment_type_id
     inner join purchase_invoices pi on pi.invoice_number = ip.purchase_invoice_id
where abs(ip.amount_tendered - ip.amount - ip.change_) > 0.1
order by   pi.invoice_number, pi.purchase_number

select *
from item_purchases

select *
from menu_items
where id in (5516,6721, 2211);

select *
from menu_items
where linked_id in (5516,6721);


select *
from stock_items;

insert into stock_items(stock,menu_item_id)
select 100,id
from  menu_items
		where linked_id in (select id
		from  menu_items
		where linked_id in (select id
							from menu_items
							where linked_id in (2211)));
