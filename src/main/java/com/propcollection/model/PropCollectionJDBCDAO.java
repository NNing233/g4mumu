package com.propcollection.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class PropCollectionJDBCDAO implements PropCollectionDAO_interface {
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/mumu_new");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = 
		"INSERT INTO proposal_collection (MEMBER_ID, PROPOSAL_ID) VALUES (?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT MEMBER_ID, PROPOSAL_ID FROM proposal_collection order by MEMBER_ID";
	private static final String GET_ONE_STMT = 
		"SELECT MEMBER_ID, PROPOSAL_ID FROM proposal_collection where MEMBER_ID = ? and PROPOSAL_ID = ?";
	private static final String DELETE = 
		"DELETE FROM proposal_collection where MEMBER_ID = ? and PROPOSAL_ID = ?";
	private static final String GET_PROP_COLLECTION_BY_MEM = 
			"SELECT * FROM proposal_collection WHERE MEMBER_ID=?";

	@Override
	public void insert(PropCollectionVO propCollectionVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, propCollectionVO.getMemberID());
			pstmt.setInt(2, propCollectionVO.getProposalID());

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void delete(Integer memberID, Integer proposalID) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, memberID);
			pstmt.setInt(2, proposalID);

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public PropCollectionVO findByPrimaryKey(Integer memberID, Integer proposalID) {

		PropCollectionVO propCollectionVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, memberID);
			pstmt.setInt(2, proposalID);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱?�� Domain objects
				propCollectionVO = new PropCollectionVO();
				propCollectionVO.setMemberID(rs.getInt("MEMBER_ID"));
				propCollectionVO.setProposalID(rs.getInt("PROPOSAL_ID"));
			}

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return propCollectionVO;
	}

	@Override
	public List<PropCollectionVO> getAll() {
		List<PropCollectionVO> list = new ArrayList<PropCollectionVO>();
		PropCollectionVO propCollectionVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱?�� Domain objects
				propCollectionVO = new PropCollectionVO();
				propCollectionVO.setMemberID(rs.getInt("MEMBER_ID"));
				propCollectionVO.setProposalID(rs.getInt("PROPOSAL_ID"));
				list.add(propCollectionVO); // Store the row in the list
			}

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
	
	@Override
	public List<PropCollectionVO> getPropCollectionByMem(Integer memberId) {
		List<PropCollectionVO> list = new ArrayList<PropCollectionVO>();
		PropCollectionVO propCollectionVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_PROP_COLLECTION_BY_MEM);
			pstmt.setInt(1, memberId);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				// empVO 也稱?�� Domain objects
				propCollectionVO = new PropCollectionVO();
				propCollectionVO.setMemberID(rs.getInt("MEMBER_ID"));
				propCollectionVO.setProposalID(rs.getInt("PROPOSAL_ID"));
				list.add(propCollectionVO); // Store the row in the list
			}
			
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
	
	public static void main(String[] args) {

		PropCollectionJDBCDAO dao = new PropCollectionJDBCDAO();


//		PropCollectionVO propCollectionVO1 = new PropCollectionVO();
//		propCollectionVO1.setMemberID(1);
//		propCollectionVO1.setProposalID(2);
//		dao.insert(propCollectionVO1);


//		dao.delete(1, 1);
//

//		PropCollectionVO propCollectionVO3 = dao.findByPrimaryKey(1,1);
//		System.out.print(propCollectionVO3.getMemberID() + ",");
//		System.out.println(propCollectionVO3.getProposalID());
//		System.out.println("---------------------");


		List<PropCollectionVO> list = dao.getAll();
		for (PropCollectionVO aPropCollection : list) {
			System.out.print(aPropCollection.getMemberID() + ",");
			System.out.print(aPropCollection.getProposalID());
			System.out.println();
		}
	}

}
