import java.sql.*;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;


public class PersistLicense {
   
    private String licence;
    private String userName;
    private String password;


    public PersistLicense(String licence, String userName, String password) {
        this.licence = licence;
        this.userName = userName;
        this.password = password;
    }

    public String updateDatabase(){

        try{  
            Class.forName("com.mysql.jdbc.Driver"); 
            Connection conn=DriverManager.getConnection("jdbc:mysql://pos-svr:3306/posdb?characterEncoding=latin1&useConfigs=maxPerformance&allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",userName,password);  
        
            LocalDateTime localDateTime = LocalDateTime.now();
           
            String query = "update license set license = ?, date_updated = ? where id = ?";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
           
            preparedStmt.setString(1, licence);
            preparedStmt.setString(2, localDateTime.toString());
            preparedStmt.setInt  (3, 1);
      
            // execute the java preparedstatement
            preparedStmt.executeUpdate();
          
        }
        catch(Exception ex){
			return ex.toString();
		}
        return "License updated!!!";
    }
}
