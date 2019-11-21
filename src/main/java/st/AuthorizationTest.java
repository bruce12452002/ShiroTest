package st;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

import java.util.Arrays;

public class AuthorizationTest {
    public static void main(String[] args) {
        Factory<org.apache.shiro.mgt.SecurityManager> factory =
                new IniSecurityManagerFactory("classpath:shiro.ini");
        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        session.setAttribute("key", "value");
        if (session.getAttribute("key").equals("value")) {
            System.out.println("ok");
        }

        if (!currentUser.isAuthenticated()) {
            try {
//                UsernamePasswordToken token = new UsernamePasswordToken("bruce", "123");
                UsernamePasswordToken token = new UsernamePasswordToken("link", "456");
                token.setRememberMe(true);
                currentUser.login(token);

                // 一定要認證通過才有用，不然以下都是 false
                System.out.println(currentUser.hasRole("xxx") ? "is xxx" : "no xxx");

                System.out.println("角色");
                boolean[] results = currentUser.hasRoles(Arrays.asList("xxx", "ooo", "aaa"));
                for (boolean b : results) {
                    System.out.println(b);
                }

                System.out.println("權限");
                boolean[] permitted = currentUser.isPermitted("user:add", "user:delete", "aaa:hahaha");
                for (boolean b : permitted) {
                    System.out.println(b);
                }
            } catch (UnknownAccountException uae) {
                System.out.println("UnknownAccountException 例外"); // 帳號錯
            } catch (IncorrectCredentialsException ice) {
                System.out.println("IncorrectCredentialsException 例外"); // 密碼錯
            }
        }
    }
}
