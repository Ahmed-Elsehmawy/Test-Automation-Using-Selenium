# 🚀 Test Automation Framework

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)](https://github.com/your-username/TestAutomationFramework)
[![Selenium](https://img.shields.io/badge/selenium-4.35.0-orange)](https://selenium.dev)
[![TestNG](https://img.shields.io/badge/testng-7.11.0-red)](https://testng.org)
[![Java](https://img.shields.io/badge/java-11%2B-blue)](https://java.com)
[![Maven](https://img.shields.io/badge/maven-3.6.0%2B-blue)](https://maven.apache.org)

A robust **Selenium + TestNG** hybrid framework built in Java.  
Supports **parallel cross-browser execution**, **data-driven testing (Excel)**, **logging**, and **rich reporting** (ExtentReports + ReportNG).

---

## 📌 Features

- ✅ **Cross-browser support**: Chrome, Firefox, Edge  
- ✅ **Parallel execution** with TestNG  
- ✅ **Data-driven testing** using Excel (Apache POI)  
- ✅ **Reusable BaseTest** with thread-safe WebDriver management  
- ✅ **Custom Listeners** for screenshot capture on success/failure  
- ✅ **Config-driven waits & validations** (timeout, result count, etc.)  
- ✅ **Reports**:  
  - ExtentReports 5.1.1 (`/reports`)  
  - ReportNG 1.1.4 (`/test-output/html`)  
- ✅ **Logging** with Log4j2 2.25.1
- ✅ **Automatic driver management** with WebDriverManager 6.3.1

---

## 📂 Project Structure

```
TestAutomationFramework/
│── src/
│   ├── main/java/
│   └── test/java/
│       ├── base/              # BaseTest, Listeners
│       ├── testcase/          # Test classes
│       └── utilities/         # Utils (ScreenshotUtil, readXLSdata, etc.)
│── src/test/resources/
│   ├── configfiles/           # config.properties, locators.properties
│   └── testdata/              # testdata.xlsx
│── reports/                   # ExtentReports output
│── screenshots/               # Captured screenshots
│── pom.xml
│── testng.xml
│── README.md
```

---

## ⚙️ Setup & Installation

### Prerequisites
- Java 11 or higher
- Maven 3.6.0 or higher
- Git

### Installation Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/TestAutomationFramework.git
   cd TestAutomationFramework
   ```

2. Import as a **Maven Project** into your IDE (Eclipse / IntelliJ).

3. Run Maven install to download dependencies:
   ```bash
   mvn clean install
   ```

---

## 🖥️ How to Run Tests

### Run via TestNG XML:
```bash
mvn test -DsuiteXmlFile=testng.xml
```

### Run specific test classes:
```bash
mvn test -Dtest=SearchTest
```

### Run with different browser:
```bash
mvn test -Dbrowser=firefox
```

### Run from IDE:
Right-click `testng.xml` → **Run as TestNG Suite**.

---

## 🌐 Browser Configuration

Update the browser and base URL in **`src/test/resources/configfiles/config.properties`**:

```properties
# Browser: chrome | firefox | edge
browser = chrome

# Base URL for tests
testurl = https://www.google.com

# WebDriver wait timeout (seconds)
wait_timeout_seconds = 40

# Minimum expected related search sections
min_related_sections = 4
```

---

## 📊 Reports

### ExtentReports 5.1.1
- Location: `/reports/Extent_<timestamp>.html`
- Feature-rich interactive HTML reports with charts and screenshots

### ReportNG 1.1.4
- Location: `/test-output/html/index.html`
- Clean, simple HTML reports integrated with TestNG

---

## 📸 Screenshots

- Captured automatically on **test failure/success**
- Saved under `/screenshots` directory
- Embedded directly into ExtentReports

---

## 📗 Data-Driven Testing

- Excel file: `src/test/resources/testdata/testdata.xlsx`
- Powered by Apache POI 5.4.1 for Excel handling
- Each test method maps to a sheet with the same name
- Example: `SearchTest` → reads from `SearchTest` sheet

---

## 🔧 Dependencies

Managed via `pom.xml` with verified versions:

| Dependency | Version | Purpose |
|------------|---------|---------|
| Selenium Java | 4.35.0 | Browser automation |
| TestNG | 7.11.0 | Test framework |
| ReportNG | 1.1.4 | Enhanced reporting |
| ExtentReports | 5.1.1 | Advanced reporting |
| Log4j2 Core | 2.25.1 | Logging framework |
| Log4j2 API | 2.25.1 | Logging API |
| Apache POI | 5.4.1 | Excel handling |
| POI OOXML | 5.4.1 | Excel OOXML support |
| POI OOXML Schemas | 4.1.2 | Excel schema support |
| XMLBeans | 5.3.0 | XML processing |
| Commons Collections4 | 4.5.0 | Collection utilities |
| WebDriverManager | 6.3.1 | Automatic driver management |
| Commons IO | 2.20.0 | File operations |
| Google Guice | 5.1.0 | Dependency injection |
| Velocity Engine | 2.3 | Template engine |

---

## 🧪 Test Examples

The framework includes sample tests demonstrating:
- Basic search functionality
- Data-driven testing
- Cross-browser compatibility
- Screenshot capture
- Custom assertions and validations

---

## 🤝 Contributing

We welcome contributions! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

Please ensure your code follows existing patterns and includes appropriate tests.

---

## 🔄 Continuous Integration

This framework can be easily integrated with CI/CD tools like:
- Jenkins
- GitHub Actions
- GitLab CI
- TeamCity

Sample Jenkins pipeline scripts are available in the `jenkins/` directory.

---

*This framework is maintained by [Ahmed_Elsehmawy].*
