/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xs.project.services;

import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import xs.project.brendan.service.Album;

/**
 * REST Web Service
 *
 * @author admin
 */
@Path("BrendanService")
public class BrendanService {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of BrendanService
     */
    public BrendanService() {
    }

    /**
     * Retrieves representation of an instance of xs.project.services.BrendanService
     * @param upc
     * @return an instance of java.lang.String
     */
    @Path("/{upc}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Album getAlbumData(@PathParam("upc") String upc) {
        xs.project.brendan.service.AlbumService service = new xs.project.brendan.service.AlbumService();
        xs.project.brendan.service.AlbumServiceSoap port = service.getAlbumServiceSoap12();
        return port.getAlbumData(upc);
    }

    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String instructions() {
        xs.project.brendan.service.AlbumService service = new xs.project.brendan.service.AlbumService();
        xs.project.brendan.service.AlbumServiceSoap port = service.getAlbumServiceSoap12();
        return port.instructions();
    }
    
}