# AI-Powered Production Incident Root Cause Analysis Platform

## Overview

Modern applications generate thousands of logs every day. When a production issue occurs, engineers spend significant time searching through logs, identifying the root cause, and finding the appropriate resolution.

The AI-Powered Production Incident Root Cause Analysis Platform automates this process by collecting incident logs, processing them asynchronously using Apache Kafka, and leveraging Large Language Models (LLMs) through OpenRouter to generate intelligent incident analysis, root cause identification, severity assessment, and remediation recommendations.

The platform reduces manual troubleshooting effort and helps teams respond to production incidents faster.

---

## Problem Statement

In traditional systems:

* Production logs are generated continuously.
* Engineers manually investigate logs.
* Root cause analysis is time-consuming.
* Resolution depends on individual expertise.
* Similar incidents may be investigated repeatedly.

This platform automates incident analysis using AI and provides instant recommendations.

---

## Key Features

* Real-time incident log ingestion
* Apache Kafka based event-driven architecture
* AI-powered incident analysis
* Automatic root cause identification
* Severity classification
* Recommendation generation
* Duplicate incident detection
* REST APIs for incident management
* MySQL persistence
* Structured logging
* Exception handling and validation

---

## Technology Stack

| Component         | Technology      |
| ----------------- | --------------- |
| Backend           | Java 17         |
| Framework         | Spring Boot     |
| Messaging         | Apache Kafka    |
| Database          | MySQL           |
| AI Integration    | OpenRouter      |
| AI Model          | GPT-OSS-120B    |
| API Documentation | Swagger/OpenAPI |
| Build Tool        | Maven           |
| Logging           | Logback         |
| Testing Tool      | Postman         |

---

## High-Level Architecture

```text
Client Application
        |
        v
POST /api/logs
        |
        v
Spring Boot API
        |
        v
Apache Kafka Topic
(incident-logs)
        |
        v
Kafka Consumer
        |
        +-------------------+
        |                   |
        v                   v
MySQL Incident Log     OpenRouter AI
Database               GPT-OSS-120B
        |                   |
        +---------+---------+
                  |
                  v
Incident Analysis
Database
                  |
                  v
GET /api/analysis/{id}
```

---

## How the Platform Works

### Step 1: Incident Received

An application sends a production incident log.

Example:

```json
{
  "traceId": "TR008",
  "correlationId": "COR008",
  "serviceName": "payment-service",
  "serviceVersion": "1.1",
  "hostName": "HOST02",
  "environment": "PROD",
  "logLevel": "ERROR",
  "logMessage": "Database Connection Timeout",
  "exceptionName": "SQLTimeoutException",
  "stackTrace": "Sample Stack Trace"
}
```

### Step 2: Incident Validation

The platform checks whether the same Trace ID and Correlation ID already exist.

### Step 3: Kafka Processing

Valid incidents are published to Kafka Topic:

```text
incident-logs
```

### Step 4: Incident Storage

The incident is stored in MySQL.

### Step 5: AI Analysis

The Kafka consumer invokes OpenRouter AI.

The AI analyzes:

* Log message
* Exception
* Environment
* Stack trace
* Service information

### Step 6: Root Cause Analysis

The AI generates:

* Severity
* Root Cause
* Recommendation
* Summary

### Step 7: Analysis Retrieval

Users can retrieve analysis using REST APIs.

---

## Prerequisites

Before running the application ensure the following software is installed:

### Java

```text
Java 17+
```

### Maven

```text
Maven 3.9+
```

### MySQL

```text
MySQL 8+
```

### Apache Kafka

```text
Kafka 3.x+
```

### OpenRouter Account

Generate an API key from:

https://openrouter.ai

---

## Application Configuration

### application-LOCAL.yml

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/incidents_platform
    username: root
    password: "****"

  kafka:
    bootstrap-servers: localhost:9092

openrouter:
  api:
    url: https://openrouter.ai/api/v1/chat/completions
    key: ${OPENROUTER_API_KEY}
    model: openai/gpt-oss-120b:free

server:
  port: 8181
```

---

## API Specifications

### 1. Create Incident

#### Endpoint

```http
POST /api/logs
```

#### Request

```json
{
  "traceId": "TR008",
  "correlationId": "COR008",
  "serviceName": "payment-service",
  "serviceVersion": "1.1",
  "hostName": "HOST02",
  "environment": "PROD",
  "logLevel": "ERROR",
  "logMessage": "Database Connection Timeout",
  "exceptionName": "SQLTimeoutException",
  "stackTrace": "Sample Stack Trace"
}
```

#### Success Response

```json
{
  "status": "SUCCESS",
  "message": "Log published successfully and completed for AI analysis with Incident ID : 10",
  "incidentId": 10
}
```

#### Duplicate Response

```json
{
  "status": "DUPLICATE",
  "message": "Incident already exists for TraceId : TR008 and CorrelationId : COR008",
  "incidentId": 10
}
```

---

### 2. Get Incident Analysis

#### Endpoint

```http
GET /api/analysis/{incidentId}
```

#### Example

```http
GET /api/analysis/10
```

#### Response

```json
{
  "incidentId": 10,
  "severity": "CRITICAL",
  "rootCause": "Database connection pool exhaustion",
  "recommendation": "Increase pool size and investigate slow queries",
  "summary": "Payment service failed due to database timeout.",
  "modelName": "openai/gpt-oss-120b:free",
  "analysisStatus": "SUCCESS"
}
```

---

### 3. Invalid Incident ID

#### Request

```http
GET /api/analysis/99999
```

#### Response

```json
{
  "status": 404,
  "message": "Analysis not found for incident id : 99999"
}
```

---

## cURL Commands

### Create Incident

```bash
curl --location 'http://localhost:8181/api/logs' \
--header 'Content-Type: application/json' \
--data '{
    "traceId":"TR008",
    "correlationId":"COR008",
    "serviceName":"payment-service",
    "serviceVersion":"1.1",
    "hostName":"HOST02",
    "environment":"PROD",
    "logLevel":"ERROR",
    "logMessage":"Database Connection Timeout",
    "exceptionName":"SQLTimeoutException",
    "stackTrace":"Sample Stack Trace"
}'
```

### Get Incident Analysis

```bash
curl --location 'http://localhost:8181/api/analysis/10'
```

---

## Database Tables

### incident_log

Stores raw production incidents.

Important fields:

* trace_id
* correlation_id
* service_name
* log_level
* log_message
* exception_name
* stack_trace

### incident_analysis

Stores AI-generated analysis.

Important fields:

* incident_id
* severity
* root_cause
* recommendation
* ai_summary
* model_name
* analysis_status

---

## Sample AI Analysis

### Input

```text
Database Connection Timeout
SQLTimeoutException
```

### Output

```text
Severity:
CRITICAL

Root Cause:
Database connection pool exhaustion

Recommendation:
Increase connection pool size and investigate slow queries

Summary:
Payment service failed due to database timeout and inability to acquire database connections.
```

---

## Future Enhancements

* Incident dashboard
* Real-time monitoring
* Email notifications
* Slack integration
* Multi-model AI support
* Grafana dashboards
* Prometheus metrics
* Kubernetes deployment
* Automated remediation workflows

---

## Benefits

* Faster incident investigation
* Reduced manual effort
* Consistent root cause analysis
* Improved operational efficiency
* Better production observability
* AI-assisted troubleshooting

---

## Author

**Tejas Choudhari**

Software Developer | Java | Spring Boot | Kafka | AI Integration
