package ar.edu.itba.pawddit.webapp.exceptionmapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ar.edu.itba.pawddit.webapp.dto.ExceptionDTO;
import ar.edu.itba.pawddit.webapp.exceptions.RepeatedUsernameException;

@Provider
public class RepeatedUsernameMapper implements ExceptionMapper<RepeatedUsernameException>{

	@Override
	public Response toResponse(final RepeatedUsernameException exception) {
		return Response.status(Status.CONFLICT).entity(new ExceptionDTO("Repeated username")).type(MediaType.APPLICATION_JSON).build();
	}

	
}
