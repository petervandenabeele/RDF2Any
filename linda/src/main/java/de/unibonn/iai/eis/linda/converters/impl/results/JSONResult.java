package de.unibonn.iai.eis.linda.converters.impl.results;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hp.hpl.jena.query.ResultSet;

/**
 * @author gsingharoy
 *
 *
 **/

public class JSONResult {
	public Boolean distict;
	public Boolean ordered;
	public List<Map<String,Map<String,String>>> bindings;
	public JSONResult(ResultSet rdfResults){
		this.distict = false;
		this.ordered = true;
		this.bindings = new ArrayList();
		
	}
}
