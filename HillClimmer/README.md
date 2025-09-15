# 🏔️ HillClimmer Malaysia Vehicle Rental System

**Version 2.3** | *Last Updated: September 15, 2025*

## 📋 Overview
A comprehensive **cross-platform Java-based** vehicle rental system designed specifically for Malaysia's hill climbing market. Features secure authentication, role-based access control, and complete rental lifecycle management with Malaysian-specific validations. **Compatible with Windows, Linux, and macOS**.

**✨ Recent Updates**: Enhanced credit card validation with reinput loops, admin bulk rental deletion, improved menu organization, rental date conflict prevention, and comprehensive bug fixes for enhanced user experience and system reliability.

---

## 📖 Documentation

- **[📚 Comprehensive User Guide](UserGuide.md)** - Complete guide with test accounts, portal tutorials, safety questions, and troubleshooting
- **[🐛 Bug Tracking](bugs.md)** - Known issues and resolution status  
- **[📋 Requirements](requirements.txt)** - System dependencies and setup requirements

---

## 📋 Changelog

### Version 2.3 (September 15, 2025) - Advanced Features Release
- ✅ **Credit Card Validation**: Added comprehensive input validation with reinput loops for card number (16 digits), card holder name (letters/spaces only), expiry date (MM/YY format with validity checks), and CVV (3 digits)
- ✅ **Admin Bulk Operations**: Implemented loop for admin rental deletion allowing multiple deletions in one session
- ✅ **Menu Organization**: Reorganized manager menu into 5 logical subsections to reduce crowding and improve navigation
- ✅ **Rental Conflict Prevention**: Added date overlap validation to prevent double-booking same vehicle on same dates
- ✅ **Enhanced Rental Display**: Improved admin view rentals to group by vehicle and show different dates clearly
- ✅ **Admin Rental Management**: Fixed rental ID generation and prevented overwriting when adding rentals
- ✅ **Payment Status Fixes**: Corrected cash payment balance updates and payment status handling
- ✅ **System Reports**: Fixed active rental counting, pending reminders, and revenue tracking for admin operations

### Version 2.2 (September 6, 2025) - Bug Fixes Release
- ✅ **Bug #1**: Removed long format for phone number and simplified input format display
- ✅ **Bug #2**: Limited answer options within a-d with proper loop validation
- ✅ **Bug #3**: Fixed password reset loop to properly validate 6+ character minimum
- ✅ **Bug #4**: Resolved error when changing current password to same new password
- ✅ **Bug #5**: Fixed login issue where old password still worked after password change
- ✅ **Bug #6**: Fixed outstanding balance display after booking confirmation
- ✅ **Bug #7**: Resolved rental ID mismatch between creation and history views
- ✅ **Bug #8**: Improved error handling with try-catch and user-friendly date validation messages
- ✅ **Bug #9**: Fixed system report crash after customer login and rental (null pointer exception)
- ✅ **Bug #10**: Fixed revenue tracking for admin-added rentals (missing reminders and system reports)
- ✅ **Bug #11**: Fixed rental history pagination to show more than 2 rentals (added page navigation)
- ✅ **Bug #12**: Added vehicle type validation during addition (prevents invalid selections)
- ✅ **Bug #13**: Implemented condition selection (a,b,c) and auto-generated vehicle IDs (MB001, DB001, BG001, CR001)
- ✅ **Bug #14**: Enhanced vehicle update functionality to include price and availability updates

### Version 2.1 (September 6, 2025)
- ✅ **Advanced ID Generation System**: Implemented automatic ID generation for all DAO classes
- ✅ **Fixed Rental ID Mismatch**: Resolved rental ID inconsistencies between creation and history views
- ✅ **Enhanced Data Integrity**: All DAOs now check existing CSV data and generate unique IDs
- ✅ **Comprehensive DAO Improvements**: Added generateNewId methods to CustomerDAO, InvoiceDAO, and all other DAO classes
- ✅ **Build System Optimization**: Reverted to Ant build system for improved stability and reliability
- ✅ **Consistent ID Formats**: Standardized ID generation (C001, R001, V001, P001, etc.)
- ✅ **Conflict Prevention**: New entities won't overwrite existing data in CSV files
- ✅ **Payment Integration**: Rental IDs remain consistent throughout the entire payment process

### Version 2.0 (September 5, 2025)
- ✅ **Enhanced Login System**: Customers can now login using either ID or email address
- ✅ **Advanced Phone Validation**: Support for multiple Malaysian phone formats with auto-normalization
- ✅ **Improved Input Validation**: Fixed while loop behavior for all input methods
- ✅ **Enhanced IC Validation**: Added date validation for Malaysian IC numbers
- ✅ **Comprehensive Testing**: Added RecentEnhancementsTest.java and organized test suite
- ✅ **User Experience**: Clear prompts and error messages throughout the application
- ✅ **Gradle Integration**: Added modern Gradle build system alongside existing Ant setup

### Version 1.0 (Initial Release)
- ✅ Basic vehicle rental system with customer and manager portals
- ✅ Malaysian-specific validations and formatting
- ✅ Role-based access control and security features
- ✅ Complete rental lifecycle management

## ✨ Key Features

### 🔐 **Advanced Security & Authentication**
- **SHA-256 Password Hashing** with unique salts for all users
- **Role-Based Access Control** with 5 authorization levels
- **Flexible Login Options**: Login with Customer ID or Email address
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

### � **Advanced Payment Processing**
- **Multiple Payment Methods**: Cash, Credit Card, Online Banking with receipts
- **Credit Card Validation**: Comprehensive input validation with reinput loops
- **Secure Processing**: Simulated payment gateway with validation
- **Payment History**: Complete transaction tracking with status updates

### 🇲🇾 **Malaysia-Specific Features**
- **IC Validation**: Malaysian IC format (XXXXXX-XX-XXXX) with date validation
- **Phone Validation**: Multiple formats (+60XXXXXXXXX, 0XXXXXXXXX, 0xx-xxx-xxxx, 0xx xxx xxxx) with auto-normalization
- **License Types**: B, B2, D, DA, E, E1, E2 validation
- **Age Calculation**: Automatic age from IC number
- **Location Support**: Malaysian addresses and regions

## 🆕 Recent Enhancements (v2.3)

### 💳 **Advanced Credit Card Validation**
- **Input Validation Loops**: Users must enter valid credit card details or cancel payment
- **Card Number**: Exactly 16 digits required with spaces/dashes allowed
- **Card Holder Name**: Letters and spaces only, no numbers or special characters
- **Expiry Date**: MM/YY format with month (01-12) and future date validation
- **CVV**: Exactly 3 digits required
- **User-Friendly Errors**: Clear error messages guide users to correct invalid input

### 👨‍💼 **Enhanced Admin Workflow**
- **Bulk Rental Deletion**: Admins can delete multiple rentals in one session without returning to menu
- **Organized Menu Structure**: Manager menu divided into 5 logical sections for better navigation
- **Improved Rental Display**: Group rentals by vehicle with clear date differentiation
- **Conflict Prevention**: Automatic validation prevents double-booking same vehicle on same dates

### 🔧 **System Reliability Improvements**
- **Payment Status Accuracy**: Fixed cash payment balance updates and status handling
- **Data Integrity**: Corrected rental ID generation and admin-added rental visibility
- **Report Accuracy**: System reports now properly count active rentals, pending reminders, and revenue

### 🔧 **Comprehensive Bug Fixes**
- **14 Critical Issues Resolved**: Fixed all reported bugs including system crashes, data inconsistencies, and user experience problems
- **Enhanced System Stability**: Resolved null pointer exceptions and application crashes
- **Improved Data Integrity**: Fixed revenue tracking, rental ID mismatches, and duplicate ID prevention
- **Better User Experience**: Added input validation, clear error messages, and intuitive navigation

### 🔑 **Enhanced Login System**
- **Flexible Customer Login**: Login using Customer ID (C001) or Email address
- **Improved User Experience**: Clear prompts indicating available login options
- **Backward Compatibility**: Existing ID-based login continues to work

### 📱 **Advanced Phone Number Handling**
- **Multiple Input Formats**: Support for +60XXXXXXXXX, 0XXXXXXXXX, 0xx-xxx-xxxx, 0xx xxx xxxx
- **Auto-Normalization**: All phone numbers stored in standard +60XXXXXXXXX format
- **Input Validation**: Real-time validation with user-friendly error messages

### 🔄 **Improved Input Validation**
- **While Loop Fixes**: All input methods now properly allow re-entry after validation errors
- **Exit Functionality**: "0" input consistently exits to main menu from any input prompt
- **Error Recovery**: Users can retry input without losing progress

### 🆔 **Advanced ID Generation System**
- **Automatic ID Generation**: All DAO classes now generate unique IDs automatically
- **Conflict Prevention**: New entities won't overwrite existing CSV data
- **Consistent Format**: Standardized ID formats (C001, R001, V001, etc.)
- **Data Integrity**: Prevents ID conflicts across all entity types

### 🛠️ **Fixed Rental ID Mismatch**
- **Consistent ID Usage**: Rental IDs now match between creation and history views
- **Payment Integration**: Rental IDs remain consistent throughout payment process
- **Data Synchronization**: Fixed synchronization issues between rental creation and management

### 🚗 **Enhanced Vehicle Management**
- **Auto-Generated Vehicle IDs**: Type-specific prefixes (MB001, DB001, BG001, CR001)
- **Condition Selection**: Structured input with a/b/c options for Good/Excellent/New
- **Comprehensive Updates**: Price and availability updates with validation
- **Duplicate Prevention**: No more duplicate vehicle ID creation

### 🧪 **Enhanced Testing Suite**
- **Comprehensive Test Coverage**: New test files for recent enhancements
- **Validation Testing**: IC format, phone normalization, login logic verification
- **Organized Test Structure**: All tests properly located in `src/test/` directory

## 🚀 Quick Start

### Prerequisites
- **Java 21 or higher**
- **NetBeans IDE (recommended)** or any Java IDE
- **Terminal/Command Prompt** for command line execution

### 💻 System Requirements

| Component | Minimum | Recommended |
|-----------|---------|-------------|
| **Operating System** | Windows 10/11, Linux (Ubuntu 18.04+), macOS 10.14+ | Windows 11, Ubuntu 20.04+, macOS 12.0+ |
| **Java Version** | OpenJDK 21 or Oracle JDK 21 | OpenJDK 21 LTS |
| **RAM** | 512 MB | 1 GB |
| **Storage** | 50 MB | 100 MB |
| **Display** | 1024x768 | 1920x1080 |

### 📦 Installing Dependencies

<details>
<summary>🪟 Windows (Using winget)</summary>

```powershell
# Install Java 21 (OpenJDK)
winget install --id Oracle.JavaRuntimeEnvironment.21 --source winget

# Or install OpenJDK 21
winget install --id Microsoft.OpenJDK.21 --source winget

# Install NetBeans IDE (optional)
winget install --id Apache.NetBeans --source winget
```
</details>

<details>
<summary>🪟 Windows (Using scoop)</summary>

```powershell
# Install scoop if not already installed
Set-ExecutionPolicy RemoteSigned -Scope CurrentUser
irm get.scoop.sh | iex

# Install Java 21
scoop bucket add java
scoop install openjdk21

# Install NetBeans IDE (optional)
scoop install netbeans
```
</details>

<details>
<summary>🐧 Linux (Ubuntu/Debian)</summary>

```bash
# Update package list
sudo apt update

# Install OpenJDK 21
sudo apt install openjdk-21-jdk

# Install NetBeans IDE (optional)
sudo snap install netbeans --classic
```
</details>

<details>
<summary>🐧 Linux (Using curl for manual installation)</summary>

```bash
# Download and install OpenJDK 21
curl -O https://download.java.net/java/GA/jdk21.0.2/f2283984656d49d69e91c558476027ac/13/GPL/openjdk-21.0.2_linux-x64_bin.tar.gz
tar -xzf openjdk-21.0.2_linux-x64_bin.tar.gz
sudo mv jdk-21.0.2 /usr/local/
export JAVA_HOME=/usr/local/jdk-21.0.2
export PATH=$JAVA_HOME/bin:$PATH
```
</details>

### 🔨 Building the Project

HillClimmer uses the **Ant build system** for reliable and consistent builds across all platforms.

<details>
<summary>🐜 Ant Build System</summary>

```bash
# Compile the project
ant compile

# Run the application
ant run

# Create JAR file
ant jar

# Clean build files
ant clean
```

**Benefits of Ant:**
- ✅ Reliable and stable build process
- ✅ Cross-platform compatibility
- ✅ Integrated with NetBeans IDE
- ✅ Simple and straightforward
- ✅ No external dependencies
</details>

### Running the Application

<details>
<summary>🖥️ Method 1: NetBeans IDE (Recommended)</summary>

1. Open the project in NetBeans
2. Navigate to `src/hillclimmer/HillClimmer.java`
3. Right-click → "Run File" (Shift+F6)
4. Choose your user type and follow the prompts
</details>

<details>
<summary>🪟 Method 2: Command Line (Windows)</summary>

```cmd
cd HillClimmer
javac -d build/classes -sourcepath src --release 21 src/hillclimmer/*.java src/hillclimmer/*/*.java
java -cp build/classes hillclimmer.HillClimmer
```
</details>

<details>
<summary>🐧 Method 3: Command Line (Linux/macOS)</summary>

```bash
cd HillClimmer
javac -d build/classes -sourcepath src --release 21 src/hillclimmer/*.java src/hillclimmer/*/*.java
java -cp build/classes hillclimmer.HillClimmer
```
</details>

<details>
<summary>🔧 Method 4: Using Ant Build Tool</summary>

```bash
# Compile and run with Ant
ant compile
ant run
```
</details>

## 📁 Project Structure

```
HillClimmer/
├── src/hillclimmer/
│   ├── HillClimmer.java          # Main application with enhanced ID management
│   ├── CustomerModule/
│   │   ├── Customer.java         # Customer management with auto-generated IDs
│   │   └── SafetyCheck.java      # Safety quiz system
│   ├── VehicleModule/
│   │   ├── Vehicle.java          # Abstract vehicle class
│   │   ├── VehicleManager.java   # Vehicle inventory with ID generation
│   │   └── [Vehicle types...]    # Bike, buggy classes with unique IDs
│   ├── RentalModule/
│   │   ├── Rental.java           # Rental processing with consistent IDs
│   │   └── RentalManager.java    # Rental management with ID synchronization
│   ├── PaymentModule/
│   │   ├── Payment.java          # Payment processing with timestamp IDs
│   │   └── TransactionManager.java # Transaction tracking
│   ├── DurationModule/
│   │   ├── DurationManager.java  # Timer system
│   │   └── Reminder.java         # Reminder system with auto-generated IDs
│   └── DatabaseModule/
│       ├── Manager.java          # Manager authentication
│       ├── ManagerDAO.java       # Manager data access
│       ├── DataAccessObject.java # Enhanced base class with ID generation
│       ├── CustomerDAO.java      # Customer DAO with auto ID generation
│       ├── VehicleDAO.java       # Vehicle DAO with type-based ID generation
│       ├── RentalDAO.java        # Rental DAO with sequential ID generation
│       ├── PaymentDAO.java       # Payment DAO with timestamp-based IDs
│       ├── ReminderDAO.java      # Reminder DAO with sequential IDs
│       ├── SafetyCheckDAO.java   # Safety check DAO with SC-prefixed IDs
│       ├── RentalPeriodDAO.java  # Rental period DAO with auto IDs
│       └── InvoiceDAO.java       # Invoice DAO with sequential ID generation
├── data/
│   ├── customers.csv             # Customer database with auto-generated IDs
│   ├── managers.csv              # Manager database
│   ├── vehicles.csv              # Vehicle inventory with unique IDs
│   ├── rentals.csv               # Rental records with consistent IDs
│   ├── payments.csv              # Payment history with timestamp IDs
│   ├── reminders.csv             # Reminder records with auto IDs
│   ├── safetychecks.csv          # Safety check records
│   ├── rentalperiods.csv         # Rental period records
│   └── invoices.csv              # Invoice records with auto IDs
├── build/
│   └── classes/                  # Compiled Java classes
├── nbproject/                    # NetBeans project configuration
├── build.xml                     # Ant build configuration
├── manifest.mf                   # JAR manifest file
└── test/
    ├── AuthenticationTest.java     # Authentication testing
    ├── ComprehensiveSystemTest.java # Full system testing
    ├── ExitInputTest.java          # Input validation testing
    ├── RecentEnhancementsTest.java # Latest features testing
    └── [Additional test files...]  # Specialized test suites
```

## 🎯 Application Workflow

### For Customers:
1. **Register** or **Login** with Customer ID or Email address
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

- **6 Sample Customers** with complete profiles and email addresses
- **5 Manager Accounts** with different authorization levels
- **70+ Vehicles** across multiple categories with auto-generated type-specific IDs
- **CSV-Based Storage** for all data persistence with conflict-free ID generation
- **Real-time Updates** for inventory and bookings with comprehensive validation
- **Flexible Login Options** (ID or Email for customers)
- **Multi-Format Phone Support** with auto-normalization
- **Comprehensive Test Suite** with 20+ test files including bug fix validations
- **Automatic ID Generation** for all entity types with type-specific prefixes
- **Data Integrity Protection** against ID conflicts and system crashes
- **Consistent Rental IDs** throughout the payment process
- **Enhanced Vehicle Management** with condition selection and price/availability updates
- **14 Critical Bug Fixes** implemented for improved system reliability

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new features
5. Submit a pull request

## 📄 License

This project is developed for educational purposes as part of the OOP course assignment.

## 👥 Team Members

- **Lye Wei Lun** (VM003) - Project Lead
- **Chin Wen Wei** (VM002) - Tester/QA
- **Neeshwran A/L Veera Chelvan** (VM004) - Tester/QA
- **Oscar Lim Zheng You** (VM005) - Tester/QA
- **Teh Guan Chen** (VM006) - Tester/QA

---

**🏔️ Ready to explore Malaysia's hill climbing adventures with HillClimmer v2.2! 🚀**

*Last updated: September 6, 2025*
