# The "Bugs" bunny

1) ~~new password cannot change for admin~~ ✅ **FIXED** - Admin password change now works with proper validation and loop-back functionality
2) ~~don't specify format (60xxxx , xxxx, xxxx) just let user type phone number as usual 01157881296 or 0115788129 (accept both value of 3 num or 4 num)~~ ✅ **FIXED** - Phone number input simplified with examples, now accepts 10-11 digit numbers with flexible formatting
3) ~~Registration failed: Password must be at least 6 characters long (if does not met requirement it no loop)~~ ✅ **FIXED** - Password confirmation now properly loops back to first password input when validation fails or passwords don't match
4) payment online (crash) - done
5) payment method cant exit back to menu (0 cancel payment) - done
6) credit card payment (the amount should auto key in) - done
7) credit card (crash) - done
8) view rental (page indicator should be at bottom together with total rental)
9) after view payment history, remain in profile menu
10) update password also loop back if requirement not met
11) by logic revenue should display after payment confirmed (system report)
12) I can create exact same account details and login choose the first one (limit email)
13) add rental history (from assignment question) customer can view past rental date and vehicle (11/7/2025 - 11/7/2025 MB001 X RM10 1 DAY)
14) availability update did not change (logical error)
15) need 2 times update vehicle all of the above to take effect changes 
16) update price did not change (logical error)
17) report did not update when delete rental (pending,revenue)
18) adding new rental to customer should be same as fixed vehicle price since u already specify vehicle id, if every vehicle id can manually fill daily rate, the vehicle id wont matter
19) when admin delete rental, customer account outstanding no change (logical error)
20) active rental no increase after book
21) save booked date so it doesn't conflict with other customer