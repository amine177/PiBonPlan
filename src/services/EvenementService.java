/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, coose Tools | Templates
 * and open the template in the editor.
 */
package services;

import Util.DataSource;
import entites.Evenements;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sboula
 */
public class EvenementService implements IService<Evenements> {
	private Connection con = DataSource.getInstance().getCon();
                PreparedStatement pre;

 
    @Override
    public void ajouter(Evenements t) 
    {
        String req="INSERT INTO evenements (nom,description,lieu,date,dateF,adresse,tel,rating,prix,brochure,nbPlace,type) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)" ; 
		try {
			pre = con.prepareStatement(req);
                        pre.setString(1, t.getNom());
                        pre.setString(2, t.getDescription());
			pre.setString(3, t.getLieu());
			pre.setDate(4, (Date) t.getDate());
			pre.setDate(5, (Date) t.getDateF());
			pre.setString(6,t.getAdresse());
			pre.setString(7, t.getTel());
			pre.setInt(8, t.getRating());
			pre.setFloat(9, t.getPrix());
			pre.setString(10, t.getBrochure());
			pre.setInt(11, t.getNbPlace());
                        pre.setString(12,t.getType());
                        
			
			pre.executeUpdate();
			
		
		
		} catch (SQLException ex) {
		}
		
    }

    @Override
    public void supprimer(int id) {
         String req="DELETE  from evenements where  id =?" ; 
        try { 
            PreparedStatement ste = pre.getConnection().prepareStatement(req) ;
             
            
            ste.setInt(1,id) ;
            ste.executeUpdate() ; 
            
        } catch (SQLException ex) {
        }

    }

    @Override
    public void modifier(int id, Evenements t) {
                        int ret = -1;
			String req = "UPDATE evenements SET"
				+ " nom = ?, description = ?,"
				+ " lieu= ?, date = ? ,"
				+ " dateF = ?, adresse = ?,"
				+ " tel = ?, rating = ?,"
                                +"prix = ?,brochure= ?,nbPlace=?"
                                +"type=? "
				+ " WHERE id = ?" ;   
                        try {
			pre = con.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
			pre.setString(1, t.getNom());
			pre.setString(2, t.getDescription());
			pre.setString(3, t.getLieu());
			pre.setDate(4, (Date) t.getDate());
                        pre.setDate(5, (Date) t.getDateF());
			pre.setString(6,t.getAdresse());
			pre.setString(7, t.getTel());
                        pre.setInt(8, t.getRating());
			pre.setFloat(9, t.getPrix());
                        pre.setString(10, t.getBrochure());
                        pre.setInt(11, t.getNbPlace());
                        pre.setString(12, t.getType());
                        
			
			pre.executeUpdate();
			ResultSet ks = pre.getGeneratedKeys();
			if (ks.next())
			ret = ks.getInt(1);
		
		
		} catch (SQLException ex) {
			Logger.getLogger(EvenementService.class.getName()).log(Level.SEVERE, null, ex);
		}
                        //No Return here 
    }

    @Override
    public ArrayList<Evenements> selectAll()  {

       
        ArrayList<Evenements> eve = new ArrayList<>();
        
            try {
            
                        
                       String req = "SELECT * FROM evenements";
			PreparedStatement pre;
			pre = con.prepareStatement(req);
		
			ResultSet rs = pre.executeQuery();
		
			

                  while (rs.next())
                  {
                      Evenements e = new Evenements();
                       e.setId(rs.getInt(1));
                        e.setDescription(rs.getString(2));
			e.setLieu(rs.getString(3));
			e.setDate(rs.getDate(4));
                        e.setDateF(rs.getDate(5));
		        e.setAdresse(rs.getString(7));
			e.setTel(rs.getString(8));
                        e.setRating(rs.getInt(9));
			e.setPrix((int) rs.getFloat(10));
                        e.setBrochure(rs.getString(11));
                        e.setNbPlace(rs.getInt(12));
                        e.setType(rs.getString(13));


                      eve.add(e);
                                
                        
                        
                        
                        
                        
                        
                  }
            } catch (SQLException ex) {
                Logger.getLogger(EvenementService.class.getName()).log(Level.SEVERE, null, ex);
            }
	                  return eve;

                 }

    @Override
    public Evenements selectOne(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean existId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int lastIdAdded() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    
}
