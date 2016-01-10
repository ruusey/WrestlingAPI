package com.servlets;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import javax.ws.rs.core.MediaType;

import com.models.Event;
import com.models.MatchLogItem;
import com.models.MatchResult;
import com.models.Response;
import com.models.Stat;
import com.models.StatUpdate;
import com.models.TopStat;
import com.models.Wrestler;
import com.owlike.genson.Genson;
import com.sms.Sender;
import com.writers.ManagerIO;

@Path("/Wrestling")
public class Endpoints {
	Genson gen = new Genson();
	ManagerIO IO = new ManagerIO();
	Sender sender = new Sender();

	@POST
	@Path("/AddWrestler")
	@Produces("application/json")
	@Consumes("application/json")
	public Response addWrestler(Wrestler w) {

		Response response = new Response();
		boolean success = false;
		try {
			success = IO.addWrestler(w);
			response.setMessage("Succesfully Added wrestler: " + w.name
					+ " with id=" + w.getId());
		} catch (Exception e) {
			response.setMessage(e.getMessage());
		}
		response.setSuccess(success);
		addStatRow(w);
		return response;
	}

	@POST
	@Path("/SetBulletin")
	@Produces("application/json")
	@Consumes(MediaType.WILDCARD)
	public Response setBulletin(String html) {

		Response response = new Response();
		boolean success = false;
		try {
			success = IO.setBulletin(html);
			response.setMessage("Successfully Updated The Bulletin");
		} catch (Exception e) {
			response.setMessage(e.getMessage());
		}
		response.setSuccess(success);
		return response;
	}

	@GET
	@Path("/GetBulletin")
	@Produces(MediaType.WILDCARD)
	@Consumes(MediaType.WILDCARD)
	public String getBulletin(String html) {

		try {
			return IO.getBulletin();

		} catch (Exception e) {

		}
		return null;
	}

	@POST
	@Path("/Update")
	@Consumes("application/json")
	@Produces("application/json")
	public Response updateWrestler(Wrestler w) {
		Response response = new Response();
		try {
			response.setSuccess(IO.updateWrestler(w));
			response.setMessage("Updated Wrestler Information For " + w.name);
		} catch (Exception e) {
			response.setMessage(e.getMessage());
		}
		return response;
	}

	@POST
	@Path("/CreateStatRow")
	@Consumes("application/json")
	@Produces("application/json")
	public Response addStatRow(Wrestler w) {
		Response response = new Response();

		try {
			response.setSuccess(IO.createStatRow(w));
			;
			response.setMessage("Created Stat Row For " + w.name);
		} catch (Exception e) {
			response.setMessage(e.getMessage());
		}
		return response;
	}

	@GET
	@Path("/DeleteWrestler")
	@Consumes(MediaType.WILDCARD)
	@Produces("application/json")
	public Response deleteWrestler(@QueryParam("wrestlerId") int wrestlerId) {
		Response response = new Response();
		boolean success = false;
		try {
			success = IO.deleteWrestler(wrestlerId);
			response.setMessage("Succesfully Removed Wrestler: " + wrestlerId);
		} catch (Exception e) {
			response.setMessage(e.getMessage());
		}
		response.setSuccess(success);
		return response;

	}

	@GET
	@Path("/GetTopStats")
	@Consumes(MediaType.WILDCARD)
	@Produces("application/json")
	public List<TopStat> getTopStats(@QueryParam("teamId") int teamId) {
		ArrayList<String> statsToGet = new ArrayList<String>();
		statsToGet.add("takedown");
		statsToGet.add("escape");
		statsToGet.add("nearfall");
		statsToGet.add("pin");
		statsToGet.add("win");
		statsToGet.add("teampoint");
		statsToGet.add("reversal");
		statsToGet.add("forfeit");
		try {
			return IO.getTopStats(statsToGet, teamId);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GET
	@Path("/GetWrestler")
	@Produces("application/json")
	public Wrestler getWrestler(@QueryParam("wrestlerId") int wrestlerId) {

		try {
			return IO.getWrestler(wrestlerId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@GET
	@Path("/GetAll")
	@Produces("application/json")
	public List<Wrestler> getAll(@QueryParam("teamId") int teamId) {

		try {
			if (teamId == 0) {
				return IO.getAllWrestlers();
			} else {
				return IO.getTeam(teamId);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GET
	@Path("/GetStats")
	@Produces("application/json")
	public List<Stat> getStats() {

		try {
			return IO.getStats();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@GET
	@Path("/IndividualStats")
	@Produces("application/json")
	public Stat individualStats(@QueryParam("wrestlerId") int wrestlerId) {

		try {
			return IO.getStats(wrestlerId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@POST
	@Path("/UpdateStats")
	@Consumes("application/json")
	@Produces("application/json")
	public Response updateStats(List<StatUpdate> stats) {
		Response response = new Response();

		try {
			response.setSuccess(IO.updateStats(stats));
			;
			response.setMessage("Updated Stats");
		} catch (Exception e) {
			response.setMessage(e.getMessage());
		}
		return response;
	}

	@POST
	@Path("/SendSMS")
	@Consumes(MediaType.WILDCARD)
	@Produces("application/json")
	public Response sendSMS(String s) {

		Response response = new Response();
		try {
			response.setSuccess(sender.send(s));
			response.setMessage("Sending SMS to Wrestlers");
		} catch (Exception e) {
			response.setMessage(e.getMessage());
		}
		return response;
	}

	@POST
	@Path("/LogMatch")
	@Consumes("application/json")
	@Produces("application/json")
	public Response LogMatch(List<MatchLogItem> list) {
		System.out.println(gen.serialize(list));
		Response response = new Response();
		try {
			response.setSuccess(IO.LogMatch(list));
			response.setMessage("Successfully Logged Match");
		} catch (Exception e) {
			response.setMessage(e.getMessage());
		}
		return response;
	}

	@GET
	@Path("/GetMatchLogs")
	@Produces("application/json")
	public List<MatchLogItem> GetMatchLogs(@QueryParam("date") String date) {
		if (date == null) {
			try {
				return IO.getLogs();
			} catch (Exception e) {
				System.out.println("Failed Retriving Logged Matches"
						+ e.getMessage());
			}
		} else {
			try {
				return IO.getLog(date);
			} catch (Exception e) {
				System.out.println("Failed Retriving Logged Matches"
						+ e.getMessage());
			}
		}

		return null;
	}

	@GET
	@Path("/GetResult")
	@Produces("application/json")
	public MatchResult getMatchResult(@QueryParam("date") String date) {
		try {
			return IO.getMatchResult(date);
		} catch (Exception e) {
			System.out
					.println("Failed Retriving Match Result" + e.getMessage());
		}
		return null;
	}

	@POST
	@Path("/AddEvent")
	@Consumes("application/json")
	@Produces("application/json")
	public Response AddEvent(Event e) {
		Response response = new Response();
		try {
			response.setSuccess(IO.createEvent(e));
			response.setMessage("Successfully Added Event: " + e.getEventName());
		} catch (Exception e2) {
			response.setMessage(e2.getMessage());
		}
		return response;
	}

	@GET
	@Path("/GetEvents")
	@Consumes("application/json")
	@Produces("application/json")
	public List<Event> GetEvents() {

		try {
			return IO.getEvents();

		} catch (Exception e2) {
			System.out.println("Failed To Retrieve Events:" + e2.getMessage());
		}
		return null;
	}
	@GET
	@Path("/GetCurrentMatch")
	@Consumes("application/json")
	@Produces("application/json")
	public List<MatchLogItem> GetCurrentMatch() {

		try {
			return IO.getCurrentMatch();

		} catch (Exception e2) {
			System.out.println("Failed To Retrieve Current Match:" + e2.getMessage());
		}
		return null;
	}
	@POST
	@Path("/UpdateCurrentMatch")
	@Consumes("application/json")
	@Produces("application/json")
	public Response UpdateCurrentMatch(MatchLogItem item) {
		Response response = new Response();
		try {
			response.setSuccess(IO.updateCurrentMatch(item));
			response.setMessage("Successfully Updated Current Match");
		} catch (Exception e2) {
			response.setMessage(e2.getMessage());
		}
		return response;
	}

	@GET
	@Path("/TruncateCurrentMatch")
	@Produces("application/json")
	public Response deleteEvent() {
		Response response = new Response();
		boolean success = false;
		try {
			success = IO.truncateCurrentMatch();
			response.setMessage("Succesfully Truncated Current Match");
		} catch (Exception e) {
			response.setMessage(e.getMessage());
		}
		response.setSuccess(success);
		return response;

	}

}
