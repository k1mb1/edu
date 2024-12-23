**Проект: Система для управления онлайн-курсами**

### **Описание проекта:**

Разработан REST API для платформы управления онлайн-курсами, включающей функционал для аутентификации и авторизации
пользователей, создания курсов, управления доступом при помощи ролей.

---

### **Основной функционал:**

1. **Управление курсами**:
    - CRUD-операции для курсов (создание, чтение, обновление, удаление).
    - Привязка курсов к Id пользователя(автору).

2. **Управление пользователями и ролями**:
    - Разделение пользователей по ролям: *Администратор*, *Пользователь*.
    - Ограничение доступа к API на основе ролей (RBAC - Role-Based Access Control).

3. **Безопасность**:
    - Использование **Keycloak** как центра аутентификации и авторизации.
    - Настройка приложения как **Resource Server** с поддержкой OAuth2.
    - Интеграция с **Keycloak** для проверки JWT токенов и управления ролями.

4. **Документирование API**:
    - Настройка и генерация документации REST API с использованием **Swagger/OpenAPI** для удобства взаимодействия с
      другими разработчиками.

---

### **Технологический стек:**

- **Back-end**:
    - **Spring Boot**: основной фреймворк для разработки приложения.
    - **Spring Security**: защита API и проверка токенов от Keycloak.
    - **Spring Data JPA**: взаимодействие с базой данных (PostgreSQL).

- **Интеграция и безопасность**:
    - **Keycloak**: аутентификация и авторизация, настройка ролей и управления доступом.
    - **OAuth2**: использование Bearer токенов для защиты API.

- **Документирование**:
    - **Swagger/OpenAPI**: документирование всех эндпоинтов для удобства тестирования и разработки.

- **База данных**:
    - **PostgreSQL**: хранение данных о пользователях, ролях, курсах и других сущностях.

- **Контейнеризация**:
    - **Docker**: развёртывание Keycloak и базы данных в контейнерах.