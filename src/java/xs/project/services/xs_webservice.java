/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xs.project.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import xs.project.dao.AlbumsDAO;
import xs.project.dao.UserDAO;
import xs.project.entities.Album;
import xs.project.entities.User;

/**
 * REST Web Service
 *
 * @author admin
 */

@Path("albums")
public class xs_webservice {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Albums
     * @throws java.lang.ClassNotFoundException
     */
    public xs_webservice() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
    }

    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Album> getAlbums() throws SQLException {
        
       AlbumsDAO ad = new AlbumsDAO();
        
       List<Album> albums = ad.getAlbums();
       
       return albums;
    }
    
    /**
     * Retrieves representation of an instance of xs.project.service.xs_webservice
     * @param i
     * 
     * @return an instance of java.lang.String
     * @throws java.sql.SQLException
     */
    @Path("/{user_id}/{active}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Album> getAlbumsByUserId(@PathParam("user_id") int i, @PathParam("active") boolean active) throws SQLException {
        
        AlbumsDAO ad = new AlbumsDAO();
        
        List<Album> albums = ad.getAlbumsByUserId(i, active);
        
        return albums;
    }
    
    /**
     *
     * @param s
     * @return
     * @throws SQLException
     * 
     *  -------- GET BY UPC ------------
     * 
     */
    @Path("/{upc}")
    @GET  
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Album> getAlbumByUpc(@PathParam("upc") String s) throws SQLException {
        
        AlbumsDAO ad = new AlbumsDAO();
        
        List<Album> albums = ad.getAlbumByUpc(s);
        
        return albums;
    }
    
    @Path("/upc/{upc}/{user_id}")
    @GET  
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Album> getAlbumByUpcAndUserId(@PathParam("upc") String upc, @PathParam("user_id") String user_id) throws SQLException {
        
        AlbumsDAO ad = new AlbumsDAO();
        
        List<Album> albums = ad.getAlbumByUpcAndUserId(upc , user_id);
        
        return albums;
    }
    
    @Path("/count/{user_id}/{active}")
    @GET
    @Consumes((MediaType.TEXT_PLAIN))
    @Produces(MediaType.TEXT_PLAIN)
    public int count(@PathParam("user_id") int i, @PathParam("active") boolean b) {
        AlbumsDAO ad = new AlbumsDAO();
        
        int count = ad.countAlbumsByUserIdChoiceActive(i, b);
        return count;
    }
    
    @Path("/entry/{entry}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Album getAlbumByEntry(@PathParam("entry") String entry) throws SQLException {
        AlbumsDAO ad = new AlbumsDAO();
        return ad.getAlbumByEntry(entry);
    }

    @Path("/login/{email}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public User getUserByEmail(@PathParam("email") String email) {
        
        UserDAO ud = new UserDAO();
        
        User user = ud.getUserByEmail(email);
        
        return user;
    }
    
    
    @Path("/userId/{userId}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public User getUserById(@PathParam("userId") int id) {
        
        UserDAO ud = new UserDAO();
        
        User user = ud.getUserById(id);
        
        return user;
    }
    
    /**
     * PUT method for updating or creating an instance of xs_webservice
     * @param a
     * @return 
     * @throws java.sql.SQLException
     */
    @Path("/create")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Album putAlbum(Album a) throws SQLException {
        
       AlbumsDAO ad = new AlbumsDAO();
       
       Album newAlbum = ad.createAlbum(a);
       
       return newAlbum;
    }
    
    @Path("/edit")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Album editAlbum(Album a) throws SQLException {
        
       AlbumsDAO ad = new AlbumsDAO();
       
       Album editedAlbum = ad.editAlbum(a);
       
       return editedAlbum;
    }
    
    
    @Path("/deleteEntry")
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Album deleteAlbum(String entry) throws SQLException {
        
        AlbumsDAO ad = new AlbumsDAO();

        Album deletedAlbum = ad.deleteAlbum(entry);

        return deletedAlbum;
    }
    
    @Path("/restoreEntry")
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Album restoreAlbum(String entry) throws SQLException {
        
        AlbumsDAO ad = new AlbumsDAO();

        Album restoredAlbum = ad.restoreAlbum(entry);

        return restoredAlbum;
    }
    
    @Path("/signup")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User createUser(User u) throws SQLException {
        
        UserDAO ud = new UserDAO();
        User newUser = ud.createUser(u);
        
        return newUser;
    }
    
}
