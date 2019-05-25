package com.optiva.topup.voms.db.init.utils;

import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class ScriptInitUtils {

  @Autowired
  private DataSource dataSource;

  public void execute(String scriptName) {
    try {
      Resource resource = new ClassPathResource(scriptName);
      log.info("start script execution: " + scriptName);
      ScriptUtils.executeSqlScript(dataSource.getConnection(), resource);
      log.info("end script execution: " + scriptName);
    } catch (SQLException e) {
      throw new InvalidSqlScriptException(e);
    }
  }

}
