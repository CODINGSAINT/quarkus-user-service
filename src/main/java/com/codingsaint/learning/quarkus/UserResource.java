package com.codingsaint.learning.quarkus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/users")
public class UserResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);

    /**
     * Get all of the users
     *
     * @return Response<List < User>> users
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response users() {
        return Response.ok(User.listAll()).build();
    }

    /**
     * Create the user
     *
     * @param user
     * @return
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response users(final @Valid User user) {
        user.setUserId(UUID.randomUUID().toString());
        user.setActive(true);
        User.persist(user);
        return Response.ok().build();
    }

    /**
     * Update the user
     *
     * @param user
     * @return
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(final @Valid User user) {
        user.update();
        return Response.ok(user).build();
    }

    /**
     * retrieve user based on userId
     *
     * @param userId
     * @return Response<User>
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("id/{id}")
    public Response getUserByUserId(@PathParam("id") String userId) {
        User user = User.findByUserId(userId);
        LOGGER.info(" finding user based on userId {}", userId);
        return Response.ok(user).build();
    }

    /**
     * delete user based on userId
     * @param userId
     * @return
     */
    @DELETE
    @Path("id/{id}")
    public Response deleteUser(@PathParam("id") String userId) {
        LOGGER.info(" delete user based on userId {}", userId);
        User.delete("userId",userId);
        return Response.noContent().build();
    }

}