-- =============================================
-- Author:   ���ۺ�
-- Create date: 2015-11-13
-- Description:	��ֵת��Ϊ�۱���
-- =============================================
--DROP FUNCTION [dbo].[UnitChangeRate]
--GO

CREATE FUNCTION UnitChangeRate(@soruceid int,@descid int)
RETURNS  decimal(18,10) 
AS
BEGIN
	
	-- Դ
	declare @SStandardUnitID int;
	declare @SStandardRatio decimal(18,10);
	-- Ŀ��
	declare @DStandardUnitID int;
	declare @DStandardRatio decimal(18,10);

	-- Դ��ֵ
	select @SStandardUnitID=StandardUnitID,@SStandardRatio=StandardRatio from MeasureUnit where  ID=@soruceid;

	-- Ŀ�ĸ�ֵ
	select @DStandardUnitID=StandardUnitID,@DStandardRatio=StandardRatio from MeasureUnit where  ID=@descid;

	-- �Ƚ� 
	declare @resultvalue decimal(18,10);
	if @SStandardUnitID=@DStandardUnitID
	begin
	   set @resultvalue=@SStandardRatio/@DStandardRatio;
	end
	else
	begin
	  set @resultvalue=-1;
	end
	--����ֵ
	RETURN  @resultvalue;

END
GO

-- =============================================
-- Author:		���ۺ�
-- Create date: 2015-11-13
-- Description:	ϵͳͳһ�����Ϊ�ֱ�׼ú��tce��
-- =============================================
--DROP FUNCTION [dbo].[RealToTce]
--GO

CREATE FUNCTION [dbo].[RealToTce] (@energynum decimal(30,2),@pointid int)
	RETURNS  decimal(30,10) 
	--@resultvalue decimal(30,10) output
AS
BEGIN
   
    declare @resultvalue decimal(30,10);
	-- ��׼��λ
	declare @StandardNumID  int;
	declare @StandardDenID  int;
	declare @UnitID         int;
	declare @StandardDegree decimal(10, 4);
    --declare @info   varchar(200);
	
SELECT   @StandardNumID =d.StandardNumID, @StandardDenID =d.StandardDenID, @UnitID=d.UnitID, @StandardDegree=c.StandardDegree
FROM      dbo.CollectPoint AS a INNER JOIN
                dbo.EntEnergy AS b ON a.EnergyID = b.ID INNER JOIN
                dbo.EnergyValue AS c ON b.ID = c.EnergyID INNER JOIN
                dbo.Energy AS d ON b.EnergyID = d.ID
WHERE a.ID=@pointid and  c.StartDate <= GETDATE()  AND c.EndDate = '1900-01-01 00:00:00';
	
	-- ʹ�õ��۱�ϵ��������
	if @StandardDegree is  null 
	begin
	      --set @info='pointID:'+@pointid+';'+'energynum:'+@energynum;
	      --print @info;
		 
		 --insert into SumLog(pointID,energynum) values(@pointid,@energynum);
		return -1;
	end
    -- ��׼ϵ������ϵ��������
	if @StandardNumID is null
	    return -2;
    -- �õ�����ϵ��
	declare @numerator decimal(18,10);
	-- ��ĸϵ��
	declare @denominator decimal(18,10);

	set @numerator=[dbo].[UnitChangeRate](@StandardNumID,22);
	set @denominator=[dbo].[UnitChangeRate](@StandardDenID,@UnitID);

	select @resultvalue = @StandardDegree*@energynum*@numerator/@denominator;
	
	return @resultvalue;
	
END
GO

-- =============================================
-- Author:		���ۺ�
-- Create date: 2015-11-14
-- Description:	��¼���۱�ϵ�������ڻ��ѹ��ڵ�����£�ʵ�������ɼ����ֵ����¼�������С�
-- =============================================
/*CREATE TABLE [dbo].[SumLog](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[pointID] [int] NULL,
	[createtime] [datetime] NULL,
	[energynum] [decimal](30, 2) NULL,
 CONSTRAINT [PK_SumLog] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[SumLog] ADD  CONSTRAINT [DF_SumLog_createtime]  DEFAULT (getdate()) FOR [createtime]
GO
*/

-- =============================================
-- Author:		���ۺ�
-- Create date: 2015-11-14
-- Description:	���ݻ���
-- =============================================
--DROP PROCEDURE [dbo].[SumCollectValue]
--GO

CREATE PROCEDURE SumCollectValue 
	@SumDim  int
AS
BEGIN
     --ʹ��ǰ׼���Ļ���ѭ������
     declare @i int ---���ѭ������ 
     declare @count int ---��ű�����ݼ�¼�� 

	 --�������������
	 declare @RealValue decimal(30,3);
	 declare @StandardValue decimal(30,3);
	 declare @EntityAdjValue decimal(30,2);--����ʵ����
	 declare @StandardAdjValue decimal(30,3);--�����۱���
	 declare @BusinessTime varchar(10);
	 declare @TimeDim   int;
	 declare @MPointID  int;
	 --��ʽ������
     declare @StandardTime datetime;
	 --���ݿ����еļ�¼����,Ϊ��ȷ���Ǹ��»���������¼
	 declare @RowsInTable int;

	 ---������ʱ�� 
	 create table #TempValue(IntID int identity(1,1), RealValue decimal(30,3),StandardValue decimal(30,3),BusinessTime varchar(10),TimeDim int,MPointID int); 
	 
	 if @SumDim=3 
	 begin
	   --��ֵд����ʱ��
	 insert into #TempValue(RealValue,StandardValue,BusinessTime,TimeDim,MPointID) select  SUM( CASE WHEN c.IsAdd = 1 THEN a.CollectValue ELSE a.CollectValue * - 1 END) RealValue,SUM( CASE WHEN c.IsAdd = 1 THEN  dbo.[RealToTce](a.CollectValue,a.PointID) ELSE dbo.[RealToTce](a.CollectValue,a.PointID) * - 1 END) StandardValue, substring(convert(varchar(10),a.BusinessTime,120),1,7), a.TimeDim, 
               c.MPointID FROM   CollectValue AS a INNER JOIN CollectPoint AS b ON a.PointID = b.ID INNER JOIN  MeasureCollect AS c ON b.ID = c.CPointID where a.TimeDim=@SumDim  and b.ValueKind=10
       group by MPointID,TimeDim,substring(convert(varchar(10),BusinessTime,120),1,7)
	   
	   -- 
	 end
	 
	 if @SumDim=4 
	 begin
	   --��ֵд����ʱ��
	 insert into #TempValue(RealValue,StandardValue,BusinessTime,TimeDim,MPointID) select  SUM( CASE WHEN c.IsAdd = 1 THEN a.CollectValue ELSE a.CollectValue * - 1 END) RealValue,SUM( CASE WHEN c.IsAdd = 1 THEN  dbo.[RealToTce](a.CollectValue,a.PointID) ELSE dbo.[RealToTce](a.CollectValue,a.PointID) * - 1 END) StandardValue, substring(convert(varchar(10),a.BusinessTime,120),1,10), a.TimeDim, 
               c.MPointID FROM   CollectValue AS a INNER JOIN CollectPoint AS b ON a.PointID = b.ID INNER JOIN  MeasureCollect AS c ON b.ID = c.CPointID where a.TimeDim=@SumDim  and b.ValueKind=10
       group by MPointID,TimeDim,substring(convert(varchar(10),BusinessTime,120),1,10)
	 end

	select @count=COUNT(1) from #TempValue;
    begin try 
	if @count>0
	begin
	 set @i=1;
	 while (@i<=@count)
	 begin
	    select @RealValue=RealValue,@StandardValue=StandardValue,@BusinessTime=BusinessTime,@TimeDim=TimeDim,@MPointID=MPointID from #TempValue where IntID=@i;
		-- ��
		if @TimeDim=3 
		begin
		   --��ʽ������
		   set @StandardTime=@BusinessTime+'-01'
		   --����Ƿ�����Ƿ�������
		   select @RowsInTable=count(1) from MeasurePointValueMonth where MPointID=@MPointID and SumDim=@StandardTime;
		   if @RowsInTable>0
		   begin
		     update MeasurePointValueMonth set EntityValue=@RealValue,StandardValue=@StandardValue,EntityAdjValue=@RealValue,@StandardAdjValue=@StandardValue,isSum=0,EntisSum=0  where MPointID=@MPointID and SumDim=@StandardTime;
		   end
		   else
		   begin
		     insert into MeasurePointValueMonth(MPointID,SumDim,EntityValue,StandardValue,EntityAdjValue,StandardAdjValue,isSum,EntisSum) values(@MPointID,@StandardTime,@RealValue,@StandardValue,@RealValue,@StandardValue,0,0);
		   end
		   --����ֵ���еı�ʾ
		   --update CollectValue  set isSum=1 from CollectValue t where t.PointID in (select CPointID from MeasureCollect where MPointID=@MPointID);  
		end

		-- ��
		if @TimeDim=4 
		begin
		   set @StandardTime=@BusinessTime
		    --����Ƿ�����Ƿ�������
		   select @RowsInTable=count(1) from MeasurePointValueMonth where MPointID=@MPointID and SumDim=@StandardTime;
		   if @RowsInTable>0
		   begin
		   update MeasurePointValueDay  set EntityValue=@RealValue,StandardValue=@StandardValue,EntityAdjValue=@RealValue,@StandardAdjValue=@StandardValue,isSum=0,EntisSum=0  where MPointID=@MPointID and SumDim=@StandardTime;
		   end
		   else
		   begin
		   insert into MeasurePointValueDay(MPointID,SumDim,EntityValue,StandardValue,EntityAdjValue,StandardAdjValue,isSum,EntisSum) values(@MPointID,@BusinessTime,@RealValue,@StandardValue,@RealValue,@StandardValue,0,0);
		   end
		   --����ֵ���еı�ʾ
		   --update CollectValue  set isSum=1 from CollectValue t where t.PointID in (select CPointID from MeasureCollect where MPointID=@MPointID);  
		end

		select @i = @i +1 --ѭ������ 
		
	 end
	end
	
	end try
	begin catch 
     --set @rtn=@@ERROR 
         
        --������Ϣ 
        select ERROR_LINE() as Line, 
            ERROR_MESSAGE() as message1, 
           ERROR_NUMBER() as number, 
            ERROR_PROCEDURE() as proc1, 
            ERROR_SEVERITY() as severity, 
            ERROR_STATE() as state1 
    end catch 
	
	drop table #TempValue---ɾ����ʱ�� 

END
GO

-- =============================================
-- Author:		���ۺ�
-- Create date: 2015-11-14
-- Description:	���ѻ��ܵı��У����ݻ��ܵ��ϼ���λ�����¡���ȣ�
-- =============================================
--DROP PROCEDURE [dbo].[SumCollectValueFromSum]
--GO

CREATE PROCEDURE SumCollectValueFromSum 
-- �������Ϊ3����ʾ���±�����������
-- �������Ϊ4: ��ʾ���ձ����±������

	@TimeDim  int
AS
BEGIN
     --ʹ��ǰ׼���Ļ���ѭ������
     declare @i int ---���ѭ������ 
     declare @count int ---��ű�����ݼ�¼�� 

	 --�������������
	 declare @EntityValue decimal(30,2);
	 declare @StandardValue decimal(30,4);
	 declare @EntityAdjValue decimal(30,2);
	 declare @StandardAdjValue decimal(30,4);
	 declare @isSum  bit;
	 declare @EntisSum bit;
	 
	 declare @SumDim varchar(10);
	 declare @MPointID  int;
     --��ʽ������
     declare @StandardTime datetime;
	 --�����Ѵ��ڵļ�¼����
	 declare @TableRows int;

	 ---������ʱ�� 
	 create table #TempValue(IntID int identity(1,1), EntityValue decimal(30,2),StandardValue decimal(30,4),EntityAdjValue decimal(30,2),StandardAdjValue decimal(30,4),SumDim varchar(10),MPointID int,isSum bit,EntisSum bit); 
	 --��ֵд����ʱ��
	 --�±�
	 if @TimeDim=3 
	 begin
	   insert into #TempValue(EntityValue,StandardValue,EntityAdjValue,StandardAdjValue,SumDim,MPointID,isSum,EntisSum) select  sum(EntityValue),sum(StandardValue),sum(EntityAdjValue),sum(StandardAdjValue),substring(convert(varchar(10),SumDim,120),1,4),MPointID,isSum,EntisSum FROM MeasurePointValueMonth  where isSum=0 or EntisSum=0
       group by MPointID,substring(convert(varchar(10),SumDim,120),1,4),isSum,EntisSum
	 end
	 -- �ձ�
	 if @TimeDim=4
	 begin
	    insert into #TempValue(EntityValue,StandardValue,EntityAdjValue,StandardAdjValue,SumDim,MPointID,isSum,EntisSum) select  sum(EntityValue),sum(StandardValue),sum(EntityAdjValue),sum(StandardAdjValue),substring(convert(varchar(10),SumDim,120),1,7),MPointID,isSum,EntisSum FROM MeasurePointValueDay  where isSum=0 or EntisSum=0
       group by MPointID,substring(convert(varchar(10),SumDim,120),1,7),isSum,EntisSum
	 end
	 

	select @count=COUNT(1) from #TempValue;
    begin try 
	if @count>0
	begin
	 set @i=1;
	 while (@i<=@count)
	 begin
	    select @EntityValue=EntityValue,@StandardValue=StandardValue,@EntityAdjValue=EntityAdjValue,@StandardAdjValue=StandardAdjValue,@SumDim=SumDim,@MPointID=MPointID,@isSum=isSum,@EntisSum=EntisSum from #TempValue where IntID=@i;
		-- ��
		if @TimeDim=3 
		begin
		   set @StandardTime=@SumDim+'-01-01'
		   select @TableRows= count(1) from MeasurePointValueYear where MPointID=@MPointID and SumDim=@StandardTime ;
		   -- �������д��ڴ˼�¼
		   if @TableRows >0
		   begin
		     if @isSum=0 
			 begin
			   if @EntisSum=0 
			   begin
			     update MeasurePointValueYear set EntityValue=@EntityValue,StandardValue=@StandardValue,EntityAdjValue=@EntityAdjValue,StandardAdjValue=@StandardAdjValue where MPointID=@MPointID and SumDim=@StandardTime ;
			   end
			   else
			   begin
			     update MeasurePointValueYear set EntityValue=EntityValue+@EntityValue,StandardValue=StandardValue+@StandardValue where MPointID=@MPointID and SumDim=@StandardTime;
			   end
			 end
			 else
			 begin
			    if @EntisSum=0 
				 begin
			     update MeasurePointValueYear set EntityAdjValue=EntityAdjValue+@EntityAdjValue,StandardAdjValue=StandardAdjValue+@StandardAdjValue where MPointID=@MPointID and SumDim=@StandardTime;
			     end
			 end
		   end
		   else
		   --����
		   begin
		   insert into MeasurePointValueYear(MPointID,SumDim,EntityValue,StandardValue,EntityAdjValue,StandardAdjValue) values(@MPointID,@StandardTime,@EntityValue,@StandardValue,@EntityValue,@StandardValue);
		   end
		    --���±�ʾ
		     if @isSum=0 
			 begin
			   if @EntisSum=0 
			   begin
			     update MeasurePointValueMonth  set isSum=1,EntisSum=1 from MeasurePointValueMonth t where  MPointID=@MPointID and substring(convert(varchar(10),SumDim,120),1,4)=@SumDim and isSum=@isSum and EntisSum=@EntisSum; 
			   end
			   else
			   begin
			     update MeasurePointValueMonth  set isSum=1 from MeasurePointValueMonth t where  MPointID=@MPointID and substring(convert(varchar(10),SumDim,120),1,4)=@SumDim and isSum=@isSum and EntisSum=@EntisSum; 
			   end
			 end
			 else
			 begin
			    if @EntisSum=0 
				begin
			     update MeasurePointValueMonth  set EntisSum=1 from MeasurePointValueMonth t where  MPointID=@MPointID and substring(convert(varchar(10),SumDim,120),1,4)=@SumDim and isSum=@isSum and EntisSum=@EntisSum; 
			    end
			 end
		   end
		end

		-- ��
		if @TimeDim=4 
		begin
		  set @StandardTime=@SumDim+'-01'
		  select @TableRows= count(1) from MeasurePointValueMonth where MPointID=@MPointID and SumDim=@StandardTime;
		  if @TableRows >0
		   begin
		     if @isSum=0
			 begin
			   if @EntisSum=0 
			   begin
			    update MeasurePointValueMonth set EntityValue=EntityValue+@EntityValue,StandardValue=StandardValue+@StandardValue,EntityAdjValue=EntityAdjValue+@EntityAdjValue,StandardAdjValue=StandardAdjValue+@StandardAdjValue,isSum=0,EntisSum=0 where MPointID=@MPointID and SumDim=@StandardTime and isSum=@isSum and EntisSum=@EntisSum; 
			   end
			   else
			   begin
			      update MeasurePointValueMonth set EntityValue=EntityValue+@EntityValue,StandardValue=StandardValue+@StandardValue,isSum=0 where MPointID=@MPointID and SumDim=@StandardTime and isSum=@isSum and EntisSum=@EntisSum; 
			   end
			 end
			 else
			 begin
			   if @EntisSum=0 
			   begin
			     update MeasurePointValueMonth set EntityAdjValue=EntityAdjValue+@EntityAdjValue,StandardAdjValue=StandardAdjValue+@StandardAdjValue,EntisSum=0 where MPointID=@MPointID and SumDim=@StandardTime and isSum=@isSum and EntisSum=@EntisSum; 
			   end
			 end
		   end
		   else
		   begin
		    insert into MeasurePointValueMonth(MPointID,SumDim,EntityValue,StandardValue,EntityAdjValue,StandardAdjValue,isSum,EntisSum) values(@MPointID,@StandardTime,@EntityValue,@StandardValue,@EntityValue,@StandardValue,0,0);
		   end
		   --����ֵ���еı�ʾ
		   if @isSum=0 
		   begin
		     if @EntisSum=0
			 begin
			   update MeasurePointValueDay  set isSum=1,EntisSum=1 from MeasurePointValueDay t where  MPointID=@MPointID and substring(convert(varchar(10),SumDim,120),1,7)=@SumDim and isSum=@isSum and EntisSum=@EntisSum;  
			 end
			 else
			 begin
			   update MeasurePointValueDay  set isSum=1 from MeasurePointValueDay t where  MPointID=@MPointID and substring(convert(varchar(10),SumDim,120),1,7)=@SumDim and isSum=@isSum and EntisSum=@EntisSum;  
			 end
		   end
		   else
		   begin
		      if @EntisSum=0 
		      begin
		      update MeasurePointValueDay  set EntisSum=1 from MeasurePointValueDay t where  MPointID=@MPointID and substring(convert(varchar(10),SumDim,120),1,7)=@SumDim and isSum=@isSum and EntisSum=@EntisSum;  
		      end
		   end
		  
		end
		select @i = @i +1 --ѭ������ 
	 end
	 
	 end try
	begin catch 
     --set @rtn=@@ERROR 
         
        --������Ϣ 
        select ERROR_LINE() as Line, 
            ERROR_MESSAGE() as message1, 
           ERROR_NUMBER() as number, 
            ERROR_PROCEDURE() as proc1, 
            ERROR_SEVERITY() as severity, 
            ERROR_STATE() as state1 
    end catch 
	--end
	drop table #TempValue---ɾ����ʱ�� 

END
GO






-- =============================================
-- Author:		���ۺ�
-- Create date: 2015-11-22
-- Description:	�޸�app��web�����е�sql��䵽�洢������
-- =============================================
--DROP PROCEDURE [dbo].[SumCollectValueFromSum]
--GO
CREATE PROCEDURE InsertCollectValue 
   @RWBH   int,
	@CJDBH  int,	
	@CJSJ   varchar(20),
	@RealValue   decimal(30,2),
	@CJZ      decimal(30,4),
	@ReValue int output

AS
BEGIN
     /*<һ>ʹ�ô洢����ǰ׼��������*/
	 --ά�����
     declare @BusinessTime  datetime ---ҵ��ʱ�� 
     declare @TimeDim int ---ҵ��ά�� 

	 -- ɨ��ֵ���
	 declare @OldValue  decimal(30,2);
	 declare @NewValue  decimal(30,2);
	 -- �������
	 declare @TaskID     int;
	 -- �۱���ʷά�����
     declare @StandardDegree decimal(10, 4);
	 -- �ɼ�ֵ����
	 declare @valuekind  int;
	 -- 
	 /*�������ݶ���*/

	 /*<��>�Ϸ��Լ��*/
	 -- 1.��������Ƿ����
	 select @TaskID =ID from CollectTask where  ID = @RWBH and EndTime>=getdate();
	 -- �����ѹ���
	 if @TaskID is null
	 begin
	   set @ReValue=-2;
	   return @ReValue;
	 end
	

	 --2.����Ƿ��۱�ϵ����ʷ��¼�Ƿ��ѹ���
	 SELECT   @StandardDegree = c.StandardDegree FROM  CollectPoint AS a INNER JOIN EntEnergy AS b ON a.EnergyID = b.ID INNER JOIN EnergyValue AS c ON b.ID = c.EnergyID
     WHERE   (c.StartDate <= GETDATE()) AND (c.EndDate = '1900-01-01 00:00:00') and a.ID=@CJDBH
	 -- �����ڴ��۱�ϵ��
	 if @StandardDegree is null
	  begin
	   set @ReValue=-3;
	   return @ReValue;
	 end
	
	-- 3.����Ƿ����ύһ��
	--�õ�ά�������Ϣ
     SELECT  top 1  @BusinessTime=a.BusinessTime,@TimeDim=a.TimeDim, @valuekind = b.valuekind FROM CollectTask AS a INNER JOIN CollectPoint AS b ON a.PointID = b.ID where a.PointID=@CJDBH   order by a.BusinessTime desc
	 -- 
	 declare @oldrows int;
	 select @oldrows=count(ID) from CollectValue where pointID=@CJDBH and BusinessTime=@BusinessTime and TimeDim=@TimeDim;
	 if @oldrows>0
	 begin
	    set @ReValue=-4;
	    return @ReValue;
	 end
	 /*�����Ϸ��Լ��*/

	 /*<��>ҵ�����*/
	-- �ۻ���
	 if @valuekind=20 
	 begin
		--�õ��ϴε�ֵ
	    declare @OldBusinessTime datetime;
	    if @TimeDim=4 
	    begin
	      set @OldBusinessTime=dateadd(DAY,-1,@BusinessTime);
	    end
	    else
	    begin
	      set @OldBusinessTime=dateadd(MONTH,-1,@BusinessTime);
	    end
		
		select   @OldValue= CollectValue  from CollectValue where PointID=@CJDBH and  BusinessTime=@OldBusinessTime and TimeDim=@TimeDim;
		  --�õ���ֵ
	    if @OldValue is null --�ɵ��ǵ�һ����ֵ
	    begin
	      set @NewValue = 0;
	    end
	    else
	    begin
	      set @NewValue = @RealValue-@OldValue;
	    end
		
		insert into CollectValue(PointID,CollectTime,CollectValue,IncreaseValue,isSum,OrigCollectValue,BusinessTime,TimeDim) values(@CJDBH,@CJSJ,@RealValue,@NewValue,0,@CJZ,@BusinessTime,@TimeDim);
	 end
	 else
	 begin
	    insert into CollectValue(PointID,CollectTime,CollectValue,isSum,OrigCollectValue,BusinessTime,TimeDim) values(@CJDBH,@CJSJ,@RealValue,0,@CJZ,@BusinessTime,@TimeDim);
	 end

     --�����������
	 update CollectTask set FinishTime=@CJSJ  where ID=@RWBH;
	 set @ReValue=1;
	 return @ReValue;
END




