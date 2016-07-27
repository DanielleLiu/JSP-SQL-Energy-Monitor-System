package sbzy.enterpriseems.model.domain;

public class AccountEntList {
    private Integer                ID;
    private Integer                EnergyCconsumptionUnitID;
    private Integer                AccountID;
    private EnergyCconsumptionUnit EnergyCconsumptionUnit;
    private Account                account;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer iD) {
        ID = iD;
    }


    public Integer getAccountID() {
        return AccountID;
    }

    public void setAccountID(Integer accountID) {
        AccountID = accountID;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public EnergyCconsumptionUnit getEnergyCconsumptionUnit() {
        return EnergyCconsumptionUnit;
    }

    public void setEnergyCconsumptionUnit(
            EnergyCconsumptionUnit energyCconsumptionUnit) {
        EnergyCconsumptionUnit = energyCconsumptionUnit;
    }

    public Integer getEnergyCconsumptionUnitID() {
        return EnergyCconsumptionUnitID;
    }

    public void setEnergyCconsumptionUnitID(Integer energyCconsumptionUnitID) {
        EnergyCconsumptionUnitID = energyCconsumptionUnitID;
    }

    @Override
    public String toString() {
        return "AccountEntList [ID=" + ID + ", EnergyCconsumptionUnitID="
                + EnergyCconsumptionUnitID + ", AccountID=" + AccountID
                + ", EnergyCconsumptionUnit=" + EnergyCconsumptionUnit
                + ", account=" + account + "]";
    }

}
