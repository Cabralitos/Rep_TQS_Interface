
package com.mycompany.jsf;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mycompany.model.Member;
import com.sun.jersey.api.client.ClientResponse;
import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Carlos Cabral
 */
@ManagedBean
public class IndexBean {

  
    public List<Member> getAllMembers() {
        Client c = Client.create();
        WebResource wr = c.resource("http://localhost:8080/ServicetList/webresources/MemberService/getAllMembers");
        String json =  wr.get(String.class);
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<Member>>(){}.getType());
  }
    
    public void createMember(){
        Client c = Client.create();
        WebResource wr = c.resource("http://localhost:8080/ServicetList/webresources/MemberService/createMember");
        
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String username = request.getParameter("registerForm:username");
        String password = request.getParameter("registerForm:password");
               
        String input = "{\"username\":\"" + username + "\",\"password\":\"" + password +" \"}";

        ClientResponse response = wr.type("application/json")
           .post(ClientResponse.class, input);

        /*if (response.getStatus() != 204) {
                throw new RuntimeException("Failed : HTTP error code : "
                     + response.getStatus());
        }*/

        //System.out.println("Output from Server .... \n");
        //String output = response.getEntity(String.class);
        //return output;
        
    }
    
    public void login() throws IOException
    {
        Client c = Client.create();
        WebResource wr = c.resource("http://localhost:8080/ServicetList/webresources/MemberService/confirmLogin");
        
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String username = request.getParameter("loginForm:username");
        String password = request.getParameter("loginForm:password");
               
        String input = "{\"username\":\"" + username + "\",\"password\":\"" + password +" \"}";

        ClientResponse response = wr.type("application/json")
           .post(ClientResponse.class, input);

        if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                     + response.getStatus());
        }

        String output = response.getEntity(String.class);
        Gson gson = new Gson();
        Member m = gson.fromJson(output, new TypeToken<Member>(){}.getType());
        
        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println("Aqui:" + m.getUsername());
        
        
        if ( m.getUsername() != "") {
           //HttpSession session = SessionUtils.getSession();
           //session.setAttribute("username", m.getUsername());
           //session.setAttribute("idmember", m.getMemberid());
            context.getExternalContext().getSessionMap().put("idmember", m.getMemberid());
            System.out.println("login");
            FacesContext.getCurrentInstance().getExternalContext().dispatch("listTask.xhtml");
            //return "admin";
        } else {
                FacesContext.getCurrentInstance().addMessage(
                                null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                "Incorrect Username and Passowrd",
                                                "Please enter correct username and Password"));
                //return "login";
        }
        
        
        //System.out.println("Output from Server .... \n");
        //String output = response.getEntity(String.class);
        //return output;
        
    
    }
    
    
    
}
