//package api;
//
//import messages.SuccessResponse;
//import io.swagger.annotations.ApiParam;
//import javax.ws.rs.*;
//import javax.ws.rs.core.Response;
//
//@Path("/shimmer")
//
//
//@io.swagger.annotations.Api(description = "the shimmer API")
//public class ShimmerApi  {
//   private final ShimmerApiService delegate = ShimmerApiServiceFactory.getShimmerApi();
//
//    @POST
//    @Consumes({ "application/json" })
//    @Produces({ "application/json" })
//    @io.swagger.annotations.ApiOperation(value = "Transfer Shimmer data", notes = "Create a new data transfer for Shimmer data", response = SuccessResponse.class, tags={  })
//    @io.swagger.annotations.ApiResponses(value = {
//        @io.swagger.annotations.ApiResponse(code = 200, message = "Data transfer is successfull. Message recieved, Success is true.", response = SuccessResponse.class),
//
//        @io.swagger.annotations.ApiResponse(code = 400, message = "Data transfer did not happe, Success is false.", response = SuccessResponse.class),
//
//        @io.swagger.annotations.ApiResponse(code = 500, message = "Internal server error. Returns error message", response = SuccessResponse.class) })
//    public Response postShimmer(@ApiParam(value = "Data transfer" ,required=true) messages.ShimmerMessage message;
//)
//    throws NotFoundException {
//        return delegate.postShimmer(shimmer);
//    }
//}
