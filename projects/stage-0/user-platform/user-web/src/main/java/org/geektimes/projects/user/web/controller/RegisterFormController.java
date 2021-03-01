package org.geektimes.projects.user.web.controller;

import org.geektimes.web.mvc.controller.PageController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/register")
public class RegisterFormController implements PageController {
    @GET
    @POST
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        System.out.println(request.getMethod());
        if (request.getMethod() == "POST") {
           return "register-result.jsp" ;
        }
        return "register-form.jsp";
    }
}
