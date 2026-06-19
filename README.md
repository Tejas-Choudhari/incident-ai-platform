# AI-Powered Production Incident Root Cause Analysis Platform

## Overview

Modern distributed applications generate thousands of logs, events, and alerts every Phase. During production incidents, engineers often spend significant time manually analyzing logs, identifying root causes, assessing impact, and recommending remediation steps.

The AI-Powered Production Incident Root Cause Analysis Platform is designed to automate this process by leveraging Event-Driven Architecture, Apache Kafka, Spring Boot, MySQL, and Generative AI.

The platform ingests logs in real-time, processes them through Kafka, stores them in a centralized repository, and utilizes a locally hosted Large Language Model (LLM) via Ollama to generate:

- Root Cause Analysis (RCA)
- Incident Severity Classification
- Incident Summaries
- Remediation Recommendations

This project demonstrates the integration of modern backend technologies with AI to improve operational efficiency and reduce Mean Time To Resolution (MTTR).

---

## Problem Statement

Production support teams face challenges such as:

- High volumes of application logs
- Manual root cause analysis
- Slow incident response
- Lack of automated recommendations
- Difficulty correlating failures across services

This platform addresses these challenges by automating incident analysis using AI.

---

## Objectives

- Build a scalable event-driven backend platform
- Process logs asynchronously using Kafka
- Store and manage incident data efficiently
- Utilize AI for automated log analysis
- Generate actionable incident insights
- Demonstrate AI integration within enterprise backend systems

---

## Key Features

### Log Ingestion

- Accept application logs through REST APIs
- Publish logs to Kafka topics
- Enable asynchronous processing

### Incident Management

- Store incident-related information
- Track occurrences and affected services
- Maintain incident lifecycle information

### AI-Powered Analysis

- Root cause identification
- Severity classification
- Incident summarization
- Remediation recommendations

### Event-Driven Architecture

- Kafka Producer
- Kafka Consumer
- Real-time log processing

### Documentation

- Swagger/OpenAPI integration
- Architecture diagrams
- API documentation

---

## Architecture

```text
Application Logs
        │
        ▼
REST API
        │
        ▼
Kafka Producer
        │
        ▼
Kafka Topic
        │
        ▼
Kafka Consumer
        │
        ▼
MySQL Database
        │
        ▼
AI Analysis Service
        │
        ▼
Ollama (Phi3 Mini)
        │
        ▼
Incident Analysis Report
```

---

## Technology Stack

### Backend

- Java 21
- Spring Boot 3.x
- Spring Data JPA
- Spring Validation

### Messaging

- Apache Kafka

### Database

- MySQL

### AI

- Spring AI
- Ollama
- Phi3 Mini Model

### Documentation

- Swagger / OpenAPI

### Build Tool

- Maven

### Development Tools

- IntelliJ IDEA
- Git
- GitHub

---

## Database Design

### incident_log

Stores all incoming application logs.

| Column | Description |
|----------|-------------|
| id | Primary Key |
| trace_id | Distributed trace identifier |
| correlation_id | Request correlation identifier |
| service_name | Source service |
| service_version | Service version |
| host_name | Host machine |
| environment | DEV/UAT/PROD |
| log_level | INFO/WARN/ERROR |
| log_message | Log content |
| exception_name | Exception type |
| stack_trace | Complete stack trace |
| source_topic | Kafka topic |
| event_timestamp | Original event timestamp |
| created_date | Record creation timestamp |

---

### incident

Stores detected incidents.

| Column | Description |
|----------|-------------|
| id | Primary Key |
| incident_number | Unique incident number |
| title | Incident title |
| status | Current status |
| severity | Incident severity |
| impacted_service | Affected service |
| root_cause | Identified root cause |
| summary | Incident summary |
| occurrence_count | Number of occurrences |
| first_occurrence | First occurrence timestamp |
| last_occurrence | Last occurrence timestamp |
| assigned_to | Incident owner |
| environment | Environment |
| created_date | Creation timestamp |
| updated_date | Last update timestamp |

---

### incident_analysis

Stores AI-generated analysis.

| Column | Description |
|----------|-------------|
| id | Primary Key |
| incident_id | Related incident |
| ai_model | AI model used |
| prompt_used | Analysis prompt |
| root_cause | AI-generated RCA |
| summary | AI-generated summary |
| recommendation | Suggested remediation |
| confidence_score | AI confidence level |
| severity | Predicted severity |
| analyzed_at | Analysis timestamp |

---

## Project Structure

```text
incident-ai-platform

├── docs
│   ├── Architecture_diagram.png
│
├── src
│   └── main
│       ├── java
│       │   └── incident.platform.service
│       │       ├── controller
│       │       ├── service
│       │       ├── repository
│       │       ├── entity
│       │       ├── dto
│       │       ├── kafka
│       │       ├── ai
│       │       ├── config
│       │       └── exception
│       │
│       └── resources
│           └── application.yml
│
├── docker
├── pom.xml
└── README.md
```

---

## Future Enhancements

- Grafana Dashboard Integration
- Redis Caching
- Vector Database Integration
- Incident Similarity Search
- RAG-Based Knowledge Retrieval
- Email Notifications
- Slack / Teams Integration
- Multi-Service Deployment
- Docker Compose Setup
- Kubernetes Deployment

---

## API Endpoints (Planned)

### Log Ingestion

```http
POST /api/logs
```

### Get All Incidents

```http
GET /api/incidents
```

### Get Incident By ID

```http
GET /api/incidents/{id}
```

### Analyze Incident

```http
POST /api/incidents/analyze
```

---

## Local Setup

### Start Kafka

```bash
zookeeper-server-start.bat config/zookeeper.properties
```

```bash
kafka-server-start.bat config/server.properties
```

### Start Ollama

```bash
ollama run phi3:mini
```

### Start Application

```bash
mvn spring-boot:run
```

---

## Current Development Status

### Phase 1

- [x] Repository Setup
- [x] Spring Boot Setup
- [x] Kafka Setup
- [x] MySQL Setup
- [x] Ollama Setup
- [x] Database Design
- [x] Architecture Documentation

### Phase 2

- [ ] Kafka Producer
- [ ] Kafka Consumer
- [ ] Log Persistence
- [ ] Swagger Integration

### Phase 3

- [ ] AI Analysis Service
- [ ] Incident Analysis APIs
- [ ] End-to-End Testing
- [ ] Demo Video

---

## Author

**Tejas Tanaji Choudhari**

Backend Software Developer

Java | Spring Boot | Kafka | MySQL | MongoDB | Redis | Microservices | AI Integration