package util;

import com.liqwei.platform.util.FileUtil;
import com.liqwei.platform.util.StringUtil;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.MXRecord;
import org.xbill.DNS.Record;
import org.xbill.DNS.Type;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/3/23.
 */
public class EmailUtils {

    public static Map<String, DomainRecord> AllDomainRecord;
    private static Map<String, List<String>> AllDomainData;
    private static String domainDataPath = "src\\DomainData.txt";
    private static String errorMailPath = "src\\ErrorMail.txt";
    private static String domainStatusPath = "src\\DomainStatus.txt";
    private static String validMailPath = "src\\ValidMail.txt";
    final static Pattern pattern = Pattern.compile("^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@[a-zA-Z0-9]+([a-zA-Z0-9-\\.][a-zA-Z0-9]+)*[\\.][a-zA-Z]{2,}$", Pattern.CASE_INSENSITIVE);
    public static String absoutPath;
    static {
        try {
            File file = new File("");
            absoutPath = file.getAbsolutePath();
            AllDomainData = init();//存放域名库的一级与二级域名
            //AllDomainRecord = readDomainStatusToMap();//从文件读取出已筛选的邮箱记录
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
    * 校验邮箱格式是否合乎规范
    * 1. 必须包含一个并且只有一个符号“@”
    * 2. 第一个字符不得是“@”或者“.”
    * 3. 不允许出现“@.”或者.@
    * 4. 结尾不得是字符“@”或者“.”
    * 5. 允许“@”前的字符中出现“＋”
    * 6. 不允许“＋”在最前面，或者“＋@”
    */
    public static boolean isStandard(String email) {
        return pattern.matcher(email).matches();
    }

    /**
     * 获得所有邮箱的域名,极其相应数量
     */
    public static Map<String, Integer> getAllEmailDomainAndNumber(List<String> emails) {
        Map<String, Integer> map = new TreeMap<String, Integer>();
        for (String email : emails) {
            String domain = getEmailSuffixDomain(email);
            if (!StringUtil.isEmpty(domain)) {
                if (map.containsKey(domain)) {
                    Integer count = map.get(domain) + 1;
                    map.put(domain, count);
                } else {
                    map.put(domain, 1);
                }
            }
        }
        return map;
    }

    /*
    * 对统计出来的邮箱域名及其数量，按数量的倒序进行排序
    * */
    public static Map<String, Integer> sortDomainByNumberDesc(Map<String, Integer> map) {
        ArrayList<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> arg0,
                               Map.Entry<String, Integer> arg1) {
                return arg1.getValue() - arg0.getValue();
            }
        });
        Map sortMap = new LinkedHashMap();
        for (int i = 0; i < list.size(); i++) {
            sortMap.put(list.get(i).getKey(), list.get(i).getValue());
        }
        return sortMap;
    }

    /**
     * 对统计出来的邮箱域名及其数量，按数量的倒序进行排序，并将域名对应的数量小于number的值清空
     *
     * @param map
     * @param number
     */
    public static Map<String, Integer> sortDomainByNumberDesc(Map<String, Integer> map, Integer number) {
        ArrayList<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> arg0,
                               Map.Entry<String, Integer> arg1) {
                return arg1.getValue() - arg0.getValue();
            }
        });
        Map sortMap = new LinkedHashMap();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getValue() >= number) {
                sortMap.put(list.get(i).getKey(), list.get(i).getValue());
            }
        }
        return sortMap;
    }

    /*
    * 从文件中获取所有的顶级域名及其二级域名
    * */
    private static Map<String, List<String>> init() throws IOException {
        Map<String, List<String>> domainStatusMap = new TreeMap<String, List<String>>();
        File file = new File(absoutPath+"\\"+domainDataPath);
        List list = FileUtil.readFileToList(file);
        for (int i = 1; i < list.size(); i++) {
            String str = list.get(i).toString();
            String[] domain = str.split("=");
            List<String> twoLevelDomain = Arrays.asList(domain[1].split(","));
            domainStatusMap.put(domain[0], twoLevelDomain);
        }
        return domainStatusMap;
    }

    /*
    *判断邮箱的域名是否存在域名库中
    * */
    private static boolean isExistDomainDatabase(String domain) {
        boolean flag = true;
        String[] domains = domain.split("\\.");
        if (domains.length == 2) {//无二级域名
            List<String> list = AllDomainData.get(domains[1]);
            if (!StringUtil.isEmpty(list)) {//服务器名存在域名服务器中
                if (list.contains(domains[0])) {//主域名为服务器
                    flag = false;
                }
            } else {
                flag = false;
            }
        } else {//存在二级域名
            if (StringUtil.isEmpty(AllDomainData.get(domains[domains.length - 1]))) {//服务器名不存在域名库中
                flag = false;
            }
        }
        return flag;
    }


    /**
     * 获得邮箱的后缀域名
     *
     * @param email
     */
    public static String getEmailSuffixDomain(String email) {
        int start = email.indexOf("@");
        String domain = email.substring(start + 1);
        return domain;
    }


    /**
     * 通过域名获得优先级排序后的MX记录
     *
     * @param domain *
     */
    public static List<String> getMXRecord(String domain) throws IOException {
        Lookup lookup = new Lookup(domain, Type.MX);
        Record[] mxRecords = lookup.run();
        List<String> mxHost = new ArrayList<String>();
        if (!StringUtil.isEmpty(mxRecords)) {
            mxHost = sortMXRecordByPriority(mxRecords);
        }
        return mxHost;
    }

    /**
     * 对域名的MX记录按优先级进行排序并返回MX记录
     *
     * @param mxRecords
     */
    public static List<String> sortMXRecordByPriority(Record[] mxRecords) {
        List<String> list = new ArrayList<String>();
        String mxHost = ((MXRecord) mxRecords[0]).getTarget().toString();
        if (mxRecords.length > 1) { // 对MX进行优先级排序
            List<Record> arrRecords = new ArrayList<Record>();
            Collections.addAll(arrRecords, mxRecords);
            Collections.sort(arrRecords, new Comparator<Record>() {
                public int compare(Record o1, Record o2) {
                    return new CompareToBuilder().append(((MXRecord) o1).getPriority(), ((MXRecord) o2).getPriority()).toComparison();
                }
            });
            for (Record mxRecord : arrRecords) {
                list.add(((MXRecord) mxRecord).getTarget().toString());
            }
        }
        if (list.size() == 0) {
            list.add(mxHost);
        }
        return list;
    }

    /**
     * 判断邮箱的域名是否可以连通
     *
     * @param mxHosts
     */
    private static boolean isConnectDomain(List<String> mxHosts) {
        Socket socket = new Socket();
        try {
            for (String mxHost : mxHosts) {
                socket.connect(new InetSocketAddress(mxHost, 25), 1000 * 10);
                return true;
            }
        } catch (SocketTimeoutException e) {
            return false;
        } catch (IOException e) {
            return false;
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
        return false;
    }


    /**
     * 获得可用的域名并写入到文件中
     */
    public static List<String> getValidMail(List<String> emails) throws IOException {
        String checkDate = StringUtil.getCurrentTime();

        List<String> validEmail = new ArrayList<String>();
        List<String> errorEmail = new ArrayList<String>();
        List<String> errorDomain = new ArrayList<String>();
        List<String> domainStatusList = new ArrayList<String>();
        Iterator<String> iterator = emails.iterator();
        while (iterator.hasNext()) {
            String email = (String) iterator.next();
            if (!isStandard(email)) {//格式正确
                errorEmail.add(email);
                iterator.remove();
            }
        }
        Map<String, Integer> allEmailDomainAndNumber = getAllEmailDomainAndNumber(emails);
        Set<String> domains = allEmailDomainAndNumber.keySet();
        Iterator<String> it = domains.iterator();
        while (it.hasNext()) {
            String domain = it.next();
            DomainRecord record = AllDomainRecord.get(domain);
            if (!StringUtil.isEmpty(record)) {//域名状态表中存在该域名
                if (!record.isValid()) {//该域名完全可用
                    errorDomain.add(domain);
                }
                it.remove();
            }
        }

        it = domains.iterator();
        while (it.hasNext()) {
            String domain = it.next();
            if (!isExistDomainDatabase(domain)) {//存在域名库中
                errorDomain.add(domain);//不存在域名库中
                AllDomainRecord.put(domain, new DomainRecord(DomainType.InvalidStyle, CheckCycleType.OneMonthCheck, checkDate));
                it.remove();
            }
        }

        it = domains.iterator();
        while (it.hasNext()) {
            String domain = it.next();
            List<String> mxHosts = getMXRecord(domain);//获取MX记录
            if (StringUtil.isEmpty(mxHosts)) {//存在MX记录
                errorDomain.add(domain);
                AllDomainRecord.put(domain, new DomainRecord(DomainType.NoMXRecord, CheckCycleType.OneMonthCheck, checkDate));
                continue;
            }
            if (isConnectDomain(mxHosts)) {//通过MX可以连通
                AllDomainRecord.put(domain, new DomainRecord(DomainType.Valid, CheckCycleType.OneMonthCheck, checkDate));
            } else {
                errorDomain.add(domain);
                AllDomainRecord.put(domain, new DomainRecord(DomainType.NotConnect, CheckCycleType.OneMonthCheck, checkDate));
            }
        }

        Iterator<String> itEmail = emails.iterator();
        while (itEmail.hasNext()) {
            String email = itEmail.next();
            String domain = getEmailSuffixDomain(email);
            if (errorDomain.contains(domain)) {
                errorEmail.add(email);
                itEmail.remove();
            } else {//邮箱域名存在可用域名中
                validEmail.add(email);
                itEmail.remove();
            }
        }

        Set<String> allDomain = AllDomainRecord.keySet();
        for (String domain : allDomain) {
            domainStatusList.add(domain + "," + AllDomainRecord.get(domain).toString());
        }

        writeEmailToFile(domainStatusList,absoutPath+"\\"+domainStatusPath);
        writeEmailToFile(errorEmail,absoutPath+"\\"+errorMailPath);
        writeEmailToFile(validEmail, absoutPath+"\\"+validMailPath);

        return validEmail;
    }



    /**
     * 将校验过后得到的可用邮箱或错误邮箱写入文件
     */
    public static void writeEmailToFile(List<String> emails, String path) throws IOException {
        File file = new File(path);

        FileUtil.writeListToFile(file, emails);
    }


    /**
     * 将文件里记录的内容读取到map集合里
     */
    private static Map<String, DomainRecord> readDomainStatusToMap() throws IOException {
        File file = new File(domainStatusPath);
        Map<String, DomainRecord> map = new HashMap<String, DomainRecord>();
        List<String> domainStatus = FileUtil.readFileToList(file, "utf-8");
        for (String str : domainStatus) {
            String[] domain = str.split(",");
            DomainRecord status = new DomainRecord(DomainType.parse(domain[1]), CheckCycleType.parse(domain[2]), domain[3]);
            if (status.isNeedCheck()) {
                map.put(domain[0], status);
            }
        }
        return map;
    }


    public static void main(String[] args) {
        try {
            List<String> emails = FileUtil.readFileToList(new File("src\\email.txt"), "utf-8");
            EmailUtils.getValidMail(emails);
        } catch (IOException e) {
            e.printStackTrace();
        }


            /*List<String> list = new ArrayList<String>();
            for(String email:emails){
                list.add(email.toLowerCase());
            }
            FileUtil.writeListToFile("src\\email.txt",list);*/
    }
}
