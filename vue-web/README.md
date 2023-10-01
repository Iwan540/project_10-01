# A Web-based Tool for Managing Markers

## Overview
This project is a web-based tool designed to provide educational institutions with the capability to manage and allocate markers. It supports multiple roles, each having its unique interface and functionalities.

## Technologies Used
- **Frontend:** Vue 2 + Element UI
- **Backend:** Springboot
- **Database:** MySQL 8.0
- **JDK Version:** JDK 1.8
- **ORM:** MyBatis Plus
- **Authentication:** JWT

## Roles and Features

### Student - Priority: 5
- Submit applications
- Choose desired courses for marking
- Check application status
- View assigned courses and hours
- View notifications

### Marker Coordinator - Priority: 1
- Assign markers to courses
- Edit course
- View all courses and student details
- Email notifications
- Add courses
- Import courses from the previous semester
- Delete courses
- Flag new Marker
- Email functionalities
- Handle student applications (under consideration, reject, accept)
- Toggle course visibility
- Email dispatch
- Send course advertisements
- Send notifications

### Course Coordinator - Priority: 3
- Edit course details
- Modify course visibility
- Email dispatch
- Rank or flag applicants as "Not Suitable" (suitable, unsuitable, unflag)
- Send course advertisements
- Send notifications

### Marker - Priority: 6
- Upon re-login, the role changes

## Team Members & Responsibilities

### Member cheng
**Role:** Frontend Development & Frontend-Backend Data Bridging
- Develop frontend interface using Vue.js and Element UI
- Ensure smooth data flow between frontend and backend
- Implement JWT for authentication

### Member joyce yin
**Role:** Backend Development & API Design
- Design and implement RESTful API using Spring Boot and MyBatis Plus
- Provide API functionalities for different roles
- Implement JWT authentication and authorization

### Member Lujia Wang
**Role:** Database Design & System Testing
- Design and implement database structure using MySQL
- Perform comprehensive system testing
- Collaborate with other team members to ensure system quality

## Initial Setup
All new users, upon registration, are given the default role of "Student".

## Getting Started

## Backend Setup
Ensure you've set up the Spring Boot environment properly. Then run the application following the standard Spring Boot procedures.

### Frontend Setup

```bash
cd vue-web
npm install
npm run serve


