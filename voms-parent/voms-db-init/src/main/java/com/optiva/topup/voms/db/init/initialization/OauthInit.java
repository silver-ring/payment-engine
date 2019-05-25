package com.optiva.topup.voms.db.init.initialization;

import com.optiva.topup.voms.db.init.DataInitializer;
import com.optiva.topup.voms.db.init.utils.ScriptInitUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OauthInit implements DataInitializer {

  private final ScriptInitUtils scriptInitUtils;

  @Autowired
  public OauthInit(ScriptInitUtils scriptInitUtils) {
    this.scriptInitUtils = scriptInitUtils;
  }

  @Override
  public void init() {
    String quartzInitScriptName = "oauth_init.sql";
    scriptInitUtils.execute(quartzInitScriptName);
  }

}
