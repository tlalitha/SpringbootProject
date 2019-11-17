package com.example.customerAccount.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.customerAccount.dataModel.CustomerAccount;

@Repository
public class CustomerService {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<CustomerAccount> getCustomerDetailsByAccId(long accountId) {

		String sql = "select c.idcustomer,a.AccountNumber,c.forename,c.surname,c.dob,a.idAccount from account a "
				+ "left outer join customeraccount ca on ca.idAccount=a.idAccount "
				+ "left outer join customer c on c.idcustomer=ca.idcustomer " + "where a.idAccount=?";

		List<CustomerAccount> list = jdbcTemplate.query(sql, new Object[] { accountId },
				new RowMapper<CustomerAccount>() {

					@Override
					public CustomerAccount mapRow(ResultSet rs, int rowNum) throws SQLException {
						CustomerAccount customerDetails = new CustomerAccount();
						customerDetails.setCusId(rs.getInt(1));
						customerDetails.setAccNum(rs.getLong(2));
						customerDetails.setfName(rs.getString(3));
						customerDetails.setsName(rs.getString(4));
						customerDetails.setDob(rs.getDate(5));
						customerDetails.setAccId(rs.getInt(6));
						return customerDetails;
					}
				});
		return list;

	}

	public List<CustomerAccount> getAccountDetailsByCusId(long cusId) {

		String sql = "select a.idAccount,a.AccountNumber,c.forename,c.surname,c.dob,c.idcustomer from customer c "
				+ "left outer join customeraccount ca on ca.idcustomer=c.idcustomer "
				+ "left outer join account a on a.idAccount=ca.idAccount " + "where c.idcustomer=?";

		List<CustomerAccount> list = jdbcTemplate.query(sql, new Object[] { cusId }, new RowMapper<CustomerAccount>() {

			@Override
			public CustomerAccount mapRow(ResultSet rs, int rowNum) throws SQLException {
				CustomerAccount customerDetails = new CustomerAccount();
				customerDetails.setAccId(rs.getInt(1));
				customerDetails.setAccNum(rs.getLong(2));
				customerDetails.setfName(rs.getString(3));
				customerDetails.setsName(rs.getString(4));
				customerDetails.setDob(rs.getDate(5));
				customerDetails.setCusId(rs.getInt(6));
				return customerDetails;
			}
		});
		return list;

	}
	
	public void createNewCustomer(CustomerAccount customerAccount) {

		String csql = "insert into customer(forename,surname,dob) values (?,?,?) ";

		jdbcTemplate.update(csql, new Object[] { customerAccount.getfName(),customerAccount.getsName(),customerAccount.getDob() });
		
		int idCustomer=jdbcTemplate.queryForObject("select idCustomer from customer where forename=? and surname=? and dob=? ", new Object[] { customerAccount.getfName(),customerAccount.getsName(),customerAccount.getDob()},Integer.class);
		
		String asql = "insert into account(AccountNumber) values (?) ";
		
		 Random random = new Random();
	     int accNum=random.nextInt(99999999);

		jdbcTemplate.update(asql, new Object[] { accNum });
		
		int idAccount=jdbcTemplate.queryForObject("select idAccount from account where AccountNumber=? ", new Object[] {accNum},Integer.class);
		
		String casql = "insert into customeraccount(idcustomer,idAccount) values (?,?) ";

		jdbcTemplate.update(casql, new Object[] { idCustomer, idAccount});
		
		int idcustomerAccount=jdbcTemplate.queryForObject("select idcustomeraccount from customeraccount where idcustomer=? and idAccount=? ", new Object[] { idCustomer,idAccount},Integer.class);
		
		jdbcTemplate.update("update customer set idcustomerAccount=? where idcustomer=? ", new Object[] { idcustomerAccount, idCustomer});
		
	}
	
	public String validateCustomer(CustomerAccount customerAccount) {

		String sql = "select c.idcustomer from customer c "
				+ "left outer join customeraccount ca on ca.idcustomer=c.idcustomer "
				+ "left outer join account a on a.idAccount=ca.idAccount " + " where  c.forename=? and c.surname=? and c.dob=? and a.AccountNumber=?  ";
		int idCustomer=0;
		try {
			idCustomer =jdbcTemplate.queryForObject(sql, new Object[] { customerAccount.getfName(),customerAccount.getsName(),customerAccount.getDob(),customerAccount.getAccNum() },Integer.class);
		}catch(Exception e) {
			return "customer details are invalid";
		}
		if(idCustomer>0) {
			return "valid customer";
		}
		return "";
	
		
	}
	
}
