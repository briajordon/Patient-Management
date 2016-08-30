/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospitalmanagement;

/**
 *
 * @author FUNKATRON
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import static java.util.Collections.list;
import java.util.LinkedList;
import java.util.Scanner;

public class DBConnect {
    private Connection con;
    private Statement st;
    public ResultSet rs;
    private ResultSet rs2;
    public static String FirstName;
    public static String LastName;
    public static String tempEmail;
    public static String tempPassword;
    public static String tempID;
    public static String tempPatientID;
    public static String tempPhoneNumber;
    public static String tempComplaint;
    public static String tempTreatment;
    public static String tempDocID;
    public static String patientFirstNameStr;
    public static String patientLastNameStr;
    public static String patientAddress;
    public static String patientDOB;
    public static String tempDocFName;
    public static String tempDocLName;
    public static String tempDocEmail;
    public static String tempEmpType;
    public static LinkedList patientID = new LinkedList();
    public static LinkedList patientLastName = new LinkedList();
    public static LinkedList patientFirstName = new LinkedList();
 public DBConnect()
 {
     try{
Class.forName("com.mysql.jdbc.Driver");  
con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_management","root","root");  
  
Statement stmt=con.createStatement();  
  
ResultSet rs=stmt.executeQuery("select * from employee");  
  
while(rs.next())  
System.out.println(rs.getString(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
}catch(Exception e){ 
    System.out.println(e);}  
  
}  
  
  public Boolean checkLogin(String email, String password){
           try{
        PreparedStatement pstmt=con.prepareStatement("select * from employee where email_address=? and password=?");
         pstmt.setString(1, email);
         pstmt.setString(2, password);
         rs=pstmt.executeQuery();
         if(rs.next()){
             String tempFN = "select * from employee where email_address='" +  email + "'";
             PreparedStatement ps=con.prepareStatement(tempFN);  
             tempEmail = rs.getString("email_address");
             tempPassword = rs.getString("password");
             tempID = rs.getString("id");
             return true;
         }
         else
             return false;
     }
     catch(Exception ex){
         System.out.println("error while validating "+ex);
         return false;
     }
     
    }
  
  
  
  
  public Boolean checkPatID(String patID)
  {
  try{
        PreparedStatement pstmt=con.prepareStatement("select * from patient where p_id=?");

         pstmt.setString(1, patID);         
         rs=pstmt.executeQuery();
       
         if(rs.next()){
             String tempFN = "select * from patient where p_id='" +  patID + "'";
             PreparedStatement ps=con.prepareStatement(tempFN);
             return true;
         }
         else{
             return false;
         }
         

     }
     catch(Exception ex){
         System.out.println("error while validating"+ex);
         return false;
     }
  }
  
  
  public Boolean checkEmail(String email){
       try{
        PreparedStatement pstmt=con.prepareStatement("select * from employee where email_address=?");

         pstmt.setString(1, email);         
         rs=pstmt.executeQuery();
         tempEmail = email;
         if(rs.next()){
             String tempFN = "select * from employee where email_address='" +  email + "'";
             PreparedStatement ps=con.prepareStatement(tempFN);
             return true;
         }
         else{
             return false;
         }
         

     }
     catch(Exception ex){
         System.out.println("error while validating"+ex);
         return false;
     }
  }
  
  
  public String getID(){
      return tempID;
  }
  
  
  public void setRegistration(String First, String Last, String email, String password, String specialty, String status, String ID){
  try{
      String query1;
      query1 = "INSERT INTO employee(email_address, id, first_name, last_name, password, emp_type)"+" VALUES(?,?,?,?,?,?)";
      PreparedStatement statement = con.prepareStatement(query1);

      statement.setString(1, email);
      statement.setString(2, ID);
      statement.setString(3, First);
      statement.setString(4, Last);
      statement.setString(5, password);
      statement.setString(6, specialty);
      statement.execute();
      tempID = ID;
      
  }catch (Exception ex){
      System.out.println("no");
  }
  }
  
  
  /*public void updatePatientInfo(String tempID3){
      try {
			PreparedStatement statement=con.prepareStatement("select * from patient where p_id=?");
                  
                        statement.setString(1, tempID3);

  }
      catch (Exception ex) {
			System.out.println("This didn't work.");
		}
      
  }*/
  
  public void updatePatientInfo()
  {
      try{
          String updateQuery;
          updateQuery = "UPDATE patient SET address='" + patientAddress + "', phone_num='" + tempPhoneNumber + "', critical_info='" + tempComplaint + "', treatment='" + tempTreatment + "'WHERE p_id ='" + tempPatientID + "'";
          PreparedStatement s = con.prepareStatement(updateQuery);
          s.executeUpdate(updateQuery);
      }
      catch(Exception ex){
          System.out.println("Did not update data.");
      }
      
      //(address, p_id, id, first_name, 
      //last_name, DOB, treatment, critical_info, phone_num
            /* tempPatientID;
    tempPhoneNumber;
    tempComplaint;
    tempTreatment;
    patientAddress;
    patientDOB;*/
  }
  
  
  public void getDoctorInfo(){
  try {
			PreparedStatement statement=con.prepareStatement("select * from employee where id=?");
                  
                        statement.setString(1, tempID);

			rs=statement.executeQuery();
			System.out.println("Records from database when you get the info");
                       
			while (rs.next()){
				
				tempDocEmail= rs.getString("email_address");
                                tempDocFName = rs.getString("first_name");
                                tempDocLName = rs.getString("last_name");
                                tempEmpType = rs.getString("emp_type");				
				
			}
					
			
		} catch (Exception ex) {
			System.out.println("This didn't work.");
		}
  }
  
  
  public void getPatientInfo(String tempID2)
  {
  	try {
			PreparedStatement statement=con.prepareStatement("select * from patient where p_id=?");
                  
                        statement.setString(1, tempID2);

			rs=statement.executeQuery();
			System.out.println("Records from database when you get the info");
                       
			while (rs.next()){
				patientAddress= rs.getString("address");
				tempPatientID= rs.getString("p_id");
                                tempDocID = rs.getString("id");
                                patientFirstNameStr = rs.getString("first_name");
                                patientLastNameStr = rs.getString("last_name");
                                patientDOB = rs.getString("DOB");
                                tempTreatment = rs.getString("treatment");
				tempComplaint = rs.getString("critical_info");
                                tempPhoneNumber = rs.getString("phone_num");				
				
			}
					
			
		} catch (Exception ex) {
			System.out.println("This didn't work.");
		}
      /* tempPatientID;
    tempPhoneNumber;
    tempComplaint;
    tempTreatment;
    patientFirstNameStr;
    patientLastNameStr;
    patientAddress;
    patientDOB;*/
  }
  
  public void dischargePatient(String pid)
  {
      try{
      PreparedStatement statement=con.prepareStatement("DELETE FROM patient WHERE p_id=?");
       
      statement.setString(1, pid);
      
        statement.execute();
      }
      
      catch (Exception ex) {
	System.out.println("This didn't work.");
	}
      
      int index = patientID.indexOf(pid);
      patientID.remove(pid);
      patientFirstName.remove(index);
      patientLastName.remove(index);
  }
  
  public String setTempPatientID(String pid)
  {
      tempPatientID = pid;
      return pid;
  }
  
  public void setPatient(String patID, String firstName, String lastName, String DOB, String address, String phoneNum, String complaint, String treatment, String docID){
 System.out.println("pls work here1");
      try{
      System.out.println("pls work here");

      String query2; 
      query2 = "INSERT INTO patient(address, p_id, id, first_name, last_name, DOB, treatment, critical_info, phone_num)"+" VALUES(?,?,?,?,?,?,?,?,?)";
      PreparedStatement st1 = con.prepareStatement(query2);
            System.out.println("pls work here2");

      st1.setString(1, address);
      st1.setString(2, patID);
      st1.setString(3, docID);
      st1.setString(4, firstName);
      st1.setString(5, lastName);
      st1.setString(6, DOB);
      st1.setString(7, treatment);
      st1.setString(8, complaint);
      st1.setString(9, phoneNum);
      st1.execute(); 
      System.out.println("executed");

     // st1.executeUpdate();
     // con.commit();
  }
  catch (Exception ex){
      System.out.println("no");
  }
  }
  
  public void getPatient(String doctorID)
  {
      doctorID = tempID;
      
      try {
                        
			String query= "SELECT * from patient WHERE id='" + doctorID + "'";
                        Statement smt= con.createStatement();
                        rs=smt.executeQuery(query);
			System.out.println("Records from database");		
			while (rs.next()){
                                String pID = rs.getString("p_id");
                                String firstName = rs.getString("first_name");
                                String lastName  =rs.getString("last_name");
                                patientID.add(pID);
                                patientFirstName.add(firstName);
                                patientLastName.add(lastName);
                                System.out.println(pID + " " + firstName);
                                
                        }
                        rs.last();
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
  public static void main(String args[]){
     DBConnect db = new DBConnect();
     db.checkLogin("kjordon@aol.com","beepbeep");
    }
}
