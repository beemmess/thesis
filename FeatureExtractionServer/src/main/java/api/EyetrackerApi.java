package api;


import api.impl.EyetrackerApiServiceImpl;
import io.swagger.annotations.*;
import org.jboss.logging.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.*;

@Path("/eyetracker")
@Api(description = "the eyetracker API")
public class EyetrackerApi  {
    private static final Logger logger = Logger.getLogger(EyetrackerApi.class.getName());

    private final EyetrackerApiServiceImpl eyetrackerApiService = new EyetrackerApiServiceImpl();

    @POST
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Transfer eyetracking data", notes = "Create a new data transfer for eyetracking data")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Data received"),
            @ApiResponse(code = 400, message = "Error, bad request.") })
    public Response postEytracker(@ApiParam(value = "Create a new data transfer for eyetracking data" ,required=true) model.EyeTrackerMessage message)
            throws NotFoundException {
        return eyetrackerApiService.response(message);
    }


}
