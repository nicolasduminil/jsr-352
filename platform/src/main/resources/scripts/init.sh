#
#!/bin/bash
#
ldapadd -h localhost -p 10389 -D "uid=admin, ou=system" -w secret -a -f ./bootstrap/conf/test2.ldif
