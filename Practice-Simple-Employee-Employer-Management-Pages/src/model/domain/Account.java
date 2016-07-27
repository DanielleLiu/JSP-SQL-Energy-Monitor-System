package sbzy.enterpriseems.model.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;

/**
 * 实体类 何罗柱 2015-8-6 【系统用户表】
 */
public class Account {
    private int           ID;
    private String        Account;
    private String        Password;
    private int           State;
    private String        RealName;
    private String        DepName;
    private List<Account> children;
    private List<Role>    roles;
    private List<Integer> roleIDs;
    private String CompanyName;//虚字段数据库没有，仅供查询显示使用
    
    //企业对象
    private EnergyCconsumptionUnit tionUnit;

    public EnergyCconsumptionUnit getTionUnit() {
		return tionUnit;
	}

	public String getCompanyName() {
		return CompanyName;
	}

	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}

	public void setTionUnit(EnergyCconsumptionUnit tionUnit) {
		this.tionUnit = tionUnit;
	}

	public int getID() {
        return ID;
    }

    public void setID(int iD) {
        ID = iD;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public List<Account> getChildren() {
        return children;
    }

    public void setChildren(List<Account> children) {
        this.children = children;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Integer> getRoleIDs() {
        return roleIDs;
    }

    public void setRoleIDs(List<Integer> roleIDs) {
        this.roleIDs = roleIDs;
    }

    public String getRealName() {
		return RealName;
	}

	public void setRealName(String realName) {
		RealName = realName;
	}

	public String getDepName() {
		return DepName;
	}

	public void setDepName(String depName) {
		DepName = depName;
	}

	@Override
    public String toString() {
        return "Account [ID=" + ID + ", Account=" + Account + ", Password="
                + Password + ", State=" + State + ", children=" + children
                + ", roles=" + roles + ", roleIDs=" + roleIDs + "]";
    }

}
