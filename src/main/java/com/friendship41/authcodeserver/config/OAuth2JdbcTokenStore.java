package com.friendship41.authcodeserver.config;

import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

public class OAuth2JdbcTokenStore extends JdbcTokenStore {
  private static final Log LOG = LogFactory.getLog(OAuth2JdbcTokenStore.class);

  public OAuth2JdbcTokenStore(final DataSource dataSource) {
    super(dataSource);
  }

  @Override
  public OAuth2AccessToken readAccessToken(final String tokenValue) {
    OAuth2AccessToken accessToken = null;

    try {
      accessToken = new DefaultOAuth2AccessToken(tokenValue);
    }
    catch (EmptyResultDataAccessException e) {
      if (LOG.isInfoEnabled()) {
        LOG.info("Failed to find access token for token "+tokenValue);
      }
    }
    catch (IllegalArgumentException e) {
      LOG.warn("Failed to deserialize access token for " +tokenValue,e);
      removeAccessToken(tokenValue);
    }

    return accessToken;
  }
}
