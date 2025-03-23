# Logger Library

## Overview

The Logger Library is a lightweight, configurable logging framework designed to meet a variety of logging needs. It supports multiple log levels and configurable logging destinations (sinks) such as file, console, and database. By default, logs are written to `logs/application.log`, and the library automatically rotates log files when they exceed a specified size.

## Features

- **Multiple Log Levels:** Supports `DEBUG`, `INFO`, `WARN`, `ERROR`, and `FATAL` levels.
- **Configurable Sinks:** Allows you to direct log messages to different destinations. For example, INFO logs can be written to a file while ERROR logs are sent to the console.
- **Threshold Filtering:** Only processes and logs messages that meet or exceed a specified log level threshold.
- **File Rotation:** Automatically rotates log files and creates compressed backups when the file size exceeds a configurable maximum.
- **Thread-Safe Logging:** Supports both synchronous and asynchronous logging modes to accommodate different application requirements.
- **Fluent Configuration:** Easily tune configuration by modifying a configuration map in the `Config` class. The builder pattern is used to construct the logging configuration in a clear, fluent manner.
- **Extensible Design:** Built using design patterns such as Builder, Factory, and Strategy, making it easy to add new sinks (e.g., for databases, MongoDB, Kafka) without modifying the core logic.
- **Custom Sink Mapping:** Supports mapping specific log levels to specific sink types, so you can, for example, direct INFO logs to one sink and ERROR logs to another.

## Getting Started

### Configure Logging

Configuration is managed through a configuration map located in the `Config` class. Modify this map to tune various aspects of the logger, including:

- Timestamp format
- Threshold log level
- Default sink type
- File sink settings (file location and maximum file size)
- Database connection settings (if using a DB sink)
- Specific sink mappings for each log level
- Optional settings for thread model and write mode (synchronous or asynchronous)

### Build the Configuration

A `ConfigLoader` loads the configuration from the `Config` class and uses the Builder pattern to create an immutable `LoggerConfig` instance.

### Using the Logger

Once configured, create a logger instance with the generated configuration. Use the logger to record messages, close it when your application terminates to release any allocated resources.

### Extensibility

The Logger Library is designed to be extended:
- **Adding New Sinks:** Implement the required sink interface to create custom logging sinks (such as for MongoDB or Kafka) and register them using the factory pattern.
- **Flexible Sink Mapping:** Configure different log levels to use different sinks. For example, you can set up the configuration so that INFO logs are written to a file while ERROR logs are sent to the console.
- **Customizing Behavior:** You can modify the configuration map to change default behavior, making it easy to adapt the logger to various environments or requirements.

## Default Behavior

- **Log Storage:** Logs are written to `logs/application.log` by default.
- **Rotation:** When the log file exceeds the configured size, it is rotated, and a compressed backup is created.
- **Filtering:** Only messages with a level equal to or higher than the specified threshold are logged.
- **Sink Mapping:** If a specific sink is not configured for a log level, the logger uses the default sink type.
