
-- 1.	用户所属企业，一个用户可以归属多个企业，为监管单位
CREATE VIEW [dbo].[VAccountEntList]
AS
				SELECT   AccountEntList_1.EntID, AccountEntList_1.AccountID, dbo.EnergyCconsumptionUnit.CompanyName, 
                dbo.Account.Account, dbo.Account.Password, dbo.Account.State, dbo.Account.RealName, dbo.Account.DepName, 
                dbo.EnergyAccountUnit.BarCode
FROM      dbo.Account INNER JOIN
                dbo.AccountEntList AS AccountEntList_1 ON dbo.Account.ID = AccountEntList_1.AccountID INNER JOIN
                dbo.EnergyCconsumptionUnit ON AccountEntList_1.EntID = dbo.EnergyCconsumptionUnit.ID INNER JOIN
                dbo.EnergyAccountUnit ON dbo.EnergyCconsumptionUnit.ID = dbo.EnergyAccountUnit.ID

GO


-- 2.	用户拥有角色，一个用户可以有多个角色
CREATE VIEW [dbo].[VAccountRoleList]
AS
SELECT   dbo.AccountRoleList.ID, dbo.AccountRoleList.AccountID, dbo.AccountRoleList.RoleID, dbo.Role.Name, dbo.Role.Kind, 
                dbo.Account.Account, dbo.Account.Password, dbo.Account.State, dbo.Account.RealName
FROM      dbo.AccountRoleList INNER JOIN
                dbo.Role ON dbo.AccountRoleList.RoleID = dbo.Role.ID INNER JOIN
                dbo.Account ON dbo.AccountRoleList.AccountID = dbo.Account.ID

GO

-- 3.	计量器具台帐

CREATE VIEW [dbo].[VEnergyMeasureInstrument]
AS
SELECT   dbo.MeasureSite.beloneUnitID, dbo.EnergyAccountUnit.Name AS AccountName, dbo.MeasureSite.Site, 
                dbo.ReplaceRecord.Name, dbo.ReplaceRecord.Manufacturer, dbo.ReplaceRecord.Specification, 
                dbo.ReplaceRecord.ManufacturerNumber, dbo.ReplaceRecord.Code, dbo.ReplaceRecord.Precision, 
                dbo.ReplaceRecord.Range, dbo.ReplaceRecord.isUse, dbo.VerifyRecord.EndDate, dbo.EnergyMeasureInstruments.One, 
                dbo.EnergyMeasureInstruments.Two, dbo.EnergyMeasureInstruments.Three, dbo.ReplaceRecord.ChangeCyc, 
                dbo.ReplaceRecord.CycUnit, dbo.EnergyAccountUnit.BarCode AS AccountUnitCode, 
                dbo.EnergyMeasureInstruments.BarCode, dbo.EnergyCconsumptionUnit.CompanyName, 
                dbo.EnergyCconsumptionUnit.OrganizationCode, dbo.EnergyCconsumptionUnit.ID AS EntID
FROM      dbo.EnergyMeasureInstruments INNER JOIN
                dbo.MeasureSite ON dbo.EnergyMeasureInstruments.ID = dbo.MeasureSite.ID INNER JOIN
                dbo.EnergyAccountUnit ON dbo.MeasureSite.beloneUnitID = dbo.EnergyAccountUnit.ID INNER JOIN
                dbo.EnergyCconsumptionUnit ON dbo.EnergyAccountUnit.BarCode LIKE { fn CONCAT({ fn CONCAT('%', 
                dbo.EnergyCconsumptionUnit.OrganizationCode) }, '%') } INNER JOIN
                dbo.ReplaceRecord ON dbo.MeasureSite.ID = dbo.ReplaceRecord.SiteID LEFT OUTER JOIN
                dbo.VerifyRecord ON dbo.ReplaceRecord.ID = dbo.VerifyRecord.ReplaceID



-- 4. 得到无地点的采集任务
CREATE VIEW [dbo].[VGetAppNullSite]
AS
SELECT   dbo.CollectTask.ID, dbo.CollectTask.BeginTime, dbo.CollectTask.EndTime, dbo.CollectTask.UpLoadTime, 
                dbo.CollectPoint.Name AS PointName, dbo.CollectPoint.CollectWay, dbo.Energy.name, dbo.Energy.DataAccuracy, 
                dbo.MeasureUnit.cname, dbo.CollectPoint.ID AS PointID, dbo.CollectCycle.PermitUnit, dbo.CollectCycle.PermitDateLen, 
                dbo.Account.Account, dbo.Account.Password, dbo.Account.State, 0 AS SMSiteID, '  ' AS Site, '  ' AS CodeInfo, 1 AS Rate, 
                dbo.CollectCycle.DownloadAhead, dbo.CollectCycle.AheadUnit, dbo.CollectCycle.Type
FROM      dbo.CollectCycle INNER JOIN
                dbo.CollectPoint INNER JOIN
                dbo.CollectTask ON dbo.CollectPoint.ID = dbo.CollectTask.PointID INNER JOIN
                dbo.NumberReader ON dbo.CollectPoint.ID = dbo.NumberReader.CPointID INNER JOIN
                dbo.Account ON dbo.NumberReader.AccountID = dbo.Account.ID INNER JOIN
                dbo.EntEnergy ON dbo.CollectPoint.EnergyID = dbo.EntEnergy.ID INNER JOIN
                dbo.MeasureUnit INNER JOIN
                dbo.Energy ON dbo.MeasureUnit.ID = dbo.Energy.UnitID ON dbo.EntEnergy.EnergyID = dbo.Energy.ID ON 
                dbo.CollectCycle.ID = dbo.CollectTask.CycID
WHERE   (NOT EXISTS
                    (SELECT   PointID
                     FROM      dbo.PointInstrument AS PointInstrument
                     WHERE   (dbo.CollectPoint.ID = PointID))) AND (dbo.CollectPoint.isEnergy = 1)

GO


-- 5. 得到多通道采集任务
CREATE VIEW [dbo].[VGetAppTasksMany]
AS
SELECT   dbo.CollectTask.ID, dbo.CollectTask.BeginTime, dbo.CollectTask.EndTime, dbo.CollectTask.UpLoadTime, 
                dbo.CollectPoint.Name AS PointName, dbo.CollectPoint.CollectWay, dbo.Energy.name, dbo.Energy.DataAccuracy, 
                dbo.MeasureUnit.cname, dbo.PointInstrument.PointID, dbo.CollectCycle.PermitUnit, dbo.CollectCycle.PermitDateLen, 
                dbo.Account.Account, dbo.Account.Password, dbo.Account.State, dbo.MeasureSite.Site, dbo.MeasureSite.CodeInfo, 
                1 AS Rate, dbo.EnergyMeasureInstruments.SMSiteID, dbo.CollectCycle.DownloadAhead, dbo.CollectCycle.AheadUnit, 
                dbo.CollectCycle.Type
FROM      dbo.CollectPoint INNER JOIN
                dbo.CollectTask ON dbo.CollectPoint.ID = dbo.CollectTask.PointID INNER JOIN
                dbo.PointInstrument ON dbo.CollectPoint.ID = dbo.PointInstrument.PointID INNER JOIN
                dbo.NumberReader ON dbo.CollectPoint.ID = dbo.NumberReader.CPointID INNER JOIN
                dbo.Account ON dbo.NumberReader.AccountID = dbo.Account.ID INNER JOIN
                dbo.EnergyMeasureInstruments ON dbo.PointInstrument.InstrumentID = dbo.EnergyMeasureInstruments.ID INNER JOIN
                dbo.MeasureSite ON dbo.EnergyMeasureInstruments.SMSiteID = dbo.MeasureSite.ID INNER JOIN
                dbo.EntEnergy ON dbo.CollectPoint.EnergyID = dbo.EntEnergy.ID INNER JOIN
                dbo.MeasureUnit INNER JOIN
                dbo.Energy ON dbo.MeasureUnit.ID = dbo.Energy.UnitID ON dbo.EntEnergy.EnergyID = dbo.Energy.ID INNER JOIN
                dbo.CollectCycle ON dbo.CollectTask.CycID = dbo.CollectCycle.ID

GO
/*...
-- 6.得到单通道采集任务
CREATE VIEW [dbo].[VGetAppTasksOnly]
AS
SELECT   dbo.CollectTask.ID, dbo.CollectTask.BeginTime, dbo.CollectTask.EndTime, dbo.CollectTask.UpLoadTime, 
                dbo.CollectPoint.Name AS PointName, dbo.CollectPoint.CollectWay, dbo.Energy.name, dbo.Energy.DataAccuracy, 
                dbo.MeasureUnit.cname, dbo.PointInstrument.PointID, dbo.CollectCycle.PermitUnit, dbo.CollectCycle.PermitDateLen, 
                dbo.Account.Account, dbo.Account.Password, dbo.Account.State, dbo.EnergyMeasureInstruments.SMSiteID, 
                dbo.MeasureSite.Site, dbo.MeasureSite.CodeInfo, dbo.ReplaceRecord.Rate, dbo.CollectCycle.DownloadAhead, 
                dbo.CollectCycle.AheadUnit, dbo.CollectCycle.Type
FROM      dbo.MeasureUnit INNER JOIN
                dbo.Energy ON dbo.MeasureUnit.ID = dbo.Energy.UnitID INNER JOIN
                dbo.CollectPoint INNER JOIN
                dbo.CollectTask ON dbo.CollectPoint.ID = dbo.CollectTask.PointID INNER JOIN
                dbo.PointInstrument ON dbo.CollectPoint.ID = dbo.PointInstrument.PointID INNER JOIN
                dbo.NumberReader ON dbo.CollectPoint.ID = dbo.NumberReader.CPointID INNER JOIN
                dbo.Account ON dbo.NumberReader.AccountID = dbo.Account.ID INNER JOIN
                dbo.EnergyMeasureInstruments ON dbo.PointInstrument.InstrumentID = dbo.EnergyMeasureInstruments.ID INNER JOIN
                dbo.MeasureSite ON dbo.EnergyMeasureInstruments.ID = dbo.MeasureSite.ID INNER JOIN
                dbo.ReplaceRecord ON dbo.MeasureSite.ID = dbo.ReplaceRecord.SiteID INNER JOIN
                dbo.EntEnergy ON dbo.CollectPoint.EnergyID = dbo.EntEnergy.ID ON 
                dbo.Energy.ID = dbo.EntEnergy.EnergyID INNER JOIN
                dbo.CollectCycle ON dbo.CollectTask.CycID = dbo.CollectCycle.ID

GO
...*/
--6_16.得到单通道采集任务
CREATE VIEW [dbo].[VGetAppTasksOnly]
AS
SELECT   dbo.CollectTask.ID, dbo.CollectTask.BeginTime, dbo.CollectTask.EndTime, dbo.CollectTask.UpLoadTime, 
                dbo.CollectPoint.Name AS PointName, dbo.CollectPoint.CollectWay, dbo.Energy.name, dbo.Energy.DataAccuracy, 
                dbo.MeasureUnit.cname, dbo.PointInstrument.PointID, dbo.CollectCycle.PermitUnit, dbo.CollectCycle.PermitDateLen, 
                dbo.Account.Account, dbo.Account.Password, dbo.Account.State, dbo.EnergyMeasureInstruments.SMSiteID, 
                dbo.MeasureSite.Site, CASE dbo.CollectPoint.CollectWay WHEN 21 THEN '  ' ELSE dbo.MeasureSite.CodeInfo END AS CodeInfo, (CASE WHEN dbo.ReplaceRecord.Rate IS NULL 
                THEN 1 ELSE dbo.ReplaceRecord.Rate END) AS rate, dbo.CollectCycle.DownloadAhead, dbo.CollectCycle.AheadUnit, 
                dbo.CollectCycle.Type
FROM      dbo.MeasureUnit INNER JOIN
                dbo.Energy ON dbo.MeasureUnit.ID = dbo.Energy.UnitID INNER JOIN
                dbo.CollectPoint INNER JOIN
                dbo.CollectTask ON dbo.CollectPoint.ID = dbo.CollectTask.PointID INNER JOIN
                dbo.PointInstrument ON dbo.CollectPoint.ID = dbo.PointInstrument.PointID INNER JOIN
                dbo.NumberReader ON dbo.CollectPoint.ID = dbo.NumberReader.CPointID INNER JOIN
                dbo.Account ON dbo.NumberReader.AccountID = dbo.Account.ID INNER JOIN
                dbo.EnergyMeasureInstruments ON dbo.PointInstrument.InstrumentID = dbo.EnergyMeasureInstruments.ID INNER JOIN
                dbo.MeasureSite ON dbo.EnergyMeasureInstruments.ID = dbo.MeasureSite.ID LEFT OUTER JOIN
                dbo.ReplaceRecord ON dbo.MeasureSite.ID = dbo.ReplaceRecord.SiteID INNER JOIN
                dbo.EntEnergy ON dbo.CollectPoint.EnergyID = dbo.EntEnergy.ID ON 
                dbo.Energy.ID = dbo.EntEnergy.EnergyID INNER JOIN
                dbo.CollectCycle ON dbo.CollectTask.CycID = dbo.CollectCycle.ID
WHERE   (dbo.EnergyMeasureInstruments.SMSiteID = 0)

-- 7.得到所有的采集任务
CREATE VIEW [dbo].[VGetAppTasks]
AS
SELECT   ID, BeginTime, EndTime, UpLoadTime, PointName, CollectWay, name, DataAccuracy, cname, PointID, PermitUnit, 
                PermitDateLen, Account, Password, State, SMSiteID, Site, CodeInfo, Rate,DownloadAhead,AheadUnit,Type
FROM      dbo.VGetAppTasksMany AS a
UNION
SELECT   ID, BeginTime, EndTime, UpLoadTime, PointName, CollectWay, name, DataAccuracy, cname, PointID, PermitUnit, 
                PermitDateLen, Account, Password, State, SMSiteID, Site, CodeInfo, Rate,DownloadAhead,AheadUnit,Type

FROM      dbo.VGetAppTasksOnly AS b
UNION
SELECT   ID, BeginTime, EndTime, UpLoadTime, PointName, CollectWay, name, DataAccuracy, cname, PointID, PermitUnit, 
                PermitDateLen, Account, Password, State, SMSiteID, Site, CodeInfo, Rate,DownloadAhead,AheadUnit,Type

FROM      dbo.VGetAppNullSite AS c
UNION
SELECT   ID, BeginTime, EndTime, UpLoadTime, PointName, CollectWay, name, DataAccuracy, cname, PointID, PermitUnit, 
                PermitDateLen, Account, Password, State, SMSiteID, Site, CodeInfo, Rate, DownloadAhead, AheadUnit, Type
FROM      dbo.VGetAppTasksMeater AS d
UNION
SELECT   ID, BeginTime, EndTime, UpLoadTime, PointName, CollectWay, name, DataAccuracy, cname, PointID, PermitUnit, 
                PermitDateLen, Account, Password, State, SMSiteID, Site, CodeInfo, Rate, DownloadAhead, AheadUnit, Type
FROM      dbo.VGetAppNullSiteMeater AS e

GO

-- 8.采集人员对应采集点
CREATE VIEW [dbo].[VGetNumberReader]
AS
SELECT   a.ID, a.AccountID, a.RoleID, a.Name, a.Kind, a.Account, a.Password, a.State, a.RealName, b.EntID, b.DepName
FROM      dbo.VAccountRoleList AS a LEFT OUTER JOIN
                dbo.VAccountEntList AS b ON a.AccountID = b.AccountID


GO

-- 9.
CREATE VIEW [dbo].[VMeasureCollect]
AS
SELECT   dbo.EnergyAccountUnit.ID, dbo.EnergyAccountUnit.PID, dbo.EnergyAccountUnit.Name, dbo.MeasurePoint.UnitID
FROM      dbo.MeasurePoint INNER JOIN
                dbo.EnergyAccountUnit ON dbo.MeasurePoint.UnitID = dbo.EnergyAccountUnit.ID
UNION
SELECT   dbo.MeasurePoint.ID AS ID, dbo.MeasurePoint.UnitID AS PID, dbo.MeasurePoint.Name AS Name, 
                dbo.MeasurePoint.UnitID
FROM      dbo.MeasurePoint INNER JOIN
                dbo.EnergyAccountUnit ON dbo.MeasurePoint.UnitID = dbo.EnergyAccountUnit.ID

GO

-- 10. 角色拥有的所有权限
CREATE VIEW [dbo].[VRolePermissionList]
AS
SELECT   dbo.RolePermissionList.ID, dbo.Permission.menuName, dbo.Permission.menuID, dbo.RolePermissionList.RoleID, 
                dbo.Role.Name, dbo.Role.Kind, dbo.Permission.isUse, dbo.Permission.isButton, dbo.Permission.menuOrder, 
                dbo.Permission.menuGrade, dbo.Permission.ButtonID, dbo.Permission.menuUrl, dbo.Permission.parentID, 
                dbo.Permission.parentName, dbo.Permission.frameUrl, dbo.Permission.Notice, dbo.Permission.isEnterprise
FROM      dbo.RolePermissionList INNER JOIN
                dbo.Permission ON dbo.RolePermissionList.FunID = dbo.Permission.menuID INNER JOIN
                dbo.Role ON dbo.RolePermissionList.RoleID = dbo.Role.ID

GO

-- 11.查询规划点
CREATE VIEW [dbo].[VSelectMeasurePoint]
AS
SELECT   a.ID, a.PID, a.Name, 0 AS ispoint,a.BarCode
FROM      dbo.EnergyAccountUnit AS a INNER JOIN
                dbo.MeasurePoint AS b ON a.ID = b.UnitID
union
SELECT   b.ID, a.ID AS PID, b.Name, 1 AS ispoint,a.BarCode
FROM      dbo.EnergyAccountUnit AS a INNER JOIN
                dbo.MeasurePoint AS b ON a.ID = b.UnitID


-- 12.主页面标准计量器具

CREATE VIEW [dbo].[MeasureEttalonView]
AS
SELECT   dbo.MeasureEttalon.EntID, A.EndDate, B.EndTime, dbo.EnergyCconsumptionUnit.CompanyName, 
                dbo.MeasureEttalon.Name, dbo.MeasureEttalon.Code, dbo.MeasureEttalon.VerifyItem, dbo.MeasureEttalon.isTrust, 
                dbo.MeasureEttalon.ID, dbo.MeasureEttalon.Precision, dbo.MeasureEttalon.Range
FROM      dbo.MeasureEttalon INNER JOIN
                dbo.EnergyCconsumptionUnit ON dbo.MeasureEttalon.EntID = dbo.EnergyCconsumptionUnit.ID LEFT OUTER JOIN
                dbo.EttalonRecord AS A ON dbo.MeasureEttalon.ID = A.EttalonID AND A.ID =
                    (SELECT   MAX(ID) AS Expr1
                     FROM      dbo.EttalonRecord
                     WHERE   (EttalonID = A.EttalonID)) LEFT OUTER JOIN
                dbo.AuthorizeRecord AS B ON dbo.MeasureEttalon.ID = B.EttalonID AND B.ID =
                    (SELECT   MAX(ID) AS Expr1
                     FROM      dbo.AuthorizeRecord
                     WHERE   (EttalonID = B.EttalonID))

-- 13.主页面仪表个数。

CREATE VIEW [dbo].[VVerifyRecord]
AS
SELECT   t.ReplaceID, t.EndDate, MeasureSite_1.beloneUnitID, EnergyAccountUnit_1.BarCode
FROM      dbo.ReplaceRecord INNER JOIN
                    (SELECT   ReplaceRecord_1.SiteID, MAX(ReplaceRecord_1.ID) AS ID
                     FROM      dbo.ReplaceRecord AS ReplaceRecord_1 RIGHT OUTER JOIN
                                     dbo.MeasureSite ON ReplaceRecord_1.SiteID = dbo.MeasureSite.ID INNER JOIN
                                     dbo.EnergyMeasureInstruments ON dbo.EnergyMeasureInstruments.ID = dbo.MeasureSite.ID INNER JOIN
                                     dbo.EnergyAccountUnit ON dbo.MeasureSite.beloneUnitID = dbo.EnergyAccountUnit.ID
                     GROUP BY ReplaceRecord_1.SiteID) AS tt ON dbo.ReplaceRecord.ID = tt.ID RIGHT OUTER JOIN
                dbo.MeasureSite AS MeasureSite_1 ON dbo.ReplaceRecord.SiteID = MeasureSite_1.ID INNER JOIN
                dbo.EnergyMeasureInstruments AS EnergyMeasureInstruments_1 ON 
                EnergyMeasureInstruments_1.ID = MeasureSite_1.ID INNER JOIN
                dbo.EnergyAccountUnit AS EnergyAccountUnit_1 ON 
                MeasureSite_1.beloneUnitID = EnergyAccountUnit_1.ID LEFT OUTER JOIN
                    (SELECT   ReplaceID, EndDate
                     FROM      dbo.VerifyRecord
                     WHERE   (EndDate IN
                                         (SELECT   MAX(EndDate) AS Expr1
                                          FROM      dbo.VerifyRecord AS VerifyRecord_1
                                          GROUP BY ReplaceID))) AS t ON dbo.ReplaceRecord.ID = t.ReplaceID
/**
-- 14.物料任务
CREATE VIEW [dbo].[VGetAppTasksMeater]
AS
SELECT   dbo.CollectTask.ID, dbo.CollectTask.BeginTime, dbo.CollectTask.EndTime, dbo.CollectTask.UpLoadTime, 
                dbo.CollectPoint.Name AS PointName, dbo.CollectPoint.CollectWay, dbo.Meaterial.name, dbo.Meaterial.DataAccuracy, 
                dbo.MeasureUnit.cname, dbo.PointInstrument.PointID, dbo.CollectCycle.PermitUnit, dbo.CollectCycle.PermitDateLen, 
                dbo.Account.Account, dbo.Account.Password, dbo.Account.State, dbo.EnergyMeasureInstruments.SMSiteID, 
                dbo.MeasureSite.Site, dbo.MeasureSite.CodeInfo, dbo.ReplaceRecord.Rate, dbo.CollectCycle.DownloadAhead, 
                dbo.CollectCycle.AheadUnit, dbo.CollectCycle.Type
FROM      dbo.CollectCycle INNER JOIN
                dbo.CollectPoint INNER JOIN
                dbo.CollectTask ON dbo.CollectPoint.ID = dbo.CollectTask.PointID INNER JOIN
                dbo.PointInstrument ON dbo.CollectPoint.ID = dbo.PointInstrument.PointID INNER JOIN
                dbo.NumberReader ON dbo.CollectPoint.ID = dbo.NumberReader.CPointID INNER JOIN
                dbo.Account ON dbo.NumberReader.AccountID = dbo.Account.ID INNER JOIN
                dbo.EnergyMeasureInstruments ON dbo.PointInstrument.InstrumentID = dbo.EnergyMeasureInstruments.ID INNER JOIN
                dbo.MeasureSite ON dbo.EnergyMeasureInstruments.ID = dbo.MeasureSite.ID INNER JOIN
                dbo.ReplaceRecord ON dbo.MeasureSite.ID = dbo.ReplaceRecord.SiteID ON 
                dbo.CollectCycle.ID = dbo.CollectTask.CycID INNER JOIN
                dbo.EntMeater ON dbo.CollectPoint.EnergyID = dbo.EntMeater.ID INNER JOIN
                dbo.Meaterial ON dbo.EntMeater.MeaterID = dbo.Meaterial.ID INNER JOIN
                dbo.MeasureUnit ON dbo.Meaterial.UnitID = dbo.MeasureUnit.ID

GO**/


-- 14.物料任务
CREATE VIEW [dbo].[VGetAppTasksMeater]
AS
SELECT   dbo.CollectTask.ID, dbo.CollectTask.BeginTime, dbo.CollectTask.EndTime, dbo.CollectTask.UpLoadTime, 
                dbo.CollectPoint.Name AS PointName, dbo.CollectPoint.CollectWay, dbo.Meaterial.name, dbo.Meaterial.DataAccuracy, 
                dbo.MeasureUnit.cname, dbo.PointInstrument.PointID, dbo.CollectCycle.PermitUnit, dbo.CollectCycle.PermitDateLen, 
                dbo.Account.Account, dbo.Account.Password, dbo.Account.State, dbo.EnergyMeasureInstruments.SMSiteID, 
                dbo.MeasureSite.Site, dbo.MeasureSite.CodeInfo, dbo.ReplaceRecord.Rate, dbo.CollectCycle.DownloadAhead, 
                dbo.CollectCycle.AheadUnit, dbo.CollectCycle.Type
FROM      dbo.NumberReader INNER JOIN
                dbo.CollectPoint ON dbo.CollectPoint.ID = dbo.NumberReader.CPointID INNER JOIN
                dbo.Account ON dbo.NumberReader.AccountID = dbo.Account.ID INNER JOIN
                dbo.CollectTask ON dbo.CollectPoint.ID = dbo.CollectTask.PointID INNER JOIN
                dbo.CollectCycle ON dbo.CollectCycle.ID = dbo.CollectTask.CycID LEFT OUTER JOIN
                dbo.PointInstrument ON dbo.CollectPoint.ID = dbo.PointInstrument.PointID LEFT OUTER JOIN
                dbo.EnergyMeasureInstruments ON 
                dbo.PointInstrument.InstrumentID = dbo.EnergyMeasureInstruments.ID LEFT OUTER JOIN
                dbo.MeasureSite ON dbo.EnergyMeasureInstruments.ID = dbo.MeasureSite.ID LEFT OUTER JOIN
                dbo.ReplaceRecord ON dbo.MeasureSite.ID = dbo.ReplaceRecord.SiteID LEFT OUTER JOIN
                dbo.EntMeater ON dbo.CollectPoint.EnergyID = dbo.EntMeater.ID LEFT OUTER JOIN
                dbo.Meaterial ON dbo.EntMeater.MeaterID = dbo.Meaterial.ID LEFT OUTER JOIN
                dbo.MeasureUnit ON dbo.Meaterial.UnitID = dbo.MeasureUnit.ID
WHERE   (dbo.CollectPoint.isEnergy = 0)

-- 15.手工录入需要的查询视图1
CREATE VIEW [dbo].[VHandInput]
AS
SELECT   dbo.CollectTask.ID, dbo.CollectTask.PointID, dbo.CollectTask.BeginTime, dbo.CollectTask.EndTime, 
                dbo.CollectTask.FinishTime, dbo.CollectTask.UpLoadTime, dbo.CollectTask.BusinessTime, dbo.CollectTask.Type, 
                dbo.CollectPoint.CollectWay, dbo.CollectPoint.isEnergy, dbo.CollectPoint.EntID, dbo.MeasureUnit.cname, 
                dbo.CollectValue.CollectValue, dbo.CollectValue.ID AS VID, dbo.CollectPoint.ID AS CID
FROM      dbo.CollectTask INNER JOIN
                dbo.CollectPoint ON dbo.CollectTask.PointID = dbo.CollectPoint.ID INNER JOIN
                dbo.EntEnergy ON dbo.CollectPoint.EnergyID = dbo.EntEnergy.ID INNER JOIN
                dbo.Energy ON dbo.EntEnergy.EnergyID = dbo.Energy.ID INNER JOIN
                dbo.MeasureUnit ON dbo.Energy.UnitID = dbo.MeasureUnit.ID LEFT OUTER JOIN
                dbo.CollectValue ON dbo.CollectPoint.ID = dbo.CollectValue.PointID
WHERE   (dbo.CollectTask.UpLoadTime IS NULL) AND (dbo.CollectTask.EndTime > GETDATE()) AND 
                (dbo.CollectPoint.isEnergy = 1) AND (dbo.CollectPoint.CollectWay = 22)
UNION
SELECT   dbo.CollectTask.ID, dbo.CollectTask.PointID, dbo.CollectTask.BeginTime, dbo.CollectTask.EndTime, 
                dbo.CollectTask.FinishTime, dbo.CollectTask.UpLoadTime, dbo.CollectTask.BusinessTime, dbo.CollectTask.Type, 
                dbo.CollectPoint.CollectWay, dbo.CollectPoint.isEnergy, dbo.CollectPoint.EntID, dbo.MeasureUnit.cname, 
                dbo.CollectValue.CollectValue, dbo.CollectValue.ID AS VID, dbo.CollectPoint.ID AS CID
FROM      dbo.CollectTask INNER JOIN
                dbo.CollectPoint ON dbo.CollectTask.PointID = dbo.CollectPoint.ID INNER JOIN
                dbo.EntMeater ON dbo.CollectPoint.EnergyID = dbo.EntMeater.ID INNER JOIN
                dbo.Meaterial ON dbo.EntMeater.MeaterID = dbo.Meaterial.ID INNER JOIN
                dbo.MeasureUnit ON dbo.Meaterial.UnitID = dbo.MeasureUnit.ID LEFT OUTER JOIN
                dbo.CollectValue ON dbo.CollectPoint.ID = dbo.CollectValue.PointID
WHERE   (dbo.CollectTask.UpLoadTime IS NULL) AND (dbo.CollectTask.EndTime > GETDATE()) AND 
                (dbo.CollectPoint.isEnergy = 0) AND (dbo.CollectPoint.CollectWay = 22)



GO
-- 16.手工录入需要的查询视图2
CREATE VIEW [dbo].[VHandInputone]
AS
SELECT   dbo.CollectTask.ID, dbo.CollectTask.PointID, dbo.CollectTask.BeginTime, dbo.CollectTask.EndTime, 
                dbo.CollectTask.FinishTime, dbo.CollectTask.UpLoadTime, dbo.CollectTask.BusinessTime, dbo.CollectTask.Type, 
                dbo.CollectPoint.CollectWay, dbo.CollectPoint.isEnergy, dbo.CollectPoint.EntID, dbo.MeasureUnit.cname, 
                dbo.CollectValue.CollectValue, dbo.CollectValue.ID AS VID, dbo.CollectPoint.ID AS CID
FROM      dbo.CollectTask INNER JOIN
                dbo.CollectPoint ON dbo.CollectTask.PointID = dbo.CollectPoint.ID INNER JOIN
                dbo.EntEnergy ON dbo.CollectPoint.EnergyID = dbo.EntEnergy.ID INNER JOIN
                dbo.Energy ON dbo.EntEnergy.EnergyID = dbo.Energy.ID INNER JOIN
                dbo.MeasureUnit ON dbo.Energy.UnitID = dbo.MeasureUnit.ID LEFT OUTER JOIN
                dbo.CollectValue ON dbo.CollectPoint.ID = dbo.CollectValue.PointID
WHERE   (dbo.CollectTask.UpLoadTime IS NULL) AND (dbo.CollectTask.EndTime > GETDATE()) AND 
                (dbo.CollectPoint.isEnergy = 1) AND (dbo.CollectPoint.CollectWay = 22) 
                 AND dbo.CollectTask.FinishTime IS  NULL 
UNION
SELECT   dbo.CollectTask.ID, dbo.CollectTask.PointID, dbo.CollectTask.BeginTime, dbo.CollectTask.EndTime, 
                dbo.CollectTask.FinishTime, dbo.CollectTask.UpLoadTime, dbo.CollectTask.BusinessTime, dbo.CollectTask.Type, 
                dbo.CollectPoint.CollectWay, dbo.CollectPoint.isEnergy, dbo.CollectPoint.EntID, dbo.MeasureUnit.cname, 
                dbo.CollectValue.CollectValue, dbo.CollectValue.ID AS VID, dbo.CollectPoint.ID AS CID
FROM      dbo.CollectTask INNER JOIN
                dbo.CollectPoint ON dbo.CollectTask.PointID = dbo.CollectPoint.ID INNER JOIN
                dbo.EntMeater ON dbo.CollectPoint.EnergyID = dbo.EntMeater.ID INNER JOIN
                dbo.Meaterial ON dbo.EntMeater.MeaterID = dbo.Meaterial.ID INNER JOIN
                dbo.MeasureUnit ON dbo.Meaterial.UnitID = dbo.MeasureUnit.ID LEFT OUTER JOIN
                dbo.CollectValue ON dbo.CollectPoint.ID = dbo.CollectValue.PointID
WHERE   (dbo.CollectTask.UpLoadTime IS NULL) AND (dbo.CollectTask.EndTime > GETDATE()) AND 
                (dbo.CollectPoint.isEnergy = 0) AND (dbo.CollectPoint.CollectWay = 22) 
                 AND dbo.CollectTask.FinishTime IS  NULL 

   

GO

-- 17.物料采集点无安装地点产生的任务

CREATE VIEW [dbo].[VGetAppNullSiteMeater]
AS
SELECT   dbo.CollectTask.ID, dbo.CollectTask.BeginTime, dbo.CollectTask.EndTime, dbo.CollectTask.UpLoadTime, 
                dbo.CollectPoint.Name AS PointName, dbo.CollectPoint.CollectWay, dbo.Meaterial.name, dbo.Meaterial.DataAccuracy, 
                dbo.MeasureUnit.cname, dbo.CollectPoint.ID AS PointID, dbo.CollectCycle.PermitUnit, dbo.CollectCycle.PermitDateLen, 
                dbo.Account.Account, dbo.Account.Password, dbo.Account.State, 0 AS SMSiteID, '  ' AS Site, '  ' AS CodeInfo, 1 AS Rate, 
                dbo.CollectCycle.DownloadAhead, dbo.CollectCycle.AheadUnit, dbo.CollectCycle.Type
FROM      dbo.CollectTask INNER JOIN
                dbo.CollectPoint ON dbo.CollectTask.PointID = dbo.CollectPoint.ID INNER JOIN
                dbo.NumberReader ON dbo.CollectPoint.ID = dbo.NumberReader.CPointID INNER JOIN
                dbo.Account ON dbo.NumberReader.AccountID = dbo.Account.ID INNER JOIN
                dbo.EntMeater ON dbo.CollectPoint.EnergyID = dbo.EntMeater.ID INNER JOIN
                dbo.Meaterial ON dbo.EntMeater.MeaterID = dbo.Meaterial.ID INNER JOIN
                dbo.MeasureUnit ON dbo.Meaterial.UnitID = dbo.MeasureUnit.ID INNER JOIN
                dbo.CollectCycle ON dbo.CollectTask.CycID = dbo.CollectCycle.ID
WHERE   (dbo.CollectPoint.isEnergy = 0) AND (NOT EXISTS
                    (SELECT   PointID
                     FROM      dbo.PointInstrument AS PointInstrument
                     WHERE   (dbo.CollectPoint.ID = PointID)))


GO


--18.企业能耗分析用到的视图

CREATE VIEW dbo.VEnterpriseanalysis
AS
SELECT     dbo.DataCenterInputValueView.StandardValue, dbo.EnergyCconsumptionUnit.CompanyName, dbo.DataCenterInputValueView.SumDim, dbo.EnergyAccountUnit.ID, 
                 dbo.EnergyAccountUnit.BarCode, dbo.EnergyCconsumptionUnit.CmpKIndID, dbo.CmpIndustryKind.name AS CmpIndustryKind_name, 
                 dbo.EnergyCconsumptionUnit.TreID, dbo.Regionalism.Name AS Regionalism_Name, dbo.Regionalism.ID AS Regionalism_ID, 
                 dbo.CmpIndustryKind.ID AS CmpIndustryKind_ID, dbo.Regionalism.NumCode, dbo.CmpIndustryKind.code
FROM      dbo.EnergyCconsumptionUnit INNER JOIN
				dbo.EnergyAccountUnit ON dbo.EnergyCconsumptionUnit.ID = dbo.EnergyAccountUnit.ID left JOIN
				dbo.DataCenterInputValueView ON dbo.DataCenterInputValueView.unitid = dbo.EnergyCconsumptionUnit.id left join
				dbo.CmpIndustryKind ON dbo.CmpIndustryKind.ID = dbo.EnergyCconsumptionUnit.CmpKIndID INNER JOIN
				dbo.Regionalism ON dbo.EnergyCconsumptionUnit.TreID = dbo.Regionalism.ID
GO

19.--总能耗统计视图
CREATE VIEW [dbo].[VRegionalismenergyView]
AS
SELECT   dbo.MeasurePointValueMonth.StandardValue, dbo.EnergyCconsumptionUnit.CompanyName, 
                dbo.MeasurePointValueMonth.SumDim, dbo.EnergyAccountUnit.BarCode, dbo.Regionalism.Name, dbo.Regionalism.Lng, 
                dbo.Regionalism.Lat, dbo.Regionalism.ID AS RegionId, dbo.Regionalism.PID
FROM      dbo.Regionalism INNER JOIN
                dbo.EnergyCconsumptionUnit ON dbo.Regionalism.ID = dbo.EnergyCconsumptionUnit.TreID RIGHT OUTER JOIN
                dbo.MeasurePointValueMonth INNER JOIN
                dbo.MeasurePoint ON dbo.MeasurePointValueMonth.MPointID = dbo.MeasurePoint.ID INNER JOIN
                dbo.EnergyAccountUnit ON dbo.MeasurePoint.UnitID = dbo.EnergyAccountUnit.ID ON 
                dbo.EnergyCconsumptionUnit.ID = dbo.EnergyAccountUnit.ID

GO


20--=行业能源消耗统计视图
CREATE VIEW dbo.VStdIndustryKindenergyView
AS
SELECT dbo.DataCenterInputValueView.StandardValue,
			dbo.EnergyCconsumptionUnit.CompanyName, 
            dbo.DataCenterInputValueView.SumDim, 
            dbo.EnergyAccountUnit.BarCode, 
            dbo.StdIndustryKind.ID, 
            dbo.StdIndustryKind.name,
            dbo.StdIndustryKind.SysCode
FROM  dbo.DataCenterInputValueView
			INNER JOIN dbo.EnergyAccountUnit on dbo.DataCenterInputValueView.unitid = dbo.EnergyAccountUnit.ID
			INNER JOIN dbo.EnergyCconsumptionUnit on dbo.DataCenterInputValueView.unitid = dbo.EnergyCconsumptionUnit.Id 
			INNER JOIN dbo.StdIndustryKind ON dbo.EnergyCconsumptionUnit.StdKindID = dbo.StdIndustryKind.ID
GO


21---地区能源消耗分析统计试图
CREATE VIEW [dbo].[VRegionalismenergyView]
AS
SELECT   dbo.MeasurePointValueMonth.StandardValue, dbo.EnergyCconsumptionUnit.CompanyName, 
                dbo.MeasurePointValueMonth.SumDim, dbo.EnergyAccountUnit.BarCode, dbo.Regionalism.Name, dbo.Regionalism.Lng, 
                dbo.Regionalism.Lat, dbo.Regionalism.ID AS RegionId, dbo.Regionalism.PID
FROM      dbo.Regionalism INNER JOIN
                dbo.EnergyCconsumptionUnit ON dbo.Regionalism.ID = dbo.EnergyCconsumptionUnit.TreID RIGHT OUTER JOIN
                dbo.MeasurePointValueMonth INNER JOIN
                dbo.MeasurePoint ON dbo.MeasurePointValueMonth.MPointID = dbo.MeasurePoint.ID INNER JOIN
                dbo.EnergyAccountUnit ON dbo.MeasurePoint.UnitID = dbo.EnergyAccountUnit.ID ON 
                dbo.EnergyCconsumptionUnit.ID = dbo.EnergyAccountUnit.ID

GO





22  -- 计量器具管理

  CREATE VIEW [dbo].[Vinstrument]
AS
SELECT   dbo.MeasureSite.beloneUnitID, dbo.EnergyAccountUnit.Name AS AccountName, dbo.MeasureSite.Site, 
                dbo.ReplaceRecord.Name, dbo.ReplaceRecord.Manufacturer, dbo.ReplaceRecord.Specification, 
                dbo.ReplaceRecord.ManufacturerNumber, dbo.ReplaceRecord.Code, dbo.ReplaceRecord.Precision, 
                dbo.ReplaceRecord.Range, dbo.ReplaceRecord.isUse, dbo.VerifyRecord.EndDate, dbo.EnergyMeasureInstruments.One, 
                dbo.EnergyMeasureInstruments.Two, dbo.EnergyMeasureInstruments.Three, dbo.ReplaceRecord.ChangeCyc, 
                dbo.ReplaceRecord.CycUnit, dbo.EnergyAccountUnit.BarCode AS AccountUnitCode, 
                dbo.EnergyMeasureInstruments.BarCode, dbo.EnergyCconsumptionUnit.CompanyName, 
                dbo.EnergyCconsumptionUnit.OrganizationCode, dbo.EnergyCconsumptionUnit.ID AS EntID, 
                dbo.Regionalism.Name AS RegionalismName, dbo.Regionalism.NumCode, dbo.StdIndustryKind.SysCode, 
                dbo.EnergyCconsumptionUnit.StdKindID, dbo.StdIndustryKind.name AS StdIndustryKindName
FROM      dbo.EnergyMeasureInstruments INNER JOIN
                dbo.MeasureSite ON dbo.EnergyMeasureInstruments.ID = dbo.MeasureSite.ID INNER JOIN
                dbo.EnergyAccountUnit ON dbo.MeasureSite.beloneUnitID = dbo.EnergyAccountUnit.ID INNER JOIN
                dbo.EnergyCconsumptionUnit ON dbo.EnergyAccountUnit.BarCode LIKE { fn CONCAT({ fn CONCAT('%', 
                dbo.EnergyCconsumptionUnit.OrganizationCode) }, '%') } INNER JOIN
                dbo.ReplaceRecord ON dbo.MeasureSite.ID = dbo.ReplaceRecord.SiteID LEFT OUTER JOIN
                dbo.Regionalism ON dbo.EnergyCconsumptionUnit.TreID = dbo.Regionalism.ID LEFT OUTER JOIN
                dbo.StdIndustryKind ON dbo.EnergyCconsumptionUnit.StdKindID = dbo.StdIndustryKind.ID LEFT OUTER JOIN
                dbo.VerifyRecord ON dbo.ReplaceRecord.ID = dbo.VerifyRecord.ReplaceID


GO


23  -- 计量标准器管理

 CREATE VIEW [dbo].[VMeasureEttalon]
  AS
SELECT   dbo.MeasureEttalon.EntID, A.EndDate, B.EndTime, dbo.EnergyCconsumptionUnit.CompanyName, 
                dbo.MeasureEttalon.Name, dbo.MeasureEttalon.Code, dbo.MeasureEttalon.VerifyItem, dbo.MeasureEttalon.isTrust, 
                dbo.MeasureEttalon.ID, dbo.MeasureEttalon.Precision, dbo.MeasureEttalon.Range, 
                dbo.Regionalism.Name AS RegionalismName, dbo.Regionalism.NumCode, dbo.StdIndustryKind.SysCode, 
                dbo.StdIndustryKind.name AS StdIndustryKindName
FROM      dbo.MeasureEttalon INNER JOIN
                dbo.EnergyCconsumptionUnit ON dbo.MeasureEttalon.EntID = dbo.EnergyCconsumptionUnit.ID INNER JOIN
                dbo.Regionalism ON dbo.EnergyCconsumptionUnit.TreID = dbo.Regionalism.ID INNER JOIN
                dbo.StdIndustryKind ON dbo.EnergyCconsumptionUnit.StdKindID = dbo.StdIndustryKind.ID LEFT OUTER JOIN
                dbo.EttalonRecord AS A ON dbo.MeasureEttalon.ID = A.EttalonID AND A.ID =
                    (SELECT   MAX(ID) AS Expr1
                     FROM      dbo.EttalonRecord
                     WHERE   (EttalonID = A.EttalonID)) LEFT OUTER JOIN
                dbo.AuthorizeRecord AS B ON dbo.MeasureEttalon.ID = B.EttalonID AND B.ID =
                    (SELECT   MAX(ID) AS Expr1
                     FROM      dbo.AuthorizeRecord
                     WHERE   (EttalonID = B.EttalonID))


24. -- 国能中心的能源输入，max(外购量，输入量）- sum(转供量，用于原料量)）,朱琨,2015-12-01
CREATE VIEW dbo.DataCenterInputValueView
AS
select invalue.sumdim, invalue.EntEnergyID, invalue.EnergyID,invalue.unitid, (isnull(invalue.StandardValue,0) - isnull(outvalue.StandardValue,0)) as StandardValue, (isnull(invalue.EntityValue,0) - isnull(outvalue.EntityValue,0)) as EntityValue
  from
  (select m.sumdim, ee.id as EntEnergyID, min(e.id) as EnergyID, max(StandardValue) as StandardValue, max(EntityValue) as EntityValue, p.unitid
      from MeasurePointValueMonth m inner join MeasurePoint p on m.MPointID = p.id
           inner join EnergyMeasurePoint em on p.id = em.mesid
           inner join EntEnergy ee on ee.id = em.energyid
           inner join Energy e on e.id = ee.energyid
           inner join EnergyAccountUnit U on p.UnitID = U.ID
      where (p.energydirection in (1,99) and U.Grade = 1)
      group by p.unitid,ee.id,m.SumDim ) as invalue
  left join
  (select m.sumdim, ee.id as EntEnergyID, min(e.id) as EnergyID, sum(StandardValue) as StandardValue, max(EntityValue) as EntityValue, p.unitid
      from MeasurePointValueMonth m inner join MeasurePoint p on m.MPointID = p.id
          inner join EnergyMeasurePoint em on p.id = em.mesid
          inner join EntEnergy ee on ee.id = em.energyid
          inner join Energy e on e.id = ee.energyid
          inner join EnergyAccountUnit U on p.UnitID = U.ID
      where (p.energydirection in (4,9) and U.Grade = 1)
      group by p.unitid,ee.id,m.SumDim ) as outvalue
  on invalue.unitid=outvalue.unitid and invalue.entenergyid = outvalue.entenergyid and invalue.sumdim = outvalue.sumdim
  
  25.-- 统计企业的能源输入，max(外购量，输入量）- sum(转供量，用于原料量)）,朱琨,2015-12-01
  CREATE VIEW dbo.EnterPriseInputValueView
AS
select invalue.sumdim, invalue.energyid,invalue.unitid, (isnull(invalue.StandardAdjValue,0) - isnull(outvalue.StandardAdjValue,0)) as StandardAdjValue, (isnull(invalue.EntityAdjValue,0) - isnull(outvalue.EntityAdjValue,0)) as EntityAdjValue
  from
  (select m.sumdim, ee.energyid, max(StandardAdjValue) as StandardAdjValue, max(EntityAdjValue) as EntityAdjValue, p.unitid
      from MeasurePointValueMonth m inner join MeasurePoint p on m.MPointID = p.id
           inner join EnergyMeasurePoint e on p.id = e.mesid
           inner join EntEnergy ee on ee.id = e.energyid
           inner join EnergyAccountUnit U on p.UnitID = U.ID
      where (p.energydirection in (1,99) and U.Grade = 1)
      group by p.unitid,ee.energyid,m.SumDim ) as invalue
  left join
  (select m.sumdim, ee.energyid, sum(StandardAdjValue) as StandardAdjValue, max(EntityAdjValue) as EntityAdjValue, p.unitid
      from MeasurePointValueMonth m inner join MeasurePoint p on m.MPointID = p.id
          inner join EnergyMeasurePoint e on p.id = e.mesid
          inner join EntEnergy ee on ee.id = e.energyid
          inner join EnergyAccountUnit U on p.UnitID = U.ID
      where (p.energydirection in (4,9) and U.Grade = 1)
      group by p.unitid,ee.energyid,m.SumDim ) as outvalue
  on invalue.unitid=outvalue.unitid and invalue.energyid = outvalue.energyid and invalue.sumdim = outvalue.sumdim




26.--实施数据查询（按月份跟天折线统计图）
	CREATE VIEW [dbo].[VMeasurePointasUnit]
AS
SELECT  dbo.EnergyAccountUnit.Name, dbo.MeasurePoint.Name AS PointName, dbo.EntEnergy.EnterEnergyName, 
                   dbo.MeasurePoint.ID
FROM      dbo.EnergyAccountUnit INNER JOIN
                   dbo.EnergyMeasurePoint INNER JOIN
                   dbo.EntEnergy ON dbo.EnergyMeasurePoint.EnergyID = dbo.EntEnergy.ID INNER JOIN
                   dbo.MeasurePoint ON dbo.EnergyMeasurePoint.MesID = dbo.MeasurePoint.ID ON 
                   dbo.EnergyAccountUnit.ID = dbo.MeasurePoint.UnitID

GO

27.--改动实施数据查询（按月份跟天折线统计图）
CREATE VIEW [dbo].[VMeasurePointasUnit]
AS
SELECT  dbo.EnergyAccountUnit.Name, dbo.MeasurePoint.Name AS PointName, dbo.EntEnergy.EnterEnergyName, 
                   dbo.MeasurePoint.ID, dbo.EntEnergy.ID AS Entenid, dbo.EnergyAccountUnit.ID AS Unitid
FROM      dbo.EnergyAccountUnit INNER JOIN
                   dbo.EnergyMeasurePoint INNER JOIN
                   dbo.EntEnergy ON dbo.EnergyMeasurePoint.EnergyID = dbo.EntEnergy.ID INNER JOIN
                   dbo.MeasurePoint ON dbo.EnergyMeasurePoint.MesID = dbo.MeasurePoint.ID ON 
                   dbo.EnergyAccountUnit.ID = dbo.MeasurePoint.UnitID

GO
