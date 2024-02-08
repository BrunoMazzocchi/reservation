#!/bin/bash

# Esperar a que MySQL esté disponible
/wait-for-it.sh mysql:$MYSQL_PORT

# Restaurar la base de datos desde el archivo de respaldo
if [ -f /backup-dir/backup.sql ]; then
  mysql -u root -p"$MYSQL_ROOT_PASSWORD" "$MYSQL_DATABASE" < /backup-dir/backup.sql
else
  echo "No se encontró el archivo de respaldo (/backup-dir/backup.sql)"
fi

# Iniciar el servicio MySQL
service mysql start

# Iniciar la aplicación
java -jar /usr/local/lib/app.jar