package sbzy.enterpriseems.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.extremecomponents.table.context.Context;
import org.extremecomponents.table.context.HttpServletRequestContext;
import org.extremecomponents.table.limit.LimitFactory;
import org.extremecomponents.table.limit.TableLimit;
import org.extremecomponents.table.limit.TableLimitFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import sbzy.enterpriseems.model.domain.CmpIndustryKind;
import sbzy.enterpriseems.model.domain.Energy;
import sbzy.enterpriseems.model.domain.EnergyAccountUnit;
import sbzy.enterpriseems.model.domain.EnergyCconsumptionUnit;
import sbzy.enterpriseems.model.domain.EnergyKPI;
import sbzy.enterpriseems.model.domain.EntEnergy;
import sbzy.enterpriseems.model.domain.EntKPI;
import sbzy.enterpriseems.model.domain.EntMeater;
import sbzy.enterpriseems.model.domain.IKPIPoint;
import sbzy.enterpriseems.model.domain.KPIDenPoint;
import sbzy.enterpriseems.model.domain.KPIKind;
import sbzy.enterpriseems.model.domain.KPINumPoint;
import sbzy.enterpriseems.model.domain.KPIRecord;
import sbzy.enterpriseems.model.domain.MeasurePoint;
import sbzy.enterpriseems.model.domain.MeasurePointValueMonth;
import sbzy.enterpriseems.model.domain.MeasurePointValueYear;
import sbzy.enterpriseems.model.domain.MeasureUnit;
import sbzy.enterpriseems.model.domain.Meaterial;
import sbzy.enterpriseems.model.service.impl.CmpIndustryKindServiceImpl;
import sbzy.enterpriseems.model.service.impl.EnergyAccountUnitServiceImpl;
import sbzy.enterpriseems.model.service.impl.EnergyCconsumptionUnitServiceImpl;
import sbzy.enterpriseems.model.service.impl.EnergyKPIServiceImpl;
import sbzy.enterpriseems.model.service.impl.EnergyServiceImpl;
import sbzy.enterpriseems.model.service.impl.EntKPIServiceImpl;
import sbzy.enterpriseems.model.service.impl.KPIDenPointServiceImpl;
import sbzy.enterpriseems.model.service.impl.KPIKindServiceImpl;
import sbzy.enterpriseems.model.service.impl.KPINumPointServiceImpl;
import sbzy.enterpriseems.model.service.impl.KPIRecordServiceImpl;
import sbzy.enterpriseems.model.service.impl.MeasurePointServiceImpl;
import sbzy.enterpriseems.model.service.impl.MeasurePointValueMonthServiceImpl;
import sbzy.enterpriseems.model.service.impl.MeasurePointValueYearServiceImpl;
import sbzy.enterpriseems.model.service.impl.MeasureUnitServiceImpl;
import sbzy.enterpriseems.model.service.impl.MeaterialServiceImpl;
import sbzy.enterpriseems.model.support.UnitTools;

@Controller
@RequestMapping("/entkpi")
public class EntKPIController extends BaseController {
	@Autowired
	private EntKPIServiceImpl entKPIService;
	@Autowired
	private EnergyKPIServiceImpl energyKPIService;
	@Autowired
	private KPINumPointServiceImpl numPointService;
	@Autowired
	private KPIDenPointServiceImpl denPointService;
	@Autowired
	private MeasurePointServiceImpl measurePointService;
    @Autowired
    private EnergyAccountUnitServiceImpl energyaccountunitService;
    @Autowired
    private KPIRecordServiceImpl kpiRecordService;
    @Autowired
    private MeasurePointValueMonthServiceImpl monthValueService;
    @Autowired
    private MeasurePointValueYearServiceImpl yearValueService;
    @Autowired
    private KPIKindServiceImpl kindService;
    @Autowired
    private EnergyCconsumptionUnitServiceImpl unitService;
    @Autowired
    private CmpIndustryKindServiceImpl cmpKindService;
    @Autowired
    private MeasureUnitServiceImpl measureUnitService;
    @Autowired
    private EnergyServiceImpl energyService;
    @Autowired
    private MeaterialServiceImpl meaterialService;
    
	@RequestMapping(value = "list")
	public String index(Model model, HttpServletRequest request,String name) throws SQLException {
		Context context = new HttpServletRequestContext(request);
		LimitFactory limitFactory = new TableLimitFactory(context);
		TableLimit limit = new TableLimit(limitFactory);
		limit.setRowAttributes(1000000000, 25);
		HashMap<String,String> queryMap=new HashMap<String,String>();
		queryMap.put("name", name);
		queryMap.put("EntID", getEntIDAdd(request));
		
		List<EntKPI> list = entKPIService.selectListByWhere("selectListByWhere", queryMap);
		
		model.addAttribute("list1", list);
		model.addAttribute("totalRows", list.size());
		model.addAttribute("name", name);
		
		/*页面权限值,*/
        String menuUrl=("entenergy");
        this.buttonMenu(menuUrl);
		model.addAttribute("add", add);
		model.addAttribute("del", del);
		model.addAttribute("upd", upd);
		model.addAttribute("excel", excel);
		/*end*/

		return "entkpi/list";
	}
	
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		return "entkpi/edit";
	}

	@RequestMapping(value = "/delete/{ID}")
	public String delete(@PathVariable("ID") String ID, RedirectAttributes redirectAttributes) throws SQLException {
		int id = Integer.parseInt(ID);
		entKPIService.delete(id);
		
		HashMap queryParam = new HashMap();
		queryParam.put("KPIID", id);
		
		List<KPINumPoint> numList = numPointService.selectListByWhere("selectListByWhere", queryParam);
		for(KPINumPoint point : numList) {
			numPointService.delete(point.getID());
		}
		
		List<KPIDenPoint> denList = denPointService.selectListByWhere("selectListByWhere", queryParam);
		for(KPIDenPoint point : denList) {
			denPointService.delete(point.getID());
		}

		redirectAttributes.addFlashAttribute("message", "删除成功");
		return "redirect:/entkpi/list";
	}

	@RequestMapping(value = "/edit/{ID}", method = RequestMethod.GET)
	public String edit(@PathVariable String ID, Model model) throws SQLException {
		int id=Integer.parseInt(ID);
		EntKPI  list=entKPIService.selectOneRecord(id);
		
		HashMap queryParam = new HashMap();
		queryParam.put("KPIID", ID);
		
		List<KPINumPoint> numList = numPointService.selectListByWhere("selectListByWhere", queryParam);
		List<KPIDenPoint> denList = denPointService.selectListByWhere("selectListByWhere", queryParam);
		
		model.addAttribute("entKPI",list );
		model.addAttribute("denMeasurePoint", denList);
		model.addAttribute("numMeasurePoint", numList);
		
		return "entkpi/edit";
	}

	@RequestMapping(value = "/save")
	public String save(
			EntKPI entKPI, BindingResult br, Model model,
			RedirectAttributes redirectAttributes, HttpServletRequest request) throws SQLException {
		String unit=this.getEntIDAdd(request);
		entKPI.setEntID(Integer.parseInt(unit));
		
		List<KPINumPoint> oldNumPoints = null;
		List<KPIDenPoint> oldDenPoints = null;
		
		if(entKPI.getID()!=null)
		{ 
			entKPIService.update(entKPI);
			HashMap param = new HashMap();
			param.put("KPIID", entKPI.getID());
			
			oldNumPoints = numPointService.selectListByWhere("selectListByWhere", param);
			oldDenPoints = denPointService.selectListByWhere("selectListByWhere", param);
		}
		else
		{
			entKPI.setEntID(Integer.parseInt(unit));
			entKPIService.insert(entKPI);
		}
		
		List<Integer> numIds = new ArrayList<Integer>();
		List<Integer> denIds = new ArrayList<Integer>();
		
		String numSize = request.getParameter("numSize");
		String denSize = request.getParameter("denSize");
		String numMaxSize = request.getParameter("numMaxSize");
		String denMaxSize = request.getParameter("denMaxSize");
		int size = Integer.parseInt(numSize);
		int maxSize = Integer.parseInt(numMaxSize);
		for(int i=1; size > 0 && i<maxSize; i++) {
			String pointID = request.getParameter("Num_MeasureID"+i);
			if(!StringUtils.isEmpty(pointID)) {
				String numID = request.getParameter("Num_KPIPoint"+i);
				String numDir = request.getParameter("Num_DIR"+i);
				KPINumPoint kpiPoint = new KPINumPoint();
				if(!StringUtils.isEmpty(numID)) {
					kpiPoint.setID(Integer.parseInt(numID));
				}
				kpiPoint.setCalDir(numDir.equals("minus")?0:1);
				kpiPoint.setKPIID(entKPI.getID());
				kpiPoint.setPointID(Integer.parseInt(pointID));
				
				if(kpiPoint.getID() > 0) {
					numPointService.update(kpiPoint);
					numIds.add(kpiPoint.getID());
				}else {
					numPointService.insert(kpiPoint);
				}
				size--;
			}
		}
		
		size = Integer.parseInt(denSize);
		maxSize = Integer.parseInt(denMaxSize);
		for(int i=1; size > 0 && i<maxSize; i++) {
			String pointID = request.getParameter("Den_MeasureID"+i);
			if(!StringUtils.isEmpty(pointID)) {
				String denID = request.getParameter("Den_KPIPoint"+i);
				String denDir = request.getParameter("Den_DIR"+i);

				KPIDenPoint kpiPoint = new KPIDenPoint();
				if(!StringUtils.isEmpty(denID)) {
					kpiPoint.setID(Integer.parseInt(denID));
				}
				kpiPoint.setCalDir(denDir.equals("minus")?0:1);
				kpiPoint.setKPIID(entKPI.getID());
				kpiPoint.setPointID(Integer.parseInt(pointID));
				
				if(kpiPoint.getID() > 0) {
					denPointService.update(kpiPoint);
					denIds.add(kpiPoint.getID());
				}else {
					denPointService.insert(kpiPoint);
				}

				size--;
			}
		}
		
		if(oldNumPoints != null) {
			for(KPINumPoint point : oldNumPoints) {
				if(!numIds.contains(point.getID())) {
					numPointService.delete(point.getID());
				}
			}
		}

		if(oldDenPoints != null) {
			for(KPIDenPoint point : oldDenPoints) {
				if(!denIds.contains(point.getID())) {
					denPointService.delete(point.getID());
				}
			}
		}

		redirectAttributes.addFlashAttribute("message", "成功");
		
		return "redirect:/entkpi/list";
	}
	
	@RequestMapping(value="/kpirpt")
	public String rpt(Model model, HttpServletRequest request) throws SQLException {
		String entId = this.getEntIDAdd(request);
		String dimension = request.getParameter("timedimension");
		String year = request.getParameter("search_startYear");
		String month = request.getParameter("search_startMonth");
		String[] ids = request.getParameterValues("entKPIIds");
		
		if(StringUtils.isEmpty(dimension)) {
			dimension = "year";
		}
		if(StringUtils.isEmpty(year)) {
			year = ""+getCurrentYear();
		}
		if(StringUtils.isEmpty(month)) {
			int m = getCurrentMonth();
			month = ""+getCurrentYear()+"-"+(m>9?m:"0"+m)+"-01";
		}
		
		HashMap<String,Object> queryMap=new HashMap<String,Object>();
		
		List<Map<String,Object>> rpt = new ArrayList<Map<String,Object>>();
		
		if(dimension != null && ids != null) {
			for(String idStr : ids) {
				Map<String, Object> record = new HashMap<String, Object>();

				int id = Integer.parseInt(idStr);
				EntKPI entKPI = entKPIService.selectOneRecord(id);
				
				record.put("Name", entKPI.getEntKPIName());
				
				queryMap.put("KPIID", entKPI.getKPIID());
				List<KPIRecord> records = kpiRecordService.selectListByWhere("selectTopListByWhere", queryMap);
				for(KPIRecord r : records) {
					KPIKind k = r.getKPIKind();
					if(k == null) {
						k = kindService.selectOneRecord(r.getKindID());
					}
					record.put(k.getCode(), r.getKvalue());
				}
				
				queryMap.put("KPIID", entKPI.getID());
				List<KPINumPoint> NumPoints = numPointService.selectListByWhere("selectListByWhere", queryMap);
				List<KPIDenPoint> DenPoints = denPointService.selectListByWhere("selectListByWhere", queryMap);
				
				Double numValue = calculatorValue(dimension, year, month, NumPoints);
				Double denValue = calculatorValue(dimension, year, month, DenPoints);
				
				record.put("NumValue", numValue);
				record.put("DenValue", denValue);
				record.put("Value", numValue/(denValue<0.0000000001?1:denValue));
				
				rpt.add(record);
			}
		}
		
		Map<String, String> kpiKind = new HashMap<String, String>();
		List<KPIKind> kinds = kindService.selectList();
		for(KPIKind kind : kinds) {
			kpiKind.put(kind.getCode(), kind.getName());
		}
		
		queryMap.clear();
		queryMap.put("EntID", entId);
		List<EntKPI> list = entKPIService.selectListByWhere("selectListByWhere", queryMap);
		
		model.addAttribute("KPIKind", kpiKind);
		model.addAttribute("RPTItems", rpt);
		model.addAttribute("EntKPIList", list);
		model.addAttribute("dimension", dimension);
		model.addAttribute("search_startYear", year);
		model.addAttribute("search_startMonth", month);
		model.addAttribute("selected_kpi", buildJSArray(ids));
		if(dimension != null) {
			if(dimension.equals("year")) {
				model.addAttribute("KPITime", year);
			}else {
				model.addAttribute("KPITime", (month==null?"":month.substring(0, 7)));
			}
		}
		return "/entkpi/kpirpt";
	}
	
	
	private Double calculatorValue(String dimension, String year, String month, List points) throws SQLException {
		Double Value = 0.0;
		for(Object p : points) {
			IKPIPoint point = (IKPIPoint)p;
			MeasureUnit detUnit = null;
			EntKPI entKPI = point.getEntKPI();
			if(entKPI == null) {
				entKPI = entKPIService.selectOneRecord(point.getKPIID());
			}
			EnergyKPI energyKPI = entKPI.getEnergyKPI();
			if(energyKPI == null) {
				energyKPI = energyKPIService.selectOneRecord(entKPI.getKPIID());
			}
			if(point instanceof KPINumPoint) {
				detUnit = energyKPI.getStandardNumUnit();
			}else{
				detUnit = energyKPI.getStandardDenUnit();
			}
			Float value = getMeasurePointValue(point, detUnit, dimension, year, month);
			if(value != null) {
				if(point.getCalDir() == 1) {
					
					Value += value;
				}else{
					Value -= value;
				}
			}
		}
		
		return Value;
	}
	
	private Float getMeasurePointValue(IKPIPoint point, MeasureUnit detUnit, String dimension, String year, String month) throws SQLException {
		HashMap<String,Object> queryMap=new HashMap<String,Object>();
		
		Float result = 0.0f;
		if(dimension.equals("year")) {
			queryMap.put("MeasurePointID", point.getPointID());
			queryMap.put("year", year+"-01-01");
			List<MeasurePointValueYear> values = yearValueService.selectListByWhere("selectListByWhere1", queryMap);
			if(values.size() > 1) {
				System.out.println("数据问题，这里预期只有一条记录，但出现了多条记录");
			}
			if(values.size() > 0) {
				for(MeasurePointValueYear value : values) {
					try {
						MeasureUnit srcUnit = getValueUnit(value);
						result += UnitTools.unitConvert(value.getEntityAdjValue(), srcUnit, detUnit);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				return result;
			}
			
		}else{
			queryMap.put("MeasurePointID", point.getPointID());
			
			queryMap.put("Month", month);

			List<MeasurePointValueMonth> values =  monthValueService.selectListByWhere("selectListByWhere", queryMap);
			if(values.size() > 1) {
				System.out.println("数据问题，这里预期只有一条记录，但出现了多条记录");
			}
			if(values.size() > 0) {
				for(MeasurePointValueMonth value : values) {
					try {
						MeasureUnit srcUnit = getValueUnit(value);
						result += UnitTools.unitConvert(value.getEntityAdjValue(), srcUnit, detUnit);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				return result;
			}
		}
		
		return null;
	}
	
	private MeasureUnit getValueUnit(MeasurePointValueYear value) {
		if(value.getMeasureunit() != null) {
			return value.getMeasureunit();
		}
		if(value.getMeasurePoint().getKind() == 1) {  //能源
			EntEnergy energy = value.getEntenergy();
			if(energy == null) {
				if(value.getEntenergyList() != null) {
					energy = value.getEntenergyList().get(0);
				}
			}
			if(energy != null) {
				return energy.getMeasureunit();
			}
		}else{  //物料
			EntMeater meater = value.getEntmeater();
			if(meater != null) {
				return meater.getMeaterial().getUnit();
			}
		}
		
		return null;
	}
	
	private MeasureUnit getValueUnit(MeasurePointValueMonth value) {
		if(value.getMeasureunit() != null) {
			return value.getMeasureunit();
		}
		try {
			if(value.getMeasurePoint().getKind() == 1) {  //能源
				EntEnergy energy = value.getEntenergy();
				if(energy != null) {
					if(energy.getMeasureunit() != null) {
						return energy.getMeasureunit();
					}else{
						Energy e = energyService.selectOneRecord(energy.getEnergyID());
						if(e.getUnit() != null) {
							return e.getUnit();
						}else{
							return measureUnitService.selectOneRecord(e.getUnitID());
						}
					}
				}
			}else{  //物料
				EntMeater meater = value.getEntmeater();
				if(meater != null) {
					Meaterial meaterial = meater.getMeaterial();
					if(meaterial == null) {
						meaterial = meaterialService.selectOneRecord(meater.getMeaterID());
					}
					if(meaterial.getUnit() != null) {
						return meaterial.getUnit();
					}else{
						return measureUnitService.selectOneRecord(meaterial.getUnitID());
					}
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		return null;
	}
	
	private String buildJSArray(String[] list) {
		String r = "[";
		
		if(list != null) {
			for(int i=0; i<list.length; i++) {
				if(i > 0) {
					r += ",";
				}
				r+=list[i];
			}
		}
		r += "]";
		
		return r;
	}
	
	@RequestMapping(value="/selectMeasure")
	@ResponseBody
	public JSONArray measureList(Model model, HttpServletRequest request) throws SQLException {
		JSONArray list = new JSONArray();

		String kpi = request.getParameter("kpi");
		if(StringUtils.isEmpty(kpi)) {
			return list;
		}
		
		int kpiid = Integer.parseInt(kpi);
		EnergyKPI energyKPI = energyKPIService.selectOneRecord(kpiid);
		
		Integer unitId = null;
		String type = request.getParameter("type");
		if(type.equals("num")) {
			unitId = energyKPI.getStandardNumUnit().getStandardUnitID();
		}else if(type.equals("den")){
			unitId = energyKPI.getStandardDenUnit().getStandardUnitID();
		}
		
		JSONObject json = new JSONObject();
		json.put("id", 0);
		json.put("text", "-- 请选择 --");
		list.add(json);
		
		
		String entID = getEntIDAdd(request);
		EnergyAccountUnit accountUnit = energyaccountunitService.selectOneR(Integer.parseInt(entID));
		HashMap param = new HashMap();
		param.put("EntID", accountUnit.getID());
		if(unitId != null) {
			param.put("StandardUnit", unitId);
		}
		param.put("Kind", 1); //能源规划点
		switch(energyKPI.getKindID()) {
		case 1:  //单位产品综合能耗
		case 2:  //单位产品能源单耗
			param.put("Kind", 1);  //物料规划点
			break;
		case 3:  //综合能耗
		case 4:  //介质单耗
			//能源规划点
			break;
		}
		
		List<MeasurePoint> measures = measurePointService.selectListByWhere("selectListByUnit", param);
		for(MeasurePoint measure : measures) {
			json = new JSONObject();
			json.put("id", measure.getID());
			if(measure.getEnergyAccountUnit() != null) {
				json.put("text", measure.getEnergyAccountUnit().getName() + "-" + measure.getName());
			}else{
				json.put("text", "未绑定核算单元-" + measure.getName());
			}
			list.add(json);
		}
		
		return list;
	}
	
	@RequestMapping(value="/selectEnergyKPI")
	@ResponseBody
	public JSONArray selectKPI(HttpServletRequest request, Model model) throws SQLException {
		JSONArray list = new JSONArray();

		JSONObject json = null;
		
		Integer cmpKindID = null;
		String entID = this.getEntIDAdd(request);
		if(!StringUtils.isEmpty(entID)) {
			int id = Integer.parseInt(entID);
			EnergyCconsumptionUnit unit = unitService.selectOneRecord(id);
			cmpKindID = unit.getCmpKIndID();
		}
		try{
			HashMap queryParam = new HashMap();
			if(cmpKindID != null && cmpKindID.intValue() > 0) {
				json = new JSONObject();
				json.put("id", 0);
				json.put("text", "-- 请选择  --");
				
				list.add(json);

				List<Integer> cmpIDs = new ArrayList<Integer>();
				cmpIDs.add(cmpKindID);
				cmpIDs = getCmpKindList(cmpKindID, cmpIDs);
				if(cmpIDs.size() == 1) {
					queryParam.put("cmpKindID", cmpIDs.get(0));
				}else if(cmpIDs.size() > 1) {
					String ids = "";
					for(int i=0; i<cmpIDs.size(); i++) {
						if(i>0) {
							ids += ",";
						}
						ids += cmpIDs.get(i);
					}
					queryParam.put("cmpKindIDs", cmpIDs);
				}
				
				List<EnergyKPI> kpiList = energyKPIService.selectListByWhere("selectListByWhere", queryParam);
				
				for(EnergyKPI kpi : kpiList) {
					json = new JSONObject();
					json.put("id", kpi.getID());
					json.put("text", kpi.getName());
					
					list.add(json);
				}

			}else {
				json = new JSONObject();
				json.put("id", 0);
				json.put("text", "-- 请为企业设置对标行业  --");
				
				list.add(json);

			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	private List<Integer> getCmpKindList(int parent, List<Integer> ids) throws SQLException {
		HashMap param = new HashMap();
		param.put("PID", parent);
		
		List<CmpIndustryKind> list = cmpKindService.selectListByWhere("selectListByWhere", param);
		for(CmpIndustryKind c : list) {
			ids.add(c.getID());
			getCmpKindList(c.getID(), ids);
		}
		
		return ids;
	}
}
