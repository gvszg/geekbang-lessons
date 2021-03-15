package org.geektimes.projects.user.web.controller;

import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.service.UserService;
import org.geektimes.projects.user.serviceImpl.UserServiceImpl;
import org.geektimes.web.mvc.controller.PageController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.Map;

@Path("/register")
public class RegisterFormController implements PageController {
    @GET
    @POST
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        System.out.println(request.getMethod());
        if (request.getMethod() == "POST") {
            Map<String, String[]> paramsMap = request.getParameterMap();
            User user = new User();
            user.setName(paramsMap.get("name")[0]);
            user.setEmail(paramsMap.get("email")[0]);
            user.setPhoneNumber(paramsMap.get("phoneNumber")[0]);
            user.setPassword(paramsMap.get("password")[0]);
            UserService userService = new UserServiceImpl();
            userService.register(user);
            return "register-result.jsp" ;
        }
        return "register-form.jsp";
    }
}
