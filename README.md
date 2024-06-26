# LeaveFlow - Employee Leave Management System

LeaveFlow is a web application that simplifies the management of employee leave requests. It provides a smooth and efficient way to manage leaves, with an intuitive interface for employees to request leaves and for managers to efficiently review and approve them.

## Features
- **Secure Authentication**: Login system for both employees and managers.
- **Leave Requests**: Employees can submit leave requests with dates and reasons.
- **Approval Workflow**: Managers can approve or reject leave requests.
- **Leave Balance Tracking**: Tracks remaining leave days for each employee.
- **Notifications**: Email alerts for important actions like submission, approval, and rejection.
- **Admin Dashboard**: Manage employee details and view leave statistics.

## Technologies Used
- **Backend**: Java, Java EE (JDBC)
- **Database**: MySQL
- **Frontend**: HTML, CSS, JavaScript, Bootstrap

## Installation
To set up and run LeaveFlow, follow these steps:

1. **Clone the Repository:**
    ```sh
    git clone https://github.com/yourusername/leaveflow.git
    ```

2. **Set Up in Eclipse:**
    - Import the project into Eclipse IDE.
    - Configure the database connection in `src/db.properties`.

3. **Database Setup:**
    - Install MySQL and create a database named `leaveflow`.
    - Run the SQL script provided in `database/` to set up the necessary tables.

4. **Build and Run:**
    - Build the project in Eclipse and run it as a Java Application.

## Usage
- **Employee**: Log in to submit leave requests and check their status.
- **Manager**: Log in to review pending requests and approve or reject them.

## Contributing
We welcome contributions to improve LeaveFlow! If you'd like to contribute:
- Fork the repository,
- Create a new branch,
- Commit your changes,
- Submit a pull request.
  
