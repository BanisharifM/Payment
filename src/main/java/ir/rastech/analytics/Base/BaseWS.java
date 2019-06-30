package ir.rastech.analytics.Base;

import com.idehgostar.jpam.util.QuerySourcesUtil;
import com.idehgostar.makhsan.core.auth.TokenData;
import com.idehgostar.makhsan.core.response.SimpleResponse;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import java.io.IOException;
import java.util.Set;

//import Permission;
//import com.idehgostar.makhsan.core.jsoncreator.JacksonJsonCreator;

/**
 * Created by Abolfazl on 8/15/2015.
 */
public class BaseWS {

    @Autowired
    protected QuerySourcesUtil querySourcesUtil;

    @Context
    private HttpServletRequest request;


    protected JacksonJsonCreator getJsonCreator() {
        return new JacksonJsonCreator();
    }

    protected String createSimpleResponse(SimpleResponse.Status status, String message) throws IOException {
        return getJsonCreator().getJson(new SimpleResponse(status, message));
    }

//    protected boolean hasRole(Role role) {
//        try {
//            return ((Set<String>) request.getAttribute("userRoles")).contains(role.getRoleName());
//        } catch (Exception e) {
//            return false;
//        }
//    }

    protected boolean hasPermission(Permission permission) {
        try {
            if (((Set<String>) request.getAttribute("userPermissions")).contains(permission))
                return true;
        } catch (Exception e) {
        }
//        try {
//            for (String roleString : (Set<String>) request.getAttribute("userRoles")) {
//                Role role = roleManager.getRoleByName(roleString);
//                if (role.getPermissions().contains(permission))
//                    return true;
//            }
//        } catch (Exception e) {
//            return false;
//        }
        return false;
    }

    protected String getUserInSite() {
        try {
            return ((String) request.getAttribute("userId"));
        } catch (Exception e) {
            return null;
        }
    }

    protected TokenData getAccessToken() {
        try {
            return ((TokenData) request.getAttribute("token"));
        } catch (Exception e) {
            return null;
        }
    }

    public String getSenderIp() {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress.contains(",") ? ipAddress.split(",")[0] : ipAddress;
    }
}
