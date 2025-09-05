# 🏔️ HillClimber Malaysia Vehicle Rental System

## 📋 Overview
A comprehensive **cross-platform Java-based** vehicle rental system designed specifically for Malaysia's hill climbing market. Features secure authentication, role-based access control, and complete rental lifecycle management with Malaysian-specific validations. **Compatible with Windows, Linux, and macOS**.

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
- **Java 21 or higher**
- **NetBeans IDE (recommended)** or any Java IDE
- **Terminal/Command Prompt** for command line execution

### � System Requirements

| Component | Minimum | Recommended |
|-----------|---------|-------------|
| **Operating System** | Windows 10/11, Linux (Ubuntu 18.04+), macOS 10.14+ | Windows 11, Ubuntu 20.04+, macOS 12.0+ |
| **Java Version** | OpenJDK 21 or Oracle JDK 21 | OpenJDK 21 LTS |
| **RAM** | 512 MB | 1 GB |
| **Storage** | 50 MB | 100 MB |
| **Display** | 1024x768 | 1920x1080 |

### �📦 Installing Dependencies

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
cd HillClimber
javac -d build/classes -sourcepath src --release 21 src/hillclimmer/*.java src/hillclimmer/*/*.java
java -cp build/classes hillclimmer.HillClimmer
```
</details>

<details>
<summary>🐧 Method 3: Command Line (Linux/macOS)</summary>

```bash
cd HillClimber
javac -d build/classes -sourcepath src --release 21 src/hillclimmer/*.java src/hillclimmer/*/*.java
java -cp build/classes hillclimmer.HillClimmer
```
</details>

<details>
<summary>🔧 Method 4: Using Maven/Gradle (Alternative)</summary>

If you prefer using build tools:

```bash
# Using Maven
mvn compile exec:java -Dexec.mainClass="hillclimmer.HillClimmer"

# Using Gradle
gradle build
gradle run
```
</details>

## � Troubleshooting

### Common Issues & Solutions

<details>
<summary>☕ Java Version Issues</summary>

```bash
# Check Java version
java -version

# Set JAVA_HOME (Windows)
setx JAVA_HOME "C:\Program Files\Java\jdk-21"

# Set JAVA_HOME (Linux/macOS)
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
```
</details>

<details>
<summary>🔨 Compilation Errors</summary>

```bash
# Clean and rebuild
rm -rf build/
mkdir build/classes

# Compile with verbose output
javac -d build/classes -sourcepath src --release 21 -verbose src/hillclimmer/*.java src/hillclimmer/*/*.java
```
</details>

<details>
<summary>⚠️ Runtime Errors</summary>

```bash
# Run with debug information
java -cp build/classes -Djava.util.logging.level=INFO hillclimmer.HillClimmer

# Check classpath
java -cp build/classes hillclimmer.HillClimmer --demo
```
</details>

<details>
<summary>🔒 Permission Issues (Linux)</summary>

```bash
# Make scripts executable
chmod +x *.sh

# Run with proper permissions
sudo java -cp build/classes hillclimmer.HillClimmer
```
</details>

<details>
<summary>🪟 Windows Path Issues</summary>

```cmd
# Add Java to PATH
set PATH=%PATH%;"C:\Program Files\Java\jdk-21\bin"

# Verify Java installation
where java
java -version
```
</details>

## �👤 User Accounts & Credentials

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

- **Lye Wei Lun** (VM003) - Project Lead
- **Chin Wen Wei** (VM002) - Tester/QA
- **Neeshwran A/L Veera Chelvan** (VM004) - Tester/QA
- **Oscar Lim Zheng You** (VM005) - Tester/QA
- **Teh Guan Chen** (VM006) - Tester/QA

---

**🏔️ Ready to explore Malaysia's hill climbing adventures with HillClimber! 🚀**
