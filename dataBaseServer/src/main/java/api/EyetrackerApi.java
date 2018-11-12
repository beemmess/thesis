package api;


import api.impl.EyetrackerApiServiceImpl;
import client.CassandraClient;
import client.domain.EyeTracker;
import model.EyeTrackerMessage;
import org.jboss.logging.Logger;
import io.swagger.annotations.*;

import javax.annotation.Generated;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/eyetrackerRaw")
@Api(description = "the eyetracker API")
public class EyetrackerApi  {
    private static final Logger logger = Logger.getLogger(EyetrackerApi.class.getName());

    private final EyetrackerApiServiceImpl eyetrackerApiService = new EyetrackerApiServiceImpl();

    @POST
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Transfer eyetracking data", notes = "Create a new data transfer for eyetracking data")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Data transfer successful"),
            @ApiResponse(code = 400, message = "Error, bad request.") })
    public Response postEytracker(@ApiParam(value = "Create a new data transfer for eyetracking data" ,required=true) EyeTrackerMessage message)
            throws NotFoundException {
        logger.info("inside eytrackerapi");
        return eyetrackerApiService.response(message);
    }


//    @GET
//    @Path("/{userId}")
//    @ApiOperation(value = "Find values in database by userId", notes = "Create a query message to database to retrieve certain data",
//            response = String.class)
//    @ApiResponses(value = {@ApiResponse(code = 400, message = "invalid user id")})
//    public Response getDataFromDb(
//            @ApiParam(value = "Query string for userId for cassandra database", required = true) @PathParam("userId") String userId){
//        CassandraClient cassandraClient = new CassandraClient();
//        String resp = cassandraClient.getDataFromUserId(userId);
//        logger.info(resp);
////        return eyetrackerApiService.response(userId);
//        return Response.ok().entity("query: " + resp).build();
//    }

//    @GET
//    @Path("/getData")
//    @ApiOperation(value = "Find values in database using a query", notes = "Create a query message to database to retrieve certain data",
//            response =String.class)
//    @ApiResponses(value = {@ApiResponse(code = 400, message = "invalid query string")})
//    public Response getDataFromDb(
//            @ApiParam(value = "Query string for cassandra database", required = true) @QueryParam("query") String query){
//        return Response.ok().entity("query: " + query).build();
//    }




}
