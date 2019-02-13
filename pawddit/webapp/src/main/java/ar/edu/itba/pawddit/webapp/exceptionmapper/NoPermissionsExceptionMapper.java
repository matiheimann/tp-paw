package ar.edu.itba.pawddit.webapp.exceptionmapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ar.edu.itba.pawddit.services.exceptions.NoPermissionsException;
import ar.edu.itba.pawddit.webapp.dto.ExceptionDto;

@Provider
public class NoPermissionsExceptionMapper implements ExceptionMapper<NoPermissionsException>{

	@Override
	public Response toResponse(final NoPermissionsException exception) {
		return Response.status(Status.FORBIDDEN).entity(new ExceptionDto(exception.getMessage())).build();
	}
	
}
