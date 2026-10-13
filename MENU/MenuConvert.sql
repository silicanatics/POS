
if OBJECT_ID('Menus') is not null
drop table  Menus
if OBJECT_ID('tempdb..#SubHeading') is not null
drop table  #SubHeading
if OBJECT_ID('tempdb..#DetailedItem') is not null
drop table  #DetailedItem
if OBJECT_ID('tempdb..#headingIDs') is not null
drop table  #headingIDs
if OBJECT_ID('tempdb..#AllBarCodes') is not null
drop table  #AllBarCodes
if OBJECT_ID('tempdb..#dups') is not null
drop table  #dups


go

create table #SubHeading(PrevId int, SubHeadingId int, Caption varchar(50))
create table #DetailedItem(DetailedItemId int, SubheadingId int, Caption varchar(50), Price money, VAT money,  Barcode varchar(30) null)
create table #headingIDs(row int 	IDENTITY(1,1) NOT NULL, id int, menuLevel int)
CREATE TABLE Menus(
	Id int 	IDENTITY(1,1) NOT NULL,
	LinkedId  int NULL,
	Caption varchar(50) NULL,
	Price   money null,
	VAT     money null,
	MenuLevel int null,
	Sequence int null,
	Barcode varchar(30),
	NodeType int null
)
go




select *
from Heading
where Caption like 'SWEE%'



insert into #headingIDs(id , menuLevel) select 12,0
insert into #headingIDs(id , menuLevel) select 80,0
insert into #headingIDs(id , menuLevel) select 13,0
insert into #headingIDs(id , menuLevel) select 16,0
insert into #headingIDs(id , menuLevel) select 5,0
insert into #headingIDs(id , menuLevel) select 6,0
insert into #headingIDs(id , menuLevel) select 7,0
insert into #headingIDs(id , menuLevel) select 8,0
insert into #headingIDs(id , menuLevel) select 9,0
insert into #headingIDs(id , menuLevel) select 11,0
insert into #headingIDs(id , menuLevel) select 10,0
insert into #headingIDs(id , menuLevel) select 15,0
insert into #headingIDs(id , menuLevel) select 14,0
insert into #headingIDs(id , menuLevel) select 17,0
insert into #headingIDs(id , menuLevel) select 1,0
insert into #headingIDs(id , menuLevel) select 2,0
insert into #headingIDs(id , menuLevel) select 63,0
insert into #headingIDs(id , menuLevel) select 3,0
insert into #headingIDs(id , menuLevel) select 19,0
insert into #headingIDs(id , menuLevel) select LinkedHeadingId,1
from HeadingLevel
where HeadingID = 19

insert into #headingIDs(id , menuLevel) select 25,0
insert into #headingIDs(id , menuLevel) select LinkedHeadingId,1
from HeadingLevel
where HeadingID = 25

insert into #headingIDs(id , menuLevel) select 31,0
insert into #headingIDs(id , menuLevel) select LinkedHeadingId,1
from HeadingLevel
where HeadingID = 31

insert into #headingIDs(id , menuLevel) select 18,0

insert into #headingIDs select 26,0
insert into #headingIDs(id , menuLevel) select LinkedHeadingId,1
from HeadingLevel
where HeadingID = 26

insert into #headingIDs(id , menuLevel) select 72,0
insert into #headingIDs(id , menuLevel) select 73,0
insert into #headingIDs(id , menuLevel) select 76,0
insert into #headingIDs(id , menuLevel) select 81,0
insert into #headingIDs(id , menuLevel) select 77,0
insert into #headingIDs(id , menuLevel) select 82,0
insert into #headingIDs(id , menuLevel) select 78,0
insert into #headingIDs(id , menuLevel) select 64,-1
insert into #headingIDs(id , menuLevel) select 65,-1





declare @HeadingID int
declare @menulvlId int
declare @subId int
declare @sequence int
declare @nodeType int
declare @MenuLevel int

select @menulvlId = null  --Important to be out of the while loop.

while exists (select 1 from #headingIDs)
begin

select @sequence = row, @HeadingID = id, @MenuLevel = menuLevel
from #headingIDs
where row = (select min(row) from #headingIDs)


	insert into #SubHeading
	select distinct @menulvlId, SubHeadingID, Caption
	from SubHeading
	where HeadingID =  @HeadingID

	/**
	select @nodeType = 1
	select @MenuLevel = 0
	if (@menulvlId is not null)
	begin
	    select @nodeType = 2
		select @MenuLevel = 1
	end
	**/

	/**
	insert into Menus
	select @menulvlId,h.Caption, null,null,MenuLevel = 1, @sequence ,null,NodeType = 2
	from Heading h 
	where h.HeadingID = @HeadingID
	and exists (select 1 from SubHeading sh where h.HeadingID = sh.HeadingID)
	**/

	insert into Menus
	select @menulvlId,h.Caption, null,null, MenuLevel = @MenuLevel, @sequence ,null,NodeType = 1
	from Heading h 
	where h.HeadingID = @HeadingID
	--and not exists (select 1 from SubHeading sh where h.HeadingID = sh.HeadingID)

	select @menulvlId = @@IDENTITY

	/****
	update #headingIDs
	set linkedID =  @menulvlId 
	from #headingIDs h inner join HeadingLevel hl
	on h.id = hl.LinkedHeadingId
	where hl.HeadingID = @HeadingID
	***/

	 while exists(select 1 from #SubHeading)
		begin

			insert into #DetailedItem
			select di.DetailedItemID, di.SubHeadingID, di.Caption, di.Price, di.VAT, di.BarCode
			from DetailedItem di inner join #SubHeading sh
			on di.SubHeadingID = sh.SubHeadingID
			where sh.SubHeadingId = (select min(SubHeadingId) from #SubHeading)

		   insert into Menus
		   select @menulvlId,Caption, null, null, 2,@sequence,null,3
		   from #SubHeading
		   where SubHeadingId = (select min(SubHeadingId) from #SubHeading)
		   select @subId = @@IDENTITY

		   delete #SubHeading
		   where SubHeadingId = (select min(SubHeadingId) from #SubHeading)

		      while exists(select 1 from #DetailedItem)
			   begin
					insert into Menus
					select @subId,Caption, Price, VAT, 3,@sequence,BarCode,4
					from #DetailedItem
					where DetailedItemId = (select min(DetailedItemId) from #DetailedItem)

					delete #DetailedItem
					where DetailedItemId = (select min(DetailedItemId) from #DetailedItem)
			   end

           select @menulvlId = null

		 end

		 
	delete #headingIDs
		where row = (select min(row) from #headingIDs)

									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
								  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1

									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
								  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1

									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
								  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1

									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
								  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1

									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
								  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1

									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
									  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1
								  	insert into Menus
	select -1,'****************', -1, -1,-1,0,'********************',1

end


		

go

select *
into #AllBarCodes
from DetailedItem di
where isnull(di.BarCode,'') != ''
and isnull(di.BarCode,'') != '0'
a


select Barcode, cnt= count(*)
into #dups
from  Menus
where  isnull(BarCode,'') != ''
and price != -1
group by Barcode
having count(*) > 1




delete #AllBarCodes
from #AllBarCodes a, Menus m
where a.BarCode = m.Barcode


select * from Menus


insert into Menus
select 5517,Caption, Price, VAT, 3,100,BarCode,4
from #AllBarCodes



select 'insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id) values(' + convert(varchar(10),id)  + ',' + 
convert(varchar(10),isnull(LinkedId,0))  + ',"' + 
Caption + '",' + 
convert(varchar(10),isnull(Price,0)) + ','+
convert(varchar(10),isnull(VAT,0)) + ','+
convert(varchar(10),MenuLevel)+ ',"'+
convert(varchar(20),isnull(Barcode,''))+
'",' + convert(varchar(10),NodeType) + ');'
from Menus
order by Id, LinkedId

go