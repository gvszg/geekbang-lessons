package org.geektimes.projects.user.web.controller;

import org.geektimes.web.mvc.controller.PageController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/register_form")
public class RegisterFormController implements PageController {
    @GET
//    @Path("/user")
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        return "register-form.jsp";
    }
}
