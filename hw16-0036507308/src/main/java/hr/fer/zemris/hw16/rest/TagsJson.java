package hr.fer.zemris.hw16.rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

import hr.fer.zemris.hw16.util.Picture;
import hr.fer.zemris.hw16.util.PictureDB;

/**
 * 
 * @author Martina
 *
 */
@Path("/tagsj")
public class TagsJson {
	
	@Context
    private ServletContext context;
	
	@GET
	@Produces("application/json")
	public Response getTags() {

		PictureDB.getPicturesFromFile(context);
		List<String> tags = PictureDB.getTags();

		Gson gson = new Gson();

		String jsonText = gson.toJson(tags);

        return Response.status(Status.OK).entity(jsonText).build();
	}

	@Path("{tag}")
	@GET
	@Produces("application/json")
	public Response getPictures(@PathParam("tag")String tag) {
		

		List<Picture> pics = PictureDB.getPicturesForTag(tag);

		List<String> names = new ArrayList<String>();
		
		for (Picture p : pics) {
			names.add(p.getName());
		}
		Gson gson = new Gson();

		String jsonText = gson.toJson(names);

        return Response.status(Status.OK).entity(jsonText).build();
	}
	
	@Path("picture/{name}")
	@GET
	@Produces("application/json")
	public Response getPicture(@PathParam("name")String name) {
		
		Picture p = PictureDB.getPictureForName(name);
		
		String text = "{" +
					  "\"name\":\"" + p.getName() + "\"," +
					  "\"desc\":\"" + p.getDescription() + "\"," +
					  "\"tags\":\"";
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(text);
		boolean first = true;
		
		
		for (String tag : p.getTags()) {
			if (first) {
				first = false;
			}
			else {
				sb.append(", ");
			}	
			sb.append(tag);
		}

		sb.append("\"}");
		Gson gson = new Gson();

		String jsonText = gson.toJson(p);
		
		return Response.status(Status.OK).entity(sb.toString()).build();
	}
}
