package util;

/**
 * Created by Administrator on 2016/3/28.
 */
public enum CheckCycleType {
    NoCheck("0"),
    OneYearCheck("1"),
    ThreeMonthsCheck("2"),
    OneMonthCheck("3");

    private String code;
    private CheckCycleType(String code){
        this.code = code;
    }

    public static CheckCycleType parse(String code){
        if("0".equals(code)){
            return NoCheck;
        }else if("1".equals(code)){
            return OneYearCheck;
        }else if("2".equals(code)){
            return ThreeMonthsCheck;
        }else{
            return OneMonthCheck;
        }
    }

    public String toString(){
        return code;
    }

}
