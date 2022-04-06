Тестовый фреймворк

Проект собирается через Maven. Что бы запустить Docker с базой данных Postgres нужно в корне проекта выполнить:

`docker-compose -f .\local_env\docker-compose.yml up -d`

Тестовые сценарии и их запуск из файла `"steps_definition.feature"`

Функционал тестовх шагов описан в файле `"StepsDefinition"`

Запросы в базу данных реализованы в файле `"DatabaseManager"`