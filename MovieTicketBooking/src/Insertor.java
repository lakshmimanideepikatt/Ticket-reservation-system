import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Insertor {
	Insertor() throws Exception{
		startInsertion();
	}
	void startInsertion() throws Exception{
	Class.forName("org.sqlite.JDBC");

    Connection connection = null;
    try {
		connection = DriverManager.getConnection("jdbc:sqlite:sample.db");

      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);
      statement.executeUpdate("DROP TABLE IF EXISTS Theaters");
      statement.executeUpdate("CREATE TABLE Theaters (Theatername STRING (25), Theaterid   STRING (7)  PRIMARY KEY, City STRING (15) NOT NULL)");
      int ids [] = {101,102,103,104,105};
      String names [] = {"Sathyam Cinemas","Udhayam Cinemas","Kasi theatre","Vetri theatres","Inox "};
      String city="chennai";
      for(int i=0;i<ids.length;i++){
           statement.executeUpdate("INSERT INTO Theaters values(' "+names[i]+"', '"+ids[i]+"', '"+city+"')");   
      }
      statement.executeUpdate("DROP TABLE IF EXISTS Slots");
      statement.executeUpdate("CREATE TABLE Slots (Theaterid STRING (7) REFERENCES Theaters (Theaterid),Slot STRING(8))");
      String time[]= {"10:30 AM","2:00 PM","6:30 PM","10:30 PM"};
      for(int i=0;i<ids.length;i++) {
      	for(int j=0;j<time.length;j++) {
          statement.executeUpdate("INSERT INTO Slots values(' "+ids[i]+"', '"+time[j]+"')");
      	}
      }
     // ResultSet slotresult=statement.executeQuery("SELECT * FROM Slots");

      statement.executeUpdate("DROP TABLE IF EXISTS Seats");
      statement.executeUpdate("CREATE TABLE Seats ( Theaterid STRING (7) REFERENCES Theaters (Theaterid), date STRING(10),Slot STRING (8)  NOT NULL, Screen CHAR NOT NULL, movie STRING (25),SeatNo INTEGER, Availability BOOLEAN)");
      String screens[]= {"A","B"};
      for(int i=0;i<ids.length;i++) {
      	for(int j=0;j<time.length;j++) {
      		for(int k=0;k<screens.length;k++) {
      			for(int seatcount=1;seatcount<=20;seatcount++) {
      	            statement.executeUpdate("INSERT INTO Seats values(' "+ids[i]+"', '"+"2018-05-17"+"', '"+time[j]+"', '"+screens[k]+"', '"+"movierandom"+"', '"+seatcount+"', '"+false+"')");   

      			}
      		}
      	}
      }
      System.out.println("completed insertion");
     
      
     
	}catch(SQLException e){  System.err.println(e.getMessage()); }       
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


