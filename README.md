# IzzoBio App - API
This is a Spring Boot application with integrated security features, including OAuth2 authentication using Google and GitHub. The application is configured to interact with a PostgreSQL database and includes email functionalities using SMTP.

## Features
- OAuth2 login with Google and GitHub
- User role management with default admin user setup
- Email sending configured with SMTP using Thymeleaf templates
- Job scheduling and background job processing with JobRunr
- Database interactions using JPA and Hibernate

## Setup configuration
Put these configuration in your environment variable

- ### Database configuration:
  - `DATABASE_URL`: URL of the PostgreSQL database.
  - `DATABASE_USERNAME`: Username for the PostgreSQL database.
  - `DATABASE_PASSWORD`: Password for the PostgreSQL database.
  
- ### Application Configuration:
  - `APP_BASE_URL`: Base URL of the front app you use.
  - `ADMIN_USER_NAME`: Default admin username.
  - `ADMIN_USER_EMAIL`: Default admin email.
  - `ADMIN_USER_PASSWORD`: Default admin password.

- ### Google OAuth2 Configuration:
  - `GOOGLE_CLIENT_ID`: Client ID for Google OAuth2.
  - `GOOGLE_CLIENT_SECRET`: Client secret for Google OAuth2.

- ### Email Configuration:
    - `MAIL_HOST`: SMTP server host.
    - `MAIL_USERNAME`: SMTP server username.
    - `MAIL_PASSWORD`: SMTP server password.