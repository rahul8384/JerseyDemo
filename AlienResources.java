package com.teluskolearning.demorestjersey;
import jakarta.ws.rs.Path;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.websocket.server.PathParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.core.HttpHeaders;

import java.util.*;



@Path("aliens") 
public class AlienResources {
	
	AlienRepository repo = new AlienRepository();
	
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<Alien> getAliens() {
		
		System.out.println("calling alien...");
		return repo.getAliens();
	}
	
	
	
	@GET
	@Path("alien/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Alien getAlien(@PathParam("id") int id) {
		System.out.println("Fetching Alien with ID: " + id);

		Alien alien = repo.getAlien(id);
//		System.out.println("in the parameter ");
		if (alien == null) {
	        // Handle case where no Alien is found
	        Alien notFoundAlien = new Alien();
	        notFoundAlien.setId(0);
	        notFoundAlien.setName("Not Found");
	        notFoundAlien.setPoints(0);
	        return notFoundAlien;
	    }
	    return alien;
	}
	
//	@GET
//	@Path("alien/{id}")
//	@Produces(MediaType.TEXT_PLAIN)
//	public String getAlienTest(@PathParam("id") int id) {
//	    System.out.println("Received GET request for Alien ID: " + id);
//	    Alien alien = repo.getAlien(id);
//	    if (alien == null) {
//	        return "Alien not found!";
//	    }
//	    return "Alien: " + alien.getName() + ", Points: " + alien.getPoints();
//	}




	
	@POST
	@Path("alien")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Alien createAlien(Alien a1){
		System.out.println(a1);
		return repo.create(a1);
	}
	
	@PUT
	@Path("alien")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Alien updateAlien(Alien alien) {
	    System.out.println("Updating Alien: " + alien);
	    Alien updatedAlien = repo.update(alien);
	    if (updatedAlien == null) {
	        throw new WebApplicationException("Alien with ID " + alien.getId() + " not found.", 404);
	    }
	    return updatedAlien;
	}
	
	@DELETE
	@Path("alien/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteAlien(@PathParam("id") int id) {
	    System.out.println("Deleting Alien with ID: " + id);
	    boolean isDeleted = repo.delete(id);
	    if (!isDeleted) {
	        throw new WebApplicationException("Alien with ID " + id + " not found.", 404);
	    }
	    return "Alien with ID " + id + " deleted successfully.";
	}


}
