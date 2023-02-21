# Проект Tinkoff VOgorode
![Bruuuuh](/image.webp)
___

## Запуск

* Среда: Gradle / Java / Jar / 17
* Требуется пробросить параметры `HANDYMAN_PORT`, `LANDSCAPE_PORT`и `RANCHER_PORT` - порты для Handyman, Landscape и Rancher cервисов соответственно.
  - Пример: `HANDYMAN_PORT=8081`
* Также требуется пробросить параметр `GRPC_PORT` для `gRPC` серверов (rancher, handyman)
  * Пример: `GRPC_PORT=9091`
* Запуск сервиса в папке с именем ${FOLDER}: `cd ${FOLDER} && ./gradlew bootRun`. Возможные параметры:
  1. `handyman`
  2. `landscape`
  3. `rancher`

## Проделанная работа
* [Домашнее задание 1](docs/hw1)
* [Домашнее задание 2](docs/hw2)