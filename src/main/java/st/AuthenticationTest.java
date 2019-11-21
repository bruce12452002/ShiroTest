package st;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

public class AuthenticationTest {
    public static void main(String[] args) {
        Factory<org.apache.shiro.mgt.SecurityManager> factory =
//                new IniSecurityManagerFactory("classpath:shiro.ini");
                new IniSecurityManagerFactory("classpath:c3p0.ini");
        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);


        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            try {
//            UsernamePasswordToken token = new UsernamePasswordToken("bruce", "123");
                // 使用資料庫時，表名一定是 users，欄位名一定是 username 和 password
                UsernamePasswordToken token = new UsernamePasswordToken("bbb", "456");
                currentUser.login(token);
                System.out.println("認證成功！");
                System.out.println("認證了？" + currentUser.isAuthenticated());
                currentUser.logout();
                System.out.println("認證了？" + currentUser.isAuthenticated());
                System.exit(0);
            } catch (UnknownAccountException uae) {
                System.out.println("UnknownAccountException 例外"); // 帳號錯
            } catch (IncorrectCredentialsException ice) {
                System.out.println("IncorrectCredentialsException 例外"); // 密碼錯
            } catch (LockedAccountException lae) {
                System.out.println("LockedAccountException 例外");
            } catch (AuthenticationException ae) {
                System.out.println("AuthenticationException 例外");
            }
        } else {
            System.out.println("已認證！");
        }
    }
}
