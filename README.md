# QR-code-application

## Overview
This Spring Boot application allows you to generate and read QR codes.

## Dependencies
- Spring Boot
- ZXing (QR Code library)
- 
## Configuration

### QR Code Generation Strategy:
Easily configure and switch between different QR code generation strategies.

### Server Configuration:
HTTP/2 support, server compression, and encoding settings.

### Client-Side Caching
This application also supports client-side caching to improve the performance and load times for static resources.

- **Static Resource Caching**
Static resources, such as stylesheets, scripts, and images, benefit from client-side caching. By setting appropriate cache headers, the browser can store these resources locally, reducing the need to fetch them from the server on subsequent visits.

- **Cache Headers:**
  - The application is configured to set cache headers for static resources located in the `/static` path.
  - A cache period of one year is specified to ensure long-term caching, enhancing the user experience by minimizing resource fetching.
