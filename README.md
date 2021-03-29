# CSE237 Piggy Bank Project

*Note to reviewer: Please look at the code and README in the development branch, NOT the main branch! 

The Piggy Bank Project is an application that is meant to simulate a simple banking application that allows users to interact with their accounts and manage their basic finances. This application is geared towards an audience that is newer to the banking system, as its features include the fundamental structure of most bank accounts. 

In its current form (i.e. Iteration 1), the application allows a user to create a profile or sign in with their chosen username and password. Currently, our program does not allow a user to make a profile with a username that has already been taken. These usernames are stored in a .txt file. Once they have made a profile/signed in, the user is brought to an account page, where they can choose to view their account statistics (i.e. balance), deposit money into their account, or withdraw from their account, if they have sufficient funds in their account. This covers three main user stories completed in this iteration, all shown in our 'done' section on Github. Once the user is done with their chosen actions (depositing, withdrawing, viewing), they should type 'exit'.  

We believe that this preliminary structure covers essential actions that could be taken by a user. In upcoming iterations, we plan to expand on many of these ideas, including having the option to make multiple accounts, transfer money between accounts, add multiple users to a single account, build out more information with passwords, and more. Additionally, in this first stage, we did not verify exact inputs by the user for deposit and withdrawal. Specifically, while we did implement making sure that there were sufficient funds for a goal withdrawal, we only tested positive, numerical input and will need to validate this input in the next iteration. Next, we plan to do more work on the password functionality of our application to make sure that signing in is only successful upon a matching password input. Additionally, we plan to perform slight modifications on the designation of accounts upon sign-in and profile creation. 

Lastly, in order to run this in the terminal, you wil need to compile the project and then run the menu file. The script for this should be as follows, starting with your location in the general project repo: 
javac src/cse237/*.java     
java -cp src cse237.menu

We are looking forward to seeing how this project will progress in the next stage! 