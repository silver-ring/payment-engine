package com.optiva.topup.voms.rest.exceptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HibernateExceptionHandler {

  @ExceptionHandler
  @ResponseStatus(HttpStatus.CONFLICT)
  public HibernateExceptionResponse handle(DataIntegrityViolationException exception) {

    PipelineObject pipelineObject = init(exception);

    PipelineObject pipelineObject1 = step1(pipelineObject);
    PipelineObject pipelineObject2 = step2(pipelineObject1);
    PipelineObject pipelineObject3 = step3(pipelineObject2);
    PipelineObject pipelineObject4 = step4(pipelineObject3);
    PipelineObject pipelineObject5 = step5(pipelineObject4);

    return pipelineObject5.getHibernateExceptionResponse();
  }

  private PipelineObject init(DataIntegrityViolationException exception) {
    PipelineObject pipelineObject = new PipelineObject();
    HibernateExceptionResponse hibernateExceptionResponse = new HibernateExceptionResponse();

    String message = exception.getMostSpecificCause().getMessage();
    hibernateExceptionResponse.setMessage(message);

    pipelineObject.setHibernateExceptionResponse(hibernateExceptionResponse);
    pipelineObject.setNextPart(message);
    return pipelineObject;
  }

  private PipelineObject step1(PipelineObject pipelineObject) {
    String[] parts = pipelineObject.getNextPart().split(";");
    String sql = parts[1].replace("SQL statement:", "").replaceAll("\\[.*\\]", "").trim();
    String errorCode = parts[1].replace("SQL statement:", "").split("\\[")[1].replace("]", "");
    String nextPart = parts[0].replace("SQL statement:", "").replaceAll("\\[.*\\]", "").trim();

    pipelineObject.getHibernateExceptionResponse().setSql(sql);
    pipelineObject.getHibernateExceptionResponse().setErrorCode(errorCode);
    pipelineObject.setNextPart(nextPart);

    return pipelineObject;
  }

  private PipelineObject step2(PipelineObject pipelineObject) {
    String[] parts = pipelineObject.getNextPart().split(":");
    String violationType = parts[0].trim();
    String nextPart = parts[1].replaceAll("\"", "").trim();

    pipelineObject.getHibernateExceptionResponse().setViolationType(violationType);
    pipelineObject.setNextPart(nextPart);

    return pipelineObject;
  }

  private PipelineObject step3(PipelineObject pipelineObject) {
    String[] parts = pipelineObject.getNextPart().split("ON PUBLIC.");

    String constraintName = parts[0].trim();
    String nextPart = parts[1].trim();

    pipelineObject.getHibernateExceptionResponse().setConstraintName(constraintName);
    pipelineObject.setNextPart(nextPart);

    return pipelineObject;
  }

  private PipelineObject step4(PipelineObject pipelineObject) {
    String[] parts = pipelineObject.getNextPart().split("VALUES");

    String[] valuesParts = parts[1].trim().replace("(", "").replace(")", "").replace("'", "").split(",");

    List<String> values = new ArrayList<>(Arrays.asList(valuesParts)).stream().map(String::trim)
        .collect(Collectors.toList());

    String id = values.remove(values.size() - 1).trim();

    String nextPart = parts[0].trim();

    pipelineObject.getHibernateExceptionResponse().setId(id);
    pipelineObject.getHibernateExceptionResponse().setValues(values);
    pipelineObject.setNextPart(nextPart);

    return pipelineObject;
  }

  private PipelineObject step5(PipelineObject pipelineObject) {
    String[] parts = pipelineObject.getNextPart().split("\\(");

    String tableName = parts[0].trim();

    List<String> props = Arrays.asList(parts[1].replace(")", "").split(",")).stream().map(String::trim)
        .collect(Collectors.toList());

    pipelineObject.getHibernateExceptionResponse().setTableName(tableName);
    pipelineObject.getHibernateExceptionResponse().setProps(props);
    pipelineObject.setNextPart(null);

    return pipelineObject;
  }

}
