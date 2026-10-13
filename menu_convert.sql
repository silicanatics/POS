/**
select *
into Heading_
from Heading
select *
into SubHeading_
from SubHeading
select *
into DetailedItem_
from DetailedItem
select *
into HeadingLevel_
from HeadingLevel
***/

if OBJECT_ID('tempdb..#Heading') is not null
drop table  #Heading
if OBJECT_ID('tempdb..#MenuData') is not null
drop table  #MenuData
if OBJECT_ID('tempdb..#SubHeading') is not null
drop table  #SubHeading
if OBJECT_ID('tempdb..#HeadingLevel') is not null
drop table  #HeadingLevel
if OBJECT_ID('tempdb..#DetailedItem') is not null
drop table  #DetailedItem
if OBJECT_ID('tempdb..#HeadingLevel') is not null
drop table  #HeadingLevel




if OBJECT_ID('Menus') is not null
drop table  Menus

create table #DetailedItem(DetailedItemId int, SubheadingId int, Caption varchar(50), Price money, Barcode varchar(20) null)
create table #SubHeading(SubHeadingId int, Caption varchar(50))
create table #Heading(HeadingId int, Caption varchar(50))
create table #HeadingLevel(HeadingLevelId int, HeadingId int, LinkedHeadingId int, MenuLevel int, HeadingIdCaption varchar(50), LinkedHeadingIdCaption varchar(50))

CREATE TABLE Menus(
	Id int 	IDENTITY(1,1) NOT NULL,
	LinkedId  int NULL,
	Caption varchar(50) NULL,
	Price   money null,
	MenuLevel int null,
	Sequence int null,
	Barcode varchar(20)
)
go


declare @headingId int
declare @subHeadingId int
declare @minHeadinId int
declare @HeadingCaption varchar(50)
declare @MenuLevel int





select h.HeadingID,
	   HeadingCaption =  h.Caption,
	   sh.SubHeadingID,
	   SubHeadingCaption =  sh.Caption,
	   di.DetailedItemID,
	   DetaileItemCaption =  di.Caption,
	   di.Price,
	   di.BarCode,
	   h.Sequence
into #MenuData
from Heading h inner join SubHeading sh
on h.HeadingID = sh.HeadingID
inner join DetailedItem di
on di.SubHeadingID = sh.SubHeadingID
order by h.HeadingID, sh.SubHeadingID




declare @subId int
declare @menulvlId int

while exists (select 1 from #MenuData)
begin

--	insert into #Heading
--	select distinct HeadingID, HeadingCaption
--	from #MenuData
--	where HeadingID = (select min(HeadingID) from #MenuData)

	insert into #HeadingLevel
	select hl1.HeadingLevelId, hl1.HeadingId , hl1.LinkedHeadingId , hl1.MenuLevel, h1.Caption, h2.Caption
	from HeadingLevel hl1 inner join Heading h1 on hl1.HeadingId = h1.HeadingID
	left join Heading h2 on hl1.LinkedHeadingId = h2.HeadingID
	where hl1.HeadingId = (select min(HeadingID) from #MenuData)


	insert into #SubHeading
	select distinct SubHeadingID, SubHeadingCaption
	from #MenuData
	where HeadingID in (select min(HeadingID) from #MenuData)
		
	insert into #DetailedItem
	select di.DetailedItemID, di.SubHeadingID, di.Caption, di.Price, di.BarCode
	from DetailedItem di inner join #SubHeading sh
	on di.SubHeadingID = sh.SubHeadingID

	insert into Menus
	select null,HeadingIdCaption, null, MenuLevel, null,null
	from #HeadingLevel
	where LinkedHeadingId = 0
		
	select @menulvlId = @@IDENTITY

	while exists (select 1 from #HeadingLevel)
	begin

	insert into Menus
	select @menulvlId,LinkedHeadingIdCaption, null, MenuLevel, null,null
	from #HeadingLevel
	where LinkedHeadingId > 0
	and HeadingLevelId = (select min(HeadingLevelId) from #HeadingLevel)

	select @menulvlId = @@IDENTITY

	 while exists(select 1 from #SubHeading)
		begin
		   insert into Menus
		   select @menulvlId,Caption, null, 2,null,null
		   from #SubHeading
		   where SubHeadingId = (select min(SubHeadingId) from #SubHeading)

		   delete #SubHeading
		   where SubHeadingId = (select min(SubHeadingId) from #SubHeading)

		   select @subId = @@IDENTITY
			   while exists(select 1 from #DetailedItem)
			   begin
					insert into Menus
					select @subId,Caption, Price, 3,null,BarCode
					from #DetailedItem
					where DetailedItemId = (select min(DetailedItemId) from #DetailedItem)

					delete #DetailedItem
					where DetailedItemId = (select min(DetailedItemId) from #DetailedItem)
			   end

		end
	delete #HeadingLevel
	where HeadingLevelId = (select min(HeadingLevelId) from #HeadingLevel)
	end

	
  delete #MenuData
  where HeadingID = (select min(HeadingID) from #MenuData)

  truncate table #Heading
  truncate table #SubHeading
  truncate table #DetailedItem
  truncate table #HeadingLevel
end


select *
from Menus
order by Id, LinkedId

--where lower(Caption) like '%gro%'

