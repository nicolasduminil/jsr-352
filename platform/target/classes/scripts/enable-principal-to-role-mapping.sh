# enable default-principal-to-role-mapping
/opt/payara/appserver/bin/asadmin set server-config.security-service.activate-default-principal-to-role-mapping=true

# create realm
/opt/payara/appserver/bin/asadmin create-auth-realm --classname com.sun.enterprise.security.auth.realm.ldap.LDAPRealm \
--property jaas-context=ldapRealm:directory=ldap://192.168.128.4:10389:base-dn=dc=payara,dc=fish
