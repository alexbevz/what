# Проект Tinkoff VOgorode
![Bruuuuh](/image.webp)
___

## Запуск

* Среда: Gradle / Java / Jar / 17
* Требуется пробросить параметры `HANDYMAN_PORT`, `LANDSCAPE_PORT`и `RANCHER_PORT` - порты для Handyman, Landscape и Rancher cервисов соответственно.
  - Пример: `HANDYMAN_PORT=8081`
* Запуск сервиса в папке с именем ${FOLDER}: `cd ${FOLDER} && ./gradlew bootRun`. Возможные параметры:
  1. `handyman`
  2. `landscape`
  3. `rancher`

## Проделанная работа
* [Домашнее задание 1](docs/hw1)