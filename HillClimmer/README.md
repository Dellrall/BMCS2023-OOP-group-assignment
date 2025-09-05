# 🏔️ HillClimber Malaysia Vehicle Rental System

## 📋 Overview
A comprehensive Java-based vehicle rental system designed specifically for Malaysia's hill climbing market. Features secure authentication, role-based access control, and complete rental lifecycle management with Malaysian-specific validations.

## ✨ Key Features

### 🔐 **Advanced Security & Authentication**
- **SHA-256 Password Hashing** with unique salts for all users
- **Role-Based Access Control** with 5 authorization levels
- **Manager Authentication** via secure CSV database
- **Secure Session Management** with personalized welcome messages

### 👥 **Multi-User System**
- **Customer Portal**: Registration, login, booking, payments
- **Manager Portal**: Vehicle & rental management with permissions
- **Personalized Experience**: Custom welcome messages for each user

### 🚗 **Complete Vehicle Management**
- **Diverse Fleet**: Mountain bikes, dirt bikes, buggies, crossovers
- **Real-time Inventory**: Track availability and maintenance
- **Manager Controls**: Add, remove, update vehicles (permission-based)

### 📅 **Rental Lifecycle Management**
- **Smart Booking**: Automated cost calculation and availability checking
- **Duration Tracking**: Timer system with automated reminders
- **Payment Integration**: Multiple payment methods with receipts

### 🇲🇾 **Malaysia-Specific Features**
- **IC Validation**: Malaysian IC format (XXXXXX-XX-XXXX)
- **Phone Validation**: +60XXXXXXXXX or 0XXXXXXXXX formats
- **License Types**: B, B2, D, DA, E, E1, E2 validation
- **Age Calculation**: Automatic age from IC number
- **Location Support**: Malaysian addresses and regions

## 🚀 Quick Start

### Prerequisites
- Java 21 or higher
- NetBeans IDE (recommended) or any Java IDE

### Running the Application

#### Method 1: NetBeans IDE (Recommended)
1. Open the project in NetBeans
2. Navigate to `src/hillclimmer/HillClimmer.java`
3. Right-click → "Run File" (Shift+F6)
4. Choose your user type and follow the prompts

#### Method 2: Command Line
```bash
cd HillClimber
javac -cp src src/hillclimmer/*.java src/hillclimmer/*/*.java
java -cp src hillclimmer.HillClimmer
```

## 👤 User Accounts & Credentials

### 👥 Customer Accounts
| Customer ID | Name | IC Number | Password |
|-------------|------|-----------|----------|
| C001 | Muhammad Ali | 950101-14-5678 | password123 |
| C002 | Jeremy Clarkson | 850101-01-1234 | TopGear2025! |
| C003 | Richard Hammond | 880202-02-2345 | HammondRacing! |
| C004 | James May | 820303-03-3456 | CaptainSlow! |
| C005 | Sabine Schmitz | 790404-04-4567 | QueenOfNurburgring! |
| C006 | Chris Evans | 860505-05-5678 | TopGearHost! |

### 👨‍💼 Manager Accounts
| Manager ID | Name | Password | Level | Permissions |
|------------|------|----------|-------|-------------|
| VM002 | Chin Wen Wei | Manager123! | 1 | View Only |
| VM003 | Lye Wei Lun | SecurePass456! | 5 | Full Admin |
| VM004 | Neeshwran A/L Veera Chelvan | Nurburg2025! | 3 | Standard Admin |
| VM005 | Oscar Lim Zheng You | OscarRacing! | 4 | Advanced Admin |
| VM006 | Teh Guan Chen | TehSecure789! | 5 | Full Admin |

## 🔐 Authorization Levels

| Level | Permissions | Description |
|-------|-------------|-------------|
| **1** | View Only | Can view vehicles and rentals |
| **2** | Basic Admin | Can add/remove vehicles and rentals |
| **3** | Standard Admin | All CRUD operations on vehicles |
| **4** | Advanced Admin | Full access + admin functions |
| **5** | Super Admin | Complete system control |

## 📁 Project Structure

```
HillClimber/
├── src/hillclimmer/
│   ├── HillClimmer.java          # Main application
│   ├── CustomerModule/
│   │   ├── Customer.java         # Customer management
│   │   └── SafetyCheck.java      # Safety quiz system
│   ├── VehicleModule/
│   │   ├── Vehicle.java          # Abstract vehicle class
│   │   ├── VehicleManager.java   # Vehicle inventory
│   │   └── [Vehicle types...]    # Bike, buggy classes
│   ├── RentalModule/
│   │   ├── Rental.java           # Rental processing
│   │   └── RentalManager.java    # Rental management
│   ├── PaymentModule/
│   │   ├── Payment.java          # Payment processing
│   │   └── TransactionManager.java # Transaction tracking
│   ├── DurationModule/
│   │   ├── DurationManager.java  # Timer system
│   │   └── Reminder.java         # Reminder system
│   └── DatabaseModule/
│       ├── Manager.java          # Manager authentication
│       ├── ManagerDAO.java       # Manager data access
│       └── [DAO classes...]      # Data persistence
├── data/
│   ├── customers.csv             # Customer database
│   ├── managers.csv              # Manager database
│   ├── vehicles.csv              # Vehicle inventory
│   ├── rentals.csv               # Rental records
│   └── payments.csv              # Payment history
└── test/
    └── [Test classes...]         # Unit tests
```

## 🎯 Application Workflow

### For Customers:
1. **Register** or **Login** with personalized welcome
2. **Complete Safety Check** (required for rentals)
3. **Browse Available Vehicles** with real-time inventory
4. **Book Rental** with automatic cost calculation
5. **Process Payment** via multiple methods
6. **View Profile** and rental history

### For Managers:
1. **Login** with role-based authentication
2. **Access Dashboard** with personalized greeting
3. **Manage Vehicles**: Add, remove, update inventory
4. **Manage Rentals**: View, add, remove bookings
5. **View Customers**: Access customer database
6. **System Reports**: Analytics and statistics

## 🛡️ Security Features

- **Password Hashing**: SHA-256 with unique salts
- **Session Security**: Secure user sessions
- **Input Validation**: Malaysian format validations
- **Access Control**: Role-based permissions
- **Data Encryption**: Secure credential storage

## 📊 System Statistics

- **6 Sample Customers** with complete profiles
- **5 Manager Accounts** with different authorization levels
- **70+ Vehicles** across multiple categories
- **CSV-Based Storage** for all data persistence
- **Real-time Updates** for inventory and bookings

## 🐛 Troubleshooting

### Common Issues:
- **Compilation Errors**: Ensure Java 21+ and correct classpath
- **File Not Found**: CSV files are auto-created in `/data/` folder
- **Login Issues**: Check credentials in managers.csv/customers.csv
- **Permission Errors**: Verify manager authorization levels

### Debug Mode:
```bash
# Enable verbose logging
java -cp src -Djava.util.logging.level=INFO hillclimmer.HillClimber
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new features
5. Submit a pull request

## 📄 License

This project is developed for educational purposes as part of the OOP course assignment.

## 👥 Team Members

- **Chin Wen Wei** (VM002) - Project Lead
- **Lye Wei Lun** (VM003) - System Architect
- **Neeshwran A/L Veera Chelvan** (VM004) - Security Specialist
- **Oscar Lim Zheng You** (VM005) - Database Administrator
- **Teh Guan Chen** (VM006) - Quality Assurance

---

**🏔️ Ready to explore Malaysia's hill climbing adventures with HillClimber! 🚀**
