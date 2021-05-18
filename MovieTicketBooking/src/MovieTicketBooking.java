
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
   public class MovieTicketBooking
    {
	   public static String availabilityCheck(Statement statement,String selectedTheaterId,String slotnow,String dateSelected,String screenSelected) throws SQLException {
		   ResultSet availabilityResultSet=statement.executeQuery("SELECT count(*) FROM Seats WHERE Theaterid='"+selectedTheaterId+"' AND Slot='"+slotnow+"' AND Availability IS '"+false+"' AND date='"+dateSelected+"' AND Screen='"+screenSelected+"'");
		   int count=availabilityResultSet.getInt(1);
		   statement.close();
		   if(count>10&&count<=20)
			   	return "Available";
		   else if(count>0&&count<=10)
			   	return "Limited Seats available";
		   return "Not Available";
		   
	   }
    public static void main(String[] args) throws Exception
     	{
    		//remove comments to reinitialize db
    		//Insertor in=new Insertor();
    		Class.forName("org.sqlite.JDBC");
    		Scanner sc=new Scanner(System.in);
    		Connection connection = null;
    		try {
    			connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
    			Statement statement = connection.createStatement();
    			ResultSet theateresult=statement.executeQuery("SELECT * FROM Theaters");
    			System.out.println("Select a theater using its theater id from the below list");
    			while(theateresult.next()) {
    				System.out.println(theateresult.getString(1)+" "+theateresult.getString(2));
    			}
    			String selectedTheaterId=sc.nextLine();
    			String dateSelected="2018-05-17";
    			System.out.println("Select a Screen from below");
    			ResultSet screenresult=statement.executeQuery("Select DISTINCT(Screen) FROM Seats WHERE Theaterid='"+selectedTheaterId+"' AND date='"+dateSelected+"' ");
    			while(screenresult.next()) {
    				System.out.println(screenresult.getString(1));
    			}
    			String screenSelected=sc.nextLine();
    			ResultSet slotResult=statement.executeQuery("SELECT * FROM SLOTS WHERE Theaterid='"+selectedTheaterId+"'");

    			System.out.println("Select a slot from below");
    			while(slotResult.next()) {
    				String slotnow=slotResult.getString(2);
    				String addOn=availabilityCheck(connection.createStatement(),selectedTheaterId,slotnow,dateSelected,screenSelected);
    				
    				System.out.println(slotnow+" ("+addOn+")");
    			}
    			String slotSelected=sc.nextLine();
    			HashSet<Integer>booked=new HashSet<Integer>();
    			ResultSet seatsBooking=statement.executeQuery("SELECT SeatNo FROM Seats WHERE Theaterid='"+selectedTheaterId+"' AND Screen='"+screenSelected+"' AND Slot='"+slotSelected+"' AND Availability IS '"+false+"' AND date='"+dateSelected+"'");
    			System.out.println("Available Seats are");
    			HashSet<Integer>hs=new HashSet<Integer>();
    			while(seatsBooking.next()) {
    				System.out.print(seatsBooking.getInt(1)+" ");
    				hs.add(seatsBooking.getInt(1));
    			}
    			System.out.println("Enter the seat no and enter -1 to exit ");
    			while(true) {
    				int n=sc.nextInt();
    				if(n==-1)
    					break;
    				if(booked.contains(new Integer(n))||!hs.contains(n)||n<1||n>20) { 
    					System.out.println("Enter a valid number");
    					continue;
    				}
    				booked.add(n);
    				statement.executeUpdate("UPDATE Seats SET Availability = '"+true+"'  WHERE Theaterid='"+selectedTheaterId+"' AND Screen='"+screenSelected+"' AND Slot='"+slotSelected+"' AND SeatNo='"+n+"' ");
    			}
    			if(booked.size()>0) {
    			System.out.print("Thanks for booking ");
    			for(int i:booked) {
    				System.out.print(i+",");
    			}
    			}
    		}
    		catch(SQLException e){  System.err.println(e.getMessage()); }       
    		  finally {         
    		      try {
    		            if(connection != null)
    		               connection.close();
    		            }
    		      catch(SQLException e) {  // Use SQLException class instead.          
    		         System.err.println(e); 
    		       }
    		}
     	}


	}


