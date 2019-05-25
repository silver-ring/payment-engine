package com.optiva.topup.voms.db.init.config;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "InitializationConfig")
public class InitializationConfig {

  private List<InitializationBean> initializationBean;

  @XmlElement(name = "InitializationBean")
  public List<InitializationBean> getInitializationBean() {
    return initializationBean;
  }

  public void setInitializationBean(
      List<InitializationBean> initializationBean) {
    this.initializationBean = initializationBean;
  }

}
