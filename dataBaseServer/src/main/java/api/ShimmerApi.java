package api;

import api.impl.ShimmerApiServiceImpl;
import io.swagger.annotations.*;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/shimmerRaw")


@Api(description = "the shimmer API")
public class ShimmerApi  {
   private final ShimmerApiServiceImpl shimmerApiServiceImpl = new ShimmerApiServiceImpl();

    @POST
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Transfer Shimmer data", notes = "Create a new data transfer for Shimmer data")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Data transfer is successfull. Message recieved"),
        @ApiResponse(code = 400, message = "Data transfer did not happen.")})
    public Response postShimmer(@ApiParam(value = "Data transfer" ,required=true) model.ShimmerMessage message)
    throws NotFoundException {
        return shimmerApiServiceImpl.response(message);
    }
}
