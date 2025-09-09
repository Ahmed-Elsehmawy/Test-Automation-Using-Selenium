# 🚀 Test Automation Framework

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
  - ExtentReports (`/reports`)  
  - ReportNG (`/test-output/html`)  

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

````

---

## ⚙️ Setup & Installation

1. Clone the repo:
   ```bash
   git clone https://github.com/your-username/TestAutomationFramework.git
````

2. Import as a **Maven Project** into your IDE (Eclipse / IntelliJ).

3. Run Maven install to download dependencies:

   ```bash
   mvn clean install
   ```

---

## 🖥️ How to Run Tests

* Run via **TestNG XML**:

  ```bash
  mvn test -DsuiteXmlFile=testng.xml
  ```

* Or directly from IDE:
  Right-click `testng.xml` → **Run as TestNG Suite**.

---

## 🌐 Browser Configuration

Update the browser and base URL in **`config.properties`**:

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

* **ExtentReports**: Generated under `/reports/Extent_<timestamp>.html`
* **ReportNG HTML**: Check `/test-output/html`

---

## 📸 Screenshots

* Captured automatically on **test failure/success**.
* Saved under `/screenshots`.

---

## 📗 Data-Driven Testing

* Excel file: `src/test/resources/testdata/testdata.xlsx`
* Each test method maps to a sheet with the same name.
* Example: `SearchTest` → reads from `SearchTest` sheet.

---

## 🔧 Dependencies

Managed via `pom.xml`:

* **Selenium Java 4.35.0**
* **TestNG 7.11.0**
* **ReportNG**
* **ExtentReports 5.1.1**
* **Log4j2**
* **Apache POI**
* **WebDriverManager**
* **Commons-IO**

---

## 🤝 Contributing

1. Fork the repo
2. Create a new branch (`feature/your-feature`)
3. Commit changes and push
4. Create a Pull Request


