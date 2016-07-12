
package com.mycompany.jsf;

import java.util.List;
import javax.faces.bean.ManagedBean;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mycompany.model.Category;
import com.mycompany.model.Priority;
import com.mycompany.model.Task;
import com.sun.jersey.api.client.ClientResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Carlos Cabral
 */
@ManagedBean
public class TaskBean {  
    
    //HttpSession session = SessionUtils.getSession();
    //Object idmember = session.getAttribute("idmember");
    
    List<Task> peq = null;

    /*public Object getIdmember() {
        return idmember;
    }

    public void setIdmember(Object idmember) {
        this.idmember = idmember;
    }*/
    
    
    public List<Task> getPesquisa() {
        return peq;
    }

    /*public HttpSession getSession() {
        return session;
    }*/

    public List<Task> onInit()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        Object idmember = context.getExternalContext().getSessionMap().get("idmember");
        System.out.println("id: "+ idmember);
        
        if(idmember != null)
        {
            Client c = Client.create();
            WebResource wr = c.resource("http://localhost:8080/ServicetList/webresources/TaskService/getAllTasksByComplete/"+idmember);
            String json =  wr.get(String.class);
            //String json =  "[{\"complete\":\"false\",\"description\":\"DescriÃ§Ã£o1\",\"idcategory\":{\"idcategory\":\"1\",\"name\":\"Pessoal\"},\"idpriority\":{\"idpriority\":\"1\",\"name\":\"Alta\",\"value\":\"3\"},\"idtask\":\"4\"}]";
            Gson gson = new Gson();
            return gson.fromJson(json, new TypeToken<List<Task>>(){}.getType());
        }
        else
        {
            //return "login?faces-redirect=true";
        }
        
        //
        return null;
        
        
    }
    
    /*public List<Category> getAllCategories()
    {
        Client c = Client.create();
        WebResource wr = c.resource("http://localhost:8080/tLISTService/webresources/CategoryService/getAllCategories");
        String json =  wr.get(String.class);
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<Category>>(){}.getType());
    }*/
    
    public List<Task> getAllTask() {
        
        //HttpSession session = SessionUtils.getSession();
        //Object idmember = session.getAttribute("idmember");        
        FacesContext context = FacesContext.getCurrentInstance();
        Object idmember = context.getExternalContext().getSessionMap().get("idmember");
        System.out.println("id: "+ idmember);
        Client c = Client.create();
        WebResource wr = c.resource("http://localhost:8080/ServicetList/webresources/TaskService/getAllTasksByMember/"+idmember);
        String json =  wr.get(String.class);
        //String json =  "[{\"complete\":\"false\",\"description\":\"DescriÃ§Ã£o1\",\"idcategory\":{\"idcategory\":\"1\",\"name\":\"Pessoal\"},\"idpriority\":{\"idpriority\":\"1\",\"name\":\"Alta\",\"value\":\"3\"},\"idtask\":\"4\"}]";
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<Task>>(){}.getType());
    }
    
    public void createTask() throws ParseException{
        Client c = Client.create();
        WebResource wr = c.resource("http://localhost:8080/ServicetList/webresources/TaskService/createTask");
        
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        
        String description = request.getParameter("createTask:description");
        String duedate = request.getParameter("createTask:duedate");
        String tags = request.getParameter("createTask:tags");
        String idcategory = request.getParameter("createTask:idcategory");
        String idpriority = request.getParameter("createTask:idpriority");
      
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = sdf.parse(duedate);
        
        Category category = getCategory("1");
        Priority priority = getPriority("2");
        
        Gson gson = new Gson();
        String catString = gson.toJson(category);
        String priString = gson.toJson(priority);
        
        System.out.println(category);
        
        //String category = "{\"idcategory\":\"1\",\"name\":\"Pessoal\"}";
        //String priority = "{\"idpriority\":\"1\",\"name\":\"Alta\",\"value\":3 }";
        
        String[] parts = tags.split(",");
        
        String input = "{\"complete\":0 ,\"description\":\""+description+"\",\"duedate\":\""+d+"\", \"idcategory\":"+ catString +", \"idpriority\":"+priString+"}";
        //String input = "{\"idcategory\":\"2\",\"description\":\"" + description + "\",\"duedate\":\"" + duedate +" \", \"idcategory\":\""+idcategory+"\",\"idpriority\":\""+idpriority+"\"}";

        ClientResponse response = wr.type("application/json")
           .post(ClientResponse.class, input);

        if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                     + response.getStatus());
        }
        else
        {
            System.out.println("Output from Server .... \n");
            int idtask = Integer.parseInt(response.getEntity(String.class));
            
            System.out.println("Inseriu task- idtask" + idtask);
            
            FacesContext context = FacesContext.getCurrentInstance();
            Object idmember = context.getExternalContext().getSessionMap().get("idmember");
            
            System.out.println(idmember);
            
            WebResource wr2 = c.resource("http://localhost:8080/ServicetList/webresources/MemberService/createTaskMember/"+idtask+"/"+idmember);
            ClientResponse response2 = wr2.post(ClientResponse.class);
            
            createTags(parts, idtask);
        }
        
        /*System.out.println("Output from Server .... \n");
        int idtask = Integer.parseInt(response.getEntity(String.class));
        
        createTags(parts, idtask);*/
        
        //return idtask;
        
    }
    
    private void createTags(String[] parts, int idtask)
    {
        Client c = Client.create();
        WebResource wr = c.resource("http://localhost:8080/ServicetList/webresources/TagService/createTag");
        
        System.out.println("createTags: idtask" + idtask);
        
        for(int i=0;i<parts.length;i++)
        {
            String input = "{\"name\":\"" + parts[i] +" \"}";

            ClientResponse response = wr.type("application/json")
               .post(ClientResponse.class, input);

            if (response.getStatus() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                         + response.getStatus());
            }
            else
            {
            
                int idtag = Integer.parseInt(response.getEntity(String.class));
                WebResource wr1 = c.resource("http://localhost:8080/ServicetList/webresources/TaskService/createTaskTag/"+idtask+"/"+idtag);
                ClientResponse response2 = wr1.post(ClientResponse.class);
            }
        }
    }

    private Category getCategory(String idcategory) {
        Client c = Client.create();
        WebResource wr = c.resource("http://localhost:8080/ServicetList/webresources/CategoryService/getCategory/"+idcategory);
        String json =  wr.get(String.class);
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<Category>(){}.getType());
    }

    private Priority getPriority(String idpriority) {
        Client c = Client.create();
        WebResource wr = c.resource("http://localhost:8080/ServicetList/webresources/PriorityService/getPriority/"+idpriority);
        String json =  wr.get(String.class);
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<Priority>(){}.getType());
    }
    
    public void setState()
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String idtask = (String) facesContext.getExternalContext().getRequestParameterMap().get("idtask");
        String complete = (String) facesContext.getExternalContext().getRequestParameterMap().get("complete");
        
        System.out.println("complete:" + complete);
        String newComplete;
        
        if(complete.equals("0"))
        {
            newComplete = "1";
        }
        else
        {
            System.out.println("ORA BOLAS");
            newComplete = "0";
        }
        
        System.out.println("idtask: " + idtask + "new complete:" + newComplete);
        
        Client c = Client.create();
        WebResource wr = c.resource("http://localhost:8080/ServicetList/webresources/TaskService/setState/"+idtask+"/"+newComplete);
        ClientResponse response = wr.post(ClientResponse.class);

    }
    
    public String setMenos()
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String idtask = (String) facesContext.getExternalContext().getRequestParameterMap().get("idtask");
        String idpriority = (String) facesContext.getExternalContext().getRequestParameterMap().get("idpriority");
        
        System.out.println("setMenos" + idpriority);
        String newPriority = "";
        
        if(idpriority.equals("3"))
        {
            newPriority = "2";
        }
        else if(idpriority.equals("2"))
        {
            newPriority = "1";
        }
        else
        {
            return "Não é possível";
        }
        
        System.out.println(newPriority);        
        
        Client c = Client.create();
        WebResource wr = c.resource("http://localhost:8080/ServicetList/webresources/PriorityService/getIDPriority/"+newPriority);
        int jsonID = Integer.parseInt(wr.get(String.class));
        
        WebResource wr1 = c.resource("http://localhost:8080/ServicetList/webresources/TaskService/setPriority/"+idtask+"/"+jsonID);
        ClientResponse response2 = wr1.post(ClientResponse.class);
        
        return null;
    }
    
    public String setMais()
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String idtask = (String) facesContext.getExternalContext().getRequestParameterMap().get("idtask");
        String idpriority = (String) facesContext.getExternalContext().getRequestParameterMap().get("idpriority");
        
        System.out.println("setMais" + idpriority);
        String newPriority = "";
        
        if(idpriority.equals("1"))
        {
            newPriority = "2";
        }
        else if(idpriority.equals("2"))
        {
            newPriority = "3";
        }
        else
        {
            return "Não é possível";
        }
        
        System.out.println(newPriority);        
        
        Client c = Client.create();
        WebResource wr = c.resource("http://localhost:8080/ServicetList/webresources/PriorityService/getIDPriority/"+newPriority);
        int jsonID = Integer.parseInt(wr.get(String.class));
        
        WebResource wr1 = c.resource("http://localhost:8080/ServicetList/webresources/TaskService/setPriority/"+idtask+"/"+jsonID);
        ClientResponse response2 = wr1.post(ClientResponse.class);
        
        return null;
    }
    
    public void pesquisar()
    {
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String pesquisa = request.getParameter("pesquisaForm:pesquisa");
        
        Client c = Client.create();
        WebResource wr = c.resource("http://localhost:8080/ServicetList/webresources/CategoryService/getCategoryByName/"+pesquisa);
        String cat = wr.get(String.class);
        
        //return "/showPesquisa.xhtml?faces-redirect=true&cat=" + cat;
        
        Gson gson = new Gson();
        Category catU = gson.fromJson(cat, new TypeToken<Category>(){}.getType());
        
        Client c1 = Client.create();
        WebResource wr1 = c1.resource("http://localhost:8080/ServicetList/webresources/TaskService/getAllTasksByCategory/"+catU.getIdcategory());
        String ta = wr1.get(String.class);
        
        peq = gson.fromJson(ta, new TypeToken<List<Task>>(){}.getType());
        
        //return gson.fromJson(ta, new TypeToken<List<Task>>(){}.getType());

    }
    
    public void partilhar()
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String idtask = (String) facesContext.getExternalContext().getRequestParameterMap().get("idtask");
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String pesquisa = request.getParameter("partilhar:pesquisa");
        
        System.out.println("Partilhar" + idtask  + "pesquisa " + pesquisa);
        
        Client c1 = Client.create();
        //WebResource wr1 = c1.resource("http://localhost:8080/ServicetList/webresources/MemberService/getIDUsername/"+username);
        //int ta = Integer.parseInt(wr1.get(String.class));
        
        //WebResource wr2 = c1.resource("http://localhost:8080/ServicetList/webresources/MemberService/createTaskMember/"+idtask+"/"+2);
        //ClientResponse response2 = wr2.post(ClientResponse.class);
        
    }
    
    public void permission() throws IOException {
        
        FacesContext context = FacesContext.getCurrentInstance();
        Object idmember = context.getExternalContext().getSessionMap().get("idmember");
        
        //System.out.println(idmember);
        if(idmember == null)
        {
            System.out.println("*** The user has no permission to visit this page. *** ");
            //ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            //context.redirect("login.jsf");
            
            UIViewRoot view = FacesContext.getCurrentInstance().getViewRoot();
            UIComponent comp= view.findComponent("login");
            Map<String, Object> attrMap = comp.getAttributes();
            String className = (String)attrMap.put("style","display:true");
            
            UIComponent comp1= view.findComponent("registar");
            Map<String, Object> attrMap1 = comp1.getAttributes();
            String className1 = (String)attrMap1.put("style","display:true");

        } else {
            System.out.println("*** The session is still active. User is logged in. *** ");
            //System.out.println(idmember);
            
            UIViewRoot view = FacesContext.getCurrentInstance().getViewRoot();
            UIComponent comp= view.findComponent("pesquisa");
            Map<String, Object> attrMap = comp.getAttributes();
            String className = (String)attrMap.put("style","display:true");
            
            UIComponent comp1= view.findComponent("tarefas");
            Map<String, Object> attrMap1 = comp1.getAttributes();
            String className1 = (String)attrMap1.put("style","display:true");
            
            UIComponent comp2= view.findComponent("login");
            Map<String, Object> attrMap2 = comp2.getAttributes();
            String className2 = (String)attrMap2.put("style","display:none");
            
            UIComponent comp3= view.findComponent("registar");
            Map<String, Object> attrMap3 = comp3.getAttributes();
            String className3 = (String)attrMap3.put("style","display:none");

            UIComponent comp4= view.findComponent("logout");
            Map<String, Object> attrMap4 = comp4.getAttributes();
            String className4 = (String)attrMap4.put("style","display:true");
        }
    }
    
   public void logout() throws IOException {
        //session = SessionUtils.getSession();
        //session.invalidate();
       FacesContext.getCurrentInstance().getExternalContext().invalidateSession(); 
       FacesContext.getCurrentInstance().getExternalContext().redirect("home.xhtml");
    }
    
}


