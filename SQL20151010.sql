
-- 增加一条记录在采集周期中，解决国能中心公共周期的问题，王佰宏，2015-10-24
insert into [dbo].[CollectCycle] (PointID,ConfDate,EffectDate,AbortDate,CycDate,CycUnit,PermitDateLen,PermitUnit,DownloadAhead,AheadUnit,AllowDo,Type) values(0,'2015-10-23','2015-11-01','2050-01-01',1,3,15,4,6,4,1,0)
update [dbo].[CollectCycle] set ConfDate='2015-10-23',EffectDate='2015-11-01',AbortDate='2050-01-01',CycDate=1,CycUnit=3,PermitDateLen=15,PermitUnit=4,DownloadAhead=6,AheadUnit=4,AllowDo=1 where type=0;

-- 为了检查人工导入日志，增加此字段
alter table EnergyCconsumptionUnit add caredate datetime
alter table EnergyAccountUnit add caredate datetime
alter table EnergyValue add caredate datetime
alter table EntEnergy add caredate datetime
alter table MeasurePoint add caredate datetime
alter table MeasurePointValueMonth add caredate datetime

-- 查询任务函数(暂时未使用到),王佰宏，2015-10-25
SELECT ID as RWBH,PointID as CJDBH,CodeInfo as TM,PointName as CJDMC,BeginTime as RWSXSJ,EndTime as RWJSSJ,PermitDateLen as YXSC,DataAccuracy as SZJD,Rate as BL,cname as JLDW,Site as WZXX,name as NYJZ,CollectWay as FL from VGetAppTasks where (case when AheadUnit=1 then DATEADD(yy,DownloadAhead,BeginTime) when AheadUnit=2 then DATEADD(qq,DownloadAhead,BeginTime) when AheadUnit=3 then DATEADD(mm,DownloadAhead,BeginTime)   when AheadUnit=4 then DATEADD(dd,DownloadAhead,BeginTime)  when AheadUnit=5 then DATEADD(hh,DownloadAhead,BeginTime) else DATEADD(mi,DownloadAhead,BeginTime) end )<=getdate() 