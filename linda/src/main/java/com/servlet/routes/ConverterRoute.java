package com.servlet.routes;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.UriInfo;

import de.unibonn.iai.eis.linda.converters.impl.CSVConverter;
import de.unibonn.iai.eis.linda.converters.impl.JSONConverter;
import de.unibonn.iai.eis.linda.converters.impl.RDBConverter;
import de.unibonn.iai.eis.linda.converters.impl.results.JSONOutput;
import de.unibonn.iai.eis.linda.example.SPARQLExample;
import de.unibonn.iai.eis.linda.helper.OutputStreamHandler;
import de.unibonn.iai.eis.linda.helper.SPARQLHandler;

@Path("/v1.0/convert/")
public class ConverterRoute {
	@GET
	@Path("csv-converter.csv")
	@Produces({"application/csv"})
	public StreamingOutput getCSVConverter(@Context UriInfo uriInfo) {
		MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters(); 
		String query = queryParams.getFirst("query");
		String dataset = queryParams.getFirst("dataset");
		System.out.println("START CSV conversion for query in dataset "+dataset+" \n"+query);
		return OutputStreamHandler.getConverterStreamingOutput(new CSVConverter(),dataset,query );

	}

	@GET
	@Path("rdb-converter.sql")
	@Produces({"application/sql"})
	public StreamingOutput getRDBConverter(@Context UriInfo uriInfo) {
		MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters(); 
		String query = queryParams.getFirst("query");
		String dataset = queryParams.getFirst("dataset");
		System.out.println("START RDV conversion for query in dataset "+dataset+" \n"+query);
		return OutputStreamHandler.getConverterStreamingOutput(new RDBConverter(), dataset, query);

	}
	
	
	@GET
	@Path("json")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONOutput getJSONConverter(@Context UriInfo uriInfo){
		MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters(); 
		String query = queryParams.getFirst("query");
		String dataset = queryParams.getFirst("dataset");
		Double startMilliseconds = (double) System.currentTimeMillis( );
		System.out.println("START JSON conversion for query in dataset "+dataset+" \n"+query);
		JSONConverter converter = new JSONConverter(SPARQLHandler.executeQuery(dataset, query));
		Double endMilliseconds = (double) System.currentTimeMillis( );
		converter.jsonOutput.setTimeTaken((endMilliseconds-startMilliseconds)/1000);
		System.out.println("FINISH JSON conversion ... ");
		return converter.jsonOutput;
	}

}
