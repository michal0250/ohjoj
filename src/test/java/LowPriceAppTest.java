//package com.example.lowpricesapp;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;
//
//import java.sql.Connection;
//
//import org.h2.jdbc.JdbcSQLException;
//import org.junit.Test;
//
//public class LowPriceAppTest {
//	@Test
//	public void shouldSaveRow() throws Exception {
//		// given
//		DBConnection dbConnection = new DBConnection();
//		Connection connection = dbConnection.getConnection();
//
//		CreateDBDAO createDBDAO = new CreateDBDAO(connection);
//		CreateDBService createDBService = new CreateDBService();
//		createDBService.setCreateDBDAO(createDBDAO);
//		createDBService.createNewDB();
//
//		ProductDAO productDAO = new ProductDAO();
//		productDAO.setConnection(connection);
//		ProductService saveService = new ProductService();
//		saveService.setProductDAO(productDAO);
//
//		// when
//		String result = saveService
//				.createNewProduct("www.sportdirect.com test1");
//
//		// then
//		assertEquals("dodano wpis do bazy danych", result);
//	}
//
//	@Test
//	public void shouldNotSaveRow() throws Exception {
//		// given
//		DBConnection dbConnection = new DBConnection();
//		Connection connection = dbConnection.getConnection();
//
//		CreateDBDAO createDBDAO = new CreateDBDAO(connection);
//		CreateDBService createDBService = new CreateDBService();
//		createDBService.setCreateDBDAO(createDBDAO);
//		createDBService.createNewDB();
//
//		ProductDAO productDAO = new ProductDAO();
//		productDAO.setConnection(connection);
//		ProductService saveService = new ProductService();
//		saveService.setProductDAO(productDAO);
//
//		// when
//		try {
//			saveService
//					.createNewProduct("www.sportdirect.com test1www.sportdirect.com test1www.sportdirect.com test1www.sportdirect.com test1www.sportdirect.com test1www.sportdirect.com test1www.sportdirect.com test1www.sportdirect.com test1www.sportdirect.com test1www.sportdirect.com test1www.sportdirect.com test1www.sportdirect.com test1www.sportdirect.com test1www.sportdirect.com test1www.sportdirect.com test1www.sportdirect.com test1www.sportdirect.com test1www.sportdirect.com test1www.sportdirect.com test1www.sportdirect.com test1www.sportdirect.com test1www.sportdirect.com test1www.sportdirect.com test1www.sportdirect.com test1www.sportdirect.com test1www.sportdirect.com test1www.sportdirect.com test1www.sportdirect.com test1www.sportdirect.com test1www.sportdirect.com test1www.sportdirect.com test1www.sportdirect.com test1www.sportdirect.com test1www.sportdirect.com test1");
//			// then
//			fail("Should throw SQL Exception");
//		} catch (JdbcSQLException e) {
//			assertTrue(true);
//		}
//	}
// }
