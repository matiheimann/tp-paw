package ar.edu.itba.pawddit.webapp.exceptionmapper;

import ar.edu.itba.pawddit.webapp.dto.ExceptionDTO;
import ar.edu.itba.pawddit.webapp.exceptions.RepeatedEmailException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RepeteadEmailMapper implements ExceptionMapper<RepeatedEmailException> {
	
	@Override
	public Response toResponse(final RepeatedEmailException exception) {
		return Response.status(Status.CONFLICT).entity(new ExceptionDTO(exception.getMessage(), exception.getConstraintViolations())).type(MediaType.APPLICATION_JSON).build();
	}

}