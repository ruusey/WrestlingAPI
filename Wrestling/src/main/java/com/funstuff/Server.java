package com.funstuff;

import java.io.File;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.models.Response;
import com.owlike.genson.Genson;

@Path("/Game")
public class Server{
	public ArrayList<PlayerEntity> players = new ArrayList<PlayerEntity>();
	Genson gen = new Genson();
	State state = new State();
	@POST
	@Path("/Connect")
	@Produces("application/json")
	@Consumes(MediaType.WILDCARD)
	public Response Connect(PlayerEntity pe, @Context HttpServletRequest req) {
		if(!state.isAlive()){
			state.start();
		}
		
		
		Response response = new Response();
		boolean success = false;
		
		
		try {
			pe.setIp(req.getRemoteAddr());
			success = state.players.add(pe);
			System.out.println("New Client Connection: "+gen.serialize(pe));
			response.setMessage("Successfully Connected To Server");
		} catch (Exception e) {
			response.setMessage(e.getMessage());
		}
		response.setSuccess(success);
		return response;
	}

	@GET
	@Path("/Update")
	@Produces("application/json")
	public ArrayList<PlayerEntity> update() {

		try {
			System.out.println(gen.serialize(state.getPlayers()));
			return state.getPlayers();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}