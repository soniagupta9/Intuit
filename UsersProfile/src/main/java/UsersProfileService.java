package main.java;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.GeocodingResult;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sonia on 10/31/16.
 */
@Path("/usersProfile")
public class UsersProfileService {

    private static final String API_KEY = "AIzaSyCOWBEtkYWc5n4dI6JEaOwQyKKisi4hSko";

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUsersProfile(List<UserProfile> userProfileList) {
        final ObjectMapper mapper = new ObjectMapper();
        GeoApiContext context = new GeoApiContext().setApiKey(API_KEY);
        try {
            for (UserProfile userProfile : userProfileList) {
                GeocodingResult[] results = GeocodingApi.geocode(context,
                        userProfile.getAddress()).await();
                AddressComponent[] addressComponents = results[0].addressComponents;
                for (AddressComponent addressComponent : addressComponents) {
                    if (addressComponent.types[0].name().equals("POSTAL_CODE")) {
                        userProfile.setPostalCode(Integer.valueOf(addressComponent.shortName));
                    }
                }

            }
        } catch (Exception e) {
            return Response.serverError().build();
        }
        try {

            FileWriter file = new FileWriter("/Users/sonia/Documents/intuit/test.json");
            file.write(mapper.writeValueAsString(userProfileList));
            file.flush();
            file.close();

        } catch (IOException e) {
            return Response.serverError().build();
        }

        // return HTTP response 200 in case of success
        return Response.status(201).build();
    }

    @GET
    @Path("/{postalCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserProfiles(@PathParam("postalCode") int postalCode) {
        final ObjectMapper mapper = new ObjectMapper();
        List<UserProfile> usersWithSamePostalCode = new ArrayList<>();
        List<UserProfile> userProfileList = new ArrayList<>();
        String result;
        TypeReference<List<UserProfile>> mapType = new TypeReference<List<UserProfile>>() {
        };
        try {
            userProfileList = mapper.readValue(new File("/Users/sonia/Documents/intuit/test.json"), mapType);
            for (UserProfile userProfile : userProfileList) {
                if (userProfile.getPostalCode() == postalCode) {
                    usersWithSamePostalCode.add(userProfile);
                }
            }

        } catch (IOException e) {
            return Response.serverError().build();
        }
        try {
            result = mapper.writeValueAsString(usersWithSamePostalCode);
        } catch (Exception e) {
            return Response.serverError().build();
        }
        // return HTTP response 200 in case of success
        return Response.status(200).entity(result).build();
    }

}

