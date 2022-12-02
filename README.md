# Finance Manager 

## Program Description

**Finance Manager** is a program used to track a user's _income_ and _costs/spending_ to help make informed financial decisions.
The program adds three neat features to your 'bank account' to build healthy financial habits such as spending **within budget**, avoiding **impulsive** purchases, and **reviewing** your monthly spending patterns.
This program can be used by anyone but it will be most useful to students with limited budget that has to make the most out of their budget to navigate college life full of expenses.
I personally splurge sometimes and tend to forget my goals so a program like this would help me build strong and healthy financial habits.

#### Budget Sergeant

This feature acts like your financial drill sergeant. Since our bank account balance _does not reflect_ what we can _actually_ afford, we need the Budget Sergeant to check our saving goals, budget limits, and monthly payments due (Netflix, utilities bill etc.) with regard to your monthly income to ensure you can _really_ afford the purchase. This ensures the user stay **within budget** and avoid **impulsive purchases** that go against their financial goals.

#### Spending A.I

This feature will **track** your purchases for the month give **a visualized summary** of the user spending pattern, **progress** towards financial goals, and provide some **insightful spending statistics** to help **review** your financial habits.

### User Stories:

- As a user, I want to be able to record and store my income and purchases/withdraws to my bank account. (X to Y class)
- As a user, I want to be able to add and store saving/budget goals to my goals list. (X to Y class)
- As a user, I want to be able to view the budget left for spending after considering my goals.
- As a user, I want to be able to make purchases and see if it goes against my goals.
- As a user, I want to be able to see statistics on total spending, largest spent amount and see a visualized chart of my spending.
- As a user, when I select the quit option from the application menu, I want the option to save the list of my spending data along with
  my balance at the time of purchase.
- As a user, when I start the application, I want to be given the option to load my previous spending data along with any saving goals.

### Instructions for Grader:
- You can generate the first required event related to adding Xs to a Y by choosing the make a purchase option on the menu after you deposit money in the bank. 
- You can generate the second required event related to adding Xs to a Y by making another purchase with enough balance and observing the list of purchases made so far in right bottom panel. 
- You can add other X to Ys by adding and updating the saving goals by choosing the third option in the menu. 
- You can locate my visual component by either saving or loading menu. If successful, the top panel should try green; otherwise, it should be red to show file exception.
- Another major visual component when you add a purchase, it is added to the spending list in the right bottom panel. 
- You can save the state of my application by selecting save file on left menu after entering username.
- You can reload the state of my application by selecting load file on left menu after entering username

### Phase 4: Task 2
```
----------------------------------
Printing all event logs...

Fri Dec 02 15:37:55 PST 2022 
Deposited $1000.0 into bank balance

Fri Dec 02 15:38:03 PST 2022
Withdrawn $200.0 from bank balance

Fri Dec 02 15:38:13 PST 2022
Withdrawn $300.0 from bank balance

Fri Dec 02 15:38:13 PST 2022
A new purchase was made and added to list of spending!

Fri Dec 02 15:38:19 PST 2022
Saving goal updated to: $250.0

Fri Dec 02 15:38:19 PST 2022
User has started saving!

Fri Dec 02 15:38:32 PST 2022
Withdrawn $50.0 from bank balance

Fri Dec 02 15:38:32 PST 2022
A new purchase was made and added to list of spending!
----------------------------------
```