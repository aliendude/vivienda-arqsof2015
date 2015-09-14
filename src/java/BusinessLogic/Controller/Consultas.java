package BusinessLogic.Controller;
/*To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor. */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/** @author trossky
 */
public class Consultas  extends Conexion {
    
       public boolean Autenticacion(String user,String pass) throws SQLException
    {
        Statement st = con.createStatement();
        
        ResultSet rs = null;
        
        String Consulta = "Select * from PERSON";
        
        rs = st.executeQuery(Consulta);
        
        while(rs.next())
        {
            if(user.equals(rs.getString("USER")) && pass.equals(rs.getString("PASSWORD")))
                return true;
        }
  
        return false;
    }
       
    
}
