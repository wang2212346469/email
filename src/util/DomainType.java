package util;

/**
 * Created by Administrator on 2016/3/28.
 */
public enum DomainType {
    Valid("0"),
    InvalidStyle("1"),
    NoMXRecord("2"),
    NotConnect("3");

    private String code;
    private DomainType(String code){
        this.code = code;
    }

    public static DomainType parse(String code){
        if("0".equals(code)){
            return Valid;
        }else if("1".equals(code)){
            return InvalidStyle;
        }else if("2".equals(code)){
            return NoMXRecord;
        }else{
            return NotConnect;
        }
    }

    public String toString(){
        return code;
    }

}
