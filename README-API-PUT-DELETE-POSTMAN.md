# Actualizar y eliminar películas con Postman

Esta guía cubre las operaciones `PUT` y `DELETE` del API. Asume que ya completaste la configuración e instalación descritas en el README principal y en `README-API-POSTMAN.md`.

## 1. Iniciar la aplicación

Si la aplicación ya está corriendo en el puerto 8080, déjala activa. Si está detenida, abre PowerShell en la carpeta del proyecto y arráncala:

```powershell
.\gradlew.bat bootRun
```

El Seeder carga diez películas al arrancar. El API usa la base H2 en memoria activa durante ese proceso. Abre Postman y utiliza como base `http://localhost:8080/api/movies`.

Para las dos solicitudes, si envías un cuerpo JSON, en Postman selecciona **Body > raw > JSON**. Postman añadirá `Content-Type: application/json`; si no lo hace, agrega ese header manualmente.

## 2. Actualizar una película con PUT

`PUT` reemplaza todos los campos editables de la película indicada. El `id` se toma de la URL y no se manda en el JSON.

1. Crea una nueva solicitud en Postman.
2. Selecciona el método `PUT`.
3. Usa `http://localhost:8080/api/movies/1` como URL. El ejemplo actualiza la película cuyo ID es `1`; puedes consultar antes `GET http://localhost:8080/api/movies` para ver los IDs disponibles.
4. En **Body > raw > JSON**, pega:

```json
{
  "title": "La llegada (edición actualizada)",
  "synopsis": "Sinopsis modificada desde Postman para comprobar la operación PUT.",
  "year": 2016,
  "cover": "https://example.com/covers/la-llegada-actualizada.jpg"
}
```

5. Presiona **Send**.

Resultado esperado: `200 OK` y el objeto actualizado como JSON. Su `id` debe seguir siendo `1`. Los cuatro campos son obligatorios; si falta uno o llega vacío, la respuesta es `400 Bad Request`. Si el ID no existe, por ejemplo `99999`, la respuesta es `404 Not Found`.

Confirma el cambio con `GET http://localhost:8080/api/movies/1` o vuelve a consultar la lista completa. Deben aparecer el título y la sinopsis nuevos.

## 3. Eliminar una película con DELETE

1. Crea una nueva solicitud en Postman.
2. Selecciona el método `DELETE`.
3. Usa `http://localhost:8080/api/movies/1` como URL. Puedes borrar el registro que acabas de actualizar, o escoger otro ID de la lista.
4. No envíes body. Presiona **Send**.

Resultado esperado: `204 No Content`, sin cuerpo de respuesta. Comprueba la eliminación con `GET http://localhost:8080/api/movies/1`: debe responder `404 Not Found`. Si intentas eliminar directamente un ID inexistente, `DELETE` también responde `404 Not Found`.

## Resumen de rutas

| Método | Ruta | Resultado esperado |
| --- | --- | --- |
| `GET` | `/api/movies` | Lista películas (`200`) |
| `GET` | `/api/movies/{id}` | Consulta por ID (`200` o `404`) |
| `POST` | `/api/movies` | Crea una película (`201` o `400`) |
| `PUT` | `/api/movies/{id}` | Reemplaza los cuatro campos (`200`, `400` o `404`) |
| `DELETE` | `/api/movies/{id}` | Elimina la película (`204` o `404`) |

## Consideraciones para la evaluación

- Ejecuta `GET /api/movies` antes de empezar para conocer los IDs existentes.
- Ejecuta un `GET` después del `PUT` para demostrar que los cambios quedaron guardados.
- Ejecuta un `GET` después del `DELETE` para demostrar que el registro ya no está disponible.
- H2 está configurada en memoria: al detener y reiniciar la aplicación, la base se crea nuevamente y el Seeder vuelve a cargar las diez películas. Los cambios manuales realizados en Postman no se conservan después del reinicio.