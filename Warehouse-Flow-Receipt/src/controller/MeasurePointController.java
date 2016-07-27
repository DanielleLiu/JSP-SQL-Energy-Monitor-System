package sbzy.enterpriseems.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import sbzy.enterpriseems.model.domain.EnergyAccountUnit;
import sbzy.enterpriseems.model.domain.EnergyMeasurePoint;
import sbzy.enterpriseems.model.domain.EnergyNet;
import sbzy.enterpriseems.model.domain.EntEnergy;
import sbzy.enterpriseems.model.domain.MeasurePoint;
import sbzy.enterpriseems.model.domain.MeasurePointValueMonth;
import sbzy.enterpriseems.model.domain.PicMeasurePoint;
import sbzy.enterpriseems.model.domain.Tree;
import sbzy.enterpriseems.model.service.impl.EnergyAccountUnitServiceImpl;
import sbzy.enterpriseems.model.service.impl.EnergyMeasurePointServiceImpl;
import sbzy.enterpriseems.model.service.impl.EnergyNetServiceImpl;
import sbzy.enterpriseems.model.service.impl.EntEnergyServiceImpl;
import sbzy.enterpriseems.model.service.impl.MeasurePointServiceImpl;
import sbzy.enterpriseems.model.service.impl.MeasurePointValueDayServiceImpl;
import sbzy.enterpriseems.model.service.impl.MeasurePointValueMonthServiceImpl;
import sbzy.enterpriseems.model.service.impl.MeasurePointValueYearServiceImpl;
import sbzy.enterpriseems.model.service.impl.PicMeasurePointServiceImpl;

/**
 * 控制器类
 * 李昊
 * 2015年8月4日
 * 【规划计量点】 TMeasurePoint
 */
@Controller
@RequestMapping("/measurepoint")
public class MeasurePointController  extends BaseController {

    /** 获取服务对象. */
    @Autowired
    private MeasurePointServiceImpl measurepointService;
    @Autowired
    private EnergyMeasurePointServiceImpl energyMeasurePointService;
    @Autowired
    private EnergyAccountUnitServiceImpl energyaccountunitService;
    @Autowired
    private EnergyNetServiceImpl energynetService;
    @Autowired
    private PicMeasurePointServiceImpl picMeasurePointService;
    @Autowired
    private MeasurePointValueDayServiceImpl valueDayService;
    @Autowired
    private MeasurePointValueMonthServiceImpl valueMonthService;
    @Autowired
    private MeasurePointValueYearServiceImpl valueYearService;
	@Autowired
    private MeasurePointValueMonthServiceImpl measurepointvaluemonthService;
	@Autowired
	private EntEnergyServiceImpl entenergyService;
    
    
    /**
     * 获取数据显示为列表，可全部获取或按是否包含字符搜索.
     * 
     * @param model
     *            the model
     * @param request
     *            the request
     * @return the string
     * @throws SQLException
     *             the SQL exception
     */
    @RequestMapping(value = "/list")//能源规划点的初始化显示页面
    public String listDefault(Model model, HttpServletRequest request,String search_name,String unitname,String energy_name)
            throws SQLException {
        //Map<String, Object> map = WebUtils.getParametersStartingWith(request,"search_");
        Context context = new HttpServletRequestContext(request);
        LimitFactory limitFactory = new TableLimitFactory(context);
        TableLimit limit = new TableLimit(limitFactory);
        limit.setRowAttributes(1000000000, 25);
        // 转换为HashMap
        HashMap<String,String> queryMap=new HashMap<String,String>();
        if(search_name != null) {
        	queryMap.put("name", search_name);
        }
        
        String KindID=this.getKind(request);
        String EntID=this.getEntIDAdd(request);
        //String CEntID=this.getEntID(request,"e.PID");
        List<MeasurePoint> measurepoints=null;
        if(KindID.equals("3")){
        	measurepoints = measurepointService.selectListByWhere("selectListByWhere", queryMap);	
        }else{
        
            EnergyAccountUnit unit = energyaccountunitService.selectOneR(Integer.parseInt(EntID));
        	queryMap.put("BarCode", unit.getBarCode()+"%");
        	//squeryMap.put("CEntID", CEntID);
        	queryMap.put("unitname", unitname);
        	queryMap.put("energy_name", energy_name);
         	measurepoints = measurepointService.selectListByWhere("selectListB", queryMap);	
        	
        }
       
        model.addAttribute("measurepointInfo", measurepoints);
        model.addAttribute("totalRows", measurepoints.size());
        model.addAttribute("kindOpts", MeasurePoint.getKindOpts());
       //页面值
        String menuUrl=("measurepoint");
        this.buttonMenu(menuUrl);
		model.addAttribute("add", add);
		model.addAttribute("del", del);
		model.addAttribute("upd", upd);
		model.addAttribute("excel", excel);
		/*end*/
        return "measurepoint/list";
    }
    

    @RequestMapping(value = "/energylist")//能源规划点的初始化显示页面
    public String indexenergylist(Model model, HttpServletRequest request,String search_name)
            throws SQLException {
        //Map<String, Object> map = WebUtils.getParametersStartingWith(request,"search_");
        Context context = new HttpServletRequestContext(request);
        LimitFactory limitFactory = new TableLimitFactory(context);
        TableLimit limit = new TableLimit(limitFactory);
        limit.setRowAttributes(1000000000, 25);
        // 转换为HashMap
        HashMap<String,String> queryMap=new HashMap<String,String>();
        if(search_name != null) {
        	queryMap.put("name", "%"+search_name+"%");
        }
        
        String KindID=this.getKind(request);
        String EntID=this.getEntIDAdd(request);
        String CEntID=this.getEntID(request,"e.PID");
        List<MeasurePoint> measurepoints=null;
        if(KindID.equals("3")){
        	measurepoints = measurepointService.selectListByWhere("selectListByWhere", queryMap);	
        }else{
        
            EnergyAccountUnit unit = energyaccountunitService.selectOneR(Integer.parseInt(EntID));
        	queryMap.put("BarCode", unit.getBarCode()+"%");
        	queryMap.put("CEntID", CEntID);
         	measurepoints = measurepointService.selectListByWhere("selectListBynoEnergy", queryMap);	
        	
        }
       
        model.addAttribute("measurepointInfo", measurepoints);
        model.addAttribute("totalRows", measurepoints.size());
        model.addAttribute("kindOpts", MeasurePoint.getKindOpts());
      
        return "measurepoint/energylist";
    }
    @RequestMapping(value = "productlist")//能源规划点的初始化显示页面
    public String indexproductlist(Model model, HttpServletRequest request,String search_name)
            throws SQLException {
        //Map<String, Object> map = WebUtils.getParametersStartingWith(request,"search_");
        Context context = new HttpServletRequestContext(request);
        LimitFactory limitFactory = new TableLimitFactory(context);
        TableLimit limit = new TableLimit(limitFactory);
        limit.setRowAttributes(1000000000, 25);
        // 转换为HashMap
        HashMap<String,String> queryMap=new HashMap<String,String>();
        if(search_name != null) {
        	queryMap.put("name", "%"+search_name+"%");
        }
        
        String KindID=this.getKind(request);
        String EntID=this.getEntIDAdd(request);
        String CEntID=this.getEntID(request,"e.PID");
        List<MeasurePoint> measurepoints=null;
        if(KindID.equals("3")){
        	measurepoints = measurepointService.selectListByWhere("selectListByWhere", queryMap);	
        }else{
        
            EnergyAccountUnit unit = energyaccountunitService.selectOneR(Integer.parseInt(EntID));
        	queryMap.put("BarCode", unit.getBarCode()+"%");
        	queryMap.put("CEntID", CEntID);
         	measurepoints = measurepointService.selectListByWhere("selectListBynoproductlist", queryMap);	
        	
        }
       
        model.addAttribute("measurepointInfo", measurepoints);
        model.addAttribute("totalRows", measurepoints.size());
        model.addAttribute("kindOpts", MeasurePoint.getKindOpts());
      
        return "measurepoint/productlist";
    }
    
  
    @RequestMapping(value = "/list1")//规划点验证的初始化显示页面
    public String listDefault1(Model model, HttpServletRequest request,String search_name)
            throws SQLException {
        //Map<String, Object> map = WebUtils.getParametersStartingWith(request,"search_");
        Context context = new HttpServletRequestContext(request);
        LimitFactory limitFactory = new TableLimitFactory(context);
        TableLimit limit = new TableLimit(limitFactory);
        limit.setRowAttributes(1000000000, 25);
        // 转换为HashMap
        HashMap<String,String> queryMap=new HashMap<String,String>();
        if(search_name != null) {
        	queryMap.put("name", "%"+search_name+"%");
        }

       String EntID=this.getEntID(request, "e1.ID");
       queryMap.put("EntID",EntID);
        List<MeasurePoint> measurepoints=null;
        measurepoints = measurepointService.selectListByWhere("selectListB1", queryMap);	
        model.addAttribute("measurepointInfo", measurepoints);
        model.addAttribute("totalRows", measurepoints.size());
        model.addAttribute("kindOpts", MeasurePoint.getKindOpts());
        return "measurepoint/list";
    }  
    
    
	@RequestMapping(value="/month/chart")
	public String chart(Model model, HttpServletRequest request) throws SQLException {
		String eId = request.getParameter("mesID");
		if(!StringUtils.isEmpty(eId)) {
			model.addAttribute("mesID", eId);
		}
		
		return "measurepoint/linechart";
	}
	
	@RequestMapping(value = "/line/energy")
	@ResponseBody
	public Map<String, Object> allenergyLineChart(Model model, HttpServletRequest request) throws SQLException {
		List<String> categories = new ArrayList<String>();
		Map<String,Object> result=new HashMap<String,Object>();
		result.put("series", this.getseriesList(request, categories));//插入List<Map<String,Object>
	    result.put("chart_xAxis_categories", categories);//插入时间List格式
		result.put("chart_yAxis_title_text", "tce");//插入文字说明：标准单位
		result.put("status", "success");//状态 

		return result;
	}

	private List<Map<String,Object>> getseriesList(HttpServletRequest request, List<String> categories) throws SQLException{
		int mesId = 0;
		String mId = request.getParameter("mesID");
		if(!StringUtils.isEmpty(mId)) {
			mesId = Integer.parseInt(mId);
		}

		HashMap queryParam = new HashMap();
		if(mesId > 0) {
			queryParam.put("mesID", mesId); //条件：标准能源介质
		}
		queryParam.put("GroupBy", "SUBSTRING(CONVERT(varchar,SumDim,111) ,1,7)");
		queryParam.put("startYear", "2015");
		
		List<Map<String, Object>> series = new ArrayList<Map<String, Object>>();
		Map<String,Object> chart_series=new HashMap<String,Object> ();
		  String name="总能耗";
         List<MeasurePointValueMonth> measurePointValue= measurepointvaluemonthService.selectListByWhere("selectListByWherewayeverMonth", queryParam);
         List<Float> data=new ArrayList<Float>();
         Map<Integer,Float> month=new  HashMap<Integer,Float>();
		for(int i=0;i<measurePointValue.size();i++){
			month.put(measurePointValue.get(i).getID()-1,measurePointValue.get(i).getStandardAdjValue());
		}
       for(int j=0;j<12;j++){
    	   if(month.containsKey(j)){
    		   data.add(j, month.get(j)); 
    		   categories.add((j+1)+"月");
    		   continue;
    	   }
        }
       chart_series.put("name", name);
       chart_series.put("data", data);
       series.add(chart_series);
		
		return series;
	}    

    /**
     * 添加数据页面.
     * 
     * @param model
     *            the model
     * @return the string
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("kindOpts", MeasurePoint.getKindOpts());
        return "measurepoint/add";
    }

    /**
     * 删除一条数据.
     * 
     * @param measurepointID
     *            the measurepoint id
     * @param redirectAttributes
     *            the redirect attributes
     * @return the string
     * @throws SQLException
     *             the SQL exception
     */
    @RequestMapping(value = "/delete/{measurepointID}")
    public String delete(@PathVariable("measurepointID") String measurepointID,
            RedirectAttributes redirectAttributes) throws SQLException {
        int id = Integer.parseInt(measurepointID);
        if(canDelete(id)) {
	        measurepointService.delete(id);
	        redirectAttributes.addFlashAttribute("message", "删除成功");
        }else{
	        redirectAttributes.addFlashAttribute("message", "此规划点有关联的能源网络或计量数据，不能删除");
        }
        return "redirect:/measurepoint/list";
    }

    private boolean canDelete(int id) {
    	HashMap<String, Object> queryParam = new HashMap<String, Object>();
    	try{
    		queryParam.put("MeasurePointID", id);
	    	List<PicMeasurePoint> list = picMeasurePointService.selectListByWhere("selectListByWhere", queryParam);
	    	if(list.size() > 0) {
	    		for(PicMeasurePoint point : list) {
	    			EnergyNet net = energynetService.selectOneRecord(point.getNetID());
	    			if(net != null) {
	    				return false;
	    			}else{
	    				picMeasurePointService.delete(point.getId());
	    			}
	    		}
	    	}
	    	queryParam.clear();
	    	queryParam.put("MPointID", id);
	    	String r = valueDayService.selectOneValueByWhere("selectOneValueByWhere", queryParam);
	    	if(Integer.parseInt(r)>0) {
	    		return false;
	    	}
	    	r = valueMonthService.selectOneValueByWhere("selectOneValueByWhere", queryParam);
	    	if(Integer.parseInt(r)>0) {
	    		return false;
	    	}
	    	r = valueYearService.selectOneValueByWhere("selectOneValueByWhere", queryParam);
	    	if(Integer.parseInt(r)>0) {
	    		return false;
	    	}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    	return true;
    }
    /**
     * 编辑一条数据.
     * 
     * @param measurepointID
     *            the measurepoint id
     * @param model
     *            the model
     * @return the string
     * @throws SQLException
     *             the SQL exception
     */
    @RequestMapping(value = "/edit/{measurepointID}", method = RequestMethod.GET)
    public String edit(@PathVariable String measurepointID, Model model)
            throws SQLException {
        model.addAttribute("isUpdate", "Y");
        HashMap<String, String> map = new HashMap<String, String>();
        int id = Integer.parseInt(measurepointID);
        MeasurePoint list = measurepointService.selectOneRecord(id);
        EnergyMeasurePoint emp = list.getEnergyMeasurePoint();
        if(emp != null) {
        	model.addAttribute("EnergyID", emp.getEnergyID());
        }
        model.addAttribute("measurepointInfo", list);
        model.addAttribute("kindOpts", MeasurePoint.getKindOpts());
        return "measurepoint/edit";
    }

    /**
     * 创建或更新一条数据.
     * 
     * @param measurePoint
     *            the measurepoint info
     * @param model
     *            the model
     * @param br
     *            the br
     * @param redirectAttributes
     *            the redirect attributes
     * @return the string
     * @throws SQLException
     *             the SQL exception
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(MeasurePoint measurePoint, Model model,
            BindingResult br, RedirectAttributes redirectAttributes,
            Integer ADD,@RequestParam("EnergyID") Integer energyID, HttpServletRequest request) {
    	
		EnergyMeasurePoint emp = null;
		
        if (measurePoint.getID() !=  null && measurePoint.getID() > 0) {
            try {
				measurepointService.update(measurePoint);
				measurePoint = measurepointService.selectOneRecord(measurePoint.getID());
				if(measurePoint.getEnergyMeasurePoint() != null) {
					if(measurePoint.getEnergyMeasurePoint().getEnergyID() != energyID) {
						emp = new EnergyMeasurePoint();
						emp.setEnergyID(energyID);
						emp.setMesID(measurePoint.getID());
						emp.setID(measurePoint.getEnergyMeasurePoint().getID());
						energyMeasurePointService.update(emp);
					}
				}else{
					emp = new EnergyMeasurePoint();
					emp.setEnergyID(energyID);
					emp.setMesID(measurePoint.getID());
					energyMeasurePointService.insert(emp);				
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } else {
        	   
            try {
				measurepointService.insert(measurePoint);
			    List<MeasurePoint> ms=measurepointService.selectListByWhere("measurePointMaxID", null);//获取规划计量点的最大ID
				emp = new EnergyMeasurePoint();
				emp.setEnergyID(energyID);
				emp.setMesID(ms.get(0).getID());
				energyMeasurePointService.insert(emp);				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
       
        if(ADD!=null){
        	redirectAttributes.addFlashAttribute("message", "提交成功请继续添加");
        	return "redirect:/measurepoint/add";
        }else{
         redirectAttributes.addFlashAttribute("message", "成功");
        	return "redirect:/measurepoint/list";
        }
        
        
    }
    
    
    
    /*
     * 获取能源核算单元名称
     */
    @RequestMapping(value = "getenergyaccountname", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> getenergyaccountname(Model model,HttpServletRequest request) throws SQLException{
    	int n=Integer.parseInt(request.getParameter("n"));
    	HashMap<String , String> map = new HashMap<String , String>(); 
    	HashMap<String , Integer> query = new HashMap<String , Integer>(); 
    	query.put("ID", n);
    	List<EnergyAccountUnit> energyAccountunits =energyaccountunitService.selectListByWhere("selectenergunityname", query);
    	
            if(energyAccountunits.size()==1){
            	String name=energyAccountunits.get(0).getName();
            	map.put("name", name);
            	map.put("status", "1");
            	
            }else{
            	map.put("status", "0");	
        
    	}
    	
    	
    	return map;
    }
    
    
    
    /*
     * 获取规划点能源介质名称
     */
    @RequestMapping(value = "getentenergyname", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> getentenergyname(Model model,HttpServletRequest request) throws SQLException{
    	int n=Integer.parseInt(request.getParameter("n"));
    	HashMap<String , String> map = new HashMap<String , String>(); 
    	HashMap<String , Integer> query = new HashMap<String , Integer>(); 
    	query.put("ID", n);
    	List<EntEnergy> entenergys =entenergyService.selectListByWhere("selectentenergyname", query);
    	
            if(entenergys.size()==1){
            	String EnterEnergyName=entenergys.get(0).getEnterEnergyName();
            	map.put("name", EnterEnergyName);
            	map.put("status", "1");
            	
            }else{
            	map.put("status", "0");	
        
    	}
    	
    	
    	return map;
    }  
    
    
    /*
     * 获取规划点能源方向
     */
    @RequestMapping(value = "energydirection", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> energydirection(Model model,HttpServletRequest request) throws SQLException{
    	int n=Integer.parseInt(request.getParameter("n"));
    	HashMap<String , String> map = new HashMap<String , String>(); 
    	HashMap<String , Integer> query = new HashMap<String , Integer>(); 
    	if(n==1){
    		map.put("name","输入");
    	}
    	if(n==2){
    		map.put("name","分配");
    		
    	}
    	if(n==3){
    		map.put("name","回收");
    		
    	}
    	if(n==4){
    		map.put("name","外供");
    		
    	}   
    	if(n==5){
    		map.put("name","损失");
    		
    	} 
    	if(n==9){
    		map.put("name","用于原料");
    		
    	} 
    	if(n==99){
    		map.put("name","购入结算");
    		
    	} 
    	map.put("status", "1");
    	return map;
    }  
    
    @RequestMapping(value = "/getAreasone")
	@ResponseBody
	public JSONArray listUnitTree(HttpServletRequest request, Model model) {
		JSONArray jsonList = new JSONArray();
		try{
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("children", new JSONArray());
			
			String rootId = getEntIDAdd(request);
			EnergyAccountUnit root = energyaccountunitService.selectOneR(Integer.parseInt(rootId));
			jsonObject.put("id", root.getID());
			jsonObject.put("text", root.getName());
			
			getUnitTree(jsonObject);
			
			jsonList.add(jsonObject);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return jsonList;
	}	

	private void getUnitTree(JSONObject parent) throws SQLException {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("PID", parent.get("id"));
		
		JSONArray childrenJson = parent.getJSONArray("children");
		
		List<EnergyAccountUnit> children = energyaccountunitService.selectListByWhere("selectListbyparent", param);
		for(EnergyAccountUnit child : children) {
			JSONObject json = new JSONObject();
			json.put("children", new JSONArray());
			json.put("id", child.getID());
			json.put("text", child.getName());
						
			getUnitTree(json);
			
			childrenJson.add(json);
		}
	}
	
	 
    @RequestMapping("/getareasoneadd")
	@ResponseBody
	public  List<Tree> getAreasone(  String UnitID,HttpServletResponse response,HttpServletRequest request) throws SQLException {
	
    	response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		   HashMap<String,String> queryMap=new HashMap<String,String>();
			/*if(UnitID.equals("")){
				UnitID="";
			}
				*/
		   String EntID=this.getEntID(request,"e1.ID");
		   String KindID=this.getKind(request);
		    queryMap.put("PID", UnitID);
		 
		    List<Tree> tr=new ArrayList<Tree>();
			if(KindID.equals("3")){
				List<EnergyAccountUnit> areaStandInfo = energyaccountunitService.selectListByWhere("selectParentListByWhere", queryMap);
			
				for(int i=0;i<areaStandInfo.size();i++){
				    Tree t=new Tree();
				    t.setId(areaStandInfo.get(i).getID());
				    t.setpId(areaStandInfo.get(i).getPID());
				    t.setName(areaStandInfo.get(i).getName());
				    tr.add(t);
				    
			}
			}
			
				else{
				queryMap.put("EntID", EntID);
				List<EnergyAccountUnit> areaStandInfo = energyaccountunitService.selectListByWhere("selectListB", queryMap);
				for(int i=0;i<areaStandInfo.size();i++){
				    Tree t=new Tree();
				    t.setId(areaStandInfo.get(i).getID());
				    t.setpId(areaStandInfo.get(i).getPID());
				    t.setName(areaStandInfo.get(i).getName());
				    tr.add(t);
				   
			}
				}
/*			JSONArray jsonArray = organizeAeraJsonTop(areaStandInfo.iterator(),UnitID);
*/		   /*StringBuilder strb=new StringBuilder();
                      strb.append("[");
			for(int i=0;i<areaStandInfo.size();i++){
	              String str="{id:"+areaStandInfo.get(i).getID()+",pId:"+areaStandInfo.get(i).getPID()+",name:"+areaStandInfo.get(i).getName()+"},";
                   if(i==(areaStandInfo.size()-1)){
     	       str="{id:"+areaStandInfo.get(i).getID()+",pId:"+areaStandInfo.get(i).getPID()+",name:"+areaStandInfo.get(i).getName()+"}";
  
                   }
	              strb.append(str);
			}
			strb.append("]");*/
			
				 return tr;	
				}
         
				
}
