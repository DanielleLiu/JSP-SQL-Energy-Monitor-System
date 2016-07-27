package sbzy.enterpriseems.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import sbzy.enterpriseems.model.domain.BillDetailPage;
import sbzy.enterpriseems.model.domain.EntEnergy;
import sbzy.enterpriseems.model.domain.MeasurePoint;
import sbzy.enterpriseems.model.domain.StockBill;
import sbzy.enterpriseems.model.service.impl.EntEnergyServiceImpl;
import sbzy.enterpriseems.model.service.impl.StockBillServiceImpl;
import sbzy.enterpriseems.model.domain.BillDetail;
import sbzy.enterpriseems.model.service.impl.BillDetailServiceImpl;
import sbzy.enterpriseems.model.domain.StorageEng;
import sbzy.enterpriseems.model.service.impl.StorageEngServiceImpl;
import sbzy.enterpriseems.model.domain.Energy;
import sbzy.enterpriseems.model.service.impl.EnergyServiceImpl;

@Controller
@RequestMapping("/stockbill")
public class StockBillController extends BaseController {
	public static final String entityPath = "/stockbill";
	// Set Path, folder name
	public static final String entityPageFolder = "stockbill";
	// 类似于该entity/class 别名
	@Autowired
	private StockBillServiceImpl StockBillService;
	private int billtype;
	@Autowired
	private BillDetailServiceImpl BillDetailService;
	@Autowired
	private StorageEngServiceImpl StorageEngService;
	@Autowired
	private EntEnergyServiceImpl EntEnergyService;

	@RequestMapping(value = "/list1")
	public String list1(Model model, HttpServletRequest request)
			throws SQLException {
		String billNum = request.getParameter("billNum");
		String storagename = request.getParameter("storagename");
		// String type=request.getParameter("type");
		// 通过企业类型做选择
		Map<String, Object> map = WebUtils.getParametersStartingWith(request,
				"search_");
		Context context = new HttpServletRequestContext(request);
		LimitFactory limitFactory = new TableLimitFactory(context);
		TableLimit limit = new TableLimit(limitFactory);
		limit.setRowAttributes(1000000000, 25);
		HashMap queryMap = new HashMap();
		queryMap.put("billNum", billNum);
		queryMap.put("storagename", storagename);
		queryMap.put("billtype", 1);

		// 用自己service里的method
		List<StockBill> objList = StockBillService.selectListByWhere(
				"selectList", queryMap);
		model.addAttribute("StockBillList", objList);
		model.addAttribute("totalRows", objList.size());
		model.addAttribute("billtype", 1);
		return entityPageFolder + "/list";
	}

	@RequestMapping(value = "/list0")
	public String list0(Model model, HttpServletRequest request)
			throws SQLException {
		String billNum = request.getParameter("billNum");
		String storagename = request.getParameter("storagename");
		Map<String, Object> map = WebUtils.getParametersStartingWith(request,
				"search_");
		Context context = new HttpServletRequestContext(request);
		LimitFactory limitFactory = new TableLimitFactory(context);
		TableLimit limit = new TableLimit(limitFactory);
		limit.setRowAttributes(1000000000, 25);
		HashMap queryMap = new HashMap();
		queryMap.put("billNum", billNum);
		queryMap.put("storagename", storagename);
		queryMap.put("billtype", 0);

		// 用自己service里的method
		List<StockBill> objList = StockBillService.selectListByWhere(
				"selectList", queryMap);
		model.addAttribute("StockBillList", objList);
		model.addAttribute("totalRows", objList.size());
		model.addAttribute("billtype", 0);
		return entityPageFolder + "/list";
	}

	@RequestMapping(value = "/list_1")
	public String index(Model model, HttpServletRequest request)
			throws SQLException {
		String billNum = request.getParameter("billNum");
		String storagename = request.getParameter("storagename");
		// String type=request.getParameter("type");
		// 通过企业类型做选择
		Map<String, Object> map = WebUtils.getParametersStartingWith(request,
				"search_");
		Context context = new HttpServletRequestContext(request);
		LimitFactory limitFactory = new TableLimitFactory(context);
		TableLimit limit = new TableLimit(limitFactory);
		limit.setRowAttributes(1000000000, 25);
		HashMap queryMap = new HashMap();
		queryMap.put("billNum", billNum);
		queryMap.put("storagename", storagename);
		queryMap.put("billtype", -1);

		// 用自己service里的method
		List<StockBill> objList = StockBillService.selectListByWhere(
				"selectList", queryMap);
		model.addAttribute("StockBillList", objList);
		model.addAttribute("totalRows", objList.size());
		model.addAttribute("billtype", -1);
		return entityPageFolder + "/list";
	}

	@RequestMapping(value = "/add/{billtype}", method = RequestMethod.GET)
	public String add(@PathVariable("billtype") String billtype, Model model,
			HttpServletRequest request) {
		model.addAttribute("billtype", billtype);
		return entityPageFolder + "/edit";
	}

	/**
	 * 删除数据
	 * 
	 * @param userID
	 * @param redirectAttributes
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping(value = "/delete/{ID}/{billtype}")
	public String delete(@PathVariable("ID") String ID,
			@PathVariable("billtype") String billtype,
			RedirectAttributes redirectAttributes) throws SQLException {
		int id = Integer.parseInt(ID);
		String listpath;
		int type = Integer.parseInt(billtype);
		if (type == 1) {
			listpath = "/list1";
		} else if (type == 0) {
			listpath = "/list0";
			;
		} else {
			listpath = "/list_1";
		}
		// return "redirect:" + entityPath + "/list";
		StockBillService.delete(id);
		redirectAttributes.addFlashAttribute("message", "删除成功");
		return "redirect:" + entityPath + listpath;
	}

	@RequestMapping(value = "/edit/{ID}/{billtype}", method = RequestMethod.GET)
	public String edit(@PathVariable String ID,
			@PathVariable("billtype") String billtype, Model model,
			HttpServletRequest request) throws SQLException {
		model.addAttribute("billtype", billtype);
		model.addAttribute("isUpdate", "Y");
		HashMap map = new HashMap();
		int id = Integer.parseInt(ID);
		// 查询数据
		map.put("billid", id);
		StockBill object = StockBillService.selectOneRecord(id);
		List<BillDetail> detailList = BillDetailService.selectListByWhere("selectList", map);
		if (!(detailList.isEmpty())) {
			model.addAttribute("BillDetailList", detailList);
		}
		model.addAttribute("StockBill", object);
		return entityPageFolder + "/edit";
	}

	@RequestMapping(value = "/save/{billtype}")
	public String save(@PathVariable("billtype") String billtype,
			StockBill currStockBill, BillDetailPage billDetailList,
			Model model, RedirectAttributes redirectAttributes)
			throws SQLException {
		List<BillDetail> billlist = billDetailList.getBillList();
		String listpath;
		int type = Integer.parseInt(billtype);
		if (type == 1) {
			listpath = "/list1";
		} else if (type == 0) {
			listpath = "/list0";
			;
		} else {
			listpath = "/list_1";
		}
		currStockBill.setBilltype(type);
		
		
		if (currStockBill.getId() != null && currStockBill.getId() > 0) 
		{
			HashMap queryMap = new HashMap();
			queryMap.put("billid", currStockBill.getId());
			// 将当前id有关的所有数据删除
			BillDetailService.selectListByWhere("deletebybillid", queryMap);
			StockBillService.update(currStockBill);
		} 
		else {
			StockBillService.insert(currStockBill);
		}
			for (BillDetail detail : billlist) {
				detail.setBillid(currStockBill.getId());
				BillDetailService.insert(detail);
			}
			
		redirectAttributes.addFlashAttribute("message", "成功");
		return "redirect:" + entityPath + listpath;
		// return "redirect:" + entityPath + "/list1";
	}

	/*
	 * @RequestMapping(value = "/getStorageEnergies", produces =
	 * "text/plain;charset=utf-8", method = RequestMethod.GET)
	 * 
	 * @ResponseBody public String getAvailableCompanies(
	 * 
	 * @RequestParam(value = "id", defaultValue = "0") int id) throws
	 * SQLException {
	 * 
	 * return getStorageEnergiesAsJSON(id).toString(); }
	 * 
	 * //parameter id用于自动勾选已选择/已保存的id public JSONArray
	 * getStorageEnergiesAsJSON(int id) throws SQLException { // 获得所有企业
	 * HashMap<String, Integer> map=new HashMap<String,Integer>();
	 * map.put("storageid",id); //map.put("storageid",1); List<StorageEng>
	 * entries = StorageEngService.selectListByWhere("selectbystorageid",map);
	 * System.out.println(entries.isEmpty()); JSONArray rootContainer = new
	 * JSONArray(); rootContainer.put(this.getPlaceholderForNoneJSONObject());
	 * for (StorageEng entry : entries) { JSONObject obj = new JSONObject();
	 * obj.put("id", entry.getId()); //HashMap<String, Integer> currmap=new
	 * HashMap<String,Integer>(); //currmap.put("id",entry.getEnergyID()):
	 * Energy currEnergy=EnergyService.selectOneRecord(entry.getEnergyID());
	 * obj.put("text", currEnergy.getName()); if (entry.getId()==id){
	 * obj.put("checked","true");} rootContainer.put(obj); }
	 * 
	 * return rootContainer; }
	 * 
	 * public JSONObject getPlaceholderForNoneJSONObject() { JSONObject
	 * placeholderForNone = new JSONObject(); placeholderForNone.put("id", 0);
	 * placeholderForNone.put("text", "-----请选择-----");
	 * placeholderForNone.put("children", new JSONArray()); return
	 * placeholderForNone; }
	 * 
	 * 
	 * }
	 */
	
	@RequestMapping(value = "/getStorageEnergies", produces = "text/plain;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	public String getStorageEnergies(Model model, HttpServletRequest request) {
		String id = request.getParameter("id");
		//int id=Integer.parseInt(idString);
		try {
			HashMap queryMap = new HashMap();
			if (id != null && id != "") {
				//queryMap.put("storageID", 1);
				int idd=Integer.parseInt(id);
				queryMap.put("storageID", idd);
				
				List<StorageEng> areaStandInfo = StorageEngService.selectListByWhere("selectbystorageid", queryMap);
				JSONArray jsonArray = organizeAeraJsonTop1(areaStandInfo.iterator());
				return jsonArray.toString();
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	private JSONArray organizeAeraJsonTop1(Iterator<StorageEng> areaItertor)
			throws SQLException {
		JSONArray jsonArray = new JSONArray();
		JSONObject jbx = new JSONObject();
		jbx.put("id", "0");
		jbx.put("text", "--请选择--");
		jsonArray.put(jbx);
		while (areaItertor.hasNext()) {
			StorageEng areaStandInfo = areaItertor.next();
			JSONObject jb = new JSONObject();
			jb.put("id", areaStandInfo.getId());
			EntEnergy currEnergy = EntEnergyService.selectOneRecord(areaStandInfo.getEnergyID());
			jb.put("text", currEnergy.getEnterEnergyName());
			jsonArray.put(jb);
		}
		return jsonArray;
	}
}