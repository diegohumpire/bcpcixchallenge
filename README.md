# BCP CIX Challenge

## Dependencias
- Gradle 7.3.3
- JDK 11

## Instalacion

1. Clona este repositorio por SSH o HTTPS

```shell
git clone https://github.com/diegohumpire/bcpcixchallenge.git
```

```shell
git clone git@github.com:diegohumpire/bcpcixchallenge.git
```

2. Ejecuta la construccion de la imagen de Docker

```shell
./docker_build.sh
```

3. Ejecuta la imagen de Docker. Nota: Puedes cambiar los puertos modificando el archivo

```shell
./docker_run.sh
```

## Autenticacion y Autorizacion

Nota: Puedes utilizar Postman para realizar las peticiones al API

Para obtener un `access_token` realizar un `POST` a `localhost:8080/api/auth`

Por defecto el usuario y contraseña es: `dhumpire` y `123456` respectivamente

Nota: Utilizar como cabezera `Content-Type` `application/x-www-form-urlencoded`

Ejemplo de Petición
```shell
curl --location --request POST 'localhost:8080/api/auth' \
    --header 'Content-Type: application/x-www-form-urlencoded' \
    --header 'Cookie: JSESSIONID=21C6D0457745A770CF89176E928CF94A' \
    --data-urlencode 'username=dhumpire' \
    --data-urlencode 'password=123456'
```

Ejemplo de Respuesta
```json
{
    "access_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaHVtcGlyZSIsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9hcGkvYXV0aCIsImV4cCI6MTY0MjA2MDMxMX0.mlYf5odUPBIyuURCC7rvSk9hWqLHdgYWyhQJF5s-vBM",
    "refresh_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaHVtcGlyZSIsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9hcGkvYXV0aCIsImV4cCI6MTY0MjA2NTcxMX0.sVt1Gf61zSs-4utBjIT75nicjGP91HaqA3DuEIG7czw"
}
```

## Calcular Tipo de Cambio

La moneda base para el tipo de cambio es en Euro `EUR`

Las monedas soportadas actualmente:

```
'EUR' -> Euro
'USD' -> Dolar 
'PEN' -> Sol Peruano
'ARS' -> Peso Argentino
'COP' -> Peso Colombiano
```

URL: `localhost:8080/api/exchange`

Method: `POST`

Headers: `Authorization`: `Bearer <JWT>`

Request:
- `amount`: `required`, `double`
- `from`: `required`, `string`, `Moneda inicial`
- `to`: `required`, `string`, `Moneda Objetivo`

Response:
- `from`: `required`, `string`, `Moneda inicial`
- `to`: `required`, `string`, `Moneda Objetivo`
- `exchangeRate`: Tipo de cambio calculado
- `amount`: `required`, `double`, `Monto inicial`
- `amountCalculated`: Monto resultado

Ejemplo de la petición

```shell
curl --location --request POST 'localhost:8080/api/exchange' \
    --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaHVtcGlyZSIsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9hcGkvYXV0aCIsImV4cCI6MTY0MjA2MDMxMX0.mlYf5odUPBIyuURCC7rvSk9hWqLHdgYWyhQJF5s-vBM' \
    --header 'Content-Type: application/json' \
    --header 'Cookie: JSESSIONID=21C6D0457745A770CF89176E928CF94A' \
    --data-raw '{
        "amount": 10,
        "from": "USD",
        "to": "ARS"
    }'
```

Ejemplo respuesta
```JSON
{
    "from": "USD",
    "to": "ARS",
    "exchangeRate": 103.5094969,
    "amount": 10.0,
    "amountCalculated": 1035.095
}
```


## Actualizar Tipo de Cambio

Para actualizar una moneda, utilizar lo siguiente:

URL: `localhost:8080/api/currencies/{Codigo de Moneda}`

Las monedas soportadas actualmente:

```
'EUR' -> Euro
'USD' -> Dolar 
'PEN' -> Sol Peruano
'ARS' -> Peso Argentino
'COP' -> Peso Colombiano
```

Method: `PUT`

Headers: `Authorization` : `Bearer <JWT>`

Body:
- `rate`: `required`, `double`

Response:
- `id`: `Identificador unico`
- `code`: `Codigo de la moneda`
- `base`: `Base de la moneda`
- `rate`: `Factor de cambio`

Ejemplo de la petición

Ejemplo

```shell
curl --location --request POST 'localhost:8080/api/currencies/PEN' \
    --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaHVtcGlyZSIsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9hcGkvYXV0aCIsImV4cCI6MTY0MjA2MDMxMX0.mlYf5odUPBIyuURCC7rvSk9hWqLHdgYWyhQJF5s-vBM' \
    --header 'Content-Type: application/json' \
    --header 'Cookie: JSESSIONID=21C6D0457745A770CF89176E928CF94A' \
    --data-raw '{
        "rate": 4.441277
    }'
```

```json
{
    "id": 3,
    "code": "PEN",
    "base": "EUR",
    "rate": 4.441288
}
```