package sbzy.enterpriseems.controller;

import java.sql.SQLException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import sbzy.enterpriseems.model.dao.impl.StorageBillDaoImpl;
import sbzy.enterpriseems.model.domain.AlarmRegion;
import sbzy.enterpriseems.model.domain.BillRec;
import sbzy.enterpriseems.model.domain.CmpIndustryKind;
import sbzy.enterpriseems.model.domain.Energy;
import sbzy.enterpriseems.model.domain.EnergyAlarmKPI;
import sbzy.enterpriseems.model.domain.EnergyCconsumptionUnit;
import sbzy.enterpriseems.model.domain.EnergyStorage;
import sbzy.enterpriseems.model.domain.EnergyValue;
import sbzy.enterpriseems.model.domain.EntEnergy;
import sbzy.enterpriseems.model.domain.EntMeater;
import sbzy.enterpriseems.model.domain.MeasurePoint;
import sbzy.enterpriseems.model.domain.MeasureUnit;
import sbzy.enterpriseems.model.domain.StorageBill;
import sbzy.enterpriseems.model.domain.Meaterial;
import sbzy.enterpriseems.model.domain.Regionalism;
import sbzy.enterpriseems.model.domain.StorageEng;
import sbzy.enterpriseems.model.service.impl.BillRecServiceImpl;
import sbzy.enterpriseems.model.service.impl.CmpIndustryKindServiceImpl;
import sbzy.enterpriseems.model.service.impl.EnergyAlarmKPIServiceImpl;
import sbzy.enterpriseems.model.service.impl.EnergyCconsumptionUnitServiceImpl;
import sbzy.enterpriseems.model.service.impl.EnergyServiceImpl;
import sbzy.enterpriseems.model.service.impl.EnergyValueServiceImpl;
import sbzy.enterpriseems.model.service.impl.EntEnergyServiceImpl;
import sbzy.enterpriseems.model.service.impl.EntMeaterServiceImpl;
import sbzy.enterpriseems.model.service.impl.MeasurePointServiceImpl;
import sbzy.enterpriseems.model.service.impl.StorageBillServiceImpl;
import sbzy.enterpriseems.model.service.impl.MeaterialServiceImpl;
import sbzy.enterpriseems.model.service.impl.RegionalismServiceImpl;

/**
 * 控制类 董春良 2015-07-31
 */
@Controller
@RequestMapping("/storagebill")
public class StorageBillController extends BaseController {
	public static final String        entityPath       = "/storagebill";

	public static final String        entityPageFolder = "storagebill";
	@Autowired
	private StorageBillServiceImpl StorageBillService;
	@Autowired
	private EnergyCconsumptionUnitServiceImpl energycconsumptionunitService;
	@Autowired
	private BillRecServiceImpl BillRecService;
	//入库单
	@RequestMapping(value = "listjoin")
	public String listjoin(Model model, HttpServletRequest request)
			throws SQLException {
		//查询条件仓库编号
		String storageID=request.getParameter("storageID");
		//单据编号
		String billNum=request.getParameter("billNum");
		Context context = new HttpServletRequestContext(request);
		LimitFactory limitFactory = new TableLimitFactory(context);
		TableLimit limit = new TableLimit(limitFactory);
		limit.setRowAttributes(1000000000, 25);
		HashMap queryMap = new HashMap();
		String entid = this.getEntID(request,"ID");
		queryMap.put("entid", entid);
		List<EnergyCconsumptionUnit> unitlist = energycconsumptionunitService.selectListByWhere("selbyorgcode", queryMap);
		queryMap.put("orgcode", unitlist.get(0).getOrganizationCode());
		queryMap.put("storageID", storageID);
		queryMap.put("billNum", billNum);
		List<StorageBill> objList = StorageBillService.selectListByWhere("selectListjoin", queryMap);
		model.addAttribute("storageBillList", objList);
		model.addAttribute("totalRows", objList.size());
		this.buttonMenu(entityPageFolder);
		model.addAttribute("add", add);
		model.addAttribute("del", del);
		model.addAttribute("upd", upd);
		model.addAttribute("excel", excel);
		model.addAttribute("biitype", 1);
		model.addAttribute("storageID", storageID);
		model.addAttribute("billNum", billNum);
		return entityPageFolder + "/list";
	}
	//出库单
	@RequestMapping(value = "listout")
	public String listout(Model model, HttpServletRequest request)
			throws SQLException {
		//查询条件仓库编号
				String storageID=request.getParameter("storageID");
				//单据编号
				String billNum=request.getParameter("billNum");
		Context context = new HttpServletRequestContext(request);
		LimitFactory limitFactory = new TableLimitFactory(context);
		TableLimit limit = new TableLimit(limitFactory);
		limit.setRowAttributes(1000000000, 25);
		HashMap queryMap = new HashMap();
		String entid = this.getEntID(request,"ID");
		queryMap.put("entid", entid);
		List<EnergyCconsumptionUnit> unitlist = energycconsumptionunitService.selectListByWhere("selbyorgcode", queryMap);
		queryMap.put("orgcode", unitlist.get(0).getOrganizationCode());
		queryMap.put("storageID", storageID);
		queryMap.put("billNum", billNum);
		List<StorageBill> objList = StorageBillService.selectListByWhere("selectListout", queryMap);
		model.addAttribute("storageBillList", objList);
		model.addAttribute("totalRows", objList.size());
		this.buttonMenu(entityPageFolder);
		model.addAttribute("add", add);
		model.addAttribute("del", del);
		model.addAttribute("upd", upd);
		model.addAttribute("excel", excel);
		model.addAttribute("biitype", -1);
		model.addAttribute("storageID", storageID);
		model.addAttribute("billNum", billNum);
		return entityPageFolder + "/list";
	}
	//盘库单
	@RequestMapping(value = "listset")
	public String listset(Model model, HttpServletRequest request)
			throws SQLException {
		//查询条件仓库编号
				String storageID=request.getParameter("storageID");
				//单据编号
				String billNum=request.getParameter("billNum");
		Context context = new HttpServletRequestContext(request);
		LimitFactory limitFactory = new TableLimitFactory(context);
		TableLimit limit = new TableLimit(limitFactory);
		limit.setRowAttributes(1000000000, 25);
		HashMap queryMap = new HashMap();
		String entid = this.getEntID(request,"ID");
		queryMap.put("entid", entid);
		List<EnergyCconsumptionUnit> unitlist = energycconsumptionunitService.selectListByWhere("selbyorgcode", queryMap);
		queryMap.put("orgcode", unitlist.get(0).getOrganizationCode());
		queryMap.put("storageID", storageID);
		queryMap.put("billNum", billNum);
		List<StorageBill> objList = StorageBillService.selectListByWhere("selectListset", queryMap);
		model.addAttribute("storageBillList", objList);
		model.addAttribute("totalRows", objList.size());
		this.buttonMenu(entityPageFolder);
		model.addAttribute("add", add);
		model.addAttribute("del", del);
		model.addAttribute("upd", upd);
		model.addAttribute("excel", excel);
		model.addAttribute("biitype", 0);
		model.addAttribute("storageID", storageID);
		model.addAttribute("billNum", billNum);
		return entityPageFolder + "/list";
	}
	/**
	 * 主要提供一些字典信息到操作界面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add/{biitype}", method = RequestMethod.GET)
	public String add(@PathVariable("biitype") int biitype,Model model,HttpServletRequest request) {
		model.addAttribute("biitype", biitype);
		return entityPageFolder + "/add";

	}
	/**
	 * 跳转仓库存储能源介质添加页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/addrec/{billid}", method = RequestMethod.GET)
	public String addeng(@PathVariable("billid") String billid,Model model,HttpServletRequest request) {
		model.addAttribute("billid",billid);
		return entityPageFolder + "/addrec";
	}
	/**
	 * 删除数据
	 * 
	 * @param userID
	 * @param redirectAttributes
	 * @return
	 * 
	 * @throws SQLException
	 */
	@RequestMapping(value = "/delete/{ID}")
	public String delete(@PathVariable("ID") String ID,
			RedirectAttributes redirectAttributes) throws SQLException {
		int id = Integer.parseInt(ID);
		StorageBillService.delete(id);
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
	@RequestMapping(value = "/deleterec/{ID}")
	public String deleteeng(@PathVariable("ID") String ID,Model model,
			RedirectAttributes redirectAttributes) throws SQLException {
		int id = Integer.parseInt(ID);

		BillRec  listrec=BillRecService.selectOneR(id);
		BillRecService.delete(id);
		StorageBill  list=StorageBillService.selectOneR(listrec.getBillID());
		HashMap queryMap1= new HashMap();
		queryMap1.put("billid", list.getId());
		List<BillRec> objList = BillRecService.selectListByWhere("selectbybill", queryMap1);
		model.addAttribute("storagebill",list);
		model.addAttribute("objList",objList);
		return entityPageFolder + "/addreclist";
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
		StorageBill  list=StorageBillService.selectOneRecord(id);

		model.addAttribute("storagebill",list );
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
	@RequestMapping(value = "/editrec/{ID}", method = RequestMethod.GET)
	public String editeng(@PathVariable String ID, Model model,HttpServletRequest request)
			throws SQLException {
		model.addAttribute("isUpdate", "Y");
		int id=Integer.parseInt(ID);
		//查询数据
		BillRec  listrec=BillRecService.selectOneR(id);
		model.addAttribute("storagebill",listrec);
		return entityPageFolder + "/editrec";
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
	public String save(StorageBill valuentity, BindingResult br,
			Model model, RedirectAttributes redirectAttributes,HttpServletRequest request)
					throws SQLException, ParseException {
		valuentity.setBillTime(request.getParameter("billTime"));
		valuentity.setBusTime(request.getParameter("busTime"));
		if (valuentity.getId()!=null && valuentity.getId() > 0) {
			StorageBillService.update(valuentity);
		} else {
			StorageBillService.insert(valuentity);
		}
		HashMap queryMap1= new HashMap();
		queryMap1.put("billid", valuentity.getId());
		List<BillRec> objList = BillRecService.selectListByWhere("selectbybill", queryMap1);
		model.addAttribute("objList",objList);
		model.addAttribute("storagebill",valuentity);
		return entityPageFolder + "/addreclist";
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
	@RequestMapping(value = "/saverec")
	public String saveeng(BillRec valuentity, BindingResult br,
			Model model, RedirectAttributes redirectAttributes, HttpServletRequest request)
					throws SQLException, ParseException {

		if (valuentity.getId()!=null&&valuentity.getId()>0) {

			BillRecService.update(valuentity);
		} else {
			//添加仓库存储能源介质
			BillRecService.insert(valuentity);
		}
		StorageBill  list=StorageBillService.selectOneR(valuentity.getBillID());
		HashMap queryMap1= new HashMap();
		queryMap1.put("billid", valuentity.getBillID());
		List<BillRec> objList = BillRecService.selectListByWhere("selectbybill", queryMap1);
		model.addAttribute("objList",objList);
		model.addAttribute("storagebill",list);
		return entityPageFolder + "/addreclist";
	}
	/**
	 * 选择仓库自动生成的单据编号 （年、月、日、3位数字流水号）
	 *
	 * @param userID
	 * @param model
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping(value = "/selbillNum", method = RequestMethod.GET)
	@ResponseBody
	public String selent(Model model,HttpServletRequest request)
			throws SQLException {
		int storageID=Integer.parseInt(request.getParameter("storageID"));
		int year=getCurrentYear();//得到年
		int month=getCurrentMonth();//得到月，因为从0开始的，所以要加1
		Calendar cal = Calendar.getInstance();
		int day=cal.get(Calendar.DAY_OF_MONTH);//得到天
		String newmonth=""+month;
		String newday=""+day;
		if(month<10){
			newmonth="0"+month;
		}
		if(day<10){
			newday="0"+day;
		}
		String date=""+year+newmonth+newday;
		String newstr="";
		if(storageID!=0){
			HashMap map = new HashMap(); 
			map.put("storageID", storageID);
			map.put("billNum", date);
			map.put("billType", request.getParameter("billType"));
			List<StorageBill> objList = StorageBillService.selectListByWhere("selectbybillNum", map);
			if(objList.size()==0){
				newstr=date+"001";
			}else{
				//3位数字流水号
				String billNum= objList.get(0).getBillNum();
				String str=billNum.substring(8);// 首先查询出那个counter值     
				int s=Integer.parseInt(str);     
				s=++s;     
				s=s==1000?1:s;     
				String reslut=s>=10?(s>=100?s+"":"0"+s):"00"+s; 
				newstr=date+reslut;
			}
		}
		return newstr;
	}

















}
