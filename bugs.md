# The "Bugs" bunny
1) [EMAIL DATABASE CHECK] can create same account, check email if exist say this account have already existed

2) [UNEXPECTED FLOW PAYMENT BUG] if invalid otp/payment cancel , the payment is count as paid

3) [SYSTEM REPORT BUG] active rental should increase after payment is success + pending reminder should be active when payment not paid, revenue should be counted after payment successful. MAKE SURE THE EFFECT APPLY TO WHEN UPDATE DELETE RENTAL USING ADMIN 

4) [VIEW RENTAL] should not display payment status since its to view past record, you can say STATUS: ACTIVE (before due), and STATUS: END (after due)

5) [ADMIN PROCESS PAYMENT BUG] customer use cash, but admin still found no pending payment

6) [ADMIN UPDATE VEHICLE BUG] (price):no change; (available): no change; recommended remove (all of the above) sometime it change and no change, no exact issue;

7) [RENTAL LOGICAL ERROR] save rental date so no other same vehicle same date can be made(THIS VEHICLE HAS BEEN RENTED IN 11/11/2025) MAKE SURE EFFECT APPLY TO  (ADD RENTAL ADMIN)

8) [ADMIN ADD RENTAL BUG] If I add same vehicle, it overwrites for some reason. Customer  account dont have added rental from admin. 

NOTE: THE IDEAL (VIEW ALL RENTAL) SHOULD LOOK LIKE DIFFERENT DATES FOR SAME VEHICLE

1) (optional)[REINPUT ADMIN DELETE RENTAL] ask user again want to delete another?

2) (optional)[PAYMENT SLIP AND PAYMENT HISTORY ARE SAME] remove payment slip in payment history, its exact same as payment

3) (optional)[CREDIT CARD INVALID FORMAT NEED REINPUT] credit card format checking, if entered 15 instead of 16 digits say invalid and loop input, same for name holder, date, csv