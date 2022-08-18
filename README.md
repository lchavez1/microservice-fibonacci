# microservice-fibonacci
Prueba tecnica para desarrollo de Microservicios / Grupo Salinas.

Cuenta con dos end point solicitados:
- Calcular serie fibonacci y devolverla | adjunto a este proceso se guarda en un documento JSON cada una de las peticiones que se realizaron de manera correcta.
[http://localhost:8080/fibo/{n}] [GET]
- Eliminar una peticion registrada en el archivo JSON.
[http://localhost:8080/fibo/{n}] [DELETE]

*Se incluye el contrato de interfaz generado en Swagger.*

Por ultimo se agrego un front-end super simple que permite hacer peticiones (consumir) al end point de fibonacci.
