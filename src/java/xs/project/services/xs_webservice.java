/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xs.project.services;

import xs.project.exceptions.Exception404;
import java.sql.SQLException;
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

import xs.project.exceptions.Exception500;

/**
 * REST Web Service for (CRUD) access to the DB
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

    /**
     *
     * @return List of Album objects with all active albums
     * @throws SQLException
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Album> getAlbums() throws SQLException {
        
       AlbumsDAO ad = new AlbumsDAO();
        
       List<Album> albums = ad.getAlbums();
       
       return albums;
    }
    
    /**
     * Retrieves representation of an instance of xs.project.service.xs_webservice
     * @param i - user Id from DB
     * @param active - true/false active parameter
     * 
     * @return a List of Album objects with all active or inactive albums of a user
     * @throws java.sql.SQLException
     * @throws xs.project.exceptions.Exception404
     */
    @Path("/{user_id}/{active}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Album> getAlbumsByUserId(@PathParam("user_id") int i, @PathParam("active") boolean active) throws SQLException, Exception404 {
        
        AlbumsDAO ad = new AlbumsDAO();
        
        List<Album> albums = ad.getAlbumsByUserId(i, active);
        
        return albums;
    }
    
    /**
     *
     * @param s - upc parameter
     * @return a List of Album objects with all active albums selected by upc
     * @throws SQLException
     * 
     *  -------- GET BY UPC ------------
     * 
     */
    @Path("/{upc}")
    @GET  
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Album> getAlbumByUpc(@PathParam("upc") String s) throws SQLException, Exception {
        
        AlbumsDAO ad = new AlbumsDAO();
        
        List<Album> albums = ad.getAlbumByUpc(s);
        
        return albums;
    }
    
    /**
     *
     * @param upc
     * @param user_id
     * @return a List of Album objects with all active albums OF A USER selected by UPC
     * @throws SQLException
     * @throws Exception404
     */
    @Path("/upc/{upc}/{user_id}")
    @GET  
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Album> getAlbumByUpcAndUserId(@PathParam("upc") String upc, @PathParam("user_id") String user_id) throws SQLException, Exception404 {
        
        AlbumsDAO ad = new AlbumsDAO();
        
        List<Album> albums = ad.getAlbumByUpcAndUserId(upc , user_id);
        
        return albums;
    }
    
    /**
     *
     * @param i - user id parameter
     * @param b - active parameter
     * @return count of active/inactive albums of a user
     * @throws Exception404
     * @throws Exception500
     */
    @Path("/count/{user_id}/{active}")
    @GET
    @Consumes((MediaType.TEXT_PLAIN))
    @Produces(MediaType.TEXT_PLAIN)
    public int count(@PathParam("user_id") int i, @PathParam("active") boolean b) throws Exception404, Exception500 {
        AlbumsDAO ad = new AlbumsDAO();
        
        int count = ad.countAlbumsByUserIdChoiceActive(i, b);
        return count;
    }
    
    /**
     *
     * @param entry 
     * @return Album object of an album selected by entry
     * @throws SQLException
     * @throws Exception404
     * @throws Exception500
     */
    @Path("/entry/{entry}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Album getAlbumByEntry(@PathParam("entry") String entry) throws SQLException, Exception404, Exception500 {
        AlbumsDAO ad = new AlbumsDAO();
        return ad.getAlbumByEntry(entry);
    }

    /**
     *
     * @param email
     * @return User object of a user selected by email
     * @throws Exception404
     */
    @Path("/login/{email}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public User getUserByEmail(@PathParam("email") String email) throws Exception404 {
        
        UserDAO ud = new UserDAO();
        
        User user = ud.getUserByEmail(email);
        
        return user;
    }
    
    /**
     *
     * @param id
     * @return User object selected by Id
     * @throws Exception404
     */
    @Path("/userId/{userId}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public User getUserById(@PathParam("userId") int id) throws Exception404 {
        
        UserDAO ud = new UserDAO();
        
        User user = ud.getUserById(id);
        
        return user;
    }
    
//    
    /**
     * POST method for updating or creating an instance of xs_webservice
     * @param a Album object that keeps data for creating an instance in DB
     * @return created album object type Object
     * @throws java.sql.SQLException
     * @throws xs.project.exceptions.Exception404
     */
    @Path("/create")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Album putAlbum(Album a) throws SQLException, Exception404 {
        
       AlbumsDAO ad = new AlbumsDAO();
       
       Album newAlbum = ad.createAlbum(a);
       
       return newAlbum;
    }
    
    /**
     *
     * 
     * @param a Album object that keeps data for editing an instance in DB
     * @return edited album object type Object
     * @throws SQLException
     * @throws Exception404
     * @throws Exception500
     */
    @Path("/edit")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Album editAlbum(Album a) throws SQLException, Exception404, Exception500 {
        
       AlbumsDAO ad = new AlbumsDAO();
       
       Album editedAlbum = ad.editAlbum(a);
       
       return editedAlbum;
    }
    
    /**
     *
     * @param entry
     * @return Album object of disactivated album
     * @throws SQLException
     * @throws Exception404
     * @throws Exception500
     */
    @Path("/deleteEntry")
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Album deleteAlbum(String entry) throws SQLException, Exception404, Exception500 {
        
        AlbumsDAO ad = new AlbumsDAO();

        Album deletedAlbum = ad.deleteAlbum(entry);

        return deletedAlbum;
    }
    
    /**
     *
     * @param entry
     * @return Album object of reactivated album
     * @throws SQLException
     * @throws Exception404
     * @throws Exception500
     */
    @Path("/restoreEntry")
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Album restoreAlbum(String entry) throws SQLException, Exception404, Exception500 {
        
        AlbumsDAO ad = new AlbumsDAO();

        Album restoredAlbum = ad.restoreAlbum(entry);

        return restoredAlbum;
    }
    
    /**
     *
     * @param u - @param a User object that keeps data for creating an instance in DB
     * @return created user object type Object
     * 
     * @throws SQLException
     * @throws Exception500
     * @throws Exception404
     */
    @Path("/signup")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User createUser(User u) throws SQLException, Exception500, Exception404 {
        
        UserDAO ud = new UserDAO();
        User newUser = ud.createUser(u);
        
        return newUser;
    }
    
}
