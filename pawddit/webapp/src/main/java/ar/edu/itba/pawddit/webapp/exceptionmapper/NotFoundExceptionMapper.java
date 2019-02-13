package ar.edu.itba.pawddit.webapp.exceptionmapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ar.edu.itba.pawddit.webapp.dto.ExceptionDto;
import ar.edu.itba.pawddit.webapp.exceptions.NotFoundException;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException>{

	@Override
	public Response toResponse(final NotFoundException exception) {
		return Response.status(Status.NOT_FOUND).entity(new ExceptionDto(exception.getMessage())).build();
	}
	
}