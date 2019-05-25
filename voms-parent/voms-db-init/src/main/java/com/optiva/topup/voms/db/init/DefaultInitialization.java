package com.optiva.topup.voms.db.init;

import com.optiva.topup.voms.common.utils.ApplicationContextUtil;
import com.optiva.topup.voms.common.utils.FileUtil;
import com.optiva.topup.voms.common.utils.ReadResourceFileException;
import com.optiva.topup.voms.db.init.config.InitializationBean;
import com.optiva.topup.voms.db.init.config.InitializationConfig;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class DefaultInitialization implements ApplicationListener<ApplicationReadyEvent> {

  private final FileUtil fileUtil;
  private final Environment environment;

  @Autowired
  public DefaultInitialization(FileUtil fileUtil, Environment environment) {
    this.fileUtil = fileUtil;
    this.environment = environment;
  }

  @Override
  public void onApplicationEvent(final ApplicationReadyEvent event) {
    List<InputStream> inputStreams = readInitializationConfig();
    processDbInitialization(inputStreams);
  }

  private void processDbInitialization(List<InputStream> inputStreams) {
    inputStreams.forEach(file -> {
      try {
        JAXBContext jaxbContext = JAXBContext.newInstance(InitializationConfig.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        InitializationConfig initializationConfig = (InitializationConfig) unmarshaller.unmarshal(file);
        initializationConfig.getInitializationBean().stream()
            .sorted(Comparator.comparing(InitializationBean::getOrder))
            .forEach(element -> {
              DataInitializer dataInitializer = (DataInitializer) ApplicationContextUtil
                  .getApplicationContext()
                  .getBean(element.getBeanName());
              dataInitializer.init();
            });
      } catch (JAXBException ex) {
        throw new ReadInitializationConfigException(ex);
      }
    });
  }

  private List<InputStream> readInitializationConfig() {

    String[] profiles = environment.getActiveProfiles();
    List<InputStream> inputStreams = new ArrayList<>();

    for (String profile : profiles) {
      try {
        inputStreams.add(fileUtil.readResourceInputStream("db_init_config_" + profile + ".xml"));
      } catch (ReadResourceFileException e) {
        continue;
      }
    }
    return inputStreams;
  }

}
