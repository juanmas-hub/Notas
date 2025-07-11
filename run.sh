#!/bin/bash

# === Preguntar al usuario los datos de acceso a MySQL ===
read -p "MySQL user: " DB_USER
read -s -p "MySQL password: " DB_PASS
echo
read -p "Database name [notes_db]: " DB_NAME
DB_NAME=${DB_NAME:-notes_db}  # usa "notes_db" si no se ingresa nada

MYSQL_PORT=3306

# === Verificar herramientas necesarias ===
function check_command() {
    command -v "$1" >/dev/null 2>&1 || {
        echo >&2 "'$1' is not installed. Aborting."
        exit 1
    }
}

echo "🔍 Checking required tools..."
check_command mysql
check_command mvn
check_command npm

# === Crear base de datos si no existe ===
echo "Looking out if database '$DB_NAME' exists..."
DB_EXISTS=$(mysql -u"$DB_USER" -p"$DB_PASS" -P"$MYSQL_PORT" -e "SHOW DATABASES LIKE '$DB_NAME';" 2>/dev/null | grep "$DB_NAME")

if [ "$DB_EXISTS" = "$DB_NAME" ]; then
    echo "Database '$DB_NAME' already exists."
else
    echo "Creating database '$DB_NAME'..."
    mysql -u"$DB_USER" -p"$DB_PASS" -P"$MYSQL_PORT" -e "CREATE DATABASE $DB_NAME CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
    echo "Database created."
fi

# === Generar application.properties dinámicamente ===
echo "Generating backend/src/main/resources/application.properties..."
cat > backend/src/main/resources/application.properties <<EOF
spring.application.name=backend

# ===============================
# DATABASE CONFIGURATION (MySQL)
# ===============================

spring.datasource.url=jdbc:mysql://localhost:3306/$DB_NAME?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=$DB_USER
spring.datasource.password=$DB_PASS
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate config
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
EOF

# === Iniciar backend ===
echo "Initializing backend..."
cd backend
mvn spring-boot:run &
BACKEND_PID=$!
cd ..

# === Iniciar frontend ===
echo "Initializing frontend..."
cd frontend
npm install
npm start &
FRONTEND_PID=$!
cd ..

echo "Everything is up and running. Backend (PID $BACKEND_PID), Frontend (PID $FRONTEND_PID)"
echo "Press Ctrl+C to stop."

wait

