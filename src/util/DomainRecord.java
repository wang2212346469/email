package util;

import com.liqwei.platform.util.DateUtil;

import java.util.Date;

/**
 * Created by Administrator on 2016/3/31.
 */
public class DomainRecord {
    private CheckCycleType checkCycleType;
    private DomainType domainType;
    private String checkDate;

    public DomainRecord(DomainType domainType, CheckCycleType checkCycleType, String checkDate){
        this.domainType = domainType;
        this.checkCycleType = checkCycleType;
        this.checkDate = checkDate;
    }

    public boolean isNeedCheck(){
        Date date = DateUtil.parseDate(checkDate);
        Date currentDate = new Date();
        if(this.checkCycleType==CheckCycleType.OneMonthCheck){
            Date oldDate = DateUtil.addDate(date, 0, 1, 0);
            if(!DateUtil.isBefore(currentDate,oldDate)){
                return false;
            }
        }else if(this.checkCycleType==CheckCycleType.ThreeMonthsCheck){
            Date oldDate = DateUtil.addDate(date, 0, 3, 0);
            if(!DateUtil.isBefore(currentDate,oldDate)){
                return false;
            }
        }else if(this.checkCycleType==CheckCycleType.OneYearCheck){
            Date oldDate = DateUtil.addDate(date, 1, 0, 0);
            if(!DateUtil.isBefore(currentDate,oldDate)){
                return false;
            }
        }
        return true;
    }

    public boolean isValid(){
        if(this.domainType == DomainType.Valid){
            return true;
        }
        return false;
    }

    public String toString(){
        return domainType+","+checkCycleType+","+checkDate;
    }
}
