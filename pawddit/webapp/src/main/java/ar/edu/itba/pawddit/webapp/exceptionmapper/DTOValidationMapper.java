package ar.edu.itba.pawddit.webapp.exceptionmapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ar.edu.itba.pawddit.webapp.dto.ExceptionDto;
import ar.edu.itba.pawddit.webapp.exceptions.DTOValidationException;

@Provider
public class DTOValidationMapper implements ExceptionMapper<DTOValidationException>{

	@Override
	public Response toResponse(final DTOValidationException exception) {
		return Response.status(Status.CONFLICT).entity(new ExceptionDto(exception.getMessage(), exception.getConstraintViolations())).build();
	}
	
}
