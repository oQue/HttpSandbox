# Simple SpringBoot application for HTTP calls

Application provides a single rest controller mapped to the root ("/") supporting `GET`, `POST`, `PUT` and `DELETE` methods.

An example request using curl:
```bash
curl -X POST -d '{"a": "b"}' -b "name_1=value_1;name_2=value_2" -H "Content-Type: application/json" localhost:8080
```

An example response from endpoint:
```json
{
  "cookies" : {
    "name_1" : "value_1",
    "name_2" : "value_2"
  },
  "data" : "{\"a\": \"b\"}\n",
  "headers" : {
    "Accept" : "*/*",
    "Content-Length" : "10",
    "Content-Type" : "application/json",
    "Cookie" : "name_1=value_1;name_2=value_2",
    "Host" : "httpsandbox.appspot.com",
    "User-Agent" : "curl/7.54.0"
  },
  "method" : "POST",
  "origin" : "127.0.0.1"
}
```

Application is written to be deployed on Google App Engine (Standard plan).

