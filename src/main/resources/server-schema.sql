CREATE TABLE oauth_client_details(
    CLIENT_ID VARCHAR(255) PRIMARY KEY,
    RESOURCE_IDS VARCHAR(256),
    CLIENT_SECRET VARCHAR(256),
    SCOPE VARCHAR(256),
    AUTHORIZED_GRANT_TYPES VARCHAR(256),
    WEB_SERVER_REDIRECT_URI VARCHAR(256),
    AUTHORITIES VARCHAR(256),
    ACCESS_TOKEN_VALIDITY INTEGER,
    REFRESH_TOKEN_VALIDITY INTEGER,
    ADDITIONAL_INFORMATION VARCHAR(4096),
    AUTOAPPROVE VARCHAR(256)
);

CREATE TABLE oauth_access_token(
   AUTHENTICATION_ID VARCHAR(255) PRIMARY KEY,
   TOKEN_ID VARCHAR(256),
   TOKEN LONGBLOB,
   USER_NAME VARCHAR(256),
   CLIENT_ID VARCHAR(256),
   AUTHENTICATION LONGBLOB,
   REFRESH_TOKEN VARCHAR(256)
);

CREATE TABLE oauth_approvals(
    userId VARCHAR(256),
    clientId VARCHAR(256),
    scope VARCHAR(256),
    status VARCHAR(10),
    expiresAt TIMESTAMP,
    lastModifiedAt TIMESTAMP
);

CREATE TABLE oauth_refresh_token(
    TOKEN_ID VARCHAR(256),
    TOKEN LONGBLOB,
    AUTHENTICATION LONGBLOB
);

CREATE TABLE member (
    MEMBER_NO INTEGER NOT NULL AUTO_INCREMENT,
    EMAIL VARCHAR(255) NOT NULL UNIQUE,
    PASSWORD VARCHAR(256) NOT NULL,
    NAME VARCHAR(256) NOT NULL,
    JOIN_FROM VARCHAR(256),
    PRIMARY KEY(MEMBER_NO)
);

CREATE TABLE kakao_member (
    KAKAO_ID INTEGER NOT NULL,
    KAKAO_ACCESS_TOKEN VARCHAR(256) NOT NULL,
    KAKAO_REFRESH_TOKEN VARCHAR(256) NOT NULL,
    PRIMARY KEY(KAKAO_ID)
);
