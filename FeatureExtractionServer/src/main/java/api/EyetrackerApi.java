package api;


import api.impl.EyetrackerApiServiceImpl;
import io.swagger.annotations.ApiParam;
import io.swagger.jaxrs.*;
import messages.SuccessResponse;
import org.jboss.logging.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.*;

@Path("/eyetracker")
@io.swagger.annotations.Api(description = "the eyetracker API")
public class EyetrackerApi  {
    private static final Logger logger = Logger.getLogger(EyetrackerApi.class.getName());

    private final EyetrackerApiServiceImpl delegate = new EyetrackerApiServiceImpl();

    @POST
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Transfer eyetracking data", notes = "Create a new data transfer for eyetracking data", response = SuccessResponse.class, tags={  })
    @io.swagger.annotations.ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 200, message = "Eytracking tranferring data successful", response = SuccessResponse.class) })
    public Response postEytracker(@ApiParam(value = "Create a new data transfer for eyetracking data" ,required=true) messages.EyeTrackerMessage message)
            throws NotFoundException {
        logger.info("inside eytrackerapi");
        return delegate.response(message);
    }
}
