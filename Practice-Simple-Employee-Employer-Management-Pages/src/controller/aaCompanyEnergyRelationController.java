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

import sbzy.enterpriseems.model.domain.aaCompanyEnergyRelation;
import sbzy.enterpriseems.model.service.impl.aaCompanyEnergyRelationServiceImpl;


@Controller
@RequestMapping("/aaCompanyEnergyRelation")
public class aaCompanyEnergyRelationController extends BaseController {
	public static final String        entityPath       = "/aaCompanyEnergyRelation";
//Set Path, folder name
	public static final String        entityPageFolder = "aaCompanyEnergyRelation";
//类似于该entity/class 别名
	@Autowired
	private aaCompanyEnergyRelationServiceImpl aaCompanyEnergyRelationService;
	
	@RequestMapping(value = "/list")
	public String index(Model model, HttpServletRequest request)
			throws SQLException {
		String companyname=request.getParameter("companyname");
		String energyid=request.getParameter("energyid");
	//	String companyid=request.getParameter("companyid");
		//通过企业类型做选择
		Map<String, Object> map = WebUtils.getParametersStartingWith(request,
				"search_");
		Context context = new HttpServletRequestContext(request);
		LimitFactory limitFactory = new TableLimitFactory(context);
		TableLimit limit = new TableLimit(limitFactory);
		limit.setRowAttributes(1000000000, 25);
		HashMap queryMap = new HashMap();
		queryMap.put("companyname",companyname);
		queryMap.put("energyid",energyid);
	//	queryMap.put("companyid", companyid);
		//用自己service里的method
		List<aaCompanyEnergyRelation> objList = aaCompanyEnergyRelationService.selectListByWhere("selectList", queryMap);
		model.addAttribute("aaCompanyEnergyRelationList", objList);
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
	 * 主要提供一些字典信息到操作界面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) {
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
	@RequestMapping(value = "/delete/{ID}")
	public String delete(@PathVariable("ID") String ID,RedirectAttributes redirectAttributes) 
			throws SQLException {
	 	int id = Integer.parseInt(ID);
		aaCompanyEnergyRelationService.delete(id);
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
		aaCompanyEnergyRelation object=aaCompanyEnergyRelationService.selectOneRecord(id);

		model.addAttribute("aaCompanyEnergyRelation",object );
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
	public String save(@RequestParam("energyid") String raw_energyid,aaCompanyEnergyRelation curraaCompanyEnergyRelation, BindingResult br, Model model,HttpServletRequest request , RedirectAttributes redirectAttributes)
					throws SQLException {
		//使用requestparam将aaCER的edit page 上得到的raw_energyid string 多选结果拆分成string array
		String [] energyids=raw_energyid.split(",");
		//如果该企业已在aaCER表中有数据，先全部删除再重新插入新的关系数据
		if (curraaCompanyEnergyRelation.getId()!=null && curraaCompanyEnergyRelation.getId() > 0) 
		{
			HashMap queryMap = new HashMap();
			queryMap.put("companyid", curraaCompanyEnergyRelation.getCompanyid());
			//将当前id有关的所有数据删除
			aaCompanyEnergyRelationService.selectListByWhere("deletebyunitid", queryMap);
		}
		
			// for each loop 插入数据
			for(String energyid:energyids){
				if(Integer.parseInt(energyid)==0){
					//选择请选择项时，energyid=0 ，不进行任何操作，跳出该次 for loop
					continue;
				}
				curraaCompanyEnergyRelation.setEnergyid(Integer.parseInt(energyid));
				//数据逐条插入，每次的companyid相同针对同一个company，没loop一个energy插入一次
				aaCompanyEnergyRelationService.insert(curraaCompanyEnergyRelation);
			} 

		redirectAttributes.addFlashAttribute("message", "成功");
		return "redirect:" + entityPath + "/list";
	}
	

}
