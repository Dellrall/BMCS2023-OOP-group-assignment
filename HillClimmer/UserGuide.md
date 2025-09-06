# � HillClimmer Malaysia - Complete User Guide

## 📋 Table of Contents
- [🔐 User Accounts & Credentials](#-user-accounts--credentials)
- [👥 Customer Portal Guide](#-customer-portal-guide)
- [👨‍💼 Manager Portal Guide](#-manager-portal-guide)
- [🛡️ Safety Check Questions & Answers](#️-safety-check-questions--answers)
- [🚗 Vehicle Information](#-vehicle-information)
- [💳 Payment Methods](#-payment-methods)
- [🔧 Troubleshooting](#-troubleshooting)

---

## � User Accounts & Credentials

### 👥 Customer Accounts

<details>
<summary>Click to expand Customer Login Information</summary>

| Customer ID | Name | IC Number | Email | Password | Phone | License |
|-------------|------|-----------|-------|----------|-------|---------|
| **C001** | Muhammad Ali | 950101-14-5678 | muhammad@email.com | **AliSecure123!** | +60123456789 | B |
| **C002** | Jeremy Clarkson | 850101-01-1234 | jeremy@topgear.com | **TopGear2025!** | +60987654321 | B2 |
| **C003** | Richard Hammond | 880202-02-2345 | richard@topgear.com | **HammondRacing123!** | +60111222333 | D |
| **C004** | James May | 820303-03-3456 | james@topgear.com | **CaptainSlow456!** | +60444555666 | DA |
| **C005** | Sabine Schmitz | 790404-04-4567 | sabine@topgear.com | **QueenOfNurburgring789!** | +60777888999 | E |
| **C006** | Chris Evans | 860505-05-5678 | chris@topgear.com | **TopGearHostABC!** | +60333444555 | E1 |

**💡 Login Options:**
- Use **Customer ID** (e.g., C001) OR **Email** (e.g., muhammad@email.com)
- Both methods work with the same password

</details>

### 👨‍💼 Manager Accounts

<details>
<summary>Click to expand Manager Login Information</summary>

| Manager ID | Name | Password | Level | Permissions | Department |
|------------|------|----------|-------|-------------|------------|
| **VM002** | Chin Wen Wei | **Manager123!** | 1 | View Only | Operations |
| **VM003** | Lye Wei Lun | **SecurePass456!** | 5 | Full Admin | Management |
| **VM004** | Neeshwran A/L Veera Chelvan | **Nurburg2025!** | 3 | Standard Admin | Vehicle Management |
| **VM005** | Oscar Lim Zheng You | **OscarRacing!** | 4 | Advanced Admin | Customer Relations |
| **VM006** | Teh Guan Chen | **TehSecure789!** | 5 | Full Admin | System Administration |

**🔑 Authorization Levels:**
- **Level 1**: View Only (vehicles, rentals)
- **Level 2**: Basic Admin (add/remove vehicles, rentals)
- **Level 3**: Standard Admin (all CRUD operations on vehicles)
- **Level 4**: Advanced Admin (full access + admin functions)
- **Level 5**: Super Admin (complete system control)

</details>

---

## 👥 Customer Portal Guide

### 🚀 Getting Started

<details>
<summary>1. Registration Process</summary>

**Step-by-Step Registration:**
1. Launch the application
2. Select "1" for Customer Portal
3. Choose "2. Register New Account"
4. Fill in required information:
   - **Name**: Full name as per IC
   - **IC Number**: Format: XXXXXX-XX-XXXX (e.g., 950101-14-5678)
   - **Phone**: Various formats accepted (+60XXXXXXXXX, 0XXXXXXXXX, etc.)
   - **Email**: Valid email address for login
   - **License Type**: B, B2, D, DA, E, E1, E2
   - **Password**: Minimum 6 characters
   - **Address**: Malaysian address

**✅ Validation Requirements:**
- IC must be valid Malaysian format with correct date
- Phone number auto-normalizes to +60XXXXXXXXX format
- Email must be unique and valid format
- Password must be at least 6 characters

</details>

<details>
<summary>2. Login Process</summary>

**Two Login Methods:**
1. **Customer ID Login**: Use your assigned ID (e.g., C001)
2. **Email Login**: Use your registered email address

**Login Steps:**
1. Select "1. Customer Login"
2. Enter Customer ID or Email
3. Enter your password
4. Access your dashboard

**🔒 Security Features:**
- Passwords are SHA-256 hashed with unique salts
- Failed login attempts are tracked
- Session management for security

</details>

<details>
<summary>3. Safety Check Assessment</summary>

**Mandatory Requirement:**
- Must pass safety check before booking any vehicle
- 10 random questions from 4 categories
- 80% passing score (8/10 correct)

**Assessment Process:**
1. Select "3. Take Safety Check"
2. Answer 10 multiple-choice questions
3. Receive immediate feedback
4. Must pass to unlock vehicle booking

**📚 Study Areas:**
- Malaysian traffic rules and regulations
- Traffic violation penalties
- Vehicle operation and safety
- Basic road safety principles

</details>

<details>
<summary>4. Vehicle Browsing & Booking</summary>

**Available Vehicle Types:**
- **Mountain Bikes** (MB001-MB020): RM45-95/day
- **Dirt Bikes** (DB001-DB020): RM58-88/day  
- **Buggies** (BG001-BG015): RM82-110/day
- **Crossovers** (CR001-CR015): RM108-145/day

**Booking Process:**
1. Select "4. Book Vehicle"
2. Browse available vehicles
3. Choose vehicle type and specific model
4. Enter rental dates (DD/MM/YYYY format)
5. Review booking details and total cost
6. Confirm booking
7. Proceed to payment

**💰 Cost Calculation:**
- Automatic calculation based on daily rates
- Duration calculated from start/end dates
- Total displayed before confirmation

</details>

<details>
<summary>5. Payment Methods</summary>

**Available Payment Options:**
1. **Cash Payment**: Pay at pickup location
2. **Credit Card**: Visa, MasterCard, American Express
3. **Online Banking**: Malaysian banks supported

**Payment Process:**
1. Select payment method
2. Enter payment details (if card/banking)
3. Confirm transaction
4. Receive payment confirmation
5. Booking confirmed and rental active

**📄 Receipt Information:**
- Transaction ID and timestamp
- Vehicle details and rental period
- Payment amount and method
- Customer information

</details>

<details>
<summary>6. Profile Management</summary>

**Profile Features:**
- View personal information
- Check rental history (paginated view)
- Update contact details
- Change password
- View safety check status

**Password Change:**
1. Select "6. Change Password"
2. Enter current password
3. Enter new password (min 6 chars)
4. Confirm new password
5. Password updated immediately

**📊 Rental History:**
- View up to 3 rentals per page
- Navigate between pages
- See rental details, dates, and costs
- Track payment status

</details>

---

## 👨‍💼 Manager Portal Guide

### 🎯 Dashboard Overview

<details>
<summary>1. Manager Authentication</summary>

**Login Process:**
1. Launch application
2. Select "2. Manager Portal"
3. Enter Manager ID (e.g., VM003)
4. Enter password
5. Access level-based dashboard

**🔐 Security Features:**
- Role-based access control
- Permission-level restrictions
- Secure session management
- Activity logging

</details>

<details>
<summary>2. Vehicle Management</summary>

**Add New Vehicle:**
1. Select "2. Add New Vehicle"
2. Choose vehicle type (1-4):
   - 1: Mountain Bike (MB prefix)
   - 2: Dirt Bike (DB prefix)
   - 3: Buggy (BG prefix)
   - 4: Crossover (CR prefix)
3. Enter vehicle details:
   - Model name
   - Daily price (RM)
   - Condition (a=Good, b=Excellent, c=New)
4. Vehicle ID auto-generated
5. Confirm addition

**Update Vehicle Details:**
1. Select "3. Update Vehicle Details"
2. Enter vehicle ID
3. Choose update options:
   - 1: Condition only
   - 2: Price only
   - 3: Availability only
   - 4: All of the above
4. Enter new values with validation
5. Confirm updates

**Remove Vehicle:**
1. Select "4. Remove Vehicle"
2. Enter vehicle ID
3. Confirm removal
4. Vehicle marked as removed

**🔧 Management Features:**
- Auto-generated unique IDs
- Real-time inventory updates
- Price and availability management
- Condition tracking (Good/Excellent/New)

</details>

<details>
<summary>3. Customer Management</summary>

**View All Customers:**
- Complete customer database
- Contact information
- Outstanding balances
- Registration details

**Customer Information Includes:**
- Customer ID and name
- IC number and age
- Phone and email
- License type
- Address information
- Safety check status
- Outstanding balance

**📊 Customer Analytics:**
- Total registered customers
- Active vs inactive accounts
- Payment history tracking
- Rental frequency analysis

</details>

<details>
<summary>4. Rental Management</summary>

**View All Rentals:**
- Paginated rental list (5 per page)
- Navigation between pages
- Rental details and status
- Customer and vehicle information

**Add New Rental (Admin):**
1. Select "6. Add New Rental"
2. Enter customer ID
3. Choose available vehicle
4. Set rental dates
5. Calculate total cost
6. Create rental record
7. Generate reminder and revenue tracking

**Rental Information:**
- Rental ID and dates
- Customer and vehicle details
- Payment status
- Duration and total cost
- Return status

**📈 Revenue Tracking:**
- All rentals contribute to system reports
- Automatic reminder generation
- Revenue calculation and analytics
- Payment tracking integration

</details>

<details>
<summary>5. System Reports</summary>

**Available Reports:**
- Total vehicles in inventory
- Total registered customers
- Active rental count
- Pending reminders
- Total revenue from active periods

**Report Features:**
- Real-time data updates
- Comprehensive system overview
- Revenue analytics
- Operational metrics

**📊 Analytics Dashboard:**
- Vehicle utilization rates
- Customer activity metrics
- Revenue trends
- System performance indicators

</details>

<details>
<summary>6. Authorization Management</summary>

**Permission Levels:**
- Level-based access control
- Feature restrictions by role
- Secure operation validation
- Administrative oversight

**Manager Capabilities by Level:**
- **Level 1-2**: Basic operations
- **Level 3-4**: Advanced management
- **Level 5**: Full system administration

</details>

---

## 🛡️ Safety Check Questions & Answers

### 📊 Assessment Overview

<details>
<summary>Assessment Structure & Requirements</summary>

**Assessment Details:**
- **Total Questions**: 10 (randomly selected)
- **Passing Score**: 80% (8/10 correct answers)
- **Question Distribution**:
  - Traffic Rules: 4 questions
  - Penalties: 3 questions
  - Vehicle Usage: 2 questions
  - Sanity Checks: 1 question

**📋 Question Selection:**
- Questions randomly selected from each category
- Priority-based distribution ensures coverage
- Final question order is randomized
- Each attempt uses different question combinations

</details>

<details>
<summary>�🚦 Traffic Rules Questions</summary>

**Question 1: Speed Limits**
What is the maximum speed limit in Malaysian residential areas?
- A) 60 km/h
- B) 50 km/h ✅
- C) 40 km/h
- D) 30 km/h

**Question 2: School Zones**
What should you do when approaching a school zone during school hours?
- A) Speed up to pass quickly
- B) Maintain normal speed
- C) Slow down and watch for children ✅
- D) Honk to alert children

**Question 3: U-Turn Regulations**
When can you make a U-turn in Malaysia?
- A) Anywhere if no oncoming traffic
- B) Only at traffic lights with U-turn signs ✅
- C) On highways
- D) Against one-way traffic

**Question 4: Licensing Age**
What is the minimum age to obtain a Malaysian driving license?
- A) 16 years
- B) 17 years
- C) 18 years ✅
- D) 21 years

**Question 5: Traffic Lights**
What does a red traffic light mean?
- A) Slow down
- B) Stop completely ✅
- C) Proceed with caution
- D) Speed up

</details>

<details>
<summary>⚖️ Penalty Questions</summary>

**Question 1: License Violations**
What is the penalty for driving without a valid license in Malaysia?
- A) Warning only
- B) RM300 fine
- C) RM300- RM1,000 fine + court ✅
- D) Vehicle confiscation

**Question 2: Speeding Violations**
What happens if you exceed speed limit by more than 60 km/h?
- A) RM300 fine
- B) License suspension + RM1,000 fine ✅
- C) Warning only
- D) Points on license

**Question 3: Mobile Phone Usage**
Penalty for using mobile phone while driving?
- A) RM300 fine
- B) RM500 fine + 10 points ✅
- C) Warning only
- D) License suspension

**Question 4: Drunk Driving**
What is the penalty for drunk driving in Malaysia?
- A) RM500 fine
- B) RM5,000- RM20,000 fine + jail ✅
- C) License suspension only
- D) Warning

</details>

<details>
<summary>🚗 Vehicle Usage Guidelines</summary>

**Question 1: Pre-Drive Checks**
Before starting a hill climbing vehicle, what should you check first?
- A) Fuel level
- B) Tire pressure and brakes ✅
- C) Radio station
- D) Seat comfort

**Question 2: Hill Driving**
What gear should you use when descending steep hills?
- A) High gear
- B) Low gear ✅
- C) Neutral
- D) Reverse

**Question 3: Parking**
When parking uphill, which way should you turn your wheels?
- A) Towards the curb
- B) Away from the curb ✅
- C) Straight
- D) Doesn't matter

**Question 4: Emergency Response**
What should you do if your vehicle starts to skid?
- A) Slam brakes
- B) Accelerate
- C) Steer into skid + ease off accelerator ✅
- D) Turn sharply

</details>

<details>
<summary>🧠 Sanity Check Questions</summary>

**Question 1: Basic Vehicle Knowledge**
How many wheels does a standard car have?
- A) 2
- B) 3
- C) 4 ✅
- D) 6

**Question 2: Traffic Signs**
What color is a stop sign?
- A) Green
- B) Yellow
- C) Red ✅
- D) Blue

**Question 3: Alcohol and Driving**
Should you drive after drinking alcohol?
- A) Yes, if you feel okay
- B) No, never ✅
- C) Only if you're not too drunk
- D) Yes, it's fun

**Question 4: Traffic Light Response**
What should you do at a green traffic light?
- A) Stop
- B) Proceed with caution ✅
- C) Speed up
- D) Wait for other cars

</details>

---

## 🚗 Vehicle Information

### Question 1
**What is the maximum speed limit in Malaysian residential areas?**
- A) 60 km/h
- B) 50 km/h ✅
- C) 40 km/h
- D) 30 km/h

### Question 2
**What should you do when approaching a school zone during school hours?**
- A) Speed up to pass quickly
- B) Maintain normal speed
- C) Slow down and watch for children ✅
- D) Honk to alert children

### Question 3
**When can you make a U-turn in Malaysia?**
- A) Anywhere if no oncoming traffic
- B) Only at traffic lights with U-turn signs ✅
- C) On highways
- D) Against one-way traffic

### Question 4
**What is the minimum age to obtain a Malaysian driving license?**
- A) 16 years
- B) 17 years
- C) 18 years ✅
- D) 21 years

### Question 5
**What does a red traffic light mean?**
- A) Slow down
- B) Stop completely ✅
- C) Proceed with caution
- D) Speed up

---

## ⚖️ Penalty Questions

### Question 1
**What is the penalty for driving without a valid license in Malaysia?**
- A) Warning only
- B) RM300 fine
- C) RM300- RM1,000 fine + court ✅
- D) Vehicle confiscation

### Question 2
**What happens if you exceed speed limit by more than 60 km/h?**
- A) RM300 fine
- B) License suspension + RM1,000 fine ✅
- C) Warning only
- D) Points on license

### Question 3
**Penalty for using mobile phone while driving?**
- A) RM300 fine
- B) RM500 fine + 10 points ✅
- C) Warning only
- D) License suspension

### Question 4
**What is the penalty for drunk driving in Malaysia?**
- A) RM500 fine
- B) RM5,000- RM20,000 fine + jail ✅
- C) License suspension only
- D) Warning

---

## 🚗 Vehicle Usage Guidelines

### Question 1
**Before starting a hill climbing vehicle, what should you check first?**
- A) Fuel level
- B) Tire pressure and brakes ✅
- C) Radio station
- D) Seat comfort

### Question 2
**What gear should you use when descending steep hills?**
- A) High gear
- B) Low gear ✅
- C) Neutral
- D) Reverse

### Question 3
**When parking uphill, which way should you turn your wheels?**
- A) Towards the curb
- B) Away from the curb ✅
- C) Straight
- D) Doesn't matter

### Question 4
**What should you do if your vehicle starts to skid?**
- A) Slam brakes
- B) Accelerate
- C) Steer into skid + ease off accelerator ✅
- D) Turn sharply

---

## 🧠 Sanity Check Questions

### Question 1
**How many wheels does a standard car have?**
- A) 2
- B) 3
- C) 4 ✅
- D) 6

### Question 2
**What color is a stop sign?**
- A) Green
- B) Yellow
- C) Red ✅
- D) Blue

### Question 3
**Should you drive after drinking alcohol?**
- A) Yes, if you feel okay
- B) No, never ✅
- C) Only if you're not too drunk
- D) Yes, it's fun

### Question 4
**What should you do at a green traffic light?**
- A) Stop
- B) Proceed with caution ✅
- C) Speed up
- D) Wait for other cars

---

## 📊 Assessment Process

### Question Selection Algorithm
1. **Traffic Rules**: 4 questions randomly selected from 5 available
2. **Penalties**: 3 questions randomly selected from 4 available
3. **Vehicle Usage**: 2 questions randomly selected from 4 available
4. **Sanity Checks**: 1 question randomly selected from 4 available
5. **Final Step**: All selected questions are shuffled for random order

### Scoring System
- Each correct answer = 1 point
- Total possible points = 10
- Passing score = 8 points (80%)
- Failed attempts require retaking the entire assessment

### Input Validation
- Only A, B, C, or D answers are accepted
- Input is case-insensitive (a, b, c, d also accepted)
- Invalid inputs prompt for re-entry
- No time limit for individual questions

---

## 🎯 Study Guide

### Key Areas to Focus On
1. **Malaysian Traffic Laws**: Speed limits, U-turn rules, licensing requirements
2. **Penalty Knowledge**: Understanding consequences of traffic violations
3. **Vehicle Safety**: Pre-drive checks, hill driving techniques, emergency procedures
4. **Basic Road Safety**: Fundamental driving principles and common sense

### Tips for Success
- Review Malaysian traffic regulations before taking the assessment
- Understand the severity of different traffic violations
- Practice hill driving techniques if unfamiliar
- Take your time to read each question carefully
- Remember that safety is the primary concern

---

## 📝 Assessment Results

### Upon Completion
- Immediate feedback on each answer
- Final score and percentage display
- Pass/fail status notification
- Review of incorrect answers for failed attempts
- Unique check ID generated for record keeping

### Record Keeping
- Customer ID association
- Timestamp of completion
- Score and pass/fail status
- Wrong answers logged for review
- Integration with customer booking eligibility

---

## 🚗 Vehicle Information

### 🚵‍♂️ Mountain Bikes (MB001-MB020)

<details>
<summary>Mountain Bike Specifications & Pricing</summary>

**Vehicle Type**: Mountain Bikes
**ID Range**: MB001 to MB020
**Daily Rate**: RM45 - RM95 per day

**Features:**
- All-terrain capability
- Lightweight aluminum frames
- Professional suspension systems
- Safety gear included (helmet, pads)

**Available Models:**
| Model ID | Model Name | Condition | Daily Rate | Availability |
|----------|------------|-----------|------------|--------------|
| MB001 | Trek Mountain Explorer | Excellent | RM85 | Available |
| MB002 | Giant Trail Master | Good | RM75 | Available |
| MB003 | Specialized Rockhopper | New | RM95 | Available |
| MB004 | Cannondale Trail | Good | RM65 | Available |
| MB005 | Scott Aspect | Excellent | RM80 | Available |

**Recommended For:**
- Beginners to moderate skill levels
- Trail exploration
- Fitness and recreation
- Age 16+ with appropriate license

</details>

### 🏍️ Dirt Bikes (DB001-DB020)

<details>
<summary>Dirt Bike Specifications & Pricing</summary>

**Vehicle Type**: Dirt Bikes
**ID Range**: DB001 to DB020
**Daily Rate**: RM58 - RM88 per day

**Features:**
- High-performance engines
- Off-road suspension
- Knobby tires for traction
- Full safety equipment provided

**Available Models:**
| Model ID | Model Name | Condition | Daily Rate | Availability |
|----------|------------|-----------|------------|--------------|
| DB001 | Honda CRF250L | Excellent | RM85 | Available |
| DB002 | Yamaha WR250F | Good | RM78 | Available |
| DB003 | KTM 350 EXC-F | New | RM88 | Available |
| DB004 | Suzuki DR-Z400S | Good | RM72 | Available |
| DB005 | Kawasaki KLX300R | Excellent | RM82 | Available |

**Requirements:**
- Valid motorcycle license (B2 or higher)
- Safety briefing mandatory
- Protective gear provided
- Age 18+ required

</details>

### 🏃‍♂️ Buggies (BG001-BG015)

<details>
<summary>Buggy Specifications & Pricing</summary>

**Vehicle Type**: All-Terrain Buggies
**ID Range**: BG001 to BG015
**Daily Rate**: RM82 - RM110 per day

**Features:**
- Roll cage protection
- 4-wheel drive capability
- High ground clearance
- Professional racing seats

**Available Models:**
| Model ID | Model Name | Condition | Daily Rate | Availability |
|----------|------------|-----------|------------|--------------|
| BG001 | Polaris RZR XP 1000 | Excellent | RM105 | Available |
| BG002 | Can-Am Maverick X3 | New | RM110 | Available |
| BG003 | Yamaha YXZ1000R | Good | RM95 | Available |
| BG004 | Arctic Cat Wildcat | Excellent | RM100 | Available |
| BG005 | Honda Pioneer | Good | RM85 | Available |

**Requirements:**
- Valid driving license (B or higher)
- Safety orientation required
- Helmet and harness provided
- Age 21+ recommended

</details>

### 🚙 Crossovers (CR001-CR015)

<details>
<summary>Crossover Specifications & Pricing</summary>

**Vehicle Type**: Crossover Vehicles
**ID Range**: CR001 to CR015
**Daily Rate**: RM108 - RM145 per day

**Features:**
- AWD/4WD systems
- Comfort and utility combined
- Premium interior
- Advanced safety features

**Available Models:**
| Model ID | Model Name | Condition | Daily Rate | Availability |
|----------|------------|-----------|------------|--------------|
| CR001 | Toyota RAV4 | Excellent | RM135 | Available |
| CR002 | Honda CR-V | New | RM145 | Available |
| CR003 | Mazda CX-5 | Good | RM125 | Available |
| CR004 | Subaru Forester | Excellent | RM140 | Available |
| CR005 | Nissan X-Trail | Good | RM118 | Available |

**Ideal For:**
- Family adventures
- Long-distance hill climbing
- Comfort with capability
- All license types welcome

</details>

---

## 💳 Payment Methods

### 💰 Available Payment Options

<details>
<summary>1. Cash Payment</summary>

**Process:**
- Select cash payment during booking
- Pay at vehicle pickup location
- Exact amount required
- Receipt provided immediately

**Benefits:**
- No processing fees
- Immediate transaction
- No credit card required
- Suitable for all customers

**Requirements:**
- Exact payment amount
- Valid identification
- Booking confirmation

</details>

<details>
<summary>2. Credit Card Payment</summary>

**Accepted Cards:**
- Visa (all types)
- MasterCard (all types)
- American Express

**Security Features:**
- Secure payment processing
- Encrypted card data
- Fraud protection
- Instant confirmation

**Process:**
1. Select credit card payment
2. Enter card details securely
3. Verify payment information
4. Receive instant confirmation
5. Digital receipt provided

**Benefits:**
- Instant booking confirmation
- Secure transactions
- Payment protection
- Convenient online process

</details>

<details>
<summary>3. Online Banking</summary>

**Supported Malaysian Banks:**
- Maybank
- CIMB Bank
- Public Bank
- RHB Bank
- Hong Leong Bank
- AmBank
- Bank Islam

**Process:**
1. Select online banking option
2. Choose your bank
3. Login to your bank account
4. Authorize payment
5. Return to confirmation page

**Features:**
- Direct bank transfer
- Real-time processing
- Secure bank login
- Immediate confirmation
- Bank-level security

</details>

### 💡 Payment Tips & Guidelines

<details>
<summary>Payment Security & Best Practices</summary>

**Security Measures:**
- All payments encrypted with SSL
- Card data never stored locally
- Bank-grade security protocols
- Fraud monitoring systems

**Payment Guidelines:**
- Verify booking details before payment
- Keep payment receipts for records
- Check vehicle availability before paying
- Contact support for payment issues

**Refund Policy:**
- Cancellations 24+ hours: Full refund
- Cancellations 12-24 hours: 50% refund
- Cancellations <12 hours: No refund
- Weather cancellations: Full refund

</details>

---

## 🛠️ Troubleshooting Guide

### 🔧 Common Issues & Solutions

<details>
<summary>🔐 Login Problems</summary>

**Issue**: Cannot login with credentials

**Solutions:**
1. **Verify Login Method**: Use Customer ID (e.g., C001) OR email
2. **Check Password**: Passwords are case-sensitive
3. **Account Status**: Ensure account is not suspended
4. **Clear Cache**: Restart application if persistent issues

**For Managers:**
- Verify Manager ID format (VM###)
- Check authorization level
- Contact system administrator if locked out

**Still Having Issues?**
- Try password reset option
- Contact customer support
- Verify account registration status

</details>

<details>
<summary>🛡️ Safety Check Issues</summary>

**Issue**: Cannot pass safety assessment

**Solutions:**
1. **Study Materials**: Review all question categories
2. **Practice**: Retake assessment multiple times
3. **Focus Areas**: Traffic rules, penalties, vehicle usage
4. **Pass Requirement**: Need 8/10 (80%) to pass

**Common Mistakes:**
- Rushing through questions
- Not reading answers carefully
- Guessing without knowledge
- Skipping study preparation

**Study Resources:**
- Malaysian Highway Code
- JPJ official guidelines
- Traffic regulation handbooks
- Vehicle operation manuals

</details>

<details>
<summary>🚗 Booking Problems</summary>

**Issue**: Vehicle not available for booking

**Solutions:**
1. **Check Dates**: Verify rental period availability
2. **Try Different Vehicles**: Browse alternative options
3. **Advance Booking**: Book earlier for popular dates
4. **Flexible Dates**: Consider adjusting rental period

**Issue**: Payment processing failed

**Solutions:**
1. **Verify Card Details**: Check card number, expiry, CVV
2. **Sufficient Funds**: Ensure adequate account balance
3. **Card Authorization**: Contact bank if declined
4. **Alternative Payment**: Try different payment method

**Issue**: Booking confirmation not received

**Solutions:**
1. **Check Email**: Look in spam/junk folders
2. **Transaction ID**: Note payment confirmation number
3. **Contact Support**: Verify booking status
4. **Print Receipt**: Save payment confirmation

</details>

<details>
<summary>⚡ System Performance</summary>

**Issue**: Application running slowly

**Solutions:**
1. **Restart Application**: Close and reopen program
2. **Check Resources**: Ensure adequate system memory
3. **Network Connection**: Verify stable internet
4. **Clear Temporary Files**: Clean system cache

**Issue**: Data not loading properly

**Solutions:**
1. **Refresh Data**: Use refresh options in menus
2. **Check Connectivity**: Verify database connection
3. **Restart Session**: Logout and login again
4. **Contact Support**: If problems persist

</details>

<details>
<summary>📱 Technical Support</summary>

**Contact Information:**
- **Email**: support@hillclimmer.my
- **Phone**: +60 3-2123 4567
- **Hours**: Monday-Friday, 8:00 AM - 6:00 PM
- **Emergency**: +60 12-345 6789 (after hours)

**Before Contacting Support:**
1. Note error messages exactly
2. Record steps that caused the issue
3. Have your account information ready
4. Try basic troubleshooting steps

**Information to Provide:**
- Customer/Manager ID
- Error message text
- Time and date of issue
- Steps to reproduce problem
- Browser/system information

</details>

---

## 📞 Contact & Support

### 🏢 HillClimmer Malaysia

**Company Information:**
- **Name**: HillClimmer Adventure Rentals Malaysia
- **Business Registration**: 123456-A
- **GST Number**: 123456789012

**📍 Head Office:**
```
HillClimmer Malaysia Sdn Bhd
Level 5, Menara Adventure
Jalan Bukit Bintang 123
55100 Kuala Lumpur
Malaysia
```

**📞 Contact Numbers:**
- **General Inquiries**: +60 3-2123 4567
- **Booking Hotline**: +60 3-2123 4568
- **Technical Support**: +60 3-2123 4569
- **Emergency (24/7)**: +60 12-345 6789

**📧 Email Addresses:**
- **General**: info@hillclimmer.my
- **Bookings**: bookings@hillclimmer.my
- **Support**: support@hillclimmer.my
- **Manager Support**: admin@hillclimmer.my

**🕒 Operating Hours:**
- **Monday - Friday**: 8:00 AM - 6:00 PM
- **Saturday**: 9:00 AM - 5:00 PM
- **Sunday**: 10:00 AM - 4:00 PM
- **Public Holidays**: Closed (Emergency support available)

---

*Generated for HillClimmer Malaysia v2.2 - Your Premier Hill Climbing Adventure Partner*

*Last Updated: January 2025*  
*HillClimmer Malaysia Vehicle Rental System v2.2*
