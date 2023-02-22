package com.shopcarousel.model;

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

public class ShopCarouselJDBCDAO implements ShopCarouselDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/mumu_new");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

	}

	// ==============

	private static final String INSERT = "INSERT INTO `shop_carousel` (`SHOP_CAROUSEL_ID`, `SHOP_CAROUSEL_POSITION`, `PRODUCT_TYPE_ID`, `SHOP_CAROUSEL_PICTURE`, `SHOP_CAROUSEL_URL`) VALUES ( ? , ? , ? , ? , ? )";

	@Override
	public void insert(ShopCarouselVO shopCarouselVO) {

		PreparedStatement pstmt = null;
		try {

			pstmt = ds.getConnection().prepareStatement(INSERT);
			pstmt.setInt(1, shopCarouselVO.getShopCarouselId());
			pstmt.setInt(2, shopCarouselVO.getShopCarouselPosition());
			pstmt.setInt(3, shopCarouselVO.getProdTypeId());
			pstmt.setBinaryStream(4, shopCarouselVO.getShopCarouselPic());
			pstmt.setString(5, shopCarouselVO.getShopCarouselUrl());
			pstmt.executeUpdate();


		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	// ==============

	private static final String DELETE = "DELETE FROM `shop_carousel` WHERE SHOP_CAROUSEL_ID = ?";

	@Override
	public void delete(Integer shopCarouselId) {

		PreparedStatement pstmt = null;

		try {

			pstmt = ds.getConnection().prepareStatement(DELETE);

//			pstmt.setInt(1, prodTypeVO.getProdTypeId());
			pstmt.setInt(1, shopCarouselId);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	// ==============

	private static final String UPDATE = "UPDATE `shop_carousel` SET `SHOP_CAROUSEL_POSITION` = ? , `PRODUCT_TYPE_ID` = ? , `SHOP_CAROUSEL_PICTURE` = ? , `SHOP_CAROUSEL_URL` = ?  WHERE `SHOP_CAROUSEL_ID` = ? ";

	@Override
	public void update(ShopCarouselVO shopCarouselVO) {
		PreparedStatement pstmt = null;

		try {
			pstmt = ds.getConnection().prepareStatement(UPDATE);

			pstmt.setInt(1, shopCarouselVO.getShopCarouselPosition());
			pstmt.setInt(2, shopCarouselVO.getProdTypeId());
			pstmt.setBinaryStream(3, shopCarouselVO.getShopCarouselPic());
			pstmt.setString(4, shopCarouselVO.getShopCarouselUrl());
			pstmt.setInt(5, shopCarouselVO.getProdTypeId());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	// ==============

	private static final String GET_ALL = "SELECT `SHOP_CAROUSEL_ID`, `SHOP_CAROUSEL_POSITION`, `PRODUCT_TYPE_ID`, `SHOP_CAROUSEL_PICTURE`, `SHOP_CAROUSEL_URL` FROM `shop_carousel` order by `SHOP_CAROUSEL_ID` ";

	@Override
	public List<ShopCarouselVO> getAll() {

		List<ShopCarouselVO> list = new ArrayList<ShopCarouselVO>();
		ShopCarouselVO shopCarouselVO = null;
		ResultSet rs = null;

		try {

			rs = ds.getConnection().prepareStatement(GET_ALL).executeQuery();

			while (rs.next()) {
				shopCarouselVO = new ShopCarouselVO();
				shopCarouselVO.setShopCarouselId(rs.getInt("SHOP_CAROUSEL_ID"));
				shopCarouselVO.setShopCarouselPosition(rs.getInt("SHOP_CAROUSEL_POSITION"));
				shopCarouselVO.setProdTypeId(rs.getInt("PRODUCT_TYPE_ID"));
				shopCarouselVO.setShopCarouselPic(null);
				shopCarouselVO.setShopCarouselUrl(rs.getString("SHOP_CAROUSEL_URL"));
				list.add(shopCarouselVO);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}

		return list;
	}

	// ==============

	private static final String GET_ONE = "SELECT `SHOP_CAROUSEL_ID`, `SHOP_CAROUSEL_POSITION`, `PRODUCT_TYPE_ID`, `SHOP_CAROUSEL_PICTURE`, `SHOP_CAROUSEL_URL` FROM `shop_carousel` WHERE SHOP_CAROUSEL_ID = ?";

	@Override
	public ShopCarouselVO findByPrimaryKey(Integer SHOP_CAROUSEL_ID) {

		ShopCarouselVO shopCarouselVO = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			pstmt =ds.getConnection().prepareStatement(GET_ONE);

			pstmt.setInt(1, SHOP_CAROUSEL_ID);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				shopCarouselVO = new ShopCarouselVO();
				shopCarouselVO.setShopCarouselId(rs.getInt("SHOP_CAROUSEL_ID"));
				shopCarouselVO.setShopCarouselPosition(rs.getInt("SHOP_CAROUSEL_POSITION"));
				shopCarouselVO.setProdTypeId(rs.getInt("PRODUCT_TYPE_ID"));
				shopCarouselVO.setShopCarouselPic(null);
				shopCarouselVO.setShopCarouselUrl(rs.getString("SHOP_CAROUSEL_URL"));
			}

		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
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
		}

		return shopCarouselVO;
	}

}
