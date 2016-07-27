package sbzy.enterpriseems.controller;

import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ejb.Remove;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.util.StringUtil;
import org.extremecomponents.table.context.Context;
import org.extremecomponents.table.context.HttpServletRequestContext;
import org.extremecomponents.table.limit.LimitFactory;
import org.extremecomponents.table.limit.TableLimit;
import org.extremecomponents.table.limit.TableLimitFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import sbzy.enterpriseems.model.domain.AlarmIndustry;
import sbzy.enterpriseems.model.domain.CmpIndustryKind;
import sbzy.enterpriseems.model.domain.Energy;
import sbzy.enterpriseems.model.domain.EnergyAlarmKPI;
import sbzy.enterpriseems.model.domain.EnergyCconsumptionUnit;
import sbzy.enterpriseems.model.domain.EnergyKPI;
import sbzy.enterpriseems.model.domain.EnergyValue;
import sbzy.enterpriseems.model.domain.EntEnergy;
import sbzy.enterpriseems.model.domain.EntMeater;
import sbzy.enterpriseems.model.domain.MeasurePoint;
import sbzy.enterpriseems.model.domain.AlarmEnt;
import sbzy.enterpriseems.model.domain.Meaterial;
import sbzy.enterpriseems.model.domain.Regionalism;
import sbzy.enterpriseems.model.service.impl.AlarmEntServiceImpl;
import sbzy.enterpriseems.model.service.impl.EnergyAlarmKPIServiceImpl;
import sbzy.enterpriseems.model.service.impl.EnergyCconsumptionUnitServiceImpl;
import sbzy.enterpriseems.model.service.impl.EnergyServiceImpl;
import sbzy.enterpriseems.model.service.impl.EnergyValueServiceImpl;
import sbzy.enterpriseems.model.service.impl.EntEnergyServiceImpl;
import sbzy.enterpriseems.model.service.impl.EntMeaterServiceImpl;
import sbzy.enterpriseems.model.service.impl.MeasurePointServiceImpl;
import sbzy.enterpriseems.model.service.impl.AlarmEntServiceImpl;
import sbzy.enterpriseems.model.service.impl.MeaterialServiceImpl;
import sbzy.enterpriseems.model.service.impl.RegionalismServiceImpl;

/**
 * 控制类 董春良 2015-07-31
 */
@Controller
@RequestMapping("/alarment")
public class AlarmEntController extends BaseController {
	public static final String        entityPath       = "/alarment";
//Set Path, folder name
	public static final String        entityPageFolder = "alarment";
//类似于该entity/class 别名
	@Autowired
	private AlarmEntServiceImpl alarmentService;
	@Autowired
	private EnergyAlarmKPIServiceImpl energyalarmkpiService;
	@Autowired
	private EnergyCconsumptionUnitServiceImpl energycconsumptionunitService;
	@Autowired
    private EntEnergyServiceImpl entenergyService;
	@RequestMapping(value = "list") //陈列已有数据
	public String index(Model model, HttpServletRequest request)
			throws SQLException {
		
		Map<String, Object> map = WebUtils.getParametersStartingWith(request,
				"search_");
		Context context = new HttpServletRequestContext(request);
		LimitFactory limitFactory = new TableLimitFactory(context);
		TableLimit limit = new TableLimit(limitFactory);
		limit.setRowAttributes(1000000000, 25);
		HashMap queryMap = new HashMap();
		
		//把map里已有的信息拿出来，
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			queryMap.put(entry.getKey(), entry.getValue());
		}
		
		//用自己service里的method
		List<AlarmEnt> objList = alarmentService.selectListByWhere("selectList", queryMap);
		model.addAttribute("alarmentList", objList);
		model.addAttribute("totalRows", objList.size());
		//实现4个功能常规，implemented in all entities
		/*页面权限值,*/
        this.buttonMenu(entityPageFolder);
		model.addAttribute("add", add);
		model.addAttribute("del", del);
		model.addAttribute("upd", upd);
		model.addAttribute("excel", excel);
		/*end*/
		return entityPageFolder + "/list";
	}
	/**
	 * 按名称作为条件查询
	 * @param search_TKPIKind
	 * @return
	 */
	@RequestMapping(value="/selectOne")
	public String selectRecode(String name,HttpServletRequest request,Model model){
		HashMap<String , String> map = new HashMap<String , String>(); 
		map.put("regionname", name);
		List<AlarmEnt> benchmarkRecs = null;
		try {
			//用自己service里的selectListByWhere 
			benchmarkRecs=alarmentService.selectListByWhere("selalarbycmpname", map);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		model.addAttribute("name", name);
		model.addAttribute("alarmentList", benchmarkRecs);
		model.addAttribute("totalRows",benchmarkRecs.size() );	
		return entityPageFolder + "/list";
		//不同人又不同权限可以增加
	}
	/**
	 * 主要提供一些字典信息到操作界面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) {
		// model.addAttribute("parentAreaStandInfos",
		// areaStandInfoService.find());
		return entityPageFolder + "/add";
	}
	
	//企业能源下拉数据集合
	@RequestMapping(value = "/getAreas_2", produces = "text/plain;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	public String getAreas_2( @RequestParam(value = "pkId", defaultValue = "") String pkId) {
		try {
			HashMap queryMap=new HashMap();
			if(pkId.equals("0"))
				pkId="";
			/*queryMap.put("PID", pkId);*/
			//企业能源数据集合
			List<EntEnergy> tionunitList=entenergyService .selectListByWhere("selectlist", queryMap);
			JSONArray jsonArray = organizeAeraJsonTop2(tionunitList.iterator(),pkId);
			return jsonArray.toString();
		} catch (Exception e) {
			return null;
		}
	}
	private JSONArray organizeAeraJsonTop2(Iterator<EntEnergy> areaItertor,String pkId) {
		JSONArray jsonArray = new JSONArray();
		JSONObject jbx = new JSONObject();
		jbx.put("id", "0");
		jbx.put("text", "--请选择--");
		jsonArray.put(jbx);
		while (areaItertor.hasNext()) {
			EntEnergy areaStandInfo = areaItertor.next();
			JSONObject jb = new JSONObject();
			jb.put("id", areaStandInfo.getID());
			jb.put("text", areaStandInfo.getEnterEnergyName());
			jsonArray.put(jb);
			}
		return jsonArray;
	}
	
	/**
	 * 删除数据
	 * 
	 * @param userID
	 * @param redirectAttributes
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping(value = "/delete/{ID}")
	public String delete(@PathVariable("ID") String ID,
			RedirectAttributes redirectAttributes) throws SQLException {
		int id = Integer.parseInt(ID);
		alarmentService.delete(id);
		redirectAttributes.addFlashAttribute("message", "删除成功");
		return "redirect:" + entityPath + "/list";
	}

	/**
	 * 为编辑页面提供基本支持信息
	 * 
	 * @param userID
	 * @param model
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping(value = "/edit/{ID}", method = RequestMethod.GET)
	public String edit(@PathVariable String ID, Model model,HttpServletRequest request)
			throws SQLException {
		model.addAttribute("isUpdate", "Y");
		HashMap<String , String> map = new HashMap<String , String>(); 
		int id=Integer.parseInt(ID);
		//查询数据
		AlarmEnt  list=alarmentService.selectOneRecord(id);

		model.addAttribute("meaterlist",list );
		return entityPageFolder + "/edit";
	}

	/**
	 * 数据保存到数据库中
	 * 
	 * @param userAccount
	 * @param passWord
	 * @param usersInfo
	 * @param br
	 * @param model
	 * @param redirectAttributes
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping(value = "/save")
	public String save(AlarmEnt valuentity, BindingResult br,
			Model model, RedirectAttributes redirectAttributes)
					throws SQLException {
		if(valuentity.getIsstandard()==null)
		{
			valuentity.setIsstandard(false);
		}
		if (valuentity.getId()!=null && valuentity.getId() > 0) {
			alarmentService.update(valuentity);
		} else {
			alarmentService.insert(valuentity);
		}
		redirectAttributes.addFlashAttribute("message", "成功");
		return "redirect:" + entityPath + "/list";
	}


}
