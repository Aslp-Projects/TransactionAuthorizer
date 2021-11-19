# TransactionAuthorizer

Microservicio encargado de crear cuentas, actualizar montos disponibles por cuenta y realizar registro de transacciones

## Uso

Comando:
```bash
mvn clean install
```

- Compilación
- Generación de archivo .jar
- Pruebas

### El ingreso de las operaciones (json como string) es por medio de consola


## Acceso a BD H2 mientras se ejecuta aplicacion:
1. Se debe ingresar a la ruta: http://localhost:8080/h2-console
   - JDBC URL: jdbc:h2:mem:testdb
   - User: user
   - Password: pass



### HealthController

#### Nodo raíz
```java
@RequestMapping("health")
```
#### Recursos
```
    GET   /health
```

### HealthController

#### Nodo raíz
```java
@RequestMapping("transaction")
```
#### Recursos
```
    POST  /transaction
    GET   /accounts
```


#### Recursos
Metodo | ruta | descripción
--- | --- | ---
GET | /health | Obtiene estado de aplicación
POST | /transaction | genera transaccion recibiendo un requestParam de tipo string
GET | /accounts | Obtiene listado de cuentas de BD H2


## Hecho con

* [Maven](https://maven.apache.org/) - Administración de dependencias
* [Spring boot](https://spring.io/guides/gs/spring-boot/) - Framework

## Autor

* **Anderson Lopez** - *Trabajo inicial* - [Git](https://github.com/AsLopez)