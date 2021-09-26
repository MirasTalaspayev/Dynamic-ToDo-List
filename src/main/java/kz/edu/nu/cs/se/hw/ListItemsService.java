package kz.edu.nu.cs.se.hw;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import java.time.LocalTime;

@Path("/items")
public class ListItemsService {
    
    private List<String> list = new CopyOnWriteArrayList<String>();
    
    public ListItemsService() {
        for (int i = 0; i < 20; i++) {
            list.add("Entry " + String.valueOf(i));
        }
        
        Collections.reverse(list);
    }
    
    @GET
    public Response getList() {
        Gson gson = new Gson();
        
        return Response.ok(gson.toJson(list)).build();
    }
    
    @GET
    @Path("{id: [0-9]+}")
    public Response getListItem(@PathParam("id") String id) {
        int i = Integer.parseInt(id);
        
        return Response.ok(list.get(i)).build();
    }
    
    @POST
    public Response postListItem(@FormParam("newEntry") String entry) {
    	if (entry.isBlank()) 
    		return Response.status(400).build();
    	String now = LocalTime.now().toString();
    	String newItem = entry + ", " + now;
    	list.add(0, newItem);
        
        return Response.ok().build();
    }
    
    @DELETE
    public Response deleteList()
    {
    	list.clear();
    	return Response.ok().build();
    }
    @DELETE
    @Path("{id: [0-9]+}")
    public Response deleteItem(@PathParam("id") String id)
    {
    	int i = Integer.parseInt(id);
    	list.remove(i);
    	return Response.ok().build();
    }
}
