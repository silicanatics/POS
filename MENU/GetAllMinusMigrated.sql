
if OBJECT_ID('Menus') is not null
drop table  Menus
if OBJECT_ID('tempdb..#SubHeading') is not null
drop table  #SubHeading
if OBJECT_ID('tempdb..#DetailedItem') is not null
drop table  #DetailedItem
if OBJECT_ID('tempdb..#headingIDs') is not null
drop table  #headingIDs

go

create table #SubHeading(PrevId int, SubHeadingId int, Caption varchar(50))
create table #DetailedItem(DetailedItemId int, SubheadingId int, Caption varchar(50), Price money, VAT money,  Barcode varchar(20) null)
create table #headingIDs(row int 	IDENTITY(1,1) NOT NULL, id int, menuLevel int)
CREATE TABLE Menus(
	Id int 	IDENTITY(1,1) NOT NULL,
	LinkedId  int NULL,
	Caption varchar(50) NULL,
	Price   money null,
	VAT     money null,
	MenuLevel int null,
	Sequence int null,
	Barcode varchar(20),
	NodeType int null
)
go





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

end


		 select * from Menus

go

select 'insert into menu_items(id,linked_id,caption,price,vat,menu_level, barcode,node_type_id) values(' + convert(varchar(10),id)  + ',' + 
convert(varchar(10),isnull(LinkedId,0))  + ',"' + 
Caption + '",' + 
convert(varchar(10),isnull(Price,0)) + ','+
convert(varchar(10),isnull(VAT,0)) + ','+
convert(varchar(10),MenuLevel)+ ',"'+
convert(varchar(10),isnull(Barcode,''))+
'",' + convert(varchar(10),NodeType) + ');'
from Menus
order by Id, LinkedId

go