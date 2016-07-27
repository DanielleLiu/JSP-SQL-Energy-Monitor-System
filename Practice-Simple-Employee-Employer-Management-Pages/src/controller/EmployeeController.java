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

import sbzy.enterpriseems.model.domain.Account;
import sbzy.enterpriseems.model.domain.Employee;
import sbzy.enterpriseems.model.domain.Employee;
import sbzy.enterpriseems.model.service.impl.EmployeeServiceImpl;


@Controller
@RequestMapping("/employee")
public class EmployeeController extends BaseController {
	public static final String        entityPath       = "/employee";
//Set Path, folder name
	public static final String        entityPageFolder = "employee";
//类似于该entity/class 别名
	@Autowired
	private EmployeeServiceImpl EmployeeService;
	
	@RequestMapping(value = "/list")
	public String index(Model model, HttpServletRequest request)
			throws SQLException {
		String searchname=request.getParameter("searchname");
		String code=request.getParameter("code");
		String pid=request.getParameter("pid");
		Map<String, Object> map = WebUtils.getParametersStartingWith(request,
				"search_");
		Context context = new HttpServletRequestContext(request);
		LimitFactory limitFactory = new TableLimitFactory(context);
		TableLimit limit = new TableLimit(limitFactory);
		limit.setRowAttributes(1000000000, 25);
		HashMap queryMap = new HashMap();
		queryMap.put("searchname", searchname);
		queryMap.put("code",code);
		queryMap.put("pid",pid);
		//queryMap.put("parentName",parentName);
		//用自己service里的method
		List<Employee> objList = EmployeeService.selectListByWhere("selectList", queryMap);
		model.addAttribute("employeeList", objList);
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
		//String sex=(request.getParameter("sex"));
		//model.addAttribute("sex",sex);
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
		EmployeeService.delete(id);
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
		Employee object=EmployeeService.selectOneRecord(id);

		model.addAttribute("employee",object );
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
	public String save(Employee currEmployee, BindingResult br, Model model, RedirectAttributes redirectAttributes)
					throws SQLException {
		if (currEmployee.getId()!=null && currEmployee.getId() > 0) {
			EmployeeService.update(currEmployee);
		} else {
			EmployeeService.insert(currEmployee);
		} 
		redirectAttributes.addFlashAttribute("message", "成功");
		return "redirect:" + entityPath + "/list";
	}
	
    //下拉树菜单选择
    @RequestMapping(value = "/getAvailableParents", produces = "text/plain;charset=utf-8", method = RequestMethod.GET)
    @ResponseBody
    public String getAvailableParents(
            @RequestParam(value = "id", defaultValue = "0") int id)
            throws SQLException {
        Employee entryCurrent = this.getLogicalQueryEntity(id);
        if (entryCurrent != null) {
            return EmployeeService.getNestedAvailableParentsAsJSON(entryCurrent).toString();
        } else {
            return new JSONArray().toString();
        }
    }

    public Employee getLogicalQueryEntity(int idCurrent) throws SQLException {
        Employee entryCurrent = null; //initialize 为null
        if (idCurrent == 0){//未定义过，设置为0，root
            entryCurrent = new Employee();
            entryCurrent.setId(0);
        }
        if (idCurrent > 0) { //如果已定义过，根据已有id找出对应数据
            entryCurrent = EmployeeService.selectOneRecord(idCurrent);
        }
        return entryCurrent;
    }
    //找出目前Employee,idCurrent pass 0 新建一个，已有ID，选出对应Employee

}
