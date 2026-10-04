# Prueba de la API con Postman

Esta guía complementa el README principal. Asume que Java ya está instalado y sabes iniciar el proyecto. Los endpoints trabajan con la misma base H2 en memoria que la página del catálogo.

## Antes de probar

1. En la carpeta del proyecto, inicia la aplicación con el comando del apartado **Ejecutar** del README principal.
2. Deja esa terminal abierta y confirma que Spring Boot inició en el puerto 8080.
3. Abre Postman.

La ruta base de la API es `http://localhost:8080/api/movies`. Envía y recibe JSON.

## 1. Obtener todas las películas

- Método: `GET`
- URL: `http://localhost:8080/api/movies`
- Body: ninguno

Presiona **Send**. Resultado esperado: estado `200 OK` y una lista JSON con las diez películas sembradas. Cada elemento incluye `id`, `title`, `synopsis`, `year` y `cover`.

## 2. Obtener una película por ID

- Método: `GET`
- URL: `http://localhost:8080/api/movies/1`
- Body: ninguno

Presiona **Send**. Resultado esperado para un ID existente: `200 OK` y un objeto JSON. Cambia `1` por otro ID para consultar otro registro. Para un ID inexistente, por ejemplo `99999`, el resultado esperado es `404 Not Found`.

## 3. Insertar una película

- Método: `POST`
- URL: `http://localhost:8080/api/movies`
- En **Headers**, agrega `Content-Type` con valor `application/json`.
- En **Body**, elige **raw** y selecciona **JSON**. Envía este ejemplo:

```json
{
  "title": "Película de prueba Postman",
  "synopsis": "Registro agregado durante la evaluación de la API.",
  "year": 2024,
  "cover": "https://example.com/covers/pelicula-prueba-postman.jpg"
}
```

Presiona **Send**. Resultado esperado: `201 Created`, la película guardada como JSON con su `id` generado y el header `Location` apuntando a `/api/movies/{id}`. No envíes `id`: lo genera la base de datos. Los cuatro campos son obligatorios y si falta alguno o llega vacío, la API responde `400 Bad Request`.

## 4. Confirmar la inserción

Vuelve a enviar `GET http://localhost:8080/api/movies`. La nueva película debe aparecer en la lista. También puedes copiar el `id` de la respuesta del POST y usar `GET http://localhost:8080/api/movies/{id}`.

## Resumen de rutas

| Método | Ruta | Resultado esperado |
| --- | --- | --- |
| `GET` | `/api/movies` | Lista todas las películas (`200`) |
| `GET` | `/api/movies/{id}` | Busca por ID (`200` o `404`) |
| `POST` | `/api/movies` | Inserta una película (`201` o `400`) |

La base H2 configurada para esta actividad es temporal. Al detener la aplicación se perderá el registro enviado por la petición POST, al arrancarla de nuevo el Seeder carga otra vez las diez películas de ejemplo.