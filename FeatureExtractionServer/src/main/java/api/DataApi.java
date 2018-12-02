package api;


import api.impl.RawDataApiServiceImpl;
import io.swagger.annotations.*;
import model.DataMessage;
import org.jboss.logging.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.*;

@Path("/data")
@Api(description = "the data API")
public class DataApi {
    private static final Logger logger = Logger.getLogger(DataApi.class.getName());

    private final RawDataApiServiceImpl rawDataApiService = new RawDataApiServiceImpl();

    @POST
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Transfer collected data to server", notes = "Create a new data transfer for collected data")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Data received"),
            @ApiResponse(code = 400, message = "Error, bad request.") })
    public Response postData(@ApiParam(value = "Create a new data transfer for collected data" ,required=true) DataMessage message)
            throws NotFoundException {
        return rawDataApiService.response(message);
    }


}
