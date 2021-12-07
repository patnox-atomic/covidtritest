## Covid Triage and Testing (covidtritest)

- Backend  => Java/SpringBoot
- Frontend => Javascript/VueJs

The purpose of this is to test your ability and proficiency in Software design and development.
Kindly add appropriate unit tests for the functions

### Assignment:
- You are required to answer all the exercises below. Marks will be awarded for clarity,
comments and ingenuity.
- You are NOT expected to submit a production-ready application. We do understand you
may have other commitments and may not have enough time. However, you are
encouraged to submit.
- Ensure that the source code is written by you (excluding libraries)

### Question:
You work at the front desk of a community health facility. You are responsible for performing
preliminary screening for covid-19 symptoms in patients. Your job involves:
a. Collecting vital signs and screening for COVID-19 symptoms
b. Assigning patients to COVID-19 testing queues – focus on the queue design. The
implementation should only provide for a simple add/remove from queue
c. Generating a report (simple web view) of those screened for COVID-19

### Tasks:
Using a language/framework of your choice, preferably java/javascript and MVC based, develop
a web-based application that addresses the above requirements. Your app should:

a. Register patients
b. Triage and record COVID-19 sysmptoms
c. Post data to an API endpoint (backend) – Use of a database for persistence is optional
d. Provide a basic report (simple table) on those screened for COVID-19

### Solution

#### Backend
#### Build and Run

./mvnw spring-boot:run
./mvnw clean
./mvnw clean spring-boot:run
./mvnw clean install -- build and install on local machine
./mvnw clean package -- build and package
./mvnw clean test -- test the app

#### Frontend
#### Build and Run

npm install -- install dependencies
npm run dev -- run app
npm run test:unit -- run tests
npm run build -- build for deployment


