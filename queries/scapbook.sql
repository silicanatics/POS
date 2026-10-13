select *
from stock_items;

select *
from purchase_invoices;


select *
from purchase_invoices pi inner join item_purchases ip
on pi.invoice_number = ip.purchase_invoice_id
where pi.invoice_date = '2022-05-12';

select ipd.*
from purchase_invoices pi inner join item_purchases ip
on pi.invoice_number = ip.purchase_invoice_id
inner join item_purchase_details ipd
on ipd.item_purchase_id = ip.id
where pi.invoice_date = '2022-05-12';


select *
from stock_items;

select *
from menu_items;