package com.experiment.quarkus.resource;

import com.experiment.quarkus.dto.ImageRequest;
import com.experiment.quarkus.dto.ImageResponse;
import com.experiment.quarkus.services.ImageService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/images")
public class ImageResource {

    @Inject
    ImageService imageService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveImages(ImageRequest request){
        ImageResponse response = imageService.saveImages(request);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }
}
