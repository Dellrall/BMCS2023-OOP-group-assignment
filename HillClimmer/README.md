# HillClimber Malaysia Vehicle Rental System

## Overview
A complete Java-based vehicle rental system designed specifically for Malaysia's hill climbing market. This consolidated application combines all functionality into a single runnable class for easy debugging in NetBeans IDE.

## Features
- 🏔️ **Malaysian-Specific Validations**: IC number, phone formats, license types
- 👥 **Multi-User Authentication**: Customer and manager access levels
- 🚗 **Complete Rental Lifecycle**: From booking to return with automated reminders
- 💳 **Payment Processing**: Multiple payment methods with transaction tracking
- ⏰ **Duration Management**: Automated timers and reminder system
- 💾 **Data Persistence**: CSV-based storage for all entities
- 👨‍💼 **Manager Functions**: Vehicle management and system administration

## How to Run in NetBeans

### Method 1: Direct Run (Recommended for Debugging)
1. Open the project in NetBeans
2. Navigate to `src/hillclimmer/HillClimmer.java`
3. Right-click on the file and select "Run File" or press Shift+F6
4. The application will start in interactive mode

### Method 2: With Demo Mode
1. Right-click on `HillClimmer.java`
2. Select "Properties"
3. Go to "Run" category
4. In "Arguments" field, enter: `--demo`
5. Click "OK" and run the file

### Method 3: Command Line
```bash
# Interactive mode
java -cp src hillclimmer.HillClimmer

# Demo mode
java -cp src hillclimmer.HillClimmer --demo
```

## Application Modes

### Interactive Mode (Default)
- Customer login and registration
- Vehicle rental booking
- Payment processing
- Profile management
- Manager administration panel

### Demo Mode (--demo)
- Automated system demonstration
- Shows all modules working together
- Validates Malaysian-specific features
- Displays system status and statistics

## Sample Data
The application includes sample data for testing:
- **Customer**: C001 (Muhammad Ali, IC: 950101-14-5678, Password: password123)
- **Manager**: VM001 (Ahmad Abdullah, Access Code: admin123)
- **Vehicles**: Mountain bikes, dirt bikes, buggies, and crossovers

## Key Classes and Modules
- `HillClimmer.java` - Main application entry point
- `Customer.java` - Customer management with Malaysian validations
- `Vehicle.java` - Abstract vehicle class with subclasses
- `VehicleManager.java` - Vehicle inventory management
- `RentalManager.java` - Rental processing
- `DurationManager.java` - Timer and reminder system
- `TransactionManager.java` - Payment processing
- DAO classes for CSV persistence

## Malaysian Features
- IC number validation (XXXXXX-XX-XXXX format)
- Phone number validation (+60XXXXXXXXX or 0XXXXXXXXX)
- License type validation (B, B2, D, DA, E, E1, E2)
- Address validation for Malaysian locations
- Age calculation from IC number

## System Requirements
- Java 8 or higher
- NetBeans IDE (recommended for debugging)
- CSV files are created automatically in the data folder

## Quick Start
1. Open in NetBeans
2. Run `HillClimmer.java`
3. Choose option 3 for new customer registration
4. Or use sample customer C001 with password "password123"
5. Book a vehicle rental and process payment
6. Try manager login with VM001/admin123 for admin functions

The system is fully functional and ready for debugging in NetBeans IDE!
