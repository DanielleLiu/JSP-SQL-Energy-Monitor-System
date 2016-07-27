package sbzy.enterpriseems.controller;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.Format;
import java.text.ParseException;
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

import sbzy.enterpriseems.model.dao.impl.EnergyStorageDaoImpl;
import sbzy.enterpriseems.model.domain.AlarmRegion;
import sbzy.enterpriseems.model.domain.CmpIndustryKind;
import sbzy.enterpriseems.model.domain.Energy;
import sbzy.enterpriseems.model.domain.EnergyAccountUnit;
import sbzy.enterpriseems.model.domain.EnergyAlarmKPI;
import sbzy.enterpriseems.model.domain.EnergyCconsumptionUnit;
import sbzy.enterpriseems.model.domain.EnergyValue;
import sbzy.enterpriseems.model.domain.EntEnergy;
import sbzy.enterpriseems.model.domain.EntMeater;
import sbzy.enterpriseems.model.domain.MeasurePoint;
import sbzy.enterpriseems.model.domain.EnergyStorage;
import sbzy.enterpriseems.model.domain.MeasureUnit;
import sbzy.enterpriseems.model.domain.Meaterial;
import sbzy.enterpriseems.model.domain.Regionalism;
import sbzy.enterpriseems.model.domain.StorageEng;
import sbzy.enterpriseems.model.service.impl.CmpIndustryKindServiceImpl;
import sbzy.enterpriseems.model.service.impl.EnergyAlarmKPIServiceImpl;
import sbzy.enterpriseems.model.service.impl.EnergyCconsumptionUnitServiceImpl;
import sbzy.enterpriseems.model.service.impl.EnergyServiceImpl;
import sbzy.enterpriseems.model.service.impl.EnergyValueServiceImpl;
import sbzy.enterpriseems.model.service.impl.EntEnergyServiceImpl;
import sbzy.enterpriseems.model.service.impl.EntMeaterServiceImpl;
import sbzy.enterpriseems.model.service.impl.MeasurePointServiceImpl;
import sbzy.enterpriseems.model.service.impl.EnergyStorageServiceImpl;
import sbzy.enterpriseems.model.service.impl.MeasureUnitServiceImpl;
import sbzy.enterpriseems.model.service.impl.MeaterialServiceImpl;
import sbzy.enterpriseems.model.service.impl.RegionalismServiceImpl;
import sbzy.enterpriseems.model.service.impl.StorageEngServiceImpl;

/**
 * 控制类 董春良 2015-07-31
 */
@Controller
@RequestMapping("/energystorage")
public class EnergyStorageController extends BaseController {
	public static final String        entityPath       = "/energystorage";

	public static final String        entityPageFolder = "energystorage";

	@Autowired
	private EnergyStorageServiceImpl energystorageService;
	@Autowired
	private StorageEngServiceImpl StorageEngService;
	@Autowired
    private MeasureUnitServiceImpl measureunitService;
	@Autowired
	private EnergyCconsumptionUnitServiceImpl energycconsumptionunitService;
	@Autowired
	private EntEnergyServiceImpl entenergyService;
	@RequestMapping(value = "list")
	public String index(Model model, HttpServletRequest request)
			throws SQLException {
		 String unitid=request.getParameter("unitid");
		Context context = new HttpServletRequestContext(request);
		LimitFactory limitFactory = new TableLimitFactory(context);
		TableLimit limit = new TableLimit(limitFactory);
		limit.setRowAttributes(1000000000, 25);
		HashMap queryMap = new HashMap();
		queryMap.put("unitid", unitid);
		String entid = this.getEntID(request,"ID");
		queryMap.put("entid", entid);
		List<EnergyCconsumptionUnit> unitlist = energycconsumptionunitService.selectListByWhere("selbyorgcode", queryMap);
		queryMap.put("orgcode", unitlist.get(0).getOrganizationCode());
		List<EnergyStorage> objList = energystorageService.selectListByWhere("selectList", queryMap);
		model.addAttribute("storageList", objList);
		model.addAttribute("totalRows", objList.size());
		
		this.buttonMenu(entityPageFolder);
   		model.addAttribute("add", add);
   		model.addAttribute("del", del);
   		model.addAttribute("upd", upd);
   		model.addAttribute("excel", excel);
   		model.addAttribute("unitid", unitid);
		return entityPageFolder + "/list";
	}
	/**
	 * 主要提供一些字典信息到操作界面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) {
		return entityPageFolder + "/add";
	}
	
	/**
	 * 跳转仓库存储能源介质添加页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/addeng/{storid}", method = RequestMethod.GET)
	public String addeng(@PathVariable("storid") String storid,Model model,HttpServletRequest request) {
		model.addAttribute("storid",storid);
		return entityPageFolder + "/addeng";
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
		energystorageService.delete(id);
		//删除字表仓库存储能源介质
		HashMap queryMap1= new HashMap();
		queryMap1.put("storageid", id);
		List<StorageEng> objList = StorageEngService.selectListByWhere("selectbystorageid", queryMap1);
		for(StorageEng eng:objList){
			StorageEngService.delete(eng.getId());
		}
		redirectAttributes.addFlashAttribute("message", "删除成功");
		return "redirect:" + entityPath + "/list";
	}
	/**
	 * 仓库存储能源介质删除数据
	 * 
	 * @param userID
	 * @param redirectAttributes
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping(value = "/deleteeng/{ID}")
	public String deleteeng(@PathVariable("ID") String ID,Model model,
			RedirectAttributes redirectAttributes) throws SQLException {
		int id = Integer.parseInt(ID);
		
		StorageEng  listeng=StorageEngService.selectOneR(id);
		StorageEngService.delete(id);
		EnergyStorage  list=energystorageService.selectOneR(listeng.getStorageID());
		HashMap queryMap1= new HashMap();
		queryMap1.put("storageid", listeng.getStorageID());
		List<StorageEng> objList = StorageEngService.selectListByWhere("selectbystorageid", queryMap1);
		
		model.addAttribute("energystorage",list);
		model.addAttribute("objList",objList);
		return entityPageFolder + "/addenglist";
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
		int id=Integer.parseInt(ID);
		//查询数据
		EnergyStorage  list=energystorageService.selectOneR(id);
		model.addAttribute("energystorage",list);
		return entityPageFolder + "/edit";
	}
	/**
	 * 为编辑页面提供基本支持信息
	 * 
	 * @param userID
	 * @param model
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping(value = "/editeng/{ID}", method = RequestMethod.GET)
	public String editeng(@PathVariable String ID, Model model,HttpServletRequest request)
			throws SQLException {
		model.addAttribute("isUpdate", "Y");
		int id=Integer.parseInt(ID);
		//查询数据
		StorageEng  listeng=StorageEngService.selectOneR(id);
		model.addAttribute("energystorage",listeng);
		return entityPageFolder + "/editeng";
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
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/save")
	public String save(@RequestParam("energyID") String energyID,EnergyStorage valuentity, BindingResult br,
			Model model, RedirectAttributes redirectAttributes, HttpServletRequest request)
					throws SQLException, ParseException {
		
		HashMap queryMap1= new HashMap();
		queryMap1.put("storageid", valuentity.getId());
		int ok =Integer.parseInt(request.getParameter("ok"));
		//企业能源介质
		String[] energyid = energyID.split(",");
		if (ok==2) {
			energystorageService.update(valuentity);
			//查询该仓库的能源介质
			List<StorageEng> List = StorageEngService.selectListByWhere("selectbystorageid", queryMap1);
			//删除该仓库的能源介质
			StorageEngService.selectListByWhere("deletebystorageid", queryMap1);
			for(StorageEng eng:List){
				for (String energyids : energyid) {
					 if(eng.getEnergyID()==Integer.parseInt(energyids)){
						 StorageEngService.insert(eng); 
					 }
				}
			}
			for (String energyids : energyid) {
				if(Integer.parseInt(energyids)==0){
					continue;
				}
				queryMap1.put("entid", energyids);
				List<StorageEng> Listent = StorageEngService.selectListByWhere("selectbyentid", queryMap1);
				if(Listent.size()==0){
					StorageEng stoeng=new StorageEng();
					stoeng.setStorageID(valuentity.getId());
					stoeng.setEnergyID(Integer.parseInt(energyids));
					//根据企业能源介质查询分子分母
					HashMap queryMap = new HashMap();
					queryMap.put("enid", energyids);
					List<MeasureUnit> num = measureunitService.selectListByWhere("selectnum", queryMap);
					List<MeasureUnit> den = measureunitService.selectListByWhere("selectden", queryMap);
					stoeng.setStandardNumID(num.get(0).getID());
					stoeng.setStandardDenID(den.get(0).getID());
					StorageEngService.insert(stoeng);
				}
			}
		} else {
			//添加能源仓库数据
			energystorageService.insert(valuentity);
			//根据仓库选择的企业能源介质添加仓库存储能源介质数据
			
			for (String energyids : energyid) {
				if(Integer.parseInt(energyids)==0){
					continue;
				}
				StorageEng stoeng=new StorageEng();
				stoeng.setStorageID(valuentity.getId());
				stoeng.setEnergyID(Integer.parseInt(energyids));
				//根据企业能源介质查询分子分母
				HashMap queryMap = new HashMap();
				queryMap.put("enid", energyids);
				List<MeasureUnit> num = measureunitService.selectListByWhere("selectnum", queryMap);
				List<MeasureUnit> den = measureunitService.selectListByWhere("selectden", queryMap);
				stoeng.setStandardNumID(num.get(0).getID());
				stoeng.setStandardDenID(den.get(0).getID());
				StorageEngService.insert(stoeng);
			}
			
		}
		List<StorageEng> objList = StorageEngService.selectListByWhere("selectbystorageid", queryMap1);
		model.addAttribute("objList",objList);
		model.addAttribute("energystorage",valuentity);
		return entityPageFolder + "/addenglist";
	}



	//下拉选择仓库入库、出库、盘库单号
	@RequestMapping(value = "/getAreas_Storage", produces = "text/plain;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	public String getAreas_Storage( @RequestParam(value = "pkId") int pkId,HttpServletRequest request) {
		try {
			HashMap queryMap = new HashMap();
			String entid = this.getEntID(request,"ID");
			queryMap.put("entid", entid);
			List<EnergyCconsumptionUnit> unitlist = energycconsumptionunitService.selectListByWhere("selbyorgcode", queryMap);
			queryMap.put("orgcode", unitlist.get(0).getOrganizationCode());
			List<EnergyStorage> objList = energystorageService.selectListByWhere("selectList", queryMap);
			JSONArray jsonArray=new JSONArray();
			/*if(pkId==1){
				jsonArray = organizeAeraJsonTop4(objList.iterator());
			}
			else if(pkId==0){
				jsonArray = organizeAeraJsonTop5(objList.iterator());
			}
			else{
				jsonArray = organizeAeraJsonTop6(objList.iterator());
			}*/
			return jsonArray.toString();
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 仓库存储能源介质数据保存到数据库中
	 * 
	 * @param userAccount
	 * @param passWord
	 * @param usersInfo
	 * @param br
	 * @param model
	 * @param redirectAttributes
	 * @return
	 * @throws SQLException
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/saveeng")
	public String saveeng(StorageEng valuentity, BindingResult br,
			Model model, RedirectAttributes redirectAttributes, HttpServletRequest request)
					throws SQLException, ParseException {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String createTime=request.getParameter("createTime");
		valuentity.setCreateTime(sdf.parse(createTime));
		valuentity.setCurrentValue(valuentity.getBeginValue());
		if (valuentity.getId()!=null&&valuentity.getId()>0) {
			
			StorageEngService.update(valuentity);
		} else {
			//添加仓库存储能源介质
			StorageEngService.insert(valuentity);
				}
		EnergyStorage  list=energystorageService.selectOneR(valuentity.getStorageID());
		HashMap queryMap1= new HashMap();
		queryMap1.put("storageid", valuentity.getStorageID());
		List<StorageEng> objList = StorageEngService.selectListByWhere("selectbystorageid", queryMap1);
		model.addAttribute("energystorage",list);
		model.addAttribute("objList",objList);
		return entityPageFolder + "/addenglist";
	}
	
    /**
     * 验证主键不能重复
     *
     * @param userID
     * @param model
     * @return
     * @throws SQLException
     */
    @RequestMapping(value = "/selregion/{ID}", method = RequestMethod.GET)
    @ResponseBody
    public String selregion(@PathVariable String ID, Model model)
            throws SQLException {
    	 String newstr="ok";
    	 int id = Integer.parseInt(ID);
    	 if(id!=0){
    		 EnergyStorage  list=energystorageService.selectOneR(id);
    		 if(list!=null){
    			 newstr="no"; 
    		 }
    	       
         }
        return newstr;
    }
	
    /**
     * 验证选择的企业能源介质单位是否统一
     *
     * @param userID
     * @param model
     * @return
     * @throws SQLException
     */
    @RequestMapping(value = "/selent/{ID}", method = RequestMethod.GET)
    @ResponseBody
    public String selent(@PathVariable String ID, Model model)
            throws SQLException {
    	 String newstr="no";
    	 String[] energyid = ID.split(",");
    	 
    	 Map mapnum = new HashMap();  
    	 Map mapden = new HashMap();  
    	  
			for (String energyids : energyid) {
				if(Integer.parseInt(energyids)==0){
					continue;
				}
				//根据企业能源介质查询分子分母
				HashMap queryMap = new HashMap();
				queryMap.put("enid", energyids);
				List<MeasureUnit> num = measureunitService.selectListByWhere("selectnum", queryMap);
				List<MeasureUnit> den = measureunitService.selectListByWhere("selectden", queryMap);
				mapnum.put(num.get(0).getID(), den.get(0).getID());
				mapden.put(den.get(0).getID(), num.get(0).getID());
			}
			if(mapnum.size()==1&&mapden.size()==1){
				newstr="ok";
			}
	    	 
        return newstr;
    }
	
  //选择仓库存储能源介质
  	@RequestMapping(value = "/getAreas_storageeng", produces = "text/plain;charset=utf-8", method = RequestMethod.GET)
  	@ResponseBody
  	public String getAreas_unitorstorage( @RequestParam(value = "id", defaultValue = "") String id,HttpServletRequest request) {
  		try {

  			 // 获得可选项的列表
  			HashMap queryMap1= new HashMap();
  			queryMap1.put("bill",id);
  			List<StorageEng> objList = StorageEngService.selectListByWhere("selectbybill", queryMap1);
  			JSONArray jsonArray = organizeAeraJsonunitorstorage(objList.iterator());
  			return jsonArray.toString();
  		} catch (Exception e) {
  			return null;
  		}

  	}

  	private JSONArray organizeAeraJsonunitorstorage(Iterator<StorageEng> areaItertor) throws SQLException {
  		JSONArray jsonArray = new JSONArray();
  		JSONObject jbx = new JSONObject();
  		jbx.put("id", "0");
  		jbx.put("text", "--请选择--");
  		jsonArray.put(jbx);

  		while (areaItertor.hasNext()) {

  			StorageEng areaStandInfo = areaItertor.next();
  			JSONObject jb = new JSONObject();
  			jb.put("id", areaStandInfo.getId());
  			EntEnergy  list=entenergyService.selectOneRecord(areaStandInfo.getEnergyID());
  			jb.put("text", list.getEnterEnergyName());
  			jsonArray.put(jb);
  		}
  		return jsonArray;
  	}

	
	

	
	
}
