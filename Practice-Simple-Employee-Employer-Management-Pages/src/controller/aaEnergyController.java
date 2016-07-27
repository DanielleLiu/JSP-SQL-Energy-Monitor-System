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

import sbzy.enterpriseems.model.domain.aaCompany;
import sbzy.enterpriseems.model.domain.aaCompanyEnergyRelation;
import sbzy.enterpriseems.model.domain.aaEnergy;
import sbzy.enterpriseems.model.service.impl.aaCompanyEnergyRelationServiceImpl;
import sbzy.enterpriseems.model.service.impl.aaEnergyServiceImpl;


@Controller
@RequestMapping("/aaEnergy")
public class aaEnergyController extends BaseController {
	public static final String        entityPath       = "/aaEnergy";
//Set Path, folder name
	public static final String        entityPageFolder = "aaEnergy";
//类似于该entity/class 别名
	@Autowired
	private aaEnergyServiceImpl aaEnergyService;
	@Autowired
	private aaCompanyEnergyRelationServiceImpl aaCompanyEnergyRelationService;
	
	@RequestMapping(value = "/list")
	public String index(Model model, HttpServletRequest request)
			throws SQLException {
		String search_name=request.getParameter("search_name");
		String code=request.getParameter("code");
		Map<String, Object> map = WebUtils.getParametersStartingWith(request,
				"search_");
		Context context = new HttpServletRequestContext(request);
		LimitFactory limitFactory = new TableLimitFactory(context);
		TableLimit limit = new TableLimit(limitFactory);
		limit.setRowAttributes(1000000000, 25);
		HashMap queryMap = new HashMap();
		queryMap.put("search_name", search_name);
		queryMap.put("code",code);
		//queryMap.put("parentName",parentName);
		//用自己service里的method
		List<aaEnergy> objList = aaEnergyService.selectListByWhere("selectList", queryMap);
		model.addAttribute("aaEnergyList", objList);
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
		aaEnergyService.delete(id);
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
		aaEnergy object=aaEnergyService.selectOneRecord(id);

		model.addAttribute("aaEnergy",object );
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
	public String save(aaEnergy curraaEnergy, BindingResult br, Model model, RedirectAttributes redirectAttributes)
					throws SQLException {
		if (curraaEnergy.getId()!=null && curraaEnergy.getId() > 0) {
			aaEnergyService.update(curraaEnergy);
		} else {
			aaEnergyService.insert(curraaEnergy);
		} 
		redirectAttributes.addFlashAttribute("message", "成功");
		return "redirect:" + entityPath + "/list";
	}
	
	
	/*  @RequestMapping(value = "/getAvailableParents", produces = "text/plain;charset=utf-8", method = RequestMethod.GET)
    @ResponseBody
    public String getAvailableParents(
            @RequestParam(value = "id", defaultValue = "0") int id)
            throws SQLException {
    	aaEnergy entryCurrent = this.getLogicalQueryEntity(id);
        if (entryCurrent != null) {
            return aaEnergyService.getNestedAvailableParentsAsJSON(entryCurrent).toString();
        } else {
            return new JSONArray().toString();
        }
    }
    
    public aaEnergy getLogicalQueryEntity(int idCurrent) throws SQLException {
    	aaEnergy entryCurrent = null;
        if (idCurrent == 0) {
            entryCurrent = new aaEnergy();
            entryCurrent.setId(0);
        }
        if (idCurrent > 0) {
            entryCurrent = aaEnergyService.selectOneRecord(idCurrent);
        }
        return entryCurrent;
    }

    @RequestMapping(value = "/getAvailableParents", produces = "text/plain;charset=utf-8", method = RequestMethod.GET)
    @ResponseBody
    public String getAvailableParents(
            @RequestParam(value = "id", defaultValue = "0") int id)
            throws SQLException {
       
            return getNestedAvailableParentsAsJSON(id).toString();
        
    }*/
    @RequestMapping(value = "/getAvailableEnergies", produces = "text/plain;charset=utf-8", method = RequestMethod.GET)
    @ResponseBody
    public String getAvailableEnergies(
            @RequestParam(value = "id", defaultValue = "0") int id)
            throws SQLException {
    //非树状结构，单层下拉列表，得到任意ID都调用 get 所有available energy并转换为JSON	
            return getNestedAvailableAsJSON(id).toString();
       
    }
    
    //下拉列表得到所有energy
  	public JSONArray getNestedAvailableAsJSON(int id)
  	        throws SQLException {
  		//新建map将得到的companyid(unitid) 放到map
  		HashMap<String, Integer> map = new HashMap<String, Integer>();
  		map.put("unitid", id);
  		//选择出所有能源数据
  	    List<aaEnergy> entries = aaEnergyService.selectListByWhere("selectList",new HashMap());
  	    //根据企业id选择出在CER表中所有该企业相关的object 以便勾选
  	    List<aaCompanyEnergyRelation> energies=aaCompanyEnergyRelationService.selectListByWhere("selectEnergy",map);
  	    
  	   // List<String> energies=aaCompanyEnergyRelationService.selectListByWhere("selectEnergyId",map);
	    
  	  //编辑查询用户所属企业进行默认勾选	
  	    JSONArray rootContainer = new JSONArray();
  	    //如果目前没有energy,表格为空放入唯一占位符 “请选择”
  	    rootContainer.put(getPlaceholderForNoneJSONObject());
  	    for (aaEnergy entry : entries){
  	    	// 根据ID 将所有能源建立为JSON
  	        JSONObject obj = new JSONObject();
  	        obj.put("id", entry.getId());
  	        obj.put("text", entry.getName());
  	        //如果该企业能源不为0，进入nested loop， go through 该企业的所有能源，如果和当前列表能源matchID,打钩 checked
  	        if(energies.size()>0)
  	    	{
  	    	  for (aaCompanyEnergyRelation energy : energies) {
  	    		  //entrey: aaEnergy type; energy: aaCER type
  	    		  if(entry.getId()==energy.getEnergyid()){
  	    			  obj.put("checked","true");
  	    		  }
  	    	  }
  	    	}
  	        rootContainer.put(obj);
  	    }
  	    return rootContainer;
  	}
  	public JSONObject getPlaceholderForNoneJSONObject() {
  	    JSONObject placeholderForNone = new JSONObject();
  	    placeholderForNone.put("id", 0);
  	    placeholderForNone.put("text", "-----请选择-----");
  	    return placeholderForNone;
  	}
}
