DROP TABLE IF EXISTS oauth_access_token;
DROP TABLE IF EXISTS oauth_refresh_token;

create table oauth_access_token (
  token_id VARCHAR(256),
  token MEDIUMBLOB,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name VARCHAR(256),
  client_id VARCHAR(256),
  authentication MEDIUMBLOB,
  refresh_token VARCHAR(256)
) ENGINE=InnoDB;

create table oauth_refresh_token (
  token_id VARCHAR(256),
  token MEDIUMBLOB,
  authentication MEDIUMBLOB
) ENGINE=InnoDB;

commit;
