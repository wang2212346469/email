import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

/**
 * 通过 DNS MX 记录查询验证邮件域名是否存在。
 * Verifies whether the mail domain exists by querying DNS MX records.
 */
public class EmailChecker {

    private final EmailValidator validator = new EmailValidator();

    /**
     * 检查邮件地址的域名是否拥有有效的 MX 记录。
     *
     * @param address 待检查的邮件地址
     * @return 如果域名存在 MX 记录则返回 true，否则返回 false
     */
    public boolean domainExists(String address) {
        if (!validator.isValid(address)) {
            return false;
        }
        Email email = new Email(address);
        return hasMxRecord(email.getDomain());
    }

    /**
     * 查询给定域名是否存在 MX 记录。
     */
    private boolean hasMxRecord(String domain) {
        DirContext ctx = null;
        try {
            Hashtable<String, String> env = new Hashtable<>();
            env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
            ctx = new InitialDirContext(env);
            Attributes attrs = ctx.getAttributes("dns://" + domain, new String[]{"MX"});
            return attrs.get("MX") != null;
        } catch (NamingException e) {
            return false;
        } finally {
            if (ctx != null) {
                try {
                    ctx.close();
                } catch (NamingException ignored) {
                }
            }
        }
    }
}
