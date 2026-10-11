# Catálogo de películas

Aplicación de catálogo implementada con Spring Boot, Spring Data JPA y H2.
H2 es una base de datos en memoria para poder facilitar la ejecucion y test del proyecto.
Permite ejecutar facilmente sin instalar un servidor de base de datos.

Para probar los endpoints REST con Postman, consulta [README-API-POSTMAN.md](README-API-POSTMAN.md).

## Requisitos

- Solo el JDK 21.

1. Accede a https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html
2. Descarga el ejecutable Installer (.msi) acorde a tu maquina.
3. En la pantalla de Custom Setup, asegúrate de marcar con un icono de disco duro las siguientes opciones:

   Set JAVA_HOME variable -> Selecciona "Will be installed on local hard drive".

   Add to PATH -> Selecciona "Will be installed on local hard drive".

4. Completa la instalación haciendo clic en Next y luego en Install.
5. Abre una nueva ventana de Git Bash, PowerShell o CMD (es importante abrir una ventana nueva para que reconozca los cambios de variables) y ejecuta:

```powershell
java -version
```
6. Deberías ver una salida similar a:

   java version "21.0.x" ...

## Ejecutar

Abre PowerShell en la carpeta del proyecto `./catalogo` y ejecuta:

```powershell
.\gradlew.bat bootRun
```

La primera ejecución puede tardar un poco mientras Gradle descarga dependencias. Cuando aparezca el mensaje de que Tomcat inició en el puerto 8080, consulta <http://localhost:8080/api/movies> para recibir el catálogo como JSON.
La ruta principal (`/`) también devuelve la lista de películas como JSON. Para detener el servidor, vuelve a la terminal donde ejecutaste el comando `.\gradlew.bat bootRun` y presiona `Ctrl+C`.

## Revisar los datos

Al iniciar la aplicación, la clase `MovieSeeder` agrega diez registros a `movies`. Puedes revisar título, sinopsis, año y portada consultando <http://localhost:8080/api/movies>. La columna `cover` contiene direcciones de ejemplo.

### Consultar la base de datos en memoria (H2) directamente

Con la aplicación ejecutándose, abre <http://localhost:8080/h2-console/>. En la pantalla de inicio de H2 solo asegurate de tener estos valores en estos campos:

| Campo | Valor |
| --- | --- |
| Driver Class | `org.h2.Driver` |
| JDBC URL | `jdbc:h2:mem:catalogo_db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;NON_KEYWORDS=YEAR` |
| User Name | `sa` |
| Password | déjala vacía |

Presiona **Connect**. En la consola SQL puedes revisar la tabla y sus columnas con:

```sql
SHOW TABLES;
SELECT id, title, synopsis, year, cover FROM movies ORDER BY id;
SELECT COUNT(*) AS total_peliculas FROM movies;
```

También puedes filtrar o cambiar los datos mientras la aplicación está activa, por ejemplo:

```sql
SELECT title, year FROM movies WHERE year >= 2015 ORDER BY year;
```

La consola y la aplicación se conectan a la misma base de datos en memoria. Mientras la aplicación esté corriendo puedes hacer consultas.
Al cerrar el proceso, la base de datos **H2 se borra automáticamente**, y en el siguiente arranque se crea de nuevo y el Seeder carga otra vez las diez películas. La consola H2 está pensada para evaluación local, no para exponerla en un servidor público.

## Cómo se relaciona con Laravel

- `Movie.java` es el modelo/entidad y define los campos persistidos en la tabla `movies`.
- Hibernate crea el esquema al arrancar, cambiar de base de datos es tan facil como cambiar la dependencia y la configuracion.
- `MovieSeeder.java` construye y guarda los diez objetos mediante el repositorio de Spring Data JPA.
- `MovieRepository.java` ofrece las operaciones de base de datos, y `MovieRestController` expone el catálogo como JSON en `/api/movies`.

## Usar otra base de datos

Para otra base SQL el patrón es: agregar su driver JDBC al `build.gradle` y configurar la URL JDBC, el driver, el usuario y la contraseña adecuados. Conviene revisar también que los tipos SQL generados sean compatibles.