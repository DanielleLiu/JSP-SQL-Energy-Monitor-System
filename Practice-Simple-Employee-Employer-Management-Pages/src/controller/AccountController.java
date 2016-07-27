package sbzy.enterpriseems.controller;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

import sbzy.enterpriseems.model.domain.Account;
import sbzy.enterpriseems.model.domain.AccountEntList;
import sbzy.enterpriseems.model.domain.AccountForLogin;
import sbzy.enterpriseems.model.domain.AccountRoleList;
import sbzy.enterpriseems.model.domain.EnergyCconsumptionUnit;
import sbzy.enterpriseems.model.domain.Account;
import sbzy.enterpriseems.model.domain.EnergyUseSort;
import sbzy.enterpriseems.model.domain.EntEnergy;
import sbzy.enterpriseems.model.domain.EntRoleList;
import sbzy.enterpriseems.model.domain.MeasurePoint;
import sbzy.enterpriseems.model.domain.MeasurePointValueDay;
import sbzy.enterpriseems.model.domain.MeasurePointValueMonth;
import sbzy.enterpriseems.model.domain.Role;
import sbzy.enterpriseems.model.domain.UnitComon;
import sbzy.enterpriseems.model.service.impl.AccountEntListServiceImpl;
import sbzy.enterpriseems.model.service.impl.AccountRoleListServiceImpl;
import sbzy.enterpriseems.model.service.impl.AccountServiceImpl;
import sbzy.enterpriseems.model.service.impl.EnergyCconsumptionUnitServiceImpl;
import sbzy.enterpriseems.model.service.impl.EntRoleListServiceImpl;
import sbzy.enterpriseems.model.service.impl.MeasurePointServiceImpl;
import sbzy.enterpriseems.model.service.impl.MeasurePointValueDayServiceImpl;
import sbzy.enterpriseems.model.service.impl.MeasurePointValueMonthServiceImpl;
import sbzy.enterpriseems.model.service.impl.PermissionServiceImpl;
import sbzy.enterpriseems.model.service.impl.RoleServiceImpl;
import sbzy.enterpriseems.model.support.EnumSupport.Role_Permission;

/**
 * 控制类 何罗柱 2015-08-06
 */
@Controller
@RequestMapping("/account")
public class AccountController extends BaseController {

	public static final String entityPath       = "/account";

	public static final String entityPageFolder = "account";
	@Autowired
	private AccountServiceImpl accountService;
	//用户所属企业
	@Autowired
	private AccountEntListServiceImpl accountentlistService;
	//角色
	@Autowired
	private RoleServiceImpl roleService;
	//提取顶层菜单信息
	@Autowired
	private PermissionServiceImpl permissionService;
	@Autowired
	private EnergyCconsumptionUnitServiceImpl energycconsumptionunitService;
	//用户拥有角色
	@Autowired
	private AccountRoleListServiceImpl accountrolelistService;
	//企业拥有角色
	@Autowired
	private EntRoleListServiceImpl entrolelistService;
	/*@RequestMapping(value = "list")
	public String index(Model model, HttpServletRequest request)
			throws SQLException {
		Map<String, Object> map = WebUtils.getParametersStartingWith(request,
				"search_");
		Context context = new HttpServletRequestContext(request);
		LimitFactory limitFactory = new TableLimitFactory(context);
		TableLimit limit = new TableLimit(limitFactory); 
		limit.setRowAttributes(1000000000, 25);
		//String EntID = this.getEntID(request);
		List<AccountEntList> objList = accountentlistService.getListForEditPage(map);
		model.addAttribute("accountList", objList);
		model.addAttribute("totalRows", objList.size());
		return entityPageFolder + "/list";


	}
*/
	@RequestMapping(value = "list")
    public String index(Model model, HttpServletRequest request)
            throws SQLException {

        Context context = new HttpServletRequestContext(request);
        LimitFactory limitFactory = new TableLimitFactory(context);
        TableLimit limit = new TableLimit(limitFactory);
        limit.setRowAttributes(1000000000, 25);
        //String EntID = this.getEntID(request);
        String EntID = this.getEntID(request,"t.EntID");
		HashMap<String,String> queryMap=new HashMap<String,String>();
		// 用户的企业编号
		String entid = this.getEntID(request);
		queryMap.put("EntID", entid);
		String Kind=this.getKind(request);
		List<Account> objList=null;
		if(Kind.equals("3"))
		{
		objList = accountService.selectListByWhere("selectList", new HashMap());
		}
		else{
			objList = accountService.selectListByWhere("selectListbyent", queryMap);
		}
        model.addAttribute("accountList", objList);
        model.addAttribute("totalRows", objList.size());
        
        EnumMap<Role_Permission, Integer> list1=permissionService.getButtons(entityPageFolder);
        
        int i = list1.size();
        for (Role_Permission aLight : Role_Permission.values()) {

        	
            System.out.println("[key=" + aLight.name() + ",value="
            + list1.get(aLight) + "]");
            if(aLight.ordinal()==Role_Permission.add.ordinal())
            System.out.println("值为："+aLight.getValue());
        }
        
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
	 * 为编辑页面提供基本支持信息
	 *
	 * @param userID
	 * @param model
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping(value = "/edit/{ID}/{num}", method = RequestMethod.GET)
	public String edit(@PathVariable String ID,@PathVariable String num, Model model)
			throws SQLException {
		model.addAttribute("isUpdate", "Y");
		HashMap<String, String> map = new HashMap<String, String>();
		// map.put("id", userID);
		int id = Integer.parseInt(ID);
		Account obj = accountService.selectOneRecord(id);
		model.addAttribute("account", obj);
		model.addAttribute("num", num);
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
	public String save(@RequestParam("tionUnit") String tionUnit,@RequestParam("roleid") String roleid,
			boolean State,
			Account account, BindingResult br, Model model,
			RedirectAttributes redirectAttributes,HttpServletRequest request) throws SQLException {
		account.setAccount(account.getAccount().trim());
		int t;
		if(State==true){t=1;account.setState(t);}else{t=0;account.setState(t);}
			if (account.getID() > 0) {
				//暂时停止修改
				accountService.update(account);
				//根据用户编号删除用户角色、用户所属企业。
				HashMap<String, Integer> map = new HashMap<String, Integer>();
				map.put("accountid", account.getID());
				accountentlistService.selectListByWhere("deletebyaccounid", map);
				accountrolelistService.selectListByWhere("deletebyaccounid", map);
				//从新添加数据
				//判断是否选择了企业
				if(tionUnit!=null&&!tionUnit.equals(""))
				{
					String [] nuntis=tionUnit.split(",");
						AccountEntList ententity=new AccountEntList();
						ententity.setAccountID(account.getID());
						//循环添加用户所属企业
					for(String unitis:nuntis)
					{
						ententity.setEnergyCconsumptionUnitID(Integer.parseInt(unitis));
						accountentlistService.insert(ententity);
					}
				}
				//判断是否选择了角色
				if(roleid!=null&&!roleid.equals(""))
				{
					String [] roleids=roleid.split(",");
						//用户角色对象
						AccountRoleList RoleList =new AccountRoleList();
						RoleList.setAccountID(account.getID());
						//循环添加用户所属企业
					for(String roleiorids:roleids)
					{
						RoleList.setRoleID(Integer.parseInt(roleiorids));
						accountrolelistService.insert(RoleList);
					}
				}
				redirectAttributes.addFlashAttribute("message", "修改成功");
			//新增操作
			} else {
				accountService.insert(account);
				//用户所属企业添加数据
				HashMap<String, String> map = new HashMap<String, String>();
				//查询刚刚添加进去的用户编号
				List<Account> objList = accountService.selectListByWhere("selmaxaccont", map);
				//判断是否选择了企业
				if(tionUnit!=null&&!tionUnit.equals(""))
				{
					String [] nuntis=tionUnit.split(",");
						AccountEntList ententity=new AccountEntList();
						ententity.setAccountID(objList.get(0).getID());
						//循环添加用户所属企业
					for(String unitis:nuntis)
					{
						ententity.setEnergyCconsumptionUnitID(Integer.parseInt(unitis));
						accountentlistService.insert(ententity);
					}
				}
				//判断是否选择了角色
				if(roleid!=null&&!roleid.equals(""))
				{
					String [] roleids=roleid.split(",");
						//用户角色对象
						AccountRoleList RoleList =new AccountRoleList();
						RoleList.setAccountID(objList.get(0).getID());
						//循环添加用户所属企业
					for(String roleiorids:roleids)
					{
						RoleList.setRoleID(Integer.parseInt(roleiorids));
						accountrolelistService.insert(RoleList);
					}
				}
			
			redirectAttributes.addFlashAttribute("message", "成功");
		}
		return "redirect:" + entityPath + "/list";
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
	public String delete(@PathVariable("ID") int ID,
			RedirectAttributes redirectAttributes) throws SQLException {
		// User user = accountService.getUser(id);
		accountService.delete(ID);
		//根据主键ID删除用户所属企业
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("accountid", ID);
		accountentlistService.selectListByWhere("deletebyaccounid", map);
		accountrolelistService.selectListByWhere("deletebyaccounid", map);
		redirectAttributes.addFlashAttribute("message", "删除成功");
		return "redirect:" + entityPath + "/list";
		
	}

	/**
	 * 按名称作为条件查询
	 *
	 * @param search_TKPIKind
	 * @return
	 */
	@RequestMapping(value = "/selectOne")
	public String selectRecode(String search_Account,
			HttpServletRequest request, Model model) throws SQLException {

		Context context = new HttpServletRequestContext(request);
		LimitFactory limitFactory = new TableLimitFactory(context);
		TableLimit limit = new TableLimit(limitFactory);
		limit.setRowAttributes(1000000000, 25);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("account", search_Account);
		map.put("unitname", request.getParameter("unitname"));
		// 用户的企业编号
		String entid = this.getEntID(request);
		String Kind=this.getKind(request);
		List<Account> objList =null;
		if(Kind.equals("3"))
		{
		objList = accountService.selectListByWhere("selectListByWhere", map);
		}
		else{
			map.put("EntID", entid);
		 objList= accountService.selectListByWhere("selectListbyent", map);
		}
		
		
		model.addAttribute("accountList", objList);
		model.addAttribute("totalRows", objList.size());
		model.addAttribute("name", search_Account);
		model.addAttribute("name", search_Account);
		model.addAttribute("unitname", request.getParameter("unitname"));
		/*页面权限值,*/
		String menurl=entityPageFolder;
        this.buttonMenu(menurl);
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
	
	
	
	@RequestMapping(value = "/add")
	public String add(Model model)  throws SQLException{
		// model.addAttribute("parentAreaStandInfos",
		// areaStandInfoService.find());
		model.addAttribute("num", "Y");
		return entityPageFolder + "/edit";
	}

	/**
	 * 选择顶级菜单，根据角色的不同
	 * 王佰宏
	 * 2015-09-10
	 * @param model
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping(value = "/login")
	public String login(Model model,HttpServletRequest request) throws SQLException {
		HashMap<String,String> map=new HashMap<String,String>();
		HttpSession session = request.getSession();
		String RoleID = getRoleID(request);
		//String RoleID = "17";
		//RoleID="(RoleID=26 or RoleID=27)";
		map.put("RoleID", RoleID);

		List<HashMap> list = permissionService.selectListHashMapByWhere(
				"selectTopMenu", map);
		model.addAttribute("tops",list);
		String RealName = session.getAttribute("RealName").toString();
		model.addAttribute("realName",RealName);
		// areaStandInfoService.find());
		return "/index";
	}
	
	
	@RequestMapping(value="/clear", produces = "text/plain;charset=utf-8", method = RequestMethod.POST)
	@ResponseBody
	public String clear(HttpSession session) {
		
		String strValue="1";
		try
		{
			session.removeAttribute("EntID");
			session.removeAttribute("RoleID");
			session.removeAttribute("RealName");
			session.removeAttribute("Kind");
			session.removeAttribute("AccID");
		}
		catch(Exception e)
		{
			strValue="0";
		}
	
		return strValue;
		
	}
	/**
	 * 登录检查用户的合法性
	 * 王佰宏
	 * 2015-08-26
	 * @param model
	 * @param userName 用户名
	 * @param passWord 密码
	 * @return 
	 */
	@RequestMapping(value="/checkValid", produces = "text/plain;charset=utf-8", method = RequestMethod.POST)
	@ResponseBody
	public String checkValid(@RequestParam(value = "userName", defaultValue = "@") String userName, @RequestParam(value = "passWord", defaultValue = "@") String passWord,@RequestParam String code, HttpServletRequest request,HttpSession session) {

		String count = "0";
		//检查验证码
		//HttpSession session = request.getSession();
		String olecode=(String)session.getAttribute("rand");
		if(!code.contains(olecode))
			return "-1";
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userAccount", userName);
		map.put("passWord", passWord);
		
		
		List<AccountRoleList> list = null;
		try {
			list = accountrolelistService
					.selectListByWhere("checkAccount", map);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		// 合法用户
		int rows = list.size();
		if (list.size() > 0) {
			String RoleID = "(";
			String EntID = "(";// 作为查询使用
			String RealName = "";
			Integer Kind = 0;// 用户种类
			Integer AccID = 0;// 用户账号ID,主要是在日志等功能
			String EntIDAdd = "";// 作为插入使用
			for (int i = 0; i < rows; i++) {
				AccountRoleList hm = list.get(i);
				Kind = hm.getKind();
				AccID = hm.getAccountID();

				if ((rows - 1) == i)// 最后一个
				{
					EntID = EntID + "EntID=" + hm.getEntID().toString() + ")";
					EntIDAdd = hm.getEntID().toString();
				} else {
					EntID = EntID + "EntID=" + hm.getEntID().toString()
							+ " or ";
				}

				if ((rows - 1) == i) {
					RoleID = RoleID + "RoleID=" + hm.getRoleID().toString() + ")";
				} else {
					RoleID = RoleID + "RoleID=" + hm.getRoleID().toString()
							+ " or ";
				}

				RealName = hm.getRealName();

			}
			// 设置Session
			session.setAttribute("EntID", EntID);// 用户所属企业
			session.setAttribute("EntIDAdd", EntIDAdd);//
			session.setAttribute("RoleID", RoleID);// 用户拥有角色
			session.setAttribute("RealName", RealName);// 用户真实姓名
			session.setAttribute("Kind", Kind);// 用户种类，如果为3表示超级管理员
			session.setAttribute("AccID", AccID);// 日志等功能中使用
			session.setAttribute("isEnterprise", "0");// 企业登录；0为国能中心登录.
			// 设置为合法用户
			count = "1";
		}
		return count;
	}


	@RequestMapping("/validata")
	@ResponseBody
	public UnitComon  validata(Account account) throws SQLException{
		UnitComon unitComon=new UnitComon();
		if(account.getID()==0){//表示页面为添加页面
			unitComon.setAdd(true);
		}else{//表示页面为修改页面
			unitComon.setAdd(false);
			 Account enersy= accountService.selectOneRecord(account.getID());
			    HashMap<String,String> map=new HashMap<String,String>();
			    map.put("account",enersy.getAccount());
			    unitComon.setData(map);
			
		}
		List<Account> users = accountService.selectListByEntryWhere("selectListByEntryWhere", account);
		if(users.isEmpty()){
			unitComon.setUnquine(true);
		}


		account=null;
		return unitComon;
	}

	//下拉多选企业
	@RequestMapping(value = "/getAvailableRoles", produces = "text/plain;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	public String getAvailableRoles(@RequestParam(value = "pkId", defaultValue = "") String  pkId, HttpServletRequest request)
					throws SQLException {
		//用户种类，如果为3表示超级管理员
		String kind=this.getKind(request);
		//用户拥有的企业
		String entid=this.getEntID(request,"ID");
		if (kind.equals("3")) {
			return energycconsumptionunitService.getAvailableRolesAsJSON(pkId).toString();
		} 
		else if (!kind.equals("3")) {
			return energycconsumptionunitService.getAvailableRolesAsJSONbyrole(entid,pkId).toString();
		} 
		else {
			return new JSONArray().toString();
		}
	}


	//下拉多角色
	@RequestMapping(value = "/getAvailableRoles1", produces = "text/plain;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	public String getAvailableRoles1(@RequestParam(value = "pkId", defaultValue = "") String  pkId, HttpServletRequest request)
					throws SQLException {
		//用户种类，如果为3表示超级管理员
		String kind=this.getKind(request);
		//用户的企业编号
		String entid=this.getEntID(request);
		if (kind.equals("3")) {
			return roleService.getAvailableRolesAsJSON(pkId).toString();
		} 
		else if (!kind.equals("3")) {
			return getAvailableRolesAsJSONbyrole(entid,pkId).toString();
		} 
		else {
			return new JSONArray().toString();
		}
	}
	 //编辑查询用户所属企业进行默认勾选	
    public List<Role> selectListbyacconid(String pkId)throws SQLException {
    	List<Role> roles=new ArrayList<Role>();
        if(pkId!=null)
        {
        	//编辑默认勾选的
            HashMap<String, String> map = new HashMap<String, String>();
        	map.put("roleids", pkId);
        	 roles = roleService.selectListByWhere("sellistbyroleids",map);
        }
        return roles;
    }
	//下拉根据角色ids查询角色数据
	public JSONArray getAvailableRolesAsJSONbyrole(String roleids,String pkId)
	        throws SQLException {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("roleids", roleids);
	    List<EntRoleList> entries = entrolelistService.selectListByWhere("selectListByWhererole",map);
	  //编辑查询用户所属企业进行默认勾选	
	    List<Role> roles=selectListbyacconid(pkId);
	    JSONArray rootContainer = new JSONArray();
	    rootContainer.put(getPlaceholderForNoneJSONObject());
	    for (EntRoleList entry : entries){
	        JSONObject obj = new JSONObject();
	        obj.put("id", entry.getRoleID());
	        obj.put("text", entry.getRole().getName());
	        if(roles.size()>0)
	    	{
	    	  for (Role entrys : roles) {
	    		  if(entrys.getID()==entry.getRoleID()){
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
	    placeholderForNone.put("text", "------------请选择角色-----------");
	    return placeholderForNone;
	}
}
