package hr.fer.zemris.java.hw14.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw14.dao.DAO;
import hr.fer.zemris.java.hw14.dao.DAOException;
import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOptions;



/**
 * Ovo je implementacija podsustava DAO uporabom tehnologije SQL. Ova
 * konkretna implementacija očekuje da joj veza stoji na raspolaganju
 * preko {@link SQLConnectionProvider} razreda, što znači da bi netko
 * prije no što izvođenje dođe do ove točke to trebao tamo postaviti.
 * U web-aplikacijama tipično rješenje je konfigurirati jedan filter 
 * koji će presresti pozive servleta i prije toga ovdje ubaciti jednu
 * vezu iz connection-poola, a po zavrsetku obrade je maknuti.
 *  
 * @author marcupic
 */
public class SQLDAO implements DAO {

	@Override
	public List<Poll> getPolls() {
		
		Connection con = SQLConnectionProvider.getConnection();
		
		List<Poll> polls = new ArrayList<>();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select id, title, message from Polls order by id");
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while(rs!=null && rs.next()) {
						Poll poll = new Poll();
						poll.setId(rs.getLong(1));
						poll.setTitle(rs.getString(2));
						poll.setMessage(rs.getString(3));
						polls.add(poll);
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata liste korisnika.", ex);
		}
		return polls;
	}

	@Override
	public List<PollOptions> getPollOptions(long id) {
		
		Connection con = SQLConnectionProvider.getConnection();
		
		List<PollOptions> pollOptions = new ArrayList<>();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select * from polloptions where pollid=?");
			pst.setLong(1, Long.valueOf(id));
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while(rs!=null && rs.next()) {
						PollOptions option = new PollOptions();
						option.setId(rs.getLong(1));
						option.setOptionTitle(rs.getString(2));
						option.setOptionLink(rs.getString(3));
						option.setPollId(id);
						option.setCount(rs.getInt(5));
						
						pollOptions.add(option);
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata liste korisnika.", ex);
		}
		return pollOptions;
	}

	@Override
	public Poll getPoll(long pollId) {
		Connection con = SQLConnectionProvider.getConnection();
		
		Poll poll = new Poll();
		
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select * from Polls where id = ?");
			pst.setLong(1, pollId);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					if(rs!=null && rs.next()) {
						poll.setId(rs.getLong(1));
						poll.setTitle(rs.getString(2));
						poll.setMessage(rs.getString(3));
				
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata liste korisnika.", ex);
		}
		
		return poll;
	}

	@Override
	public void updateOption(int id) {
		
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		int votes;
		
		try {
			pst = con.prepareStatement("select * from PollOptions where id = ?");
			pst.setLong(1, id);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					if(rs!=null && rs.next()) {
						votes = rs.getInt(5) + 1;
						PreparedStatement p = null;
						p = con.prepareStatement("UPDATE Polloptions set votesCount = ? WHERE id=?");
						p.setInt(1, votes);
						p.setLong(2, id);
						p.executeUpdate();
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata liste korisnika.", ex);
		}
		

	}

}