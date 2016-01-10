package com.writers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.logging.Logger;
import com.models.Event;
import com.models.LogData;
import com.models.MatchLogItem;
import com.models.MatchResult;
import com.models.Stat;
import com.models.StatUpdate;
import com.models.Team;
import com.models.TopStat;
import com.models.Wrestler;
import com.owlike.genson.Genson;

public class ManagerIO {

	public boolean addWrestler(Wrestler w) throws Exception {
		boolean success = false;
		Logger logger = new Logger();
		LogData lData = new LogData();

		lData.setWriteToConsole(true);
		lData.setSource(this.getClass().getName());
		w.setPhone("+1"
				+ (w.getPhone().replace("(", "").replace(")", "")
						.replace("-", "").replace(" ", "")));
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			lData.setLogData("Error: unable to load driver class!");
			lData.setType("error");
			success = false;
		}
		String url = "jdbc:mysql://localhost:3306/wrestling";
		String USER = "root";
		String PASS = "";
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, USER, PASS);

			String sql = "INSERT INTO wrestlers (name, weight, teamid,email,phone,contact)"
					+ "values (?, ? ,? ,? ,? ,? )";
			// st.executeUpdate(sql);
			PreparedStatement preparedStmt = conn.prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);
			preparedStmt.setString(1, w.getName());
			preparedStmt.setInt(2, w.getWeight());
			preparedStmt.setInt(3, w.team.getId());
			preparedStmt.setString(4, w.getEmail());
			preparedStmt.setString(5, w.getPhone());
			preparedStmt.setInt(6, w.contact);

			preparedStmt.execute();

			ResultSet rs = null;
			try {
				rs = preparedStmt.getGeneratedKeys();
				if (rs.next()) {
					w.setId(rs.getInt(1));
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (Exception ex) {
					}
				}
			}

			success = true;
			lData.setLogData("Successfully Added Wrestler: " + w.name);
			lData.setType("info");

		} catch (Exception e) {
			success = false;
			lData.setLogData(e.getMessage());
			lData.setType("error");
			throw e;

		} finally {
			conn.close();
		}
		logger.Log(lData);
		return success;

	}

	public boolean setBulletin(String s) throws Exception {
		boolean success = false;
		Logger logger = new Logger();
		LogData lData = new LogData();

		lData.setWriteToConsole(true);
		lData.setSource(this.getClass().getName());

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			lData.setLogData("Error: unable to load driver class!");
			lData.setType("error");
			success = false;
		}
		String url = "jdbc:mysql://localhost:3306/wrestling";
		String USER = "root";
		String PASS = "";
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, USER, PASS);

			String sql = "UPDATE bulletin SET bulletin='" + s + "' WHERE id=1";
			PreparedStatement p = conn.prepareStatement(sql);
			p.executeUpdate();

			success = true;
			lData.setLogData("Successfully Set The Bulletin Content");
			lData.setType("info");

		} catch (Exception e) {
			success = false;
			lData.setLogData(e.getMessage());
			lData.setType("error");
			throw e;

		} finally {
			conn.close();
		}
		logger.Log(lData);
		return success;

	}

	public String getBulletin() throws Exception {
		Logger logger = new Logger();
		LogData lData = new LogData();

		lData.setWriteToConsole(true);
		lData.setSource(this.getClass().getName());

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			lData.setLogData("Error: unable to load driver class!");
			lData.setType("error");

		}
		String url = "jdbc:mysql://localhost:3306/wrestling";
		String USER = "root";
		String PASS = "";
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, USER, PASS);

			String sql = "SELECT b.bulletin FROM bulletin b WHERE id=1";
			Statement st = conn.createStatement();

			ResultSet rs = st.executeQuery(sql);
			rs.first();

			lData.setLogData("Successfully Got The Bulletin Content");
			lData.setType("info");
			return rs.getString("bulletin");

		} catch (Exception e) {
			lData.setLogData(e.getMessage());
			lData.setType("error");
			throw e;
		} finally {
			logger.Log(lData);
			conn.close();
		}
	}

	public boolean deleteWrestler(int id) throws Exception {
		Logger logger = new Logger();
		LogData lData = new LogData();

		lData.setWriteToConsole(true);
		lData.setSource(this.getClass().getName());
		boolean success = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			lData.setLogData("unable to load driver class!");
			lData.setType("error");
			success = false;
		}
		String url = "jdbc:mysql://localhost:3306/wrestling";
		String USER = "root";
		String PASS = "";
		Connection conn = null;
		try {
			Date dt = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, USER, PASS);
			Statement st = conn.createStatement();
			String sql = "UPDATE wrestlers SET deleted='" + sdf.format(dt)
					+ "' WHERE id=" + id;
			st.executeUpdate(sql);
			lData.setLogData("Succesfully Removed Wrestler: " + id);
			lData.setType("info");

			success = true;
		} catch (Exception e) {
			lData.setLogData(e.getMessage());
			lData.setType("error");
			success = false;
			throw e;

		} finally {
			conn.close();
		}
		logger.Log(lData);
		return success;
	}

	public boolean createEvent(Event e) throws Exception {
		boolean success = false;
		Logger logger = new Logger();
		LogData lData = new LogData();

		lData.setWriteToConsole(true);
		lData.setSource(this.getClass().getName());
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			lData.setLogData("Error: unable to load driver class!");
			lData.setType("error");
			success = false;
		}
		String url = "jdbc:mysql://localhost:3306/wrestling";
		String USER = "root";
		String PASS = "";
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, USER, PASS);

			String sql = "INSERT INTO events (event_name, event_description, event_date)"
					+ "values (?, ? ,? )";
			// st.executeUpdate(sql);
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString(1, e.getEventName());
			preparedStmt.setString(2, e.getDescription());
			preparedStmt.setString(3, e.getDateStr());

			preparedStmt.execute();

			success = true;
			lData.setLogData("Successfully Added Event: " + e.getEventName());
			lData.setType("info");

		} catch (Exception e2) {
			success = false;
			lData.setLogData(e2.getMessage());
			lData.setType("error");
			throw e2;

		} finally {
			conn.close();
		}
		logger.Log(lData);
		return success;

	}

	public List<Event> getEvents() throws Exception {
		Genson gen = new Genson();
		List<Event> res = new ArrayList<Event>();
		Logger logger = new Logger();
		LogData lData = new LogData();

		lData.setWriteToConsole(true);
		lData.setSource(this.getClass().getName());
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			lData.setLogData("Error: unable to load driver class!");
			lData.setType("error");
		}
		String url = "jdbc:mysql://localhost:3306/wrestling";
		String USER = "root";
		String PASS = "";
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, USER, PASS);
			Statement st = conn.createStatement();
			String SQL = "SELECT id,event_name,event_description,event_date FROM events WHERE deleted is null";
			ResultSet rs = st.executeQuery(SQL);
			while (rs.next()) {
				int columnId = 1;

				Event e = new Event();
				e.setID(rs.getInt(columnId++));
				e.setEventName(rs.getString(columnId++));
				e.setDescription(rs.getString(columnId++));
				e.setDateStr(rs.getString(columnId++));

				res.add(e);
			}

			lData.setLogData("Successfully Retrieved Events");
			lData.setType("info");

		} catch (Exception e2) {
			lData.setLogData(e2.getMessage());
			lData.setType("error");
			throw e2;

		} finally {
			conn.close();
		}
		logger.Log(lData);
		System.out.println(gen.serialize(res));
		return res;

	}

	public boolean deleteEvent(int id) throws Exception {
		Logger logger = new Logger();
		LogData lData = new LogData();

		lData.setWriteToConsole(true);
		lData.setSource(this.getClass().getName());
		boolean success = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			lData.setLogData("unable to load driver class!");
			lData.setType("error");
			success = false;
		}
		String url = "jdbc:mysql://localhost:3306/wrestling";
		String USER = "root";
		String PASS = "";
		Connection conn = null;
		try {
			Date dt = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, USER, PASS);
			Statement st = conn.createStatement();
			String sql = "UPDATE events SET deleted='" + sdf.format(dt)
					+ "' WHERE id=" + id;
			st.executeUpdate(sql);
			lData.setLogData("Succesfully Removed Event: " + id);
			lData.setType("info");

			success = true;
		} catch (Exception e) {
			lData.setLogData(e.getMessage());
			lData.setType("error");
			success = false;
			throw e;

		} finally {
			conn.close();
		}
		logger.Log(lData);
		return success;
	}

	public boolean LogMatch(List<MatchLogItem> logs) throws Exception {
		boolean success = false;
		Logger logger = new Logger();
		LogData lData = new LogData();

		lData.setWriteToConsole(true);
		lData.setSource(this.getClass().getName());

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			lData.setLogData("Error: unable to load driver class!");
			lData.setType("error");
			success = false;
		}
		String url = "jdbc:mysql://localhost:3306/wrestling";
		String USER = "root";
		String PASS = "";
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, USER, PASS);
			Date dt = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = sdf.format(dt);
			
			for (MatchLogItem log : logs) {
				if (log == null) {
					continue;
				}
				String sql = "INSERT INTO match_logs (match_name, wrestler, period,point_name, point_value, date)"
						+ "values (?, ? ,? ,? ,? ,?)";
				// st.executeUpdate(sql);
				PreparedStatement preparedStmt = conn.prepareStatement(sql);
				preparedStmt.setString(1, log.getMatchName());
				preparedStmt.setString(2, log.getWrestler());
				preparedStmt.setInt(3, log.getPeriod());
				preparedStmt.setString(4, log.getPointType());
				preparedStmt.setInt(5, log.getPointScore());
				preparedStmt.setString(6, date);
		
				preparedStmt.execute();
			}

			success = true;
			lData.setLogData("Successfully Logged Match");
			lData.setType("info");

		} catch (Exception e) {
			success = false;
			lData.setLogData(e.getMessage());
			lData.setType("error");
			throw e;

		} finally {
			conn.close();
		}
		logger.Log(lData);
		return success;

	}

	public List<MatchLogItem> getLogs() throws Exception {
		ArrayList<MatchLogItem> res = new ArrayList<MatchLogItem>();
		Logger logger = new Logger();
		LogData lData = new LogData();

		lData.setWriteToConsole(true);
		lData.setSource(this.getClass().getName());

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			lData.setLogData("Error: unable to load driver class!");
			lData.setType("error");
		}
		String url = "jdbc:mysql://localhost:3306/wrestling";
		String USER = "root";
		String PASS = "";
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, USER, PASS);

			String sql = "SELECT DISTINCT match_name, date FROM match_logs ORDER BY id DESC";
			// st.executeUpdate(sql);

			Statement st = conn.createStatement();

			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				int columnId = 1;

				MatchLogItem mlog = new MatchLogItem();
				mlog.setMatchName(rs.getString(columnId++));
				mlog.setDate(rs.getString(columnId++));
				res.add(mlog);

			}

			lData.setLogData("Successfully Logged Match");
			lData.setType("info");

		} catch (Exception e) {
			lData.setLogData(e.getMessage());
			lData.setType("error");
			throw e;

		} finally {
			conn.close();
		}
		logger.Log(lData);
		return res;
	}
	public MatchResult getMatchResult(String date) throws Exception{
		MatchResult result = new MatchResult();
		List<MatchLogItem> log = getLog(date);
		int awayScore = 0;
		int homeScore = 0;
		result.setMatchName(log.get(0).getMatchName());
		for(MatchLogItem i : getLog(date)){
			if(i.getMatchName()!=null){
				result.setMatchName(i.getMatchName());
			}
			if(i.getWrestler().contains("home")){
				homeScore+=i.getPointScore();
			}else{
				awayScore+=i.getPointScore();
			}
			if(i.getPointType().equals("pin") ||i.getPointType().equals("forfeit")){
				result.setResult("Fall");
			}
			
		}
		result.setAwayScore(awayScore);
		result.setHomeScore(homeScore);
		int scoreDif = homeScore-awayScore;
		if (!result.getResult().equals("Fall")) {

			if (scoreDif <= 7) {
				result.setResult("Win");
			}
			if (scoreDif >= 8 && scoreDif < 15) {
				result.setResult("Major Fall");
			}
			if (scoreDif >= 15) {
				result.setResult("Tech Fall");
			}

			if (scoreDif < 0) {
				result.setResult("Loss");
			}
		}
		Genson gen = new Genson();
		System.out.println(gen.serialize(result));
		return result;
	}
	public List<MatchLogItem> getLog(String date) throws Exception {
		ArrayList<MatchLogItem> res = new ArrayList<MatchLogItem>();
		Logger logger = new Logger();
		LogData lData = new LogData();

		lData.setWriteToConsole(true);
		lData.setSource(this.getClass().getName());

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			lData.setLogData("Error: unable to load driver class!");
			lData.setType("error");
		}
		String url = "jdbc:mysql://localhost:3306/wrestling";
		String USER = "root";
		String PASS = "";
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, USER, PASS);

			String sql = "SELECT wrestler, period, point_name, point_value FROM match_logs WHERE date='"
					+ date + "'";
			// st.executeUpdate(sql);

			Statement st = conn.createStatement();

			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				int columnId = 1;

				MatchLogItem mlog = new MatchLogItem();
				mlog.setWrestler(rs.getString(columnId++));
				mlog.setPeriod(rs.getInt(columnId++));
				mlog.setPointType(rs.getString(columnId++));
				mlog.setPointScore(rs.getInt(columnId++));
				res.add(mlog);

			}

			lData.setLogData("Successfully Logged Match");
			lData.setType("info");

		} catch (Exception e) {
			lData.setLogData(e.getMessage());
			lData.setType("error");
			throw e;

		} finally {
			conn.close();
		}
		logger.Log(lData);
		return res;

	}

	public List<Stat> getStats() throws Exception {
		new Logger();
		LogData lData = new LogData();

		lData.setWriteToConsole(true);
		lData.setSource(this.getClass().getName());
		List<Stat> res = new ArrayList<Stat>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			lData.setLogData("unable to load driver class!");
			lData.setType("error");
		}
		String url = "jdbc:mysql://localhost:3306/wrestling";
		String USER = "root";
		String PASS = "";
		Connection conn = null;

		String SQL = "SELECT * FROM stats s INNER JOIN wrestlers w WHERE w.id=s.wrestlerID AND w.deleted is null ORDER BY w.weight ASC";
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, USER, PASS);
			Statement st = conn.createStatement();

			ResultSet rs = st.executeQuery(SQL);
			while (rs.next()) {
				int columnId = 2;

				Stat stat = new Stat();
				stat.setWrestlerID(rs.getInt(columnId++));
				stat.setTakedown(rs.getInt(columnId++));
				stat.setEscape(rs.getInt(columnId++));
				stat.setNearfall(rs.getInt(columnId++));
				stat.setPin(rs.getInt(columnId++));
				stat.setWin(rs.getInt(columnId++));
				stat.setLoss(rs.getInt(columnId++));
				stat.setTeampoint(rs.getInt(columnId++));
				stat.setReversal(rs.getInt(columnId++));
				stat.setForfeit(rs.getInt(columnId++));
				stat.setWrestlerName(rs.getString(columnId+1));

				res.add(stat);
			}

		} catch (Exception e) {
			lData.setLogData(e.getMessage());
			lData.setType("error");
			throw e;

		} finally {
			conn.close();
		}
		lData.setLogData("Successfully Retrieved All Stats");
		lData.setType("info");
		return res;

	}

	public Stat getStats(int wrestlerId) throws Exception {
		Logger logger = new Logger();
		LogData lData = new LogData();

		lData.setWriteToConsole(true);
		lData.setSource(this.getClass().getName());
		new ArrayList<Stat>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			lData.setLogData("unable to load driver class!");
			lData.setType("error");

		}
		Stat stat = new Stat();
		String url = "jdbc:mysql://localhost:3306/wrestling";
		String USER = "root";
		String PASS = "";
		Connection conn = null;
		String SQL = "SELECT wrestlerID,takedown,escape,nearfall,pin,win,loss,teampoint,reversal,forfeit FROM stats WHERE wrestlerID="
				+ wrestlerId;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, USER, PASS);
			Statement st = conn.createStatement();

			ResultSet rs = st.executeQuery(SQL);

			if (rs.next()) {
				int columnId = 1;

				stat.setWrestlerID(rs.getInt(columnId++));
				stat.setTakedown(rs.getInt(columnId++));
				stat.setEscape(rs.getInt(columnId++));
				stat.setNearfall(rs.getInt(columnId++));
				stat.setPin(rs.getInt(columnId++));
				stat.setWin(rs.getInt(columnId++));
				stat.setLoss(rs.getInt(columnId++));
				stat.setTeampoint(rs.getInt(columnId++));
				stat.setReversal(rs.getInt(columnId++));
				stat.setForfeit(rs.getInt(columnId++));

			}
			

		} catch (Exception e) {
			lData.setLogData(e.getMessage());
			lData.setType("error");
			throw e;

		} finally {
			conn.close();
		}
		lData.setLogData("Successfully Retrieved Stats For: " + wrestlerId);
		lData.setType("info");
		logger.Log(lData);
		
		return stat;

	}

	public List<TopStat> getTopStats(ArrayList<String> statsToGet, int teamId)
			throws Exception {
		new Logger();
		LogData lData = new LogData();

		lData.setWriteToConsole(true);
		lData.setSource(this.getClass().getName());
		List<TopStat> res = new ArrayList<TopStat>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			lData.setLogData("unable to load driver class!");
			lData.setType("error");
		}
		String url = "jdbc:mysql://localhost:3306/wrestling";
		String USER = "root";
		String PASS = "";
		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, USER, PASS);
			Statement st = conn.createStatement();
			for (String s : statsToGet) {
				String SQL = "SELECT w.name, s."
						+ s
						+ " FROM stats s "
						+ "INNER JOIN wrestlers w ON w.id=s.wrestlerID WHERE w.deleted is null AND w.teamid="
						+ teamId + " order by s." + s + " desc limit 1 ";
				ResultSet rs = st.executeQuery(SQL);
				while (rs.next()) {
					int columnId = 1;

					TopStat stat = new TopStat();
					stat.setStat(s);
					stat.setWrestler(rs.getString(columnId++));
					stat.setNumber(rs.getInt(columnId++));
					if (stat.getNumber() != 0) {
						res.add(stat);
					}
				}
			}
		} catch (Exception e) {
			lData.setLogData(e.getMessage());
			lData.setType("error");
			throw e;

		} finally {
			conn.close();
		}
		lData.setLogData("Successfully Retrieved All Stats");
		lData.setType("info");
		return res;

	}

	public boolean createStatRow(Wrestler w) throws Exception {
		Logger logger = new Logger();
		LogData lData = new LogData();

		lData.setWriteToConsole(true);
		lData.setSource(this.getClass().getName());
		boolean success = false;
		System.out.println(w.id);
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			lData.setLogData("unable to load driver class!");
			lData.setType("error");
			success = false;
		}
		String url = "jdbc:mysql://localhost:3306/wrestling";
		String USER = "root";
		String PASS = "";
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection(url, USER, PASS);

			String sql = "INSERT INTO stats (wrestlerID)" + "values (?)";
			// st.executeUpdate(sql);
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setInt(1, w.id);
			preparedStmt.execute();

			lData.setLogData("Successfully Created Stats Row For: " + w.name);
			lData.setType("info");
			success = true;

		} catch (Exception e) {
			lData.setLogData(e.getMessage());
			lData.setType("error");
			success = false;
			throw e;

		}
		logger.Log(lData);
		return success;
	}

	public boolean updateStats(List<StatUpdate> stats) throws Exception {
		boolean success = false;
		Logger logger = new Logger();
		LogData lData = new LogData();

		lData.setWriteToConsole(true);
		lData.setSource(this.getClass().getName());
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			lData.setLogData("Error: unable to load driver class!");
			lData.setType("error");
			success = false;
		}
		String url = "jdbc:mysql://localhost:3306/wrestling";
		String USER = "root";
		String PASS = "";
		Connection conn = null;
		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, USER, PASS);
			for (StatUpdate s : stats) {
				if (s == null) {
					continue;
				}
				if (s.getType().equals("nearfall")
						|| s.getType().equals("teampoint")) {
					String sql = "UPDATE stats SET " + s.getType() + " = "
							+ s.getType() + " + " + s.getScore()
							+ " WHERE wrestlerID=" + s.getWrestlerID();
					// st.executeUpdate(sql);
					PreparedStatement preparedStmt = conn.prepareStatement(sql);

					preparedStmt.executeUpdate();
				} else if (s.getScore() < 0) {
					String sql = "UPDATE stats SET " + s.getType() + " = "
							+ s.getType() + " - 1 WHERE wrestlerID="
							+ s.getWrestlerID();
					// st.executeUpdate(sql);
					PreparedStatement preparedStmt = conn.prepareStatement(sql);

					preparedStmt.executeUpdate();
				} else {
					String sql = "UPDATE stats SET " + s.getType() + " = "
							+ s.getType() + " + 1 WHERE wrestlerID="
							+ s.getWrestlerID();
					// st.executeUpdate(sql);
					PreparedStatement preparedStmt = conn.prepareStatement(sql);

					preparedStmt.executeUpdate();
				}

			}

			success = true;
			lData.setLogData("Successfully Updated Stats For: ");
			lData.setType("info");

		} catch (Exception e) {
			success = false;
			lData.setLogData(e.getMessage());
			lData.setType("error");
			throw e;

		} finally {
			conn.close();
		}
		logger.Log(lData);
		return success;

	}

	public boolean updateWrestler(Wrestler w) throws Exception {
		Logger logger = new Logger();
		LogData lData = new LogData();

		lData.setWriteToConsole(true);
		lData.setSource(this.getClass().getName());
		boolean success = false;

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			lData.setLogData("unable to load driver class!");
			lData.setType("error");
			success = false;
		}
		String url = "jdbc:mysql://localhost:3306/wrestling";
		String USER = "root";
		String PASS = "";
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, USER, PASS);
			String sql = "UPDATE wrestlers SET name=?,weight=?,teamID=?,email=?,phone=?,contact=? WHERE id="
					+ w.id;
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString(1, w.getName());
			preparedStmt.setInt(2, w.getWeight());
			preparedStmt.setInt(3, w.team.getId());
			preparedStmt.setString(4, w.getEmail());
			preparedStmt.setString(5, w.getPhone());
			preparedStmt.setInt(6, w.contact);
			preparedStmt.execute();
			success = true;

			lData.setLogData("Successfully Update Wrestler Info For: " + w.id);
			lData.setType("info");

		} catch (Exception e) {
			lData.setLogData(e.getMessage());
			lData.setType("error");
			success = false;
			throw e;

		} finally {
			conn.close();
		}
		logger.Log(lData);
		return success;
	}

	public Wrestler getWrestler(int wrestlerId) throws Exception {
		Logger logger = new Logger();
		LogData lData = new LogData();

		lData.setWriteToConsole(true);
		lData.setSource(this.getClass().getName());

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			lData.setLogData("unable to load driver class!");
			lData.setType("error");

		}
		String url = "jdbc:mysql://localhost:3306/wrestling";
		String USER = "root";
		String PASS = "";
		Connection conn = null;
		String SQL = "select t.id,t.name,w.id,w.name,w.weight,w.email,w.phone,w.contact "
				+ "from wrestlers w "
				+ " INNER JOIN teams t on t.id=w.teamID WHERE w.id="
				+ wrestlerId + " ORDER BY weight ASC";

		Wrestler wrestler = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, USER, PASS);
			Statement st = conn.createStatement();

			ResultSet rs = st.executeQuery(SQL);
			if (rs.next()) {
				int columnId = 1;

				Team team = new Team();
				team.setId(rs.getInt(columnId++));
				team.setName(rs.getString(columnId++));

				wrestler = new Wrestler();
				wrestler.setId(rs.getInt(columnId++));
				wrestler.setName(rs.getString(columnId++));
				wrestler.setWeight(rs.getInt(columnId++));
				wrestler.setEmail(rs.getString(columnId++));
				wrestler.setPhone(rs.getString(columnId++));
				wrestler.setContact(rs.getInt(columnId++));

				wrestler.setTeam(team);

			}
			lData.setLogData("Sucessfully Retrieved Wrestler: " + wrestlerId);
			lData.setType("info");

		} catch (Exception e) {
			lData.setLogData(e.getMessage());
			lData.setType("error");
			throw e;

		} finally {
			conn.close();
		}
		logger.Log(lData);
		return wrestler;

	}

	public List<Wrestler> getTeam(int teamId) throws Exception {
		Logger logger = new Logger();
		LogData lData = new LogData();

		lData.setWriteToConsole(true);
		lData.setSource(this.getClass().getName());
		List<Wrestler> res = new ArrayList<Wrestler>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			lData.setLogData("unable to load driver class!");
			lData.setType("error");

		}
		String url = "jdbc:mysql://localhost:3306/wrestling";
		String USER = "root";
		String PASS = "";
		Connection conn = null;
		String SQL = "select w.id,w.name,w.weight,w.email,w.phone,w.contact "
				+ "from wrestlers w " + "WHERE w.deleted is null AND w.teamid="
				+ teamId + " ORDER BY weight ASC";
		Wrestler wrestler = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, USER, PASS);
			Statement st = conn.createStatement();

			ResultSet rs = st.executeQuery(SQL);
			while (rs.next()) {
				int columnId = 1;
				wrestler = new Wrestler();
				wrestler.setId(rs.getInt(columnId++));
				wrestler.setName(rs.getString(columnId++));
				wrestler.setWeight(rs.getInt(columnId++));
				wrestler.setEmail(rs.getString(columnId++));
				wrestler.setPhone(rs.getString(columnId++));
				wrestler.setContact(rs.getInt(columnId++));
				res.add(wrestler);
			}

			lData.setLogData("Sucessfully Retrieved All Wrestlers for team "
					+ teamId);
			lData.setType("info");

		} catch (Exception e) {
			lData.setLogData(e.getMessage());
			lData.setType("error");
			throw e;

		} finally {
			conn.close();
		}
		logger.Log(lData);
		return res;

	}

	public List<Wrestler> getAllWrestlers() throws Exception {
		Logger logger = new Logger();
		LogData lData = new LogData();

		lData.setWriteToConsole(true);
		lData.setSource(this.getClass().getName());
		List<Wrestler> res = new ArrayList<Wrestler>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			lData.setLogData("unable to load driver class!");
			lData.setType("error");

		}
		String url = "jdbc:mysql://localhost:3306/wrestling";
		String USER = "root";
		String PASS = "";
		Connection conn = null;
		String SQL = "select t.id,t.name,w.id,w.name,w.weight,w.email,w.phone,w.contact from wrestlers w INNER JOIN "
				+ "teams t on t.id=w.teamID WHERE w.deleted is null ORDER BY weight ASC";
		Wrestler wrestler = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, USER, PASS);
			Statement st = conn.createStatement();

			ResultSet rs = st.executeQuery(SQL);
			while (rs.next()) {
				int columnId = 1;
				Team team = new Team();
				team.setId(rs.getInt(columnId++));
				team.setName(rs.getString(columnId++));

				wrestler = new Wrestler();
				wrestler.setId(rs.getInt(columnId++));
				wrestler.setName(rs.getString(columnId++));
				wrestler.setWeight(rs.getInt(columnId++));
				wrestler.setEmail(rs.getString(columnId++));
				wrestler.setPhone(rs.getString(columnId++));
				wrestler.setContact(rs.getInt(columnId++));
				wrestler.setTeam(team);
				res.add(wrestler);
			}

			lData.setLogData("Sucessfully Retrieved All Wrestlers");
			lData.setType("info");

		} catch (Exception e) {
			lData.setLogData(e.getMessage());
			lData.setType("error");
			throw e;

		} finally {
			conn.close();
		}
		logger.Log(lData);
		return res;

	}
	
	public List<MatchLogItem> getCurrentMatch() throws Exception {
		Logger logger = new Logger();
		LogData lData = new LogData();

		lData.setWriteToConsole(true);
		lData.setSource(this.getClass().getName());
		List<MatchLogItem> res = new ArrayList<MatchLogItem>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			lData.setLogData("unable to load driver class!");
			lData.setType("error");

		}
		String url = "jdbc:mysql://localhost:3306/wrestling";
		String USER = "root";
		String PASS = "";
		Connection conn = null;
		String SQL = "SELECT match_name, wrestler, period, point_name, point_value, date FROM current_match ORDER BY id ASC";
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, USER, PASS);
			Statement st = conn.createStatement();

			ResultSet rs = st.executeQuery(SQL);
			while (rs.next()) {
				int columnId = 1;
				MatchLogItem update = new MatchLogItem();
				update.setMatchName(rs.getString(columnId++));
				update.setWrestler(rs.getString(columnId++));
				update.setPeriod(rs.getInt(columnId++));
				update.setPointType(rs.getString(columnId++));
				update.setPointScore(rs.getInt(columnId++));
				update.setDate(rs.getString(columnId++));
				res.add(update);
			}

			lData.setLogData("Sucessfully Retrieved Current Match");
			lData.setType("info");

		} catch (Exception e) {
			lData.setLogData(e.getMessage());
			lData.setType("error");
			throw e;

		} finally {
			conn.close();
		}
		logger.Log(lData);
		return res;

	}
	public boolean updateCurrentMatch(MatchLogItem item) throws Exception {
		Logger logger = new Logger();
		LogData lData = new LogData();

		lData.setWriteToConsole(true);
		lData.setSource(this.getClass().getName());
		boolean success = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			lData.setLogData("unable to load driver class!");
			lData.setType("error");

		}
		String url = "jdbc:mysql://localhost:3306/wrestling";
		String USER = "root";
		String PASS = "";
		Connection conn = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, USER, PASS);
			Date dt = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = sdf.format(dt);
			
			
				
				String sql = "INSERT INTO current_match (match_name, wrestler, period, point_name, point_value, date)"
						+ "values (?, ? ,? ,? ,? ,?)";
				// st.executeUpdate(sql);
				PreparedStatement preparedStmt = conn.prepareStatement(sql);
				preparedStmt.setString(1, item.getMatchName());
				preparedStmt.setString(2, item.getWrestler());
				preparedStmt.setInt(3, item.getPeriod());
				preparedStmt.setString(4, item.getPointType());
				preparedStmt.setInt(5, item.getPointScore());
				preparedStmt.setString(6, date);
				

				preparedStmt.execute();
			

			success = true;
			lData.setLogData("Successfully Updated Current Match");
			lData.setType("info");
			
		} catch (Exception e) {
			lData.setLogData(e.getMessage());
			lData.setType("error");
			success=false;
			throw e;

		} finally {
			conn.close();
		}
		logger.Log(lData);
		return success;

	}
	public boolean truncateCurrentMatch() throws Exception {
		Logger logger = new Logger();
		LogData lData = new LogData();

		lData.setWriteToConsole(true);
		lData.setSource(this.getClass().getName());
		boolean success = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			lData.setLogData("unable to load driver class!");
			lData.setType("error");

		}
		String url = "jdbc:mysql://localhost:3306/wrestling";
		String USER = "root";
		String PASS = "";
		Connection conn = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, USER, PASS);
			
			Statement st = conn.createStatement();
			
				
			String sql = "TRUNCATE current_match";
			st.execute(sql);
		
			success = true;
			lData.setLogData("Successfully Truncated Current Match");
			lData.setType("info");
			
		} catch (Exception e) {
			lData.setLogData(e.getMessage());
			lData.setType("error");
			success=false;
			throw e;

		} finally {
			conn.close();
		}
		logger.Log(lData);
		return success;

	}
}
