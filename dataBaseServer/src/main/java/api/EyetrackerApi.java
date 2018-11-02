package api;


import api.impl.EyetrackerApiServiceImpl;
import model.EyeTrackerMessage;
import org.jboss.logging.Logger;
import io.swagger.annotations.*;
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


}
