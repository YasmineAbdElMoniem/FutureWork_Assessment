# Automation Framework :

A dual automation framework implementing **Rest-Assured for API testing** and **Selenium WebDriver for Web UI testing**,
featuring comprehensive validation, smart retry mechanisms, and Allure reporting.

---

## Table of Contents

- [Features](#star-features)
- [Prerequisites](#warning-prerequisites)
- [Setup](#gear-setup)
- [Test Execution](#rocket-test-execution)
- [Reports](#bar_chart-reports)
- [Framework Structure](#file_folder-framework-structure)
- [Documentation](#books-documentation)

---

## Features

### API Testing (Rest-Assured)

- Partner API endpoint testing
- Validation of status codes (200/400)
- Response time validation (<2000ms)
- Retry logic for network issues
- Data-driven testing with `TestDataProvider`

### Web Testing (Selenium)

- User authentication flows
- Page Object Model implementation
- Screenshot capture on failure and Success
- Test data management via JSON

---

## Prerequisites

- Java JDK 17+
- Maven 3.8+
- Chrome/Firefox browsers (Web tests)
- Selenium WebDriver
- Rest-Assured
- TestNG (for test execution)
- Java Faker
- Allure Reports
- Log4j2

---

## Setup

1. **Clone Repository**

```bash
git clone https://github.com/your-repo/automation-framework.git
cd automation-framework
