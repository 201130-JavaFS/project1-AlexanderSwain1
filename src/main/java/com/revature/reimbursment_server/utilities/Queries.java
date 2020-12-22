package com.revature.reimbursment_server.utilities;

public class Queries 
{
	public static final String TRUNCATE_USERS = "TRUNCATE TABLE ERS.USERS CASCADE";
	public static final String GET_USER_BY_ID = "SELECT * FROM ERS.USERS WHERE ID = ?";
	public static final String GET_USER_BY_USERNAME = "SELECT * FROM ERS.USERS WHERE USERNAME = ?";
	public static final String GET_USER_BY_EMAIL = "SELECT * FROM ERS.USERS WHERE EMAIL = ?";
	
	public static final String TRUNCATE_REIMBURSEMENTS = "TRUNCATE TABLE ERS.REIMBURSEMENTS";
	public static final String GET_REIMBURSMENT_BY_ID = "SELECT * FROM ERS.REIMBURSEMENTS WHERE ID = ?";
	public static final String GET_ALL_REIMBURSMENTS = "SELECT * FROM ERS.REIMBURSEMENTS";
	public static final String GET_ALL_REIMBURSMENTS_WITH_AUTHOR = "SELECT * FROM ERS.REIMBURSEMENTS WHERE AUTHOR = ?";
	public static final String GET_ALL_REIMBURSMENTS_WITH_RESOLVER = "SELECT * FROM ERS.REIMBURSEMENTS WHERE RESOLVER = ?";
	public static final String GET_ALL_REIMBURSMENTS_WITH_STATUS = "SELECT * FROM ERS.REIMBURSEMENTS WHERE STATUS_ID = ?";
	public static final String CREATE_REIMBURSMENT = "INSERT INTO ERS.REIMBURSEMENTS(amount, submitted, resolved, description, receipt, author, resolver, status_id, type_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";
	public static final String UPDATE_REIMBURSMENT = "UPDATE ERS.REIMBURSEMENTS set amount = ?, submitted = ?, resolved = ?, description = ?, receipt = ?, author = ?, resolver = ?, status_id = ?, type_id = ? where id = ?;";
}
